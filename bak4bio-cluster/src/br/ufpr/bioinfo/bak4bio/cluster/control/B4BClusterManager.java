package br.ufpr.bioinfo.bak4bio.cluster.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufpr.bioinfo.bak4bio.cluster.config.B4BConfig;
import br.ufpr.bioinfo.bak4bio.cluster.model.B4BUser;

public class B4BClusterManager {
	
	private Runtime runtime;
	
	public B4BClusterManager(Runtime runtime) {
		this.runtime = runtime;
	}
	
	public void start() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		
		this.createBlastFileFolder();
		B4BUser user = getUserAdmin();
		
		JSONArray blasts = this.getPendingBlasts(user);
		
		try {
			for(int i = 0; i < blasts.length(); i++){
				JSONObject blast = new JSONObject(blasts.get(i).toString());
				
				String id = blast.get("id").toString();
				String database = blast.get("database").toString();
				String program = blast.get("program").toString();
				
				String word_size = blast.get("word_size").toString(); //Word size // -W
				String max_target_sequence = blast.get("max_target_sequence").toString(); //Max target sequence // -b / NAO SEI
				String expect = blast.get("expect").toString(); //Expect // -e
				String max_matches_range = blast.get("max_matches_range").toString(); //Max matches range // -
				String m_scores = blast.get("m_scores").toString(); //Match/Mismatch scores // -r -q
				String gap_costs = blast.get("gap_costs").toString(); //Gap costs // -G -E
				
				String status = blast.get("status").toString();
				String title = blast.get("title").toString();
				String owner_id = blast.get("owner_id").toString();
				String entry_id = blast.get("entry_id").toString();
				
				String entry = blast.get("entry").toString();
				
				blast = new JSONObject(entry);
				String source_file_name = blast.get("source_file_name").toString();
				
				//Gerar Comando BLAST
				//exemplo:   /opt/openmpi/bin/mpirun -np 3 /opt/bio/mpiblast/bin/mpiblast -d month.nt -i /tmp/test.txt -p blastn -o /tmp/result.txt
				String command = "/opt/openmpi/bin/mpirun -np 3 /opt/bio/mpiblast/bin/mpiblast ";
				
				if (database == null || database.equals("") || database.equals("null")){
					database = " -d month.nt ";
				}else{
					database = " -d " + database + ".nt ";
				}
				
				//UNICA DB INSTALADA NO CLUSTER
				database = " -d month.nt ";
				
				if (program == null || program.equals("") || program.equals("null")){
					program = " -p blastn ";
				}else{
					program = " -p " + program + " ";
				}
				
				String parametersCommands = "";
				
				if (word_size == null || word_size.equals("") || word_size.equals("null")){
					word_size = " ";
				}else{
					word_size = " -W " + word_size + " ";
				}
				parametersCommands += word_size;
				
				if (max_target_sequence == null || max_target_sequence.equals("") || max_target_sequence.equals("null")){
					max_target_sequence = " ";
				}else{
					max_target_sequence = " -v " + max_target_sequence + " ";
				}
				parametersCommands += max_target_sequence;
				
				if (expect == null || expect.equals("") || expect.equals("null")){
					expect = " ";
				}else{
					expect = " -e " + expect + " ";
				}
				parametersCommands += expect;
				
				if (m_scores == null || m_scores.equals("") || m_scores.equals("null")){
					m_scores = " ";
				}else{
					try{
						String temp[] = m_scores.split(","); 
						m_scores = " -r " + temp[0] + " ";
						m_scores += " -q " + temp[1] + " ";
					}catch(Exception e){
						m_scores = " ";
					}
				}
				parametersCommands += m_scores;
				
				if (gap_costs == null || gap_costs.equals("") || gap_costs.equals("null")){
					gap_costs = " ";
				}else{
					try{
						String temp[] = gap_costs.split(" "); 
						gap_costs = " -G " + temp[0] + " ";
						gap_costs += " -E " + temp[1] + " ";
					}catch(Exception e){
						gap_costs = " ";
					}
				}
				parametersCommands += gap_costs;
				
				
				String input_file_string = "";
				try{
					//O arquivo a ser usado estï¿½ na url: url_servidor/<owner_id>/<source_file_name>
			       
					URL url = new URL("http://" + B4BConfig.IP + "/" + owner_id + "/" + source_file_name);
			        URLConnection yc = url.openConnection();
			        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			        
			        String inputLine;
			        while ((inputLine = in.readLine()) != null){
			        	input_file_string += inputLine + "\n";
			        }
			        
			        //System.out.println(input_file_string);
			        in.close();
				}catch(Exception e){
					System.out.println("Erro Ler Arquivo: " + e.toString());
				}
		        
				if(input_file_string.equals("")){
					changeBlastToStatusError(user, id);
					break;
				}
				
				String input_file = " " + B4BConfig.RAIZ + "blastfiles/" + owner_id + "/" + id + ".fasta ";
				
				//Cria um input file e coloca o input string dentro
				
				String ComandoCriaPasta = "mkdir " + B4BConfig.RAIZ + "blastfiles/" + owner_id;

				try{
					Process process = runtime.exec(ComandoCriaPasta);
					InputStream in = process.getInputStream();
					in.close();
				}catch(Exception e){
					System.out.println("Erro Runtime Exec: " + e.getMessage());
				}finally{
					//FIXME Ao criar a pasta, espera um segundo, pois senão o próximo comando, ao tentar acessar a pasta, dá erro dizendo que a pasta não existe
					try {
						Thread.sleep(1000);
					} catch(Exception e) {
						System.out.println("Error: " + e.getMessage());
					} 
				}
				
				FileWriter arquivo;  
				
				try {  
					arquivo = new FileWriter(new File(B4BConfig.RAIZ + "blastfiles/" + owner_id + "/" + id + ".fasta"));  
					arquivo.write(input_file_string);  
					arquivo.close();  
		        } catch (IOException e) {  
		        	System.out.println("Erro File IOException: " + e.toString());
		        } catch (Exception e) {  
		        	System.out.println("Erro File Exception: " + e.toString());
		        }finally{
		        	//FIXME Ao criar o arquivo, espera um segundo, pois senão o comando que vai acessar o arquivo dá erro dizendo que o arquivo não existe
					try {
						Thread.sleep(1000);
					} catch(Exception e) {
						System.out.println("Error: " + e.getMessage());
					} 
				}

				//Cria comando
				
				String output_file = " " + B4BConfig.RAIZ + "blastfiles/" + owner_id + "/OUT-" + id + ".txt ";
				
				String COMANDO = command + database + " -i " + input_file + program + parametersCommands + "-o" + output_file;
				
				//Chamar o atualizar (REST PUT url_servidor/blasts/<id>.json) BLAST (status = "processing" e start_at = "NOW")
				Date dataAgora = new Date();
				String start_at = df.format(dataAgora); //"2013-02-13T11:37:15Z"
				status = "processing";

				try{
					StringEntity entity = new StringEntity("{\"status\":\"" + status + "\",\"start_at\":\"" + start_at + "\"}");
					HttpPut httpPut = new HttpPut("http://" + B4BConfig.IP + "/blasts/" + id + ".json?auth_token=" + user.getAuthToken());
					entity.setContentType("application/json;charset=UTF-8");//text/plain;charset=UTF-8
				    entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
				    httpPut.setEntity(entity); 
					
					requestAndHandle(httpPut);
				    
				}catch(Exception e){
					System.out.println("Erro Connectar WS Atualizar Blast status=PROCESSING: " + e.toString());
				}
				
				//Iniciar Thread e Executar no Cluster
				Thread thread1 =  new Thread(new BlastTask(COMANDO, id, output_file, source_file_name, user.getAuthToken(), B4BConfig.IP), "thread1");
				thread1.start();
				
			}
		}catch (JSONException e1) {
			System.out.println("Nenhum blast encontrado. " + e1.toString());
		}
	}
	
	private JSONArray getPendingBlasts(B4BUser user) {
		JSONArray blasts = new JSONArray();
		InputStream is = null;
		
		try{
			HttpGet httpGet = new HttpGet("http://" + B4BConfig.IP + "/blasts.json?auth_token=" + user.getAuthToken() + "&status=pending");
		    
			DefaultHttpClient httpClient = new DefaultHttpClient();
			
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			
			String response = sb.toString();
			System.out.println("response: " + response + "\n");
			
			blasts = new JSONArray(response);
		}catch(Exception e){
			System.out.println("Error at getPendingBlasts: " + e.getMessage());
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				System.out.println("Erro at close inputStream: " + e.getMessage());
			}
		}
		
		return blasts;
	}

	private B4BUser getUserAdmin() {
		B4BUser user = null;
		
		OutputStreamWriter wr = null;
		BufferedReader rd = null;
		String response = "";
		String line;
		String data;
		
		try {
			data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode("bak4bio@gmail.com", "UTF-8");
			data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode("%=BAK4BIO=%", "UTF-8");

		    URL url = new URL("http://" + B4BConfig.IP + "/tokens.json");
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput(true);
		    
		    wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.write(data);
		    wr.flush();

		    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    
		    
		    while ((line = rd.readLine()) != null) {
		    	response += line;
		    }
		    
		    JSONObject json = new JSONObject(response);
		    user = new B4BUser(json.get("user_id").toString(), json.get("auth_token").toString());
		    
		    System.out.println("\n User: token = " + user.getAuthToken() + " and id = " + user.getId() + "\n");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (MalformedURLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (JSONException e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
		    try {
		    	if (wr != null) {
		    		wr.close();
		    	}
		    	
		    	if (rd != null) {
		    		rd.close();
		    	}
				
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		
		return user;
	}

	private void createBlastFileFolder() {
		InputStream in = null;
		String ComandoCriaPastaInicial = "mkdir blastfiles";
		
		try{
			Process proc = runtime.exec(ComandoCriaPastaInicial);
			in = proc.getInputStream();
		}catch(Exception e){
			System.out.println("Error at created folder: " + e.getMessage());
		}finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					System.out.println("Error at close inputStream: " + e.getMessage());
				}
			}
		}
	}

	private void changeBlastToStatusError(B4BUser user, String id){
		try{
			StringEntity entity = new StringEntity("{\"status\": \"error\"}");
			HttpPut httpPut = new HttpPut("http://" + B4BConfig.IP + "/blasts/" + id + ".json?auth_token=" + user.getAuthToken());
			entity.setContentType("application/json;charset=UTF-8");
		    entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
		    httpPut.setEntity(entity); 
			
			requestAndHandle(httpPut);
		    
		}catch(Exception e){
			System.out.println("Erro Connectar WS Atualizar Blast status=ERROR: " + e.toString());
		}
	}
	
	private static void requestAndHandle(HttpRequestBase httpRequest) throws IOException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			System.out.println("PUT Realizado: " + httpResponse.getStatusLine().getStatusCode());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}

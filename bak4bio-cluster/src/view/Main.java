package view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import json.JSONArray;
import json.JSONObject;
import json.JSONException;

import java.net.*;
import java.io.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.sun.net.httpserver.HttpPrincipal;

class RunnableThread implements Runnable{
	Thread runner;
	String COMANDO;
	String IDBLAST;
	String OUTFILE;
	String FILENAME;
	String AUTH_TOKEN;
	String IP;
	public RunnableThread(String CMD, String idBlast, String output_file, String source_file_name, String auth_token, String ip) {
		COMANDO = CMD;
		IDBLAST = idBlast;
		OUTFILE = output_file;
		FILENAME = source_file_name;
		AUTH_TOKEN = auth_token;
		IP = ip;
	}
	public void run(){
		System.out.println("Rodando Thread > " + COMANDO);
		String saida = "";
		Process proc = null;
		try{
			proc = Runtime.getRuntime().exec(COMANDO);
			InputStream in = proc.getInputStream();
			in.close();
			try
			{
				Thread.sleep(5000);
			}
			catch(Exception e)
			{
			} 
		}catch(Exception e){
			System.out.println("Erro Runtime Exec: " + e.toString());
		}finally{
			Date dataAgora = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		
			//Ao finalizar, pegar arquivo gerado pelo Cluster
			String output_file = OUTFILE;
			String NomeDoArquivo = "OUT-" + FILENAME;
			
			//Chamar o REST NEW url_servidor/contents/new.json (Para fazer um new de content , como se fosse um Constructor)
			HttpGet httpGet = new HttpGet("http://" + IP + "/contents/new.json?auth_token=" + AUTH_TOKEN);
		
			String response = "";
			try{
				DefaultHttpClient httpClient = new DefaultHttpClient();
				InputStream is = null;
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				response = sb.toString();
				is.close();
			}catch(Exception e){
				System.out.println(e.toString());
			}
			System.out.println("RESP CONTENT: " + response);
			
			JSONObject o = new JSONObject(response);
			
			//Inicializar Variáveis
			String owner_id = o.get("owner_id").toString();
			
			//Chamar o REST POST url_servidor/contents.json (passando as informações)
			//Para a parte de upload via json, você vai utilizar precisar jogar o arquivo em Base64
			// http://stackoverflow.com/questions/9758879/sending-files-to-a-rails-json-api
			//No cluster o arquivo encontra-se na pasta da variavel "output_file"
			String created_at = df.format(dataAgora);
			String source_file_name = NomeDoArquivo;
			
			
			
			
			
			
			
			
			
			
			
			
			
			String CONTENTTYPE = "";
			String ARQUIVO = "";
			
			try{
				StringEntity entity = new StringEntity("{\"description\":\"" + FILENAME + "\",\"owner_id\":" + owner_id + ",\"source\": {\"data\": \"" + ARQUIVO + "\", \"file_name\": \"" + NomeDoArquivo + "\", \"content_type:\" \"" + CONTENTTYPE + "\"}}");
				HttpPost httpPost = new HttpPost("http://" + IP + "/blasts/" + IDBLAST + ".json?auth_token=" + AUTH_TOKEN);
				entity.setContentType("application/json;charset=UTF-8");//text/plain;charset=UTF-8
			    entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
			    httpPost.setEntity(entity); 
				
				requestAndHandle(httpPost);
			    
			}catch(Exception e){
				System.out.println("Erro Connectar WS Atualizar Blast status=PROCESSING: " + e.toString());
			}
			
			//Atualizar as informações (status, output_id, end_at) do BLAST
			dataAgora = new Date();
			String end_at = df.format(dataAgora); //"2013-02-13T11:37:15Z"
			String status = "finished";
			String output_id = ""; /////COLOCAR ID AQUI
			
			try{
				StringEntity entity = new StringEntity("{\"status\":\"" + status + "\",\"end_at\":\"" + end_at + "\",\"output_id\":\"" + output_id + "\"}");
				HttpPut httpPut = new HttpPut("http://" + IP + "/blasts/" + IDBLAST + ".json?auth_token=" + AUTH_TOKEN);
				entity.setContentType("application/json;charset=UTF-8");//text/plain;charset=UTF-8
			    entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
			    httpPut.setEntity(entity); 
				
				requestAndHandle(httpPut);
			    
			}catch(Exception e){
				System.out.println("Erro Connectar WS Atualizar Blast status=PROCESSING: " + e.toString());
			}
			
			System.out.println("Thread Finalizada\n");
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

public class Main {
	
	private static String IP = "54.232.82.7";
	
	private static String RAIZ = "/root/workspace/BlastCluster/";
	
	public static void main(String[] args) {
		
		Process proc = null;
		String ComandoCriaPastaInicial = "mkdir blastfiles";
		String saida = "";
		try{
			proc = Runtime.getRuntime().exec(ComandoCriaPastaInicial);
			InputStream in = proc.getInputStream();
			in.close();
		}catch(Exception e){
			System.out.println("Erro Runtime Exec: " + e.toString());
		}
		
		String response = "";
		String auth_token = "";
	    String user_id = "";
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	    
		try{
			String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode("bak4bio@gmail.com", "UTF-8");
		    data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode("%=BAK4BIO=%", "UTF-8");

		    URL url = new URL("http://" + IP + "/tokens.json");
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.write(data);
		    wr.flush();

		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String line;
		    while ((line = rd.readLine()) != null) {
		    	response += line;
		    }
		    wr.close();
		    rd.close();
		    
		    JSONObject json = new JSONObject(response);
		    auth_token = json.get("auth_token").toString();
		    user_id = json.get("user_id").toString();
		    
		    System.out.println("token = " + auth_token + "\n");
		    
		}catch(Exception e){
			System.out.println("Erro Connectar WS Pegar Token: " + e.toString());
		}
	
		response = "";
		try{

			HttpGet httpGet = new HttpGet("http://" + IP + "/blasts.json?auth_token=" + auth_token + "&status=pending");
		    
			DefaultHttpClient httpClient = new DefaultHttpClient();
			InputStream is = null;
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			response = sb.toString();
			is.close();
	        
		    System.out.println("response: " + response + "\n");
		    
		}catch(Exception e){
			System.out.println("Erro Connectar WS Pegar Blasts: " + e.toString());
		}
		
		try {
			//Montar Objeto
			JSONArray blasts = new JSONArray(response);
			for(int i = 0; i < blasts.length(); i++){
				JSONObject o = new JSONObject(blasts.get(i).toString());
				
				//Inicializar Variáveis
				String id = o.get("id").toString();
				String database = o.get("database").toString();
				String program = o.get("program").toString();
				
				String word_size = o.get("word_size").toString(); //Word size // -W
				String max_target_sequence = o.get("max_target_sequence").toString(); //Max target sequence // -b / NAO SEI
				String expect = o.get("expect").toString(); //Expect // -e
				String max_matches_range = o.get("max_matches_range").toString(); //Max matches range // -
				String m_scores = o.get("m_scores").toString(); //Match/Mismatch scores // -r -q
				String gap_costs = o.get("gap_costs").toString(); //Gap costs // -G -E
				
				String status = o.get("status").toString();
				String title = o.get("title").toString();
				String owner_id = o.get("owner_id").toString();
				String entry_id = o.get("entry_id").toString();
				
				String entry = o.get("entry").toString();
				
				o = new JSONObject(entry);
				String source_file_name = o.get("source_file_name").toString();
				
				//Gerar Comando BLAST
				//exemplo:   /opt/openmpi/bin/mpirun -np 3 /opt/bio/mpiblast/bin/mpiblast -d month.nt -i /tmp/test.txt -p blastn -o /tmp/result.txt
				String comandoinicial = "/opt/openmpi/bin/mpirun -np 3 /opt/bio/mpiblast/bin/mpiblast ";
				
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
				
				String comandosadicionais = "";
				
				if (word_size == null || word_size.equals("") || word_size.equals("null")){
					word_size = " ";
				}else{
					word_size = " -W " + word_size + " ";
				}
				comandosadicionais += word_size;
				
				if (max_target_sequence == null || max_target_sequence.equals("") || max_target_sequence.equals("null")){
					max_target_sequence = " ";
				}else{
					max_target_sequence = " -v " + max_target_sequence + " ";
				}
				comandosadicionais += max_target_sequence;
				
				if (expect == null || expect.equals("") || expect.equals("null")){
					expect = " ";
				}else{
					expect = " -e " + expect + " ";
				}
				comandosadicionais += expect;
				
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
				comandosadicionais += m_scores;
				
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
				comandosadicionais += gap_costs;
				
				
				String input_file_string = "";
				try{
					//O arquivo a ser usado está na url: url_servidor/<owner_id>/<source_file_name>
			        URL url = new URL("http://" + IP + "/" + owner_id + "/" + source_file_name);
			        URLConnection yc = url.openConnection();
			        BufferedReader in = new BufferedReader(new InputStreamReader(
			                                    yc.getInputStream()));
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
					mudaStatusErro(auth_token, id);
					break;
				}
				
				String input_file = " " + RAIZ + "blastfiles/" + owner_id + "/" + id + ".fasta ";
				//Cria um input file e coloca o input string dentro
				proc = null;
				String ComandoCriaPasta = "mkdir " + RAIZ + "blastfiles/" + owner_id;
				saida = "";
				try{
					proc = Runtime.getRuntime().exec(ComandoCriaPasta);
					InputStream in = proc.getInputStream();
					in.close();
				}catch(Exception e){
					System.out.println("Erro Runtime Exec: " + e.toString());
				}finally{
					try
					{
						Thread.sleep(1000);
					}
					catch(Exception e)
					{
					} 
				}
				FileWriter arquivo;  
				try {  
					arquivo = new FileWriter(new File(RAIZ + "blastfiles/" + owner_id + "/" + id + ".fasta"));  
					arquivo.write(input_file_string);  
					arquivo.close();  
		        } catch (IOException e) {  
		        	System.out.println("Erro File IOException: " + e.toString());
		        } catch (Exception e) {  
		        	System.out.println("Erro File Exception: " + e.toString());
		        }finally{
					try
					{
						Thread.sleep(1000);
					}
					catch(Exception e)
					{
					} 
				}

				//Cria comando
				
				String output_file = " " + RAIZ + "blastfiles/" + owner_id + "/OUT-" + id + ".txt ";
				
				String COMANDO = comandoinicial + database + " -i " + input_file + program + comandosadicionais + "-o" + output_file;
				
				//Chamar o atualizar (REST PUT url_servidor/blasts/<id>.json) BLAST (status = "processing" e start_at = "NOW")
				Date dataAgora = new Date();
				String start_at = df.format(dataAgora); //"2013-02-13T11:37:15Z"
				status = "processing";

				response = "";
				try{
					StringEntity entity = new StringEntity("{\"status\":\"" + status + "\",\"start_at\":\"" + start_at + "\"}");
					HttpPut httpPut = new HttpPut("http://" + IP + "/blasts/" + id + ".json?auth_token=" + auth_token);
					entity.setContentType("application/json;charset=UTF-8");//text/plain;charset=UTF-8
				    entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
				    httpPut.setEntity(entity); 
					
					requestAndHandle(httpPut);
				    
				}catch(Exception e){
					System.out.println("Erro Connectar WS Atualizar Blast status=PROCESSING: " + e.toString());
				}
				
				//Iniciar Thread e Executar no Cluster
				Thread thread1 =  new Thread(new RunnableThread(COMANDO, id, output_file, source_file_name, auth_token, IP),"thread1");
				thread1.start();
				
			}
		}catch (JSONException e1) {
			System.out.println("Nenhum blast encontrado. " + e1.toString());
		}
	}
	
	public static void mudaStatusErro(String auth_token, String id){
		try{
			StringEntity entity = new StringEntity("{\"status\": \"error\"}");
			HttpPut httpPut = new HttpPut("http://" + IP + "/blasts/" + id + ".json?auth_token=" + auth_token);
			entity.setContentType("application/json;charset=UTF-8");//text/plain;charset=UTF-8
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

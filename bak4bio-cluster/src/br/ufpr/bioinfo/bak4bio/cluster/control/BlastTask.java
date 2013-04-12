package br.ufpr.bioinfo.bak4bio.cluster.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.axis.encoding.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

class BlastTask implements Runnable {
	Thread runner;
	String COMANDO;
	String IDBLAST;
	String OUTFILE;
	String FILENAME;
	String AUTH_TOKEN;
	String IP;
	
	public BlastTask(String CMD, String idBlast, String output_file, String source_file_name, String auth_token, String ip) {
		COMANDO = CMD;
		IDBLAST = idBlast;
		OUTFILE = output_file;
		FILENAME = source_file_name;
		AUTH_TOKEN = auth_token;
		IP = ip;
	}
	
	public void run(){
		System.out.println("Rodando Thread > " + COMANDO);
		Process proc = null;
		
		try{
			proc = Runtime.getRuntime().exec(COMANDO);
			InputStream in = proc.getInputStream();
			in.close();
			try {
				Thread.sleep(5000);
			} catch(Exception e) {
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
			
			try {
				JSONObject o = new JSONObject(response);
				String owner_id = o.get("owner_id").toString();
				
				//Chamar o REST POST url_servidor/contents.json (passando as informa��es)
				//Para a parte de upload via json, voc� vai utilizar precisar jogar o arquivo em Base64
				// http://stackoverflow.com/questions/9758879/sending-files-to-a-rails-json-api
				//No cluster o arquivo encontra-se na pasta da variavel "output_file"
				String created_at = df.format(dataAgora);
				String source_file_name = NomeDoArquivo;
				
				//TODO Aqui vc deve apontar para o arquivo gerado pelo BLAST!
				File arquivoGeradoPeloBlast = new File("CAMINHO DO ARQUIVO!");
				
				String ARQUIVO = Base64.encode(FileUtils.readFileToByteArray(arquivoGeradoPeloBlast));
				String CONTENTTYPE = "";
				
				StringEntity entity = new StringEntity("{\"description\":\"" + FILENAME + "\",\"owner_id\":" + owner_id + ",\"source\": {\"data\": \"" + ARQUIVO + "\", \"file_name\": \"" + NomeDoArquivo + "\", \"content_type:\" \"" + CONTENTTYPE + "\"}}");
				HttpPost httpPost = new HttpPost("http://" + IP + "/blasts/" + IDBLAST + ".json?auth_token=" + AUTH_TOKEN);
				entity.setContentType("application/json;charset=UTF-8");//text/plain;charset=UTF-8
			    entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
			    httpPost.setEntity(entity); 
				
				requestAndHandle(httpPost);
				
			} catch (JSONException e) {
				System.out.println("Erro: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("Erro Connectar WS Atualizar Blast status=PROCESSING: " + e.toString());
			}
			
			//Atualizar as informa��es (status, output_id, end_at) do BLAST
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

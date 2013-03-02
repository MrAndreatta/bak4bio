package br.ufpr.bioinfo.bak4bio.android.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import br.ufpr.bioinfo.bak4bio.android.model.ResultFormat;

public class Proxy {
	
	private String host;
	private ResultFormat resultFormat = ResultFormat.NONE;
	private String route = Routes.NONE;
	private Map<String, String> params;
	private HttpClient client;
	private HttpGet httpGet;
	private HttpResponse response;
	private int limit = 0;
	
	public Proxy(String host) {
		this.host = host;
		this.params = new HashMap<String, String>();
	}
	
	public void request() throws IOException {
		String params = this.urlParamsBuilder();
		//FIXME Refatorar a forma de definir o limite da busca dos registros
		String url = host + route + params;
		
		if (limit > 0) {
			url+= "/1," + limit; 
		}
		url += resultFormat.getName();
				
		
		Log.i("BAK4BIO", "Url ---->" + url);
		
		client = new DefaultHttpClient();
		httpGet = new HttpGet(url);
		response = client.execute(httpGet);
	}
	
	public String response() throws IOException {
		StringBuilder result = new StringBuilder("");
		
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		
		if (statusCode == 200) {
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String line;
			
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			return result.toString(); 
		}
		return null;
	}
	
	private String urlParamsBuilder() {
		String urlParams = "?";
		
		if (params.isEmpty()) {
			return "";
		}
		
		for (Map.Entry<String, String> entry : params.entrySet()) {
			urlParams += entry.getKey()  + "=" + entry.getValue() + "&";
		}
		
		return urlParams;
	}

	public void setRoute(String route) {
		this.route = route;
	}
	
	public void setResultFormat(ResultFormat resultFormat) {
		this.resultFormat = resultFormat;
	}

	public void addParam(String name, String value) {
		this.params.put(name, value);
	}
	
	public void removeParam(String name) {
		this.params.remove(name);
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}

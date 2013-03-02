package br.ufpr.bioinfo.bak4bio.android.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import br.ufpr.bioinfo.bak4bio.android.config.B4BConfig;
import br.ufpr.bioinfo.bak4bio.android.model.Result;

public class RestClient {
	
	private String rootUrl;
	
	public RestClient(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	public Result doGet(String relativeUrl, List<NameValuePair> params) throws IOException, URISyntaxException {
		String command = B4BConfig.getContextServer() + relativeUrl + "?" + URLEncodedUtils.format(params, "utf-8");
		URL url = new URL(B4BConfig.PROTOCOL, B4BConfig.getHostServer(), B4BConfig.getPortServer(), command); 

		HttpGet httpGet = new HttpGet(url.toURI());
		return requestAndHandle(httpGet);
	}
	
	public Result doPut(String relativeUrl, JSONObject params) throws IOException {
		HttpPut httpPut = new HttpPut(getUrl(relativeUrl));
		httpPut.setEntity(createBodyInput(params));
		
		return requestAndHandle(httpPut);
	}
	
	public Result doPost(String relativeUrl, JSONObject params) throws IOException {
		HttpPost httpPost = new HttpPost(getUrl(relativeUrl));
		httpPost.setEntity(createBodyInput(params));
		
		return requestAndHandle(httpPost);
	}
	
	public Result doDelete(String relativeUrl) throws IOException {
		HttpDelete httpDelete = new HttpDelete(getUrl(relativeUrl));
		
		return requestAndHandle(httpDelete);
	}

	private StringEntity createBodyInput(JSONObject params) throws UnsupportedEncodingException {
		StringEntity input = new StringEntity(params.toString());
		input.setContentType("application/json");
		
		return input;
	}
	
	private Result requestAndHandle(HttpRequestBase httpRequest) throws IOException {
		Result result = new Result();
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		InputStream is = null;
		
		try {
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			
			result.setStatusCode(httpResponse.getStatusLine().getStatusCode());
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			
			result.setValue(sb.toString());
			
			return result;
		} finally {
			is.close();
		}
	}
	
	private String getUrl(String relativeUrl) {
		return rootUrl + relativeUrl;
	}

	public String getRootUrl() {
		return rootUrl;
	}
	
}
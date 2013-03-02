package br.ufpr.bioinfo.bak4bio.android.operations;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import br.ufpr.bioinfo.bak4bio.android.model.Result;
import br.ufpr.bioinfo.bak4bio.android.proxy.RestClient;

public class BaseGetOperation {
	
	private RestClient restClient;
	
	public BaseGetOperation(RestClient restClient) {
		this.restClient = restClient;
	}

	public JSONArray execute(String path, List<NameValuePair> params) throws OperationException {
		try {
			Result response = restClient.doGet(path, params);
			
			if (response.getStatusCode() == 200) {
				Object json = new JSONTokener(response.getValue()).nextValue();
				
				if (json instanceof JSONObject) {
					return new JSONArray().put(json);
				}else if (json instanceof JSONArray) {
					return (JSONArray) json;
				}
				
			}else if (response.getStatusCode() == 401) {
				throw new OperationException("Unauthorized");
			}else if (response.getStatusCode() == 500) {
				throw new OperationException("Internal server error");
			}else {
				throw new OperationException("Code: " + response.getStatusCode());
			}
		} catch (IOException e) {
			throw new OperationException("IO Exception:" + e.getMessage());
		} catch (URISyntaxException e) {
			throw new OperationException("URI Exception:" + e.getMessage());
		} catch (JSONException e) {
			throw new OperationException("JSON Exception:" + e.getMessage());
		}
		return null;
	}

}

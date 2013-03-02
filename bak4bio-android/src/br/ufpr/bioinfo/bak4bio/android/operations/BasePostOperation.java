package br.ufpr.bioinfo.bak4bio.android.operations;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import br.ufpr.bioinfo.bak4bio.android.model.Result;
import br.ufpr.bioinfo.bak4bio.android.proxy.RestClient;

public class BasePostOperation {
	
	private RestClient restClient; 
	
	public BasePostOperation(RestClient restClient) {
		this.restClient = restClient;
	}

	public JSONArray execute(String path, JSONObject params) throws OperationException {
		Result response;
		try {
			response = restClient.doPost(path, params);
			
			if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
				Object json = new JSONTokener(response.getValue()).nextValue();
				
				if (json instanceof JSONObject) {
					return new JSONArray().put(json);
				}else if (json instanceof JSONArray) {
					return (JSONArray) json;
				}
			}else if (response.getStatusCode() == 401) {
				throw new OperationException(this.parseErrors(response));
			}else if (response.getStatusCode() == 422) {
				throw new OperationException(this.parseErrors(response));
			}else if (response.getStatusCode() == 500) {
				throw new OperationException("Internal server error");
			}else {
				throw new OperationException("Code: " + response.getStatusCode() + "\n" + this.parseErrors(response));
			}
		} catch (IOException e) {
			throw new OperationException("IO Exception:" + e.getMessage());
		} catch (JSONException e) {
			throw new OperationException("JSON Exception:" + e.getMessage());
		}
		
		return null;
	}

	private String parseErrors(Result response) throws JSONException {
		Object json = new JSONTokener(response.getValue()).nextValue();
		JSONArray jsonErrors = new JSONArray();
		String message = "";
		
		if (json instanceof JSONObject) {
			jsonErrors.put(json);
		}else if (json instanceof JSONArray) {
			jsonErrors = (JSONArray) json;
		}
		
		for (int i = 0; i < jsonErrors.length(); i++) {
			message += jsonErrors.getString(i) + "\n";
		}
		return message;
	}
}

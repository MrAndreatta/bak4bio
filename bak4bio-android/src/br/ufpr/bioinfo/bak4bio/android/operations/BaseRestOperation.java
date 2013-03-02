package br.ufpr.bioinfo.bak4bio.android.operations;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufpr.bioinfo.bak4bio.android.proxy.RestClient;

public class BaseRestOperation<T> {
	
	protected RestClient restClient;
	protected JSONObject params = new JSONObject();
	protected List<NameValuePair> params2 = new ArrayList<NameValuePair>();
	
	protected List<T> indexResult;
	protected T newResult;
	protected T createResult;
	
	protected BaseGetOperation getOperation;
	protected BasePostOperation postOperation;
	
	
	public BaseRestOperation(RestClient restClient) {
		this.restClient = restClient;
		this.getOperation = new BaseGetOperation(this.restClient);
		this.postOperation = new BasePostOperation(this.restClient);
	}
	
	public void indexOperation() throws OperationException {
		this.indexResult = new ArrayList<T>();
		
		JSONArray jsonArray = this.getOperation.execute(getIndexPath(), this.params2);
		
		if (jsonArray != null) { 
		   int len = jsonArray.length();
		   
		   for (int i=0; i< len;i++){ 
			   try {
				   this.indexResult.add(this.parser(jsonArray.getJSONObject(i)));
			   } catch (JSONException e) {
				   throw new OperationException("JSON Problem: " + e.getMessage());
			   } catch (ParseException e) {
				   throw new OperationException("Parser Problem: " + e.getMessage());
			   }
		   } 
		}
	}
	
	public void newOperation() throws OperationException {
		JSONArray jsonArray = this.getOperation.execute(this.getNewPath(), this.params2);
		
		try {
			this.newResult = this.parser(jsonArray.getJSONObject(0));
		} catch (JSONException e) {
			throw new OperationException("JSON Problem: " + e.getMessage());
		} catch (ParseException e) {
			throw new OperationException("Parser Problem: " + e.getMessage());
		}
	}
	
	public void createOperation() throws OperationException {
		JSONArray jsonArray = this.postOperation.execute(this.getCreatePath(), params);
		
		try {
			this.createResult = this.parser(jsonArray.getJSONObject(0));
		} catch (JSONException e) {
			throw new OperationException("JSON Problem: " + e.getMessage());
		} catch (ParseException e) {
			throw new OperationException("Parser Problem: " + e.getMessage());
		}
	}
	
	protected String getIndexPath() {
		return null;
	}
	
	protected String getNewPath() {
		return null;
	}
	
	protected String getCreatePath() {
		return null;
	}
	
	protected T parser(JSONObject jsonObject) throws JSONException, ParseException {
		return null;
	}

	public void addParam(String key, Object value) throws JSONException {
		this.params.put(key, value);
		this.params2.add(new BasicNameValuePair(key, value.toString()));
	}
	
	public List<T> getIndexResult() {
		return indexResult;
	}
	
	public T getNewResult() {
		return newResult;
	}
	
	public T getCreateResult() {
		return createResult;
	}
}

package br.ufpr.bioinfo.bak4bio.android.operations;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufpr.bioinfo.bak4bio.android.model.Content;
import br.ufpr.bioinfo.bak4bio.android.model.User;
import br.ufpr.bioinfo.bak4bio.android.model.parser.ContentParser;
import br.ufpr.bioinfo.bak4bio.android.proxy.RestClient;

public class BioboxOperations extends BaseRestOperation<Content> {
	
	private User loginResult;
	
	public BioboxOperations(RestClient restClient) {
		super(restClient);
	}
	
	@Override
	protected String getIndexPath() {
		return "contents.json";
	}
	
	@Override
	protected String getNewPath() {
		return "contents/new.json";
	}
	
	@Override
	protected Content parser(JSONObject jsonObject) throws JSONException {
		return ContentParser.json2entity(jsonObject);
	}
	
	public User getLoginResult() {
		return this.loginResult;
	}
}

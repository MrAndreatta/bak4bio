package br.ufpr.bioinfo.bak4bio.android.operations;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufpr.bioinfo.bak4bio.android.model.Token;
import br.ufpr.bioinfo.bak4bio.android.model.parser.TokenParser;
import br.ufpr.bioinfo.bak4bio.android.proxy.RestClient;

public class TokenOperations extends BaseRestOperation<Token>{
	
	public TokenOperations(RestClient restClient) {
		super(restClient);
	}
	
	@Override
	protected String getCreatePath() {
		return "tokens.json";
	}
	
	@Override
	protected Token parser(JSONObject jsonObject) throws JSONException,ParseException {
		return TokenParser.json2entity(jsonObject);
	}

}

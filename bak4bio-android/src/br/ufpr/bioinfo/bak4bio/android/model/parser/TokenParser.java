package br.ufpr.bioinfo.bak4bio.android.model.parser;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufpr.bioinfo.bak4bio.android.model.Token;

public class TokenParser {
	
	public static Token json2entity(JSONObject json) throws JSONException {
		Token token = new Token(json.getString("auth_token"), json.getInt("user_id") );
		
		return token;
	}
	
	public static JSONObject entity2Json() {
		return new JSONObject();
	}
}

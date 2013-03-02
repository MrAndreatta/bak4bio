package br.ufpr.bioinfo.bak4bio.android.model.parser;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufpr.bioinfo.bak4bio.android.model.Content;

public class ContentParser {

	public static Content json2entity(JSONObject json) throws JSONException {
		Content content = new Content();
		content.setId(json.getInt("id"));
		content.setDescription(json.getString("description"));
		content.setSourceFileName(json.getString("source_file_name"));
		content.setSourceFileSize(json.isNull("source_file_size")? 0 : json.getInt("source_file_size"));
		
		return content;
	}
	
	public static JSONObject entity2Json() {
		return new JSONObject();
	}
	
}

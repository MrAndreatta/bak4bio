package br.ufpr.bioinfo.bak4bio.android.model.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufpr.bioinfo.bak4bio.android.model.Blast;

public class BlastParser {

	public static Blast json2entity(JSONObject json) throws JSONException, ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Blast blast = new Blast();
		
		blast.setId(json.isNull("id") ? null : json.getInt("id"));
		blast.setTitle((json.getString("title")));
		blast.setStartAt(json.isNull("start_at") ? null : format.parse(json.getString("start_at")));
		blast.setStatus(json.getString("status"));
		blast.setEndAt(json.isNull("end_at") ? null :format.parse(json.getString("end_at")));
		
		if (!json.isNull("output_id")) {
			blast.setOutput(ContentParser.json2entity(json.getJSONObject("output")));
		}
			
		return blast;
	}
	
	public static JSONObject entity2Json() {
		return new JSONObject();
	}
	
}

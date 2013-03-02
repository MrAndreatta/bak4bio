package br.ufpr.bioinfo.bak4bio.android.operations;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufpr.bioinfo.bak4bio.android.model.Blast;
import br.ufpr.bioinfo.bak4bio.android.model.parser.BlastParser;
import br.ufpr.bioinfo.bak4bio.android.proxy.RestClient;

public class BlastOperations extends BaseRestOperation<Blast> {

	public BlastOperations(RestClient restClient) {
		super(restClient);
	}

	@Override
	protected Blast parser(JSONObject jsonObject) throws JSONException, ParseException {
		return BlastParser.json2entity(jsonObject);
	}
	
	@Override
	protected String getIndexPath() {
		return "blasts.json";
	}
	
	@Override
	protected String getNewPath() {
		return "blasts/new.json";
	}
	
	@Override
	protected String getCreatePath() {
		return "blasts.json";
	}
}

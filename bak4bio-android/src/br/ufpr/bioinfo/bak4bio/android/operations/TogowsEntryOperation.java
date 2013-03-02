package br.ufpr.bioinfo.bak4bio.android.operations;

import org.json.JSONArray;

import br.ufpr.bioinfo.bak4bio.android.model.ResultFormat;
import br.ufpr.bioinfo.bak4bio.android.proxy.Proxy;
import br.ufpr.bioinfo.bak4bio.android.proxy.Routes;

public class TogowsEntryOperation {

	private String id;
	private String database;
	private String field = "";
	private Proxy proxy;
	private String result;
	
	public TogowsEntryOperation(Proxy proxy) {
		this.proxy = proxy;
	}

	public void idSetting(String id) {
		this.id = id;
	}
	
	public void databaseSetting(String dataBase) {
		this.database = dataBase;
	}
	
	public void search() throws OperationException {
		String route = Routes.ENTRY + "/" + this.getDatabaseRoute(database) + "/" + id;
		
		if (!field.equals("")) {
			route+= "/" + field;
		}
		
		proxy.setRoute(route);
		proxy.setResultFormat(ResultFormat.JSON);
		
		try {
			proxy.request();
			JSONArray array = new JSONArray(proxy.response());
			result = array.get(0).toString();
		} catch (Exception e) {
			throw new OperationException("Erro ao consultar banco de dados." + e.getMessage());
		}
	}

	public String getResult() {
		return result;
	}
	
	private String getDatabaseRoute(String dataBase) {
		//TODO Jogar para as Routes.java com as devidas constantes
		return dataBase;
	}

	public void fieldSetting(String field) {
		this.field = field;
		
	}
}
package br.ufpr.bioinfo.bak4bio.android.operations;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import br.ufpr.bioinfo.bak4bio.android.model.ResultFormat;
import br.ufpr.bioinfo.bak4bio.android.proxy.Proxy;
import br.ufpr.bioinfo.bak4bio.android.proxy.Routes;

public class TogowsSearchOperation {
	
	private String database;
	private String query;
	private Proxy proxy;
	private List<String> result = new ArrayList<String>();
	
	public TogowsSearchOperation(Proxy proxy) {
		this.proxy = proxy;
	}
	
	public void databaseSetting(String dataBase) {
		this.database = dataBase;
	}

	public void filter(String query) {
		this.query = query;
	}

	public void search() throws OperationException {
		String route = Routes.SEARCH + "/" + this.getDatabaseRoute(database) + "/" + this.query;
		proxy.setRoute(route);
		proxy.setResultFormat(ResultFormat.JSON);
		proxy.setLimit(20);
		
		try {
			proxy.request();
			
			String resultResponse = proxy.response();
			
			if (resultResponse == null) {
				return;
			}
			
			JSONArray jsonArray = new JSONArray(resultResponse);;
			
			//FIXME Workaround (Bug in REST PDBJ)
			if (database.equals("pdb")) {
				jsonArray = new JSONArray(jsonArray.getString(0));
			}
			
			if (jsonArray != null) { 
			   int len = jsonArray.length();
			   
			   for (int i=0; i< len;i++){ 
			    result.add(jsonArray.get(i).toString());
			   } 
			} 
		} catch (Exception e) {
			throw new OperationException("Erro ao consultar banco de dados: " + e.getMessage());
		}
	}

	public List<String> getResult() {
		return result;
	}
	
	private String getDatabaseRoute(String dataBase) {
		//TODO Jogar para as Routes.java com as devidas constantes
		return dataBase;
	}

}

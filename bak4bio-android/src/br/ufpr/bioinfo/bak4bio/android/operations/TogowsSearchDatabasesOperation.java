package br.ufpr.bioinfo.bak4bio.android.operations;

import br.ufpr.bioinfo.bak4bio.android.model.ResultFormat;
import br.ufpr.bioinfo.bak4bio.android.proxy.Proxy;
import br.ufpr.bioinfo.bak4bio.android.proxy.Routes;

public class TogowsSearchDatabasesOperation {
	
	private String[] result;
	private Proxy proxy;
	
	public TogowsSearchDatabasesOperation(Proxy proxy) {
		this.proxy = proxy;
	}
	
	public void search() throws OperationException {
		try {
			proxy.setResultFormat(ResultFormat.NONE);
			proxy.setRoute(Routes.ENTRY);
			proxy.request();
			String databases = proxy.response();
			
			result = databases.split("\t");
		} catch (Exception e) {
			throw new OperationException("Erro ao consultar banco de dados." + e.getMessage());
		}
	}
	
	public String[] getResult() {
		return this.result;
	}
}

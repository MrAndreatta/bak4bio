package br.ufpr.bioinfo.bak4bio.android.operations;

import br.ufpr.bioinfo.bak4bio.android.config.B4BConfig;
import br.ufpr.bioinfo.bak4bio.android.config.TogoWSConfig;
import br.ufpr.bioinfo.bak4bio.android.model.User;
import br.ufpr.bioinfo.bak4bio.android.proxy.Proxy;
import br.ufpr.bioinfo.bak4bio.android.proxy.RestClient;
import android.content.Context;

public class Operations {
	
	private boolean started = false;
	
	private Context context;
	
	public Operations(Context context) {
		this.context = context;
	}
	
	public void start() {
		if (started) {
			throw new IllegalStateException("Já está iniciado");
		}
		
		started = true;
	}
	
	public void finish() {
		if (!started) {
			throw new IllegalStateException("Não está iniciado");
		}
		started = false;
	}

	public TogowsSearchDatabasesOperation togowsSearchDatabasesOperation() {
		Proxy proxy = new Proxy(TogoWSConfig.getUrlServer());
		TogowsSearchDatabasesOperation baseEntryOperation = new TogowsSearchDatabasesOperation(proxy);
		
		return baseEntryOperation;
	}
	
	public TogowsSearchOperation togowsSearchOperation() {
		Proxy proxy = new Proxy(TogoWSConfig.getUrlServer());
		TogowsSearchOperation searchOperation = new TogowsSearchOperation(proxy);
		
		return searchOperation;
	}

	public TogowsEntryOperation togowsEntryOperation() {
		Proxy proxy = new Proxy(TogoWSConfig.getUrlServer());
		TogowsEntryOperation entryOperation = new TogowsEntryOperation(proxy);
		
		return entryOperation;
	}
	
	public BioboxOperations bioboxOperations() {
		RestClient restClient = new RestClient(B4BConfig.getUrlServer());
		BioboxOperations searchContentsOperation = new BioboxOperations(restClient);
		
		return searchContentsOperation;
	}
	
	public BlastOperations blastOperations() {
		RestClient restClient = new RestClient(B4BConfig.getUrlServer());
		BlastOperations blastOperations = new BlastOperations(restClient);
		
		return blastOperations;
	}
	
	public TokenOperations tokenOperations() {
		RestClient restClient = new RestClient(B4BConfig.getUrlServer());
		TokenOperations tokenOperations = new TokenOperations(restClient);
		
		return tokenOperations;
	}
}

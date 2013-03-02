package br.ufpr.bioinfo.bak4bio.android.control;

import br.ufpr.bioinfo.bak4bio.android.model.Token;

public class ApplicationManager {

	private static ApplicationManager instance = null;
	
	private Token currentToken;
	
	private ApplicationManager() {
	}
	
	public static synchronized ApplicationManager getInstance() {
		if (instance == null) {
	    	instance = new ApplicationManager();
	    }
	    return instance;
	}
	
	public void setCurrentToken(Token currentToken) {
		this.currentToken = currentToken;
	}
	
	public Token getCurrentToken() {
		return this.currentToken;
	}
	
}

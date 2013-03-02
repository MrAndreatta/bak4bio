package br.ufpr.bioinfo.bak4bio.android.model;

public class Token {
	
	private int userId;
	private String auth_token;

	public Token(String auth_token, int userId) {
		this.auth_token = auth_token;
		this.userId = userId;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public String getAuthToken() {
		return this.auth_token;
	}

}

package br.ufpr.bioinfo.bak4bio.android.model;

public class User {
	
	private Integer id;
	private String auth_token;

	public User(String auth_token, int id) {
		this.auth_token = auth_token;
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getAuthToken() {
		return this.auth_token;
	}

}

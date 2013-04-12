package br.ufpr.bioinfo.bak4bio.cluster.model;

public class B4BUser {

	private String id;
	private String authToken;
	
	public B4BUser(String id, String authToken) {
		this.id = id;
		this.authToken = authToken;
	}

	public String getId() {
		return id;
	}

	public String getAuthToken() {
		return authToken;
	}
}

package br.ufpr.bioinfo.bak4bio.android.model;

import java.io.Serializable;

public class Field implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private boolean identifier;
	
	public Field(String name, boolean isIdentifier) {
		this.name = name;
		this.identifier = isIdentifier;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isIdentifier() {
		return identifier;
	}

	public void setIdentifier(boolean isIdentifier) {
		this.identifier = isIdentifier;
	}
}

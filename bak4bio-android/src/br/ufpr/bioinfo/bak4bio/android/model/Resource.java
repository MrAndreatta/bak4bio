package br.ufpr.bioinfo.bak4bio.android.model;

import java.io.Serializable;
import java.util.List;

public class Resource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String KEGG = "KEGG";
	public static final String DDJB = "DDJB";
	public static final String NCBI = "NCBI";
	public static final String EBI = "EBI";
	public static final String PDBJ = "PDBJ";
	
	public static final String NONE = "none";
	
	private String name;
	private List<Database> databases;
	
	public Resource(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public List<Database> getDatabases() {
		return databases;
	}

	public void setDatabases(List<Database> databases) {
		this.databases = databases;
	}
}

package br.ufpr.bioinfo.bak4bio.android.model;

public enum ResultFormat {
	JSON(".json"),
	XML(".xml"),
	FASTA(".fasta"),
	GFF(".gff"),
	NONE("");
	
	private final String name;

	private ResultFormat(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

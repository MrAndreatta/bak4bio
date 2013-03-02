package br.ufpr.bioinfo.bak4bio.android.model;

import java.io.Serializable;
import java.util.List;

public class Database implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//KEGG Databases
	public static final String KEGG_COMPOUND = "compound";
	public static final String KEGG_DRUG = "drug";
	public static final String KEGG_ENZYME = "enzyme"; 
	//public static final String KEGG_GENES = "genes"; very slow
	public static final String KEGG_GLYCAN = "glycan";
	public static final String KEGG_ORTHOLOGY = "orthology";
	public static final String KEGG_REACTION = "reaction";
	public static final String KEGG_MODULE = "module";
	public static final String KEGG_PATHWAY = "pathway";
	
	//DDJB Databases
	public static final String DDBJ_DDBJ = "ddbj";
	public static final String DDBJ_DAD = "dad";
	
	//NCBI Databases
	public static final String NCBI_NUCCORE = "nuccore";
	public static final String NCBI_NUCEST = "nucest";
	public static final String NCBI_NUCGSS = "nucgss";
	public static final String NCBI_NUCLEOTIDE = "nucleotide";
	public static final String NCBI_PROTEIN = "protein";
	public static final String NCBI_GENE = "gene";
	//public static final String NCBI_OMIM = "omim"; ["Database: omim - is not supported for rettype=detailed retmode=text"]
	public static final String NCBI_HOMOLOGENE = "homologene";
	public static final String NCBI_SNP = "snp";
	public static final String NCBI_MESH = "mesh";
	public static final String NCBI_PUBMED = "pubmed";
	
	//EBI Databases
	public static final String EBI_EMBL = "embl";
	public static final String EBI_UNIPROT = "uniprot";
	//public static final String EBI_UNIPARC = "uniparc"; Not work
	//public static final String EBI_UNIREF100 = "uniref100"; Just XML
	//public static final String EBI_UNIREF90 = "uniref90"; Just XML
	//public static final String EBI_UNIREF50 = "uniref50"; Just XML
	
	//PDBJ Databases
	public static final String PDBJ_PDB = "pdb";
	
	//None
	public static final String NONE = "none";

	private String name;
	private List<Field> fields;
	
	public Database(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}

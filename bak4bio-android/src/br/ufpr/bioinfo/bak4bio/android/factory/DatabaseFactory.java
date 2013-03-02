package br.ufpr.bioinfo.bak4bio.android.factory;

import java.util.ArrayList;
import java.util.List;

import br.ufpr.bioinfo.bak4bio.android.model.Database;
import br.ufpr.bioinfo.bak4bio.android.model.Field;

public class DatabaseFactory {
	
	public static Database build(String name) {
		
		//DDBJ
		if (name == Database.DDBJ_DAD) {
			return ddbjDadBuilder(name);
		}
		if (name == Database.DDBJ_DDBJ) {
			return ddbjDdbjBuilder(name);
		}
		
		//EBI
		if (name == Database.EBI_EMBL) {
			return ebiEmblBuilder(name);
		}
		if (name == Database.EBI_UNIPROT) {
			return ebiUniprotBuilder(name);
		}
		
		//KEGG
		if (name == Database.KEGG_DRUG) {
			return keggDrugBuilder(name);
		}
		if (name == Database.KEGG_ENZYME) {
			return keggEnzymeBuilder(name);
		}
		if (name == Database.KEGG_PATHWAY) {
			return keggPathwayBuilder(name);
		}
		
		//http://togows.dbcls.jp/entry/compound/C00011/names
		//http://togows.dbcls.jp/entry/glycan/G00001/name
		//http://togows.dbcls.jp/entry/reaction/R00005/name
		//http://togows.dbcls.jp/entry/module/M00002/name
		
		//NCBI
		if (name == Database.NCBI_GENE) {
			return ncbiGeneBuilder(name);
		}
		if (name == Database.NCBI_NUCLEOTIDE) {
			return ncbiNucleotideBuilder(name);
		}
		if (name == Database.NCBI_PROTEIN) {
			return ncbiProteinBuilder(name);
		}
		if (name == Database.NCBI_PUBMED) {
			return ncbiPubmedBuilder(name);
		}
		
		//http://togows.dbcls.jp/entry/nuccore/399145347/definition
		//http://togows.dbcls.jp/entry/nucest/399145347/definition
		//http://togows.dbcls.jp/entry/nucgss/399145347/definition
		//http://togows.dbcls.jp/entry/homologene/2325/caption
		//snp// not description field
		//mesh not fields
		
		//PDBJ
		if (name == Database.PDBJ_PDB) {
			return pdbjPdbBuilder(name);
		}
		
		return new Database(Database.NONE);
	}
	
	//DDBJ
	private static Database ddbjDadBuilder(String name) {
		Database database = new Database(name);
		return database; 
	}
	
	private static Database ddbjDdbjBuilder(String name) {
		Database database = new Database(name);
		return database; 
	}
	
	//EBI
	private static Database ebiEmblBuilder(String name) {
		Database database = new Database(name);
		List<Field> fields = new ArrayList<Field>();
		fields.add(new Field("definition", true));
		database.setFields(fields);
		
		return database; 
	}
	
	private static Database ebiUniprotBuilder(String name) {
		Database database = new Database(name);
		List<Field> fields = new ArrayList<Field>();
		fields.add(new Field("definition", true));
		database.setFields(fields);
		return database; 
	}
	
	//KEGG
	private static Database keggDrugBuilder(String name) {
		Database database = new Database(name);
		List<Field> fields = new ArrayList<Field>();
		fields.add(new Field("name", true));
		database.setFields(fields);
		
		return database; 
	}
	
	private static Database keggEnzymeBuilder(String name) {
		Database database = new Database(name);
		List<Field> fields = new ArrayList<Field>();
		fields.add(new Field("name", true));
		database.setFields(fields);
		
		return database; 
	}
	
	private static Database keggPathwayBuilder(String name) {
		Database database = new Database(name);
		List<Field> fields = new ArrayList<Field>();
		fields.add(new Field("name", true));
		database.setFields(fields);
		
		return database; 
	}
	
	//NCBI
	private static Database ncbiGeneBuilder(String name) {
		Database database = new Database(name);
		List<Field> fields = new ArrayList<Field>();
		fields.add(new Field("names", true));
		database.setFields(fields);
		
		return database; 
	}
	
	private static Database ncbiNucleotideBuilder(String name) {
		Database database = new Database(name);
		List<Field> fields = new ArrayList<Field>();
		fields.add(new Field("organism", true));
		database.setFields(fields);
		
		return database; 
	}
	
	private static Database ncbiProteinBuilder(String name) {
		Database database = new Database(name);
		List<Field> fields = new ArrayList<Field>();
		fields.add(new Field("common_name", true));
		database.setFields(fields);
		
		return database; 
	}
	
	private static Database ncbiPubmedBuilder(String name) {
		Database database = new Database(name);
		List<Field> fields = new ArrayList<Field>();
		fields.add(new Field("title", true));
		database.setFields(fields);
		
		return database; 
	}
	
	//PDBJ
	private static Database pdbjPdbBuilder(String name) {
		Database database = new Database(name);
		List<Field> fields = new ArrayList<Field>();
		fields.add(new Field("keywords", true));
		database.setFields(fields);
		
		return database; 
	}
}
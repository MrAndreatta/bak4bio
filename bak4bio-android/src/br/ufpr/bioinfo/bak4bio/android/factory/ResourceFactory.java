package br.ufpr.bioinfo.bak4bio.android.factory;

import java.util.ArrayList;
import java.util.List;

import br.ufpr.bioinfo.bak4bio.android.model.Database;
import br.ufpr.bioinfo.bak4bio.android.model.Resource;

public class ResourceFactory {
	
	public static Resource build(String name) {
		
		if (name == Resource.DDJB) {
			return ddjbBuilder(name);
		}
		if (name == Resource.EBI) {
			return ebiBuilder(name);
		}
		if (name == Resource.KEGG) {
			return keggBuilder(name);
		}
		if (name == Resource.NCBI) {
			return ncbiBuilder(name);
		}
		if (name == Resource.PDBJ) {
			return pdbjBuilder(name);
		}
		return new Resource(Resource.NONE);
	}
	
	private static Resource ddjbBuilder(String name) {
		Resource resource = new Resource(name);
		
		List<Database> databases = new ArrayList<Database>();
		databases.add(DatabaseFactory.build(Database.DDBJ_DAD));
		databases.add(DatabaseFactory.build(Database.DDBJ_DDBJ));
		
		resource.setDatabases(databases);
		return resource;
	}
	
	private static Resource ebiBuilder(String name) {
		Resource resource = new Resource(name);
		
		List<Database> databases = new ArrayList<Database>();
		databases.add(DatabaseFactory.build(Database.EBI_EMBL));
		//databases.add(DatabaseFactory.build(Database.EBI_UNIPARC));
		databases.add(DatabaseFactory.build(Database.EBI_UNIPROT));
		//databases.add(DatabaseFactory.build(Database.EBI_UNIREF100));
		//databases.add(DatabaseFactory.build(Database.EBI_UNIREF50));
		//databases.add(DatabaseFactory.build(Database.EBI_UNIREF90));

		resource.setDatabases(databases);
		return resource;
	}
	
	private static Resource keggBuilder(String name) {
		Resource resource = new Resource(name);
		
		List<Database> databases = new ArrayList<Database>();
		//databases.add(DatabaseFactory.build(Database.KEGG_COMPOUND));
		databases.add(DatabaseFactory.build(Database.KEGG_DRUG));
		databases.add(DatabaseFactory.build(Database.KEGG_ENZYME));
		//databases.add(DatabaseFactory.build(Database.KEGG_GENES));
		//databases.add(DatabaseFactory.build(Database.KEGG_GLYCAN));
		//databases.add(DatabaseFactory.build(Database.KEGG_MODULE));
		//databases.add(DatabaseFactory.build(Database.KEGG_ORTHOLOGY));
		databases.add(DatabaseFactory.build(Database.KEGG_PATHWAY));
		//databases.add(DatabaseFactory.build(Database.KEGG_REACTION));

		resource.setDatabases(databases);
		return resource;
	}
	
	private static Resource ncbiBuilder(String name) {
		Resource resource = new Resource(name);
		
		List<Database> databases = new ArrayList<Database>();
		databases.add(DatabaseFactory.build(Database.NCBI_GENE));
		//databases.add(DatabaseFactory.build(Database.NCBI_HOMOLOGENE));
		//databases.add(DatabaseFactory.build(Database.NCBI_MESH));
		//databases.add(DatabaseFactory.build(Database.NCBI_NUCCORE));
		//databases.add(DatabaseFactory.build(Database.NCBI_NUCEST));
		//databases.add(DatabaseFactory.build(Database.NCBI_NUCGSS));
		databases.add(DatabaseFactory.build(Database.NCBI_NUCLEOTIDE));
		//databases.add(DatabaseFactory.build(Database.NCBI_OMIM));
		databases.add(DatabaseFactory.build(Database.NCBI_PROTEIN));
		databases.add(DatabaseFactory.build(Database.NCBI_PUBMED));
		//databases.add(DatabaseFactory.build(Database.NCBI_SNP));
		
		resource.setDatabases(databases);
		return resource;
	}
	
	private static Resource pdbjBuilder(String name) {
		Resource resource = new Resource(name);
		
		List<Database> databases = new ArrayList<Database>();
		databases.add(DatabaseFactory.build(Database.PDBJ_PDB));
		
		resource.setDatabases(databases);
		return resource;
	}
}

package br.ufpr.bioinfo.bak4bio.cluster.config;

import br.ufpr.bioinfo.bak4bio.cluster.control.B4BClusterManager;

public class Main {
	
	public static void main(String[] args) {
		Runtime runtime = Runtime.getRuntime();
		
		B4BClusterManager manager = new B4BClusterManager(runtime);
		manager.start();
	}
}

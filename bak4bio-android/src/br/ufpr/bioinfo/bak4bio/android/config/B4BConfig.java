package br.ufpr.bioinfo.bak4bio.android.config;

public class B4BConfig {
	public static final ProjectContext PROJECT_CONTEXT = ProjectContext.DEVELOPMENT;
	
	public static final String PROTOCOL = "http";
	
	private static final String DEVELOPMENT_HOST_SERVER = "192.168.1.6";
	private static final int DEVELOPMENT_PORT_SERVER = 3000;
	private static final String DEVELOPMENT_CONTEXT_SERVER = "/";
	
	private static final String PRODUCTION_HOST_SERVER = "54.232.82.7";
	private static final int PRODUCTION_PORT_SERVER = 80;
	private static final String PRODUCTION_CONTEXT_SERVER = "/";
	
	public static String getHostServer() {
		switch (PROJECT_CONTEXT) {
			case DEVELOPMENT:
				return DEVELOPMENT_HOST_SERVER;
			case TEST:
				//TODO
			case PRODUCTION:
				return PRODUCTION_HOST_SERVER;
			default:
				break;
		}
		return null;
	}
	
	public static int getPortServer() {
		switch (PROJECT_CONTEXT) {
			case DEVELOPMENT:
				return DEVELOPMENT_PORT_SERVER;
			case TEST:
				//TODO
			case PRODUCTION:
				return PRODUCTION_PORT_SERVER;
			default:
				break;
		}
		return 0;
	}
	
	public static String getContextServer() {
		switch (PROJECT_CONTEXT) {
		case DEVELOPMENT:
			return DEVELOPMENT_CONTEXT_SERVER;
		case TEST:
			//TODO
		case PRODUCTION:
			return PRODUCTION_CONTEXT_SERVER;
		default:
			break;
		}
		return null;
	}
	
	public static String getUrlServer() {
		switch (PROJECT_CONTEXT) {
			case DEVELOPMENT:
				return PROTOCOL + "://" + DEVELOPMENT_HOST_SERVER + ":" + DEVELOPMENT_PORT_SERVER + DEVELOPMENT_CONTEXT_SERVER;
			case TEST:
				//TODO
			case PRODUCTION:
				return PROTOCOL + "://" + PRODUCTION_HOST_SERVER + ":" + PRODUCTION_PORT_SERVER + PRODUCTION_CONTEXT_SERVER;
			default:
				break;
		}
		return null;
	}
	
}


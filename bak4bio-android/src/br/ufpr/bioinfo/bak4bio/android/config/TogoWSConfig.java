package br.ufpr.bioinfo.bak4bio.android.config;

public class TogoWSConfig {
	public static final ProjectContext PROJECT_CONTEXT = ProjectContext.DEVELOPMENT;
	
	public static final String PROTOCOL = "http";
	
	private static final String DEVELOPMENT_HOST_SERVER = "togows.dbcls.jp";
	private static final int DEVELOPMENT_PORT_SERVER = 80;
	private static final String DEVELOPMENT_CONTEXT_SERVER = "/";
	
	public static String getHostServer() {
		switch (PROJECT_CONTEXT) {
			case DEVELOPMENT:
				return DEVELOPMENT_HOST_SERVER;
			case TEST:
				//TODO
			case PRODUCTION:
				//TODO
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
				//TODO
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
			//TODO
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
				//TODO
			default:
				break;
		}
		return null;
	}
	
}

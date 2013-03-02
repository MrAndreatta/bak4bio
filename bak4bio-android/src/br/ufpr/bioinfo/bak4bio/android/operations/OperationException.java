package br.ufpr.bioinfo.bak4bio.android.operations;

public class OperationException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public OperationException(String message, Throwable e) {
		super(message, e);
	}

	public OperationException(String message) {
		super(message);
	}

}

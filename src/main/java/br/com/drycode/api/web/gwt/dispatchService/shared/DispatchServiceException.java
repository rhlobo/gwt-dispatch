package br.com.drycode.api.web.gwt.dispatchService.shared;

public class DispatchServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	// IMPORTANT A package-visible default constructor is necessary for serialization. Do not remove this.
	protected DispatchServiceException() {}

	public DispatchServiceException(final String message) {
		super(message);
	}
}
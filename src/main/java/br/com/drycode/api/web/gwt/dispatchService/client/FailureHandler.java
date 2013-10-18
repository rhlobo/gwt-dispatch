package br.com.drycode.api.web.gwt.dispatchService.client;

public interface FailureHandler<T extends Throwable> {

	void handle(T caught);
}

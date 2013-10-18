package br.com.drycode.api.web.gwt.dispatchService.client;

import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchRequest;
import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchResponse;

public interface DispatchService {

	<T extends FailureHandler<R>, R extends Throwable> void addFailureHandler(Class<R> throwableClass, T handler);

	// TODO Javadoc that requests must obey gwt serialization rulez (and have a default constructor).
	<T extends DispatchRequest<R>, R extends DispatchResponse> void dispatch(T request, DispatchCallback<R> dispatchCallback);
}

package br.com.drycode.api.web.gwt.dispatchService.client;

import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchResponse;

public interface DispatchCallback<T extends DispatchResponse> {

	void onSuccess(T result);

	void onTreatedFailure(Throwable caught);

	void onUntreatedFailure(Throwable caught);
}

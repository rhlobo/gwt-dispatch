package br.com.drycode.api.web.gwt.dispatchService.client;

import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchRequest;
import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchResponse;

import com.google.gwt.user.client.rpc.AsyncCallback;

// FIXME Package visibility? (verify all classes)
public interface DispatchRpcServiceAsync {

	<R extends DispatchResponse> void dispatch(DispatchRequest<R> request, AsyncCallback<R> callback);
}

package br.com.drycode.api.web.gwt.dispatchService.client;

import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchRequest;
import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchResponse;
import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchServiceException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("dispatchService")
public interface DispatchRpcService extends RemoteService {

	<R extends DispatchResponse> R dispatch(DispatchRequest<R> request) throws DispatchServiceException, Exception;
}

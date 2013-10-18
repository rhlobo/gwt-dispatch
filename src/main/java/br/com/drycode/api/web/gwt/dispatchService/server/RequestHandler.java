package br.com.drycode.api.web.gwt.dispatchService.server;

import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchRequest;
import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchResponse;

public interface RequestHandler<T extends DispatchRequest<R>, R extends DispatchResponse> {

	R handle(T request) throws Exception;
}

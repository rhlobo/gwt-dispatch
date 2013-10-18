package br.com.drycode.api.web.gwt.dispatchService.server;

import javax.servlet.http.HttpServletRequest;

import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchRequest;

public interface RequestFilter {

	// TODO Receive FilterChain just as the servlet filter definition so that a filter can perform both pre and post actions.
	void doFilter(DispatchRequest<?> request, HttpServletRequest httpServletRequest) throws Exception;

}

package br.com.drycode.api.web.gwt.dispatchService.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.com.drycode.api.web.gwt.dispatchService.client.DispatchRpcService;
import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchRequest;
import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchResponse;
import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchServiceException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DispatchServiceServlet extends RemoteServiceServlet implements DispatchRpcService {

	private static final long serialVersionUID = 1L;

	private static final Map<Class<?>, RequestHandler<?, ?>> requestHandlersMap = new HashMap<Class<?>, RequestHandler<?, ?>>();

	private static final Set<RequestFilter> requestFilterSet = new HashSet<RequestFilter>();

	public static <T extends DispatchRequest<R>, R extends DispatchResponse> void registerRequestHandler(final Class<T> requestClass,
			final RequestHandler<T, R> requestHandler) throws DispatchServiceException {

		if (requestHandlersMap.containsKey(requestClass)) throw new DispatchServiceException(
				"There is already another handler registered for this request type.");

		requestHandlersMap.put(requestClass, requestHandler);
	}

	public static <T extends DispatchRequest<R>, R extends DispatchResponse> void unregisterRequestHandler(final Class<T> requestClass) {
		requestHandlersMap.remove(requestClass);
	}

	public static void registerRequestFilter(final RequestFilter filter) {
		requestFilterSet.add(filter);
	}

	/**
	 * Unregisters the specified filter.
	 * 
	 * More formally, removes an filter <tt>e</tt> such that <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>, if this
	 * set contains such an filter. Returns <tt>true</tt> if the filter was
	 * registered (and thus now removed).
	 * 
	 * @param filter
	 *            object to be unregistered, if present
	 * @return <tt>true</tt> if this set contained the specified element
	 */
	public static boolean unregisterRequestFilter(final RequestFilter filter) {
		return requestFilterSet.remove(filter);
	}

	@Override
	public <R extends DispatchResponse> R dispatch(final DispatchRequest<R> request) throws Exception {
		if (!requestHandlersMap.containsKey(request.getClass())) throw new DispatchServiceException("There is no handler associated with the incoming request.");

		return processRequest(request);
	}

	@SuppressWarnings("unchecked")
	private <T extends DispatchRequest<R>, R extends DispatchResponse> R processRequest(final T request) throws Exception {
		for (final RequestFilter requestFilter : requestFilterSet)
			requestFilter.doFilter(request, this.getThreadLocalRequest());

		return ((RequestHandler<T, R>) requestHandlersMap.get(request.getClass())).handle(request);
	}
}

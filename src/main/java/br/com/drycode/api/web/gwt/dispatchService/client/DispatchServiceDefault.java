package br.com.drycode.api.web.gwt.dispatchService.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchRequest;
import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchResponse;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

// TODO Test exception handling
// TODO Test dispatch
public class DispatchServiceDefault implements DispatchService {

	private final Map<Class<? extends Throwable>, List<FailureHandler<? extends Throwable>>> failureHandlerListMap = new HashMap<Class<? extends Throwable>, List<FailureHandler<? extends Throwable>>>();

	private final DispatchRpcServiceAsync rpcService = GWT.create(DispatchRpcService.class);

	public DispatchServiceDefault() {}

	public DispatchServiceDefault(final RequestBuilderConfigurator requestBuilderConfigurator) {
		setRequestBuilderConfigurator(requestBuilderConfigurator);
	}

	private void setRequestBuilderConfigurator(final RequestBuilderConfigurator requestBuilderConfigurator) {
		final RpcRequestBuilder customRpcRequestBuilder = new RpcRequestBuilder() {

			@Override
			protected RequestBuilder doCreate(final String serviceEntryPoint) {
				final RequestBuilder requestBuilder = super.doCreate(serviceEntryPoint);
				requestBuilderConfigurator.configureRequestBuilder(requestBuilder);
				return requestBuilder;
			}
		};
		((ServiceDefTarget) rpcService).setRpcRequestBuilder(customRpcRequestBuilder);
	}

	@Override
	public <T extends DispatchRequest<R>, R extends DispatchResponse> void dispatch(final T request,
			final DispatchCallback<R> dispatchCallback) {
		rpcService.dispatch(request, new AsyncCallback<R>() {

			@Override
			public void onSuccess(final R result) {
				dispatchCallback.onSuccess(result);
			}

			@Override
			public void onFailure(final Throwable caught) {
				if (processFailure(caught))
					dispatchCallback.onTreatedFailure(caught);
					else
					dispatchCallback.onUntreatedFailure(caught);
				}
		});
	}

	@Override
	public <T extends FailureHandler<R>, R extends Throwable> void addFailureHandler(final Class<R> throwableClass, final T handler) {
		if (!failureHandlerListMap.containsKey(throwableClass)) failureHandlerListMap.put(throwableClass, new ArrayList<FailureHandler<?>>());

		final List<FailureHandler<?>> handlerList = failureHandlerListMap.get(throwableClass);
		handlerList.add(handler);
	}

	/**
	 * Processes the failure looking for registered {@link FailureHandler}s for
	 * the specific {@link Throwable} class, calling them if found.
	 * 
	 * @param caught
	 *            the cause of the failure.
	 * @return a boolean indicating if any failure handler was registered - and
	 *         therefore used - for the given {@link Throwable}.
	 */
	private boolean processFailure(final Throwable caught) {
		final List<FailureHandler<? extends Throwable>> handlerList = failureHandlerListMap.get(caught.getClass());
		if (handlerList == null) return false;

		for (final FailureHandler<?> handler : handlerList)
			notifyEventHandler(handler, caught);

		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void notifyEventHandler(final FailureHandler handler, final Throwable caught) {
		handler.handle(caught);
	}
}
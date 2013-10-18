package br.com.drycode.api.web.gwt.dispatchService.shared.responses;

import br.com.drycode.api.web.gwt.dispatchService.shared.DispatchResponse;

public class DispatchResponseObjectContainer<T> implements DispatchResponse {

	private T transportedObject;

	protected DispatchResponseObjectContainer() {
	}

	public DispatchResponseObjectContainer(final T obj) {
		transportedObject = obj;
	}

	public void setTransportedObject(final T obj) {
		this.transportedObject = obj;
	}

	public T getTransportedObject() {
		return transportedObject;
	}
}

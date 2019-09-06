package com.hdisolutions.restprocessor.factory.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode (callSuper = false)
public class ControllerMethodClientInvokerMapKey extends ControllerMethodClientMapKey {

	private String invoker;
	
	public ControllerMethodClientInvokerMapKey(String controller, String method, String client, String invoker) {
		
		super(controller, method, client);
		this.invoker = invoker;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		ControllerMethodClientInvokerMapKey keyParam = (ControllerMethodClientInvokerMapKey)obj;
		
		return this.getController().equalsIgnoreCase(keyParam.getController()) &&
			   this.getMethod().equalsIgnoreCase(keyParam.getMethod()) &&
			   this.getClient().equalsIgnoreCase(keyParam.getClient()) &&
			   this.invoker.equals(keyParam.getInvoker());
	}
	
	@Override
	public int hashCode() {
		return this.controller.hashCode() + this.method.hashCode() + this.client.hashCode() + this.invoker.hashCode();
	}	
}

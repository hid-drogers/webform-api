package com.hdisolutions.restprocessor.factory.model;

import lombok.Data;

@Data
public class ControllerMethodClientMapKey {

	protected String controller;
	protected String method;
	protected String client;
	
	public ControllerMethodClientMapKey(String controller, String method, String client) {
		this.controller = controller;
		this.method = method;
		this.client = client;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		ControllerMethodClientMapKey keyParam = (ControllerMethodClientMapKey)obj;
		
		return this.controller.equalsIgnoreCase(keyParam.getController()) &&
			   this.method.equalsIgnoreCase(keyParam.getMethod()) &&
			   this.client.equalsIgnoreCase(keyParam.getClient());
	}
	
	@Override
	public int hashCode() {
		return this.controller.hashCode() + this.method.hashCode() + this.client.hashCode();
	}
	
}
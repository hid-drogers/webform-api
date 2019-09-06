package com.hdisolutions.enums;

public enum RestServiceInternalInvoker {

	WEB_FORM("webForm", "Public facing web page. See Aaron Bowman for details."),
	
	// TODO: Refactor the EPA listener to use PAInitiationController & not PaLogic Web App.
	// Eventually do the same for EPA's PA Request listener and PA Cancel Request listener.
	EPA_PA_INIT_REQ_LISTENER("epaPAInitiationRequestListener", "SpringBoot app with a RabbitMQ msg listener"); 
	
	private String name;
	private String description;
	
	private RestServiceInternalInvoker(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public static RestServiceInternalInvoker forName(String name) {
		
		RestServiceInternalInvoker invokerForName = null;
		
		for(RestServiceInternalInvoker invoker: values()) {
			if(invoker.getName().equalsIgnoreCase(name)) {
				invokerForName = invoker;
				break;
			}
		}
		
		return invokerForName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
}

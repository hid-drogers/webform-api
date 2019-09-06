package com.hdisolutions.enums;

public enum HDIClient {

	HSPRING("hspring"),
	PHMI("phmi"),
	HID("hid"),
	BCBSAL("bcbsal"),
	RXBENEFITS("rxbenefits"),
	SSCRIPTS("sscripts"),
	RXSENSE("rxsense"),
	PADEMO("pademo");
	
	private String name;

	private HDIClient(String name) {
		this.name = name;
	};
	
	public String getName() {
		return name;
	}

	public static HDIClient forName(String name) {
		
		HDIClient clientForName = null;
		
		for(HDIClient client: values()) {
			if(client.getName().equalsIgnoreCase(name)) {
				clientForName = client;
				break;
			}
		}
		
		return clientForName;
	}	
}

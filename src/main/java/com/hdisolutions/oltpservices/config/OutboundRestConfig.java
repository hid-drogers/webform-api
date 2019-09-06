package com.hdisolutions.oltpservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * This config class is used to configure Rest components, like RestTemplate, for all
 * Restful calls made to external endpoints:
 * 
 * -PALogic Services
 *  
 * @author darrin.rogers
 *
 */
@Configuration
@ComponentScan({"com.hdisolutions"})
public class OutboundRestConfig {
	
  @Bean(name = "palogicServicesRestTemplate")
  public RestTemplate palogicServicesRestTemplate() {
	  return new RestTemplate();
  }
}

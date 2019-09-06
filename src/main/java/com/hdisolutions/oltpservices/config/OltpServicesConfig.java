package com.hdisolutions.oltpservices.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"com.hdisolutions"})
@PropertySource(value = { "classpath:messages.properties" })
public class OltpServicesConfig {

}

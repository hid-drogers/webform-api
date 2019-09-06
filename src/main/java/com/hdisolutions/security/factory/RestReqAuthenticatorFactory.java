package com.hdisolutions.security.factory;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.hdisolutions.security.authenticator.RestRequestAuthenticator;
import com.hdisolutions.web.util.HDIRestConstants;

@Component
public class RestReqAuthenticatorFactory implements ApplicationContextAware {

	private ApplicationContext applicationContext = null;
	
	private static final Log log = LogFactory.getLog(RestReqAuthenticatorFactory.class);
	
	@Resource(name = "clientSpecificAuthenticatorMap")
	private Map<String, Class<RestRequestAuthenticator>> clientSpecificAuthenticatorMap;
	
	public RestRequestAuthenticator makeRequestAuthenticator(HttpServletRequest request) {
		
		// Use default if no client, or client/invoker specific authenticator
		RestRequestAuthenticator authenticator = (RestRequestAuthenticator)applicationContext.getBean("baseRequestAuthenticator");
		
		String client = request.getHeader(HDIRestConstants.CLIENT);
		if(StringUtils.isNotEmpty(client) && clientSpecificAuthenticatorMap.containsKey(client)) {
			authenticator = applicationContext.getBean((Class<RestRequestAuthenticator>)clientSpecificAuthenticatorMap.get(client));
		}
		
		String msg = "Authenticator created for client header '" + client + "'";
		if(request.getHeader(HDIRestConstants.INVOKER) != null) {
			msg += " and invoker header '" + request.getHeader(HDIRestConstants.INVOKER) + "'";
		}
		msg += ". Implementation class: " + authenticator.getClass().getSimpleName();
		
		log.info(msg);
		
		return authenticator;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}		
}

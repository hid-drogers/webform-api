package com.hdisolutions.restprocessor.factory;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.hdisolutions.oltpservices.config.RestConfig;
import com.hdisolutions.restprocessor.RestProcessor;
import com.hdisolutions.restprocessor.factory.model.ControllerMethodClientMapKey;

@Component
public class RestProcessorFactory implements ApplicationContextAware {

	private ApplicationContext applicationContext = null;
	
	@Resource
	private Map<ControllerMethodClientMapKey, String> restProcessorMap;
	
	public RestProcessor makeProcessor(String controller, String method, String client) {
		
		ControllerMethodClientMapKey controllerMethodDefaultMapKey = new ControllerMethodClientMapKey(controller, method, RestConfig.DEFAULT);
		String beanAlias = StringUtils.uncapitalize(restProcessorMap.get(controllerMethodDefaultMapKey));
		
		ControllerMethodClientMapKey controllerMethodClientMapKey = new ControllerMethodClientMapKey(controller, method, client);
		if(restProcessorMap.containsKey(controllerMethodClientMapKey)) {
			beanAlias = StringUtils.uncapitalize(restProcessorMap.get(controllerMethodClientMapKey));
		}
		
		return (RestProcessor) applicationContext.getBean(beanAlias);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}

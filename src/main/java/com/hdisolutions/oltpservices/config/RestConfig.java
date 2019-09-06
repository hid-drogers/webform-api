package com.hdisolutions.oltpservices.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.hdisolutions.enums.HDIClient;
import com.hdisolutions.restprocessor.factory.model.ControllerMethodClientMapKey;

/**
 * This config class is used to configure implementations of Restful processing 
 * components via maps: authenticators and processors. The Restful web services use 
 * interfaces which have base/common behavior classes. Factories use these maps for
 * any client specific implementations to be returned (if they exist). Otherwise the
 * factories return the default implementations.
 *  
 * @author darrin.rogers
 *
 */
@Configuration
@ComponentScan({"com.hdisolutions"})
public class RestConfig {
	
  public static final String DEFAULT = "DEFAULT";
  
  // Controller names
  public static final String PHYSICIAN_CONTROLLER = "PhysicianController";
  public static final String PATIENT_CONTROLLER = "PatientController";
  public static final String DRUG_CONTROLLER = "DrugController";
	
  // Processor names
  public static final String GET_PHYSICIANS_PROCESSOR = "GetPhysiciansProcessor";
  
  public static final String GET_PATIENT_COUNT_PROCESSOR = "GetPatientCountProcessor";
  public static final String RXSENSE_GET_PATIENT_COUNT_PROCESSOR = "RxSenseGetPatientCountProcessor";
  public static final String RXBENEFITS_GET_PATIENT_COUNT_PROCESSOR = "RxBenefitsGetPatientCountProcessor";
  
  public static final String GET_DRUGS_PROCESSOR = "GetDrugsProcessor";
  
  @SuppressWarnings("rawtypes")
  @Bean(name = "clientSpecificAuthenticatorMap")
  public Map<String, Class> clientSpecificAuthenticatorMap() {
    
	  Map<String, Class> authenticatorMap = new HashMap<String, Class>();
	  
	  return authenticatorMap;
  }
  
  @Bean
  public Map<ControllerMethodClientMapKey, String> restProcessorMap(){
	  
	  Map<ControllerMethodClientMapKey, String> processorMap = new HashMap<ControllerMethodClientMapKey, String>();
	  
	  addPhysicianControllerProcessors(processorMap);
	  addPatientControllerProcessors(processorMap);
	  addDrugControllerProcessors(processorMap);
	  
	  return processorMap;
  }
  
  private void addPhysicianControllerProcessors(Map<ControllerMethodClientMapKey, String> processorMap) {
	  
	  ControllerMethodClientMapKey getPhysiciansMethodKey = new ControllerMethodClientMapKey(PHYSICIAN_CONTROLLER, "getPhysicians", DEFAULT);
	  processorMap.put(getPhysiciansMethodKey, GET_PHYSICIANS_PROCESSOR);
  }

  private void addPatientControllerProcessors(Map<ControllerMethodClientMapKey, String> processorMap) {
	  
	  ControllerMethodClientMapKey getPatientCountMethodKey = new ControllerMethodClientMapKey(PATIENT_CONTROLLER, "getPatientCount", DEFAULT);
	  processorMap.put(getPatientCountMethodKey, GET_PATIENT_COUNT_PROCESSOR);
	  
	  // Client specific implementations
	  ControllerMethodClientMapKey rxBenefitsGetPatientCountMethodKey =
			  new ControllerMethodClientMapKey(PATIENT_CONTROLLER, "getPatientCount", HDIClient.RXBENEFITS.getName());
	  processorMap.put(rxBenefitsGetPatientCountMethodKey, RXBENEFITS_GET_PATIENT_COUNT_PROCESSOR);
	  
	  ControllerMethodClientMapKey rxSenseGetPatientCountMethodKey =
			  new ControllerMethodClientMapKey(PATIENT_CONTROLLER, "getPatientCount", HDIClient.RXSENSE.getName());
	  processorMap.put(rxSenseGetPatientCountMethodKey, RXSENSE_GET_PATIENT_COUNT_PROCESSOR);			  
  }
  
  
  private void addDrugControllerProcessors(Map<ControllerMethodClientMapKey, String> processorMap) {
	
	  ControllerMethodClientMapKey getDrugsMethodKey = new ControllerMethodClientMapKey(DRUG_CONTROLLER, "getDrugs", DEFAULT);
	  processorMap.put(getDrugsMethodKey, GET_DRUGS_PROCESSOR);
  }

}

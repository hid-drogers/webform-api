package com.hdisolutions.oltpservices.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hdisolutions.model.dto.BaseInvokerResponse;
import com.hdisolutions.web.controller.HDIRestController;

/**
 * Processes all CRUD requests for Drug domain entity
 * 
 * Known invokers: WebForm
 * Know clients: bcbsal, sscripts, rxbenefits, rxsense
 * 
 * @author darrin.rogers
 *
 */

@RestController
@RequestMapping(value="/api/drugs")
public class DrugController extends HDIRestController {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<BaseInvokerResponse> getDrugs(HttpServletRequest request, HttpServletResponse response) {
		return process(request, response, "DrugController", "getDrugs");
	}
}

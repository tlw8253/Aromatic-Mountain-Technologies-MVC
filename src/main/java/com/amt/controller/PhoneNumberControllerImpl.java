package com.amt.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amt.app.Constants;
import com.amt.dto.AddPhoneNumberDTO;
import com.amt.dto.PhoneNumberDTO;
import com.amt.model.PhoneNumber;
import com.amt.service.PhoneNumberService;
import com.amt.service.PhoneNumberServiceImpl;

public class PhoneNumberControllerImpl implements PhoneNumberController, Constants {
	private Logger objLogger = LoggerFactory.getLogger(PhoneNumberControllerImpl.class);
	private PhoneNumberService objPhoneNumberService;

	Map<String, String> mPathParmaMap;
	Map<String, List<String>> mQueryParmaMap;
	int imPathParmaMapSize = 0;
	int imQueryParmaMap = 0;
	boolean bmQueryParmaMapIsEmpty = true;

	public PhoneNumberControllerImpl() {
		this.objPhoneNumberService = new PhoneNumberServiceImpl();
	}
	
	@Override
	public void setService(PhoneNumberService objPhoneNumberService) {
		this.objPhoneNumberService = objPhoneNumberService;
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	private void setContextMaps(Context objCtx) {
		String sMethod = csCRT + "setContextMaps(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		mPathParmaMap = objCtx.pathParamMap();
		imPathParmaMapSize = mPathParmaMap.size();
		mQueryParmaMap = objCtx.queryParamMap();
		imQueryParmaMap = mQueryParmaMap.size();
		bmQueryParmaMapIsEmpty = mQueryParmaMap.isEmpty();

		logContextParameters(objCtx);
	}

	//
	// ###
	private void logContextParameters(Context objCtx) {
		String sMethod = csCRT + "logContextParameters(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		mPathParmaMap = objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context path parameter map: [" + mPathParmaMap + "]");
		objLogger.debug(sMethod + "Context path parameter map size: [" + imPathParmaMapSize + "]");

		mQueryParmaMap = objCtx.queryParamMap();
		objLogger.debug(sMethod + "Context query parameter map: [" + mQueryParmaMap + "]");
		objLogger.debug(sMethod + "Context query parameter map size: [" + imQueryParmaMap + "] isEmpty: ["
				+ bmQueryParmaMapIsEmpty + "]");
	}


	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	private Handler postAddPhoneNumber = (objCtx) -> {
		String sMethod = csCRT + "postAddPhoneNumber(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		setContextMaps(objCtx);

		AddPhoneNumberDTO objAddPhoneNumberDTO = new AddPhoneNumberDTO();
		objCtx.status(ciStatusCodeErrorBadRequest);
		objCtx.json(csMsgBadParamCustomerBodyAsClass);

		objAddPhoneNumberDTO = objCtx.bodyAsClass(AddPhoneNumberDTO.class);
		objLogger.debug(sMethod + "objAddPhoneNumberDTO: [" + objAddPhoneNumberDTO.toString() + "]");

		PhoneNumber objPhoneNumber = new PhoneNumber();

		String sParamUsername = "";

		// expect 1 path parameters with user id
		if (imPathParmaMapSize != 1) {
			objLogger.debug(sMethod + csMsgBadParamPathParmNotRightNumber);
			objCtx.status(ciStatusCodeErrorBadRequest);
			objCtx.json(csMsgBadParamPathParmNotRightNumber);
			return;
		}

		sParamUsername = objCtx.pathParam(csParamUserName);
		objLogger.debug(sMethod + "Context path parameter username: [" + sParamUsername + "]");

		String sPhoneNumber = objAddPhoneNumberDTO.getPhoneNumber();
		String sPhoneNumberType = objAddPhoneNumberDTO.getPhoneNumberType();

		PhoneNumberDTO objPhoneNumberDTO = new PhoneNumberDTO(sPhoneNumber, sPhoneNumberType, sParamUsername);
		objLogger.debug(sMethod + "calling add service with objPhoneNumberDTO: [" + objPhoneNumberDTO.toString() + "]");
		objPhoneNumber = objPhoneNumberService.addNewPhoneNumber(objPhoneNumberDTO);
		objLogger.debug(sMethod + "objPhoneNumber: [" + objPhoneNumber.toString() + "]");

		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(objPhoneNumber);

	};


	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Override
	public void mapEndpoints(Javalin app) {

		app.post("/amt_phone/:username", postAddPhoneNumber);
	}

}

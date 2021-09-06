package com.amt.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amt.app.Constants;
import com.amt.dto.AddAddressDTO;
import com.amt.dto.AddressDTO;
import com.amt.model.Address;
import com.amt.service.AddressService;
import com.amt.service.AddressServiceImpl;

public class AddressControllerImpl implements AddressController, Constants {
	private Logger objLogger = LoggerFactory.getLogger(AddressControllerImpl.class);
	private AddressService objAddressService;

	Map<String, String> mPathParmaMap;
	Map<String, List<String>> mQueryParmaMap;
	int imPathParmaMapSize = 0;
	int imQueryParmaMap = 0;
	boolean bmQueryParmaMapIsEmpty = true;

	public AddressControllerImpl() {
		this.objAddressService = new AddressServiceImpl();
	}
	
	@Override
	public void setService(AddressService objAddressService) {
		this.objAddressService = objAddressService;
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
	private Handler postAddAddress = (objCtx) -> {
		String sMethod = csCRT + "postAddAddress(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		setContextMaps(objCtx);

		AddAddressDTO objAddAddressDTO = new AddAddressDTO();

		objCtx.status(ciStatusCodeErrorBadRequest);
		objCtx.json(csMsgBadParamCustomerBodyAsClass);

		objAddAddressDTO = objCtx.bodyAsClass(AddAddressDTO.class);
		objLogger.debug(sMethod + "objAddAddressDTO: [" + objAddAddressDTO.toString() + "]");

		Address objAddress = new Address();

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

		String sLine1 = objAddAddressDTO.getAddressLine1();
		String sLine2 = objAddAddressDTO.getAddressLine2();
		String sCity = objAddAddressDTO.getAddressCity();
		String sState = objAddAddressDTO.getAddressState();
		String sZip = objAddAddressDTO.getAddressZipCode();
		String sType = objAddAddressDTO.getAddressType();

		AddressDTO objAddressDTO = new AddressDTO(sLine1, sLine2, sCity, sState, sZip, sType, sParamUsername);
		objLogger.debug(sMethod + "calling add service with objAddressDTO: [" + objAddressDTO.toString() + "]");
		objAddress = objAddressService.addNewAddress(objAddressDTO);
		objLogger.debug(sMethod + "objAddress: [" + objAddress.toString() + "]");

		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(objAddress);
	};


	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Override
	public void mapEndpoints(Javalin app) {

		app.post("/amt_adx/:username", postAddAddress);

	}

}

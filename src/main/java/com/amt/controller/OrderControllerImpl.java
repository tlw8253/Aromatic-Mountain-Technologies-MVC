package com.amt.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amt.app.Constants;
import com.amt.dto.AddOrderDTO;
import com.amt.model.Order;
import com.amt.service.OrderService;
import com.amt.service.OrderServiceImpl;

public class OrderControllerImpl implements OrderController, Constants {
	private Logger objLogger = LoggerFactory.getLogger(OrderControllerImpl.class);
	OrderService objOrderService;

	Map<String, String> mPathParmaMap;
	Map<String, List<String>> mQueryParmaMap;
	int imPathParmaMapSize = 0;
	int imQueryParmaMap = 0;
	boolean bmQueryParmaMapIsEmpty = true;

	public OrderControllerImpl() {		
		this.objOrderService = new OrderServiceImpl();
	}

	public void setService(OrderService objOrderService) {
		this.objOrderService = objOrderService;
	}
	
	//
	// ###
	private void setContextMaps(Context objCtx) {
		String sMethod = "setContextMaps(): ";
		objLogger.trace(sMethod + "Entered");

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
		String sMethod = "logContextParameters(): ";
		objLogger.trace(sMethod + "Entered");

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
	private Handler postAddCustomerOrder = (objCtx) -> {
		String sMethod = csCRT + "postAddCustomerOrder(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		setContextMaps(objCtx);
		// expect 1 path parameters with user id
		if (imPathParmaMapSize != 1) {
			objLogger.debug(sMethod + csMsgBadParamPathParmNotRightNumber);
			objCtx.status(ciStatusCodeErrorBadRequest);
			objCtx.json(csMsgBadParamPathParmNotRightNumber);
			return;
		}

		String sParamUsername = objCtx.pathParam(csParamUserName);
		objLogger.debug(sMethod + "Context path parameter username: [" + sParamUsername + "]");

		AddOrderDTO objAddOrderDTO = new AddOrderDTO();

		objCtx.status(ciStatusCodeErrorBadRequest);
		objCtx.json(csMsgBadParamOrderBodyAsClass);

		objAddOrderDTO = objCtx.bodyAsClass(AddOrderDTO.class);
//		objLogger.debug(sMethod + "objAddOrderDTO: [" + objAddOrderDTO.toString() + "]");

		objLogger.debug(sMethod + "calling add service with objAddOrderDTO: [" + objAddOrderDTO.toString() + "]");
		
		Order objOrder = new Order();

		objOrder = objOrderService.addNewOrder(sParamUsername, objAddOrderDTO);
		objLogger.debug(sMethod + "objOrder: [" + objOrder.toString() + "]");

		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(objOrder);

	};



	

	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Override
	public void mapEndpoints(Javalin app) {

		//
		app.post("/amt_order/:username", postAddCustomerOrder);
	}

}

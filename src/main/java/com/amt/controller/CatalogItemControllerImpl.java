package com.amt.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amt.app.Constants;
import com.amt.dto.AddCatalogItemDTO;
import com.amt.dto.LoginDTO;
import com.amt.dto.MessageDTO;
import com.amt.exception.AuthenticationFailureException;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.CatalogItem;
import com.amt.model.User;
import com.amt.service.CatalogItemService;
import com.amt.service.CatalogItemServiceImpl;
import com.amt.service.LoginServiceImpl;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

//@Component specifies to Spring that it should treat this as a Spring bean class
//This means that Spring will know to automatically instantiate and configure this class (and store it inside of the Spring IoC container)
//This replaces the <bean> tag inside of the xml configuration
//@Component	//Removed in the java-config framework

//need to still implement Controller interface for AppJavalin
public class CatalogItemControllerImpl implements CatalogItemController, Constants {
	private Logger objLogger = LoggerFactory.getLogger(CatalogItemControllerImpl.class);
	private LoginServiceImpl objLoginService;
	private CatalogItemService objCatalogItemService;

	Map<String, String> mPathParmaMap;
	Map<String, List<String>> mQueryParmaMap;
	int imPathParmaMapSize = 0;
	int imQueryParmaMap = 0;
	boolean bmQueryParmaMapIsEmpty = true;

	public CatalogItemControllerImpl() {
		super();
		objLoginService = new LoginServiceImpl();
		objCatalogItemService = new CatalogItemServiceImpl();
	}

	// Autowired can be utilized for 3 different types of injection:
	// 1. Field Injection (where we place the autowired annotation directly on the variable itself (which will then use the 
	// Java Reflection API to set the value of that variable without using constructors or setters)
	// 2. Constructor Injection (where we place the autowired annotation directly on the constructor)
	// 3. Setter Injection (where we place the autowired annotation directly on the setter method)
	//@Autowired	//Removed in the java-config framework
	public void setService(CatalogItemService objCatalogItemService) {
		String sMethod = csCRT+ "setCatalogService(): ";
		objLogger.trace(csCR + sMethod + "Entered. Setting up injection.");
		this.objCatalogItemService = objCatalogItemService;

	}

	@Override
	public String CatalogServiceBeanTest() {
		String sMethod = csCRT+ "CatalogServiceBeanTest(): ";
		objLogger.trace(csCR + sMethod + "Entered. Testing a Spring Bean.");	
		
		return "CatalogServiceBeanTest: testing this Spring Bean.";
	}
	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	private void setContextMaps(Context objCtx) {
		String sMethod = csCRT+ "setContextMaps(): ";
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

	// ###
	private User getCurrentSessionUser(Context objCtx) {
		String sMethod = csCRT + "getCurrentSessionUser(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		HttpSession httpSession = objCtx.req.getSession();
		objLogger.debug(sMethod + "Getting session attribute for: [" + csSessionCurrentUser + "]");
		if (httpSession.getAttribute(csSessionCurrentUser) == null) {
			objLogger.debug(sMethod + "no active session recorded for any user");
			objCtx.json(new MessageDTO(csMsgSessionUserNotActive));
			objCtx.status(401);
			return null;
		} else {
			User objUser = (User) httpSession.getAttribute(csSessionCurrentUser);
			objLogger.debug(sMethod + "Active session recorded objUser: [" + objUser.toString() + "]");
			return objUser;
		}
	}

	private boolean validateUserSession(Context objCtx, LoginDTO objDTO)
			throws BadParameterException, AuthenticationFailureException, DatabaseException {
		String sMethod = csCRT + "validateUserSession(): ";
		objLogger.trace(csCR + sMethod + "Entered");
		boolean isValid = false;

		User objGetFromSession = getCurrentSessionUser(objCtx);
		if (objGetFromSession == null) {
			objLogger.debug(sMethod + "no active session recorded for any user");
		} else {
			objLogger.debug(sMethod + "objGetFromSession: [" + objGetFromSession.toString() + "]");
			if (objLoginService.validateSessionUser(objDTO, objGetFromSession)) {
				isValid = true;
			} else {
				objLogger.debug(sMethod + "current user is not the session user");
				objCtx.json(new MessageDTO(csMsgSessionUserNotActive));
				objCtx.status(401);
			}
		}

		return isValid;
	}

	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	private Handler postAddCatalogItem = (objCtx) -> {
		String sMethod = csCRT + "postAddCatalogItem(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		setContextMaps(objCtx);

		AddCatalogItemDTO objAddCatalogItemDTO = new AddCatalogItemDTO();
		LoginDTO objDTO = new LoginDTO();

		objCtx.status(ciStatusCodeErrorBadRequest);
		objCtx.json(csMsgBadParamCustomerBodyAsClass);

		objAddCatalogItemDTO = objCtx.bodyAsClass(AddCatalogItemDTO.class);
		objLogger.debug(sMethod + "objAddCatalogItemDTO: [" + objAddCatalogItemDTO.toString() + "]");

		objDTO.setUsername(objAddCatalogItemDTO.getLoginUsername());
		objDTO.setPassword(objAddCatalogItemDTO.getLoginPassword());

		boolean isValidUserSession = validateUserSession(objCtx, objDTO);

		if (isValidUserSession) {

			User objCurrSessionUser = getCurrentSessionUser(objCtx);
			objLogger.debug(sMethod + "validated user session with objCurrSessionUser: ["
					+ objCurrSessionUser.toString() + "]");

			String sEmployeeRole = objCurrSessionUser.getEmployeeRole().getEmployeeRole();
			if (sEmployeeRole.equalsIgnoreCase(csarEmployeeRoles[enumUserEmployee.CATALOG_ADMIN.pos])
					|| (sEmployeeRole.equalsIgnoreCase(csarEmployeeRoles[enumUserEmployee.CATALOG_EMPLOYEE.pos]))) {

				objLogger.debug(sMethod + "Employee is authorized to add catalog item: ["
						+ objCurrSessionUser.getUsername() + "]");
				CatalogItem objCatalogItem = new CatalogItem();

				objLogger.debug(sMethod + "calling add service with objAddCatalogItemDTO: ["
						+ objAddCatalogItemDTO.toString() + "]");
				objCatalogItem = objCatalogItemService.addNewCatalogItem(objAddCatalogItemDTO);

				objLogger.debug(sMethod + "objCatalogItem: [" + objCatalogItem.toString() + "]");

				objCtx.status(ciStatusCodeSuccess);
				objCtx.json(objCatalogItem);
			} else {
				objCtx.status(ciStatusCodeErrorBadRequest);
				objCtx.json(csMsgEmployeeNotAuthorizeCatalog);
			}
		}
	};

	@Override
	public void mapEndpoints(Javalin app) {
		//
		app.post("/amt_catalog_item", postAddCatalogItem);

	}

}

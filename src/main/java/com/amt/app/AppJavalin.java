package com.amt.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amt.controller.Controller;
import com.amt.controller.OrderControllerImpl;
import com.amt.controller.PhoneNumberControllerImpl;
import com.amt.controller.AddressControllerImpl;
import com.amt.controller.CatalogItemControllerImpl;
import com.amt.controller.LoginControllerImpl;
import com.amt.controller.UserControllerImpl;
import com.amt.controller.ExceptionController;

import io.javalin.Javalin;

/**
 * 
 * 
 * This is the main driver for this project when using Javalin technologies.
 * 
 * 
 * 
 * @author tlw8253
 *
 */
public class AppJavalin implements Constants {
	private final static Logger objLogger = LoggerFactory.getLogger(AppJavalin.class);
	private static Javalin objJavalinApp;

	public static void main(String[] args) {
		String sMethod = csCRT + "main(): ";
		objLogger.trace(sMethod + "Entered");

		// showEnvironmentVariables();

		// Chrome will not allow sending request without the following config
		objJavalinApp = Javalin.create((config) -> {
			config.enableCorsForAllOrigins();
			config.addStaticFiles(csClientStaticFolder);
		});

//		objJavalinApp = Javalin.create();		

		objLogger.debug(sMethod	+ "Mapping Controllers.");
		mapControllers(new ExceptionController(), new UserControllerImpl(), new OrderControllerImpl(),
				new CatalogItemControllerImpl(), new LoginControllerImpl(), new AddressControllerImpl(), new PhoneNumberControllerImpl());

		objLogger.info(sMethod + "Starting listening on port: [" + ciListingPort + "]");
		objJavalinApp.start(ciListingPort); // start up our Javalin server on port defined for this program

	}

	//
	// ###
	public static void mapControllers(Controller... controllers) {
		String sMethod = csCRT + "mapControllers(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		for (Controller c : controllers) {
			c.mapEndpoints(AppJavalin.objJavalinApp);
		}
	}

	//
	// ###
	private static void showEnvironmentVariables() {
		String sMethod = csCRT + "showEnvironmentVariables(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		String sDbEnv = "aws_db_url";
		String sUsernameEnv = "aws_db_username";
		String sPasswordEnv = "aws_db_password";

		objLogger.debug(sMethod + "AWS System Environment Variables.");
		String sDbUrl = System.getenv(sDbEnv);
		objLogger.debug(sMethod + "sDbEnv: [" + sDbEnv + "] value: [" + sDbUrl + "]");

		String sDbUsername = System.getenv(sUsernameEnv);
		objLogger.debug(sMethod + "sUsernameEnv: [" + sUsernameEnv + "] value: [" + sDbUsername + "]");

		String sDbPassword = System.getenv(sPasswordEnv);
		sDbPassword = "********";
		objLogger.debug(sMethod + "sPasswordEnv: [" + sPasswordEnv + "] value: [" + sDbPassword + "]");

		objLogger.debug(sMethod + "Localhost System Environment Variables.");
		sUsernameEnv = "localhost_db_username";
		sPasswordEnv = "localhost_db_password";
		sDbEnv = "localhost_db_url";

		sDbUrl = System.getenv(sDbEnv);
		objLogger.debug(sMethod + "sDbEnv: [" + sDbEnv + "] value: [" + sDbUrl + "]");
		sDbUsername = System.getenv(sUsernameEnv);
		objLogger.debug(sMethod + "sUsernameEnv: [" + sUsernameEnv + "] value: [" + sDbUsername + "]");
		sDbPassword = System.getenv(sPasswordEnv);
		sDbPassword = "********";
		objLogger.debug(sMethod + "sPasswordEnv: [" + sPasswordEnv + "] value: [" + sDbPassword + "]");

		objLogger.debug(sMethod + "Project Database name System Environment Variables.");
		String sProjDbNameEnv = "p2_db_name";
		String sProjectDbName = System.getenv(sProjDbNameEnv);

		objLogger.debug(sMethod + "sProjDbNameEnv: [" + sProjDbNameEnv + "] value: [" + sProjectDbName + "]");

	}

}// END Class

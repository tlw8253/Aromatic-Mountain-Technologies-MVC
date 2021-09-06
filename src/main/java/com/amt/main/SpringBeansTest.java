package com.amt.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.amt.controller.CatalogItemController;
import com.amt.controller.CatalogItemControllerImpl;


public class SpringBeansTest {

	public static void main(String[] args) throws JsonProcessingException {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		CatalogItemController objCatalogController = (CatalogItemController) context.getBean(CatalogItemController.class);
//		CatalogControllerImpl objCatalogController = (CatalogControllerImpl) context.getBean(CatalogControllerImpl.class);
		//ShipController shipController = (ShipController) context.getBean(ShipController.class);
		
		String json = objCatalogController.CatalogServiceBeanTest();
		//String json = shipController.getAllShips();
		
		System.out.println(json);
		
		
	}

}

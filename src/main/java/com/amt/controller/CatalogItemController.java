package com.amt.controller;

import com.amt.service.CatalogItemService;

import io.javalin.Javalin;

public interface CatalogItemController extends Controller {

	public abstract void mapEndpoints(Javalin app);
	public abstract String CatalogServiceBeanTest();
	public abstract void setService(CatalogItemService objCatalogItemService);
	
}

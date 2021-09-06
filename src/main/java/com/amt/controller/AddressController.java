package com.amt.controller;

import com.amt.service.AddressService;

import io.javalin.Javalin;

public interface AddressController extends Controller {

	public abstract void mapEndpoints(Javalin app);
	public abstract void setService(AddressService objAddressService);
	
}

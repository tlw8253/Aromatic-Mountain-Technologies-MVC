package com.amt.controller;

import com.amt.service.PhoneNumberService;

import io.javalin.Javalin;

public interface PhoneNumberController extends Controller {

	public abstract void mapEndpoints(Javalin app);
	public abstract void setService(PhoneNumberService objPhoneNumberService);
	
}

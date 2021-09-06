package com.amt.controller;

import com.amt.service.AddressService;
import com.amt.service.PhoneNumberService;
import com.amt.service.UserService;

import io.javalin.Javalin;

public interface UserController extends Controller {

	public abstract void mapEndpoints(Javalin app);
	public abstract void setService(UserService objUserService);
	
}

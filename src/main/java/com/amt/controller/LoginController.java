package com.amt.controller;

import com.amt.service.LoginService;

import io.javalin.Javalin;

public interface LoginController extends Controller {

	public abstract void mapEndpoints(Javalin app);
	public abstract void setService(LoginService objLoginService);
	
}

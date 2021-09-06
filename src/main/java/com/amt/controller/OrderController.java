package com.amt.controller;

import com.amt.service.OrderService;

import io.javalin.Javalin;

public interface OrderController extends Controller {

	public abstract void mapEndpoints(Javalin app);
	public abstract void setService(OrderService objOrderService);
	
}

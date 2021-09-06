package com.amt.service;

import com.amt.dto.AddOrderDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.Order;

public interface OrderService {

	public abstract Order addNewOrder(String sUsername, AddOrderDTO objAddOrderDTO) throws DatabaseException, BadParameterException;
}

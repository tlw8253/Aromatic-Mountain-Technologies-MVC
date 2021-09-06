package com.amt.service;


import com.amt.dto.AddUserDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.User;

public interface UserService {

	public abstract User getUserByUsername(String sUsername) throws DatabaseException, BadParameterException;
	public abstract User addNewUser(AddUserDTO objAddUserDTO) throws DatabaseException, BadParameterException;

}

package com.amt.service;

import com.amt.dto.LoginDTO;
import com.amt.exception.AuthenticationFailureException;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.User;

public interface LoginService {

	public abstract User login(LoginDTO objLoginDTO) throws BadParameterException, AuthenticationFailureException, DatabaseException;
	public abstract boolean validateSessionUser(LoginDTO objDTO, User objSessionUser)
			throws BadParameterException, AuthenticationFailureException, DatabaseException;

}

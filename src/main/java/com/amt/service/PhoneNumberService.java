package com.amt.service;

import com.amt.dto.PhoneNumberDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.PhoneNumber;

public interface PhoneNumberService {

	public abstract PhoneNumber addNewPhoneNumber(PhoneNumberDTO objPhoneNumberDTO) throws DatabaseException, BadParameterException;
}

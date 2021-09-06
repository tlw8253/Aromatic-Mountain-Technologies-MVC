package com.amt.service;

import com.amt.dto.AddressDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.Address;

public interface AddressService {

	public abstract Address addNewAddress(AddressDTO objAddressDTO) throws DatabaseException, BadParameterException;
}

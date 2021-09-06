package com.amt.service;


import com.amt.dto.AddCatalogItemDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.CatalogItem;

public interface CatalogItemService {

	public abstract CatalogItem addNewCatalogItem(AddCatalogItemDTO objAddCatalogItemDTO) throws DatabaseException, BadParameterException;

}

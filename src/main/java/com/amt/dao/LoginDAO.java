package com.amt.dao;

import java.sql.SQLException;

import com.amt.model.User;

//DAO - Data Access Object
public interface LoginDAO {

	public abstract User getLogin(String sUsername, String password) throws SQLException;
	
}

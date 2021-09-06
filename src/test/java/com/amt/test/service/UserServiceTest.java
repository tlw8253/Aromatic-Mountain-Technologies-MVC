package com.amt.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*; // You may need to type this import manually to make use of 
//the argument matchers for Mockito, such as eq() or any()

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.amt.app.Constants;
import com.amt.app.Constants.enumUserEmployee;
import com.amt.dao.GenericDAO;
import com.amt.dao.UserDAO;
import com.amt.dto.UserDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.Order;
import com.amt.model.User;
import com.amt.service.UserServiceImpl;

public class UserServiceTest implements Constants {
	private static Logger objLogger = LoggerFactory.getLogger(UserServiceTest.class);

	private UserServiceImpl objMockUserService;
	private UserDAO objMockUserDAO;

	public UserServiceTest() {
		super();
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		objLogger.trace("setUpBeforeClass()");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		objLogger.trace("tearDownAfterClass()");
	}

	@Before
	public void setUp() throws Exception {
		objLogger.trace("setUp()");		
		this.objMockUserDAO = mock(UserDAO.class);
		this.objMockUserService = new UserServiceImpl(objMockUserDAO);
		}

	@After
	public void tearDown() throws Exception {
		objLogger.trace("tearDown()");
	}

	@Test	//100.000
	public void test_addNewUserSuccess() throws SQLException, BadParameterException, DatabaseException {
		objLogger.trace("test_addNewUserSuccess()");


	}

//a whole slew of negative validation test cases can be created.
	@Test	//101.000
	public void test_addNewUserBadPwdNoCapLetter() throws SQLException, BadParameterException, DatabaseException {
		String sMethod = "test_addNewUserBadPwdNoCapLetter(): ";
		objLogger.trace(sMethod);


	}

	
	
	
}

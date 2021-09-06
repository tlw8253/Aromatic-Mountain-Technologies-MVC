package com.amt.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amt.dto.AddUserDTO;
import com.amt.dto.UserDTO;
import com.amt.exception.BadParameterException;
import com.amt.exception.DatabaseException;
import com.amt.model.User;
import com.amt.service.UserServiceImpl;




/**
 * This is a driver used during development to test functionality as it is
 * built. 
 * 
 * @author tlw8748253
 *
 */
public class UserDriver implements Constants {
	private final static Logger objLogger = LoggerFactory.getLogger(UserDriver.class);

	public static void main(String[] args) {
		String sMethod = csCRT + "main(): ";
		objLogger.trace(csCR + sMethod + "Entered");		

		//addProjectMemberUsers();
	}
	
	
	//
	//###
	public static void addProjectMemberUsers() {
		String sMethod = csCRT + "addProjectMemberUsers(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		addNewUsers("tw8253", "A_Pass12345", "Tom", "Weikel", "thomas.weikel@revature.net", csarUserType[enumUserType.EMPLOYEE.pos],csarEmployeeRoles[enumUserEmployee.CATALOG_ADMIN.pos]);
		addNewUsers("ap1234", "A_Pass12345", "Antonio", "Pierre", "antonio.pierre@revature.net", csarUserType[enumUserType.EMPLOYEE.pos],csarEmployeeRoles[enumUserEmployee.CATALOG_ADMIN.pos]);
		addNewUsers("mr1234", "A_Pass12345", "Matthew", "Rho", "matthew.rho@revature.net", csarUserType[enumUserType.EMPLOYEE.pos],csarEmployeeRoles[enumUserEmployee.CATALOG_EMPLOYEE.pos]);
	}
	
	//
	//###
	public static void addNewUsers(String username, String password, String firstName, String lastName, String email, String userType, String employeeRole) {
		String sMethod = csCRT + "addNewUsers(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		UserServiceImpl objUserService = new UserServiceImpl();
		AddUserDTO objAddUserDTO = new AddUserDTO(username, password, firstName, lastName, email, userType, employeeRole);
		objLogger.debug(sMethod + "adding user with objUserDTO: [" + objAddUserDTO.toString() + "]");

		try {
			User objUser = objUserService.addNewUser(objAddUserDTO);
			objLogger.debug(sMethod + "user object added objUser: [" + objUser.toString() + "]");
		} catch (DatabaseException e) {
			objLogger.debug(sMethod + "DatabaseException: [" + e.getMessage() + "]");
		} catch (BadParameterException e) {
			objLogger.debug(sMethod + "BadParameterException: [" + e.getMessage() + "]");
		}

		
		
	}


}

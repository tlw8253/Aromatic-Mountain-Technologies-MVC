package com.amt.dao;

import java.sql.SQLException;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amt.app.Constants;
import com.amt.model.User;
import com.amt.util.SessionFactorySingleton;
import com.amt.util.PasswordUtil;

public class LoginDAOImpl implements LoginDAO, Constants {
	private Logger objLogger = LoggerFactory.getLogger(LoginDAOImpl.class);

	public LoginDAOImpl() {
		super();
	}

	@Override
	public User getLogin(String sUsername, String sPassword) throws SQLException {
		String sMethod = csCRT + "getLogin(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();

		try {
			objLogger.debug(sMethod + "Authenticating username: [" + sUsername + "] with password provided.");
			String sHQL = "FROM User u WHERE u.username = :username AND u.password = :password";
			// can no longer do a direct compare in the database
			// need to retrieve the record with password and salt then validate through
			// password utility.
			sHQL = "FROM User u WHERE u.username = :username";

			objLogger.debug(sMethod + "sHQL: [" + sHQL + "] with parameter username: [" + sUsername + "]");

			User objUser = (User) session.createQuery(sHQL).setParameter("username", sUsername).getSingleResult();

			objLogger.debug(sMethod + "found user: [" + objUser.toString() + "] in database now validate password.");
			String sEncryptedPwd = objUser.getPassword();
			String sSalt = objUser.getPasswordSalt();
			boolean bPasswordValid = PasswordUtil.verifyUserPassword(sPassword, sEncryptedPwd, sSalt);

			if (bPasswordValid) {
				objLogger.debug(sMethod + "user: [" + objUser.toString() + "]" + csCRT + "DID pass encrypted password validation with salt key.");
				return objUser;
			} else {
				objLogger.warn(sMethod + "user: [" + objUser.toString() + "]" + csCRT + "DID NOT pass encrypted password validation with salt key.");
				return null;
			}

		} catch (NoResultException e) {
			objLogger.debug(sMethod + "NoResultException: [" + e.getMessage() + "]");
			return null;

		} finally {
			session.close();
		}
	}


}

package com.amt.dao;

import java.sql.SQLException;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amt.app.Constants;
import com.amt.dto.CatalogItemDTO;
import com.amt.model.CatalogItem;
import com.amt.model.CatalogItemType;
import com.amt.util.SessionFactorySingleton;

//@Component		//Removed in the java-config framework
public class CatalogItemDAOImpl implements CatalogItemDAO, Constants {
	private Logger objLogger = LoggerFactory.getLogger(CatalogItemDAOImpl.class);

	public CatalogItemDAOImpl() {
		super();
	}

/*
		private int catalogItemId;
	private String catalogItem;
	private String catalogItemDescription;
	private double catalogItemPrice;
	private int catalogItemInStockQty;
	private CatalogItemType catalogItemType;
*/
	
	
	@Override
	public CatalogItem getCatalogItemByName(String sName) throws SQLException{
		String sMethod = csCRT + "getCatalogItemByName(): ";
		objLogger.trace(csCR + sMethod + "Entered");
		
		
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		// load a complete persistent objects into memory		
		String sHQL = "";		
		sHQL = "FROM CatalogItem ci WHERE ci.catalogItemName = :catalogItemName";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sName: [" + sName +"]");
		
		try {
			CatalogItem objCatalogItem = (CatalogItem) session.createQuery(sHQL)
											.setParameter("catalogItemName", sName)
											.getSingleResult();
			objLogger.debug(sMethod + "objCatalogItem: [" + objCatalogItem.toString() + "]");
			
			tx.commit();
			return objCatalogItem;
			
		}catch(Exception e) {
			objLogger.error(sMethod + "Error getting catalog item by sName: [" + sName + "]");
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName() + "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: message: [" + e.getMessage() + "]");	
			return null;
		}finally {
			session.close();
		}
	}
	
	//
	// ###
	@Override // 20210819 1223 working method added user using Admin drive through service
				// layer
	public CatalogItem addNewCatalogItem(CatalogItemDTO objCatalogItemDTO) throws SQLException, HibernateException {
		String sMethod = csCRT + "addNewCatalogItem(): ";
		objLogger.trace(csCR + sMethod + "Entered");

		objLogger.debug(sMethod + "objAddCatalogItemDTO: [" + objCatalogItemDTO.toString() + "]");

		// by this time the service layer would have validated the parameters
		String sCatalogItemName = objCatalogItemDTO.getCatalogItemName();
		String sCatalogItem = objCatalogItemDTO.getCatalogItem();
		String sCatalogItemDescription = objCatalogItemDTO.getCatalogItemDescription();
		double dCatalogItemPrice = objCatalogItemDTO.getCatalogItemPrice();
		int iCatalogItemInStockQty = objCatalogItemDTO.getCatalogItemInStockQty();
		String sCatalogItemType = objCatalogItemDTO.getCatalogItemType();

		
		CatalogItem objCatalogItem = new CatalogItem(sCatalogItemName, sCatalogItem, sCatalogItemDescription, dCatalogItemPrice, iCatalogItemInStockQty);
		objLogger.debug(sMethod + "objCatalogItem: [" + objCatalogItem.toString() + "]");
		
		// get Address Type object
		CatalogItemTypeDAOImpl objCatalogItemTypeDAOImpl = new CatalogItemTypeDAOImpl();
		CatalogItemType objCatalogItemType = objCatalogItemTypeDAOImpl.getByRecordIdentifer(sCatalogItemType);
		objLogger.debug(sMethod + "objCatalogItemType: [" + objCatalogItemType.toString() + "]");
		
		objCatalogItem.setCatalogItemType(objCatalogItemType);		
		
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		try {
			
			objLogger.debug(sMethod + "Adding final objCatalogItem: [" + objCatalogItem.toString() + "] to the database.");

			session.persist(objCatalogItem);
			tx.commit();
			return objCatalogItem;

		} catch (Exception e) {
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName()
					+ "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
			return null;
		} finally {
			session.close();
		}

	}



}//END Class

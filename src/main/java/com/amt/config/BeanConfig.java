package com.amt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amt.controller.AddressController;
import com.amt.controller.AddressControllerImpl;
import com.amt.controller.CatalogItemController;
import com.amt.controller.CatalogItemControllerImpl;
import com.amt.controller.OrderController;
import com.amt.controller.OrderControllerImpl;
import com.amt.controller.PhoneNumberController;
import com.amt.controller.PhoneNumberControllerImpl;
import com.amt.controller.LoginController;
import com.amt.controller.LoginControllerImpl;
import com.amt.controller.UserController;
import com.amt.controller.UserControllerImpl;
import com.amt.dao.AddressDAO;
import com.amt.dao.AddressDAOImpl;
import com.amt.dao.CatalogItemDAO;
import com.amt.dao.CatalogItemDAOImpl;
import com.amt.dao.LoginDAO;
import com.amt.dao.LoginDAOImpl;
import com.amt.dao.OrderDAO;
import com.amt.dao.OrderDAOImpl;
import com.amt.dao.PhoneNumberDAO;
import com.amt.dao.PhoneNumberDAOImpl;
import com.amt.dao.UserDAO;
import com.amt.dao.UserDAOImpl;
import com.amt.model.User;
import com.amt.service.AddressService;
import com.amt.service.AddressServiceImpl;
import com.amt.service.CatalogItemService;
import com.amt.service.CatalogItemServiceImpl;
import com.amt.service.LoginService;
import com.amt.service.LoginServiceImpl;
import com.amt.service.OrderService;
import com.amt.service.OrderServiceImpl;
import com.amt.service.PhoneNumberService;
import com.amt.service.PhoneNumberServiceImpl;
import com.amt.service.UserService;
import com.amt.service.UserServiceImpl;

//This configuration class is a spring bean itself
//It is a special configuration spring bean
//We can create it by annotation this class with @Configuration
@Configuration
public class BeanConfig {

	// The following methods annotated with @Bean are the bean definitions themselves
	// The name of the methods are the names of the beans

	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Bean
	public AddressDAO addressDAO() {
		return new AddressDAOImpl();
	}

	@Bean
	public AddressService addressService() {
		return new AddressServiceImpl(addressDAO());	// constructor injection using DAO method defined above
	}

	
	@Bean
	public AddressController addressController() {
		
		AddressController ojbAddressController = new AddressControllerImpl();
		((AddressController) ojbAddressController).setService(addressService()); // setter injection
		
		return ojbAddressController;
	}

	
	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Bean
	public CatalogItemDAO catalogItemDAO() {
		return new CatalogItemDAOImpl();
	}
	
	@Bean	// @Bean carries a special meaning
	// It will "intercept" this method call to determine if that bean already exists in the container or not
	// If it already exists, it will simply return the existing bean instead of running the code that exists inside of this method
	// If it does not exist yet, that is when it will actually run the code inside the method, constructing the new object, then
	// storing it inside the container
	public CatalogItemService catalogItemService() {
		return new CatalogItemServiceImpl(catalogItemDAO());	// constructor injection using DAO method defined above
	}
	
	@Bean
	public CatalogItemController catalogController() {
		
		CatalogItemController objController = new CatalogItemControllerImpl();
		((CatalogItemControllerImpl) objController).setService(catalogItemService()); // setter injection
		
		return objController;
	}
	
	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Bean
	public LoginDAO loginDAO() {
		return new LoginDAOImpl();
	}

	@Bean
	public LoginService loginService() {
		return new LoginServiceImpl(loginDAO());	// constructor injection using DAO method defined above
	}

	
	@Bean
	public LoginController loginController() {
		
		LoginController objLoginController = new LoginControllerImpl();
		((LoginController) objLoginController).setService(loginService()); // setter injection
		
		return objLoginController;
	}


	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Bean
	public OrderDAO orderDAO() {
		return new OrderDAOImpl();
	}

	@Bean
	public OrderService orderService() {
		return new OrderServiceImpl(orderDAO());	// constructor injection using DAO method defined above
	}

	
	@Bean
	public OrderController orderController() {
		
		OrderController objOrderController = new OrderControllerImpl();
		((OrderController) objOrderController).setService(orderService()); // setter injection
		
		return objOrderController;
	}


	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Bean
	public PhoneNumberDAO phoneNumberDAO() {
		return new PhoneNumberDAOImpl();
	}

	@Bean
	public PhoneNumberService phoneNumberService() {
		return new PhoneNumberServiceImpl(phoneNumberDAO());	// constructor injection using DAO method defined above
	}

	
	@Bean
	public PhoneNumberController phoneNumberController() {
		
		PhoneNumberController ojbPhoneNumberController = new PhoneNumberControllerImpl();
		((PhoneNumberController) ojbPhoneNumberController).setService(phoneNumberService()); // setter injection
		
		return ojbPhoneNumberController;
	}

	
	
	// ###//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	@Bean
	public UserDAO userDAO() {
		return new UserDAOImpl();
	}
	
	@Bean
	public UserService userService() {
		return new UserServiceImpl(userDAO());	// constructor injection using DAO method defined above
	}

	@Bean
	public UserController userController() {
		
		UserController objUserController = new UserControllerImpl();
		((UserController) objUserController).setService(userService()); // setter injection
		
		return objUserController;
	}

	

	
	
	
	
	
	
	
}//END Class













package com.purang.transaction.project;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class AnnotationUserServiceImpl implements IUserService{

	private IUserDao userDao;
	private IAddressService addressService;
	
	
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public void setAddressService(IAddressService addressService) {
		this.addressService = addressService;
	}

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
	@Override
	public void save(UserModel user) {
		userDao.save(user);
		user.getAddress().setId(user.getId());
		try {
			addressService.save(user.getAddress());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
	@Override
	public int countAll() {
		return userDao.countAll();
	}

}

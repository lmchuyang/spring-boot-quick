package com.purang.transaction.project;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class AnnotationAddressServiceImpl implements IAddressService{

	private IAddressDao addressDao;
	
	public void setAddressDao(IAddressDao addressDao) {
		this.addressDao = addressDao;
	}

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
	@Override
	public void save(AddressModel address) {
		addressDao.save(address);
		throw new RuntimeException();//回流
	}

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=true)
	@Override
	public int countAll() {
		return addressDao.countAll();
	}

}

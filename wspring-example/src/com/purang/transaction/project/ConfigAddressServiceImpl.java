package com.purang.transaction.project;

public class ConfigAddressServiceImpl implements IAddressService  {
	
	 private IAddressDao addressDao; 
	 
	public void setAddressDao(IAddressDao addressDao) {
		this.addressDao = addressDao;
	}

	@Override
	public void save(AddressModel address) {
		addressDao.save(address);
		throw new RuntimeException();
	}

	@Override
	public int countAll() {
		return addressDao.countAll();
	}

}

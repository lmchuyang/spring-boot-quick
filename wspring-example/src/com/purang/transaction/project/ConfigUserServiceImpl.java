package com.purang.transaction.project;

public class ConfigUserServiceImpl implements IUserService  {

	private IUserDao  userDao;
	private IAddressService addressService;
	
	public void setUserDao(IUserDao userdaoDao) {
		this.userDao = userdaoDao;
	}

	public void setAddressService(IAddressService addressService) {
		this.addressService = addressService;
	}

	@Override
	public void save(UserModel user) {
		userDao.save(user);
		user.getAddress().setUser_Id(user.getId());
		try {
			addressService.save(user.getAddress());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int countAll() {
		return userDao.countAll();
	}

}

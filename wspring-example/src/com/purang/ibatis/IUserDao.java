package com.purang.ibatis;


public interface IUserDao {

	public void save(UserModel userModel);
	public int countAll();
	
}

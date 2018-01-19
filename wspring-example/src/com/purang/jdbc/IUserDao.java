package com.purang.jdbc;

import java.util.Map;

public interface IUserDao {

	public void save(UserModel userModel);
	public int countAll();
	void save(Map map);
	
}

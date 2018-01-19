package com.purang.ibatis;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UserIbatisDaoImpl extends SqlMapClientDaoSupport implements IUserDao{

	@Override
	public void save(UserModel userModel){
		getSqlMapClientTemplate().insert("UserSQL.insert",userModel);
	}

	@Override
	public int countAll() {
		
		return (Integer) getSqlMapClientTemplate().queryForObject("User.countAll");
	}
	public void  name1() {
		getSqlMapClientTemplate().update("UserSQL.update");
	}
}

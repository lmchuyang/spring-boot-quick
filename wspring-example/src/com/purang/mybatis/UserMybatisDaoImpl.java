package com.purang.mybatis;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.purang.ibatis.IUserDao;
import com.purang.ibatis.UserModel;




public class UserMybatisDaoImpl extends SqlSessionDaoSupport implements IUserDao{

	@Override
	public void save(UserModel userModel) {
			getSqlSession().insert("UserSQL.insert", userModel);  		
	}

	@Override
	public int countAll() {
		  return (Integer) getSqlSession().selectOne("UserSQL.countAll");  
		  }

}

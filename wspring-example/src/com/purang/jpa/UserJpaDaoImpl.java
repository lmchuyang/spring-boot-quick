package com.purang.jpa;

import org.springframework.orm.jpa.support.JpaDaoSupport;


public class UserJpaDaoImpl extends JpaDaoSupport implements IUserDao {

	private static  String  count_all = "select count(*) from UserModel ";
	@Override
	public void save(UserModel2 userModel2) {
		getJpaTemplate().persist(userModel2);
	}

	@Override
	public int countAll() {
		Number number = (Number) getJpaTemplate().find(count_all);
		return number.intValue();
	}


}

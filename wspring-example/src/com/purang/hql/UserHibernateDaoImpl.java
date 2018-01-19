package com.purang.hql;

import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.purang.jdbc.IUserDao;
import com.purang.jdbc.UserModel;



public class UserHibernateDaoImpl extends HibernateDaoSupport implements IUserDao {

    private static final String COUNT_ALL_HQL = "select count(*) from UserModel";
    
    @Override
    public void save(UserModel model) {
        getHibernateTemplate().save(model);
    }

    @Override
    public int countAll() {
        Number count = (Number) getHibernateTemplate().find(COUNT_ALL_HQL).get(0);
        return count.intValue();
    }

	@Override
	public void save(Map map) {
		// TODO Auto-generated method stub
		
	}
    
}
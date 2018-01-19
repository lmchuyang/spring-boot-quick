package com.purang.jdbc;

import java.util.Map;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;



public class UserJdbcDaoImpl extends JdbcDaoSupport implements IUserDao {  
	  private static final String INSERT_SQL = " INSERT INTO test(id,NAME) VALUES(:id,namete) ";  
	  private static final String COUNT_ALL_SQL = "select count(*) from test";  
	   
	  
	  @SuppressWarnings("deprecation")
	@Override  
	  public void save(UserModel model) {  
		  getJdbcTemplate().update(INSERT_SQL, new BeanPropertySqlParameterSource(model));  
	  }  
	  @Override  
	  public int countAll() {  
	      return getJdbcTemplate().queryForInt(COUNT_ALL_SQL);  
	  }
	@Override
	public void save(Map map) {
		 getJdbcTemplate().update(INSERT_SQL,map);  
	}  
	}  

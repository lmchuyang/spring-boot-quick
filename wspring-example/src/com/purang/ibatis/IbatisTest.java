package com.purang.ibatis;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;

public class IbatisTest {

	private static SqlMapClient sqlMapClient;  
	
	public static void main(String[] args) {
		String[] str = new String[]{"resourceJdbc.xml","applicationContext-ibatis.xml"};
		ApplicationContext act = new ClassPathXmlApplicationContext(str);
		sqlMapClient = act.getBean(SqlMapClient.class);
		

		SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate(sqlMapClient);
		final UserModel model = new UserModel();
		model.setName("myname");
		sqlMapClientTemplate.insert("UserSQL.insert", model);  
		 //通过回调允许更复杂操作  
		sqlMapClientTemplate.execute(new SqlMapClientCallback<Void>() {
			@Override
			public Void doInSqlMapClient(SqlMapExecutor  session) throws SQLException {
				 session.insert("UserSQL.insert", model);  
				return null;
			}
		});
	
	}

}

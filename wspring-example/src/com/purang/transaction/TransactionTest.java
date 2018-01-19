package com.purang.transaction;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
	






import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;



public class TransactionTest {

    private static ApplicationContext ctx;  
    private static PlatformTransactionManager txManager;  
    private static DataSource dataSource;  
    private static JdbcTemplate jdbcTemplate;  
    
    //id自增主键从0开始  
    private static final String CREATE_TABLE_SQL = "  CREATE TABLE test3( " +  
    " id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,  " +  
    " name varchar(100))";  
    private static final String DROP_TABLE_SQL = "drop table test3";  
    private static final String INSERT_SQL = " insert into test3(name) values(?)";  
    private static final String COUNT_SQL = " select count(*) from test3";
    
    @BeforeClass  
    public static void setUpClass() {
       String[] config = new String[] {"resourceJdbc.xml","com/purang/transaction/applicationContext-jdbc.xml"};
      ctx = new ClassPathXmlApplicationContext(config);
      txManager = ctx.getBean(PlatformTransactionManager.class);
    
      dataSource = ctx.getBean(DataSource.class);
      jdbcTemplate = new JdbcTemplate(dataSource);
     
    }
	@Test
	public void testPlatformTransactionManager() {
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);//设定隔离级别 （提交读）
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); //传播行为 (必须有事务支持)
		TransactionStatus status = txManager.getTransaction(def);
		//jdbcTemplate.execute(CREATE_TABLE_SQL);
		try {
			jdbcTemplate.update(INSERT_SQL,"test888");
			txManager.commit(status);
			System.out.println("================"+status);
		} catch (RuntimeException  e) {
			 txManager.rollback(status);  
		}
		//jdbcTemplate.execute(DROP_TABLE_SQL);
	}
	
	@Test
	public void testPlatformTransactionManagerForLowLevel1() {
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();    
		def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);   
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);  
		 TransactionStatus status = txManager.getTransaction(def);  
		 Connection conn = (Connection) DataSourceUtils.getConnection(dataSource);
		try {
			//conn.prepareStatement(CREATE_TABLE_SQL).execute();
			PreparedStatement pare = (PreparedStatement) conn.prepareStatement(INSERT_SQL);
			pare.setString(1, "test741");
			pare.execute();
			//conn.prepareStatement(DROP_TABLE_SQL).execute();
			txManager.commit(status);
			System.out.println("================"+status);
		} catch (Exception  e) {
			 status.setRollbackOnly();
			 txManager.rollback(status);  
		}finally {  
		      DataSourceUtils.releaseConnection(conn, dataSource);  
		}
	}
	@Test
	public void testPlatformTransactionManagerForLowLevel2() {
		 String[] config = new String[] {"resourceJdbc.xml","com/purang/transaction/applicationContext-jdbc2.xml"};
		 ApplicationContext  ctx2 = new ClassPathXmlApplicationContext(config);
		  DataSource dataSourceProxy = ctx2.getBean("dataSourceProxy",DataSource.class);
		 Connection conn = null ;
		try {
			conn = dataSourceProxy.getConnection();
			conn.setAutoCommit(false);
			//conn.prepareStatement(CREATE_TABLE_SQL).execute();
			PreparedStatement pare = (PreparedStatement) conn.prepareStatement(INSERT_SQL);
			pare.setString(1, "testwLevel2");
			pare.execute();
			ResultSet rsResult  = conn.prepareStatement(" SELECT * FROM test3 ").executeQuery();
			int count=0;
			while (rsResult.next()) {
				count++;
				System.out.println("7898"+rsResult.getString("name"));
			}
			System.out.println("== "+count);
			//conn.prepareStatement(DROP_TABLE_SQL).execute();
			conn.commit();
			System.out.println("========89998898");
		} catch (Exception  e) {
			 try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}  
		}finally {  
			  try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
	}
	@Test
	public void testTransactionTemplate() {
		//jdbcTemplate.execute(CREATE_TABLE_SQL);
		TransactionTemplate transp = new TransactionTemplate(txManager);
		transp.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
		transp.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				jdbcTemplate.update(INSERT_SQL,"template");
			}
			
		});
		//jdbcTemplate.execute(DROP_TABLE_SQL);
	}
}

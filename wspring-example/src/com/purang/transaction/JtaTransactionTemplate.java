package com.purang.transaction;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class JtaTransactionTemplate {
	private static ApplicationContext ctx;  
    private static PlatformTransactionManager jtaTXManager;  
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
/*       String[] config = new String[] {"resourceJdbc.xml","com/purang/transaction/applicationContext-jdbc.xml"};
      ctx = new ClassPathXmlApplicationContext(config);
      jtaTXManager = ctx.getBean(PlatformTransactionManager.class);
      dataSource = ctx.getBean(DataSource.class);
      jdbcTemplate = new JdbcTemplate(dataSource);*/
     
    }
	@Test
	public void testJtaTransactionTemplate() {
		   String[] configLocations = new String[] {
           "com/purang/transaction/applicationContext-jta-mysql.xml"};
        ctx = new ClassPathXmlApplicationContext(configLocations);
        jtaTXManager = ctx.getBean(PlatformTransactionManager.class);
		final DataSource  mysqldataSource1 = ctx.getBean("dataSource1",DataSource.class);
		final DataSource  mysqldataSource2 = ctx.getBean("dataSource2",DataSource.class);
		final JdbcTemplate template1 = new JdbcTemplate(mysqldataSource1);
		final JdbcTemplate template2 = new JdbcTemplate(mysqldataSource2);
		
		TransactionTemplate transactionTemplate = new TransactionTemplate(jtaTXManager);
	    transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
	    //transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		int count = template1.queryForInt(COUNT_SQL);
		System.out.println(" ====: "+count);
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					template1.update(INSERT_SQL,"jtatest1");
					 //因为数据库2没有创建数据库表因此会回滚事务  
					template2.update(INSERT_SQL,"jtatest2");
				}
			});
		} catch (TransactionException e) {
			int num = template1.queryForInt(COUNT_SQL);
			System.out.println("catch ====: "+num);
			//e.printStackTrace();
		}
		//template1.update(DROP_TABLE_SQL);
	}
}

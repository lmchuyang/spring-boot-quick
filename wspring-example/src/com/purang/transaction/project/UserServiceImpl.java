package com.purang.transaction.project;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class UserServiceImpl implements IUserService{
	 	private IUserDao userDao;  
	    private IAddressService addressService;  
	    private PlatformTransactionManager txManager;  
	    
	    public void setUserDao(IUserDao userDao) {  
	        this.userDao = userDao;  
	    }  
	    public void setTxManager(PlatformTransactionManager txManager) {  
	        this.txManager = txManager;  
	    }  
	    public void setAddressService(IAddressService addressService) {  
	        this.addressService = addressService;  
	    } 
	@Override
	public void save(final UserModel user) {
		TransactionTemplate tmTemplate =  TransactionTemplateUtils.getDefaultTransactionTemplate(txManager);
		
		try {
			tmTemplate.execute(new TransactionCallbackWithoutResult(){
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					userDao.save(user);
					user.getAddress().setUser_Id(user.getId());
					
					try {
						addressService.save(user.getAddress());
					} catch (RuntimeException e) {
					}
					
				}
			});
		} catch (TransactionException e) {
			System.out.println("================================================");
			e.printStackTrace();
		}
		
	}

	@Override
	public int countAll() {
		return userDao.countAll();
	}

}

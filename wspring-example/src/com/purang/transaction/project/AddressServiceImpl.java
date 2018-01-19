package com.purang.transaction.project;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class AddressServiceImpl implements IAddressService{

	private IAddressDao addressDao;
	private PlatformTransactionManager txManager;
	
    public void setAddressDao(IAddressDao addressDao) {  
        this.addressDao = addressDao;  
    }  
    public void setTxManager(PlatformTransactionManager txManager) {  
        this.txManager = txManager;  
    } 
	@Override
	public void save(final AddressModel address) {
		TransactionTemplate template =  TransactionTemplateUtils.getDefaultTransactionTemplate(txManager);  
		template.execute(new TransactionCallbackWithoutResult(){

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				addressDao.save(address);
				throw new RuntimeException();
			}});
	}

	@Override
	public int countAll() {
		return addressDao.countAll();
	}

}

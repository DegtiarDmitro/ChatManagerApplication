package com.manager.chat.dao.impl;

import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.manager.chat.dao.ManagerDao;
import com.manager.chat.entity.Manager;

public class ManagerDaoImpl extends BaseDaoImpl<Manager> implements ManagerDao{
	
	
	
	public ManagerDaoImpl() {
        super();
        GET_ALL_HQL = "From User";
        DELETE_BY_ID_HQL = "DELETE FROM User WHERE ID = :id";
        GET_ONE_BY_ID_HQL =  "FROM User WHERE ID = :id";
    }
	
	/**

     * @param name -
     */
	@SuppressWarnings("unchecked")
	@Override
    public Manager getOneByName(String name){

        if(!session.isOpen()){
            return null;
        }
        Transaction transaction = null;
        Manager obj = null;
        try{
            transaction = session.beginTransaction();
            
			Query<Manager> query = session.createQuery("FROM Manager WHERE username = :name");
            query.setParameter("name", name);
            obj = (Manager)query.uniqueResult();
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            LOG.info(e.getMessage());
        }
        return obj;
    }


}

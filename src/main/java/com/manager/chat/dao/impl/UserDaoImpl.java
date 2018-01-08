package com.manager.chat.dao.impl;

import com.manager.chat.dao.UserDao;
import com.manager.chat.entity.User;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    public UserDaoImpl() {
        super();
        GET_ALL_HQL = "From User";
        DELETE_BY_ID_HQL = "DELETE FROM User WHERE ID = :id";
        GET_ONE_BY_ID_HQL =  "FROM User WHERE ID = :id";
    }

    @Override
    public User getOneByName(String name) {
        if(!session.isOpen()){
            return null;
        }
        Transaction transaction = null;
        User obj = null;
        try{
            transaction = session.beginTransaction();

            Query<User> query = session.createQuery("FROM User WHERE username = :name");
            query.setParameter("name", name);
            obj = query.uniqueResult();
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

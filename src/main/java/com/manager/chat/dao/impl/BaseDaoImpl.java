package com.manager.chat.dao.impl;

import com.manager.chat.dao.BaseDao;
import com.manager.chat.entity.BaseEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import java.util.List;

public class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T>{


    protected static SessionFactory sessionFactory = null;
    protected static Session session = null;
    protected static Logger LOG = null;

    protected String GET_ALL_HQL = "";//"From T";
    protected String DELETE_BY_ID_HQL = "";//"DELETE FROM T WHERE ID = :id";
    protected String GET_ONE_BY_ID_HQL = "";//"FROM T WHERE ID = :id";
    //private String GET_BY_NAME_HQL = "";


    static {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
        LOG = Logger.getLogger(BaseDao.class.getName());
    }


    @SuppressWarnings("unchecked")
	@Override
    public T add(T entity) {

        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return get(entity.getId());
    }

    @Override
    public void update(T entity) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
	@Override
    public T get(int id) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        Transaction transaction = null;
        T obj = null;
        try{
            transaction = session.beginTransaction();
            Query<T> query = session.createQuery(GET_ONE_BY_ID_HQL);
            query.setParameter("id", id);
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

    @Override
    public void delete(int id) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Query query = session.createQuery(DELETE_BY_ID_HQL);
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            LOG.info(e.getMessage());
        }
    }

    @Override
    public void delete(T entity) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            LOG.info(e.getMessage());
        }
    }

    @Override
    public List<T> getAll() {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        Transaction transaction = null;
        List<T> list = null;
        try{
            transaction = session.beginTransaction();
            Query query = session.createQuery(GET_ALL_HQL);
            list = query.list();
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            LOG.info(e.getMessage());
        }
        return list;
    }
    
    
    
    
    
}

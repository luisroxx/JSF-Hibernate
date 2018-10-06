/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zerto.website.dao;

import br.com.zerto.website.utils.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Zerato
 */
public class GenericDao{
    
    private Session session;

    protected void saveOrUpdate(Object obj) {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.merge(obj);
            session.getTransaction().commit();
            session.close();
    }

    protected void delete(Object obj) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(obj);
        session.getTransaction().commit();
        session.close();
    }

    protected Object find(Class clazz, Long Id) {
       session = HibernateUtil.getSessionFactory().openSession();
       session.beginTransaction();
       Class obj = (Class) session.get(clazz, Id);
       session.close();
       return obj;
    }

    protected List findAll(Class clazz) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Class> listObj = new ArrayList<>();
        listObj = session.createCriteria(clazz).list();
        session.close();
        return listObj;
    }
}

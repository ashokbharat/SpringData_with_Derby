package com.ashok.bharat.DAOImpl;


import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ashok.bharat.Model.Circle;

//You can use HibernateDAOSupport to create a sessionfactory and oepning a session by extending HibernateDAOSupport class
//If we use hibernateDAOSUpport you can get rid of declaring sessionfactory instance 
//mark this as spring bean for database repository
//@Repository
@Component
public class HibernateDaoImpl {
  @Autowired
  private SessionFactory sessFact;

  public SessionFactory getSessFact() {
    return sessFact;
  }

  public void setSessFact(SessionFactory sessFact) {
    this.sessFact = sessFact;
  }
  public long getCircleCount(){
    String hql = "select count(*) from Circle";
    Query query = this.getSessFact().openSession().createQuery(hql);
    return (Long)query.uniqueResult();
  }
  
  public void insertCircle(Circle cr){
    String hql = "insert into Circle(id,name)"+" select id,name from Circle where id=:Id and name=:Name";
    Query query = this.getSessFact().openSession().createQuery(hql);
    query.setParameter("Id", cr.getId());
    query.setParameter("Name", cr.getName());
    /*query.setInteger(0, cr.getId());
    query.setString(1, cr.getName());*/
    int i = query.executeUpdate();
  }
}

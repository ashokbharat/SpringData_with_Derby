package com.ashok.bharat.dbClient;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ashok.bharat.DAO.CircleDAO;
import com.ashok.bharat.DAOImpl.CircleDaoImpl;
import com.ashok.bharat.DAOImpl.HibernateDaoImpl;
import com.ashok.bharat.DAOImpl.SimpleJdbcDaoImpl;
import com.ashok.bharat.Model.Circle;

public class DbClient {

  /**
   * @param args
   */
  public static void main(String[] args) {
  //writing code with spring support
   // ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
    //CircleDAO dao = (CircleDAO) ctx.getBean("circleDaoImpl");//Note: here make camel case and declare the class name to be camel case starting letter to be caps
   // CircleDaoImpl cdao = ctx.getBean("circleDaoImpl",CircleDaoImpl.class);
    //CircleDAO cdao = new CircleDAOImpl();
    //Circle circle = dao.getCircle(1);
    //System.out.println("Circle name is :" + circle.getName());
    //System.out.println("Circle records count is :" + cdao.getCircleRecordsCount());
    //System.out.println("Circle name for circle id is :" + cdao.getCircleNameForId(2));
    //System.out.println("Circle for circle id is :" + cdao.getCircleForId(1).getName());
   // cdao.insertCircle(new Circle(4,"rohita_cricle"));
    //cdao.deleteCircle(3);
    //cdao.deleteCircle(3);
    //cdao.createTriangle();
    //System.out.println("Circles count is :" + cdao.getAllCircles().size());
    
    //Making use of DAO Support
    /*SimpleJdbcDaoImpl simpleDaoImpl = ctx.getBean("jdbcDaoImpl",SimpleJdbcDaoImpl.class);
    System.out.println(simpleDaoImpl.getCircleCount());*/
    
    //Using HibernateDAO
    ApplicationContext ctx  = new ClassPathXmlApplicationContext("spring.xml");
    HibernateDaoImpl hbntdao = ctx.getBean("hibernateDaoImpl",HibernateDaoImpl.class);
    hbntdao.insertCircle(new Circle(5,"Hello_Circle"));
    System.out.println("HibernateDAO Impl returned circle count is :" + hbntdao.getCircleCount());

  }
}

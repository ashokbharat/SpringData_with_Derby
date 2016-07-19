package com.ashok.bharat.DAOImpl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class SimpleJdbcDaoImpl extends JdbcDaoSupport {
  //Making use of DAO Support classes provided by Spring to make use of JDbcDAOSupport(based on JdbcTemplate) which
  //takes care of the initialization of datasource and jdbctemplate
  
  public int getCircleCount(){
    String sql="select count(*) from circle";
    return this.getJdbcTemplate().queryForObject(sql, Integer.class);
  }
}

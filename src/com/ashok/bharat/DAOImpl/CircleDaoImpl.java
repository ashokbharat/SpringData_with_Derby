package com.ashok.bharat.DAOImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.ashok.bharat.DAO.CircleDAO;
import com.ashok.bharat.Model.Circle;
//@Component
public class CircleDaoImpl implements CircleDAO{
  //@Autowired
  private DataSource dataSource;
  //@Autowired
  private JdbcTemplate jdbcTemplate;
  private NamedParameterJdbcTemplate namedJdbcTemplate;
  
  
  public NamedParameterJdbcTemplate getNamedJdbcTemplate() {
    return namedJdbcTemplate;
  }

  public void setNamedJdbcTemplate(NamedParameterJdbcTemplate namedJdbcTemplate) {
    this.namedJdbcTemplate = namedJdbcTemplate;
  }

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public DataSource getDataSource() {
    return dataSource;
  }
@Autowired
  public void setDataSource(DataSource dataSource) {
    //this.dataSource = dataSource;
    //Pre Initialization of jdbctemplate during the setter injection of datasource
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  //@Override
  /*public Circle getCircle(int id) {
    Circle circle = null;
    //business logic to interact with derby db
    //Commenting the driver class loading and getting connection using Spring's Data Source
    //String driverClass = "org.apache.derby.jdbc.ClientDriver";
    Connection con = null;
    try {
    //Class.forName(driverClass).newInstance();
    
    
      //con = DriverManager.getConnection("jdbc:derby://localhost:1527//db");
      con = dataSource.getConnection();
      PreparedStatement ps = con.prepareStatement("Select * from Circle where id=?");
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      if(rs.next()){
        circle = new Circle(id,rs.getString("name"));
      }
      rs.close();
      ps.close();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    finally{
      try {
        con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return circle;
    }*/
  //Using JdbcTemplate which takes care of resultset executing query,closing connections and exception handling in whuch much of the jdbc boilerplate 
  //code can be minimized
  public int getCircleRecordsCount(){
    String sql = "select count(*) from circle";
    //jdbcTemplate.setDataSource(getDataSource());
    return jdbcTemplate.queryForObject(sql, Integer.class);
  }
  
  public String getCircleNameForId(int circleId){
    String sql="select name from circle where id=?";
    return jdbcTemplate.queryForObject(sql, new Object[]{circleId}, String.class);
  }
  
  //Usage of RowMapper class:
  //As spring jdbc doesnt know of mapping the db fields of a table to attributes of POJO class without any ORM framework, a RowMapper class is used
  public Circle getCircle(int circleId){
    String sql = "select * from circle where id=?";
    return jdbcTemplate.queryForObject(sql, new Object[]{circleId},new CircleMapper());
  }
  
  //The circlemapper method mapRow will be invoked if the resultset contains custom objects and to do mapping of fields of table to attributes of Circle class POJO
  private static final class CircleMapper implements RowMapper<Circle>{
    //here for each entry in the resultset the fields are mapped to attributes of circle POJO class, say if 5 entries are present in circle table, then
    //5 times this method is invoked and circle objects are created accordingly row by row.
    @Override
    public Circle mapRow(ResultSet rs, int rowNum) throws SQLException {
      Circle circle = new Circle();
      circle.setId(rs.getInt("ID"));
      circle.setName(rs.getString("NAME"));
      return circle;
    }
    
  }
  
  public List<Circle> getAllCircles(){
    String sql = "select * from circle";
    //return jdbcTemplate.queryForList(sql, Circle.class);//query return exception, so use mapper
    return jdbcTemplate.query(sql, new CircleMapper());
  }
  
  //write Operations
 /* public void insertCircle(Circle circle){
    String sql = "insert into circle values(?,?)";
    jdbcTemplate.update(sql, new Object[]{circle.getId(),circle.getName()});
  }*/
  
  //Rewriting insert circle method using NamedParameterJdbcTemplate as it provides named placeholders for avoiding conflicts in rearranging the order of columns
  public void insertCircle(Circle circle){
    String sql = "insert into circle values(:id,:name)";
    SqlParameterSource namedParameters = new MapSqlParameterSource("id",circle.getId()).addValue("name",circle.getName());
    namedJdbcTemplate.update(sql, namedParameters);
  }
  
  
  
  
  
  //DDL query execution
  public void createTriangle(){
    String sql="create table TRIANGLE (ID INTEGER,NAME VARCHAR(50))";
    jdbcTemplate.execute(sql);
  }
  
  public void deleteCircle(int circleId){
    String sql = "delete from circle where ID=?";
    jdbcTemplate.update(sql, new Object[]{circleId});
  }
}

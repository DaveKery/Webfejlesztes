package com.example.DAO;

import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository                               // this annnotation means this class is working with data (usually with Database) 
public class CustomerDAO {         // DAO class is working with Database and returns data to the caller
       
       @Autowired NamedParameterJdbcTemplate jdbc;      // for accessing Database
       
       public CustomerDTO getCustomerByID(@NonNull Integer identity){       // @NonNull throws an exception if this parameter is null
              
              try{
                     
                     return jdbc.queryForObject("SELECT * FROM Customer where id = :id",     // :id is a parameter
                                                new MapSqlParameterSource("id", identity),                 // replaces the :parameter with this parameter source 
                                                BeanPropertyRowMapper.newInstance(CustomerDTO.class));  // returns CustomerDTO
                     
              } catch(EmptyResultDataAccessException e) {      // in case of id does not exist, it will return null
                     return null;
              }      
       }
       
       public List<CustomerDTO> getCustomerNames(){       // get all names from Customer table by using "SELECT name FROM Customer"
              
              // newInstance() method create as much instances as many records are there in Database
              return jdbc.query("SELECT name FROM Customer", (Map)null, BeanPropertyRowMapper.newInstance(CustomerDTO.class));  
       }
       
       public List<CustomerDTO> getCustomerIDs(){       // get all IDs from Customer table by using "SELECT id FROM Customer"
              
              // newInstance() method create as much instances as many records are there in Database
              return jdbc.query("SELECT id FROM Customer", (Map)null, BeanPropertyRowMapper.newInstance(CustomerDTO.class));  
       }
       
       public List<CustomerDTO> getCustomerLocations(){       // get all lokacio from Customer table by using "SELECT lokacio FROM Customer"
              
              // newInstance() method create as much instances as many records are there in Database
              return jdbc.query("SELECT lokacio FROM Customer", (Map)null, BeanPropertyRowMapper.newInstance(CustomerDTO.class));  
       }
       
       public List<CustomerDTO> getCustomerBirthdays(){       // get all birthDay from Customer table by using "SELECT birthDay FROM Customer"
              
              // newInstance() method create as much instances as many records are there in Database
              return jdbc.query("SELECT birthDay FROM Customer", (Map)null, BeanPropertyRowMapper.newInstance(CustomerDTO.class));  
       }
       
        public List<CustomerDTO> getAllCustomer(){       // get all data from Customer table by using "SELECT * FROM Customer"
              
              // newInstance() method create as much instances as many records are there in Database
              return jdbc.query("SELECT * FROM Customer", (Map)null, BeanPropertyRowMapper.newInstance(CustomerDTO.class));  
       }
       
       public void insertCustomer(CustomerDTO c){
              
              // JdbcTemplate is needed for SimpleJdbcInsert (that is reason for using getJdbcTemplate())
              SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbc.getJdbcTemplate());   // in JdbcTemplate we use ? for parameters, in NamedParameterJdbcTemplate we use : for parameters
              simpleJdbcInsert.withSchemaName("public").withTableName("customer");  // schema and table are defined
              simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(c));      // execute INSERT by using CustomerDTO intance
       }
       
       public void insertCustomer(String name, Integer id, String lokacio, Date birthday){
              
              // update() method is used for SQL modification like SQL create, delete, update...
              jdbc.update("INSERT INTO Customer (name, id, lokacio, birthday) values(:name, :id, :lokacio, :birthday); ", 
                                   new MapSqlParameterSource()
                                           .addValue("name", name)    // replaces the :parameter with this parameter source
                                          .addValue("id", id)                // replaces the :parameter with this parameter source
                                          .addValue("lokacio", lokacio) // replaces the :parameter with this parameter source
                                          .addValue("birthday", birthday) // replaces the :parameter with this parameter source
              );         
       }
}

package com.example.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository   // this annnotation means this class is working with data (usually with Database)
public class LoginDAO {
       
       @Autowired NamedParameterJdbcTemplate jdbc;      // for accessing Database
       
        // WORLD2 LOGIN
        public LoginDTO getLoginValuesForWorld2(String givenUsername, String givenPassword) {
              
              MapSqlParameterSource inQueryParams = new MapSqlParameterSource();           
              inQueryParams.addValue("userName", givenUsername);
              inQueryParams.addValue("userPassword", givenPassword);
              
              try{
                     
                     return jdbc.queryForObject("SELECT userName,userPassword FROM LoginToWorld2 WHERE userName = :userName AND userPassword = :userPassword",         
                                                 inQueryParams,               
                                                 BeanPropertyRowMapper.newInstance(LoginDTO.class));  
                     
              }catch(EmptyResultDataAccessException e){
                     return null;
              }     
              
       }
        
       // WORLD3 LOGIN
       public LoginDTO getLoginValuesForWorld3(String givenUsername, String givenPassword) {
              
              MapSqlParameterSource inQueryParams = new MapSqlParameterSource();           
              inQueryParams.addValue("userName", givenUsername);
              inQueryParams.addValue("userPassword", givenPassword);
              
              try{
                     
                     return jdbc.queryForObject("SELECT userName,userPassword FROM LoginToWorld3 WHERE userName = :userName AND userPassword = :userPassword",         
                                                 inQueryParams,               
                                                 BeanPropertyRowMapper.newInstance(LoginDTO.class));  
                     
              }catch(EmptyResultDataAccessException e){
                     return null;
              }     
              
       } 
}

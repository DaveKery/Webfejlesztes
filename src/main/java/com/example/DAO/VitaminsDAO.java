package com.example.DAO;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository                               // this annnotation means this class is working with data (usually with Database) 
public class VitaminsDAO {         // DAO class is working with Database and returns data to the caller
       
       @Autowired NamedParameterJdbcTemplate jdbc;      // for accessing Database
       
       public List<VitaminsDTO> getEachVitaminData(){      // return list of objects that has each data from 'Vitamins' table
              
              try{
                     
                     return jdbc.query("SELECT * FROM Vitamins;", (Map)null, BeanPropertyRowMapper.newInstance(VitaminsDTO.class));
                     
              } catch(EmptyResultDataAccessException e) {      
                     return null;
              }      
       }
       
}

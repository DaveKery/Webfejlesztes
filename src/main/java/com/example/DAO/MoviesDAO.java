package com.example.DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository                               // this annnotation means this class is working with data (usually with Database) 
public class MoviesDAO {            // DAO class is working with Database and returns data to the caller
       
       @Autowired NamedParameterJdbcTemplate jdbc;      // for accessing Database
       
       public List<MoviesDTO> getMovieTitles(){       // get all titles from Movies table by using "SELECT title FROM Movies"
              return jdbc.query("SELECT title FROM Movies;", (Map)null, BeanPropertyRowMapper.newInstance(MoviesDTO.class));  
       }
       
       public MoviesDTO getMovieDataFromTitlesList(String chosenTitle){      // pass the already chosen title from 'select option' 
              
              try{
                     return jdbc.queryForObject("SELECT * FROM Movies where title = :title",         // :title is a parameter
                                                new MapSqlParameterSource("title", chosenTitle),               // replaces the :parameter with this parameter source 
                                                BeanPropertyRowMapper.newInstance(MoviesDTO.class));  // returns MoviesDTO
                     
              } catch(EmptyResultDataAccessException e) {      // in case of 'chosenTitle' does not exist, it will return null
                     return null;
              }      
       }
       
       public MoviesDTO getMovieDataByID(Integer chosenID){      // pass the already chosen ID by typing into a field on website
             
              try{
                     
                     MoviesDTO.idIsNotNumber = false;
                     
                     return jdbc.queryForObject("SELECT * FROM Movies where id = :id",     // :id is a parameter
                                                new MapSqlParameterSource("id", chosenID),               // replaces the :parameter with this parameter source 
                                                BeanPropertyRowMapper.newInstance(MoviesDTO.class));  // returns MoviesDTO
                     
              } catch(EmptyResultDataAccessException e) {      // in case of 'chosenID' cannot be used for query
                     
                     if(!(chosenID instanceof Integer)){
                            MoviesDTO.idIsNotNumber = true;    // in case of 'chosenID' is NOT a number
                     }
                     
                     return null;          // id exists and it is an Integer
              }      

       }
       
       public MoviesDTO getMovieDataByTitle(String chosenTitle){      // pass the already chosen title from 'select option' 
              
              try{
                     
                     Integer n = Integer.parseInt(chosenTitle);       // convert String to Integer  (if it is not possible during RunTime, then it is an unchecked exception that jumps to catch scope)
                     MoviesDTO.titleIsNumber = true;                     // in case of 'chosenMovieByTitle' is a number, it must not be a valid value
                     return null;
                     
              }catch(NumberFormatException ne){
                     
                     MoviesDTO.titleIsNumber = false;     // in case of 'chosenMovieByTitle' is not a number, it must be a valid value
                     
                     try{
                            return jdbc.queryForObject("SELECT * FROM Movies where title = :title",         // :title is a parameter
                                                 new MapSqlParameterSource("title", chosenTitle),               // replaces the :parameter with this parameter source 
                                                 BeanPropertyRowMapper.newInstance(MoviesDTO.class));  // returns MoviesDTO
                     
                     } catch(EmptyResultDataAccessException e) {      // in case of given title does not exist, it will return null
                            return null;
                     }      
                     
              }
       }
       
       public Integer getNumberOfMovies(){
              
              try{
                     
                     return jdbc.queryForObject("SELECT count(1) FROM Movies", (Map)null, Integer.class);
                     
              }catch(EmptyResultDataAccessException e){
                     return null;
              }
              
       }
       
       public MoviesDTO insertMovieIntoMoviesTable(Integer id, String title, String cinema, Date releaseDate){
              
              MapSqlParameterSource inQueryParams = new MapSqlParameterSource();           
              inQueryParams.addValue("id", id);
              inQueryParams.addValue("title", title);
              inQueryParams.addValue("cinema", cinema);
              inQueryParams.addValue("releaseDate", releaseDate);
              
              jdbc.update("INSERT INTO Movies (id, title, cinema, releaseDate) values(:id, :title, :cinema, :releaseDate);", 
                                new MapSqlParameterSource()
                                          .addValue("id", id)
                                          .addValue("title", title)
                                          .addValue("cinema", cinema)
                                          .addValue("releaseDate", releaseDate)
              );
              
              return jdbc.queryForObject("SELECT * FROM Movies where id = :id and title = :title AND cinema = :cinema AND releaseDate = :releaseDate;",
                                                inQueryParams,               
                                                BeanPropertyRowMapper.newInstance(MoviesDTO.class)); 
       }
       
       public List<MoviesDTO> getEachMovieData(){      // return list of objects that has each data from 'Movies' table
              
              try{
                     
                     return jdbc.query("SELECT * FROM Movies;", (Map)null, BeanPropertyRowMapper.newInstance(MoviesDTO.class));
                     
              } catch(EmptyResultDataAccessException e) {      
                     return null;
              }      
       }
       
       public ArrayList<String> getWatchedFilms(List<MoviesDTO> eachMovieData){      // return list of objects that has each data from 'Movies' table
              
              ArrayList<String> statuses = new ArrayList<>();
              
              for(MoviesDTO movies : eachMovieData){
                     
                     if (movies.getWatchLater()) {
                            statuses.add(movies.getTitle());   // put only watched films already
                     }
              }
             
              return statuses;
       }
       
       public List<Boolean> getwatchLaterStatuses(){      // return list of 'watchLater' values that represents the initial status of checkboxes from 'Movies' table
              
              try{
                     
                     return jdbc.query("SELECT watchLater FROM Movies;", (Map)null, BeanPropertyRowMapper.newInstance(MoviesDTO.class));
                     
              } catch(EmptyResultDataAccessException e) {      
                     return null;
              }      
       }
       
       public void insertCustomer(MoviesDTO m){
              
              // JdbcTemplate is needed for SimpleJdbcInsert (that is reason for using getJdbcTemplate())
              SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbc.getJdbcTemplate());   // in JdbcTemplate we use ? for parameters, in NamedParameterJdbcTemplate we use : for parameters
              simpleJdbcInsert.withSchemaName("public").withTableName("Movies");  // schema and table are defined
              simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(m));      // execute INSERT by using CustomerDTO intancec
       }
       
}

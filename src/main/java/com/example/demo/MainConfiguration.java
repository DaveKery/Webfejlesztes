package com.example.demo;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Configuration
public class MainConfiguration {

       /* Azért kell, hogy a messages.properties-t megtalálja a /config könyvtár alatt */
       @Bean
       public MessageSource messageSource() {
              ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
              messageSource.setBasename("config/messages");
              // messageSource.setDefaultEncoding("UTF-8");
              return messageSource;
       }

       /* DataSource property specifies an object containing data to be represented as a Recordset object  
                              !!! This is a factory for connections to the physical databases !!! 
                               It is an alternative to the DriverManager facility
                              A datasource uses a URL along with username/password credentials to establish the database connection
                              datasource implements the javax.sql.DataSource interface */
       @Bean
       @ConfigurationProperties("mydb")          // configure from 'application.properties' where 'mydb' properties were set
       public DataSource getDataSource() {      // DataSource class need to be imported, Spring runs it, returns a DataSource obj that is actually a Bean
              return DataSourceBuilder.create().build();       // bind externalized properties to the DataSource instance (DataSourceBuilder)
       }

       @Bean
       public NamedParameterJdbcTemplate getJdbcTemplate(@Autowired DataSource datasource) {
              return new NamedParameterJdbcTemplate(datasource);      // it builds NamedParameterJdbcTemplate by datasource, 
                                                                                                         // this is the Java level we can add SQL orders to !!!
       }
}

package com.example.DAO;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoviesDTO {
       
       private Long id;
       private String title;
       private String cinema;
       private Date releaseDate;
       private String storyText;
       private Boolean watchLater;
       
       static public Boolean titleIsNumber;
       static public Boolean idIsNotNumber;
       
       
}

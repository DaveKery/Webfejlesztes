package com.example.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoviesForm {
       
       // stuffs in World2.html
       private String chosenSpiderMovieColumn;
       private String chosenMovieFromCinema;
       private Integer chosenMovieByID;
       private String chosenMovieByTitle;
       private Integer insertedID;
       private String insertedTitle;
       private String insertedCinema;
       private Date insertedReleaseDate = new Date();

        // stuffs in World3.html
       private List<String> watchedMovies = new ArrayList<>();
       private String radioFavouriteMovie;
       
}

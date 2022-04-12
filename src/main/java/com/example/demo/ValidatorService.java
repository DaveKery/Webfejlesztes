package com.example.demo;

import com.example.DAO.LoginDTO;
import com.example.DAO.MoviesDTO;
import org.springframework.validation.BindingResult;

public class ValidatorService {

       public static boolean validateRegistration(RegistrationForm registrationForm, BindingResult result) {     
              
              boolean hasNoError = true;
              
              // it is true if user leaves any of the fields (firstName, lastName, userPassword, yearNumber, readDoc) empty that means 'inst' is NULL and all of its fields are as well!!!
              if(registrationForm.getFirstName() == null || registrationForm.getLastName() == null || registrationForm.getUserPassword() == null || registrationForm.getDatum() == null || registrationForm.getRadioResult() == null || registrationForm.getCommentArea() == null || registrationForm.getYearNumber() == null || registrationForm.getMonthNumber() == null || registrationForm.getDayNumber() == null || registrationForm.getDateToTrip() == null){
                     result.rejectValue("firstName", "FieldIsEmpty", "This is the default error message!");
                     result.rejectValue("lastName", "FieldIsEmpty", "This is the default error message!");
                     result.rejectValue("userPassword", "FieldIsEmpty", "This is the default error message!");
                     result.rejectValue("yearNumber", "FieldIsEmpty", "This is the default error message!");
                     result.rejectValue("readDoc", null, "You have to read and accept the mandatory documents!");
                     return false;
              }

              if (registrationForm.getFirstName().isEmpty()) {
                     result.rejectValue("firstName", "FieldIsEmpty", "This is the default error message!");
                     hasNoError = false;
              } 
              else if (registrationForm.isFirstNameInvalid()) {
                     result.rejectValue("firstName", "InvalidFirstName", "This is the default error message!");
                     hasNoError = false;
              }

              if (registrationForm.getLastName().isEmpty()) {
                     result.rejectValue("lastName", "FieldIsEmpty", "This is the default error message!");
                     hasNoError = false;
              } 
              else if (registrationForm.isLastNameInvalid()) {
                     result.rejectValue("lastName", "InvalidLastName", "This is the default error message!");
                     hasNoError = false;
              }

              if (registrationForm.getUserPassword().isEmpty()) {
                     result.rejectValue("userPassword", "FieldIsEmpty", "This is the default error message!");
                     hasNoError = false;
              } 
              else if (!(registrationForm.isPasswordValid())) {
                     result.rejectValue("userPassword", "InvalidPassword", "This is the default error message!");
                     hasNoError = false;
              } 
              else if (!(registrationForm.isPasswordLengthValid())) {
                     result.rejectValue("userPassword", "InvalidLengthOfPassword", "This is the default error message!");
                     hasNoError = false;
              }

              if (!(registrationForm.isUserOldEnough())) {
                     result.rejectValue("yearNumber", "UserIsTooYoung", "This is the default error message!");
                     hasNoError = false;
              }

              if (registrationForm.isReadDoc() == false) {
                     result.rejectValue("readDoc", null, "You have to read and accept the mandatory documents!");
                     hasNoError = false;
              }
              
              return hasNoError;
       }

       public static void validateMoviesTableByID(MoviesDTO moviesDTO, BindingResult result, MoviesForm moviesForm) {

              // TODO: Why does it keep writing the system error if we do not type numeric character(s)??? ->>> Field does not contain any Integer!

              if (moviesDTO.idIsNotNumber){         
                     result.rejectValue("chosenMovieByID", "IDisNOTaNumberOrEmpty", "This is the default error message!");
              }
              else if (moviesDTO == null) {      // check if returned MoviesDTO object from movieDAO.getMovieDataByID(moviesForm.getChosenMovieByID()) is null or exists in DB table
                     result.rejectValue("chosenMovieByID", "IDnotExists", "This is the default error message!");
              }
       }

       public static void validateMoviesTableByTITLE(MoviesDTO moviesDTO, BindingResult result, MoviesForm moviesForm) {
              
              if(moviesDTO.titleIsNumber){         // static instance variable can be changed to true/false in case of 'chosenMovieByTitle' only contains integer or not just integer
                     result.rejectValue("chosenMovieByTitle", "TITLEisInteger", "This is the default error message!");
              }
              else if (moviesForm.getChosenMovieByTitle().isEmpty()) {
                     result.rejectValue("chosenMovieByTitle", "TITLEfieldEmpty", "This is the default error message!");
              } 
              else if (moviesDTO == null) {      // check if returned MoviesDTO object from movieDAO.getMovieDataByID(moviesForm.getChosenMovieByID()) is null or exists in DB table
                     result.rejectValue("chosenMovieByTitle", "TITLEnotExists", "This is the default error message!");
              }
       }
       
       public static boolean validateInsertValues(MoviesForm moviesForm, BindingResult result){
              
              boolean valuesAreValid = true;
              
              // TODO: If the given ID is already exist, then throw an error 
              if(moviesForm.getInsertedID() == null){
                     result.rejectValue("insertedID", null, "Invalid value!");
                     valuesAreValid = false;
              }
              
              // TODO: If the given TITLE is already exist, then throw an error 
              if(moviesForm.getInsertedTitle().isEmpty()){
                     result.rejectValue("insertedTitle", null, "Empty title value!");
                     valuesAreValid = false;
              }
              
              if(moviesForm.getInsertedCinema().isEmpty()){
                     result.rejectValue("insertedCinema", null, "Empty cinema value!");
                     valuesAreValid = false;
              }
              
              if(moviesForm.getInsertedReleaseDate() == null){
                     result.rejectValue("insertedReleaseDate", null, "Invalid value!");
                     valuesAreValid = false;
              }
              
              if(valuesAreValid){
                     return true;
              }else{
                     return false;
              }
              
       }
       
       public static boolean validateLoginWorld2(LoginDTO loginDTO, BindingResult result, RegistrationForm registrationForm){
              
              // loginDTO is null if any of the input fields 'authWorld2UserName' or 'authWorld2Password' are not proper for getLoginValuesForWorld2() at queryForObject() method
              if (loginDTO == null) {        
                     result.rejectValue("authWorld2UserName", "LoginWorld2INVALID", "This is the default error message!");
                     result.rejectValue("authWorld2Password", null, "");     // 3rd parameter is empty since we don't want more error message in World2 login window
                     return false;
              }
              
              return true;
       }
       
       public static boolean validateLoginWorld3(LoginDTO loginDTO, BindingResult result, RegistrationForm registrationForm){
              
              // loginDTO is null if any of the input fields 'authWorld3UserName' or 'authWorld3Password' are not proper for getLoginValuesForWorld3() at queryForObject() method
              if (loginDTO == null) {        
                     result.rejectValue("authWorld3UserName", "LoginWorld3INVALID", "This is the default error message!");
                     result.rejectValue("authWorld3Password", null, "");     // 3rd parameter is empty since we don't want more error message in World3 login window
                     return false;
              }
              
              return true;
       }
       
}

package com.example.demo;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElementService {             // TODO: ezt szétválasztani külön ...Form.java oszutályokra, azokon belül legyenek külön a HTML fájlokra vonatkozó változók
       
       // stuffs in view.html 
       private String firstName;
       private String lastName;
       private String userPassword;
       private String radioResult;
       private String commentArea;
       private String yearNumber;
       private String monthNumber;
       private String dayNumber;
       private boolean readDoc;
       public Date datum = new Date();
       private String DateToTrip;
       private String authWorld2UserName;
       private String authWorld2Password;
       private String authWorld3UserName;
       private String authWorld3Password;
       
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
       private String moviesRadioWorld3;
       private boolean check;
       
       public ElementService() {
              
       }
       
       public void setRadioResult(String radioResult) {        // this method is needed for inst.setRadioResult(defaultRadioButton); in @GetMapping handler method
               this.radioResult = radioResult;
       }
       
       public boolean isFirstNameInvalid(){
              
              for(int i = 0; i < firstName.length(); i++){
                     
                     if(!((firstName.charAt(i) >= 'A' && firstName.charAt(i) <= 'Z') || (firstName.charAt(i) >= 'a' && firstName.charAt(i) <= 'z'))){
                            return true;
                     }
              }
              return false;
       }
       
       public boolean isLastNameInvalid(){
              
              for(int i = 0; i < lastName.length(); i++){
                     
                     if(!((lastName.charAt(i) >= 'A' && lastName.charAt(i) <= 'Z') || (lastName.charAt(i) >= 'a' && lastName.charAt(i) <= 'z'))){
                            return true;
                     }
              }
              return false;
       }
       
       public boolean isPasswordValid(){
              
              boolean letterExists = false;
              boolean numberExists = false;

              for(int i = 0; i < userPassword.length(); i++){

                  if(letterExists == false || numberExists == false){
                         
                         if((userPassword.charAt(i) >= 'A' && userPassword.charAt(i) <= 'Z') || (userPassword.charAt(i) >= 'a' && userPassword.charAt(i) <= 'z')){
                                letterExists = true;
                         }
                         else if(userPassword.charAt(i) >= '0' && userPassword.charAt(i) <= '9'){
                                numberExists = true;
                         }
                  }
                  else{
                         break;
                  }
              }

              if(numberExists && letterExists){
                     return true;
              }else{
                     return false;
              }
       }
       
       public boolean isPasswordLengthValid(){
              
              if(userPassword.length() >= 8){
                     return true;
              }else{
                     return false;
              }
       }
       
       public boolean isUserOldEnough(){
              
              if((Integer.parseInt(yearNumber) <= 2003) && (Integer.parseInt(monthNumber) <= 12) && (Integer.parseInt(dayNumber) <= 21)){
                     return true;
              }else if((Integer.parseInt(yearNumber) <= 2003) && (Integer.parseInt(monthNumber) < 12) && (Integer.parseInt(dayNumber) <= 31)){
                     return true;
              }else if((Integer.parseInt(yearNumber) < 2003) && (Integer.parseInt(monthNumber) <= 12) && (Integer.parseInt(dayNumber) <= 31)){
                     return true;
              }else{
                     return false;
              }
       }
}

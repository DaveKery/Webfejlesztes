package com.example.demo;
import com.example.DAO.CustomerDAO;
import com.example.DAO.CustomerDTO;
import com.example.DAO.LoginDAO;
import com.example.DAO.LoginDTO;
import com.example.DAO.MoviesDAO;
import com.example.DAO.MoviesDTO;
import com.example.DAO.VitaminsDAO;
import com.example.DAO.VitaminsDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication(scanBasePackages = "com.example")       // Spring can only look for Beans in this package com.example
@Controller
public class DemoApplication extends ValidatorService {
       
       @Autowired CustomerDAO customerDAO;                 // for working with Database
       @Autowired MoviesDAO movieDAO;                         // for working with Database
       @Autowired LoginDAO world2LoginDAO;                  // for working with Database
       @Autowired LoginDAO world3LoginDAO;                  // for working with Database
       @Autowired VitaminsDAO vitaminsDAO;                  // for working with Database
       
        public static void main(String[] args) {
              SpringApplication.run(DemoApplication.class, args);  // START Spring applications
        }
        
        @ModelAttribute             
        public void getLanguage(Model model) {  // this method runs before @GetMapping and @PostMapping in @Controller class
               
              ArrayList<HtmlOption> nyelvek = new ArrayList<>();
              nyelvek.add(new HtmlOption("C#", "1"));
              nyelvek.add(new HtmlOption("Java", "2"));
              nyelvek.add(new HtmlOption("Python", "3"));
              
              model.addAttribute("languages", nyelvek); // this case we handle model, so no need to use return
        }
        
        @ModelAttribute             
        public void getHistoryDates(Model model) {  // this method runs before @GetMapping and @PostMapping in @Controller class
               
              ArrayList<HtmlOption> events = new ArrayList<>();
              events.add(new HtmlOption("1969-07-20", "date of first landed humans on the Moon"));
              events.add(new HtmlOption("1945-09-02", "date of end of WorldWar the II"));
              events.add(new HtmlOption("1939-09-01", "date of beginning of WorldWar the II"));
              events.add(new HtmlOption("1001-01-01", "date of the first King (Istvan Vajk) was crowned in Hungary"));
              
              model.addAttribute("dates_from_history", events); // this case we handle model, so no need to use return
        }
        
        @ModelAttribute             
        public void getSpidermanMovieData(Model model) {  // this method runs before @GetMapping and @PostMapping in @Controller class
               
              ArrayList<HtmlOption> spidermanDataList = new ArrayList<>();
               
               spidermanDataList.add(new HtmlOption("title", "1"));
               spidermanDataList.add(new HtmlOption("releaseDate", "2")); 
              
              model.addAttribute("spiderDataList", spidermanDataList); // this case we handle model, so no need to use return
        }
        
        @ModelAttribute             
        public void getMoviesForWorld3(Model model) {  // this method runs before @GetMapping and @PostMapping in @Controller class
               
              ArrayList<HtmlOption> moviesForWorld3 = new ArrayList<>();
              
              for(MoviesDTO x : movieDAO.getMovieTitles()){           // iterate through the titles of 'Movies' table to put them into an ArrayList one line below
                     moviesForWorld3.add(new HtmlOption(x.getTitle(), x.getTitle()));      // upload 'moviesForWorld3' ArrayList by each title of 'Movies' table
              }
              
              model.addAttribute("moviesForWORLD3", moviesForWorld3); // this case we handle model, so no need to use return
        }
        
        
        
        
        /* Application form handler methods
        *  This handler methods are used for creating a webpage containing an application form in order to filling out data and print them out + a control panel for other web pages */
        @GetMapping("/")
        public String showForm(Model model) {
               
               List<CustomerDTO> allCustomer = customerDAO.getAllCustomer();
               
               CustomerDTO myCustomer1 = customerDAO.getCustomerByID(1);
               CustomerDTO myCustomer28 = customerDAO.getCustomerByID(28);
               
               CustomerDTO ujCustomer = new CustomerDTO();
               ujCustomer.setName("Arnold");
               ujCustomer.setId(5l);
               ujCustomer.setLokacio("Győr");
               ujCustomer.setBirthday(new Date());
               
               customerDAO.insertCustomer(ujCustomer);
               
               ElementService inst = new ElementService();   // ElementService OBJECT 
               inst.setRadioResult("1");
      
               model.addAttribute("Obj", inst);
               model.addAttribute("allCustomer", allCustomer);  
               
               return "view";
        }
        
        @PostMapping("/")
        public String submitForm(@ModelAttribute("Obj") ElementService peldany, BindingResult result, Model model) {
               
               LoginDTO world2Authentication = world2LoginDAO.getLoginValuesForWorld2(peldany.getAuthWorld2UserName(), peldany.getAuthWorld2Password());
               if (validateLoginWorld2(world2Authentication, result, peldany)) {
                      
                      List<MoviesDTO> moviesTitles = movieDAO.getMovieTitles();     // for printing the watchable film titles for user
               
                      ElementService inst = new ElementService();   

                      model.addAttribute("spidermanDataObject", inst);
                      model.addAttribute("filmCimek", moviesTitles);
                      model.addAttribute("NumberOfMovies", movieDAO.getNumberOfMovies()); // for printing the number of watchable film titles for user
                      
                      return "world2_view";
               }
               
               LoginDTO world3Authentication = world3LoginDAO.getLoginValuesForWorld3(peldany.getAuthWorld3UserName(), peldany.getAuthWorld3Password());
               if (validateLoginWorld3(world3Authentication, result, peldany)) {
                      //showWorld3(model);        // calling this GetMapping handler method will lead us to World3 by the same URL: http://localhost:8080/  and not http://localhost:8080/World3
                      //return "world3_view";      // TODO: correct the following -> it does not lead us to the correct URI: http://localhost:8080/World3   just goes to http://localhost:8080/ but why???
                      return "redirect:/World3";   // szerver visszaválaszol a böngészőnek, és egy másik URL-re irányítja át (átredirektáljuk az alábbi URL-re)
               }
               
               if (validateRegistration(peldany, result)) {
                      return "display"; 
               }
               
               if(result.hasErrors()){     // catches every errors that "result" has from validation methods !!!
                      return "view";
               }
               
               return "view";      // !!! FYI It has changed from display to view !!!
        }
        
        
        
        
        /* World2 handler methods
        *  This handler method is used for query data from DataBase */
        @GetMapping("/World2")
        public String showWorld2(Model model){
               
               List<MoviesDTO> moviesTitles = movieDAO.getMovieTitles();     // for printing the watchable film titles for user
               
               ElementService inst = new ElementService();   

               model.addAttribute("spidermanDataObject", inst);
               model.addAttribute("filmCimek", moviesTitles);
               model.addAttribute("NumberOfMovies", movieDAO.getNumberOfMovies()); // for printing the number of watchable film titles for user
               
              return "world2_view";
        }
        
        @PostMapping("/World2")
        public String submitWorld2(@ModelAttribute("spidermanDataObject") ElementService element, BindingResult result, Model model){
               
               List<MoviesDTO> moviesTitles = movieDAO.getMovieTitles();     // for printing the watchable film titles for user
               
               MoviesDTO currentMovieFromTitlesList = movieDAO.getMovieDataFromTitlesList(element.getChosenMovieFromCinema());
               model.addAttribute("valasztottFilmCimListabol", currentMovieFromTitlesList);
               
               MoviesDTO currentMovieByID = movieDAO.getMovieDataByID(element.getChosenMovieByID());     // after calling GetMapping handler method, we passes a given ID into the 'chosenMovieByID' field 
               model.addAttribute("valasztottFilmIDSzerint", currentMovieByID);
               validateMoviesTableByID(currentMovieByID, result, element);        // VALIDATION: 'chosenMovieByID' field is empty or exists 'Movies' table at all
              
               MoviesDTO currentMovieByTitle = movieDAO.getMovieDataByTitle(element.getChosenMovieByTitle());     // after calling GetMapping handler method, we passes a given TITLE into the 'chosenMovieByTitle' field 
               model.addAttribute("valasztottFilmTitleSzerint", currentMovieByTitle);
               validateMoviesTableByTITLE(currentMovieByTitle, result, element);        // VALIDATION: 'chosenMovieByTitle' field is empty or exists 'Movies' table at all
              
               // validate() ide kell, mivel előbb kell letesztelni, hogy jó típusú értékek vannak-e bene, és utána végrehajtani az INSERT parancsot
               if(validateInsertValues(element, result)){
                     MoviesDTO insertedMovie = movieDAO.insertMovieIntoMoviesTable(element.getInsertedID(), element.getInsertedTitle(), element.getInsertedCinema(), element.getInsertedReleaseDate());
                     model.addAttribute("insertedSpidermanFilm", insertedMovie);   // this model used for presenting title,cinema and releaseDate that was just inserted by user
               }
               
//               MoviesDTO insertedMovie = movieDAO.insertMovieIntoMoviesTable(element.getInsertedID(), element.getInsertedTitle(), element.getInsertedCinema(), element.getInsertedReleaseDate());
//               model.addAttribute("insertedSpidermanFilm", insertedMovie);   // this model used for presenting title,cinema and releaseDate that was just inserted by user
              
               model.addAttribute("filmCimek", moviesTitles);
               model.addAttribute("NumberOfMovies", movieDAO.getNumberOfMovies());  // for printing the number of watchable film titles for user
               
               return "world2_view";             // @PostMapping will keep user on the same page after clicking on submit button
        }
        
        
        
        
        /* World3 handler methods
        *  This handler method is used for presenting a table with each movie from 'Movies' table with a little image and details */
        @GetMapping("/World3")
        public String showWorld3(Model model){
               
               List<MoviesDTO> eachMovieData = movieDAO.getEachMovieData();           // gets false values for each 'watchLater' variables 
               
               //List<Boolean> watchedFilmsStatus = movieDAO.getwatchLaterStatuses();        // ??? ez nem biztos hogy kell ???
               
               // TODO: create a list of checkboxes, that will be presented in GetMapping first, user will be able to tick them, then pass it to PostMapping secondly without 500 Internal Server Error!!!
               List<Boolean> checkboxes = new ArrayList<>();
               checkboxes.add(false);
               checkboxes.add(false);
               
               ElementService inst = new ElementService();
               
               model.addAttribute("filmAdatok", eachMovieData);
               model.addAttribute("Obj", inst);
               model.addAttribute("checkboxes", checkboxes);
               model.addAttribute("moviesForm", new MoviesForm());
               
              return "world3_view";
        }
        
        @PostMapping("/World3")
        public String submitWorld3(@ModelAttribute("moviesForm") MoviesForm moviesForm, BindingResult result, Model model){   // ??? PostMapping handler metódusba nem lehet @ModelAttribute("filmAdatok") List<MoviesDTO> xxx beletenni?     -> https://stackoverflow.com/questions/55855987/how-to-use-a-list-as-a-model-attribute-in-spring
               
               List<MoviesDTO> eachMovieData = movieDAO.getEachMovieData();           // gets false values for each 'watchLater' variables 
               
               // iterate through checkboxes and check which one is ticked/not ticked
               /*for(int i = 0; i < checkboxes.size(); i++){            
                     
                     if (checkboxes.get(i) == true) {
                            eachMovieData.get(i).setWatchLater(true);        // if checkbox is ticked, then set that specific number of 'watchLater' of 'eachMovieData' to true
                     }
               }*/
                 
               ElementService inst = new ElementService();
               
               model.addAttribute("filmAdatok", eachMovieData);
               model.addAttribute("Obj", inst);  
               model.addAttribute("checkboxStatuses",movieDAO.getWatchedFilms(eachMovieData)); // get String values of 'title' values from each MoviesDTO in 'eachMovieData' List

               return "world3_view";
        }

        
        
        
        @RequestMapping("/user/{code}")
        public String searchForUser(@PathVariable(value="code") String enterCode) throws Exception {
               
               if (Integer.parseInt(enterCode) == 123 || Integer.parseInt(enterCode) == 321 || Integer.parseInt(enterCode) == 999){
                      System.out.println(enterCode);
               } else{
                      throw new Exception("This code: " +  enterCode + " is not valid for entering!");
               }
               
              return "multiverse_view";
        }        
        
        
        
        
        /* Multiverse handler methods
        *  This handler method is used for leading user to Multiverse webpage for using interactive components */
        @GetMapping("/Multiverse")
        public String showMultiverse(){
              return "multiverse_view";
        }
        
        /* Vitamins handler methods
        *  This handler method is used for leading user to Vitamins webpage for getting to know basic information of vitamin comsumption */
        @GetMapping("/Vitamins")
        public String showVitamins(Model model){
               
               List<VitaminsDTO> vitaminsData = vitaminsDAO.getEachVitaminData();
               
               model.addAttribute("vitaminsData", vitaminsData);
               
              return "vitamins_view";
        }
        
        /* Liquid handler methods
        *  This handler method is used for leading user to Liquid webpage for getting to know basic information of the corresponding amount and quality of drinking */
        @GetMapping("/Liquid")
        public String showLiquid(){
              return "liquid_view";
        }
        
        /* Vitamins handler methods
        *  This handler method is used for leading user to Vitamins webpage for getting to know basic information of vitamin comsumption */
        @GetMapping("/Food")
        public String showFood(){
              return "food_view";
        }
        
        
        
}


// DINAMIKUSAN a form application-ből felvett adatok alapján vihetők be adatbázisba az adatok
//customerDAO.insertCustomer(peldany.getFirstName(), (int)(Math.random()*1000), "Bukarest", peldany.getDatum());        // form mezőbe begépelt firstname és datumot vigye fel Customer table-be
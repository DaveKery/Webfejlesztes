package com.example.DAO;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {
       
       private String name;
       private Long id;
       private String lokacio;
       private Date birthday;
       
}

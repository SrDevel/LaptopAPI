package com.example.tallerspringrest;

import com.example.tallerspringrest.entities.Laptop;
import com.example.tallerspringrest.repositories.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TallerspringRestApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TallerspringRestApplication.class, args);
        LaptopRepository repository = context.getBean(LaptopRepository.class);

        //CRUD
        //Crear laptop
        System.out.println("Numero de laptops en la base de datos: "+repository.findAll().size());

        Laptop laptop = new Laptop(null, "HP","Pavilon", "Core i7", 16, 1000, "GT 1050", "Windows 10", 2800000d);
        Laptop laptop2 = new Laptop(null, "HP","Pavilon", "Core i7", 16, 1000, "GT 1050", "Windows 10", 2800000d);

        //Almacenar laptop
        repository.save(laptop);
        repository.save(laptop2);

        //Recuperar todos los laptops
        System.out.println("Numero de laptops en la base de datos: "+repository.findAll().size());

        //Borrar laptop
        //repository.deleteById(1L);

    }

}

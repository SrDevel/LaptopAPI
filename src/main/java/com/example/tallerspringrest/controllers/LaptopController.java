package com.example.tallerspringrest.controllers;

import com.example.tallerspringrest.entities.Laptop;
import com.example.tallerspringrest.repositories.LaptopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {

    @Value("${app.message}")
    String mensajePrueba;
    private final LaptopRepository repository;
    private final Logger log = LoggerFactory.getLogger(LaptopController.class);

    public LaptopController(LaptopRepository repository){
        this.repository = repository;
        System.out.println(mensajePrueba);
    }

    @GetMapping("/api/laptops")
    public List<Laptop> findAll(){
        return repository.findAll();
    }

    @GetMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> findById(@PathVariable Long id){
        Optional<Laptop> laptopOpt = repository.findById(id);

        return laptopOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/laptops")
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop){
        if (laptop.getId() != null){
            log.warn("Trying to create a existing computer with id {" + laptop.getId()+"}");
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(repository.save(laptop));
        }
    }

    @PutMapping("/api/laptops")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop){
        if (laptop.getId() == null){
            log.warn("Trying to create a laptop with a existing id {" + laptop.getId()+ "}");
            return ResponseEntity.badRequest().build();
        } else {
            if (!repository.existsById(laptop.getId())){
                log.warn("Trying to create a laptop with a wrong id {" + laptop.getId()+ "}");
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok(repository.save(laptop));
    }

    @DeleteMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> delete(@PathVariable Long id){
        Optional<Laptop> laptopOpt = repository.findById(id);
        if (laptopOpt.isEmpty()){
            log.warn("Trying to delete a non-exist laptop with id {" + id + "}");
            return ResponseEntity.notFound().build();
        }
        repository.existsById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/laptops")
    public ResponseEntity<Laptop> deleteAll(){
        log.warn("Deleting all laptops");
        repository.deleteAll();
        return ResponseEntity.noContent().build();
    }

}

package com.example.tallerspringrest.controllers;

import com.example.tallerspringrest.TallerspringRestApplication;
import com.example.tallerspringrest.entities.Laptop;
import com.example.tallerspringrest.repositories.LaptopRepository;
import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.server.reactive.HttpHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    private TestRestTemplate textRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;


    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        textRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @DisplayName("Get all laptops")
    @Test
    void findAll() {
        ResponseEntity<Laptop[]> response = textRestTemplate.getForEntity("/api/laptops", Laptop[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());

        List<Laptop> laptops =
                Arrays.asList(response.getBody());

        assertEquals(1, laptops.size());
    }

    @DisplayName("Get laptop by id")
    @Test
    void findById() {
        ResponseEntity<Laptop> response = textRestTemplate.getForEntity("/api/laptops/1", Laptop.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("Create a new laptop")
    @Test
    void create() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                    {
                            "marca": "HP",
                            "modelo": "Pavilon - 2",
                            "procesador": "Core i7",
                            "ram": 16,
                            "almacenamiento": 1000,
                            "tarjetaGrafica": "GT 1050",
                            "sistemaOperativo": "Windows 10",
                            "precio": 2800000.0
                        }
                """;

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Laptop> response = textRestTemplate.exchange("/api/laptops",HttpMethod.POST,request, Laptop.class);

        Laptop laptop = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, laptop.getId());
        assertEquals("HP", laptop.getMarca());
    }

    @DisplayName("Update a laptop")
    @Test
    void update() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                        "id": 1,
                        "marca": "HP",
                        "modelo": "Pavilon - 2 modified",
                        "procesador": "Core i7",
                        "ram": 16,
                        "almacenamiento": 1000,
                        "tarjetaGrafica": "GT 1050",
                        "sistemaOperativo": "Windows 10",
                        "precio": 2800000.0
                    }
                """;

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Laptop> response = textRestTemplate.exchange("/api/laptops", HttpMethod.PUT, request, Laptop.class);

        Laptop laptop = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("Delete a laptop")
    @Test
    void delete() {
        ResponseEntity<Laptop> response = textRestTemplate.exchange("/api/laptops/1", HttpMethod.DELETE, null, Laptop.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @DisplayName("Delete all laptops")
    @Test
    void deleteAll() {
        ResponseEntity<Laptop[]> response = textRestTemplate.exchange("/api/laptops", HttpMethod.DELETE, null, Laptop[].class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
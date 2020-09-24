package com.example.demo.controllers;

import com.example.demo.model.entities.Person;
import com.example.demo.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonaController {

  @Autowired
  private PersonaService personaService;


  @GetMapping
  public List<Person> getAll() {
    return personaService.getAll();
  }

  @PostMapping
  public void create(@RequestBody Person person) {
    personaService.create(person);
  }

  @GetMapping("/{id}")
  public Person find(@PathVariable Long id){
    return personaService.find(id);
  }

  @GetMapping("/by-name")
  public List<Person> getByName(@RequestParam String name) {
    return personaService.findByName(name);
  }

}

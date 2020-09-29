package com.example.demo.controllers;

import com.example.demo.model.entities.Person;
import com.example.demo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

  @Autowired
  private PersonService personService;

  @GetMapping("/{id}")
  public Person find(@PathVariable Integer id){
    return personService.find(id);
  }

  @PostMapping
  public void create(@RequestBody @Valid Person person){
     personService.create(person);
  }


  @PutMapping("/{id}")
  public void update(@PathVariable Integer id, @RequestBody @Valid Person person){
    personService.update(id, person);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Integer id) {
    personService.delete(id);
  }

  @GetMapping
  public List<Person> getAll() {
    return personService.getAll();
  }

  @GetMapping("/by-name")
  public List<Person> findByName(@RequestParam String name){
    return personService.getByName(name);
  }
}

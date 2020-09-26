package com.example.demo.services;


import com.example.demo.model.entities.Person;
import com.example.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;

  public void create(Person p) {
    boolean personExist = personRepository.existsById(p.getId());

    if (personExist) throw new RuntimeException("Ya existe la persona");

    personRepository.save(p);

  }

  public Person find(Integer id) {
    return personRepository.findById(id).get();
  }

  public void update(Integer id, Person p) {
    boolean personExist = personRepository.existsById(id);

    if (!personExist) throw new RuntimeException("No existe la persona");

    p.setId(id);
    personRepository.save(p);
  }

  public void delete(Integer id) {
    boolean personExist = personRepository.existsById(id);

    if (!personExist) throw new RuntimeException("No existe la persona");

    personRepository.deleteById(id);
  }

  public List<Person> getAll() {
    return personRepository.findAll();
  }

  public List<Person> getByName(String name) {
    return personRepository.findByName("%"+name+"%");
  }
}

package com.example.demo.services;

import com.example.demo.exceptions.BusinessException;
import com.example.demo.model.entities.Person;
import com.example.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService {

  @Autowired
  private PersonRepository personaRepository;

  public void create(Person p) {
    Person pfound = personaRepository.findById(p.getId()).orElse(null);

    if(pfound != null) {
      throw new BusinessException("persona ya existe");
    }

    personaRepository.save(p);
  }

  public List<Person> getAll() {
    return personaRepository.findAll();
  }

  public List<Person> findByName(String name) {
    return personaRepository.findByName("%"+name+"%");
  }

  public Person find(Integer id) {
    return personaRepository.findById(id).get();
  }

}

package com.example.demo.services;

import com.example.demo.model.entities.Person;
import com.example.demo.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService {

  @Autowired
  private PersonaRepository personaRepository;

  public void create(Person p) {
    Person pfound = personaRepository.findById(p.getId()).orElse(null);

    if(pfound != null) {
      throw new RuntimeException("persona ya exxiste");
    }

    personaRepository.save(p);
  }

  public List<Person> getAll() {
    return personaRepository.findAll();
  }

  public List<Person> findByName(String name) {
    return personaRepository.getPersonaByName("%"+name+"%");
  }

  public Person find(Long id) {
    return personaRepository.findById(id).get();
  }

}

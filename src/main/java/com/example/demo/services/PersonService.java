package com.example.demo.services;


import com.example.demo.exceptions.BusinessException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.entities.Person;
import com.example.demo.repositories.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  public void create(Person p) {
    boolean personExist = personRepository.existsById(p.getId());

    if (personExist) throw new BusinessException("Ya existe la persona", "person_already_exists");

    List<Person> personsByPhone = personRepository.findByPhoneNumber(p.getPhoneNumber());

    if(!personsByPhone.isEmpty()) throw new BusinessException("Ya existe el telefono", "phone_already_exists");

    personRepository.save(p);

  }

  public Person find(Integer id) {

    Person p = personRepository.findById(id).get();

    try {
      redisTemplate.opsForValue().set(id.toString(), objectMapper.writeValueAsString(p));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return p;

  }

  public void update(Integer id, Person p) {
    boolean personExist = personRepository.existsById(id);

    if (!personExist) throw new NotFoundException("No existe la persona", "not_found_for_update");

    p.setId(id);
    personRepository.save(p);
  }

  public void delete(Integer id) {
    boolean personExist = personRepository.existsById(id);

    if (!personExist) throw new NotFoundException("No existe la persona");

    personRepository.deleteById(id);
  }

  public List<Person> getAll() {
    return personRepository.findAll();
  }

  public List<Person> getByName(String name) {
    return personRepository.findByName("%"+name+"%");
  }
}

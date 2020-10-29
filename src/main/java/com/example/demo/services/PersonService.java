package com.example.demo.services;


import com.example.demo.exceptions.BusinessException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.entities.Person;
import com.example.demo.repositories.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private RedisTemplate<String,String> redisTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @CachePut(value="persons", key = "#p.id")
  public Person create(Person p) {
    boolean personExist = personRepository.existsById(p.getId());

    if (personExist) throw new BusinessException("Ya existe la persona", "person_already_exists");

    List<Person> personsByPhone = personRepository.findByPhoneNumber(p.getPhoneNumber());

    if(!personsByPhone.isEmpty()) throw new BusinessException("Ya existe el telefono", "phone_already_exists");

    personRepository.save(p);

    return p;
  }


  @Cacheable(value="persons", key = "#id")
  public Person find2(Integer id) {
    return personRepository.findById(id).get();
  }

  public Person find(Integer id) {

    String cachedPersonStr = redisTemplate.opsForValue().get("persons:"+id);

    if(cachedPersonStr!=null){
      try {
        return objectMapper.readValue(cachedPersonStr.toString(), Person.class);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }

    Person p = personRepository.findById(id).get();


    try {
      redisTemplate.opsForValue().set("persons:"+id, objectMapper.writeValueAsString(p),30, TimeUnit.SECONDS);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return p;
  }

  //@CachePut(value="persons", key = "#id", unless="#result == null")
  @CacheEvict(value="persons", key = "#id")
  public Person update(Integer id, Person p) {
    boolean personExist = personRepository.existsById(id);

    if (!personExist) throw new NotFoundException("No existe la persona", "not_found_for_update");

    p.setId(id);
    personRepository.save(p);

    return p;
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

package com.example.demo.repository;

import com.example.demo.model.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Person, Long> {

  @Query("select p From Person p where lower(p.name) like :name")
  List<Person> getPersonaByName(String name);
}

package com.example.demo.repositories;

import com.example.demo.model.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

  @Query("SELECT p FROM Person p WHERE lower(p.name) like lower(:name)")
  List<Person> findByName(String name);

  List<Person> findByPhoneNumber(String phoneNumber);
}

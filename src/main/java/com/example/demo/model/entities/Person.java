package com.example.demo.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Table(name = "persons")
public class Person implements Serializable {

  @Id
  private Integer id;

  @NotBlank
  @NotEmpty
  @NotNull
  private String name;

  @Column(name = "last_name")
  @NotEmpty
  @NotNull
  private String lastName;

  @Column(name = "phone_number")
  @Size(min=5, max=10, message = "entre 5 y 10 digitos")
  private String phoneNumber;

  public Person() {
  }

  public Person(Integer id, String name, String lastName, String phoneNumber) {
    this.id = id;
    this.name = name;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}

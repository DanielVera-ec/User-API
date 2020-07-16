package com.tts.UserAPI.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;


@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Length(max = 20, message = "First name can't be more than 20 characters")
  private String firstName;
  
  @Length(min = 2, message = "Last name must be at least 2 characters")
  private String lastName;
  
  @Size(min = 4, max = 20, message = "Full state names only please")
  private String state;
  
  public User() {}

  public User(String firstName, String lastName,
      String state) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.state = state;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Long getId() {
    return id;
  }
}

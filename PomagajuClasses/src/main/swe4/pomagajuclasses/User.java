package swe4.pomagajuclasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private String mail;
  private ArrayList<Donations> donations = new ArrayList<>();

  public User(String first, String last, String name, String pw) {
    this.firstName = first;
    this.lastName = last;
    this.username = name;
    this.password = pw;
  }

  public User(String first, String last, String name, String mail, String pw) {
    this.firstName = first;
    this.lastName = last;
    this.username = name;
    this.mail = mail;
    this.password = pw;
  }

  public User(String mail, String pw) {
    this.username = mail;
    this.mail = mail;
    this.password = pw;
  }

  public String getUsername() {
    return username;
  }

  public String getMail() {
    return mail;
  }

  public String getPassword() {
    return password;
  }

  public String getName() {
    return firstName + " " + lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void addDonation(Donations d) {
    donations.add(d);
  }
}

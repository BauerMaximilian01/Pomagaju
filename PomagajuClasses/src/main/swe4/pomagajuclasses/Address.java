package swe4.pomagajuclasses;

import java.io.Serializable;

public class Address implements Serializable {
  private String street;
  private String zipCode;
  private String loc;

  public Address(String street, String zip, String loc) {
    this.street = street;
    this.zipCode = zip;
    this.loc = loc;
  }

  public String getStreet() {
    return street;
  }

  public String getZipCode() {
    return zipCode;
  }

  public String getLocation() {
    return loc;
  }

  @Override
  public String toString() {
    return street + " " + zipCode + " " + loc;
  }
}

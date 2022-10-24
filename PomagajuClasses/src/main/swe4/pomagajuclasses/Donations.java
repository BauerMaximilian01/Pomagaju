package swe4.pomagajuclasses;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Donations implements Serializable {
  private String email;
  private Goods good;
  private int quant;
  private LocalDateTime timeStamp;
  private String token;

  public Donations(String email, Goods g, int q, LocalDateTime of) {
    this.email = email;
    this.good = g;
    this.quant = q;
    this.timeStamp = of;
  }

  public Donations(String email, Goods g, int q, LocalDateTime of, String token) {
    this.email = email;
    this.good = g;
    this.quant = q;
    this.timeStamp = of;
    this.token = token;
  }

  public String getEmail() {
    return email;
  }

  public String getGood() {
    return good.getIdentifier();
  }

  public String getQuant() {
    return Integer.toString(quant);
  }

  public int getQuantity() {
    return quant;
  }

  public String getTimeStamp() {
    return timeStamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }

  public LocalDate getDate() {
    return LocalDate.from(timeStamp);
  }

  public String getToken() {
    return this.token;
  }
}

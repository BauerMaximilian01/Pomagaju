package swe4.pomagajuclasses;

import java.io.Serializable;

public class Goods implements Serializable {
  private String identifier; // bezeichnung
  private String description;
  private String state;
  private String category;
  private int quantity;

  public Goods(String n, String d, String s, String c, int q) {
    this.identifier = n;
    this.description = d;
    this.state = s;
    this.category = c;
    this.quantity = q;
  }

  public String getIdentifier() {
    return identifier;
  }

  public String getDescription() {
    return description;
  }

  public String getState() {
    return state;
  }

  public String getCategory() {
    return category;
  }

  public int getQuantity() {return quantity;}

  public String getQuantityAsString() {
    return Integer.toString(quantity);
  }

  public void editGood(String ident, String desc, String stat, String cat, int quant) {
    identifier = ident;
    description = desc;
    state = stat;
    category = cat;
    quantity = quant;
  }
}

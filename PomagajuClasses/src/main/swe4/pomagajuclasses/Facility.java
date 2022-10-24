package swe4.pomagajuclasses;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Facility implements Serializable {
  private String name;
  private String country;
  private String district;
  private Address address;
  private String region;
  private String active = "aktiv";

  private final Map<String, Goods> unfilteredGoods = new HashMap<>();
  private final ArrayList<Goods> filteredGoods = new ArrayList<>();
  private final ArrayList<Donations> unfilteredDonations = new ArrayList<>();
  private final ArrayList<Donations> filteredDonations = new ArrayList<>();

  public Facility(String name, String country, String district, Address address, String region) {
    this.name = name;
    this.country = country;
    this.district = district;
    this.address = address;
    this.region = region;
  }

  public Facility(String name, String country, String district, Address address, String region, String active) {
    this.name = name;
    this.country = country;
    this.district = district;
    this.address = address;
    this.region = region;
    this.active = active;
  }

  public Facility(String name, String country, String district, Address address, boolean second, String region, String active) {
    this.name = name;
    this.country = country;
    this.district = district;
    this.address = address;
    this.region = region;
    this.active = active;

    if (second) {
      initGoods2();
      initDonations();
    } else {
      initGoods();
      initDonations2();
    }
  }

  public String getName() {
    return name;
  }

  public String getCountry() {
    return country;
  }

  public String getDistrict() {
    return district;
  }

  public String getAddressAsString() {
    return address.toString();
  }

  public Address getAddress() {return address;}

  public String getRegion() {
    return region;
  }

  public String getActive() {
    return active;
  }

  public boolean addGood(String ident, String desc, String state, String cat, int q) {
    if (!unfilteredGoods.containsKey(ident)) {
      Goods g = new Goods(ident, desc, state, cat, q);
      unfilteredGoods.put(g.getIdentifier(), g);
      updateGoodsList();
      return true;
    }

    return false;
  }

  public boolean addGood(Goods g) {
    if (!unfilteredGoods.containsKey(g.getIdentifier())) {
      unfilteredGoods.put(g.getIdentifier(), g);
      updateGoodsList();
      return true;
    }

    return false;
  }

  public void changeGood(String oldIdent, String newIdent, String desc, String state, String cat, int q) {
    Goods g = unfilteredGoods.get(oldIdent);
    unfilteredGoods.remove(oldIdent);

    g.editGood(newIdent, desc, state, cat, q);
    unfilteredGoods.put(g.getIdentifier(), g);

    updateGoodsList();
  }

  public void changeGood(String oldIdent, Goods newGood) {
    unfilteredGoods.remove(oldIdent);
    unfilteredGoods.put(newGood.getIdentifier(), newGood);

    updateGoodsList();
  }

  public void editFacility(String name, String country, String district, Address address, String active) {
    this.name = name;
    this.country = country;
    this.district = district;
    this.address = address;
    this.active = active;
  }

  public boolean hasNoGoods() {
    return unfilteredGoods.isEmpty();
  }

  public void removeGood(String ident) {
    unfilteredGoods.remove(ident);

    updateGoodsList();
  }

  public List<Goods> getGoodsList() {
    return filteredGoods;
  }

  public void filterGoods(String filter) {
    filteredGoods.clear();

    if (filter.equalsIgnoreCase("alle")) {
      filteredGoods.addAll(unfilteredGoods.values());
    } else {
      for (Goods g : unfilteredGoods.values()) {
        if (g.getCategory().equalsIgnoreCase(filter))
          filteredGoods.add(g);
      }
    }
  }

  public void addDonation(Donations d) {
    unfilteredDonations.add(d);
    updateDonationList();
  }

  public ArrayList<Donations> getDonations() {
    return filteredDonations;
  }

  public void filterDonations(LocalDate date) {
    filteredDonations.clear();

    if (date == null) {
      filteredDonations.addAll(unfilteredDonations);
    } else {
      for (Donations d : unfilteredDonations) {
        if (d.getDate().equals(date)) {
          filteredDonations.add(d);
        }
      }
    }
  }

  private void updateGoodsList() {
    filteredGoods.clear();
    filteredGoods.addAll(unfilteredGoods.values());
  }

  private void updateDonationList() {
    filteredDonations.clear();
    filteredDonations.addAll(unfilteredDonations);
  }

  private void initGoods() {
    Goods g1 = new Goods("Duschgel", "Duschgel egal welcher Duft", "neu", "Hygiene", 250);
    Goods g2 = new Goods("Shampoo", "kein spezielles wie Läuseshampoo", "neu", "Hygiene", 200);
    Goods g3 = new Goods("Kaffee", "", "neu", "Lebensmittel", 400);
    Goods g4 = new Goods("T-Shirts", "einfache T-Shirts Größe egal", "gebraucht", "Kleidung", 1300);

    unfilteredGoods.put(g1.getIdentifier(), g1);
    unfilteredGoods.put(g2.getIdentifier(), g2);
    unfilteredGoods.put(g3.getIdentifier(), g3);
    unfilteredGoods.put(g4.getIdentifier(), g4);
    updateGoodsList();
    System.out.println("initalized Goods");
    System.out.println(unfilteredGoods);
  }

  private void initGoods2() {
    Goods g1 = new Goods("Duschgel", "Duschgel egal welcher Duft", "neu", "Hygiene", 250);
    Goods g2 = new Goods("Kaffee", "", "neu", "Lebensmittel", 400);
    Goods g3 = new Goods("T-Shirts", "einfache T-Shirts Größe egal", "gebraucht", "Kleidung", 1300);

    unfilteredGoods.put(g1.getIdentifier(), g1);
    unfilteredGoods.put(g2.getIdentifier(), g2);
    unfilteredGoods.put(g3.getIdentifier(), g3);
    updateGoodsList();
    System.out.println("initalized Goods2");
    System.out.println(unfilteredGoods);
  }

  private void initDonations() {
    LocalDateTime date1 = LocalDateTime.of(2022, Month.JULY, 10, 12, 0);
    LocalDateTime date2 = LocalDateTime.of(2022, Month.MAY, 15, 13, 30);
    LocalDateTime date3 = LocalDateTime.of(2022, Month.DECEMBER, 24, 23, 59);

    Donations d1 = new Donations("guttuher.asdf@gmail.com", unfilteredGoods.get("Duschgel"), 10, date1);
    Donations d2 = new Donations("123@gmail.com", unfilteredGoods.get("Kaffee"), 30, date2);
    Donations d3 = new Donations("greni@gmx.com", unfilteredGoods.get("T-Shirts"), 100, date3);

    unfilteredDonations.add(d1);
    unfilteredDonations.add(d2);
    unfilteredDonations.add(d3);

    updateDonationList();
  }

  private void initDonations2() {
    LocalDateTime date1 = LocalDateTime.of(2022, Month.JULY, 10, 12, 0);
    LocalDateTime date2 = LocalDateTime.now();

    Donations d1 = new Donations("guttuher.asdf@gmail.com", unfilteredGoods.get("Duschgel"), 10, date1);
    Donations d2 = new Donations("123@gmail.com", unfilteredGoods.get("Kaffee"), 30, date2);

    unfilteredDonations.add(d1);
    unfilteredDonations.add(d2);

    updateDonationList();
  }
}
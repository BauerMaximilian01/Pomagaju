package swe4.rmi;

import swe4.pomagajuclasses.Address;
import swe4.pomagajuclasses.Donations;
import swe4.pomagajuclasses.Facility;
import swe4.pomagajuclasses.Goods;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FacilitiesDatabase {
  private static final Map<String, Facility> facilities = new HashMap<>();
  private static final ArrayList<Facility> facilityArrayList = new ArrayList<>();

  FacilitiesDatabase() {

  }

  public static boolean addFacility(Facility f) {
    if (!facilities.containsKey(f.getName())) {
      facilities.put(f.getName(), f);
      facilityArrayList.add(f);
      return true;
    }

    return false;
  }

  public static String changeFacility(String oldName, Facility f, boolean activeness) {
    if (facilities.containsKey(oldName)) {
      Facility changedFacility = facilities.get(oldName);
      facilities.remove(oldName);

      changedFacility.editFacility(f.getName(), f.getCountry(), f.getDistrict(), f.getAddress(), activeness ? "aktiv" : "inaktiv");

      facilities.put(changedFacility.getName(), changedFacility);

      return f.getName();
    }

    return null;
  }

  public static void removeFacility(String name) {
    facilities.remove(name);
    facilityArrayList.clear();
    facilityArrayList.addAll(facilities.values());
  }

  public static Facility getFacility(String name) {
    return facilities.get(name);
  }

  public static ArrayList<Facility> getFacilities() {
    return facilityArrayList;
  }

  public static ArrayList<Facility> getFacilitiesFiltered(String s) {
    facilityArrayList.clear();

    if (s.equalsIgnoreCase("alle")) {
      facilityArrayList.addAll(facilities.values());
    } else {
      for (Facility f : facilities.values()) {
        if (f.getDistrict().equalsIgnoreCase(s))
          facilityArrayList.add(f);
      }
    }

    return facilityArrayList;
  }

  public static void filterFacilities(String s) {
    facilityArrayList.clear();

    if (s.equalsIgnoreCase("alle")) {
      facilityArrayList.addAll(facilities.values());
    } else {
      for (Facility f : facilities.values()) {
        if (f.getDistrict().equalsIgnoreCase(s))
          facilityArrayList.add(f);
      }
    }
  }

  public static void filterFacilitiesOnCountry(String s) {
    facilityArrayList.clear();

    if (s.equalsIgnoreCase("alle")) {
      facilityArrayList.addAll(facilities.values());
    } else {
      for (Facility f : facilities.values()) {
        if (f.getCountry().equalsIgnoreCase(s))
          facilityArrayList.add(f);
      }
    }
  }

  public static void filterFacilitiesOnGoods(String s) {
    facilityArrayList.clear();

    for (Facility f : facilities.values()) {
      for (Goods g : f.getGoodsList()) {
        if (g.getIdentifier().equalsIgnoreCase(s))
          facilityArrayList.add(f);
      }
    }
  }

  public static void filterGoods(String s) {
    if (s.equalsIgnoreCase("alle")) {
      for (Facility f : facilities.values()) {
        f.filterGoods("alle");
      }
    } else {
      for (Facility f : facilities.values()) {
        f.filterGoods(s);
      }
    }
  }

  public static void filterDonations(Facility f, LocalDate date) {
    facilities.get(f.getName()).filterDonations(date);
  }

  public static void filterGoods(Facility f, String s) {
    if (f == null) {
      filterGoods(s);
    } else {
      facilities.get(f.getName()).filterGoods(s);
    }
  }

  public static void removeGood(Facility f, Goods g) {
    facilities.get(f.getName()).removeGood(g.getIdentifier());
  }

  public static void changeGood(Facility f, String oldIdent, Goods newGood) {
    facilities.get(f.getName()).changeGood(oldIdent, newGood);
  }

  public static boolean addGood(Facility f, Goods g) {
    return facilities.get(f.getName()).addGood(g);
  }

  public static void addDonationToFacility(Facility f, Donations d) {
    facilities.get(f.getName()).addDonation(d);
  }

  public static void initializeFacilities() {
    Facility f1 = new Facility("Barmherzige Brüder Linz", "Oberösterreich", "Linz", new Address("Rudigierstraße 21", "4020", "Linz"), true,"Volyn","aktiv");
    Facility f2 = new Facility("Annahmestelle Salzburg", "Salzburg", "Salzburg", new Address("Getreidegasse 3", "5020", "Salzburg"), false, "Kharkiv", "inaktiv");
    Facility f3 = new Facility("Maria hilft!", "Wien", "6. Bezirk/Mariahilf", new Address("Mariahilferstraße 23", "1060", "6. Bezirk/Mariahilf"), true, "Donetsk", "aktiv");

    facilities.put(f1.getName(), f1);
    facilities.put(f2.getName(), f2);
    facilities.put(f3.getName(), f3);

    facilityArrayList.addAll(facilities.values());
  }
}

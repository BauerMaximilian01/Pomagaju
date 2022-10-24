package swe4.database;

import swe4.pomagajuclasses.Donations;
import swe4.pomagajuclasses.Facility;
import swe4.pomagajuclasses.Goods;
import swe4.pomagajuclasses.User;

import java.util.Collection;

public interface PomagajuDAO {
  String changeFacility(String oldName, Facility f, boolean activeness);
  void removeFacility(String name);
  Collection<Facility> getFacilitiesWhere(String whereClause);
  boolean addFacility(Facility f);
  Facility getFacility(String name);
  Collection<Facility> getFacilities();


  Collection<Goods> getGoodsWhere(String whereClause);
  void changeGood(Facility f, String oldIdent, Goods newGood);
  void removeGood(Facility f, Goods g);
  boolean addGood(Facility f, Goods g);

  Collection<Donations> getDonationsWhere(String whereClause);
  boolean addDonation(Facility f, Donations d);

  Collection<String> getCategories();

  boolean addUser(User u);
  User getUser(String name);
  boolean addUser(String mail, String pw);
}

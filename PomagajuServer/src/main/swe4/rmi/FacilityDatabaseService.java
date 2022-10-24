package swe4.rmi;

import swe4.pomagajuclasses.Donations;
import swe4.pomagajuclasses.Facility;
import swe4.pomagajuclasses.Goods;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface FacilityDatabaseService extends Remote {
  boolean addFacility(Facility f) throws RemoteException;
  String changeFacility(String oldName, Facility f, boolean activeness) throws RemoteException;
  void removeFacility(String name) throws RemoteException;
  Facility getFacility(String name) throws RemoteException;
  ArrayList<Facility> getFacilities() throws RemoteException;
  ArrayList<Facility> getFacilitiesWhere(String whereClause) throws RemoteException;

  void changeGood(Facility f, String oldIdent, Goods newGood) throws RemoteException;
  void removeGood(Facility f, Goods g) throws RemoteException;
  //void filterGoods(Facility f, String filter) throws RemoteException;
  boolean addGood(Facility f, Goods g) throws RemoteException;
  ArrayList<Goods> getGoodsWhere(String whereClause) throws RemoteException;

  ArrayList<Donations> getDonationsWhere(String whereClause) throws RemoteException;
}

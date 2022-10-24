package swe4.rmi;

import swe4.database.PomagajuDAO;
import swe4.pomagajuclasses.Donations;
import swe4.pomagajuclasses.Facility;
import swe4.pomagajuclasses.Goods;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class FacilityDatabaseServiceImpl implements FacilityDatabaseService {
  private PomagajuDAO pomagajuDAO = null;

  public FacilityDatabaseServiceImpl(PomagajuDAO pomagajuDAO) {
    this.pomagajuDAO = pomagajuDAO;
  }

  @Override
  public synchronized String changeFacility(String oldName, Facility f, boolean activeness) throws RemoteException {
    return pomagajuDAO.changeFacility(oldName, f, activeness);
  }

  @Override
  public synchronized void removeFacility(String name) throws RemoteException {
    pomagajuDAO.removeFacility(name);
  }

  @Override
  public synchronized boolean addFacility(Facility f) throws RemoteException {
    return pomagajuDAO.addFacility(f);
  }

  @Override
  public synchronized Facility getFacility(String name) throws RemoteException {
    return pomagajuDAO.getFacility(name);
  }

  @Override
  public synchronized ArrayList<Facility> getFacilities() throws RemoteException {
    return (ArrayList<Facility>) pomagajuDAO.getFacilities();
  }

  @Override
  public synchronized ArrayList<Facility> getFacilitiesWhere(String whereClause) throws RemoteException {
    return (ArrayList<Facility>) pomagajuDAO.getFacilitiesWhere(whereClause);
  }

  @Override
  public synchronized void changeGood(Facility f, String oldIdent, Goods newGood) throws RemoteException {
    pomagajuDAO.changeGood(f, oldIdent, newGood);
  }

  @Override
  public synchronized void removeGood(Facility f, Goods g) throws RemoteException {
    pomagajuDAO.removeGood(f, g);
  }

  @Override
  public synchronized boolean addGood(Facility f, Goods g) throws RemoteException {
    return pomagajuDAO.addGood(f, g);
  }

  @Override
  public ArrayList<Goods> getGoodsWhere(String whereClause) throws RemoteException {
    return (ArrayList<Goods>) pomagajuDAO.getGoodsWhere(whereClause);
  }

  @Override
  public ArrayList<Donations> getDonationsWhere(String whereClause) throws RemoteException {
    return (ArrayList<Donations>) pomagajuDAO.getDonationsWhere(whereClause);
  }
}

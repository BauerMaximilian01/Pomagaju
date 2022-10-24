package swe4.rmi;

import swe4.database.PomagajuDAO;
import swe4.pomagajuclasses.Donations;
import swe4.pomagajuclasses.Facility;
import swe4.pomagajuclasses.User;

import java.rmi.RemoteException;

public class UserDatabaseServiceImpl implements UserDatabaseService{
  private PomagajuDAO pomagajuDAO = null;

  public UserDatabaseServiceImpl(PomagajuDAO pomagajuDAO) {
    this.pomagajuDAO = pomagajuDAO;
  }

  @Override
  public synchronized boolean addUser(User u) throws RemoteException {
    return pomagajuDAO.addUser(u);
  }

  @Override
  public synchronized User getUser(String name) throws RemoteException {
    return pomagajuDAO.getUser(name);
  }

  @Override
  public synchronized boolean addUser(String mail, String pw) throws RemoteException {
    System.out.println(mail + " " + pw);
    return pomagajuDAO.addUser(mail, pw);
  }

  @Override
  public synchronized boolean addDonation(Facility f, Donations d) throws RemoteException {
    return pomagajuDAO.addDonation(f, d);
  }

  @Override
  public synchronized boolean validateInput(String name, String pw) throws RemoteException {
    User u = getUser(name);

    if (u.getUsername().equals(name) && u.getPassword().equals(pw)) {
      return true;
    }

    return false;
  }

  @Override
  public synchronized boolean validateInputMail(String mail, String pw) throws RemoteException {
    return validateInput(mail, pw);
  }
}

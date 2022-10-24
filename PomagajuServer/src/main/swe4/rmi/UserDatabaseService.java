package swe4.rmi;

import swe4.pomagajuclasses.Donations;
import swe4.pomagajuclasses.Facility;
import swe4.pomagajuclasses.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserDatabaseService extends Remote {
  boolean addUser(User u) throws RemoteException;
  boolean addUser(String mail, String pw) throws RemoteException;
  User getUser(String name) throws RemoteException;
  boolean addDonation(Facility f, Donations d) throws RemoteException;
  boolean validateInput(String name, String pw) throws RemoteException;
  boolean validateInputMail(String mail, String pw) throws RemoteException;
}

package swe4.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface GoodsDatabaseService extends Remote {
  Collection<String> getCategories() throws RemoteException;
}

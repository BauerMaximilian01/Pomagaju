package swe4.rmi;

import swe4.database.PomagajuDAO;

import java.rmi.RemoteException;
import java.util.Collection;

public class GoodsDatabaseServiceImpl implements GoodsDatabaseService {
  private PomagajuDAO pomagajuDAO = null;

  public GoodsDatabaseServiceImpl(PomagajuDAO pomagajuDAO) {
    this.pomagajuDAO = pomagajuDAO;
  }

  @Override
  public synchronized Collection<String> getCategories() throws RemoteException {
    return pomagajuDAO.getCategories();
  }
}

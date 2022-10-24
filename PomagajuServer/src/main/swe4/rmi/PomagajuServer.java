package swe4.rmi;

import swe4.Utility;
import swe4.database.PomagajuDAOImpl;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class PomagajuServer {
  static final private String connectionString = "jdbc:mysql://localhost/PomagajuDB?autoReconnect=true&useSSL=false";
  static final private String pwd = "root";
  static final private String user = "root";

  static void launchRMIRegistryAndService (String[] args, Remote serviceObject, String serviceName) throws IOException {
    launchRMIRegistryAndService (args, serviceObject, serviceName, 0);   // 0 -> let UnicastRemoteObject.exportObject choose a servicePort
  }

  static void launchRMIRegistryAndService (String[] args, Remote serviceObject, String serviceName, int servicePort) throws IOException {
    int registryPort = Utility.parsePort(args, Registry.REGISTRY_PORT);
    String registryURL = Utility.getRMIRegistryURL("localhost", registryPort, serviceName);

    try {
      LocateRegistry.createRegistry(registryPort);
      System.out.println("Server: localhost, port " + servicePort + ": RMI Registry created.");
    } catch (RemoteException x) {
      System.out.println("Server: localhost, port " + servicePort + ": RMI Registry already running.");
    }

    Remote serviceStub = UnicastRemoteObject.exportObject(serviceObject, registryPort);

    System.out.println(
        "Server: Rebinding RMI Service " + registryURL + " to an Object of type " + serviceObject.getClass().getName() +
            " on Port " + servicePort + " ..."
    );

    Naming.rebind(registryURL, serviceStub);


    System.out.println("Server: is up and running ...");
  }

  public static void main(String[] args) {
    try {
      launchRMIRegistryAndService(args, new FacilityDatabaseServiceImpl(new PomagajuDAOImpl(connectionString, user, pwd)), "FacilityDatabaseService");
      launchRMIRegistryAndService(args, new UserDatabaseServiceImpl(new PomagajuDAOImpl(connectionString, user, pwd)), "UserDatabaseService");
      launchRMIRegistryAndService(args, new GoodsDatabaseServiceImpl(new PomagajuDAOImpl(connectionString, user, pwd)), "GoodsDatabaseService");
    } catch (Exception x) {
      x.printStackTrace();
    }
  }
}

package swe4.gui.adminapp;

import swe4.Utility;
import swe4.pomagajuclasses.Donations;
import swe4.pomagajuclasses.Facility;
import swe4.pomagajuclasses.Goods;
import swe4.pomagajuclasses.User;
import swe4.rmi.FacilityDatabaseService;
import swe4.rmi.GoodsDatabaseService;
import swe4.rmi.UserDatabaseService;

import java.rmi.Naming;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class Client {
  private static FacilityDatabaseService facilityService = null;
  private static UserDatabaseService userService = null;
  private static GoodsDatabaseService goodsService = null;

  public static void initService() {
    try {
      // Facility Service
      String serviceName = "FacilityDatabaseService";
      String registryURL = Utility.getRMIRegistryURL(new String[] {}, serviceName);

      System.out.println("Client: Looking for RMI Service " + registryURL + " ...");

      facilityService = (FacilityDatabaseService) Naming.lookup(registryURL); // remote reference

      System.out.println("Client: Type of Server: " + facilityService.getClass().getName());

      // User Service
      serviceName = "UserDatabaseService";
      registryURL = Utility.getRMIRegistryURL(new String[] {}, serviceName);

      System.out.println("Client: Looking for RMI Service " + registryURL + " ...");

      userService = (UserDatabaseService) Naming.lookup(registryURL); // remote reference

      System.out.println("Client: Type of Server: " + facilityService.getClass().getName());

      // Goods Service
      serviceName = "GoodsDatabaseService";
      registryURL = Utility.getRMIRegistryURL(new String[] {}, serviceName);

      System.out.println("Client: Looking for RMI Service " + registryURL + " ...");

      goodsService = (GoodsDatabaseService) Naming.lookup(registryURL); // remote reference

      System.out.println("Client: Type of Server: " + facilityService.getClass().getName());

    } catch (Exception x) {
      x.printStackTrace ();
    }
  }

  /*--------------------*/
  /*  Facility Service  */
  /*--------------------*/

  public static String changeFacility(String oldName, Facility f, boolean activeness) {
    try {
      return facilityService.changeFacility(oldName, f, activeness);
    } catch (Exception x) {
      x.printStackTrace();
      return null;
    }
  }

  public static void removeFacility(String name) {
    try {
      facilityService.removeFacility(name);
    } catch (Exception x) {
      x.printStackTrace();
    }
  }

  public static boolean addFacility(Facility f) {
    try {
      return facilityService.addFacility(f);
    } catch (Exception x) {
      x.printStackTrace();
      return false;
    }
  }

  public static Facility getFacility(String name) {
    try {
      return facilityService.getFacility(name);
    } catch (Exception x) {
      x.printStackTrace();
      return null;
    }
  }

  public static ArrayList<Facility> getFacilities() {
    try {
      return facilityService.getFacilities();
    } catch (Exception x) {
      x.printStackTrace();
      return null;
    }
  }

  public static ArrayList<Facility> getFacilitiesWhere(String whereClause) {
    try {
      return facilityService.getFacilitiesWhere(whereClause);
    } catch (Exception x) {
      x.printStackTrace();
      return null;
    }
  }

  /*--------------------*/
  /*    User Service    */
  /*--------------------*/

  public static boolean addUser(User u) {
    try {
      System.out.println(u.getLastName());
      return userService.addUser(u);
    } catch (Exception x) {
      x.printStackTrace();
      return false;
    }
  }

  public static User getUser(String name) {
    try {
      return userService.getUser(name);
    } catch (Exception x) {
      x.printStackTrace();
      return null;
    }
  }

  public static ArrayList<Donations> getDonationsWhere(String whereClause) {
    try {
      return facilityService.getDonationsWhere(whereClause);
    } catch (Exception x) {
      x.printStackTrace();
      return null;
    }
  }

  public static boolean validateInput(String name, String pw) {
    try {
      return userService.validateInput(name, pw);
    } catch (Exception x) {
      x.printStackTrace();
      return false;
    }
  }

  /*--------------------*/
  /*    Goods Service   */
  /*--------------------*/
  public static Collection<String> getCategories() {
    try {
      return goodsService.getCategories();
    } catch (Exception x) {
      x.printStackTrace();
      return null;
    }
  }

  public static void changeGood(Facility f, String oldIdent, Goods newGood) {
    try {
      facilityService.changeGood(f, oldIdent, newGood);
    } catch (Exception x) {
      x.printStackTrace();
    }
  }

  public static void removeGood(Facility f, Goods g) {
    try {
      facilityService.removeGood(f, g);
    } catch (Exception x) {
      x.printStackTrace();
    }
  }

  public static boolean addGood(Facility f, Goods g) {
    try {
      return facilityService.addGood(f, g);
    } catch (Exception x) {
      x.printStackTrace();
      return false;
    }
  }

  public static ArrayList<Goods> getGoodsWhere(String whereClause) {
    try {
      return facilityService.getGoodsWhere(whereClause);
    } catch (Exception x) {
      x.printStackTrace();
      return null;
    }
  }
}

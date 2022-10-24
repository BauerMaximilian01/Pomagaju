//package swe4.rmi;
//
//import swe4.Utility;
//import swe4.pomagajuclasses.Donations;
//import swe4.pomagajuclasses.Facility;
//import swe4.pomagajuclasses.Goods;
//import swe4.pomagajuclasses.User;
//
//import java.rmi.Naming;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Collection;
//
//public class Client {
//  private static FacilityDatabaseService facilityService = null;
//  private static UserDatabaseService userService = null;
//  private static GoodsDatabaseService goodsService = null;
//
//  public Client() {
//    try {
//      // Facility Service
//      String serviceName = "FacilityDatabaseService";
//      String registryURL = Utility.getRMIRegistryURL(new String[] {}, serviceName);
//
//      System.out.println("Client: Looking for RMI Service " + registryURL + " ...");
//
//      facilityService = (FacilityDatabaseService) Naming.lookup(registryURL); // remote reference
//
//      System.out.println("Client: Type of Server: " + facilityService.getClass().getName());
//
//      // User Service
//      serviceName = "UserDatabaseService";
//      registryURL = Utility.getRMIRegistryURL(new String[] {}, serviceName);
//
//      System.out.println("Client: Looking for RMI Service " + registryURL + " ...");
//
//      userService = (UserDatabaseService) Naming.lookup(registryURL); // remote reference
//
//      System.out.println("Client: Type of Server: " + facilityService.getClass().getName());
//
//      // Goods Service
//      serviceName = "GoodsDatabaseService";
//      registryURL = Utility.getRMIRegistryURL(new String[] {}, serviceName);
//
//      System.out.println("Client: Looking for RMI Service " + registryURL + " ...");
//
//      goodsService = (GoodsDatabaseService) Naming.lookup(registryURL); // remote reference
//
//      System.out.println("Client: Type of Server: " + facilityService.getClass().getName());
//
//    } catch (Exception x) {
//      x.printStackTrace ();
//    }
//  }
//
//  /*--------------------*/
//  /*  Facility Service  */
//  /*--------------------*/
//
//  public static String changeFacility(String oldName, Facility f, boolean activeness) {
//    try {
//      return facilityService.changeFacility(oldName, f, activeness);
//    } catch (Exception x) {
//      x.printStackTrace();
//      return null;
//    }
//  }
//
//  public static void removeFacility(String name) {
//    try {
//      facilityService.removeFacility(name);
//    } catch (Exception x) {
//      x.printStackTrace();
//    }
//  }
//
//  public static void filterFacilities(String s) {
//    try {
//      facilityService.filterFacilities(s);
//    } catch (Exception x) {
//      x.printStackTrace();
//    }
//  }
//
//  public static void filterFacilitiesOnCountry(String s) {
//    try {
//      facilityService.filterFacilitiesOnCountry(s);
//    } catch (Exception x) {
//      x.printStackTrace();
//    }
//  }
//
//  public static void filterFacilitiesOnGoods(String s) {
//    try {
//      facilityService.filterFacilitiesOnGoods(s);
//    } catch (Exception x) {
//      x.printStackTrace();
//    }
//  }
//
//  public static void filterGoods(String s) {
//    try {
//      facilityService.filterGoods(s);
//    } catch (Exception x) {
//      x.printStackTrace();
//    }
//  }
//
//  public static void filterDonations(Facility f, LocalDate date) {
//    try {
//      facilityService.filterDonations(f, date);
//    } catch (Exception x) {
//      x.printStackTrace();
//    }
//  }
//
//  public static boolean addFacility(Facility f) {
//    try {
//      return facilityService.addFacility(f);
//    } catch (Exception x) {
//      x.printStackTrace();
//      return false;
//    }
//  }
//
//  public static Facility getFacility(String name) {
//    try {
//      return facilityService.getFacility(name);
//    } catch (Exception x) {
//      x.printStackTrace();
//      return null;
//    }
//  }
//
//  public static ArrayList<Facility> getFacilities() {
//
//    try {
//      return facilityService.getFacilities();
//    } catch (Exception x) {
//      x.printStackTrace();
//      return null;
//    }
//  }
//
//  /*--------------------*/
//  /*    User Service    */
//  /*--------------------*/
//
//  public static boolean addUser(User u) {
//    try {
//      return userService.addUser(u);
//    } catch (Exception x) {
//      x.printStackTrace();
//      return false;
//    }
//  }
//
//  public static User getUser(String name) {
//    try {
//      return userService.getUser(name);
//    } catch (Exception x) {
//      x.printStackTrace();
//      return null;
//    }
//  }
//
//  public static boolean addUser(String mail, String pw) {
//    try {
//      return userService.addUser(mail, pw);
//    } catch (Exception x) {
//      x.printStackTrace();
//      return false;
//    }
//  }
//
//  public static boolean addDonation(Donations d) {
//    try {
//      return userService.addDonation(d);
//    } catch (Exception x) {
//      x.printStackTrace();
//      return false;
//    }
//  }
//
//  public static boolean validateInput(String name, String pw) {
//    try {
//      return userService.validateInput(name, pw);
//    } catch (Exception x) {
//      x.printStackTrace();
//      return false;
//    }
//  }
//
//  public static boolean validateInputMail(String mail, String pw) {
//    try {
//      return userService.validateInputMail(mail, pw);
//    } catch (Exception x) {
//      x.printStackTrace();
//      return false;
//    }
//  }
//
//  /*--------------------*/
//  /*    Goods Service   */
//  /*--------------------*/
//  public static Collection<String> getCategories() {
//    try {
//      return goodsService.getCategories();
//    } catch (Exception x) {
//      x.printStackTrace();
//      return null;
//    }
//  }
//
//  public static void changeGood(Facility f, String oldIdent, Goods newGood) {
//    try {
//      facilityService.changeGood(f, oldIdent, newGood);
//    } catch (Exception x) {
//      x.printStackTrace();
//    }
//  }
//
//  public static void removeGood(Facility f, Goods g) {
//    try {
//      facilityService.removeGood(f, g);
//    } catch (Exception x) {
//      x.printStackTrace();
//    }
//  }
//
//  public static boolean addGood(Facility f, Goods g) {
//    try {
//      return facilityService.addGood(f, g);
//    } catch (Exception x) {
//      x.printStackTrace();
//      return false;
//    }
//  }
//
//  public static void filterGoods(Facility f, String filter) {
//    try {
//      facilityService.filterGoods(f, filter);
//    } catch (Exception x) {
//      x.printStackTrace();
//    }
//  }
//}

package swe4.rmi;

import swe4.pomagajuclasses.Donations;
import swe4.pomagajuclasses.User;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
  private static Map<String, User> users = new HashMap<>();

  public static boolean addUser(User u) {
    if (!users.containsKey(u.getUsername())) {
      users.put(u.getUsername(), u);
      return true;
    }

    return false;
  }

  public static boolean addUser(String mail, String pw) {
    if (!users.containsKey(mail)) {
      users.put(mail, new User(mail, pw));
      return true;
    }

    return false;
  }

  public static boolean addUser(String first, String last, String username, String pw) {
    if (!users.containsKey(username)) {
      users.put(username, new User(first, last, username, pw));
      return true;
    }

    return false;
  }

  public static boolean addDonation(Donations d) {
    if (!users.containsKey(d.getEmail())) {
      return false;
    }

    users.get(d.getEmail()).addDonation(d);

    return true;
  }

  public static void initializeUser() {
    User u1 = new User("Gertrude", "Werner", "WerniGert69", "IchLiebeMeinenEnkel");
    User u2 = new User("Manfred", "Zuensler", "Zuensli53", "BuchsBaumZuensler");
    User u3 = new User("Siglinde", "Mauerbluemchen", "Bluemchen13", "IchMagMauern");
    User u4 = new User("test", "user", "test", "test");

    /* Mobile users */
    User u5 = new User("example@gmail.com", "1234");
    User u6 = new User("help@gmx.com", "test123");

    addUser(u1);
    addUser(u2);
    addUser(u3);
    addUser(u4);

    addUser(u5);
    addUser(u6);
  }

  public static boolean validateInput(String name, String pw) {
    if (users.containsKey(name)) {
      if (users.get(name).getUsername().equals(name) && users.get(name).getPassword().equals(pw))
        return true;
    }

    return false;
  }

  public static boolean validateInputMail(String mail, String pw) {
    if (users.containsKey(mail)) {
      if (users.get(mail).getMail().equals(mail) && users.get(mail).getPassword().equals(pw))
        return true;
    }

    return false;
  }

  public static User getUser(String name) {
    return users.get(name);
  }
}

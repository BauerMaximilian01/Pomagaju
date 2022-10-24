//package unitTests;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.junit.jupiter.params.provider.ValueSource;
//
//import swe4.pomagajuclasses.Donations;
//import swe4.pomagajuclasses.Goods;
//import swe4.pomagajuclasses.User;
//import swe4.rmi.PomagajuServer;
//import swe4.rmi.UserDatabaseServiceImpl;
//
//import java.rmi.RemoteException;
//import java.time.LocalDateTime;
//import java.time.Month;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class UserDBServerTests {
//  private static UserDatabaseServiceImpl userService = null;
//
//  @DisplayName("Start server and userService for testing before all Tests")
//  @BeforeAll
//  static void startServerBeforeTesting() {
//    PomagajuServer.main(new String[] {});
//    userService = new UserDatabaseServiceImpl();
//  }
//
//  @DisplayName("UserDatabase.addUser returns true when non stored user added with names")
//  @ParameterizedTest
//  @MethodSource("generateUsersNames")
//  void addUserReturnsTrueWhenNotStoredUserAddedWithNames(User u) throws RemoteException {
//    assertTrue(userService.addUser(u));
//  }
//
//  static Stream<User> generateUsersNames() {
//    User u1 = new User("Max", "Bauer", "maxl", "1234");
//    User u2 = new User("example1@gmail.com", "543");
//    User u3 = new User("testUser", "userTest", "testing", "6666");
//
//    return Stream.of(u1, u2, u3);
//  }
//
//  @DisplayName("UserDatabase.addUser returns true when non stored user added with mails")
//  @ParameterizedTest
//  @MethodSource("generateUsersMail")
//  void addUserReturnsTrueWhenNotStoredUserAddedWithMails(User u) throws RemoteException {
//    assertTrue(userService.addUser(u));
//  }
//
//  static Stream<User> generateUsersMail() {
//    User u1 = new User("test@gmail.com", "testing");
//    User u2 = new User("example2@gmail.com", "543");
//    User u3 = new User("testUser@gmx.com", "6666");
//
//    return Stream.of(u1, u2, u3);
//  }
//
//  @DisplayName("UserDatabase.addUser returns false when stored User added")
//  @ParameterizedTest
//  @MethodSource("generateStoredUsers")
//  void addUserReturnsTrueWhenStoredUserAdded(User u) throws RemoteException {
//    assertFalse(userService.addUser(u));
//  }
//
//  static Stream<User> generateStoredUsers() {
//    User u1 = new User("Gertrude", "Werner", "WerniGert69", "1234");
//    User u2 = new User("example@gmail.com", "543");
//    User u3 = new User("test", "test", "test", "test");
//
//    return Stream.of(u1, u2, u3);
//  }
//
//  @DisplayName("userService.getUser returns valid User")
//  @ParameterizedTest(name = "names = {0}")
//  @ValueSource(strings = {"WerniGert69", "test", "Bluemchen13"})
//  void getUserReturnsValidUser(String name) throws RemoteException {
//    assertNotNull(userService.getUser(name));
//  }
//
//  @DisplayName("userService.getUser returns invalid User")
//  @ParameterizedTest(name = "names = {0}")
//  @ValueSource(strings = {"Werni", "Kurti", "bluemi"})
//  void getUserReturnsInvalidUser(String name) throws RemoteException {
//    assertNull(userService.getUser(name));
//  }
//
//  @DisplayName("userService.addDonation returns true when added a not stored donation")
//  @ParameterizedTest
//  @MethodSource("generateNotStoredDonations")
//  void addDonationReturnsTrueWhenAddedNotStoredDonation(Donations d) throws RemoteException {
//    assertTrue(userService.addDonation(d));
//  }
//
//  static Stream<Donations> generateNotStoredDonations() {
//    Donations d1 = new Donations("example@gmail.com", new Goods("testGood", "", "neu", "Hygiene", 4), 2, LocalDateTime.of(2022, Month.JULY, 14, 13, 00));
//    Donations d2 = new Donations("example@gmail.com", new Goods("testGood2", "", "gebraucht", "Hygiene", 10), 3, LocalDateTime.of(2022, Month.JULY, 12, 10, 00));
//    Donations d3 = new Donations("example@gmail.com", new Goods("Kaffee", "", "neu", "Lebensmittel", 400), 100, LocalDateTime.of(2022, Month.AUGUST, 14, 13, 00));
//
//    return Stream.of(d1, d2, d3);
//  }
//
//  @DisplayName("userService.validateInput returns true when valid credentials given")
//  @Test
//  void validateInputReturnsTrueWhenValidCredentialsGiven() throws RemoteException {
//    assertTrue(userService.validateInput("WerniGert69", "IchLiebeMeinenEnkel"));
//    assertTrue(userService.validateInput("test", "test"));
//    assertTrue(userService.validateInput("Zuensli53", "BuchsBaumZuensler"));
//  }
//
//  @DisplayName("userService.validateInput returns false when invalid credentials given")
//  @Test
//  void validateInputReturnsFalseWhenValidCredentialsGiven() throws RemoteException {
//    assertFalse(userService.validateInput("Werni", "IchLiebeMeinenEnkel"));
//    assertFalse(userService.validateInput("test", "test12"));
//    assertFalse(userService.validateInput("Zuensli13", "BuchsBaumZuensler"));
//  }
//
//  @DisplayName("userService.validateInput returns true when valid credentials given email")
//  @Test
//  void validateInputReturnsTrueWhenValidCredentialsGivenMail() throws RemoteException {
//    assertTrue(userService.validateInputMail("example@gmail.com", "1234"));
//    assertTrue(userService.validateInputMail("help@gmx.com", "test123"));
//  }
//
//  @DisplayName("userService.validateInput returns false when invalid credentials given email")
//  @Test
//  void validateInputReturnsFalseWhenInvalidCredentialsGivenMail() throws RemoteException {
//    assertFalse(userService.validateInputMail("example55@gmail.com", "1234"));
//    assertFalse(userService.validateInputMail("help@gmx.com", "tes34"));
//  }
//}

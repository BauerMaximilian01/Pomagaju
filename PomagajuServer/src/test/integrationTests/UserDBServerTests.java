//package integrationTests;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.junit.jupiter.params.provider.ValueSource;
//import swe4.pomagajuclasses.Donations;
//import swe4.pomagajuclasses.Goods;
//import swe4.pomagajuclasses.User;
//import swe4.rmi.Client;
//import swe4.rmi.PomagajuServer;
//
//import java.time.LocalDateTime;
//import java.time.Month;
//import java.util.Arrays;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class UserDBServerTests {
//  @DisplayName("Start server and Client for testing before all Tests")
//  @BeforeAll
//  static void startServerBeforeTesting() {
//    PomagajuServer.main(new String[] {});
//    new Client();
//  }
//
//  @DisplayName("UserDatabase.addUser returns true when non stored user added with names")
//  @ParameterizedTest
//  @MethodSource("generateUsersNames")
//  void addUserReturnsTrueWhenNotStoredUserAddedWithNames(User u) {
//    assertTrue(Client.addUser(u));
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
//  void addUserReturnsTrueWhenNotStoredUserAddedWithMails(User u) {
//    assertTrue(Client.addUser(u));
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
//  void addUserReturnsTrueWhenStoredUserAdded(User u) {
//    assertFalse(Client.addUser(u));
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
//  @DisplayName("Client.getUser returns valid User")
//  @ParameterizedTest(name = "names = {0}")
//  @ValueSource(strings = {"WerniGert69", "test", "Bluemchen13"})
//  void getUserReturnsValidUser(String name) {
//    assertNotNull(Client.getUser(name));
//  }
//
//  @DisplayName("Client.getUser returns invalid User")
//  @ParameterizedTest(name = "names = {0}")
//  @ValueSource(strings = {"Werni", "Kurti", "bluemi"})
//  void getUserReturnsInvalidUser(String name) {
//    assertNull(Client.getUser(name));
//  }
//
//  @DisplayName("Client.addDonation returns true when added a not stored donation")
//  @ParameterizedTest
//  @MethodSource("generateNotStoredDonations")
//  void addDonationReturnsTrueWhenAddedNotStoredDonation(Donations d) {
//    assertTrue(Client.addDonation(d));
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
//  @DisplayName("Client.validateInput returns true when valid credentials given")
//  @Test
//  void validateInputReturnsTrueWhenValidCredentialsGiven() {
//    assertTrue(Client.validateInput("WerniGert69", "IchLiebeMeinenEnkel"));
//    assertTrue(Client.validateInput("test", "test"));
//    assertTrue(Client.validateInput("Zuensli53", "BuchsBaumZuensler"));
//  }
//
//  @DisplayName("Client.validateInput returns false when invalid credentials given")
//  @Test
//  void validateInputReturnsFalseWhenValidCredentialsGiven() {
//    assertFalse(Client.validateInput("Werni", "IchLiebeMeinenEnkel"));
//    assertFalse(Client.validateInput("test", "test12"));
//    assertFalse(Client.validateInput("Zuensli13", "BuchsBaumZuensler"));
//  }
//
//  @DisplayName("Client.validateInput returns true when valid credentials given email")
//  @Test
//  void validateInputReturnsTrueWhenValidCredentialsGivenMail() {
//    assertTrue(Client.validateInputMail("example@gmail.com", "1234"));
//    assertTrue(Client.validateInputMail("help@gmx.com", "test123"));
//  }
//
//  @DisplayName("Client.validateInput returns false when invalid credentials given email")
//  @Test
//  void validateInputReturnsFalseWhenInvalidCredentialsGivenMail() {
//    assertFalse(Client.validateInputMail("example55@gmail.com", "1234"));
//    assertFalse(Client.validateInputMail("help@gmx.com", "tes34"));
//  }
//}

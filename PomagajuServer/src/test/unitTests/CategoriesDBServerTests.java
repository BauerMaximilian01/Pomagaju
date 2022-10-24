//package unitTests;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import swe4.rmi.Client;
//import swe4.rmi.GoodsDatabaseServiceImpl;
//import swe4.rmi.PomagajuServer;
//
//import java.rmi.RemoteException;
//import java.util.Collection;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class CategoriesDBServerTests {
//  @DisplayName("Start server and Client for testing before all Tests")
//  @BeforeAll
//  static void startServerBeforeTesting() {
//    PomagajuServer.main(new String[] {});
//  }
//
//  @DisplayName("CategoriesDatabase.getCats returns valid Collection<String>")
//  @Test
//  void getCatsReturnsValidCollectionOfStrings() throws RemoteException {
//    Collection<String> categories = new GoodsDatabaseServiceImpl().getCategories();
//
//    assertEquals("[Hygiene, Lebensmittel, Kleidung, Elektronik]", categories.toString());
//    assertNotNull(categories);
//  }
//}

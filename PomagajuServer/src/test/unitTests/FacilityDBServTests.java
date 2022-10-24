//package unitTests;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//import swe4.pomagajuclasses.Address;
//import swe4.pomagajuclasses.Facility;
//import swe4.rmi.FacilityDatabaseServiceImpl;
//
//import swe4.rmi.PomagajuServer;
//
//import java.rmi.RemoteException;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class FacilityDBServTests {
//  private static FacilityDatabaseServiceImpl facilityService = null;
//
//  @DisplayName("Start server and facilityService for testing before all Tests")
//  @BeforeAll
//  static void startServerBeforeTesting() {
//    PomagajuServer.main(new String[] {});
//    facilityService = new FacilityDatabaseServiceImpl();
//  }
//
//  @DisplayName("facilityService.changeFacility returns name of Facility when Valid parameters")
//  @Test
//  void changeFacilityReturnsNameOfFacilityWhenValidParameters() throws RemoteException {
//    Facility f1 = new Facility("Annahmestelle 3", "Oberösterreich", "Linz" ,new Address("Rudigierstraße 21", "4020", "Linz"),"Volyn");
//    Facility f2 = new Facility("Bring Goods To Us", "Wien", "6. Bezirk" ,new Address("Hagenberger Allee 21", "4020", "Hagenberg"),"Verdansk");
//
//    assertEquals("Annahmestelle 3", facilityService.changeFacility("Barmherzige Brüder Linz", f1, false));
//    assertEquals("Bring Goods To Us", facilityService.changeFacility("Annahmestelle Salzburg", f2, true));
//
//    assertNotNull(facilityService.getFacility("Annahmestelle 3"));
//    assertNotNull(facilityService.getFacility("Bring Goods To Us"));
//  }
//
//  @DisplayName("facilityService.changeFacility returns null when invalid parameters")
//  @Test
//  void changeFacilityReturnsNullWhenInvalidParameters() throws RemoteException  {
//    Facility f1 = new Facility("Annahmestelle 3", "Oberösterreich", "Linz" ,new Address("Rudigierstraße 21", "4020", "Linz"),"Volyn");
//    Facility f2 = new Facility("Bring Goods To Us", "Oberösterreich", "Linz" ,new Address("Hagenberger Allee 21", "4020", "Hagenberg"),"Verdansk");
//
//    assertNull(facilityService.changeFacility("Linz", f1, false));
//    assertNull(facilityService.changeFacility("Maria hilf mir!", f2, true));
//  }
//
//  @DisplayName("facilityService.removeFacility removes Facility when Valid name of Facility")
//  @Test
//  void removeFacilityRemovesFacilityWhenValidInputName() throws RemoteException  {
//    facilityService.removeFacility("Maria hilft!");
//    assertNull(facilityService.getFacility("Maria hilft!"));
//  }
//
//  @DisplayName("facilityService.removeFacility doesn't remove Facility when Invalid name of Facility")
//  @Test
//  void removeFacilityDoesntRemoveFacilityWhenInvalidInputName() throws RemoteException  {
//    facilityService.removeFacility("Maria hilf mir!");
//    assertNotNull(facilityService.getFacility("Maria hilft!"));
//  }
//
//  @DisplayName("facilityService.filterFacilities filters Facilities on district")
//  @Test
//  void filterFacilitiesFiltersFacilitiesOnDisctrict() throws RemoteException  {
//    facilityService.filterFacilities("Linz");
//    assertEquals(1, facilityService.getFacilities().size());
//    assertNotNull(facilityService.getFacilities());
//  }
//
//  @DisplayName("facilityService.filterFacilitiesOnCountry filters Facilities on Country")
//  @Test
//  void filterFacilitiesOnCountryFiltersFacilitiesOnCountry() throws RemoteException  {
//    facilityService.filterFacilitiesOnCountry("Salzburg");
//    assertEquals(1, facilityService.getFacilities().size());
//    assertNotNull(facilityService.getFacilities());
//  }
//
//  @DisplayName("facilityService.filterFacilitiesOnGoods filters Facilities on Goods")
//  @Test
//  void filterFacilitiesOnGoodsFiltersFacilitiesOnGoods() throws RemoteException  {
//    facilityService.filterFacilitiesOnGoods("Shampoo");
//    assertEquals(1, facilityService.getFacilities().size());
//  }
//
//  @DisplayName("facilityService.addFacility returns true when valid Facility parameter")
//  @ParameterizedTest
//  @MethodSource("generateValidFacilities")
//  void addFacilityReturnsTrueWhenValidFacilityParameter(Facility f) throws RemoteException  {
//    assertTrue(facilityService.addFacility(f));
//  }
//
//  static Stream<Facility> generateValidFacilities() {
//    Facility f1 = new Facility("Annahmestelle 1", "Tirol", "Tirol", new Address("TirolerStrasse 21", "4560", "Tirol"), "bla");
//    Facility f2 = new Facility("Annahmestelle 2", "Tirol", "Tirol", new Address("Strasse in Tirol 12", "4560", "Tirol"), "bla");
//    Facility f3 = new Facility("Annahmestelle 3", "Salzburg", "Salzburg", new Address("LinzerGasse 123", "4560", "Salzburg"), "bla");
//
//    return Stream.of(f1, f2, f3);
//  }
//
//
//}

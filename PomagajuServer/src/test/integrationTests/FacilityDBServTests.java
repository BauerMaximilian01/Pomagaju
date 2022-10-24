package integrationTests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import swe4.pomagajuclasses.Address;
import swe4.pomagajuclasses.Facility;
import swe4.rmi.Client;
import swe4.rmi.PomagajuServer;

import java.util.stream.Stream;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.*;

public class FacilityDBServTests {
  @DisplayName("Start server and Client for testing before all Tests")
  @BeforeAll
  static void startServerBeforeTesting() {
    PomagajuServer.main(new String[] {});
    new Client();
  }

  @DisplayName("Client.changeFacility returns name of Facility when Valid parameters")
  @Test
  void changeFacilityReturnsNameOfFacilityWhenValidParameters() {
    Facility f1 = new Facility("Annahmestelle 3", "Oberösterreich", "Linz" ,new Address("Rudigierstraße 21", "4020", "Linz"),"Volyn");
    Facility f2 = new Facility("Bring Goods To Us", "Wien", "6. Bezirk" ,new Address("Hagenberger Allee 21", "4020", "Hagenberg"),"Verdansk");

    assertEquals("Annahmestelle 3", Client.changeFacility("Barmherzige Brüder Linz", f1, false));
    assertEquals("Bring Goods To Us", Client.changeFacility("Annahmestelle Salzburg", f2, true));

    assertNotNull(Client.getFacility("Annahmestelle 3"));
    assertNotNull(Client.getFacility("Bring Goods To Us"));
  }

  @DisplayName("Client.changeFacility returns null when invalid parameters")
  @Test
  void changeFacilityReturnsNullWhenInvalidParameters() {
    Facility f1 = new Facility("Annahmestelle 3", "Oberösterreich", "Linz" ,new Address("Rudigierstraße 21", "4020", "Linz"),"Volyn");
    Facility f2 = new Facility("Bring Goods To Us", "Oberösterreich", "Linz" ,new Address("Hagenberger Allee 21", "4020", "Hagenberg"),"Verdansk");

    assertNull(Client.changeFacility("Linz", f1, false));
    assertNull(Client.changeFacility("Maria hilf mir!", f2, true));
  }

  @DisplayName("Client.removeFacility removes Facility when Valid name of Facility")
  @Test
  void removeFacilityRemovesFacilityWhenValidInputName() {
    Client.removeFacility("Maria hilft!");
    assertNull(Client.getFacility("Maria hilft!"));
  }

  @DisplayName("Client.removeFacility doesn't remove Facility when Invalid name of Facility")
  @Test
  void removeFacilityDoesntRemoveFacilityWhenInvalidInputName() {
    Client.removeFacility("Maria hilf mir!");
    assertNotNull(Client.getFacility("Maria hilft!"));
  }

  @DisplayName("Client.filterFacilities filters Facilities on district")
  @Test
  void filterFacilitiesFiltersFacilitiesOnDisctrict() {
    Client.filterFacilities("Linz");
    assertEquals(1, Client.getFacilities().size());
    assertNotNull(Client.getFacilities());
  }

  @DisplayName("Client.filterFacilitiesOnCountry filters Facilities on Country")
  @Test
  void filterFacilitiesOnCountryFiltersFacilitiesOnCountry() {
    Client.filterFacilitiesOnCountry("Salzburg");
    assertEquals(1, Client.getFacilities().size());
    assertNotNull(Client.getFacilities());
  }

  @DisplayName("Client.filterFacilitiesOnGoods filters Facilities on Goods")
  @Test
  void filterFacilitiesOnGoodsFiltersFacilitiesOnGoods() {
    Client.filterFacilitiesOnGoods("Shampoo");
    assertEquals(1, Client.getFacilities().size());
  }

  @DisplayName("Client.addFacility returns true when valid Facility parameter")
  @ParameterizedTest
  @MethodSource("generateValidFacilities")
  void addFacilityReturnsTrueWhenValidFacilityParameter(Facility f) {
    assertTrue(Client.addFacility(f));
  }

  static Stream<Facility> generateValidFacilities() {
    Facility f1 = new Facility("Annahmestelle 1", "Tirol", "Tirol", new Address("TirolerStrasse 21", "4560", "Tirol"), "bla");
    Facility f2 = new Facility("Annahmestelle 2", "Tirol", "Tirol", new Address("Strasse in Tirol 12", "4560", "Tirol"), "bla");
    Facility f3 = new Facility("Annahmestelle 3", "Salzburg", "Salzburg", new Address("LinzerGasse 123", "4560", "Salzburg"), "bla");

    return Stream.of(f1, f2, f3);
  }


}

package integrationTests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import swe4.pomagajuclasses.Facility;
import swe4.pomagajuclasses.Goods;
import swe4.rmi.Client;
import swe4.rmi.PomagajuServer;

import java.util.stream.Stream;

public class GoodsDBServerTests {
  @DisplayName("Start server and Client for testing before all Tests")
  @BeforeAll
  static void startServerBeforeTesting() {
    PomagajuServer.main(new String[] {});
    new Client();
  }

  @DisplayName("Client.changeGood changes Good of Facility and doesn't throw exception")
  @Test
  void changeGoodChangesGoodOfFacilityWithoutThrowingException() {
    Facility f = Client.getFacility("Maria hilft!");

    assertDoesNotThrow(() -> Client.changeGood(f, "Shampoo", new Goods("Duschgel", "description", "neu", "Hygiene", 145)));
    assertNotEquals(f.getGoodsList(), Client.getFacility("Maria hilft!").getGoodsList());
  }

  @DisplayName("Client.removeGood removes Good of Facility")
  @Test
  void removeGoodRemovesGoodOfFacility() {
    Facility f = Client.getFacility("Maria hilft!");

    assertDoesNotThrow(() -> Client.removeGood(f, f.getGoodsList().get(1)));
    assertNotEquals(f.getGoodsList(), Client.getFacility("Maria hilft!").getGoodsList());
  }

  @DisplayName("Client.addGood returns True when not stored Good added")
  @ParameterizedTest
  @MethodSource("generateNotStoredGoods")
  void addGoodReturnsTrueWhenNotStoredGoodAdded(Goods g) {
    Facility f = Client.getFacility("Maria hilft!");

    assertTrue(Client.addGood(f, g));
  }

  static Stream<Goods> generateNotStoredGoods() {
    Goods g1 = new Goods("Laptop", "description", "neu", "Elektronik", 145);
    Goods g2 = new Goods("iPhone", "Apple iPhone green", "gebraucht", "Elektronik", 500);
    Goods g3 = new Goods("bla", "bla", "neu", "Hygiene", 300);

    return Stream.of(g1, g2, g3);
  }

  @DisplayName("Client.addGood returns False when already stored Good added")
  @Test
  void addGoodReturnsFalseWhenAlreadyStoredGoodAdded() {
    Facility f = Client.getFacility("Maria hilft!");
    var goodsList = Client.getFacility("Maria hilft!").getGoodsList();

    Goods g = goodsList.get(0);

    assertFalse(Client.addGood(f, g));
  }
}

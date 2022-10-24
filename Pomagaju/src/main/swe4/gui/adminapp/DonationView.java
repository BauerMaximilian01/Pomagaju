package swe4.gui.adminapp;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import swe4.pomagajuclasses.Donations;

public class DonationView {
  private Scene donationScene = null;

  DonationView() {
    createScene();
    Global.addScene(donationScene, "donations");
  }

  /* ----------------------------------------- */
  /*               Donations View              */
  /* ----------------------------------------- */

  private void createScene() {
    donationScene = new Scene(makeDonations(), 800, 400);
  }

  private BorderPane makeDonations() {
    BorderPane borderPane = new BorderPane();

    borderPane.setTop(makeViewNav());
    borderPane.setCenter(makeDonationsTableBox());

    return borderPane;
  }

  private HBox makeViewNav() {
    return ReusableViewDesignMethods.createNav();
  }

  private VBox makeDonationsTableBox() {

    TableView<Donations> table = makeDonationTable();

    VBox.setVgrow(table, Priority.ALWAYS);
    VBox vBox = ReusableViewDesignMethods.makeTableBoxTitle("Vorangekündigte Spenden", table);
    vBox.getChildren().add(table);
    vBox.getChildren().add(ReusableViewDesignMethods.makeInteractionBox("donations", table));
    return vBox;
  }

  private TableView<Donations> makeDonationTable() {
    TableView<Donations> table = new ReusableViewDesignMethods<Donations>().getTable("Email", "Hilfsgut", "Menge", "Zeitpunkt",
                                                                                         "email", "good", "quant", "timeStamp");

    new Thread(() -> {
      var donations = Client.getDonationsWhere(String.format("WHERE facilityName = '%s'", Global.getCurrentFacility().getName()));

      Platform.runLater(() -> {
        table.getItems().clear();
        table.getItems().addAll(donations);
      });
    }).start();

    return table;
  }
}

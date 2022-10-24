package swe4.gui.adminapp;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import swe4.Utility;
import swe4.pomagajuclasses.Facility;

public class FacilityView {
  private Scene facilityScene = null;

  FacilityView() {
    createScene();
    Global.addScene(facilityScene, "facilities");
  }

  /* ----------------------------------------- */
  /*              Facilities View              */
  /* ----------------------------------------- */

  private void createScene() {
    facilityScene = new Scene(makeFacilities(), 800, 400);
  }

  private BorderPane makeFacilities() {
    BorderPane borderPane = new BorderPane();

    borderPane.setTop(makeViewNav());
    borderPane.setCenter(makeFacilitiesBox());

    return borderPane;
  }

  private HBox makeFacilitiesBox() {
    HBox hBox = new HBox();

    VBox table = makeFacilityTableBox();

    HBox.setHgrow(table, Priority.ALWAYS);
    hBox.getChildren().add(table);

    return hBox;
  }

  private VBox makeFacilityTableBox() {
    TableView<Facility> table = makeFacilityTable();

    VBox.setVgrow(table, Priority.ALWAYS);
    VBox vBox = ReusableViewDesignMethods.makeTableBoxTitle("Annahmestellen", table);
    vBox.getChildren().add(table);
    vBox.getChildren().add(ReusableViewDesignMethods.makeInteractionBox("facility", table));

    return vBox;
  }

  private HBox makeViewNav() {
    return ReusableViewDesignMethods.createNav();
  }

  private TableView<Facility> makeFacilityTable() {
    TableView<Facility> table = new ReusableViewDesignMethods<Facility>().getTable("Name", "Bundesland", "Bezirk", "Adresse", "Region", "Aktiv/Inaktiv",
                                                                                   "name", "country", "district", "address", "region", "active");

    table.setRowFactory(tv -> {
      TableRow<Facility> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
            && event.getClickCount() == 2) {
          if (row.getItem().getName().equalsIgnoreCase(Global.getCurrentFacility().getName())) {
            new EditFacilityDialog(Global.getCurrentFacility(), Global.getStage(), table).showStage();
          } else {
            new InspectFacilityDialog(row.getItem(), Global.getStage()).showStage();
          }
        }
      });
      return row;
    });

    new Thread (() -> {
      var facilities = Client.getFacilities();

      Platform.runLater(() -> {
        table.getItems().clear();
        table.getItems().addAll(facilities);
      });
    }).start();

    return table;
  }
}

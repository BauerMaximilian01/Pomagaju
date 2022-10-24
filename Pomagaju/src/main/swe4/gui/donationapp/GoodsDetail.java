package swe4.gui.donationapp;

import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import swe4.gui.donationapp.Client;
import swe4.pomagajuclasses.Facility;
import swe4.pomagajuclasses.Goods;

import java.util.List;

public class GoodsDetail {
  private static BorderPane borderPane = null;
  private Goods currentGood = Global.getCurrentGood();
  private Facility currentFacility = Global.getCurrentFacility();

  GoodsDetail() {
    createScene();
  }

  private void createScene() {
    borderPane = new BorderPane();

    borderPane.setBottom(ReusableViewDesignMethods.makeBottomNav());
    borderPane.setTop(ReusableViewDesignMethods.makeBanner(Global.getCurrentGood().getIdentifier()));
    borderPane.setCenter(makeGoodInfo());

    Scene scene = new Scene(borderPane, 350, 620);
    Global.addScene("goodsDetail", scene);
    Global.setScene(scene);
    Global.showStage();
  }

  private VBox makeGoodInfo() {
    VBox vBox = new VBox(makeDonateButton());

    String[] labels = {"Kategorie:", "Beschreibung:", "Menge:", "Zustand:", "Informationen zur Annahmestelle",
                       "Name:", "Adresse:", "Spende geht in die Region:"};

    String[] labelsNoFacility = {"Kategorie:", "Beschreibung:", "Menge:", "Zustand:",
                                 "Annahmestellen", "Spende kann in folgende Regionen gehen:"};

    if (Global.getCurrentFacility() != null) {
      for (String label : labels) {
        vBox.getChildren().add(makeLabel(label));
        Label info = new Label();

        if (label.equalsIgnoreCase("kategorie:")) {
          info.setText(currentGood.getCategory());
        } else if (label.equalsIgnoreCase("beschreibung:")) {
          info.setText(currentGood.getDescription());
        } else if (label.equalsIgnoreCase("menge:")) {
          info.setText(currentGood.getQuantityAsString());
        } else if (label.equalsIgnoreCase("zustand:")) {
          info.setText(currentGood.getState());
        } else if (label.equalsIgnoreCase("name:")) {
          info.setText(currentFacility.getName());
        } else if (label.equalsIgnoreCase("adresse:")) {
          info.setText(currentFacility.getAddressAsString());
        } else if (label.contains("Spende geht")) {
          info.setText(currentFacility.getRegion());
        }

        if (!label.contains("Informationen"))
          vBox.getChildren().add(info);
      }
    } else {
      for (int i = 0; i < labelsNoFacility.length; ++i) {
        vBox.getChildren().add(makeLabel(labelsNoFacility[i]));
        Label info = new Label();

        if (labelsNoFacility[i].equalsIgnoreCase("kategorie:")) {
          info.setText(currentGood.getCategory());
        } else if (labelsNoFacility[i].equalsIgnoreCase("beschreibung:")) {
          info.setText(currentGood.getDescription());
        } else if (labelsNoFacility[i].equalsIgnoreCase("menge:")) {
          info.setText(currentGood.getQuantityAsString());
        } else if (labelsNoFacility[i].equalsIgnoreCase("zustand:")) {
          info.setText(currentGood.getState());
        } else if (labelsNoFacility[i].equalsIgnoreCase("Annahmestellen")) {

          new Thread(() -> {
            String whereClause = String.format("WHERE goods.identifier = '%s'", currentGood.getIdentifier());

            var facilities = Client.getFacilitiesWhere(whereClause);

            Platform.runLater(() -> {
              for (Facility f : facilities) {
                vBox.getChildren().add(new Label(f.getName()));
              }
            });
          }).start();

        } else if (labelsNoFacility[i].contains("Spende kann")) {

          new Thread(() -> {
            var facilities = Client.getFacilities();

            Platform.runLater(() -> {
              for (Facility f : facilities) {
                vBox.getChildren().add(new Label(f.getRegion()));
              }
            });
          }).start();
        }

        if (!labelsNoFacility[i].contains("Kann an") || !labelsNoFacility[i].contains("Spende geht"))
          vBox.getChildren().add(info);
      }
    }

    vBox.setPadding(new Insets(10));
    vBox.setSpacing(5);

    return vBox;
  }

  private Label makeLabel(String s) {
    Label label = new Label(s);
    label.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 16;");

    return label;
  }

  private HBox makeDonateButton() {
    Button btn = new Button("Spenden");

    btn.setStyle("-fx-background-color: #ffd700;" + "-fx-text-fill: black;");

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        new DonateView();
      }
    });

    HBox hBox = new HBox(ReusableViewDesignMethods.makeButtonBackwards(), btn);
    hBox.setSpacing(30);
    hBox.setAlignment(Pos.CENTER);

    return hBox;
  }
}

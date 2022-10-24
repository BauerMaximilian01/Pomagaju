package swe4.gui.adminapp;

import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import swe4.pomagajuclasses.Address;
import swe4.pomagajuclasses.Facility;

public class NewFacilityDialog {
  private Stage primaryStage = new Stage();
  private TableView table;

  NewFacilityDialog(Stage owner, TableView table) {
    this.table = table;

    VBox vBox = new VBox(makeNewFacilityForm());

    BorderPane pane = new BorderPane();
    pane.setMinSize(350, 150);
    pane.setCenter(vBox);

    Scene scene = new Scene(pane, 400, 520);

    primaryStage.initModality(Modality.WINDOW_MODAL);
    primaryStage.initOwner(owner);
    primaryStage.initStyle(StageStyle.DECORATED);

    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Neue Annahmestelle hinzufügen");
  }

  public void showStage() {
    primaryStage.show();
  }

  private VBox makeNewFacilityForm() {
    Label name = new Label("Name");
    Label country = new Label("Bundesland");
    Label district = new Label("Bezirk");
    Label street = new Label("Straße und Hausnummer");
    Label zip = new Label("Postleitzahl");
    Label loc = new Label("Ort");
    Label region = new Label("Region");

    TextField nameField = new TextField();
    TextField countryField = new TextField();
    TextField districtField = new TextField();
    TextField streetField = new TextField();
    TextField zipField = new TextField();
    TextField locField = new TextField();
    TextField regionField = new TextField();

    HBox buttonBox = new HBox(makeOkButton(nameField, countryField, districtField, streetField, zipField, locField, regionField), makeCancelButton());
    buttonBox.setSpacing(10);
    buttonBox.setAlignment(Pos.CENTER);

    VBox vBox = new VBox(name, nameField, country, countryField, district, districtField, street, streetField, zip, zipField, loc, locField, region, regionField, buttonBox);
    vBox.setSpacing(10);
    vBox.setPadding(new Insets(10));
    vBox.setMinSize(200, 150);

    return vBox;
  }

  private Button makeOkButton(TextField... t) {
    Button btn = new Button("hinzufügen");

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        if (validateUserInput(t)) {

          new Thread(() -> {
            boolean success = Client.addFacility(new Facility(t[0].getText(), t[1].getText(), t[2].getText(), new Address(t[3].getText(), t[4].getText(), t[5].getText()), t[6].getText()));

            var facilities = Client.getFacilities();

            Platform.runLater(() -> {
              if (success) {
                table.getItems().clear();
                table.getItems().addAll(facilities);
                primaryStage.hide();
              } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Die Annahmestelle existiert bereits. ");
                a.setContentText("Tabellenzeile Doppelklicken zum bearbeiten.");
                a.showAndWait();
              }
            });
          }).start();


        } else {
          Alert a = new Alert(Alert.AlertType.ERROR);
          a.setTitle("ERROR");
          a.setContentText("Bitte füllen Sie zuerst alle Felder aus.");
          a.showAndWait();
        }
      }
    });

    return btn;
  }

  private boolean validateUserInput(TextField... t) {
    for (TextField textField : t) {
      if (textField.getText() == null || textField.getText().trim().isEmpty()) {
        return false;
      }
    }

    return true;
  }

  private Button makeCancelButton() {
    Button btn = new Button("abbrechen");

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        primaryStage.hide();
      }
    });

    return btn;
  }
}

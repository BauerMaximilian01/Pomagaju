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
import swe4.Utility;
import swe4.pomagajuclasses.Address;
import swe4.pomagajuclasses.Facility;

public class EditFacilityDialog {
  private final Stage primaryStage = new Stage();
  private final TableView table;

  EditFacilityDialog(Facility f, Stage owner, TableView table) {
    this.table = table;

    VBox vBox = new VBox(makeEditableForm(f));

    BorderPane pane = new BorderPane();
    pane.setMinSize(200, 150);
    pane.setCenter(vBox);

    Scene scene = new Scene(pane, 400, 520 );

    primaryStage.initModality(Modality.WINDOW_MODAL);
    primaryStage.initOwner(owner);
    primaryStage.initStyle(StageStyle.DECORATED);

    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Annahmestelle bearbeiten");
  }

  private VBox makeEditableForm(Facility f) {
    Label name = new Label("Name");
    Label country = new Label("Bundesland");
    Label district = new Label("Bezirk");
    Label street = new Label("Straße und Hausnummer");
    Label zip = new Label("Postleitzahl");
    Label loc = new Label("Ort");
    Label region = new Label("Region");

    TextField nameField = new TextField();
    nameField.setText(f.getName());
    String oldName = nameField.getText();

    TextField countryField = new TextField();
    countryField.setText(f.getCountry());

    TextField distField = new TextField();
    distField.setText(f.getDistrict());

    TextField streetField = new TextField();
    streetField.setText(f.getAddress().getStreet());

    TextField zipField = new TextField();
    zipField.setText(f.getAddress().getZipCode());

    TextField locField = new TextField();
    locField.setText(f.getAddress().getLocation());

    TextField regionField = new TextField();
    regionField.setText(f.getRegion());

    CheckBox activeness = new CheckBox();
    activeness.setText("Annahmestelle aktiv");

    activeness.setSelected(f.getActive().equals("aktiv"));

    HBox buttonBox = new HBox(makeOkButton(oldName, activeness, nameField, countryField, distField, streetField, zipField, locField, regionField), makeDeleteButton(f), makeCancelButton());
    buttonBox.setSpacing(10);
    buttonBox.setAlignment(Pos.CENTER);

    VBox vBox = new VBox(name, nameField, country, countryField, district, distField, street, streetField, zip, zipField, loc, locField, region, regionField, activeness, buttonBox);
    vBox.setSpacing(10);
    vBox.setPadding(new Insets(10));
    vBox.setMinSize(200, 150);

    return vBox;
  }

  private Button makeOkButton(String oldName, CheckBox check, TextField... t) {
    Button btn = new Button("Änderungen speichern");


    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        if (validateUserInput(t)) {

          new Thread(() -> {
            String changedFacility = Client.changeFacility(oldName, new Facility(t[0].getText(), t[1].getText(), t[2].getText(), new Address(t[3].getText(), t[4].getText(), t[5].getText()), t[6].getText()), check.isSelected());
            Global.setCurrentFacility(Client.getFacility(changedFacility));

            var facilities = Client.getFacilities();

            Platform.runLater(() -> {
              table.getItems().clear();
              table.getItems().addAll(facilities);

              primaryStage.hide();
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

  private Button makeDeleteButton(Facility f) {
    Button btn = new Button("Annahmestelle löschen");

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        if (Global.getCurrentFacility().hasNoGoods()) {

          new Thread(() -> {
            Client.removeFacility(f.getName());

            Global.setCurrentFacility(null);
            Global.setCurrentUser(null);

            Platform.runLater(() -> {
              primaryStage.hide();
              Global.getStage().hide();
              new LoginView().showStage();
            });
          }).start();

        } else {
          Alert a = new Alert(Alert.AlertType.ERROR);
          a.setTitle("Annahmestelle konnte nicht gelöscht werden");
          a.setContentText("Annahmestelle besitzt noch Hilfsgüter");
          a.showAndWait();
        }
      }
    });

    return btn;
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

  public void showStage() {
    primaryStage.show();
  }
}

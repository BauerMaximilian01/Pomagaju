package swe4.gui.adminapp;

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
import swe4.pomagajuclasses.Facility;

public class InspectFacilityDialog {
  private Stage primaryStage = new Stage();

  InspectFacilityDialog(Facility f, Stage owner) {
    VBox vBox = new VBox(makeNonEditableForm(f));

    BorderPane pane = new BorderPane();
    pane.setMinSize(200, 150);
    pane.setCenter(vBox);

    Scene scene = new Scene(pane, 400, 500 );

    primaryStage.initModality(Modality.WINDOW_MODAL);
    primaryStage.initOwner(owner);
    primaryStage.initStyle(StageStyle.DECORATED);

    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Annahmestelle bearbeiten");
  }

  private VBox makeNonEditableForm(Facility f) {
    Label hint = new Label("Sie können nur die Annahmestelle bearbeiten,");
    Label hint2 = new Label("bei welcher Sie angemeldet sind.");
    Label name = new Label("Name");
    Label country = new Label("Bundesland");
    Label district = new Label("Bezirk");
    Label street = new Label("Straße und Hausnummer");
    Label zip = new Label("Postleitzahl");
    Label loc = new Label("Ort");

    TextField nameField = new TextField();
    nameField.setText(f.getName());
    //String oldName = nameField.getText();
    nameField.setEditable(false);

    TextField countryField = new TextField();
    countryField.setText(f.getCountry());
    countryField.setEditable(false);

    TextField distField = new TextField();
    distField.setText(f.getDistrict());
    distField.setEditable(false);

    TextField streetField = new TextField();
    streetField.setText(f.getAddress().getStreet());
    streetField.setEditable(false);

    TextField zipField = new TextField();
    zipField.setText(f.getAddress().getZipCode());
    zipField.setEditable(false);

    TextField locField = new TextField();
    locField.setText(f.getAddress().getLocation());
    locField.setEditable(false);

    CheckBox activeness = new CheckBox();
    activeness.setText("Annahmestelle aktiv");
    activeness.setDisable(true);

    activeness.setSelected(f.getActive().equals("aktiv"));

    HBox buttonBox = new HBox(makeSwitchButton(f));
    buttonBox.setSpacing(10);
    buttonBox.setAlignment(Pos.CENTER);

    VBox vBox = new VBox(hint, hint2, name, nameField, country, countryField, district, distField, street, streetField, zip, zipField, loc, locField, activeness, buttonBox);
    vBox.setSpacing(10);
    vBox.setPadding(new Insets(10));
    vBox.setMinSize(200, 150);

    return vBox;
  }

  public Button makeSwitchButton(Facility f) {
    Button btn = new Button("zu dieser Annahmestelle wechseln");
    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        Global.setCurrentFacility(null);
        Global.setCurrentUser(null);
        primaryStage.hide();
        Global.getStage().hide();
        new LoginView().showStage();
      }
    });

    return btn;
  }

  public void showStage() {
    primaryStage.show();
  }
}

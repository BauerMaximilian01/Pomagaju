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
import swe4.pomagajuclasses.Goods;

public class NewGoodDialog {
  private Stage primaryStage = new Stage();
  private TableView table = null;

  NewGoodDialog(Stage owner, TableView table) {
    this.table = table;

    VBox vBox = new VBox(makeNewGoodForm());

    BorderPane pane = new BorderPane();
    pane.setMinSize(200, 150);
    pane.setCenter(vBox);

    Scene scene = new Scene(pane, 400, 360 );

    primaryStage.initModality(Modality.WINDOW_MODAL);
    primaryStage.initOwner(owner);
    primaryStage.initStyle(StageStyle.DECORATED);

    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Neues Hilfsgut hinzufügen");
  }

  public void showStage() {
    primaryStage.show();
  }

  private VBox makeNewGoodForm() {
    Label name = new Label("Bezeichnung");
    Label desc = new Label("Beschreibung");
    Label state = new Label("Zustand");
    Label cat = new Label("Kategorie");
    Label quant = new Label("Menge");

    TextField nameField = new TextField();
    TextField descField = new TextField();
    TextField quantField = new TextField();

    ComboBox<String> stateCombo = new ComboBox<>();
    stateCombo.getItems().addAll("neu", "gebraucht");

    ComboBox<String> catCombo = new ComboBox<>();

    new Thread(() -> {
      var categories = Client.getCategories();

      Platform.runLater(() -> {
        for (String s : categories) {
          catCombo.getItems().add(s);
        }
      });
    }).start();

    HBox buttonBox = new HBox(makeOkButton(stateCombo, catCombo, nameField, descField, quantField), makeCancelButton());
    buttonBox.setSpacing(10);
    buttonBox.setAlignment(Pos.CENTER);

    VBox vBox = new VBox(name, nameField, desc, descField, state, stateCombo, cat, catCombo, quant, quantField, buttonBox);
    vBox.setSpacing(10);
    vBox.setPadding(new Insets(10));
    vBox.setMinSize(200, 150);

    return vBox;
  }

  private Button makeOkButton(ComboBox<String> states, ComboBox<String> cats, TextField... t) {
    Button btn = new Button("hinzufügen");

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        if (validateUserInput(states, cats, t[0], t[2])) {

          new Thread(() -> {
            Global.getCurrentFacility().addGood(new Goods(t[0].getText(), t[1].getText(), states.getValue(), cats.getValue(), Integer.parseInt(t[2].getText())));

            var succeded = Client.addGood(Global.getCurrentFacility(), new Goods(t[0].getText(), t[1].getText(), states.getValue(), cats.getValue(), Integer.parseInt(t[2].getText())));
            var goods = Client.getGoodsWhere(String.format("WHERE facilityName = '%s'", Global.getCurrentFacility()));

            Platform.runLater(() -> {
              if (succeded) {
                table.getItems().clear();
                table.getItems().addAll(goods);
                primaryStage.hide();
              } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Das Hilfsgut existiert bereits. ");
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

  private boolean validateUserInput(ComboBox<String> states, ComboBox<String> cats, TextField... t) {
    if (states.getValue() == null && cats.getValue() == null)
      return false;

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

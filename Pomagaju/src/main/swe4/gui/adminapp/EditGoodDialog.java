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

public class EditGoodDialog {
  private Stage primaryStage = new Stage();
  private TableView table = null;

  EditGoodDialog(Goods g, Stage owner, TableView table) {
    this.table = table;
    VBox vBox = new VBox(makeEditableForm(g));

    BorderPane pane = new BorderPane();
    pane.setMinSize(200, 150);
    pane.setCenter(vBox);

    Scene scene = new Scene(pane, 400, 360 );

    primaryStage.initModality(Modality.WINDOW_MODAL);
    primaryStage.initOwner(owner);
    primaryStage.initStyle(StageStyle.DECORATED);

    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Hilfsgut bearbeiten");
  }

  private VBox makeEditableForm(Goods g) {
    Label name = new Label("Bezeichnung");
    Label desc = new Label("Beschreibung");
    Label state = new Label("Zustand");
    Label cat = new Label("Kategorie");
    Label quant = new Label("Menge");

    TextField nameField = new TextField();
    nameField.setText(g.getIdentifier());
    String oldIdent = nameField.getText();

    TextField descField = new TextField();
    descField.setText(g.getDescription());

    TextField quantField = new TextField();
    quantField.setText(g.getQuantityAsString());

    ComboBox<String> stateCombo = new ComboBox<>();
    stateCombo.getItems().addAll("neu", "gebraucht");
    stateCombo.setValue(g.getState());

    ComboBox<String> catCombo = new ComboBox<>();

    new Thread(() -> {
      var categories = Client.getCategories();

      Platform.runLater(() -> {
        catCombo.getItems().addAll(categories);
        catCombo.setValue(g.getCategory());
      });
    }).start();

    HBox buttonBox = new HBox(makeOkButton(oldIdent, stateCombo, catCombo, nameField, descField, quantField), makeDeleteButton(g), makeCancelButton());
    buttonBox.setSpacing(10);
    buttonBox.setAlignment(Pos.CENTER);

    VBox vBox = new VBox(name, nameField, desc, descField, state, stateCombo, cat, catCombo, quant, quantField, buttonBox);
    vBox.setSpacing(10);
    vBox.setPadding(new Insets(10));
    vBox.setMinSize(200, 150);

    return vBox;
  }

  private Button makeOkButton(String oldIdent, ComboBox<String> states, ComboBox<String> cats, TextField... t) {
    Button btn = new Button("Änderungen speichern");

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        if (validateUserInput(states, cats, t)) {

          new Thread(() -> {
            Client.changeGood(Global.getCurrentFacility(), oldIdent, new Goods(t[0].getText(), t[1].getText(), states.getValue(), cats.getValue(), Integer.parseInt(t[2].getText())));

            var goods = Client.getGoodsWhere(String.format("WHERE facilityName = '%s'", Global.getCurrentFacility().getName()));

            Platform.runLater(() -> {
              table.getItems().clear();
              table.getItems().addAll(goods);
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

  private Button makeDeleteButton(Goods g) {
    Button btn = new Button("Hilfsgut löschen");

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        Global.getCurrentFacility().removeGood(g.getIdentifier());

        new Thread(() -> {
          Client.removeGood(Global.getCurrentFacility(), g);

          var goods = Client.getGoodsWhere(String.format("WHERE facilityName = '%s'", Global.getCurrentFacility().getName()));

          Platform.runLater(() -> {
            table.getItems().clear();
            table.getItems().addAll(goods);
            primaryStage.hide();
          });
        }).start();
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

package swe4.gui.adminapp;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import swe4.pomagajuclasses.Goods;

public class GoodsView {
  private Scene goodsScene = null;
  private Stage goodsStage = new Stage();

  GoodsView() {
    Global.setStage(goodsStage);

    createScene();

    Global.addScene(goodsScene, "goods");
    goodsStage.setScene(goodsScene);
  }

  public void showStage() {
    goodsStage.show();
    goodsStage.setResizable(true);
    goodsStage.setTitle("Pomagaju");
  }

  /* ----------------------------------------- */
  /*                 Goods View                */
  /* ----------------------------------------- */

  private void createScene() {
    goodsScene = new Scene(makeGoods(), 800, 400);
  }

  private BorderPane makeGoods() {
    BorderPane borderPane = new BorderPane();

    borderPane.setTop(makeViewNav());
    borderPane.setCenter(makeGoodsBox());

    return borderPane;
  }

  private HBox makeViewNav() {
    return ReusableViewDesignMethods.createNav();
  }

  private HBox makeGoodsBox() {
    HBox hBox = new HBox();

    VBox table = makeGoodsTable();

    HBox.setHgrow(table, Priority.ALWAYS);
    hBox.getChildren().add(table);

    return hBox;
  }

  private VBox makeGoodsTable() {
    TableView<Goods> table = createTable();

    VBox.setVgrow(table, Priority.ALWAYS);

    VBox vBox = ReusableViewDesignMethods.makeTableBoxTitle("Bedarf an Hilfsgütern", table);
    vBox.getChildren().add(table);
    vBox.getChildren().add(ReusableViewDesignMethods.makeInteractionBox("Goods", table));

    return vBox;
  }

  private TableView<Goods> createTable() {
    TableView<Goods> table = new ReusableViewDesignMethods<Goods>().getTable("Bezeichnung", "Beschreibung", "Zustand", "Kategorie", "Menge",
                                                                             "identifier", "description", "state", "category", "quantity");

    table.setRowFactory(tv -> {
      TableRow<Goods> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (!row.isEmpty() && event.getButton()== MouseButton.PRIMARY
            && event.getClickCount() == 2) {
            new EditGoodDialog(row.getItem(), goodsStage, table).showStage();
        }
      });
      return row;
    });

    new Thread (() -> {
      var goods = Client.getGoodsWhere(String.format("WHERE facilityName = '%s'", Global.getCurrentFacility().getName()));

      Platform.runLater(() -> {
        table.getItems().clear();
        table.getItems().addAll(goods);
      });
    }).start();

    return table;
  }
}
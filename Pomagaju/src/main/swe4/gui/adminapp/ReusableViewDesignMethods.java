package swe4.gui.adminapp;

import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import swe4.Utility;

import java.io.File;
import java.time.LocalDate;

public class ReusableViewDesignMethods<T> {

  public static HBox createNav() {
    return makeTopNav();
  }

  /* ----------------------------------------- */
  /*        Methods for Application Views      */
  /*                  TableBox                 */
  /* ----------------------------------------- */

  public static VBox makeTableBoxTitle(String s, TableView table) {
    Label title = new Label(s);
    title.setStyle("-fx-font-size: 20;" + "-fx-font-weight: bold;");

    Button btn = new Button("aktualisieren");
    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {

        if (s.equalsIgnoreCase("Annahmestellen")) {

          new Thread(() -> {
            var facilities = Client.getFacilities();
            Platform.runLater(() -> {
              table.getItems().clear();
              table.getItems().addAll(facilities);
            });
          }).start();

        } else if (s.equalsIgnoreCase("Bedarf an Hilfsgütern")) {

          new Thread(() -> {
            var goodsList = Client.getGoodsWhere(String.format("WHERE facilityName = '%s'", Global.getCurrentFacility().getName()));

            Platform.runLater(() -> {
              table.getItems().clear();
              table.getItems().addAll(goodsList);
            });
          }).start();

        } else if (s.equalsIgnoreCase("Vorangekündigte Spenden")) {

          new Thread(() -> {
            var donations = Client.getDonationsWhere(String.format("WHERE facilityName = '%s'", Global.getCurrentFacility().getName()));
            Platform.runLater(() -> {
              table.getItems().clear();
              table.getItems().addAll(donations);
            });
          }).start();
        }
      }
    });

    VBox vBox = new VBox(title, btn);

    vBox.setMinSize(650, 340);
    vBox.setPadding(new Insets(10));
    vBox.setSpacing(10);
    vBox.setAlignment(Pos.CENTER);

    return vBox;
  }

  public static HBox makeInteractionBox(String s, TableView table) {
    HBox hBox = new HBox();
    HBox spacer = new HBox();
    spacer.setMinSize(100, 50);

    HBox.setHgrow(spacer, Priority.ALWAYS);

    hBox.getChildren().add(makeFilterComboButton(s, table));
    hBox.getChildren().add(spacer);

    if (!s.equalsIgnoreCase("donations")) {
      hBox.getChildren().add(makeAddButton(s, table));
    }

    hBox.setAlignment(Pos.CENTER);
    hBox.setSpacing(10);

    return hBox;
  }

  private static VBox makeAddButton(String s, TableView table) {
    VBox vBox = new VBox();
    vBox.setAlignment(Pos.CENTER);
    vBox.setSpacing(12);

    Label title = new Label();

    Button btn = new Button();
    btn.setStyle("-fx-text-fill: black;" + "-fx-background-color: #ffd700;");
    btn.setEffect(new DropShadow());

    if (s.equalsIgnoreCase("facility")) {
      btn.setText("+ Annahmestelle hinzufügen");
      btn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
          new NewFacilityDialog(Global.getStage(), table).showStage();
        }
      });

      title.setText("neue Annahmestelle anlegen");
    } else {
      btn.setText("+ neues Hilfsgut hinzufügen");
      btn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
          new NewGoodDialog(Global.getStage(), table).showStage();
        }
      });

      title.setText("neues Hilfsgut anlegen");
    }

    vBox.getChildren().addAll(title, btn);

    return vBox;
  }

  private static VBox makeFilterComboButton(String s, TableView table) {
    VBox vBox = new VBox();

    if (s.equalsIgnoreCase("facility")) {
      vBox.getChildren().add(makeFilterComboButtonFacility(table));
    } else if (s.equalsIgnoreCase("goods")) {
      vBox.getChildren().add(makeFilterComboButtonGoods(table));
    } else {
      vBox.getChildren().add(makeFilterComboButtonDonations(table));
    }

    return vBox;
  }

  private static VBox makeFilterComboButtonFacility(TableView table) {
    TextField filterField = new TextField();

    filterField.setPromptText("Bezirk eingeben");

    Button filterBtn = new Button("filtern");
    filterBtn.setStyle("-fx-background-color: #0057b8;" + "-fx-text-fill: white;");
    filterBtn.setEffect(new DropShadow());
    filterBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {

        new Thread(() -> {
          String whereClause;
          if (filterField.getText() != null) {
            System.out.println(filterField.getText());
            whereClause = String.format("WHERE district = '%s'", filterField.getText());
          } else {
            whereClause = "";
          }

          var facilities = Client.getFacilitiesWhere(whereClause);

          Platform.runLater(() -> {
            table.getItems().clear();
            table.getItems().addAll(facilities);
          });
        }).start();

      }
    });

    HBox hBox = new HBox(filterField, makeClearButtonFacilities(filterField, table));
    hBox.setSpacing(8);

    VBox vBox = new VBox(hBox, filterBtn);
    vBox.setAlignment(Pos.CENTER);
    vBox.setSpacing(8);

    return vBox;
  }

  private static VBox makeFilterComboButtonGoods(TableView table) {
    ComboBox<String> filterCats = new ComboBox<>();

    filterCats.getItems().add("Alle");
    filterCats.setValue("Alle");

    new Thread(() -> {
      var categories = Client.getCategories();

      Platform.runLater(() -> {
        for (String s : categories) {
          filterCats.getItems().add(s);
        }
      });
    }).start();


    Button filterBtn = new Button("filtern");
    filterBtn.setStyle("-fx-background-color: #0057b8;" + "-fx-text-fill: white;");
    filterBtn.setEffect(new DropShadow());
    filterBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {

        new Thread(() -> {
          String whereClause;

          if (filterCats.getValue().equalsIgnoreCase("alle")) {
            whereClause = String.format("WHERE facilityName = '%s'", Global.getCurrentFacility().getName());
          } else {
            whereClause = String.format("WHERE categories.category = '%s' AND facilityName = '%s'", filterCats.getValue(), Global.getCurrentFacility().getName());
          }

          var goodsList = Client.getGoodsWhere(whereClause);
          Platform.runLater(() -> {
            table.getItems().clear();
            table.getItems().addAll(goodsList);
          });
        }).start();

      }
    });

    HBox hBox = new HBox(filterCats, makeClearButtonGoods(filterCats, table));
    hBox.setSpacing(8);

    VBox vBox = new VBox(hBox, filterBtn);
    vBox.setAlignment(Pos.CENTER);
    vBox.setSpacing(8);

    return vBox;
  }

  private static VBox makeFilterComboButtonDonations(TableView table) {
    DatePicker datePicker = new DatePicker();
    datePicker.setDayCellFactory(picker -> new DateCell() {
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        LocalDate today = LocalDate.now();

        setDisable(empty || date.compareTo(today) < 0 );
      }
    });

    Button filterBtn = new Button("filtern");
    filterBtn.setStyle("-fx-background-color: #0057b8;" + "-fx-text-fill: white;");
    filterBtn.setEffect(new DropShadow());
    filterBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {

        new Thread(() -> {
          String whereClause;

          System.out.println(datePicker.getValue());

          if (datePicker.getValue() != null) {
            whereClause = String.format("WHERE facilityName = '%s' AND date(delivery) = '%s'", Global.getCurrentFacility().getName(), datePicker.getValue());
          } else {
            whereClause = String.format("WHERE facilityName = '%s'", Global.getCurrentFacility().getName());
          }

          var donations = Client.getDonationsWhere(whereClause);

          Platform.runLater(() -> {
            table.getItems().clear();
            table.getItems().addAll(donations);
          });
        }).start();
      }
    });

    HBox hBox = new HBox(datePicker, makeClearButtonDonations(datePicker, table));
    hBox.setSpacing(8);

    VBox vBox = new VBox(hBox, filterBtn);
    vBox.setAlignment(Pos.CENTER);
    vBox.setSpacing(8);

    return vBox;
  }

  private static Button makeClearButtonFacilities(TextField text, TableView table) {
    Button btn = new Button("Auswahl löschen");

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        text.setText(null);

        new Thread(() -> {
          var facilities = Client.getFacilities();

          Platform.runLater(() -> {
            table.getItems().clear();
            table.getItems().addAll(facilities);
          });
        }).start();
      }
    });
    return btn;
  }

  private static Button makeClearButtonGoods(ComboBox<String> box, TableView table) {
    Button btn = new Button("Auswahl löschen");

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        box.setValue("Alle");

        new Thread(() -> {
          //TODO
//          Global.getCurrentFacility().filterGoods(box.getValue());
//          Client.filterGoods(Global.getCurrentFacility(), box.getValue());

          String whereClause = String.format("WHERE facilityName = '%s'", Global.getCurrentFacility().getName());

          var goods = Client.getGoodsWhere(whereClause);

          Platform.runLater(() -> {
            table.getItems().clear();
            table.getItems().addAll(goods);
          });
        }).start();
      }
    });
    return btn;
  }

  private static Button makeClearButtonDonations(DatePicker date, TableView table) {
    Button btn = new Button("Auswahl löschen");

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        date.setValue(null);

        new Thread(() -> {
          var donations = Client.getDonationsWhere(String.format("WHERE facilityName = '%s'", Global.getCurrentFacility().getName()));

          Platform.runLater(() -> {
            table.getItems().clear();
            table.getItems().addAll(donations);
          });
        }).start();
      }
    });
    return btn;
  }

  /* ----------------------------------------- */
  /*        Methods for Application Views      */
  /*                    Table                  */
  /* ----------------------------------------- */

  public TableView<T> getTable(String... s) {
    TableView<T> table = new TableView<>();
    table.prefHeightProperty().bind(Global.getStage().heightProperty());
    table.prefWidthProperty().bind(Global.getStage().widthProperty());
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    int j = 0;
    for (int i = 0; i < s.length / 2; ++i) {
      TableColumn<T, String> col = new TableColumn<>(s[i]);
      col.setCellValueFactory(new PropertyValueFactory<>(s[s.length / 2 + j]));
      ++j;

      table.getColumns().add(col);
    }

    return table;
  }

  /* ----------------------------------------- */
  /*        Methods for Application Views      */
  /*              Top Navigation bar           */
  /* ----------------------------------------- */
  public static HBox makeTopNav() {
    HBox hBox = new HBox();
    hBox.setMinSize(800, 60);
    hBox.setAlignment(Pos.CENTER);
    hBox.setStyle("-fx-background-color: #1A1A1A");

    String[] icons = {"ukraine.png", "goods_icon.png", "home_icon.png", "donation_icon.png", "account_icon.png"};

    for (int i = 0; i < icons.length; ++i) {
      hBox.getChildren().add(makeItems(icons[i]));
      if (hBox.getChildren().stream().count() == 1 || hBox.getChildren().stream().count() == 5) {
        HBox spacer = new HBox();
        spacer.setStyle("-fx-background-color: #1A1A1A");
        spacer.setMinSize( 50, 60);

        HBox.setHgrow(spacer, Priority.ALWAYS);

        hBox.getChildren().add(spacer);
      }
    }

    return hBox;
  }

  private static VBox makeItems(String iconPath) {
    VBox vBox = new VBox();

    if (iconPath.equals("ukraine.png")) {
      HBox hBox = new HBox();
      hBox.setPadding(new Insets(8));
      ImageView ukraineLogo = new ImageView(new Image(new File("src/resources/" + iconPath).toURI().toString(), 100, 50, true, false));

      Label pomagajuLabel = new Label("Pomagaju");
      pomagajuLabel.setStyle("-fx-text-alignment: center;" + "-fx-font-weight: bold;" + "-fx-font-size: 14;" + "-fx-text-fill: white;");

      hBox.getChildren().addAll(ukraineLogo, pomagajuLabel);
      hBox.setAlignment(Pos.CENTER);

      vBox.getChildren().add(hBox);
    } else {
      vBox.setPadding(new Insets(8, 0, 0, 0));
      ImageView icon = new ImageView(new Image(new File("src/resources/" + iconPath).toURI().toString(), 40, 46, true, false));

      Pane paneIndicator = new Pane();
      paneIndicator.setMinSize(46, 5);
      paneIndicator.setStyle("-fx-background-color: #1A1A1A");

      Button btn = new Button();
      btn.setMinSize(45, 50);
      btn.setGraphic(icon);

      Tooltip tip = new Tooltip();
      tip.setShowDelay(Duration.millis(50));
      tip.setStyle("-fx-font-size: 14");

      if (iconPath.contains("account")) {
        tip.setText("Account Informationen / neuen User anlegen");
        btn.setText(makeButtonText(iconPath));
      } else {
        tip.setText(makeButtonText(iconPath));
      }

      btn.setTooltip(tip);
      btn.setStyle("-fx-background-color: #1A1A1A;" + "-fx-text-fill: white;");

      sideNavSelection(btn, paneIndicator);

      if (iconPath.contains("goods")) {
        btn.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
            Global.getStage().setScene(Global.getScene("goods"));
          }
        });
      } else if (iconPath.contains("home")) {
        btn.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
            Global.getStage().setScene(Global.getScene("facilities"));
          }
        });
      } else if (iconPath.contains("donation")) {
        btn.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
            Global.getStage().setScene(Global.getScene("donations"));
          }
        });
      } else if (iconPath.contains("account")) {
        btn.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
            Global.getStage().setScene(Global.getScene("account"));
          }
        });
      }

      vBox.getChildren().addAll(btn, paneIndicator);
    }

    return vBox;
  }

  private static void sideNavSelection(Button btn, Pane pane) {
    btn.setOnMouseEntered(e -> {
      btn.setStyle("-fx-background-color: black;" + "-fx-text-fill: white;");
      pane.setStyle("-fx-background-color: #C5CCFF");
    });

    btn.setOnMouseExited(e -> {
      btn.setStyle("-fx-background-color: #1A1A1A;" + "-fx-text-fill: white;");
      pane.setStyle("-fx-background-color: #1A1A1A");
    });
  }

  private static String makeButtonText(String s) {
    String tmp = "";

    if (s.contains("goods")) {
      tmp = "Hilfsgüter";
    } else if (s.contains("home")) {
      tmp = "Annahmestellen verwalten";
    } else if (s.contains("donation")) {
      tmp = "vorangekündigte Spenden";
    } else if (s.contains("account")) {
      tmp = Global.getCurrentUser().getName();
    }

    return tmp;
  }
}

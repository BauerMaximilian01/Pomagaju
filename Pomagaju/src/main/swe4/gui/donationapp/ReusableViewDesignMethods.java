package swe4.gui.donationapp;

import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import swe4.gui.donationapp.Client;
import swe4.pomagajuclasses.Facility;
import swe4.pomagajuclasses.Goods;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ReusableViewDesignMethods {
  /* ----------------------------------------- */
  /*        Methods for Application Views      */
  /*           Bottom Navigation bar           */
  /* ----------------------------------------- */

  public static HBox makeBottomNav() {
    HBox hBox = new HBox();
    hBox.setMinSize(375, 60);
    hBox.setAlignment(Pos.CENTER);
    hBox.setStyle("-fx-background-color: #1A1A1A");

    String[] icons = {"home_icon.png", "goods_icon.png", "logout.png"};

    for (int i = 0; i < icons.length; ++i) {
      hBox.getChildren().add(makeItems(icons[i]));
    }

    return hBox;
  }

  private static VBox makeItems(String iconPath) {
    VBox vBox = new VBox();

    vBox.setPadding(new Insets(8, 0, 0, 0));

    ImageView icon = new ImageView(new Image(new File("src/resources/" + iconPath).toURI().toString(), 40, 46, true, false));


    Button btn = new Button();
    btn.setMinSize(40, 40);
    btn.setGraphic(icon);
    btn.setStyle("-fx-background-color: #1A1A1A;");

    if (iconPath.contains("goods")) {
      btn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
          Global.setCurrentFacility(null);
          Global.setScene("goods");
          GoodsView.refreshScrollBox("");
        }
      });
    } else if (iconPath.contains("home")) {
      btn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
          Global.setScene("facility");
          FacilityView.refreshScrollBox("");
        }
      });
    } else if (iconPath.contains("logout")) {
      btn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
          Global.setScene("login");

          Global.setCurrentFacility(null);
          Global.setCurrentUser(null);
          Global.setCurrentGood(null);
        }
      });
    }

    vBox.getChildren().addAll(btn);

    return vBox;
  }

  /* ----------------------------------------- */
  /*        Methods for Application Views      */
  /*            Scrollable Container           */
  /* ----------------------------------------- */

  public static ScrollPane makeScrollableContainer(String view, String whereClause) {

    ScrollPane scroll = new ScrollPane();
    scroll.setContent(makeContent(view, whereClause));

    return scroll;
  }

  private static VBox makeContent(String view, String whereClause) {
    VBox vBox = new VBox();
    vBox.setAlignment(Pos.CENTER);
    vBox.setSpacing(2);
    vBox.setStyle("-fx-background-color: white");

    if (view.equals("facility")) {
      new Thread(() -> {
        var facilities = Client.getFacilitiesWhere(whereClause);

        Platform.runLater(() -> {
          for (Facility f : facilities) {
            vBox.getChildren().add(makeContentItems(f));
          }
        });
      }).start();

    } else if (view.equals("goods")) {

      if (Global.getCurrentFacility() != null) {

        new Thread(() -> {
          var goods = Client.getGoodsWhere(whereClause);

          Platform.runLater(() -> {
            for (Goods g : goods)
              vBox.getChildren().add(makeContentItems(g));
          });
        }).start();

      } else {

        new Thread(() -> {
          List<Goods> goods = Client.getGoodsWhere(whereClause);

          Platform.runLater(() -> {
            for (Goods g : goods) {
              vBox.getChildren().add(makeContentItems(g));
            }
          });
        }).start();
      }
    }

    return vBox;
  }

  private static HBox makeContentItems(Facility f) {
    HBox hBox = new HBox();
    hBox.setPadding(new Insets(5));
    hBox.setPrefSize(347, 50);
    hBox.setStyle("-fx-border-color: black");

    VBox vBox = new VBox(new Label(f.getName()), new Label(f.getAddressAsString()));
    Button btn = makeButtonForward(f);

    HBox spacer = new HBox();
    spacer.setMinSize(5, 5);
    HBox.setHgrow(spacer, Priority.ALWAYS);

    hBox.getChildren().addAll(vBox, spacer, btn);

    return hBox;
  }

  private static HBox makeContentItems(Goods g) {
    HBox hBox = new HBox();
    hBox.setPadding(new Insets(5));
    hBox.setPrefSize(347, 50);
    hBox.setStyle("-fx-border-color: black");

    VBox vBox = new VBox(new Label(g.getIdentifier()), new Label(g.getCategory()), new Label(g.getQuantityAsString()));
    Button btn = makeButtonForward(g);

    HBox spacer = new HBox();
    spacer.setMinSize(5, 5);
    HBox.setHgrow(spacer, Priority.ALWAYS);

    hBox.getChildren().addAll(vBox, spacer, btn);

    return hBox;
  }

  private static Button makeButtonForward(Facility f) {
    Button btn = new Button("auswählen");

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        Global.setCurrentFacility(f);
        GoodsView.refreshScrollBox(String.format("WHERE facilityName = '%s'", f.getName()));
        GoodsView.refreshInteractionBox();
        Global.setScene(Global.getScenes("goods"));
      }
    });

    return btn;
  }

  private static Button makeButtonForward(Goods g) {
    Button btn = new Button("auswählen");

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        Global.setCurrentGood(g);
        new GoodsDetail();
      }
    });

    return btn;
  }

  public static HBox makeBanner(String s) {
    HBox hBox = new HBox();
    Label banner = new Label(s);

    banner.setStyle("-fx-text-fill: black;" + "-fx-font-weight: bold;" + "-fx-font-size: 14;");
    hBox.setStyle("-fx-background-color: C5CCFF;");

    hBox.getChildren().add(banner);
    hBox.setAlignment(Pos.CENTER);
    hBox.setSpacing(50);
    hBox.setPrefHeight(50);

    return hBox;
  }

  public static VBox makeInteractionBoxFacility() {
    TextField field = new TextField();
    field.setPromptText("Bezirk eingeben");

    Button btn = new Button("filtern");
    btn.setStyle("-fx-text-fill: white;" + "-fx-background-color: #0057b8;");
    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        new Thread(() -> {
          String whereClause;
          if (field.getText().equals("")) {
            whereClause = "";
          } else {
            whereClause = String.format("WHERE facilities.district = '%s'", field.getText());
          }

          Platform.runLater(() -> {
            FacilityView.refreshScrollBox(whereClause);
          });
        }).start();
      }
    });

    ComboBox<String> countryBox = new ComboBox<>();
    countryBox.getItems().addAll("Alle", "Burgenland", "Kärnten", "Niederösterreich", "Oberösterreich", "Salzburg", "Steiermark", "Tirol", "Vorarlberg", "Wien");

    Button comboButton = new Button("filtern");
    comboButton.setStyle("-fx-text-fill: white;" + "-fx-background-color: #0057b8;");
    comboButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        new Thread(() -> {
          String whereClause;
          if (countryBox.getValue().equals("Alle")) {
            whereClause = "";
          } else {
            whereClause = String.format("WHERE facilities.country = '%s'", countryBox.getValue());
          }

          Platform.runLater(() -> {
            FacilityView.refreshScrollBox(whereClause);
          });
        }).start();
      }
    });

    Button refreshButton = new Button("aktualisieren");
    refreshButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        FacilityView.refreshScrollBox("");
      }
    });

    HBox hBox = new HBox(field, btn);
    hBox.setSpacing(10);
    hBox.setAlignment(Pos.CENTER);
    hBox.setPrefHeight(30);

    HBox countryFiltering = new HBox(countryBox, comboButton);
    countryFiltering.setSpacing(10);
    countryFiltering.setAlignment(Pos.CENTER);
    countryFiltering.setPrefHeight(30);

    VBox vBox = new VBox(countryFiltering, hBox, refreshButton);
    vBox.setAlignment(Pos.CENTER);
    vBox.setPadding(new Insets(8));
    vBox.setSpacing(5);

    return vBox;
  }

  public static VBox makeInteractionBoxGoods() {
    ComboBox<String> filterCats = new ComboBox<>();

    filterCats.getItems().add("Alle");
    filterCats.setValue("Alle");

    new Thread(() -> {
      var categories = Client.getCategories();

      Platform.runLater(() -> {
        filterCats.getItems().addAll(categories);
      });
    }).start();

    Button btn = new Button("filtern");
    btn.setStyle("-fx-text-fill: white;" + "-fx-background-color: #0057b8;");
    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        new Thread(() -> {

          String whereClause;

          if (Global.getCurrentFacility() != null) {
            if (!filterCats.getValue().equalsIgnoreCase("alle")) {
              whereClause = String.format("WHERE facilities.facilityName = '%s' AND category = '%s'", Global.getCurrentFacility().getName(), filterCats.getValue());
            } else {
              whereClause = String.format("WHERE facilities.facilityName = '%s'", Global.getCurrentFacility().getName());
            }
          } else {
            if (!filterCats.getValue().equalsIgnoreCase("alle")) {
              System.out.println(filterCats.getValue());
              whereClause = String.format("WHERE category = '%s'", filterCats.getValue());
            } else {
              whereClause = "";
            }
          }

          Platform.runLater(() -> GoodsView.refreshScrollBox(whereClause));
        }).start();
      }
    });

    Button backButton = makeButtonBackwards(filterCats);
    if (Global.getCurrentFacility() == null)
      backButton.setDisable(true);

    HBox hBox = new HBox(backButton, filterCats, btn);
    hBox.setSpacing(10);
    hBox.setAlignment(Pos.CENTER);
    hBox.setPrefHeight(50);

    Button refreshButton = new Button("aktualisieren");
    refreshButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        GoodsView.refreshScrollBox("");
      }
    });

    VBox vBox = new VBox(hBox, refreshButton);
    vBox.setAlignment(Pos.CENTER);

    return vBox;
  }

  public static Button makeButtonBackwards(ComboBox<String> cats) {
    Button btn = new Button("zurück");

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        cats.setValue("Alle");
        Global.getCurrentFacility().filterGoods("alle");
        Global.setScene(Global.getScenes("facility"));
      }
    });

    return btn;
  }

  public static Button makeButtonBackwards() {
    Button btn = new Button("zurück");

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        Global.setScene(Global.getScenes("goods"));
      }
    });

    return btn;
  }
}

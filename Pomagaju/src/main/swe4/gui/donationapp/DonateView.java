package swe4.gui.donationapp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.StringConverter;
import swe4.pomagajuclasses.Donations;
import swe4.pomagajuclasses.Facility;
import swe4.pomagajuclasses.Goods;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;

public class DonateView {
  private static BorderPane borderPane = null;
  private Goods currentGood = Global.getCurrentGood();
  private Facility currentFacility = Global.getCurrentFacility();

  DonateView() {
    createScene();
  }

  private void createScene() {
    borderPane = new BorderPane();

    borderPane.setBottom(ReusableViewDesignMethods.makeBottomNav());
    borderPane.setTop(ReusableViewDesignMethods.makeBanner(Global.getCurrentGood().getIdentifier()));

    if (Global.getCurrentUser() == null) {
      Label error = new Label("Nur registrierte Nutzer können Spenden vorankündigen.");
      error.setStyle("-fx-text-fill: red");

      Button btn = new Button("Jetzt registrieren");
      btn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
          Global.setScene("register");
        }
      });

      VBox vBox = new VBox(error, btn);
      vBox.setSpacing(10);

      borderPane.setCenter(vBox);
    } else {
      borderPane.setCenter(makeDonateForm());
    }

    Scene scene = new Scene(borderPane, 350, 620);
    Global.addScene("donate", scene);
    Global.setScene(scene);
    Global.showStage();
  }

  private VBox makeDonateForm() {
    DatePicker date = new DatePicker();
    date.setMaxWidth(180);
    date.setDayCellFactory(picker -> new DateCell() {
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        LocalDate today = LocalDate.now();

        setDisable(empty || date.compareTo(today) <= 0 );
      }
    });

    TextField time = new TextField();
    time.setMaxWidth(180);
    time.setPromptText("z.B.: 10:10");
    TextField quant = new TextField();
    quant.setMaxWidth(180);

    ComboBox<Facility> facilityComboBox = new ComboBox<>();


    facilityComboBox.setMaxWidth(180);

    new Thread(() -> {
      if (Global.getCurrentFacility() == null) {
        String whereClause = String.format("WHERE goods.identifier = '%s'", Global.getCurrentGood().getIdentifier());
        facilityComboBox.getItems().addAll(Client.getFacilitiesWhere(whereClause));
      } else {
        facilityComboBox.setValue(Global.getCurrentFacility());
      }

      var facilities = Client.getFacilities();
      var currentfacility = Global.getCurrentFacility();

      Platform.runLater(() -> {
        if (currentfacility == null) {
          facilityComboBox.getItems().addAll(facilities);
        } else {
          facilityComboBox.getItems().add(currentfacility);
          facilityComboBox.setValue(currentfacility);
        }

        makeStringConverter(facilityComboBox);
      });
    }).start();

    Label dateLabel = makeLabel("Datum auswählen: ");
    Label timeLabel = makeLabel("Zeit auswählen: ");
    Label quantLabel = makeLabel("Menge auswählen: ");
    Label facilityLabel = makeLabel("Annahmestelle auswählen: ");

    TextFlow flow = new TextFlow();

    Label thanksLabel = new Label("");

    VBox vBox = new VBox(dateLabel, date, timeLabel, time, quantLabel, quant, facilityLabel, facilityComboBox, makeLabel("Ihr Token, zur eindeutigen "), makeLabel("Identifizierung Ihrer Spende."), flow, thanksLabel, makeDonateButton(date, time, quant, facilityComboBox, flow, thanksLabel));
    vBox.setSpacing(8);
    vBox.setPadding(new Insets(8));

    return vBox;
  }

  public static void makeStringConverter(ComboBox<Facility> facilityComboBox) {
    StringConverter<Facility> converter = new StringConverter<Facility>() {
      @Override
      public String toString(Facility facility) {
        if (facility != null) {
          return facility.getName();
        }

        return null;
      }

      @Override
      public Facility fromString(String s) {
        AtomicReference<Facility> f = null;

        new Thread(() -> {
          f.set(Client.getFacility(s));
        }).start();

        return f.get();
      }
    };

    facilityComboBox.setConverter(converter);
  }

  private Label makeLabel(String s) {
    Label label = new Label(s);
    label.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 16;");

    return label;
  }

  private HBox makeDonateButton(DatePicker date, TextField time, TextField quant, ComboBox<Facility> fBox, TextFlow flow, Label thanks) {
    Button btn = new Button("Spenden");

    btn.setStyle("-fx-background-color: #ffd700;" + "-fx-text-fill: black;");

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        if (date.getValue() != null && date.getValue().isAfter(LocalDate.now())
            && time.getText() != null && !time.getText().trim().isEmpty()
            && quant.getText() != null && !quant.getText().trim().isEmpty()) {

          String str = date.getValue().toString() + " " + time.getText() + ":00";

          LocalDateTime dateTime = LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

          String token = generateNewToken();

          new Thread(() -> {
            Client.addDonation(fBox.getValue(), new Donations(Global.getCurrentUser().getMail(), Global.getCurrentGood(), Integer.parseInt(quant.getText()), dateTime, generateNewToken()));

            Platform.runLater(() -> {
              Text success = new Text(token);
              thanks.setText("Vielen Dank für Ihre Spende!");
              flow.getChildren().addAll(success);
              btn.setDisable(true);
            });
          }).start();
        } else {
          Text error = new Text("Bitte füllen Sie alle Felder aus.");
          error.setStyle("-fx-text-fill: red");
          flow.getChildren().add(error);
        }
      }
    });

    HBox hBox = new HBox(ReusableViewDesignMethods.makeButtonBackwards(), btn);
    hBox.setSpacing(30);
    hBox.setAlignment(Pos.CENTER);

    return hBox;
  }

  private static final SecureRandom secureRandom = new SecureRandom();
  private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

  private static String generateNewToken() {
    byte[] randomBytes = new byte[24];
    secureRandom.nextBytes(randomBytes);
    return base64Encoder.encodeToString(randomBytes);
  }
}

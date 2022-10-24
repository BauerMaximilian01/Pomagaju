package swe4.gui.adminapp;

import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import swe4.Utility;
import swe4.pomagajuclasses.User;

import java.io.File;

public class AccountView {
  private Scene accountScene = null;

  AccountView() {
    createScene();
    Global.addScene(accountScene, "account");
  }

  /* ----------------------------------------- */
  /*                Account View               */
  /* ----------------------------------------- */

  private void createScene() {
    accountScene = new Scene(makeAccountLayout(), 800, 400);
  }

  private BorderPane makeAccountLayout() {
    BorderPane borderPane = new BorderPane();

    borderPane.setTop(makeViewNav());
    borderPane.setCenter(makeAccountDetailsBox());

    return borderPane;
  }

  private HBox makeViewNav() {
    return ReusableViewDesignMethods.createNav();
  }

  private HBox makeAccountDetailsBox() {
    HBox hBox = new HBox();
    hBox.setPadding(new Insets(20));
    hBox.setSpacing(10);

    VBox accountDet = makeAccountDetails();
    VBox newUser = makeAddNewUserForm();

    hBox.getChildren().add(accountDet);
    hBox.getChildren().add(newUser);

    return hBox;
  }

  private VBox makeAccountDetails() {
    VBox vBox = new VBox();
    vBox.setSpacing(10);

    HBox userInfo = makeUserInfo();
    HBox facilityInfo = makeFacilityInfo();

    VBox.setVgrow(userInfo, Priority.ALWAYS);
    VBox.setVgrow(facilityInfo, Priority.ALWAYS);

    vBox.getChildren().addAll(userInfo, facilityInfo);
    return vBox;
  }

  private HBox makeUserInfo() {
    HBox userInfo = new HBox();

    Label nameOfUser = new Label(Global.getCurrentUser().getName());
    nameOfUser.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 20;");
    ImageView accountIcon = new ImageView(new Image(new File("src/resources/account_icon_black.png").toURI().toString(), 60, 60, true, false));

    Button logoutBtn = new Button("Logout");
    logoutBtn.setStyle("-fx-background-color: #0057b8;" + "-fx-font-weight: bold;" + "-fx-text-fill: white");
    logoutBtn.setEffect(new DropShadow());
    logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        Global.setCurrentUser(null);
        Global.setCurrentFacility(null);

        Global.getStage().close();
        new LoginView().showStage();
      }
    });

    userInfo.getChildren().addAll(new VBox(accountIcon), new VBox(nameOfUser, logoutBtn));

    userInfo.setStyle("-fx-background-color: white");
    userInfo.setEffect(new DropShadow());
    userInfo.setMinSize(350, 100);
    userInfo.setPadding(new Insets(15));
    userInfo.setSpacing(8);

    return userInfo;
  }

  private HBox makeFacilityInfo() {
    HBox facilityInfo = new HBox();

    Label nameOfFacility = new Label(Global.getCurrentFacility().getName());
    Label addressOfFacility = new Label(Global.getCurrentFacility().getAddressAsString());
    nameOfFacility.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 20;");
    addressOfFacility.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 20;");
    ImageView facilityIcon = new ImageView(new Image(new File("src/resources/building_icon_black.png").toURI().toString(), 60, 60, true, false));

    facilityInfo.getChildren().addAll(new VBox(facilityIcon), new VBox(nameOfFacility, addressOfFacility));

    facilityInfo.setStyle("-fx-background-color: white");
    facilityInfo.setEffect(new DropShadow());
    facilityInfo.setMinSize(450, 100);
    facilityInfo.setPadding(new Insets(15));

    return facilityInfo;
  }

  private VBox makeAddNewUserForm() {
    VBox vBox = new VBox(makeUserForm());
    return vBox;
  }

  private VBox makeUserForm() {
    Label firstName = new Label("Vorname");
    Label lastName = new Label("Nachname");
    Label userName = new Label("Benutzername");
    Label passWord = new Label("Passwort");

    TextField firstNameField = new TextField();
    TextField lastNameField = new TextField();
    TextField userNameField = new TextField();
    TextField passWordField = new TextField();

    Button newUserBtn = new Button("Benutzer anlegen");
    newUserBtn.setEffect(new DropShadow());
    newUserBtn.setStyle("-fx-background-color: #ffd700;" + "-fx-text-fill: black;" + "-fx-font-weight: bold;");
    newUserBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        if (firstNameField.getText() != null && !firstNameField.getText().trim().isEmpty()
            && lastNameField.getText() != null && !lastNameField.getText().trim().isEmpty()
            && userNameField.getText() != null && !userNameField.getText().trim().isEmpty()
            && passWordField.getText() != null && !passWordField.getText().trim().isEmpty()) {

          new Thread (() -> {
            var addedUser = Client.addUser(new User(firstNameField.getText(), lastNameField.getText(), userNameField.getText(), passWordField.getText()));

            Platform.runLater(() -> {
              if (addedUser) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("");
                a.setContentText("Der Benutzer wurde erfolgreich erstellt.");
                a.showAndWait();
              } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setContentText("Der Benutzername existiert bereits.");
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

    VBox vBox = new VBox();
    vBox.setSpacing(10);
    vBox.setPadding(new Insets(10));
    vBox.setMinSize(300, 200);
    vBox.setStyle("-fx-background-color: white");
    vBox.setEffect(new DropShadow());

    vBox.getChildren().addAll(firstName, firstNameField, lastName, lastNameField, userName, userNameField, passWord, passWordField, newUserBtn);

    return vBox;
  }
}

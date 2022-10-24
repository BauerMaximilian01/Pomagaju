package swe4.gui.adminapp;

import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import swe4.Utility;
import swe4.gui.donationapp.DonateView;
import swe4.pomagajuclasses.Facility;
import swe4.pomagajuclasses.User;

import java.io.File;
import java.io.Serializable;

public class LoginView implements Serializable {
  private GoodsView admin = null;
  private Scene loginScene = null;
  private Stage loginStage = null;

  LoginView() {
    createScene();
  }

  public Stage getStage() {
    return loginStage;
  }

  private void initScenes() {
    new GoodsView().showStage();
    new DonationView();
    new FacilityView();
    new AccountView();
  }

  /* ----------------------------------------- */
  /*             Login View                    */
  /* ----------------------------------------- */

  public void showStage() {
    loginStage = new Stage();
    loginStage.setScene(loginScene);

    loginStage.show();
    loginStage.setResizable(false);
    loginStage.setTitle("Pomagaju");
  }

  public void closeStage() {
    loginStage.close();
  }

  private void createScene() {
    loginScene = new Scene(makeLogin(), 800, 400);
  }

  private FlowPane makeLogin() {
    FlowPane flow = new FlowPane();
    GridPane grid = makeLoginForm();
    grid.setAlignment(Pos.CENTER);
    flow.getChildren().add(grid);

    HBox imageBox = new HBox();
    imageBox.setMinSize(400, 400);
    imageBox.setBackground(new Background(new BackgroundFill(Color.rgb(197, 204, 255), CornerRadii.EMPTY, Insets.EMPTY)));
    imageBox.setAlignment(Pos.CENTER);
    ImageView imageView = new ImageView(new Image(new File("src/resources/ukraine.png").toURI().toString(), 300, 200, true, false));
    imageBox.getChildren().add(imageView);

    flow.getChildren().add(imageBox);
    return flow;
  }

  private synchronized GridPane makeLoginForm() {
    GridPane grid = new GridPane();
    grid.setMinSize(400, 400);
    grid.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    grid.setPadding(new Insets(30));
    grid.setHgap(10);
    grid.setVgap(10);

    ComboBox<Facility> facilities = new ComboBox<>();

    new Thread(() -> {
      var facilitiesList = Client.getFacilities();

      Platform.runLater(() -> {
        facilities.getItems().addAll(facilitiesList);
      });
    }).start();

    DonateView.makeStringConverter(facilities);
    facilities.setMinWidth(330);

    TextField username = new TextField();
    username.setMinWidth(330);
    PasswordField passwd = new PasswordField();
    username.setMinWidth(330);

    Label loginLabel = new Label("Login");
    loginLabel.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 20;");

    Button loginButton = new Button("Login");
    loginButton.setPrefWidth(100);
    loginButton.setBackground(new Background(new BackgroundFill(Color.rgb(255, 215, 0), CornerRadii.EMPTY, Insets.EMPTY)));
    GridPane.setHalignment(loginButton, HPos.CENTER);
    loginButton.setEffect(new DropShadow(2.0, Color.BLACK));
    loginButton.setDefaultButton(true);

    loginButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        loginButton.arm();
        Global.setCurrentFacility(facilities.getValue());

        if (facilities.getValue() != null) {

          new Thread(() -> {
            var success = Client.validateInput(username.getText(), passwd.getText());

            User u = null;

            if (success)
              u = Client.getUser(username.getText());

            User finalU = u;

            Platform.runLater(() -> {
              if (success) {

                Global.setCurrentUser(finalU);

                Global.setCurrentFacility(facilities.getValue());
                closeStage();

                initScenes();

                username.setText("");
                passwd.setText("");
                facilities.getItems().clear();

              } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Login fehlgeschlagen");
                a.setContentText("Der Benutzername oder das Passwort ist falsch.");
                a.showAndWait();
              }
            });
          }).start();


        } else {
          Alert a = new Alert(Alert.AlertType.ERROR);
          a.setTitle("Login fehlgeschlagen");
          a.setContentText("Bitte wählen Sie eine Annahmestelle aus.");
          a.showAndWait();
        }
      }
    });

    grid.add(loginLabel, 0, 0);
    grid.add(new Label("Annahmestelle auswählen*"), 0, 1);
    grid.add(facilities, 0, 2);
    grid.add(new Label("Benutzername*"), 0, 3);
    grid.add(username, 0, 4);
    grid.add(new Label("Passwort*"), 0, 5);
    grid.add(passwd, 0, 6);
    grid.add(loginButton, 0, 7, 2, 1);

    return grid;
  }
}

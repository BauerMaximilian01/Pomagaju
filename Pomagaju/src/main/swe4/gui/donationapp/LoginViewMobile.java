package swe4.gui.donationapp;

import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import swe4.pomagajuclasses.User;

import java.io.File;

public class LoginViewMobile {
  LoginViewMobile() {
    createScene();
  }

  private void createScene() {
    VBox vBox = new VBox();
    vBox.getChildren().addAll(makeImageBanner(vBox), makeLoginForm());
    vBox.setSpacing(30);

    Scene scene = new Scene(vBox, 350, 620);
    Global.addScene("login", scene);
    Global.setScene(scene);
    Global.showStage();
  }

  private HBox makeImageBanner(VBox vBox) {
    HBox hBox = new HBox();

    hBox.setBackground(new Background(new BackgroundFill(Color.rgb(197, 204, 255), CornerRadii.EMPTY, Insets.EMPTY)));
    ImageView imageView = new ImageView(new Image(new File("src/resources/ukraine.png").toURI().toString(), 270, 170, true, false));
    hBox.getChildren().add(imageView);
    hBox.setAlignment(Pos.CENTER);
    hBox.setPrefSize(350, 250);

    return hBox;
  }

  private VBox makeLoginForm() {
    Label email = new Label("E-Mail*");
    Label password = new Label("Passwort*");

    TextField emailField = new TextField();
    TextField passwordField = new PasswordField();

    HBox hBox = new HBox(makeLoginButton(emailField, passwordField));
    hBox.setAlignment(Pos.CENTER);

    HBox registerLabel = new HBox(makeRegisterLabel());
    registerLabel.setAlignment(Pos.CENTER);
    HBox guestLabel = new HBox(makeGuestLabel());
    guestLabel.setAlignment(Pos.CENTER);

    VBox vBox = new VBox(email, emailField, password, passwordField, hBox, registerLabel, guestLabel);
    vBox.setSpacing(10);
    vBox.setPadding(new Insets(10));

    return vBox;
  }

  private Button makeLoginButton(TextField mail, TextField pw) {
    Button btn = new Button("Login");

    btn.setStyle("-fx-background-color: #ffd700;" + "-fx-text-fill: black;");
    btn.setPrefWidth(100);

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {

        new Thread(() -> {
          var success = Client.validateInputMail(mail.getText(), pw.getText());

          User u = null;
          if (success)
            u = Client.getUser(mail.getText());

          User finalU = u;

          Platform.runLater(() -> {
            if (success) {
              Global.setCurrentUser(finalU);
              mail.setText(null);
              pw.setText(null);
              new FacilityView();
            } else {
              mail.setStyle("-fx-border-color: red;");
              pw.setStyle("-fx-border-color: red;");
            }
          });
        }).start();
      }
    });

    return btn;
  }

  private TextFlow makeRegisterLabel() {
    TextFlow flow = new TextFlow();
    Text register = new Text("Noch keinen Account? Jetzt ");
    Text registerColor = new Text("registrieren.");

    registerColor.setStyle("-fx-text-fill: #0057b8");
    registerColor.setOnMouseClicked(mouseEvent -> {
      new RegisterViewMobile();
    });

    flow.getChildren().addAll(register, registerColor);

    return flow;
  }

  private TextFlow makeGuestLabel() {
    TextFlow flow = new TextFlow();
    Text guest = new Text("Als Gast ");
    Text guestColor = new Text("fortfahren.");

    guestColor.setStyle("-fx-text-fill: #0057b8");
    guestColor.setOnMouseClicked(mouseEvent -> {
      new FacilityView();
    });

    flow.getChildren().addAll(guest, guestColor);

    return flow;
  }
}

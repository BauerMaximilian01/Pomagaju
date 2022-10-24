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
import swe4.gui.donationapp.Client;
import swe4.pomagajuclasses.User;

import java.io.File;

public class RegisterViewMobile {
  RegisterViewMobile() {
    createScene();
  }

  private void createScene() {
    VBox vBox = new VBox();
    vBox.getChildren().addAll(makeImageBanner(vBox), makeRegisterForm());
    vBox.setSpacing(30);

    Scene scene = new Scene(vBox, 350, 620);
    Global.addScene("register", scene);
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

  private VBox makeRegisterForm() {
    Label email = new Label("E-Mail*");
    Label password = new Label("Passwort*");
    Label alreadyExists = new Label("");
    alreadyExists.setStyle("-fx-text-fill: red");

    TextField emailField = new TextField();
    TextField passwordField = new PasswordField();

    HBox hBox = new HBox(makeRegisterButton(emailField, passwordField, alreadyExists));
    hBox.setAlignment(Pos.CENTER);

    HBox loginLabel = new HBox(makeLoginLabel());
    loginLabel.setAlignment(Pos.CENTER);

    VBox vBox = new VBox(email, emailField, password, passwordField, alreadyExists, hBox, loginLabel);
    vBox.setSpacing(10);
    vBox.setPadding(new Insets(10));

    return vBox;
  }

  private Button makeRegisterButton(TextField mail, TextField pw, Label exists) {
    Button btn = new Button("Registrieren");

    btn.setStyle("-fx-background-color: #ffd700;" + "-fx-text-fill: black;");
    btn.setPrefWidth(100);

    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        if (mail.getText() != null && !mail.getText().trim().isEmpty()
            && pw.getText() != null && !pw.getText().trim().isEmpty()) {

          new Thread(() -> {
            var registered = Client.addUser(mail.getText(), pw.getText());
            User u = null;

            if (registered)
              u = Client.getUser(mail.getText());

            User finalU = u;

            Platform.runLater(() -> {
              if (registered) {
                Global.setCurrentUser(finalU);
                new FacilityView();
              } else {
                mail.setStyle("-fx-border-color: red;");
                pw.setStyle("-fx-border-color: red;");
                exists.setText("Dieser Nutzer existiert bereits.");
              }
            });
          }).start();

        } else {
          mail.setStyle("-fx-border-color: red;");
          pw.setStyle("-fx-border-color: red;");
          exists.setText("Bitte füllen Sie zuerst alle Felder aus.");
        }
      }
    });

    return btn;
  }

  private TextFlow makeLoginLabel() {
    TextFlow flow = new TextFlow();
    Text login = new Text("Sie haben bereits einen Account? Jetzt ");
    Text loginColor = new Text("einloggen.");

    loginColor.setStyle("-fx-text-fill: #0057b8");
    loginColor.setOnMouseClicked(mouseEvent -> {
      Global.setScene(Global.getScenes("login"));
    });

    flow.getChildren().addAll(login, loginColor);

    return flow;
  }
}

package swe4.gui.donationapp;

import java.util.logging.LogManager;

import javafx.application.Application;
import javafx.stage.Stage;

public class Pomagaju extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    Client.initService();

    new LoginViewMobile();
  }

  public static void main(String[] args) {
    try {
      LogManager.getLogManager().reset();   // turn off JDK logging
      launch(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


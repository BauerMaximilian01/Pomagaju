package swe4.gui.donationapp;

import javafx.scene.Scene;
import javafx.stage.Stage;
import swe4.pomagajuclasses.Facility;
import swe4.pomagajuclasses.Goods;
import swe4.pomagajuclasses.User;

import java.util.HashMap;
import java.util.Map;

public class Global {
  private static Stage stage = new Stage();
  private static Map<String, Scene> scenes = new HashMap<>();
  private static Facility currentFacility = null;
  private static Goods currentGood = null;
  private static User currentUser = null;

  public static Stage getStage() {
    return stage;
  }

  public static void setScene(Scene scene) {
    stage.setScene(scene);
  }

  public static void setScene(String s) {
    stage.setScene(scenes.get(s));
  }

  public static void showStage() {
    stage.show();
    stage.setResizable(false);
    stage.setTitle("Pomagaju");
  }

  public static void addScene(String s, Scene scene) {
    scenes.put(s, scene);
  }

  public static Scene getScenes(String s) {
    return scenes.get(s);
  }

  public static void setCurrentFacility(Facility f) {
    currentFacility = f;
  }

  public static void setCurrentGood(Goods g) {
    currentGood = g;
  }

  public static void setCurrentUser(User u) {
    currentUser = u;
  }

  public static Facility getCurrentFacility() {
    return currentFacility;
  }

  public static Goods getCurrentGood() {
    return currentGood;
  }

  public static User getCurrentUser() {
    return currentUser;
  }
}

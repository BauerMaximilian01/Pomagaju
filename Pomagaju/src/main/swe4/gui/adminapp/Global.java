package swe4.gui.adminapp;

import javafx.scene.Scene;
import javafx.stage.Stage;
import swe4.pomagajuclasses.Facility;
import swe4.pomagajuclasses.User;

import java.util.HashMap;
import java.util.Map;

public class Global {
  private static Facility currentFacility = null;
  private static User currentUser = null;
  private static Map<String, Scene> scenes = new HashMap<>();
  private static Stage stage = null;
  private static Stage loginStage = null;

  public static void setLoginStage(Stage s) {
    loginStage = s;
  }

  public static Stage getLoginStage() {
    return loginStage;
  }

  public static void addScene(Scene scene, String sceneName) {
    scenes.put(sceneName, scene);
  }

  public static void setStage(Stage s) {
    stage = s;
  }

  public static Scene getScene(String name) {
    return scenes.get(name);
  }

  public static Stage getStage() {
    return stage;
  }

  public static void setCurrentFacility(Facility f) {
    currentFacility = f;
  }

  public static void setCurrentUser(User u) {
    currentUser = u;
  }

  public static Facility getCurrentFacility() {
    return currentFacility;
  }

  public static User getCurrentUser() {
    return currentUser;
  }
}

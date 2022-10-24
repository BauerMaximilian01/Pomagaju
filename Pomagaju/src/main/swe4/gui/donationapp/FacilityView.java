package swe4.gui.donationapp;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import swe4.pomagajuclasses.Facility;

import java.util.Collection;

public class FacilityView {
  private static BorderPane borderPane = null;

  FacilityView() {
    createScene();
    new GoodsView(false);
  }

  private void createScene() {
    borderPane = new BorderPane();

    borderPane.setBottom(ReusableViewDesignMethods.makeBottomNav());
    borderPane.setTop(new VBox(ReusableViewDesignMethods.makeBanner("Annahmestellen"), ReusableViewDesignMethods.makeInteractionBoxFacility()));
    borderPane.setCenter(ReusableViewDesignMethods.makeScrollableContainer("facility", ""));

    Scene scene = new Scene(borderPane, 350, 620);
    Global.addScene("facility", scene);
    Global.setScene(scene);
    Global.showStage();
  }

  public static void refreshScrollBox(String whereClause) {
    borderPane.setCenter(ReusableViewDesignMethods.makeScrollableContainer("facility", whereClause));
  }
}

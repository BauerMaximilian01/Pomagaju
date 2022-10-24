package swe4.gui.donationapp;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GoodsView {
  private static BorderPane borderPane = null;

  GoodsView(boolean show) {
    createScene(show);
  }

  private void createScene(boolean show) {
    borderPane = new BorderPane();

    borderPane.setBottom(ReusableViewDesignMethods.makeBottomNav());
    borderPane.setTop(new VBox(ReusableViewDesignMethods.makeBanner("Hilfgüter"), ReusableViewDesignMethods.makeInteractionBoxGoods()));
    borderPane.setCenter(ReusableViewDesignMethods.makeScrollableContainer("goods", ""));

    Scene scene = new Scene(borderPane, 350, 620);
    Global.addScene("goods", scene);

    if (show) {
      Global.setScene(scene);
      Global.showStage();
    }
  }

  public static void refreshScrollBox(String whereClause) {
    borderPane.setCenter(ReusableViewDesignMethods.makeScrollableContainer("goods", whereClause));
  }

  public static void refreshInteractionBox() {
    borderPane.setTop(new VBox(ReusableViewDesignMethods.makeBanner("Hilfgüter"), ReusableViewDesignMethods.makeInteractionBoxGoods()));
  }
}

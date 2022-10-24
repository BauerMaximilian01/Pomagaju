//       $Id: Scribble.java 43451 2021-04-28 08:04:50Z p20068 $
//      $URL: https://svn01.fh-hagenberg.at/bin/cepheiden/Inhalt/Java/FX/Scribble-template/src/main/swe4/gui/Scribble.java $
// $Revision: 43451 $
//     $Date: 2021-04-28 10:04:50 +0200 (Mi., 28 Apr 2021) $
//   $Author: p20068 $
//   Creator: Peter Kulczycki
//  Creation: April 28, 2015
// Copyright: (c) 2021 Peter Kulczycki (peter.kulczycki<AT>fh-hagenberg.at)
//   License: This document contains proprietary information belonging to
//            University of Applied Sciences Upper Austria, Campus Hagenberg.
//            It is distributed under the Boost Software License (see
//            https://www.boost.org/users/license.html).

// Event handling #1 (class Scribble implements EventHandler <ActionEvent>)
// ------------------------------------------------------------------------
// EventHandler <ActionEvent> handler = this;
//
// Event handling #2 (implement an event handler in an inner class)
// ----------------------------------------------------------------
// EventHandler <ActionEvent> handler = new ButtonEventHandler ();
// btnPane.addEventHandler (ActionEvent.ACTION, new ButtonEventHandler ());
//
// Event handling #3 (implement an anonymous class)
// ------------------------------------------------
// EventHandler <ActionEvent> handler = new EventHandler <ActionEvent> () {
//    @Override
//    public void handle (ActionEvent event) {
//       ...
//    }
// };
//
// Event handling #4 (use a lambda expression)
// -------------------------------------------
// EventHandler <ActionEvent> handler = event -> {
//    ...
// };

package swe4.gui.adminapp;

import java.util.logging.LogManager;

import javafx.application.Application;
import javafx.stage.Stage;
import swe4.rmi.FacilitiesDatabase;

public class Pomagaju extends Application {

  @Override
  public void start(Stage stage) throws Exception {
   Client.initService();

    LoginView login = new LoginView();
    login.showStage();

    Global.setLoginStage(login.getStage());
  }

  public static void main(String[] args) {
    try {
      LogManager.getLogManager().reset();
      launch(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

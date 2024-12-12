package ui;

import explodingwildcats.TurnManager;

/**
 * Entry point for the Exploding Wildcats game, responsible for initializing the user interface.
 */
public class Main {

  /**
   * Launches the game, displays a welcome message,
   * and starts the game logic (according to the rule book).
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    TurnManager turnManager = new TurnManager("english");
    turnManager.setupGameEngine();
    turnManager.doGameLoop();
  }
}
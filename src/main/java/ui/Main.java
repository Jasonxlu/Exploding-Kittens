package ui;

import explodingwildcats.CardPileFactory;
import explodingwildcats.GameEngine;
import explodingwildcats.PlayerFactory;
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
    UserInterface ui = new UserInterface();
    PlayerFactory playerFactory = new PlayerFactory();
    CardPileFactory cardPileFactory = new CardPileFactory();
    GameEngine gameEngine = new GameEngine(playerFactory, cardPileFactory);

    TurnManager turnManager = new TurnManager(ui, gameEngine);
    turnManager.setupGameEngine();
  }
}
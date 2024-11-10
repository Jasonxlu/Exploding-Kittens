package ui;

import explodingwildcats.CardPile;
import explodingwildcats.GameEngine;
import explodingwildcats.Player;
import explodingwildcats.PlayerFactory;

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

    System.out.println("Welcome to Exploding Wildcats!");

    int numberOfPlayers = ui.getNumberOfPlayers();
    String[] playerNames = ui.getPlayerNames(numberOfPlayers);

    PlayerFactory playerFactory = new PlayerFactory();
    CardPile drawPile = new CardPile();
    GameEngine gameEngine = new GameEngine(playerFactory, drawPile);

    // Setting up game engine
    gameEngine.setUpPlayers(numberOfPlayers, playerNames);
    gameEngine.createDrawPile();
    gameEngine.dealDefuses();
    gameEngine.dealCards();
    gameEngine.insertExplodingCards();

    System.out.println("Let's get started!");

  }
}
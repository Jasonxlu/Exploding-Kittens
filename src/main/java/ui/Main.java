package ui;

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
    System.out.println("Number of players: " + numberOfPlayers);
  }
}
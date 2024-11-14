package ui;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Class responsible for handling user interactions in the user interface.
 */
public class UserInterface {

  private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

  /**
   * Prompts the user to enter the number of players for the game.
   * Must be an integer, and it must be between 2-6.
   *
   * @return the number of players as an integer between 2 and 6, inclusive
   */
  public int getNumberOfPlayers() {
    int numOfPlayers = 0;

    while (true) {
      System.out.print("Enter the number of players (between 2 and 6): ");
      try {
        numOfPlayers = Integer.parseInt(scanner.nextLine().trim());
        if (numOfPlayers >= 2 && numOfPlayers <= 6) {
          break;
        } else {
          System.out.println("Number of players must be between 2 and 6");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Number of players must be an integer.");
      }
    }

    return numOfPlayers;
  }

  /**
   * Prompts the user to enter valid names for each player in the game.
   * Reads a name for each player and stores it in an array.
   *
   * @param numberOfPlayers the number of players for which names are required
   * @return an array of player names with a length equal to the number of players
   */
  public String[] getPlayerNames(int numberOfPlayers) {
    String[] playerNames = new String[numberOfPlayers];
    for (int i = 0; i < numberOfPlayers; i++) {
      int playerNum = i + 1;
      String name;

      while (true) {
        System.out.printf("Enter player %d's name: ", playerNum);
        name = scanner.nextLine().trim();

        if (!name.isEmpty()) {
          break;
        } else {
          System.out.println("Name cannot be empty. Please enter a valid name.");
        }
      }

      playerNames[i] = name;
    }

    return playerNames;
  }
}

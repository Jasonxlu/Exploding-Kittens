package ui;

import java.util.Scanner;

/**
 * Class responsible for handling user interactions in the user interface.
 */
public class UserInterface {

  private final Scanner scanner = new Scanner(System.in);

  /**
   * Prompts the user to enter the number of players for the game.
   * Must be an integer, and it must be between 2-6.
   *
   * @return the number of players as an integer between 2 and 6, inclusive
   */
  public int getNumberOfPlayers() {
    // should ask user for an input
    // should be a number between 2-6
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
}

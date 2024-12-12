package ui;

import explodingwildcats.TurnManager;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

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
    String language = promptForLanguage();
    TurnManager turnManager = new TurnManager(language);
    turnManager.setupGameEngine();
    turnManager.doGameLoop();
  }

  /**
   * Prompts the user to select a language for the game.
   *
   * @return the selected language as a string ("english" or "spanish")
   */
  private static String promptForLanguage() {
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    String language = "";
    while (true) {
      System.out.println("Please select a language / Por favor, escoge un language:");
      System.out.println("1. Enter 1 for English");
      System.out.println("2. Ingresa 2 para Español");
      System.out.print("Enter your choice / Ingresa tu opción: ");
      String choice = scanner.nextLine().trim();

      switch (choice) {
        case "1":
          language = "english";
          break;
        case "2":
          language = "spanish";
          break;
        default:
          System.out.println("Invalid choice. Enter 1 or 2. / Opción inválida. Ingresa 1 o 2.");
          continue;
      }
      break;
    }
    return language;
  }
}
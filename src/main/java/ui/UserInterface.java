package ui;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Class responsible for handling user interactions in the user interface.
 */
public class UserInterface {

  private ResourceBundle bundle;

  private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

  /**
   * Public constructor for UserInterface.
   *
   * @param language chosen by the players at the start
   */
  public UserInterface(String language) {
    this.bundle = getResourceBundle(language);
  }

  /**
   * Sets the bundle for the specified language.
   *
   * @param language the players would like to play in
   */
  public ResourceBundle getResourceBundle(String language) {
    Locale locale;
    if (language.equalsIgnoreCase("spanish")) {
      locale = new Locale("es", "VE");
    } else {
      locale = Locale.ENGLISH;
    }

    return ResourceBundle.getBundle("ui_message", locale);
  }

  /**
   * Prompts the user to enter the number of players for the game.
   * Must be an integer, and it must be between 2-6.
   *
   * @return the number of players as an integer between 2 and 6, inclusive
   */
  public int getNumberOfPlayers() {
    int numOfPlayers = 0;

    while (true) {
      System.out.print(bundle.getString("prompt.enter_number_of_players"));
      try {
        numOfPlayers = Integer.parseInt(scanner.nextLine().trim());
        if (numOfPlayers >= 2 && numOfPlayers <= 6) {
          break;
        } else {
          System.out.println(bundle.getString("error.number_of_players_out_of_range"));
        }
      } catch (NumberFormatException e) {
        System.out.println(bundle.getString("error.invalid_number_of_players_input"));
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
        String prompt = MessageFormat.format(
                bundle.getString("prompt.enter_player_name"), playerNum);
        System.out.print(prompt);
        name = scanner.nextLine().trim();

        if (!name.isEmpty()) {
          if (Arrays.asList(playerNames).contains(name)) {
            System.out.println(bundle.getString("error.duplicate_name"));
            continue;
          }
          break;
        } else {
          System.out.println(bundle.getString("error.empty_name"));
        }
      }

      playerNames[i] = name;
    }

    return playerNames;
  }

  /**
   * Prints out the given string in a new line.
   *
   * @param s the string to print.
   */
  public void println(String s) {
    System.out.println(s);
  }

  /**
   * Prompts the user to reorder the numbers from 1-numToReorder.
   *
   * @param numToReorder the string to print.
   */
  public int[] promptNewOrder(int numToReorder) {
    System.out.println(bundle.getString("prompt.new_order_instruction"));
    int[] newOrder = new int[numToReorder];

    boolean orderSet = false;
    while (!orderSet) {
      orderSet = true;
      String[] enteredOrder = scanner.nextLine().trim().split(",");
      if (enteredOrder.length != numToReorder) {
        orderSet = false;
        System.out.println(MessageFormat.format(
                bundle.getString("error.invalid_order_length"), numToReorder));
        continue;
      }
      boolean[] seenNums = new boolean[numToReorder];
      try {
        for (int i = 0; i < enteredOrder.length; i++) {
          int num = Integer.parseInt(enteredOrder[i].trim());
          if (num < 1 || num > numToReorder) {
            orderSet = false;
            System.out.println(MessageFormat.format(
                    bundle.getString("error.number_out_of_range"), numToReorder));
            break;
          }
          seenNums[num - 1] = true;
          newOrder[i] = num;
        }
      } catch (NumberFormatException e) {
        orderSet = false;
        System.out.println(bundle.getString("error.invalid_number"));
        continue;
      }

      // check if all numbers in range were covered
      for (int i = 0; i < seenNums.length; i++) {
        if (!seenNums[i]) {
          orderSet = false;
          System.out.println(MessageFormat.format(
                  bundle.getString("error.missing_order_number"), i + 1));
          break;
        }
      }
    }
    return newOrder;
  }

  /**
   * Prompts the user to enter a valid new position for the
   * exploding kitten or the imploding cat in the draw pile.
   * The position must be between 0 (inclusive) and drawPileSize (exclusive).
   *
   * @param drawPileSize the size of the draw pile
   * @param explodingKitten determines whether it's asking for the placement
   *                        of an exploding kitten or imploding cat.
   *
   * @return a valid position within the range 0 to drawPileSize - 1
   */
  public int promptPlacementForExplodeOrImplode(int drawPileSize, boolean explodingKitten) {
    int placementIndex = -1;
    String cardName = explodingKitten
            ? bundle.getString("card.exploding_kitten")
            : bundle.getString("card.imploding_cat");

    while (true) {
      System.out.println(MessageFormat.format(
              bundle.getString("prompt.place_card_position"), cardName, drawPileSize - 1));

      try {
        placementIndex = Integer.parseInt(scanner.nextLine().trim());
        if (placementIndex >= 0 && placementIndex < drawPileSize) {
          break;
        } else {
          System.out.println(MessageFormat.format(
                  bundle.getString("error.invalid_position"), drawPileSize - 1));
        }
      } catch (NumberFormatException e) {
        System.out.println(bundle.getString("error.invalid_input_integer"));
      }
    }

    return placementIndex;
  }

  /**
   * Prompts the users for the name of a player who wants to play a Nope card.
   *
   * @param isRetry whether this was already called in the TurnManager promptNope loop.
   * @return the user's input.
   */
  public String promptNope(boolean isRetry) {
    String printMessage = isRetry
            ? bundle.getString("prompt.nope_retry")
            : bundle.getString("prompt.nope_play");

    System.out.println(printMessage);
    return scanner.nextLine().trim();
  }

  /**
   * Prints that the last user did not have a Nope card and prompts for a new player name.
   *
   * @param playerName the player that did not have a nope card.
   * @return the new user input.
   */
  public String printLastPlayerDidNotHaveNopeAndGetNewPlayer(String playerName) {
    System.out.println(MessageFormat.format(
            bundle.getString("error.nope_card_not_found"), playerName));
    return scanner.nextLine().trim();
  }

  /**
   * Prompts the player to play a card.
   *
   * @param rePrompting whether the user is reprompting.
   * @return the trimmed, lowercase input
   */
  public String promptPlayCard(boolean rePrompting) {
    String printMessage = rePrompting
            ? bundle.getString("prompt.play_card_reprompt")
            : bundle.getString("prompt.play_card");

    System.out.println(printMessage);
    return scanner.nextLine().trim().toLowerCase();
  }

  /**
   * Prompts the user for which cards to play.
   *
   * @param numToPlay the number of cat cards to prompt the user for
   * @return the trimmed, lowercase input
   */
  public String[] promptPlayComboCards(int numToPlay) {
    String[] cards = new String[numToPlay];
    System.out.println(bundle.getString("prompt.play_combo_cards"));
    for (int i = 0; i < numToPlay; i++) {
      System.out.printf(MessageFormat.format(
              bundle.getString("prompt.combo_card_number"), i + 1));
      cards[i] = scanner.nextLine().trim().toLowerCase();
    }
    return cards;
  }

  /**
   * Prompts the user to for a target player to attack.
   *
   * @param isRetry whether this is a retry prompt.
   * @return the user's input.
   */
  public String promptTargetedAttack(boolean isRetry) {
    String printMessage = isRetry
            ? bundle.getString("prompt.targeted_attack_retry")
            : bundle.getString("prompt.targeted_attack");

    System.out.println(printMessage);
    return scanner.nextLine().trim();
  }

  /**
   * Print and return the error message.
   *
   * @return the error message
   */
  public String printMustPlay2Or3CardsAsComboError() {
    final String message = bundle.getString("error.must_play_2_or_3_cards");
    System.out.println(message);
    return message;
  }

  /**
   * Print and return the error message.
   *
   * @return the error message
   */
  public String printMismatchUserCardsAndComboCount() {
    final String message = bundle.getString("error.mismatch_user_cards_combo_count");
    System.out.println(message);
    return message;
  }

  /**
   * Print and return the error message.
   *
   * @return the error message
   */
  public String printMismatchCardValidationCardsAndComboCount() {
    final String message = bundle.getString("error.mismatch_card_validation_combo_count");
    System.out.println(message);
    return message;
  }

  /**
   * Prompts the user to enter a player name for the 2 card combo.
   *
   * @param isRetry whether this is a retry prompt.
   * @return the user's input.
   */
  public String prompt2CardCombo(boolean isRetry) {
    String printMessage = isRetry
            ? bundle.getString("prompt.combo_target_name_2")
            : bundle.getString("prompt.combo_retry_name_2");
    System.out.println(printMessage);
    return scanner.nextLine().trim();
  }

  /**
   * Prompts the target user to enter a card name to give up for the 2 card combo.
   *
   * @param isRetry whether this is a retry prompt.
   * @return the user's input.
   */
  public String prompt2CardComboTarget(boolean isRetry) {
    String printMessage = isRetry
            ? bundle.getString("prompt.combo_retry_card_2")
            : bundle.getString("prompt.combo_target_card_2");
    System.out.println(printMessage);
    return scanner.nextLine().trim();
  }

  /**
   * Prints the error message for when the target player has no cards to steal.
   */
  public void printCardComboErrorTargetPlayerHasNoCards() {
    System.out.println(bundle.getString("error.target_player_no_cards"));
  }

  /**
   * Prompts the user to enter a player name for the 3 card combo.
   *
   * @param isRetry whether this is a retry prompt.
   * @return the user's input.
   */
  public String prompt3CardComboTargetName(boolean isRetry) {
    String printMessage = isRetry
            ? bundle.getString("prompt.combo_retry_name_3")
            : bundle.getString("prompt.combo_target_name_3");
    System.out.println(printMessage);
    return scanner.nextLine().trim();
  }

  /**
   * Prompts the current player to enter a card name to steal for the 3 card combo.
   *
   * @param isRetry whether this is a retry prompt.
   * @return the user's input.
   */
  public String prompt3CardComboTargetCard(boolean isRetry) {
    if (isRetry) {
      System.out.println("Invalid card. Please type in a valid card name.");
    } else {
      System.out.println("Please type in the name of the card you are asking to steal.");
    }
    return scanner.nextLine().trim();
  }

  /**
   * Prints out the players hand given the hand.
   *
   * @param hand the hand to print.
   */
  public void printPlayerHand(String[] hand) {
    for (int i = 0; i < hand.length; i++) {
      System.out.printf("Card #%d: %s%n", i + 1, hand[i]);
    }
  }

  /**
   * Prints the players in the game.
   *
   * @param players the players to print.
   */
  public void printPlayers(String[] players) {
    for (int i = 0; i < players.length; i++) {
      System.out.printf("Player #%d: %s%n", i + 1, players[i]);
    }
  }
}

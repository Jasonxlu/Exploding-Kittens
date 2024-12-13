package ui;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
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
          if (Arrays.asList(playerNames).contains(name)) {
            System.out.println("There already exists a player with that name.");
            continue;
          }
          break;
        } else {
          System.out.println("Name cannot be empty. Please enter a valid name.");
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
    System.out.println("Please enter the new order in the format: "
            + "<# of first card>, "
            + "<# of second card>, "
            + "<# of third card>.");
    int[] newOrder = new int[numToReorder];

    boolean orderSet = false;
    while (!orderSet) {
      orderSet = true;
      String enteredOrderStr = scanner.nextLine();
      String[] enteredOrder = enteredOrderStr.trim().split(",");
      System.out.println(enteredOrder.length);
      if (enteredOrder.length != numToReorder) {
        orderSet = false;
        System.out.println("Error: Please enter exactly " + numToReorder + " numbers.");
        continue;
      }
      boolean[] seenNums = new boolean[numToReorder];
      try {
        for (int i = 0; i < enteredOrder.length; i++) {
          int num = Integer.parseInt(enteredOrder[i].trim());
          if (num < 1 || num > numToReorder) {
            orderSet = false;
            System.out.println("You entered a number outside of the range to reorder (1-"
                    + numToReorder + ").");
            break;
          }
          seenNums[num - 1] = true;
          newOrder[i] = num;
        }
      } catch (NumberFormatException e) {
        orderSet = false;
        System.out.println("You entered an invalid number.");
        continue;
      }

      // check if all numbers in range were covered
      for (int i = 0; i < seenNums.length; i++) {
        if (!seenNums[i]) {
          orderSet = false;
          System.out.println("You forgot to set " + i + " in your new order.");
          break;
        }
      }
    }
    return newOrder;
  }

  /**
   * Prompts the user to enter a valid new position for the
   * exploding kitten or the imploding cat in the draw pile.
   * The position must be between 0 (inclusive) and drawPileSize (inclusive).
   *
   * @param drawPileSize the size of the draw pile
   * @param explodingKitten determines whether it's asking for the placement
   *                        of an exploding kitten or imploding cat.
   *
   * @return a valid position within the range 0 to drawPileSize
   */
  public int promptPlacementForExplodeOrImplode(int drawPileSize, boolean explodingKitten) {
    int placementIndex = -1;
    String cardName = explodingKitten ? "Exploding Kitten" : "Imploding Cat";

    while (true) {
      System.out.printf("Enter the position to place "
              + "the %s (0 = top of the pile, %d = bottom of the pile): ", cardName, drawPileSize);
      try {
        placementIndex = Integer.parseInt(scanner.nextLine().trim());
        if (placementIndex >= 0 && placementIndex <= drawPileSize) {
          break;
        } else {
          System.out.printf("Invalid position. "
                  + "Please enter a number between 0 and %d.%n", drawPileSize);
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid integer.");
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
    String printMessage;
    if (isRetry) {
      printMessage = "Unable to find that player. "
              + "Please type in a valid player name, "
              + "or hit enter if nobody wants to play a Nope.";
    } else {
      printMessage = "Does anyone want to play a Nope card? "
              + "If so, type in the player's name. "
              + "If not, hit enter.";
    }
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
    System.out.printf("%s does not have a Nope card in their hand. "
            + "Please type in a different player.%n", playerName);
    return scanner.nextLine().trim();
  }

  /**
   * Prompts the player to play a card.
   *
   * @param rePrompting whether the user is reprompting.
   * @return the trimmed, lowercase input
   */
  public String promptPlayCard(boolean rePrompting) {
    if (rePrompting) {
      System.out.print("Unable to parse input.\n"
              + "Hit enter to end your turn and draw a card, "
              + "or type the name of the card you want to play "
              + "(or type '2/3 cards'): ");
    } else {
      System.out.print("Do you want to play a card, or end your turn?\n"
              + "Hit enter to end your turn and draw a card, "
              + "or type the name of the card you want to play "
              + "(or type '2/3 cards'): ");
    }
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
    System.out.println("Which cards do you want to play?");
    for (int i = 0; i < numToPlay; i++) {
      System.out.printf("Card #%d: ", i + 1);
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
    if (isRetry) {
      System.out.println("Unable to find that player. Please type in a valid player name.");
    } else {
      System.out.println("Who would you like to attack? Please type in the player's name.");
    }
    return scanner.nextLine().trim();
  }

  /**
   * Print and return the error message.
   *
   * @return the error message
   */
  public String printMustPlay2Or3CardsAsComboError() {
    final String message = "You must play 2 or 3 cards as a combo.";
    System.out.println(message);
    return message;
  }

  /**
   * Print and return the error message.
   *
   * @return the error message
   */
  public String printMismatchUserCardsAndComboCount() {
    final String message = "Number of cards returned by user does not match combo count.";
    System.out.println(message);
    return message;
  }

  /**
   * Print and return the error message.
   *
   * @return the error message
   */
  public String printMismatchCardValidationCardsAndComboCount() {
    final String message = "Number of cards returned by card validation does not match "
            + "combo count.";
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
    if (isRetry) {
      System.out.println("Unable to find that player. Please type in a valid player name.");
    } else {
      System.out.println("Who would you like to target with your 2 card combo? "
              + "Please type in the player's name.");
    }
    return scanner.nextLine().trim();
  }

  /**
   * Prompts the target user to enter a card name to give up for the 2 card combo.
   *
   * @param isRetry whether this is a retry prompt.
   * @return the user's input.
   */
  public String prompt2CardComboTarget(boolean isRetry) {
    if (isRetry) {
      System.out.println("Invalid card. Please type in a valid card name.");
    } else {
      System.out.println("Please type in the name of the card you are giving up.");
    }
    return scanner.nextLine().trim();
  }

  /**
   * Prints the error message for when the target player has no cards to steal.
   */
  public void printCardComboErrorTargetPlayerHasNoCards() {
    System.out.println("The target player has no cards to steal.");
  }

  /**
   * Prompts the user to enter a player name for the 3 card combo.
   *
   * @param isRetry whether this is a retry prompt.
   * @return the user's input.
   */
  public String prompt3CardComboTargetName(boolean isRetry) {
    if (isRetry) {
      System.out.println("Unable to find that player. Please type in a valid player name.");
    } else {
      System.out.println("Who would you like to target with your 3 card combo? "
              + "Please type in the player's name.");
    }
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

  /**
<<<<<<< HEAD
  * Prints seeing the future.
  */
  public void printSeeingTheFuture() {
    System.out.println("Seeing the future...");
  }

  /**
   * Prints altering the future.
   */
  public void printAlteringTheFuture() {
    System.out.println("Altering the future...");
  }

  /**
   * Prints drawing a card.
   * @param fromBottom whether the player drew from the bottom.
   */
  public void printDrawingCard(boolean fromBottom) {
    if (fromBottom) {
      System.out.println("Drawing a card from the bottom...");
    } else {
      System.out.println("Drawing a card...");
    }
  }

  /**
   * Prints drawing an exploding kitten.
   * @param hasDefuse whether the player has a defuse card.
   */
  public void printDrawExplodingKitten(boolean hasDefuse) {
    System.out.println("Oh no! You drew an exploding kitten!");
    if (hasDefuse) {
      System.out.println("Using your defuse! "
              + "Choose where to place the exploding kitten in the draw pile.");
    }
  }

  /**
   * Prints drawing an imploding kitten.
   * @param isFaceUp whether it was face up already.
   */
  public void printDrawImplodingKitten(boolean isFaceUp) {
    if (isFaceUp) {
      System.out.println("Oh no! You drew an imploding kitten and it was face up!");
    } else {
      System.out.println("You drew an imploding kitten!"
              + "Choose where to place the imploding kitten face up in the draw pile.");
    }
  }

  /**
   * Prints adding a card to your hand.
   * @param cardName the card.
   */
  public void printAddingCardToHand(String cardName) {
    System.out.printf("Added a %s to your hand.%n", cardName);
  }

  /**
   * Prints reversing the turn order
   */
  public void printTurnOrderReversed() {
    System.out.println("Turn order was reversed.");
  }

  /**
   * Prints attacking.
   * @param numExtraCards the number of extra cards to draw after the attack.
   */
  public void printAttacking(int numExtraCards) {
    String cardPlural = numExtraCards == 1 ? "card" : "cards";
    System.out.printf("Attacking! The next player has to draw %d extra %s.%n", numExtraCards, cardPlural);
  }

  /**
   * Prints shuffling.
   */
  public void printShuffling() {
    System.out.println("Shuffling the draw pile...");
  }

  /**
   * Prints skipping.
   */
  public void printSkipping() {
    System.out.println("Skipping your turn...");
  }

  /**
   * Prints doing a targeted attack.
   */
  public void printDoingTargetedAttack() {
    System.out.println("Doing a targeted attack...");
  }

  /**
   * Prints doing a targeted attack result.
   * @param numExtraCards the number of extra cards to draw after the attack.
   */
  public void printTargetedAttackResult(int numExtraCards) {
    String cardPlural = numExtraCards == 1 ? "card" : "cards";
    System.out.printf("Did a targeted attack! The next player has to draw %d extra %s.%n",
            numExtraCards,
            cardPlural);
  }

  /**
   * Prints doing an n-card combo.
   * @param numCards number of cards.
   */
  public void printDoingCardCombo(int numCards) {
    System.out.printf("Doing a %d-card combo. Select a victim:%n", numCards);
  }

  /**
   * Prints doing a 3 card combo on a target.
   */
  public void printNopePlayed() {
    System.out.println("Played a Nope card!");
  }

  /**
   * Prints that the current player was eliminated.
   */
  public void printPlayerEliminated() {
    System.out.println("You have been eliminated.");
  }

  /**
   * Prints information about the current state of the game.
   *
   * @param playerName the player name.
   * @param playerNames all the players' names.
   * @param numExtraCardsToDraw the number of extra cards to draw.
   * @param isTurnOrderReversed whether the turn order is currently reversed.
   * @param printImplodingKittenIsNext whether to print that the imploding kitten is
   *                                   at the top of the draw pile.
   */
  public void printGameState(String playerName,
                             String[] playerNames,
                             int numExtraCardsToDraw,
                             boolean isTurnOrderReversed,
                             boolean printImplodingKittenIsNext) {
    System.out.printf("%s, it's your turn.%n", playerName);
    if (numExtraCardsToDraw > 0) {
      System.out.printf("You must take %d extra turns.%n", numExtraCardsToDraw);
    }

    if (isTurnOrderReversed) {
      for (String name : playerNames) {
        System.out.printf("<-- %s ", name);
      }
      System.out.print("<--");
    } else {
      System.out.print("--> ");
      for (String name : playerNames) {
        System.out.printf("%s --> ", name);
      }
    }
    System.out.println();

    if (printImplodingKittenIsNext) {
      System.out.println("The imploding kitten is at the top of the draw pile.");
    }
  }
}

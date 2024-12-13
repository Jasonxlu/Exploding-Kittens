package ui;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Class responsible for handling user interactions in the user interface.
 */
public class UserInterface {

  private final ResourceBundle bundle;
  private Map<String, String> inputMap;
  private Map<String, String> cardNameMap;
  private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

  /**
   * Public constructor for UserInterface.
   *
   * @param language chosen by the players at the start
   */
  public UserInterface(String language) {
    this.bundle = getResourceBundle(language);
    createInputMap();
    createCardNamesMap();
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
   * Creates input map for translating to english.
   */
  public void createInputMap() {
    inputMap = new HashMap<>();
    inputMap.put(bundle.getString("card.attack"), "attack");
    inputMap.put(bundle.getString("card.skip"), "skip");
    inputMap.put(bundle.getString("card.targeted_attack"), "targeted attack");
    inputMap.put(bundle.getString("card.shuffle"), "shuffle");
    inputMap.put(bundle.getString("card.see_the_future"), "see the future");
    inputMap.put(bundle.getString("card.reverse"), "reverse");
    inputMap.put(bundle.getString("card.draw_from_bottom"), "draw from bottom");
    inputMap.put(bundle.getString("card.alter_the_future"), "alter the future");
    inputMap.put(bundle.getString("input.2_cards"), "2 cards");
    inputMap.put(bundle.getString("input.3_cards"), "3 cards");
  }

  /**
   * Creates input map for translating to english.
   */
  public void createCardNamesMap() {
    cardNameMap = new HashMap<>();
    cardNameMap.put("ATTACK", bundle.getString("card.attack"));
    cardNameMap.put("DEFUSE", bundle.getString("card.defuse"));
    cardNameMap.put("NOPE", bundle.getString("card.nope"));
    cardNameMap.put("SEE_THE_FUTURE", bundle.getString("card.see_the_future"));
    cardNameMap.put("SHUFFLE", bundle.getString("card.shuffle"));
    cardNameMap.put("SKIP", bundle.getString("card.skip"));
    cardNameMap.put("EXPLODE", bundle.getString("card.explode"));
    cardNameMap.put("ALTER_THE_FUTURE", bundle.getString("card.alter_the_future"));
    cardNameMap.put("DRAW_FROM_BOTTOM", bundle.getString("card.draw_from_bottom"));
    cardNameMap.put("IMPLODE", bundle.getString("card.implode"));
    cardNameMap.put("REVERSE", bundle.getString("card.reverse"));
    cardNameMap.put("TARGETED_ATTACK", bundle.getString("card.targeted_attack"));
    cardNameMap.put("FERAL_CAT", bundle.getString("card.feral_cat"));
    cardNameMap.put("TACO_CAT", bundle.getString("card.taco_cat"));
    cardNameMap.put("HAIRY_POTATO_CAT", bundle.getString("card.hairy_potato_cat"));
    cardNameMap.put("BEARD_CAT", bundle.getString("card.beard_cat"));
    cardNameMap.put("RAINBOW_CAT", bundle.getString("card.rainbow_cat"));
  }

  /**
   * Prompts the user to enter the number of players for the game.
   * Must be an integer, and it must be between 2-6.
   *
   * @return the number of players as an integer between 2 and 6, inclusive
   */
  public int getNumberOfPlayers() {
    int numOfPlayers;

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
      String enteredOrderStr = scanner.nextLine();
      String[] enteredOrder = enteredOrderStr.trim().split(",");
      System.out.println(enteredOrder.length);
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
   * The position must be between 0 (inclusive) and drawPileSize (inclusive).
   *
   * @param drawPileSize the size of the draw pile
   * @param explodingKitten determines whether it's asking for the placement
   *                        of an exploding kitten or imploding cat.
   *
   * @return a valid position within the range 0 to drawPileSize
   */
  public int promptPlacementForExplodeOrImplode(int drawPileSize, boolean explodingKitten) {
    int placementIndex;
    String cardName = explodingKitten
            ? bundle.getString("card.exploding_kitten")
            : bundle.getString("card.imploding_cat");

    while (true) {
      System.out.println(MessageFormat.format(
              bundle.getString("prompt.place_card_position"), cardName, drawPileSize));

      try {
        placementIndex = Integer.parseInt(scanner.nextLine().trim());
        if (placementIndex >= 0 && placementIndex <= drawPileSize) {
          break;
        } else {
          System.out.println(MessageFormat.format(
                  bundle.getString("error.invalid_position"), drawPileSize));
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
   * @param rePrompting whether the user is re-prompting.
   * @return the trimmed, lowercase input
   */
  public String promptPlayCard(boolean rePrompting) {
    String printMessage = rePrompting
            ? bundle.getString("prompt.play_card_reprompt")
            : bundle.getString("prompt.play_card");
    System.out.println(printMessage);

    String card = scanner.nextLine().trim().toLowerCase();
    return normaliseInput(card);
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
      String card = scanner.nextLine().trim().toLowerCase();
      cards[i] = normaliseInput(card);
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
    String cardName = scanner.nextLine().toLowerCase().trim();
    return normaliseInput(cardName);
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
    String printMessage = isRetry
            ? bundle.getString("prompt.combo_retry_card_3")
            : bundle.getString("prompt.combo_target_card_3");
    System.out.println(printMessage);
    String cardName = scanner.nextLine().toLowerCase().trim();
    return normaliseInput(cardName);
  }

  /**
   * Prints out the players hand given the hand.
   *
   * @param hand the hand to print.
   */
  public void printPlayerHand(String[] hand) {
    for (int i = 0; i < hand.length; i++) {
      System.out.println(MessageFormat.format(
              bundle.getString("print.player_hand"), i + 1, cardNameMap.get(hand[i])));
    }
  }

  /**
   * Prints the players in the game.
   *
   * @param players the players to print.
   */
  public void printPlayers(String[] players) {
    for (int i = 0; i < players.length; i++) {
      System.out.println(MessageFormat.format(
              bundle.getString("print.players"), i + 1, players[i]));
    }
  }

  /**
   * Normalises the user input.
   * In other words, it takes the input in any language and turns it to english.
   *
   * @param userInput the string to be normalised
   * @return the english version of the string or the original input if there's no translation
   */
  public String normaliseInput(String userInput) {
    String normalised = inputMap.get(userInput.trim().toLowerCase());
    if (normalised == null) {
      return userInput;
    }
    return normalised;
  }

  /**
   * Prints that nopes cannot be played as regular cards.
   */
  public void printUnplayableCardErrorNope() {
    System.out.println(bundle.getString("error.unplayable_card_nope"));
  }

  /**
   * Prints that defuses cannot be played as regular cards.
   */
  public void printUnplayableCardErrorDefuse() {
    System.out.println(bundle.getString("error.unplayable_card_defuse"));
  }

  /**
   * Prints that cat cards cannot be played as regular cards.
   */
  public void printUnplayableCardErrorCatCard() {
    System.out.println(bundle.getString("error.unplayable_card_cat"));
  }

  /**
  * Prints seeing the future.
  */
  public void printSeeingTheFuture() {
    System.out.println(bundle.getString("feedback.seeing_future"));
  }

  /**
   * Prints altering the future.
   */
  public void printAlteringTheFuture() {
    System.out.println(bundle.getString("feedback.altering_future"));
  }

  /**
   * Prints drawing a card.
   *
   * @param fromBottom whether the player drew from the bottom.
   */
  public void printDrawingCard(boolean fromBottom) {
    if (fromBottom) {
      System.out.println(bundle.getString("feedback.drawing_from_bottom"));
    } else {
      System.out.println(bundle.getString("feedback.drawing"));
    }
  }

  /**
   * Prints drawing an exploding kitten.
   *
   * @param hasDefuse whether the player has a defuse card.
   */
  public void printDrawExplodingKitten(boolean hasDefuse) {
    System.out.println(bundle.getString("feedback.drew_exploding"));
    if (hasDefuse) {
      System.out.println(bundle.getString("feedback.using_defuse"));
    }
  }

  /**
   * Prints drawing an imploding kitten.
   *
   * @param isFaceUp whether it was face up already.
   */
  public void printDrawImplodingKitten(boolean isFaceUp) {
    if (isFaceUp) {
      System.out.println(bundle.getString("feedback.drew_face_up_imploding"));
    } else {
      System.out.println(bundle.getString("feedback.drew_face_down_imploding"));
    }
  }

  /**
   * Prints adding a card to your hand.
   *
   * @param cardName the card.
   */
  public void printAddingCardToHand(String cardName) {
    System.out.println(MessageFormat.format(
            bundle.getString("feedback.added_card_to_hand"), cardNameMap.get(cardName)));
  }

  /**
   * Prints reversing the turn order.
   */
  public void printTurnOrderReversed() {
    System.out.println(bundle.getString("feedback.reversed_order"));
  }

  /**
   * Prints attacking.
   *
   * @param numExtraCards the number of extra cards to draw after the attack.
   */
  public void printAttacking(int numExtraCards) {
    String cardPlural = numExtraCards == 1
            ? bundle.getString("feedback.card_singular")
            : bundle.getString("feedback.card_plural");
    System.out.println(MessageFormat.format(
            bundle.getString("feedback.attacking"), numExtraCards, cardPlural));
  }

  /**
   * Prints shuffling.
   */
  public void printShuffling() {
    System.out.println(bundle.getString("feedback.shuffling"));
  }

  /**
   * Prints skipping.
   */
  public void printSkipping() {
    System.out.println(bundle.getString("feedback.skipping"));
  }

  /**
   * Prints doing a targeted attack.
   */
  public void printDoingTargetedAttack() {
    System.out.println(bundle.getString("feedback.targeted_attack"));
  }

  /**
   * Prints doing a targeted attack result.
   *
   * @param numExtraCards the number of extra cards to draw after the attack.
   */
  public void printTargetedAttackResult(int numExtraCards) {
    String cardPlural = numExtraCards == 1 ? "card" : "cards";
    System.out.println(MessageFormat.format(
            bundle.getString("feedback.targeted_attack_result"), numExtraCards, cardPlural));
  }

  /**
   * Prints doing an n-card combo.
   *
   * @param numCards number of cards.
   */
  public void printDoingCardCombo(int numCards) {
    System.out.println(MessageFormat.format(
            bundle.getString("feedback.combo"), numCards));
  }

  /**
   * Prints the error message from a validation exception message.
   *
   * @param exceptionMessage the exception's message.
   */
  public void printValidateComboCardErrorMessage(String exceptionMessage) {
    switch (exceptionMessage) {
      case "Not a valid combo size.":
        System.out.println(bundle.getString("combo_error.invalid_size"));
        break;
      case "Player does not have the input cards.":
        System.out.println(bundle.getString("combo_error.cards_missing"));
        break;
      case "Cat cards must be matching or feral.":
        System.out.println(bundle.getString("combo_error.not_matching_or_feral"));
        break;
      case "Cards must be matching.":
        System.out.println(bundle.getString("combo_error.not_matching"));
        break;
      default:
        break;
    }
  }

  /**
   * Prints doing a 3 card combo on a target.
   */
  public void printNopePlayed() {
    System.out.println(bundle.getString("feedback.nope"));
  }

  /**
   * Prints that the current player was eliminated.
   */
  public void printPlayerEliminated() {
    System.out.println(bundle.getString("feedback.elimination"));
  }

  /**
   * Prints the peeked cards.
   *
   * @param cardNames list of card names
   */
  public void printPeekedCards(String[] cardNames) {
    StringBuilder message = new StringBuilder();

    if (cardNames.length > 0) {
      message.append(bundle.getString("label.top"))
              .append(": ")
              .append(cardNameMap.get(cardNames[0]));
    }
    if (cardNames.length > 1) {
      message.append(", ")
              .append(bundle.getString("label.second"))
              .append(": ").append(cardNameMap.get(cardNames[1]));
    }
    if (cardNames.length == 3) {
      message.append(", ")
              .append(bundle.getString("label.third"))
              .append(": ").append(cardNameMap.get(cardNames[2]));
    }

    System.out.println(message);
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
    System.out.println(MessageFormat.format(
            bundle.getString("game_state.current_turn"), playerName));
    if (numExtraCardsToDraw > 0) {
      System.out.println(MessageFormat.format(
              bundle.getString("game_state.extra_turns"), numExtraCardsToDraw));
    }

    System.out.print(bundle.getString("game_state.general_order"));
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
      System.out.println(bundle.getString("game_state.imploding_cat_warning"));
    }
  }

  /**
   * Celebrates the winner.
   *
   * @param winnerName the player name who won
   */
  public void printWinner(String winnerName) {
    System.out.println(MessageFormat.format(
            bundle.getString("game_state.winner"), winnerName.toUpperCase()));
  }
}

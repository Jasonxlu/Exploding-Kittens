package explodingwildcats;

import java.util.List;
import java.util.NoSuchElementException;
import ui.UserInterface;

/**
 * Class responsible for doing turns.
 */
public class TurnManager {

  private final UserInterface ui;
  final GameEngine gameEngine;
  int numExtraCardsToDraw; // Package private to support unit testing.
  int currPlayerIndex; // Package private to support unit testing.
  boolean isImplodingCatFaceUp = false;
  boolean playerTurnHasEnded = false;

  /**
   * Public constructor for TurnManager.
   *
   * @param language takes in the language the players chose.
   */
  public TurnManager(String language) {
    this.ui = new UserInterface(language);
    PlayerFactory playerFactory = new PlayerFactory();
    CardPileFactory cardPileFactory = new CardPileFactory();

    this.gameEngine = new GameEngine(playerFactory, cardPileFactory);

    this.numExtraCardsToDraw = 0;
  }

  /**
   * Package private constructor for TurnManager, only mocking the UI.
   * (For the purpose of BDD.)
   *
   */
  TurnManager(UserInterface ui) {
    this.ui = ui;
    PlayerFactory playerFactory = new PlayerFactory();
    CardPileFactory cardPileFactory = new CardPileFactory();

    this.gameEngine = new GameEngine(playerFactory, cardPileFactory);

    this.numExtraCardsToDraw = 0;
  }

  /**
   * Package private constructor for TurnManager.
   * Having a different package private one avoids spotbugs storing mutable object error.
   *
   * @param ui user interface for printing.
   * @param gameEngine game engine for running the game.
   */
  TurnManager(UserInterface ui, GameEngine gameEngine) {
    this.ui = ui;
    this.gameEngine = gameEngine;

    this.numExtraCardsToDraw = 0;
  }

  /**
   * Sets up the game engine.
   */
  public void setupGameEngine() {
    int numberOfPlayers = ui.getNumberOfPlayers();
    if (numberOfPlayers < 2 || numberOfPlayers > 6) {
      throw new IllegalArgumentException("Invalid number of players.");
    }
    String[] playerNames = ui.getPlayerNames(numberOfPlayers);
    if (numberOfPlayers != playerNames.length) {
      throw new IllegalArgumentException("Invalid number of player names.");
    }

    gameEngine.setUpPlayers(numberOfPlayers, playerNames);
    gameEngine.createDrawPile();
    gameEngine.dealDefuses();
    gameEngine.shuffleDrawPile();
    gameEngine.dealCards();
    gameEngine.insertExplodingAndImplodingCards();
    gameEngine.shuffleDrawPile();
  }

  /**
   * Does the main game loop.
   */
  public void doGameLoop() {
    while (!gameEngine.isGameOver()) {
      playCardLoop();
    }

    String winnerName = gameEngine.getPlayerByIndex(currPlayerIndex).getName();
    ui.printWinner(winnerName);
  }

  /**
   * Prompts if the current player wants to play a card w/ UI.promptPlayCard().
   *
   */
  public void playCardLoop() {
    playerTurnHasEnded = false;
    boolean shouldReprompt = false;
    // advanceTurn will set playerTurnHasEnded to false.
    while (!playerTurnHasEnded) {
      printTurnInfo();
      printPlayerHand(currPlayerIndex);
      String userInputCard = ui.promptPlayCard(shouldReprompt);
      if (userInputCard.isEmpty()) {
        endTurn(false);
        shouldReprompt = false;
        continue;
      }

      if (userInputCard.equals("2 cards")) {
        shouldReprompt = promptAndPlayCombo(2);
        continue;
      } else if (userInputCard.equals("3 cards")) {
        shouldReprompt = promptAndPlayCombo(3);
        continue;
      }

      Card cardToPlay;
      try {
        cardToPlay = getPlayableCard(userInputCard);
      } catch (Exception originalCardChosenException) {
        // this means the player had an invalid input.
        shouldReprompt = true;
        continue;
      }
      boolean canPlayCard = gameEngine.playerHasCard(cardToPlay, currPlayerIndex);
      if (!canPlayCard) {
        shouldReprompt = true;
      } else {
        // check if anyone wants to play a nope before doing the card's effect.
        if (promptPlayNope()) {
          shouldReprompt = false;
          continue;
        }
        gameEngine.removeCardFromPlayer(cardToPlay, currPlayerIndex);
        switch (cardToPlay) {
          case ATTACK:
            doAttack();
            break;
          case SKIP:
            doSkip();
            break;
          case TARGETED_ATTACK:
            doTargetedAttack();
            break;
          case SHUFFLE:
            doShuffle();
            break;
          case SEE_THE_FUTURE:
            doSeeTheFuture();
            break;
          case REVERSE:
            doReverse();
            break;
          case DRAW_FROM_BOTTOM:
            doDrawFromBottom();
            break;
          case ALTER_THE_FUTURE:
            doAlterTheFuture();
            break;
          default:
            throw new IllegalArgumentException(
                    "A card was played that should not have been played.");
        }
        shouldReprompt = false;
      }
    }
  }


  /**
   * Get the card by the cardName. If it's not playable, throw an exception.
   * Package private for unit testing.
   *
   * @param cardName the string card name.
   * @return the Card if it is playable.
   */
  Card getPlayableCard(String cardName) {
    Card card = gameEngine.getCardByName(cardName);
    switch (card) {
      case NOPE:
        ui.printUnplayableCardErrorNope();
        throw new IllegalArgumentException("You cannot play a nope right now.");
      case DEFUSE:
        ui.printUnplayableCardErrorDefuse();
        throw new IllegalArgumentException("You cannot play a defuse right now.");
      case TACO_CAT:
      case BEARD_CAT:
      case RAINBOW_CAT:
      case FERAL_CAT:
      case HAIRY_POTATO_CAT:
        ui.printUnplayableCardErrorCatCard();
        throw new IllegalArgumentException("You must play a cat card as a combo.");
      case EXPLODE:
      case IMPLODE:
        throw new IllegalArgumentException("You cannot play an exploding/imploding kitten.");
      default:
        return card;
    }
  }

  /**
   * Prints information about the current turn.
   */
  public void printTurnInfo() {
    Player player = gameEngine.getPlayerByIndex(currPlayerIndex);
    String playerName = player.getName();
    String[] playerNames = gameEngine.getPlayers().stream()
            .map(Player::getName).toArray(String[]::new);
    boolean printImplodingIsNext = false;
    if (isImplodingCatFaceUp) {
      // check if imploding kitten is the top card.
      Card[] topCards = gameEngine.peekDrawPile();
      printImplodingIsNext = topCards.length > 0
              && topCards[0] == Card.IMPLODE;
    }
    ui.printGameState(playerName,
            playerNames,
            numExtraCardsToDraw,
            gameEngine.getIsTurnOrderReversed(),
            printImplodingIsNext);
  }


  /**
   * Does the effect of an alter the future card.
   */
  public void doAlterTheFuture() {

    ui.printAlteringTheFuture();

    Card[] peekedCards = gameEngine.peekDrawPile();
    int numToReorder = peekedCards.length;

    String[] cardNames = new String[peekedCards.length];
    for (int i = 0; i < peekedCards.length; i++) {
      cardNames[i] = peekedCards[i].name();
    }

    ui.printPeekedCards(cardNames);

    int[] newOrder = ui.promptNewOrder(numToReorder);
    Card[] newTopCards = new Card[numToReorder];
    for (int i = 0; i < numToReorder; i++) {
      newTopCards[i] = peekedCards[newOrder[i] - 1];
    }
    gameEngine.replaceTopDrawPileCards(newTopCards);
  }

  /**
   * Does the effect of a reverse card.
   */
  public void doReverse() {
    gameEngine.reverseTurnOrder();
    ui.printTurnOrderReversed();
    advanceTurn(true);
  }

  /**
   * Prompts the user for which cat cards to play as a combo.
   * Returns true if the turnManager should reprompt.
   * Made package private to support unit testing.
   *
   * @param numCards the number of cards to play.
   */
  boolean promptAndPlayCombo(int numCards) {
    if (numCards != 2 && numCards != 3) {
      String errorMessage = ui.printMustPlay2Or3CardsAsComboError();
      throw new IllegalArgumentException(errorMessage);
    }
    String[] stringCards = ui.promptPlayComboCards(numCards);
    if (stringCards.length != numCards) {
      String errorMessage = ui.printMismatchUserCardsAndComboCount();
      throw new IllegalStateException(errorMessage);
    }
    Card[] cards;
    try {
      cards = gameEngine.validateComboCards(stringCards, currPlayerIndex);
    } catch (Exception validateCardException) {
      ui.printValidateComboCardErrorMessage(validateCardException.getMessage());
      return true;
    }
    if (cards.length != numCards) {
      String message = ui.printMismatchCardValidationCardsAndComboCount();
      throw new IllegalStateException(message);
    }
    if (numCards == 2) {
      do2CardCombo();
    } else {
      do3CardCombo();
    }

    for (Card card : cards) {
      gameEngine.removeCardFromPlayer(card, currPlayerIndex);
    }
    return false;
  }

  /**
   * Does the effect of a see the future card.
   */
  public void doSeeTheFuture() {

    ui.printSeeingTheFuture();
    Card[] peekedCards = gameEngine.peekDrawPile();

    String[] cardNames = new String[peekedCards.length];
    for (int i = 0; i < peekedCards.length; i++) {
      cardNames[i] = peekedCards[i].name();
    }

    ui.printPeekedCards(cardNames);
  }

  /**
   * Does the effect of a draw from bottom card.
   */
  public void doDrawFromBottom() {
    endTurn(true);
  }

  /**
   * Does the effect of an attack card.
   */
  public void doAttack() {
    if (numExtraCardsToDraw == 0) {
      numExtraCardsToDraw += 1;
    } else {
      numExtraCardsToDraw += 2;
    }
    ui.printAttacking(numExtraCardsToDraw);
    advanceTurn(true);
  }

  /**
   * Decides whether to nope the previously played card.
   *
   * @return a boolean representing whether the previously played card should be noped.
   */
  public boolean promptPlayNope() {
    boolean somebodyPlayedNopeCard = promptAndValidateNopePlayerAndPlayNopeIfSo();
    if (somebodyPlayedNopeCard) {
      return !promptPlayNope();
    }
    return false;
  }

  /**
   * Prompts if a player wants to play a nope card w/ UI.promptNope().
   * If not, returns false - nobody played a nope.
   * If so, checks if the input player is a valid player,
   * If so, checks if the player has a Nope card.
   * If any of these cases is not true, it informs the users and prompts again.
   * If all the cases passed, play the nope.
   *
   * @return a boolean representing whether a player played a nope card.
   */
  public boolean promptAndValidateNopePlayerAndPlayNopeIfSo() {
    String name = ui.promptNope(false);
    Player player;
    while (true) {
      if (name.isEmpty()) {
        return false;
      }
      try {
        int playerIndex = gameEngine.getPlayerIndexByName(name);
        player = gameEngine.getPlayerByIndex(playerIndex);
      } catch (Exception e) {
        name = ui.promptNope(true);
        continue;
      }

      if (player.removeCardFromHand(Card.NOPE)) { // 'plays' the card.
        ui.printNopePlayed();
        return true;
      }
      name = ui.printLastPlayerDidNotHaveNopeAndGetNewPlayer(player.getName());
    }
  }

  /**
   * Does the effect of a shuffle card.
   */
  public void doShuffle() {
    ui.printShuffling();
    gameEngine.shuffleDrawPile();
  }

  /**
   * Does the effect of a skip card.
   */
  public void doSkip() {
    ui.printSkipping();
    if (numExtraCardsToDraw > 0) {
      numExtraCardsToDraw--;
    } else {
      advanceTurn(true);
    }
  }

  /**
   * Does the effect of a targeted attack card.
   */
  public void doTargetedAttack() {
    boolean validPlayerFound = false;

    ui.printDoingTargetedAttack();
    printPlayers();
    String name = ui.promptTargetedAttack(false);

    while (!validPlayerFound) {
      try {
        currPlayerIndex = gameEngine.getPlayerIndexByName(name);
        validPlayerFound = true;
      } catch (NoSuchElementException e) {
        printPlayers();
        name = ui.promptTargetedAttack(true);
      }
    }

    // Update the number of extra cards to draw.
    if (numExtraCardsToDraw == 0) {
      numExtraCardsToDraw += 1;
    } else {
      numExtraCardsToDraw += 2;
    }
    ui.printTargetedAttackResult(numExtraCardsToDraw);
  }

  /**
   * Does the effect of a 2 card combo.
   */
  public void do2CardCombo() {
    boolean validPlayerFound = false;
    int targetIndex = -1;

    ui.printDoingCardCombo(2);

    printPlayers();
    String name = ui.prompt2CardCombo(false);

    while (!validPlayerFound) {
      try {
        targetIndex = gameEngine.getPlayerIndexByName(name);
        if (targetIndex == currPlayerIndex) {
          printPlayers();
          name = ui.prompt2CardCombo(true);
        } else {
          validPlayerFound = true;
        }
      } catch (NoSuchElementException e) {
        printPlayers();
        name = ui.prompt2CardCombo(true);
      }
    }

    if (gameEngine.getPlayerByIndex(targetIndex).getHand().length == 0) {
      ui.printCardComboErrorTargetPlayerHasNoCards();
      return;
    }

    printPlayerHand(targetIndex);
    String card = ui.prompt2CardComboTarget(false);
    Card cardToGive = null;
    boolean validCardFound = false;

    while (!validCardFound) {
      try {
        cardToGive = gameEngine.getCardByName(card);
        if (gameEngine.playerHasCard(cardToGive, targetIndex)) {
          validCardFound = true;
        } else {
          printPlayerHand(targetIndex);
          card = ui.prompt2CardComboTarget(true);
        }
      } catch (IllegalArgumentException e) {
        printPlayerHand(targetIndex);
        card = ui.prompt2CardComboTarget(true);
      }
    }

    // Remove the card from the target player's hand.
    gameEngine.removeCardFromPlayer(cardToGive, targetIndex);

    // Add the card to the current player's hand.
    gameEngine.getPlayerByIndex(currPlayerIndex).addCardToHand(cardToGive);
  }

  /**
   * Does the effect of a 3 card combo.
   */
  public void do3CardCombo() {
    boolean validPlayerFound = false;
    int targetIndex = -1;

    ui.printDoingCardCombo(3);
    printPlayers();
    String name = ui.prompt3CardComboTargetName(false);

    while (!validPlayerFound) {
      try {
        targetIndex = gameEngine.getPlayerIndexByName(name);
        if (targetIndex == currPlayerIndex) {
          printPlayers();
          name = ui.prompt3CardComboTargetName(true);
        } else {
          validPlayerFound = true;
        }
      } catch (NoSuchElementException e) {
        printPlayers();
        name = ui.prompt3CardComboTargetName(true);
      }
    }

    if (gameEngine.getPlayerByIndex(targetIndex).getHand().length == 0) {
      ui.printCardComboErrorTargetPlayerHasNoCards();
      return;
    }

    String card = ui.prompt3CardComboTargetCard(false);
    Card cardToGive = null;
    boolean validCardFound = false;

    while (!validCardFound) {
      try {
        cardToGive = gameEngine.getCardByName(card);
        if (gameEngine.playerHasCard(cardToGive, targetIndex)) {
          validCardFound = true;
        } else {
          return;
        }
      } catch (IllegalArgumentException e) {
        card = ui.prompt3CardComboTargetCard(true);
      }
    }

    // Remove the card from the target player's hand.
    gameEngine.removeCardFromPlayer(cardToGive, targetIndex);

    // Add the card to the current player's hand.
    gameEngine.getPlayerByIndex(currPlayerIndex).addCardToHand(cardToGive);
  }

  /**
   * Print the player's hand given the player index.
   *
   * @param playerIndex the index of the player
   */
  public void printPlayerHand(int playerIndex) {
    // Check that the player index is valid.
    if (playerIndex < 0 || playerIndex > gameEngine.getPlayers().size() - 1) {
      throw new IndexOutOfBoundsException("Player index is out of bounds.");
    }

    Player player = gameEngine.getPlayerByIndex(playerIndex);
    Card[] hand = player.getHand();

    // Convert the hand to a string array.
    String[] handString = new String[hand.length];
    for (int i = 0; i < hand.length; i++) {
      handString[i] = hand[i].toString();
    }

    ui.printPlayerHand(handString);
  }

  /**
   * Print the players in the game.
   */
  public void printPlayers() {
    List<Player> players = gameEngine.getPlayers();
    String[] playerNames = new String[players.size()];
    for (int i = 0; i < players.size(); i++) {
      playerNames[i] = players.get(i).getName();
    }
    ui.printPlayers(playerNames);
  }

  /**
   * Ends a player's turn.
   *
   * @param drawFromBottom whether to draw from the bottom of the draw pile.
   */
  public void endTurn(boolean drawFromBottom) {
    if (numExtraCardsToDraw > 0) {
      numExtraCardsToDraw--;
      drawAndProcessCard(drawFromBottom); // don't advance the turn either way
    } else {
      boolean alreadyAdvanced = drawAndProcessCard(drawFromBottom);
      if (!alreadyAdvanced) {
        advanceTurn(true);
      }
    }
  }

  /**
   * Draws a card from the Game Engine's draw pile.
   * Calls the corresponding function.
   *
   * @param drawFromBottom whether to draw from the bottom.
   *
   * @return whether a turn advance happened.
   */
  public boolean drawAndProcessCard(boolean drawFromBottom) {
    ui.printDrawingCard(drawFromBottom);

    Card drawnCard = drawFromBottom ? gameEngine.popBottomCard() : gameEngine.popTopCard();

    switch (drawnCard) {
      case EXPLODE:
        return handleExplodingKitten();
      case IMPLODE:
        return handleImplodingCat();
      default:
        try {
          handleRegularCard(drawnCard);
        } catch (Exception e) {
          ui.println(e.getMessage());
        }
        break;
    }
    return false;
  }

  /**
   * Handles the case where the exploding kitten is drawn.
   *
   * @return whether the player died.
   */
  public boolean handleExplodingKitten() {
    boolean hasDefuse = gameEngine.playerHasCard(Card.DEFUSE, currPlayerIndex);
    ui.printDrawExplodingKitten(hasDefuse);

    if (hasDefuse) {
      gameEngine.removeCardFromPlayer(Card.DEFUSE, currPlayerIndex);
      gameEngine.discardCard(Card.DEFUSE);

      int drawPileSize = gameEngine.getDrawPile().length;
      int placementIndex = ui.promptPlacementForExplodeOrImplode(drawPileSize, true);
      gameEngine.addCardToDrawPileAt(Card.EXPLODE, placementIndex);
    } else {
      eliminateCurrentPlayer();
      return true;
    }

    return false;
  }

  /**
   * Handles the case where the imploding cat is drawn.
   *
   * @return whether the player was eliminated.
   */
  public boolean handleImplodingCat() {
    ui.printDrawImplodingKitten(isImplodingCatFaceUp);

    if (isImplodingCatFaceUp) {
      eliminateCurrentPlayer();
      return true;
    } else {
      int drawPileSize = gameEngine.getDrawPile().length;
      int placementIndex = ui.promptPlacementForExplodeOrImplode(drawPileSize, false);
      gameEngine.addCardToDrawPileAt(Card.IMPLODE, placementIndex);
      isImplodingCatFaceUp = true;
    }
    return false;
  }

  /**
   * Adds the card to the player's hand and calls endTurn.
   *
   * @param card the card drawn from the draw pile
   */
  public void handleRegularCard(Card card) {
    switch (card) {
      case EXPLODE:
      case IMPLODE:
        throw new IllegalArgumentException("Cannot add this card type to a player's hand");
      default:
        ui.printAddingCardToHand(card.name());
        Player currPlayer = gameEngine.getPlayers().get(currPlayerIndex);
        currPlayer.addCardToHand(card);
    }
  }

  /**
   * Eliminates the current player.
   */
  public void eliminateCurrentPlayer() {
    ui.printPlayerEliminated();
    gameEngine.eliminatePlayer(currPlayerIndex);
    advanceTurn(false);
  }

  /**
   * Updates whose turn it is.
   */
  public void advanceTurn(boolean playerSurvived) {
    int numOfPlayers = gameEngine.getNumberOfPlayers();
    boolean orderReversed = gameEngine.getIsTurnOrderReversed();

    if (orderReversed) {
      currPlayerIndex = (currPlayerIndex - 1 + numOfPlayers) % numOfPlayers;
    } else {
      currPlayerIndex = playerSurvived
              ? (currPlayerIndex + 1) % numOfPlayers
              : currPlayerIndex % numOfPlayers;
    }

    playerTurnHasEnded = true;
  }
}
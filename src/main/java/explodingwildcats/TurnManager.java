package explodingwildcats;

import ui.UserInterface;

/**
 * Class responsible for doing turns.
 */
public class TurnManager {

  private UserInterface ui;
  private GameEngine gameEngine;
  int numExtraCardsToDraw; // Package private to support unit testing.
  int currPlayerIndex; // Package private to support unit testing.
  boolean isImplodingCatFaceUp = false;
  boolean playerTurnHasEnded = false;

  TurnManager(UserInterface ui,
              GameEngine gameEngine) {
    this.ui = ui;
    this.gameEngine = gameEngine;
    this.numExtraCardsToDraw = 0;
  }

  /**
   * Does the effect of an alter the future card.
   */
  public void doAlterTheFuture() {
    Card[] peekedCards = gameEngine.peekDrawPile();
    int numToReorder = peekedCards.length;

    String peekedCardsMessage = "Top: " + peekedCards[0].name();
    if (numToReorder > 1) {
      peekedCardsMessage += ", 2nd: " + peekedCards[1].name();
    }
    if (numToReorder == 3) {
      peekedCardsMessage += ", 3rd: " + peekedCards[2].name();
    }
    ui.println(peekedCardsMessage);

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
    ui.println("Turn order was reversed.");
    endTurn();
  }

  /**
   * Draws a card from the Game Engine's draw pile.
   * Calls the corresponding function.
   */
  public void drawAndProcessCard(boolean drawFromBottom) {
    Card drawnCard = drawFromBottom ? gameEngine.popBottomCard() : gameEngine.popTopCard();

    switch (drawnCard) {
      case EXPLODE:
        handleExplodingKitten();
        break;
      case IMPLODE:
        handleImplodingCat();
        break;
      default:
        try {
          handleRegularCard(drawnCard);
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
        break;
    }
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
        Player currPlayer = gameEngine.getPlayers().get(currPlayerIndex);
        currPlayer.addCardToHand(card);
        endTurn();
    }
  }

  /**
   * Handles the case where the exploding kitten is drawn.
   */
  public void handleExplodingKitten() {
    boolean hasDefuse = gameEngine.playerHasCard(Card.DEFUSE, currPlayerIndex);

    if (hasDefuse) {
      gameEngine.removeCardFromPlayer(Card.DEFUSE, currPlayerIndex);
      gameEngine.discardCard(Card.DEFUSE);

      int drawPileSize = gameEngine.getDrawPile().length;
      int placementIndex = ui.promptPlacementForExplodeOrImplode(drawPileSize, true);
      gameEngine.addCardToDrawPileAt(Card.EXPLODE, placementIndex);
    } else {
      gameEngine.eliminatePlayer(currPlayerIndex);
    }

    endTurn();
  }

  /**
   * Handles the case where the imploding cat is drawn.
   */
  public void handleImplodingCat() {
    if (isImplodingCatFaceUp) {
      gameEngine.eliminatePlayer(currPlayerIndex);
    } else {
      int drawPileSize = gameEngine.getDrawPile().length;
      int placementIndex = ui.promptPlacementForExplodeOrImplode(drawPileSize, false);
      gameEngine.addCardToDrawPileAt(Card.IMPLODE, placementIndex);
    }
    endTurn();
  }

  /**
   * Ends a player's turn.
   */
  public void endTurn() {
    if (numExtraCardsToDraw > 0) {
      numExtraCardsToDraw--;
      drawAndProcessCard(false);
    } else {
      advanceTurn();
    }
  }

  /**
   * Updates whose turn it is.
   */
  public void advanceTurn() {
    int numOfPlayers = gameEngine.getNumberOfPlayers();
    boolean orderReversed = gameEngine.getIsTurnOrderReversed();

    if (orderReversed) {
      currPlayerIndex = (currPlayerIndex - 1 + numOfPlayers) % numOfPlayers;
    } else {
      currPlayerIndex = (currPlayerIndex + 1) % numOfPlayers;
    }
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
      throw new IllegalArgumentException("You must play 2 or 3 cards as a combo.");
    }
    String[] stringCards = ui.promptPlayComboCards(numCards);
    if (stringCards.length != numCards) {
      throw new IllegalStateException(
              "Number of cards returned by user does not match combo count.");
    }
    Card[] cards;
    try {
      cards = validateComboCards(stringCards);
    } catch (Exception validateCardException) {
      ui.println(validateCardException.getMessage());
      return true;
    }
    if (cards.length != numCards) {
      throw new IllegalStateException(
              "Number of cards returned by card validation does not match combo count.");
    }
    if (numCards == 2) {
      do2CardCombo();
    } else if (numCards == 3) {
      do3CardCombo();
    }

    for (Card card: cards) {
      gameEngine.removeCardFromPlayer(card, currPlayerIndex);
    }
    return false;
  }


  /**
   * TODO: Validates whether the current user has the input cards.
   * Returns the Card[] if so, and throws an exception if it is invalid.
   * Made package private to support unit testing.
   *
   * @param stringCards the string representation of the cards.
   */
  Card[] validateComboCards(String[] stringCards) {
    return new Card[0];
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
      gameEngine.printCurrentPlayerHand();
      String userInputCard = ui.promptPlayCard(shouldReprompt);
      if (userInputCard.isEmpty()) {
        endTurn();
        shouldReprompt = false;
        continue;
      }

      if (userInputCard.equals("2 cat cards")) {
        shouldReprompt = promptAndPlayCombo(2);
        continue;
      } else if (userInputCard.equals("3 cat cards")) {
        shouldReprompt = promptAndPlayCombo(3);
        continue;
      }

      Card cardToPlay;
      try {
        cardToPlay = getPlayableCard(userInputCard);
      } catch (Exception originalCardChosenException) {
        // this means the player had an invalid input.
        ui.println(originalCardChosenException.getMessage());
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
   * TODO: Does the effect of a targeted attack card.
   * Should:
   * 1. play an attack card.
   * 2. change the current player index to the targetPlayerIndex.
   */
  public void doTargetedAttack() {

  }


  /**
   * Does the effect of a see the future card.
   */
  public void doSeeTheFuture() {
    Card[] peekedCards = gameEngine.peekDrawPile();

    String peekedCardsMessage = "Top: " + peekedCards[0].name();
    if (peekedCards.length > 1) {
      peekedCardsMessage += ", 2nd: " + peekedCards[1].name();
    }
    if (peekedCards.length == 3) {
      peekedCardsMessage += ", 3rd: " + peekedCards[2].name();
    }

    ui.println(peekedCardsMessage);
  }

  /**
   * Does the effect of a draw from bottom card.
   */
  public void doDrawFromBottom() {
    drawAndProcessCard(true);
    endTurn();
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
    endTurn();
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
    Player p;
    while (true) {
      if (name.isEmpty()) {
        return false;
      }
      try {
        p = gameEngine.getPlayerByName(name);
      } catch (Exception e) {
        name = ui.promptNope(true);
        continue;
      }

      if (p.removeCardFromHand(Card.NOPE)) { // 'plays' the card.
        return true;
      }
      name = ui.printLastPlayerDidNotHaveNopeAndGetNewPlayer(p.getName());
    }
  }

  Card getPlayableCard(String cardName) {
    switch (cardName) {
      case "attack":
        return Card.ATTACK;
      case "skip":
        return Card.SKIP;
      case "targeted attack":
        return Card.TARGETED_ATTACK;
      case "shuffle":
        return Card.SHUFFLE;
      case "see the future":
        return Card.SEE_THE_FUTURE;
      case "reverse":
        return Card.REVERSE;
      case "draw from bottom":
        return Card.DRAW_FROM_BOTTOM;
      case "alter the future":
        return Card.ALTER_THE_FUTURE;
      case "nope":
        throw new IllegalArgumentException("You cannot play a nope right now.");
      case "taco cat":
      case "beard cat":
      case "rainbow cat":
      case "feral cat":
      case "hairy potato cat":
        throw new IllegalArgumentException("You must play a cat card as a combo.");
      default:
        break;
    }
    throw new IllegalArgumentException("Could not parse input.");
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
   * Does the effect of a shuffle card.
   */
  public void doShuffle() {
    gameEngine.shuffleDrawPile();
    endTurn();
  }

  /**
   * Does the effect of a skip card.
   */
  public void doSkip() {
    if (numExtraCardsToDraw > 0) {
      numExtraCardsToDraw--;
    } else {
      endTurn();
    }
  }

  /**
   * TODO: Does the effect of a 2 card combo.
   */
  public void do2CardCombo() { }

  /**
   * TODO: Does the effect of a 3 card combo.
   */
  public void do3CardCombo() { }
}
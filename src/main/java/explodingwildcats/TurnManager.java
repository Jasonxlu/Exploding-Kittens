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
        handleRegularCard(drawnCard);
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
   * TODO: handles the case where the exploding kitten is drawn.
   */
  public void handleExplodingKitten() {}

  /**
   * TODO: handles the case where the imploding cat is drawn.
   */
  public void handleImplodingCat() {}

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
}
package explodingwildcats;

import ui.UserInterface;

/**
 * Class responsible for doing turns.
 */
public class TurnManager {

  private UserInterface ui;
  private GameEngine gameEngine;

  TurnManager(UserInterface ui,
              GameEngine gameEngine) {
    this.ui = ui;
    this.gameEngine = gameEngine;
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

  public void doSeeTheFuture() {
    Card[] peekedCards = gameEngine.peekDrawPile();
    ui.println("Top: Targeted_Attack, 2nd: None, 3rd: None");
  }
}
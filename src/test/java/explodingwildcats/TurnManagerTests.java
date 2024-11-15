package explodingwildcats;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import ui.UserInterface;

public class TurnManagerTests {

  @Test
  public void doAlterTheFuture_oneCardInDrawPile_reorderInSamePlace() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Card cardInDrawPile = Card.SKIP;
    Card[] peekedDrawPile = new Card[] { cardInDrawPile };

    // 1. GameEngine.peek() called
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(peekedDrawPile);

    // 2. ui.println called with peeked cards
    ui.println("Top: SKIP");
    int[] newOrder = new int[] {1};

    // 3. ui.promptNewOrder called with number of peeked cards
    EasyMock.expect(ui.promptNewOrder(1)).andReturn(newOrder);

    Card[] reorderedDrawPile = new Card[] { cardInDrawPile };
    // 4. GameEngine.replaceTopDrawPileCards called with the new order converted to the cards.
    gameEngine.replaceTopDrawPileCards(reorderedDrawPile);

    EasyMock.replay(ui, gameEngine);

    turnManager.doAlterTheFuture();

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void doAlterTheFuture_twoCardsInDrawPile_reorderOppositeOrder() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Card topCardInDrawPile = Card.NOPE;
    Card secondCardInDrawPile = Card.ATTACK;
    Card[] peekedDrawPile = new Card[] { topCardInDrawPile, secondCardInDrawPile };

    // 1. GameEngine.peek() called
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(peekedDrawPile);

    // 2. ui.println called with peeked cards
    ui.println("Top: NOPE, 2nd: ATTACK");
    int[] newOrder = new int[] {2, 1};

    // 3. ui.promptNewOrder called with number of peeked cards
    EasyMock.expect(ui.promptNewOrder(2)).andReturn(newOrder);

    Card[] reorderedDrawPile = new Card[] { secondCardInDrawPile, topCardInDrawPile };
    // 4. GameEngine.replaceTopDrawPileCards called with the new order converted to the cards.
    gameEngine.replaceTopDrawPileCards(reorderedDrawPile);

    EasyMock.replay(ui, gameEngine);

    turnManager.doAlterTheFuture();

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void doAlterTheFuture_threeCardsInDrawPile_swapTopTwo() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Card topCardInDrawPile = Card.IMPLODE;
    Card secondCardInDrawPile = Card.DEFUSE;
    Card thirdCardInDrawPile = Card.REVERSE;
    Card[] peekedDrawPile = new Card[] { topCardInDrawPile, secondCardInDrawPile, thirdCardInDrawPile };

    // 1. GameEngine.peek() called
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(peekedDrawPile);

    // 2. ui.println called with peeked cards
    ui.println("Top: IMPLODE, 2nd: DEFUSE, 3rd: REVERSE");
    int[] newOrder = new int[] {2, 1, 3};

    // 3. ui.promptNewOrder called with number of peeked cards
    EasyMock.expect(ui.promptNewOrder(3)).andReturn(newOrder);

    Card[] reorderedDrawPile = new Card[] { secondCardInDrawPile, topCardInDrawPile, thirdCardInDrawPile };
    // 4. GameEngine.replaceTopDrawPileCards called with the new order converted to the cards.
    gameEngine.replaceTopDrawPileCards(reorderedDrawPile);

    EasyMock.replay(ui, gameEngine);

    turnManager.doAlterTheFuture();

    EasyMock.verify(ui, gameEngine);
  }
}

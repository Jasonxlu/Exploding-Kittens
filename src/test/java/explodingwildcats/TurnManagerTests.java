package explodingwildcats;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import ui.UserInterface;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TurnManagerTests {

  @Test
  public void doAlterTheFuture_oneCardPeeked_reorderInSamePlace() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Card cardPeeked = Card.SKIP;
    Card[] peekedCards = new Card[] { cardPeeked };

    // 1. GameEngine.peek() called
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(peekedCards);

    // 2. ui.println called with peeked cards
    ui.println("Top: SKIP");
    int[] newOrder = new int[] {1};

    // 3. ui.promptNewOrder called with number of peeked cards
    EasyMock.expect(ui.promptNewOrder(1)).andReturn(newOrder);

    Card[] reorderedPeekedCards = new Card[] { cardPeeked };
    // 4. GameEngine.replaceTopDrawPileCards called with the new order converted to the cards.
    gameEngine.replaceTopDrawPileCards(reorderedPeekedCards);

    EasyMock.replay(ui, gameEngine);

    turnManager.doAlterTheFuture();

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void doAlterTheFuture_twoCardsPeeked_reorderOppositeOrder() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Card topCardPeeked = Card.NOPE;
    Card secondCardPeeked = Card.ATTACK;
    Card[] peekedCards = new Card[] { topCardPeeked, secondCardPeeked };

    // 1. GameEngine.peek() called
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(peekedCards);

    // 2. ui.println called with peeked cards
    ui.println("Top: NOPE, 2nd: ATTACK");
    int[] newOrder = new int[] {2, 1};

    // 3. ui.promptNewOrder called with number of peeked cards
    EasyMock.expect(ui.promptNewOrder(2)).andReturn(newOrder);

    Card[] reorderedPeekedCards = new Card[] { secondCardPeeked, topCardPeeked };
    // 4. GameEngine.replaceTopDrawPileCards called with the new order converted to the cards.
    gameEngine.replaceTopDrawPileCards(reorderedPeekedCards);

    EasyMock.replay(ui, gameEngine);

    turnManager.doAlterTheFuture();

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void doAlterTheFuture_maxCardsPeeked_reverseCards() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Card topCardPeeked = Card.IMPLODE;
    Card secondCardPeeked = Card.DEFUSE;
    Card thirdCardPeeked = Card.REVERSE;
    Card[] peekedCards = new Card[] { topCardPeeked, secondCardPeeked, thirdCardPeeked };

    // 1. GameEngine.peek() called
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(peekedCards);

    // 2. ui.println called with peeked cards
    ui.println("Top: IMPLODE, 2nd: DEFUSE, 3rd: REVERSE");
    int[] newOrder = new int[] {3, 2, 1};

    // 3. ui.promptNewOrder called with number of peeked cards
    EasyMock.expect(ui.promptNewOrder(3)).andReturn(newOrder);

    Card[] reorderedPeekedCards = new Card[] { thirdCardPeeked, secondCardPeeked, topCardPeeked };
    // 4. GameEngine.replaceTopDrawPileCards called with the new order converted to the cards.
    gameEngine.replaceTopDrawPileCards(reorderedPeekedCards);

    EasyMock.replay(ui, gameEngine);

    turnManager.doAlterTheFuture();

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void doSeeTheFuture_oneCardPeeked_oneCardPrinted() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Card cardPeeked = Card.TARGETED_ATTACK;
    Card[] peekedCards = new Card[] { cardPeeked };

    // 1. GameEngine.peek() called
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(peekedCards);

    // 2. ui.println called with the peeked cards
    ui.println("Top: TARGETED_ATTACK");

    EasyMock.replay(ui, gameEngine);

    turnManager.doSeeTheFuture();

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void doSeeTheFuture_twoCardsPeeked_twoCardsPrinted() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Card topCardPeeked = Card.DEFUSE;
    Card secondCardPeeked = Card.IMPLODE;
    Card[] peekedCards = new Card[] { topCardPeeked, secondCardPeeked };

    // 1. GameEngine.peek() called
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(peekedCards);

    // 2. ui.println called with the peeked cards
    ui.println("Top: DEFUSE, 2nd: IMPLODE");

    EasyMock.replay(ui, gameEngine);

    turnManager.doSeeTheFuture();

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void doSeeTheFuture_maxCardsPeeked_threeCardsPrinted() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Card topCardPeeked = Card.NOPE;
    Card secondCardPeeked = Card.EXPLODE;
    Card thirdCardPeeked = Card.REVERSE;
    Card[] peekedCards = new Card[] {topCardPeeked, secondCardPeeked, thirdCardPeeked};

    // 1. GameEngine.peek() called
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(peekedCards);

    // 2. ui.println called with the peeked cards
    ui.println("Top: NOPE, 2nd: EXPLODE, 3rd: REVERSE");

    EasyMock.replay(ui, gameEngine);

    turnManager.doSeeTheFuture();

    EasyMock.verify(ui, gameEngine);
  }
  
  @Test
  public void doReverse_callsCorrectFunctions() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .createMock();

    gameEngine.reverseTurnOrder();
    ui.println("Turn order was reversed.");
    turnManager.endTurn();

    EasyMock.replay(ui, gameEngine, turnManager);

    turnManager.doReverse();

    EasyMock.verify(ui, gameEngine, turnManager);
  }

  @Test
  public void doDrawFromBottom_callsDrawAndProcessCardWithTrueThenEndTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .addMockedMethod("drawAndProcessCard")
            .createMock();

    boolean drawAndProcessCardParameter = true;
    turnManager.drawAndProcessCard(drawAndProcessCardParameter);
    turnManager.endTurn();
    EasyMock.replay(ui, gameEngine, turnManager);

    turnManager.doDrawFromBottom();

    EasyMock.verify(ui, gameEngine, turnManager);
  }

  @Test
  public void doAttack_numExtraCardsToDrawIs0_adds1AndEndTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .createMock();

    turnManager.endTurn();
    EasyMock.replay(ui, gameEngine, turnManager);

    turnManager.doAttack();

    int expectedNumExtraCardsToDraw = 1;
    int actualNumExtraCardsToDraw = turnManager.numExtraCardsToDraw;

    assertEquals(expectedNumExtraCardsToDraw, actualNumExtraCardsToDraw);

    EasyMock.verify(ui, gameEngine, turnManager);
  }

  @Test
  public void doAttack_numExtraCardsToDrawIs7_adds2AndEndTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .createMock();

    turnManager.numExtraCardsToDraw = 7;

    turnManager.endTurn();
    EasyMock.replay(ui, gameEngine, turnManager);

    turnManager.doAttack();

    int expectedNumExtraCardsToDraw = 9;
    int actualNumExtraCardsToDraw = turnManager.numExtraCardsToDraw;

    assertEquals(expectedNumExtraCardsToDraw, actualNumExtraCardsToDraw);

    EasyMock.verify(ui, gameEngine, turnManager);
  }

  @Test
  public void doShuffle_singleCardInDrawPile_noChange() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Card[] drawPile = new Card[] { Card.DEFUSE };
    EasyMock.expect(gameEngine.getDrawPile()).andReturn(drawPile);

    EasyMock.replay(gameEngine);

    turnManager.doShuffle();

    Card[] expectedDrawPile = new Card[] { Card.DEFUSE };
    Card[] actualDrawPile = gameEngine.getDrawPile();

    // Check each element of the array is equal
    for (int i = 0; i < actualDrawPile.length; i++) {
      assertEquals(expectedDrawPile[i], actualDrawPile[i]);
    }

    EasyMock.verify(gameEngine);
  }

  @Test
  public void doShuffle_multipleCardsInDrawPile_noChange() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Card[] drawPile = new Card[] { Card.ATTACK, Card.REVERSE, Card.NOPE };
    EasyMock.expect(gameEngine.getDrawPile()).andReturn(drawPile);

    EasyMock.replay(gameEngine);

    turnManager.doShuffle();

    Card[] actualDrawPile = gameEngine.getDrawPile();

    // Check that the draw pile is in the same order
    boolean isShuffled = false;
    for (int i = 0; i < actualDrawPile.length; i++) {
      if (actualDrawPile[i] != drawPile[i]) {
        isShuffled = true;
        break;
      }
    }

    assertFalse(isShuffled);

    EasyMock.verify(gameEngine);
  }

  @Test
  public void doShuffle_maxCardsInDrawPile_noChange() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Card[] drawPile = new Card[] { Card.SKIP, Card.ATTACK, Card.TARGETED_ATTACK, Card.SKIP, Card.ATTACK, Card.TARGETED_ATTACK,
            Card.SKIP, Card.ATTACK, Card.TARGETED_ATTACK,
            Card.SHUFFLE, Card.SEE_THE_FUTURE, Card.NOPE, Card.REVERSE, Card.DRAW_FROM_BOTTOM,
            Card.ALTER_THE_FUTURE, Card.TACO_CAT, Card.HAIRY_POTATO_CAT, Card.BEARD_CAT,
            Card.RAINBOW_CAT, Card.FERAL_CAT, Card.SHUFFLE, Card.SEE_THE_FUTURE, Card.NOPE, Card.REVERSE, Card.DRAW_FROM_BOTTOM,
            Card.ALTER_THE_FUTURE, Card.TACO_CAT, Card.HAIRY_POTATO_CAT, Card.BEARD_CAT,
            Card.RAINBOW_CAT, Card.FERAL_CAT, Card.SHUFFLE, Card.SEE_THE_FUTURE, Card.NOPE, Card.REVERSE, Card.DRAW_FROM_BOTTOM,
            Card.ALTER_THE_FUTURE, Card.TACO_CAT, Card.HAIRY_POTATO_CAT, Card.BEARD_CAT,
            Card.RAINBOW_CAT, Card.FERAL_CAT, Card.SHUFFLE, Card.SEE_THE_FUTURE, Card.NOPE, Card.REVERSE, Card.DRAW_FROM_BOTTOM,
            Card.ALTER_THE_FUTURE, Card.TACO_CAT, Card.HAIRY_POTATO_CAT, Card.BEARD_CAT,
            Card.RAINBOW_CAT, Card.FERAL_CAT };
    EasyMock.expect(gameEngine.getDrawPile()).andReturn(drawPile);

    EasyMock.replay(gameEngine);

    turnManager.doShuffle();
    Card[] actualDrawPile = gameEngine.getDrawPile();

    // Check that the draw pile is in the same order
    boolean isShuffled = false;
    for (int i = 0; i < actualDrawPile.length; i++) {
      if (actualDrawPile[i] != drawPile[i]) {
        isShuffled = true;
        break;
      }
    }

    assertFalse(isShuffled);

    EasyMock.verify(gameEngine);
  }

}



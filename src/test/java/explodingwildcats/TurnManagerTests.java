package explodingwildcats;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ui.UserInterface;

import java.util.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TurnManagerTests {

  @Test
  public void doAlterTheFuture_oneCardPeeked_reorderInSamePlace() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    ui.printAlteringTheFuture();

    Card cardPeeked = Card.SKIP;
    Card[] peekedCards = new Card[] { cardPeeked };

    // 1. GameEngine.peek() called
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(peekedCards);

    // 2. ui.println called with peeked cards
    String[] cardNames = new String[] { cardPeeked.name() };
    ui.printPeekedCards(cardNames);
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

    ui.printAlteringTheFuture();

    Card topCardPeeked = Card.NOPE;
    Card secondCardPeeked = Card.ATTACK;
    Card[] peekedCards = new Card[] { topCardPeeked, secondCardPeeked };

    // 1. GameEngine.peek() called
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(peekedCards);

    // 2. ui.println called with peeked cards
    String[] cardNames = new String[] { topCardPeeked.name(), secondCardPeeked.name() };
    ui.printPeekedCards(cardNames);
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

    ui.printAlteringTheFuture();

    Card topCardPeeked = Card.IMPLODE;
    Card secondCardPeeked = Card.DEFUSE;
    Card thirdCardPeeked = Card.REVERSE;
    Card[] peekedCards = new Card[] { topCardPeeked, secondCardPeeked, thirdCardPeeked };

    // 1. GameEngine.peek() called
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(peekedCards);

    // 2. ui.println called with peeked cards
    String[] cardNames = new String[] { topCardPeeked.name(), secondCardPeeked.name(), thirdCardPeeked.name() };
    ui.printPeekedCards(cardNames);
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

    ui.printSeeingTheFuture();

    Card cardPeeked = Card.TARGETED_ATTACK;
    Card[] peekedCards = new Card[] { cardPeeked };

    // 1. GameEngine.peek() called
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(peekedCards);

    // 2. ui.println called with the peeked cards
    String[] cardNames = new String[] { cardPeeked.name() };
    ui.printPeekedCards(cardNames);

    EasyMock.replay(ui, gameEngine);

    turnManager.doSeeTheFuture();

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void doSeeTheFuture_twoCardsPeeked_twoCardsPrinted() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    ui.printSeeingTheFuture();

    Card topCardPeeked = Card.DEFUSE;
    Card secondCardPeeked = Card.IMPLODE;
    Card[] peekedCards = new Card[] { topCardPeeked, secondCardPeeked };

    // 1. GameEngine.peek() called
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(peekedCards);

    // 2. ui.println called with the peeked cards
    String[] cardNames = { topCardPeeked.name(), secondCardPeeked.name() };
    ui.printPeekedCards(cardNames);

    EasyMock.replay(ui, gameEngine);

    turnManager.doSeeTheFuture();

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void doSeeTheFuture_maxCardsPeeked_threeCardsPrinted() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    ui.printSeeingTheFuture();

    Card topCardPeeked = Card.NOPE;
    Card secondCardPeeked = Card.EXPLODE;
    Card thirdCardPeeked = Card.REVERSE;
    Card[] peekedCards = new Card[] {topCardPeeked, secondCardPeeked, thirdCardPeeked};

    // 1. GameEngine.peek() called
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(peekedCards);

    // 2. ui.println called with the peeked cards
    String[] cardNames = { topCardPeeked.name(), secondCardPeeked.name(), thirdCardPeeked.name() };
    ui.printPeekedCards(cardNames);

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
            .addMockedMethod("advanceTurn")
            .createMock();

    gameEngine.reverseTurnOrder();

    ui.printTurnOrderReversed();

    turnManager.advanceTurn(true);

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
            .createMock();

    boolean endTurnParameter = true;
    turnManager.endTurn(endTurnParameter);

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
            .addMockedMethod("advanceTurn")
            .createMock();

    int expectedNumExtraCardsToDraw = 1;
    ui.printAttacking(expectedNumExtraCardsToDraw);

    turnManager.advanceTurn(true);
    EasyMock.replay(ui, gameEngine, turnManager);

    turnManager.doAttack();

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
            .addMockedMethod("advanceTurn")
            .createMock();


    turnManager.numExtraCardsToDraw = 7;
    int expectedNumExtraCardsToDraw = 9;

    ui.printAttacking(expectedNumExtraCardsToDraw);

    turnManager.advanceTurn(true);
    EasyMock.replay(ui, gameEngine, turnManager);

    turnManager.doAttack();

    int actualNumExtraCardsToDraw = turnManager.numExtraCardsToDraw;

    assertEquals(expectedNumExtraCardsToDraw, actualNumExtraCardsToDraw);

    EasyMock.verify(ui, gameEngine, turnManager);
  }

  @Test
  public void doShuffle_singleCardInDrawPile_shuffleDrawPileCalled() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    ui.printShuffling();

    // One card in the draw pile
    Card[] drawPile = new Card[] { Card.DEFUSE };
    EasyMock.expect(gameEngine.getDrawPile()).andReturn(drawPile);

    gameEngine.shuffleDrawPile();

    EasyMock.replay(gameEngine, ui);

    turnManager.doShuffle();

    // Verify that there is one card in the draw pile
    Card[] actualDrawPile = gameEngine.getDrawPile();
    assertEquals(drawPile.length, actualDrawPile.length);

    EasyMock.verify(gameEngine, ui);
  }

  @Test
  public void doShuffle_multipleCardsInDrawPile_shuffleDrawPileCalled() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    ui.printShuffling();

    Card[] drawPile = new Card[] { Card.ATTACK, Card.REVERSE, Card.NOPE };
    EasyMock.expect(gameEngine.getDrawPile()).andReturn(drawPile);

    gameEngine.shuffleDrawPile();

    EasyMock.replay(gameEngine, ui);

    turnManager.doShuffle();

    // Verify that there is one card in the draw pile
    Card[] actualDrawPile = gameEngine.getDrawPile();
    assertEquals(drawPile.length, actualDrawPile.length);

    EasyMock.verify(gameEngine, ui);
  }

  @Test
  public void doShuffle_maxCardsInDrawPile_shuffleDrawPileCalled() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    ui.printShuffling();

    Card[] drawPile = new Card[] { Card.SKIP, Card.ATTACK, Card.TARGETED_ATTACK, Card.SKIP, Card.ATTACK, Card.TARGETED_ATTACK,
            Card.SKIP, Card.ATTACK, Card.TARGETED_ATTACK, Card.SHUFFLE, Card.SEE_THE_FUTURE, Card.NOPE, Card.REVERSE, Card.DRAW_FROM_BOTTOM,
            Card.ALTER_THE_FUTURE, Card.TACO_CAT, Card.HAIRY_POTATO_CAT, Card.BEARD_CAT, Card.RAINBOW_CAT, Card.FERAL_CAT, Card.SHUFFLE, Card.SEE_THE_FUTURE, Card.NOPE, Card.REVERSE, Card.DRAW_FROM_BOTTOM,
            Card.ALTER_THE_FUTURE, Card.TACO_CAT, Card.HAIRY_POTATO_CAT, Card.BEARD_CAT, Card.RAINBOW_CAT, Card.FERAL_CAT,Card.SHUFFLE, Card.SEE_THE_FUTURE, Card.NOPE, Card.REVERSE, Card.DRAW_FROM_BOTTOM,
            Card.ALTER_THE_FUTURE, Card.TACO_CAT, Card.HAIRY_POTATO_CAT, Card.BEARD_CAT, Card.RAINBOW_CAT, Card.FERAL_CAT,Card.SHUFFLE, Card.SEE_THE_FUTURE, Card.NOPE, Card.REVERSE, Card.DRAW_FROM_BOTTOM,
            Card.ALTER_THE_FUTURE, Card.TACO_CAT, Card.HAIRY_POTATO_CAT, Card.BEARD_CAT, Card.RAINBOW_CAT, Card.FERAL_CAT };
    EasyMock.expect(gameEngine.getDrawPile()).andReturn(drawPile);

    gameEngine.shuffleDrawPile();

    EasyMock.replay(gameEngine, ui);

    turnManager.doShuffle();

    // Verify that there is one card in the draw pile
    Card[] actualDrawPile = gameEngine.getDrawPile();
    assertEquals(drawPile.length, actualDrawPile.length);

    EasyMock.verify(gameEngine, ui);
  }


  @ParameterizedTest
  @CsvSource({
          "6, 0", "6, 5", "6, 3",
          "2, 0", "2, 1",
          "4, 0", "4, 3", "4, 2",
  })
  public void advanceTurn_ReversedOrderFalse_PlayerSurvived(int numOfPlayers, int currPlayerIndex) {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    EasyMock.expect(gameEngine.getIsTurnOrderReversed()).andReturn(false).anyTimes();
    EasyMock.expect(gameEngine.getNumberOfPlayers()).andReturn(numOfPlayers).anyTimes();
    EasyMock.replay(gameEngine);

    turnManager.numExtraCardsToDraw = 0;
    turnManager.currPlayerIndex = currPlayerIndex;

    turnManager.advanceTurn(true);

    int expected = (currPlayerIndex + 1) % numOfPlayers;
    int actual = turnManager.currPlayerIndex;
    assertEquals(expected, actual);
    assertTrue(turnManager.playerTurnHasEnded);

    EasyMock.verify(gameEngine);
  }

  @ParameterizedTest
  @CsvSource({
          "6, 0", "6, 5", "6, 3",
          "2, 0", "2, 1",
          "4, 0", "4, 3", "4, 2",
  })
  public void advanceTurn_ReversedOrderTrue_PlayerSurvived(int numOfPlayers, int currPlayerIndex) {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    EasyMock.expect(gameEngine.getIsTurnOrderReversed()).andReturn(true).anyTimes();
    EasyMock.expect(gameEngine.getNumberOfPlayers()).andReturn(numOfPlayers).anyTimes();
    EasyMock.replay(gameEngine);

    turnManager.numExtraCardsToDraw = 0;
    turnManager.currPlayerIndex = currPlayerIndex;

    turnManager.advanceTurn(true);

    int expected = (currPlayerIndex - 1 + numOfPlayers) % numOfPlayers;
    int actual = turnManager.currPlayerIndex;
    assertEquals(expected, actual);
    assertTrue(turnManager.playerTurnHasEnded);

    EasyMock.verify(gameEngine);
  }

  @Test
  public void advanceTurn_ReservedOrderFalse_PlayerDidNotSurvive() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    int numOfPlayers = 4;
    int currPlayerIndex = 2;

    EasyMock.expect(gameEngine.getIsTurnOrderReversed()).andReturn(false).anyTimes();
    EasyMock.expect(gameEngine.getNumberOfPlayers()).andReturn(numOfPlayers).anyTimes();
    EasyMock.replay(gameEngine);

    turnManager.numExtraCardsToDraw = 0;
    turnManager.currPlayerIndex = currPlayerIndex;

    turnManager.advanceTurn(false);

    int expected = 2;
    int actual = turnManager.currPlayerIndex;
    assertEquals(expected, actual);
    assertTrue(turnManager.playerTurnHasEnded);

    EasyMock.verify(gameEngine);
  }

  @Test
  public void advanceTurn_ReservedOrderTrue_PlayerDidNotSurvive() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    int numOfPlayers = 4;
    int currPlayerIndex = 2;

    EasyMock.expect(gameEngine.getIsTurnOrderReversed()).andReturn(true).anyTimes();
    EasyMock.expect(gameEngine.getNumberOfPlayers()).andReturn(numOfPlayers).anyTimes();
    EasyMock.replay(gameEngine);

    turnManager.numExtraCardsToDraw = 0;
    turnManager.currPlayerIndex = currPlayerIndex;

    turnManager.advanceTurn(false);

    int expected = 1;
    int actual = turnManager.currPlayerIndex;
    assertEquals(expected, actual);
    assertTrue(turnManager.playerTurnHasEnded);

    EasyMock.verify(gameEngine);
  }

  @Test
  public void drawAndProcessCard_regularCardFromTop_callsHandleRegularCard_returnFalse() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("handleRegularCard")
            .addMockedMethod("handleExplodingKitten")
            .addMockedMethod("handleImplodingCat")
            .createMock();

    Card regularCard = Card.SKIP;

    EasyMock.expect(gameEngine.popTopCard()).andReturn(regularCard);
    turnManager.handleRegularCard(regularCard);

    boolean drawFromBottom = false;
    ui.printDrawingCard(drawFromBottom);

    EasyMock.replay(gameEngine, turnManager, ui);

    boolean result = turnManager.drawAndProcessCard(drawFromBottom);
    assertFalse(result);

    EasyMock.verify(gameEngine, turnManager, ui);
  }

  @Test
  public void drawAndProcessCard_regularCardFromBottom_callsHandleRegularCard_returnFalse() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("handleRegularCard")
            .addMockedMethod("handleExplodingKitten")
            .addMockedMethod("handleImplodingCat")
            .createMock();

    boolean drawFromBottom = true;
    ui.printDrawingCard(drawFromBottom);

    Card regularCard = Card.SKIP;

    EasyMock.expect(gameEngine.popBottomCard()).andReturn(regularCard);
    turnManager.handleRegularCard(regularCard);

    EasyMock.replay(gameEngine, turnManager, ui);

    boolean result = turnManager.drawAndProcessCard(drawFromBottom);
    assertFalse(result);

    EasyMock.verify(gameEngine, turnManager, ui);
  }

  @Test
  public void drawAndProcessCard_explodeCardFromTop_callsHandleExplodingKitten_returnsTrue() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("handleRegularCard")
            .addMockedMethod("handleExplodingKitten")
            .addMockedMethod("handleImplodingCat")
            .createMock();

    boolean drawFromBottom = false;
    ui.printDrawingCard(drawFromBottom);

    Card explodingCard = Card.EXPLODE;

    EasyMock.expect(gameEngine.popTopCard()).andReturn(explodingCard);
    boolean eliminated = true;
    EasyMock.expect(turnManager.handleExplodingKitten()).andReturn(eliminated);

    EasyMock.replay(gameEngine, turnManager, ui);

    boolean result = turnManager.drawAndProcessCard(false);
    assertEquals(eliminated, result);

    EasyMock.verify(gameEngine, turnManager, ui);
  }

  @Test
  public void drawAndProcessCard_explodeCardFromBottom_callsHandleExplodingKitten_returnsTrue() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("handleRegularCard")
            .addMockedMethod("handleExplodingKitten")
            .addMockedMethod("handleImplodingCat")
            .createMock();

    boolean drawFromBottom = true;
    ui.printDrawingCard(drawFromBottom);

    Card explodingCard = Card.EXPLODE;

    EasyMock.expect(gameEngine.popBottomCard()).andReturn(explodingCard);
    boolean eliminated = true;
    EasyMock.expect(turnManager.handleExplodingKitten()).andReturn(eliminated);

    EasyMock.replay(gameEngine, turnManager, ui);

    boolean result = turnManager.drawAndProcessCard(drawFromBottom);
    assertEquals(eliminated, result);

    EasyMock.verify(gameEngine, turnManager, ui);
  }

  @Test
  public void drawAndProcessCard_implodeCardFromTop_callsHandleImplodingCat_returnsTrue() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("handleRegularCard")
            .addMockedMethod("handleExplodingKitten")
            .addMockedMethod("handleImplodingCat")
            .createMock();

    boolean drawFromBottom = false;
    ui.printDrawingCard(drawFromBottom);

    Card implodingCard = Card.IMPLODE;

    EasyMock.expect(gameEngine.popTopCard()).andReturn(implodingCard);

    boolean eliminated = true;
    EasyMock.expect(turnManager.handleImplodingCat()).andReturn(eliminated);

    EasyMock.replay(gameEngine, turnManager, ui);

    boolean result = turnManager.drawAndProcessCard(drawFromBottom);
    assertEquals(eliminated, result);

    EasyMock.verify(gameEngine, turnManager, ui);
  }

  @Test
  public void drawAndProcessCard_implodeCardFromBottom_callsHandleImplodingCat_returnTrue() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("handleRegularCard")
            .addMockedMethod("handleExplodingKitten")
            .addMockedMethod("handleImplodingCat")
            .createMock();

    boolean drawFromBottom = true;
    ui.printDrawingCard(drawFromBottom);

    Card implodingCard = Card.IMPLODE;

    EasyMock.expect(gameEngine.popBottomCard()).andReturn(implodingCard);

    boolean eliminated = true;
    EasyMock.expect(turnManager.handleImplodingCat()).andReturn(eliminated);

    EasyMock.replay(gameEngine, turnManager, ui);

    boolean result = turnManager.drawAndProcessCard(drawFromBottom);
    assertEquals(eliminated, result);

    EasyMock.verify(gameEngine, turnManager, ui);
  }

  @Test
  public void drawAndProcessCard_regularCardThrowsException_callsUiPrintln() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("handleRegularCard")
            .addMockedMethod("handleExplodingKitten")
            .addMockedMethod("handleImplodingCat")
            .createMock();

    boolean drawFromBottom = false;
    ui.printDrawingCard(drawFromBottom);

    Card regularCard = Card.SKIP;
    String errorMessage = "Cannot add this card type to a player's hand";

    EasyMock.expect(gameEngine.popTopCard()).andReturn(regularCard);

    turnManager.handleRegularCard(regularCard);
    EasyMock.expectLastCall().andThrow(new IllegalArgumentException(errorMessage));
    ui.println(errorMessage);

    EasyMock.replay(gameEngine, turnManager, ui);

    turnManager.drawAndProcessCard(false);

    EasyMock.verify(gameEngine, turnManager, ui);
  }

  @Test
  public void drawAndProcessCard_implodeCardFromTop_handleImplodingCatReturnsFalse_returnFalse() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("handleRegularCard")
            .addMockedMethod("handleExplodingKitten")
            .addMockedMethod("handleImplodingCat")
            .createMock();

    Card implodingCard = Card.IMPLODE;

    EasyMock.expect(gameEngine.popTopCard()).andReturn(implodingCard);

    boolean eliminated = false;
    EasyMock.expect(turnManager.handleImplodingCat()).andReturn(eliminated);

    EasyMock.replay(gameEngine, turnManager);

    boolean result = turnManager.drawAndProcessCard(false);
    assertEquals(eliminated, result);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void drawAndProcessCard_explodeCardFromBottom_handleExplodingCatReturnsFalse_returnFalse() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("handleRegularCard")
            .addMockedMethod("handleExplodingKitten")
            .addMockedMethod("handleImplodingCat")
            .createMock();

    Card implodingCard = Card.EXPLODE;

    EasyMock.expect(gameEngine.popBottomCard()).andReturn(implodingCard);

    boolean eliminated = false;
    EasyMock.expect(turnManager.handleExplodingKitten()).andReturn(eliminated);

    EasyMock.replay(gameEngine, turnManager);

    boolean result = turnManager.drawAndProcessCard(true);
    assertEquals(eliminated, result);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void handleRegularCard_addsCardToPlayerHand() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Player player = EasyMock.createMock(Player.class);
    Card card = Card.SKIP;
    List<Player> players = List.of(player);

    ui.printAddingCardToHand(card.name());

    EasyMock.expect(gameEngine.getPlayers()).andReturn(players);
    player.addCardToHand(card);

    EasyMock.replay(gameEngine, player, ui);

    turnManager.handleRegularCard(card);

    EasyMock.verify(gameEngine, player, ui);
  }

  @Test
  public void handleRegularCard_ExplodeCard_ThrowsIllegalArgumentException() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Card card = Card.EXPLODE;
    String expectedMessage = "Cannot add this card type to a player's hand";

    EasyMock.replay(gameEngine, ui);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      turnManager.handleRegularCard(card);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);


    EasyMock.verify(gameEngine, ui);
  }

  @Test
  public void handleRegularCard_ImplodeCard_ThrowsIllegalArgumentException() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Card card = Card.IMPLODE;
    String expectedMessage = "Cannot add this card type to a player's hand";

    EasyMock.replay(gameEngine, ui);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      turnManager.handleRegularCard(card);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);


    EasyMock.verify(gameEngine, ui);
  }

  @Test
  public void endTurn_drawCounterZero_notDrawFromBottom_drawReturnsFalse_AdvanceTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .addMockedMethod("drawAndProcessCard")
            .createMock();

    turnManager.numExtraCardsToDraw = 0;
    boolean drawFromBottom = false;

    boolean eliminated = false;
    EasyMock.expect(turnManager.drawAndProcessCard(drawFromBottom)).andReturn(eliminated);
    turnManager.advanceTurn(true);

    EasyMock.replay(gameEngine, turnManager);

    turnManager.endTurn(drawFromBottom);

    int expected = 0;
    int actual = turnManager.numExtraCardsToDraw;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void endTurn_drawCounterZero_drawFromBottom_drawReturnsFalse_AdvanceTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .addMockedMethod("drawAndProcessCard")
            .createMock();

    turnManager.numExtraCardsToDraw = 0;
    boolean drawFromBottom = true;

    boolean eliminated = false;
    EasyMock.expect(turnManager.drawAndProcessCard(drawFromBottom)).andReturn(eliminated);
    turnManager.advanceTurn(true);

    EasyMock.replay(gameEngine, turnManager);

    turnManager.endTurn(drawFromBottom);

    int expected = 0;
    int actual = turnManager.numExtraCardsToDraw;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void endTurn_drawCounterOne_notDrawFromBottom_drawReturnsFalse_ExtraCardsDecremented() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .addMockedMethod("drawAndProcessCard")
            .createMock();

    turnManager.numExtraCardsToDraw = 1;
    boolean drawFromBottom = false;

    boolean eliminated = false;
    EasyMock.expect(turnManager.drawAndProcessCard(drawFromBottom)).andReturn(eliminated);

    EasyMock.replay(gameEngine, turnManager);

    turnManager.endTurn(drawFromBottom);

    int expected = 0;
    int actual = turnManager.numExtraCardsToDraw;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void endTurn_drawCounterOne_drawFromBottom_drawReturnsFalse_ExtraCardsDecremented() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .addMockedMethod("drawAndProcessCard")
            .createMock();

    turnManager.numExtraCardsToDraw = 1;
    boolean drawFromBottom = true;

    boolean eliminated = false;
    EasyMock.expect(turnManager.drawAndProcessCard(drawFromBottom)).andReturn(eliminated);

    EasyMock.replay(gameEngine, turnManager);

    turnManager.endTurn(drawFromBottom);

    int expected = 0;
    int actual = turnManager.numExtraCardsToDraw;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void endTurn_drawCounterGreaterThanOne_notDrawFromBottom_drawReturnsFalse_ExtraCardsDecremented() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .addMockedMethod("drawAndProcessCard")
            .createMock();

    turnManager.numExtraCardsToDraw = 3;
    boolean drawFromBottom = false;

    boolean eliminated = false;
    EasyMock.expect(turnManager.drawAndProcessCard(drawFromBottom)).andReturn(eliminated);

    EasyMock.replay(gameEngine, turnManager);

    turnManager.endTurn(drawFromBottom);

    int expected = 2;
    int actual = turnManager.numExtraCardsToDraw;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void endTurn_drawCounterGreaterThanOne_drawFromBottom_drawReturnsFalse_ExtraCardsDecremented() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .addMockedMethod("drawAndProcessCard")
            .createMock();

    turnManager.numExtraCardsToDraw = 3;
    boolean drawFromBottom = true;

    boolean eliminated = false;
    EasyMock.expect(turnManager.drawAndProcessCard(drawFromBottom)).andReturn(eliminated);

    EasyMock.replay(gameEngine, turnManager);

    turnManager.endTurn(drawFromBottom);

    int expected = 2;
    int actual = turnManager.numExtraCardsToDraw;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void endTurn_drawCounterMaxValue_notDrawFromBottom_drawReturnsFalse_ExtraCardsDecremented() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .addMockedMethod("drawAndProcessCard")
            .createMock();

    turnManager.numExtraCardsToDraw = 9;
    boolean drawFromBottom = false;

    boolean eliminated = false;
    EasyMock.expect(turnManager.drawAndProcessCard(drawFromBottom)).andReturn(eliminated);

    EasyMock.replay(gameEngine, turnManager);

    turnManager.endTurn(drawFromBottom);

    int expected = 8;
    int actual = turnManager.numExtraCardsToDraw;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void endTurn_drawCounterMaxValue_drawFromBottom_drawReturnsFalse_ExtraCardsDecremented() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .addMockedMethod("drawAndProcessCard")
            .createMock();

    turnManager.numExtraCardsToDraw = 9;
    boolean drawFromBottom = true;

    boolean eliminated = false;
    EasyMock.expect(turnManager.drawAndProcessCard(drawFromBottom)).andReturn(eliminated);

    EasyMock.replay(gameEngine, turnManager);

    turnManager.endTurn(drawFromBottom);

    int expected = 8;
    int actual = turnManager.numExtraCardsToDraw;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void endTurn_drawCounterMaxValue_drawFromBottom_drawReturnsTrue_ExtraCardsDecremented() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .addMockedMethod("drawAndProcessCard")
            .createMock();

    turnManager.numExtraCardsToDraw = 9;
    boolean drawFromBottom = true;

    boolean eliminated = true;
    EasyMock.expect(turnManager.drawAndProcessCard(drawFromBottom)).andReturn(eliminated);

    EasyMock.replay(gameEngine, turnManager);

    turnManager.endTurn(drawFromBottom);

    int expected = 8;
    int actual = turnManager.numExtraCardsToDraw;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void endTurn_drawCounterIs0_drawFromBottom_drawReturnsTrue_advanceTurnNotCalled() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .addMockedMethod("drawAndProcessCard")
            .createMock();

    turnManager.numExtraCardsToDraw = 0;
    boolean drawFromBottom = true;

    boolean eliminated = true;
    EasyMock.expect(turnManager.drawAndProcessCard(drawFromBottom)).andReturn(eliminated);

    EasyMock.replay(gameEngine, turnManager);

    turnManager.endTurn(drawFromBottom);

    int expected = 0;
    int actual = turnManager.numExtraCardsToDraw;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void endTurn_drawCounterIs0_drawFromTop_drawReturnsFalse_advanceIsCalled() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .addMockedMethod("drawAndProcessCard")
            .createMock();

    turnManager.numExtraCardsToDraw = 0;
    boolean drawFromBottom = false;

    boolean eliminated = false;
    EasyMock.expect(turnManager.drawAndProcessCard(drawFromBottom)).andReturn(eliminated);

    // call advance turn
    turnManager.advanceTurn(!eliminated); // call with survived = true (not eliminated)

    EasyMock.replay(gameEngine, turnManager);

    turnManager.endTurn(drawFromBottom);

    int expected = 0;
    int actual = turnManager.numExtraCardsToDraw;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void handleExplodingKitten_hasDefuseFalse_EliminatesPlayer_returnsTrue() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("eliminateCurrentPlayer")
            .createMock();

    turnManager.currPlayerIndex = 0;
    boolean hasDefuse = false;

    ui.printDrawExplodingKitten(hasDefuse);

    EasyMock.expect(gameEngine.playerHasCard(Card.DEFUSE, turnManager.currPlayerIndex)).andReturn(hasDefuse);
    turnManager.eliminateCurrentPlayer();

    EasyMock.replay(gameEngine, turnManager, ui);

    boolean returnValue = turnManager.handleExplodingKitten();
    assertTrue(returnValue);

    EasyMock.verify(gameEngine, turnManager, ui);
  }

  @Test
  public void handleExplodingKitten_hasDefuseTrue_returnsFalse() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    turnManager.currPlayerIndex = 0;
    boolean hasDefuse = true;
    int placementLocation = 0;
    int drawPileSize = 3;

    ui.printDrawExplodingKitten(hasDefuse);

    EasyMock.expect(gameEngine.playerHasCard(Card.DEFUSE, turnManager.currPlayerIndex)).andReturn(hasDefuse);
    gameEngine.removeCardFromPlayer(Card.DEFUSE, turnManager.currPlayerIndex);
    gameEngine.discardCard(Card.DEFUSE);
    EasyMock.expect(gameEngine.getDrawPile()).andReturn(new Card[drawPileSize]);
    EasyMock.expect(ui.promptPlacementForExplodeOrImplode(drawPileSize, true)).andReturn(placementLocation);
    gameEngine.addCardToDrawPileAt(Card.EXPLODE, placementLocation);

    EasyMock.replay(gameEngine, ui);

    boolean returnValue = turnManager.handleExplodingKitten();
    assertFalse(returnValue);

    EasyMock.verify(gameEngine, ui);
  }

  @Test
  public void promptPlayNope_UIPromptNopeReturnsFalse_returnFalse() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("promptAndValidateNopePlayerAndPlayNopeIfSo")
            .createMock();

    EasyMock.expect(turnManager.promptAndValidateNopePlayerAndPlayNopeIfSo()).andReturn(false);

    EasyMock.replay(gameEngine, turnManager);

    boolean actualNopeWasPlayed = turnManager.promptPlayNope();
    boolean expectedNopeWasPlayed = false;

    assertEquals(expectedNopeWasPlayed, actualNopeWasPlayed);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void promptPlayNope_UIPromptNopeReturnsTrue_promptPlayNopeReturnsTrue_returnFalse() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("promptAndValidateNopePlayerAndPlayNopeIfSo")
            .createMock();

    // We want the first call to promptAndValidateNopePlayerAndPlayNopeIfSo to return true
    // as we want it to be that somebody did play a Nope.
    // We want the second call to it to also return true and the third to return false,
    // representing somebody playing another nope, but nobody noping that nope.
    // thus, the nope was noped, so we should return false.
    EasyMock.expect(turnManager.promptAndValidateNopePlayerAndPlayNopeIfSo()).andReturn(true);
    EasyMock.expect(turnManager.promptAndValidateNopePlayerAndPlayNopeIfSo()).andReturn(true);
    EasyMock.expect(turnManager.promptAndValidateNopePlayerAndPlayNopeIfSo()).andReturn(false);

    EasyMock.replay(gameEngine, turnManager);

    boolean actualNopeWasPlayed = turnManager.promptPlayNope();
    boolean expectedNopeWasPlayed = false;

    assertEquals(expectedNopeWasPlayed, actualNopeWasPlayed);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void promptPlayNope_UIPromptNopeReturnsTrue_promptPlayNopeReturnsFalse_returnTrue() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("promptAndValidateNopePlayerAndPlayNopeIfSo")
            .createMock();

    // We want the first call to promptAndValidateNopePlayerAndPlayNopeIfSo to return true
    // as we want it to be that somebody did play a Nope.
    // We want the second call to it to return false, representing nobody noping that nope.
    // Thus, the previous card got noped, so we should return true.
    EasyMock.expect(turnManager.promptAndValidateNopePlayerAndPlayNopeIfSo()).andReturn(true);
    EasyMock.expect(turnManager.promptAndValidateNopePlayerAndPlayNopeIfSo()).andReturn(false);

    EasyMock.replay(gameEngine, turnManager);

    boolean actualNopeWasPlayed = turnManager.promptPlayNope();
    boolean expectedNopeWasPlayed = true;

    assertEquals(expectedNopeWasPlayed, actualNopeWasPlayed);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void handleImplodingCat_faceUp_EliminatesPlayer_returnTrue() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("eliminateCurrentPlayer")
            .createMock();

    turnManager.currPlayerIndex = 0;
    turnManager.isImplodingCatFaceUp = true;

    ui.printDrawImplodingKitten(turnManager.isImplodingCatFaceUp);

    // Expectations
    turnManager.eliminateCurrentPlayer();

    EasyMock.replay(gameEngine, turnManager, ui);

    boolean returnValue = turnManager.handleImplodingCat();
    assertTrue(returnValue);

    EasyMock.verify(gameEngine, turnManager, ui);
  }

  @Test
  public void handleImplodingCat_faceDown_CardInsertedBack_returnFalse() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);


    turnManager.currPlayerIndex = 0;
    turnManager.isImplodingCatFaceUp = false;
    int placementLocation = 0;
    int drawPileSize = 3;

    ui.printDrawImplodingKitten(turnManager.isImplodingCatFaceUp);

    EasyMock.expect(gameEngine.getDrawPile()).andReturn(new Card[drawPileSize]);
    EasyMock.expect(ui.promptPlacementForExplodeOrImplode(drawPileSize, false)).andReturn(placementLocation);
    gameEngine.addCardToDrawPileAt(Card.IMPLODE, placementLocation);

    EasyMock.replay(gameEngine, ui);

    boolean returnValue = turnManager.handleImplodingCat();
    assertFalse(returnValue);
    assertTrue(turnManager.isImplodingCatFaceUp);

    EasyMock.verify(gameEngine, ui);
  }

  @Test
  public void promptAndValidateNopePlayerAndPlayNopeIfSo_uiPromptNopeReturnsEmptyString_returnFalse() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    EasyMock.expect(ui.promptNope(false)).andReturn("");

    EasyMock.replay(gameEngine, ui);

    boolean actualReturnValue = turnManager.promptAndValidateNopePlayerAndPlayNopeIfSo();
    boolean expectedReturnValue = false;

    assertEquals(expectedReturnValue, actualReturnValue);

    EasyMock.verify(gameEngine, ui);
  }

  @Test
  public void promptAndValidateNopePlayerAndPlayNopeIfSo_uiPromptNopeReturnsValidPlayerName_gameEngineReturnsPlayer_playerHasCard_returnTrue() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String validPlayerName = "John";
    Player player = EasyMock.createMock(Player.class);
    int validPlayerIndex = 0;

    EasyMock.expect(ui.promptNope(false)).andReturn(validPlayerName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(validPlayerName)).andReturn(validPlayerIndex);
    EasyMock.expect(gameEngine.getPlayerByIndex(validPlayerIndex)).andReturn(player);
    EasyMock.expect(player.removeCardFromHand(Card.NOPE)).andReturn(true);

    ui.printNopePlayed();

    EasyMock.replay(gameEngine, ui, player);

    boolean actualReturnValue = turnManager.promptAndValidateNopePlayerAndPlayNopeIfSo();
    boolean expectedReturnValue = true;

    assertEquals(expectedReturnValue, actualReturnValue);

    EasyMock.verify(gameEngine, ui, player);
  }

  @Test
  public void promptAndValidateNopePlayerAndPlayNopeIfSo_uiPromptNopeReturnsValidPlayerName_gameEngineReturnsPlayer_playerDoesNotHaveCard_retry_uiNewNameReturnsEmpty_returnFalse() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String validPlayerName = "John";
    Player player = EasyMock.createMock(Player.class);
    int validPlayerIndex = 0;

    EasyMock.expect(ui.promptNope(false)).andReturn(validPlayerName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(validPlayerName)).andReturn(validPlayerIndex);
    EasyMock.expect(gameEngine.getPlayerByIndex(validPlayerIndex)).andReturn(player);
    EasyMock.expect(player.removeCardFromHand(Card.NOPE)).andReturn(false);
    EasyMock.expect(player.getName()).andReturn(validPlayerName);
    EasyMock.expect(ui.printLastPlayerDidNotHaveNopeAndGetNewPlayer(validPlayerName)).andReturn("");

    EasyMock.replay(gameEngine, ui, player);

    boolean actualReturnValue = turnManager.promptAndValidateNopePlayerAndPlayNopeIfSo();
    boolean expectedReturnValue = false;

    assertEquals(expectedReturnValue, actualReturnValue);

    EasyMock.verify(gameEngine, ui, player);
  }

  @Test
  public void promptAndValidateNopePlayerAndPlayNopeIfSo_uiPromptNopeReturnsValidPlayerName_gameEngineReturnsPlayer_playerDoesNotHaveCard_retry_uiNewNameReturnsValidName_gameEngineReturnsPlayer_playerHasCard_returnTrue() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String validPlayerName1 = "John";
    String validPlayerName2 = "Joe";
    Player player1 = EasyMock.createMock(Player.class);
    Player player2 = EasyMock.createMock(Player.class);
    int validPlayerIndex1 = 0;
    int validPlayerIndex2 = 1;

    EasyMock.expect(ui.promptNope(false)).andReturn(validPlayerName1);
    EasyMock.expect(gameEngine.getPlayerIndexByName(validPlayerName1)).andReturn(validPlayerIndex1);
    EasyMock.expect(gameEngine.getPlayerByIndex(validPlayerIndex1)).andReturn(player1);
    EasyMock.expect(player1.removeCardFromHand(Card.NOPE)).andReturn(false);
    EasyMock.expect(player1.getName()).andReturn(validPlayerName1);
    EasyMock.expect(ui.printLastPlayerDidNotHaveNopeAndGetNewPlayer(validPlayerName1)).andReturn(validPlayerName2);
    EasyMock.expect(gameEngine.getPlayerIndexByName(validPlayerName2)).andReturn(validPlayerIndex2);
    EasyMock.expect(gameEngine.getPlayerByIndex(validPlayerIndex2)).andReturn(player2);
    EasyMock.expect(player2.removeCardFromHand(Card.NOPE)).andReturn(true);

    ui.printNopePlayed();

    EasyMock.replay(gameEngine, ui, player1, player2);

    boolean actualReturnValue = turnManager.promptAndValidateNopePlayerAndPlayNopeIfSo();
    boolean expectedReturnValue = true;

    assertEquals(expectedReturnValue, actualReturnValue);

    EasyMock.verify(gameEngine, ui, player1, player2);
  }

  @Test
  public void promptAndValidateNopePlayerAndPlayNopeIfSo_uiPromptNopeReturnsInvalidPlayerName_gameEngineThrowsException_retry_uiNewNameReturnsEmpty_returnFalse() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String validPlayerName = "John";
    Player player = EasyMock.createMock(Player.class);
    int validPlayerIndex = 0;

    EasyMock.expect(ui.promptNope(false)).andReturn(validPlayerName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(validPlayerName)).andReturn(validPlayerIndex);
    EasyMock.expect(gameEngine.getPlayerByIndex(validPlayerIndex)).andThrow(new IndexOutOfBoundsException("Player does not exist at this index"));
    EasyMock.expect(ui.promptNope(true)).andReturn("");

    EasyMock.replay(gameEngine, ui, player);

    boolean actualReturnValue = turnManager.promptAndValidateNopePlayerAndPlayNopeIfSo();
    boolean expectedReturnValue = false;

    assertEquals(expectedReturnValue, actualReturnValue);

    EasyMock.verify(gameEngine, ui, player);
  }

  @Test
  public void doSkip_numExtraCardsToDrawOne_numExtraCardsToDrawDecremented() {
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui);

    ui.printSkipping();
    turnManager.numExtraCardsToDraw = 1;

    EasyMock.replay(ui);
    turnManager.doSkip();

    int actualNumExtraCardsToDraw = turnManager.numExtraCardsToDraw;
    int expectedNumExtraCardsToDraw = 0;

    assertEquals(expectedNumExtraCardsToDraw, actualNumExtraCardsToDraw);
    EasyMock.verify(ui);
  }

  @Test
  public void doSkip_numExtraCardsToDrawTwo_numExtraCardsToDrawDecremented() {
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui);

    int extraCards = 2;

    turnManager.numExtraCardsToDraw = 2;

    ui.printSkipping();

    EasyMock.replay(ui);
    turnManager.doSkip();

    int actualNumExtraCardsToDraw = turnManager.numExtraCardsToDraw;
    int expectedNumExtraCardsToDraw = extraCards - 1;

    assertEquals(expectedNumExtraCardsToDraw, actualNumExtraCardsToDraw);
    EasyMock.verify(ui);
  }

  @Test
  public void doSkip_NoExtraCardsToDraw_turnEnded() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .createMock();

    int extraCards = 0;
    turnManager.numExtraCardsToDraw = extraCards;
    turnManager.advanceTurn(true);

    ui.printSkipping();

    EasyMock.replay(turnManager, ui, gameEngine);

    turnManager.doSkip();

    int actualNumExtraCardsToDraw = turnManager.numExtraCardsToDraw;

    assertEquals(extraCards, actualNumExtraCardsToDraw);

    EasyMock.verify(turnManager, ui, gameEngine);
  }

  @Test
  public void getPlayableCard_attack() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "attack";
    Card expectedCard = Card.ATTACK;
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(expectedCard);

    EasyMock.replay(gameEngine);

    Card actualCard = turnManager.getPlayableCard(cardName);

    assertEquals(expectedCard, actualCard);

    EasyMock.verify(gameEngine);
  }

  @Test
  public void getPlayableCard_skip() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "skip";
    Card expectedCard = Card.SKIP;
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(expectedCard);

    EasyMock.replay(gameEngine);

    Card actualCard = turnManager.getPlayableCard(cardName);

    assertEquals(expectedCard, actualCard);

    EasyMock.verify(gameEngine);
  }

  @Test
  public void getPlayableCard_targetedAttack() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "targeted attack";
    Card expectedCard = Card.TARGETED_ATTACK;
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(expectedCard);

    EasyMock.replay(gameEngine);

    Card actualCard = turnManager.getPlayableCard(cardName);

    assertEquals(expectedCard, actualCard);

    EasyMock.verify(gameEngine);
  }

  @Test
  public void getPlayableCard_shuffle() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "shuffle";
    Card expectedCard = Card.SHUFFLE;
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(expectedCard);

    EasyMock.replay(gameEngine);

    Card actualCard = turnManager.getPlayableCard(cardName);

    assertEquals(expectedCard, actualCard);

    EasyMock.verify(gameEngine);
  }

  @Test
  public void getPlayableCard_seeTheFuture() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "see the future";
    Card expectedCard = Card.SEE_THE_FUTURE;
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(expectedCard);

    EasyMock.replay(gameEngine);

    Card actualCard = turnManager.getPlayableCard(cardName);

    assertEquals(expectedCard, actualCard);

    EasyMock.verify(gameEngine);
  }

  @Test
  public void getPlayableCard_reverse() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "reverse";
    Card expectedCard = Card.REVERSE;
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(expectedCard);

    EasyMock.replay(gameEngine);

    Card actualCard = turnManager.getPlayableCard(cardName);

    assertEquals(expectedCard, actualCard);

    EasyMock.verify(gameEngine);
  }

  @Test
  public void getPlayableCard_drawFromBottom() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "draw from bottom";
    Card expectedCard = Card.DRAW_FROM_BOTTOM;
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(expectedCard);

    EasyMock.replay(gameEngine);

    Card actualCard = turnManager.getPlayableCard(cardName);

    assertEquals(expectedCard, actualCard);

    EasyMock.verify(gameEngine);
  }

  @Test
  public void getPlayableCard_alterTheFuture() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "alter the future";
    Card expectedCard = Card.ALTER_THE_FUTURE;
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(expectedCard);

    EasyMock.replay(gameEngine);

    Card actualCard = turnManager.getPlayableCard(cardName);

    assertEquals(expectedCard, actualCard);

    EasyMock.verify(gameEngine);
  }

  @Test
  public void getPlayableCard_invalidInput() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "invalid";
    String exceptionMessage = "Could not parse input.";
    EasyMock.expect(gameEngine.getCardByName(cardName)).andThrow(
            new IllegalArgumentException(exceptionMessage));

    EasyMock.replay(gameEngine);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      turnManager.getPlayableCard(cardName);
    });

    String actualMessage = exception.getMessage();
    assertEquals(exceptionMessage, actualMessage);
    EasyMock.verify(gameEngine);
  }

  @Test
  public void getPlayableCard_playedNopeCard() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "nope";
    Card card = Card.NOPE;
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(card);
    ui.printUnplayableCardErrorNope();

    EasyMock.replay(gameEngine, ui);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      turnManager.getPlayableCard(cardName);
    });

    String expectedMessage = "You cannot play a nope right now.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(gameEngine, ui);
  }

  @Test
  public void getPlayableCard_playedExplodingKitten() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "exploding kitten";
    Card card = Card.EXPLODE;
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(card);

    EasyMock.replay(gameEngine, ui);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      turnManager.getPlayableCard(cardName);
    });

    String expectedMessage = "You cannot play an exploding/imploding kitten.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(gameEngine, ui);
  }

  @Test
  public void getPlayableCard_playedImplodingKitten() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "imploding kitten";
    Card card = Card.IMPLODE;
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(card);

    EasyMock.replay(gameEngine, ui);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      turnManager.getPlayableCard(cardName);
    });

    String expectedMessage = "You cannot play an exploding/imploding kitten.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(gameEngine, ui);
  }

  @Test
  public void getPlayableCard_playedDefuseCard() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "defuse";
    Card card = Card.DEFUSE;
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(card);
    ui.printUnplayableCardErrorDefuse();

    EasyMock.replay(gameEngine, ui);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      turnManager.getPlayableCard(cardName);
    });

    String expectedMessage = "You cannot play a defuse right now.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(gameEngine, ui);
  }

  @ParameterizedTest
  @CsvSource({
    "taco cat", "beard cat", "rainbow cat", "feral cat", "hairy potato cat"
  })
  public void getPlayableCard_playedCatCards(String cardName) {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Card card;
    switch (cardName) {
      case "taco cat":
        card = Card.TACO_CAT;
        break;
      case "beard cat":
        card = Card.BEARD_CAT;
        break;
      case "rainbow cat":
        card = Card.RAINBOW_CAT;
        break;
      case "feral cat":
        card = Card.FERAL_CAT;
        break;
      case "hairy potato cat":
        card = Card.HAIRY_POTATO_CAT;
        break;
      default:
        throw new IllegalArgumentException("csv arguments do not match a card type.");
    }
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(card);
    ui.printUnplayableCardErrorCatCard();

    EasyMock.replay(gameEngine, ui);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      turnManager.getPlayableCard(cardName);
    });

    String expectedMessage = "You must play a cat card as a combo.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(gameEngine, ui);
  }


  @Test
  public void playCardLoop_UserInputEmpty_callsEndTurn_endsTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .addMockedMethod("printPlayerHand")
            .addMockedMethod("printTurnInfo")
            .createMock();

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);

    EasyMock.expect(ui.promptPlayCard(false)).andReturn("");

    turnManager.endTurn(false);
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = true; // Manually terminate loop
      return null;
    });

    EasyMock.replay(turnManager, gameEngine, ui);

    turnManager.playCardLoop();

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void playCardLoop_UserInputAttack_callsDoAttack_endsTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .addMockedMethod("getPlayableCard")
            .addMockedMethod("doAttack")
            .addMockedMethod("promptPlayNope")
            .addMockedMethod("printPlayerHand")
            .addMockedMethod("printTurnInfo")
            .createMock();

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);

    int currPlayerIndex = 0;
    turnManager.currPlayerIndex = currPlayerIndex;

    String userInput = "attack";
    Card userInputCard = Card.ATTACK;
    boolean playerHasCard = true;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.getPlayableCard(userInput)).andReturn(userInputCard);
    EasyMock.expect(gameEngine.playerHasCard(userInputCard, currPlayerIndex)).andReturn(playerHasCard);
    boolean somebodyPlayedNope = false;
    EasyMock.expect(turnManager.promptPlayNope()).andReturn(somebodyPlayedNope);

    gameEngine.removeCardFromPlayer(userInputCard, currPlayerIndex);
    turnManager.doAttack();
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = true; // Manually terminate loop
      return null;
    });

    EasyMock.replay(turnManager, gameEngine, ui);

    turnManager.playCardLoop();

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void playCardLoop_UserInputSeeTheFuture_callsDoSeeTheFuture_doesNotEndTurn_secondUserInputIsEmpty_endsTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .addMockedMethod("getPlayableCard")
            .addMockedMethod("doSeeTheFuture")
            .addMockedMethod("promptPlayNope")
            .addMockedMethod("printPlayerHand")
            .addMockedMethod("printTurnInfo")
            .createMock();

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);

    int currPlayerIndex = 0;
    turnManager.currPlayerIndex = currPlayerIndex;

    String userInput = "see the future";
    Card userInputCard = Card.SEE_THE_FUTURE;
    boolean playerHasCard = true;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.getPlayableCard(userInput)).andReturn(userInputCard);
    EasyMock.expect(gameEngine.playerHasCard(userInputCard, currPlayerIndex)).andReturn(playerHasCard);
    boolean somebodyPlayedNope = false;
    EasyMock.expect(turnManager.promptPlayNope()).andReturn(somebodyPlayedNope);

    gameEngine.removeCardFromPlayer(userInputCard, currPlayerIndex);
    turnManager.doSeeTheFuture();
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = false; // do not terminate loop
      return null;
    });

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);
    String newUserInput = "";
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(newUserInput);
    turnManager.endTurn(false);
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = true; // Manually terminate loop
      return null;
    });

    EasyMock.replay(turnManager, gameEngine, ui);

    turnManager.playCardLoop();

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void playCardLoop_UserInputAlterTheFuture_playerDoesNotHaveCard_secondUserInputIsEmpty_endsTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .addMockedMethod("getPlayableCard")
            .addMockedMethod("printPlayerHand")
            .addMockedMethod("printTurnInfo")
            .createMock();

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);

    int currPlayerIndex = 0;
    turnManager.currPlayerIndex = currPlayerIndex;

    String userInput = "alter the future";
    Card userInputCard = Card.ALTER_THE_FUTURE;
    boolean playerHasCard = false;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.getPlayableCard(userInput)).andReturn(userInputCard);
    EasyMock.expect(gameEngine.playerHasCard(userInputCard, currPlayerIndex)).andReturn(playerHasCard);

    boolean rePrompting = true;

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);
    String newUserInput = "";
    EasyMock.expect(ui.promptPlayCard(rePrompting)).andReturn(newUserInput);
    turnManager.endTurn(false);
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = true; // Manually terminate loop
      return null;
    });

    EasyMock.replay(turnManager, gameEngine, ui);

    turnManager.playCardLoop();

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void playCardLoop_InvalidUserInput_promptAndPlayComboCatCardsReturnsFalse_secondUserInputIsEmpty_endsTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .addMockedMethod("getPlayableCard")
            .addMockedMethod("promptAndPlayCombo")
            .addMockedMethod("printPlayerHand")
            .addMockedMethod("printTurnInfo")
            .createMock();

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);

    String userInput = "invalid";
    String exceptionMessage = "Could not parse input";
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.getPlayableCard(userInput)).andThrow(
            new IllegalArgumentException(exceptionMessage)
    );

    boolean isRePrompting = true;

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);
    String newUserInput = "";
    EasyMock.expect(ui.promptPlayCard(isRePrompting)).andReturn(newUserInput);
    turnManager.endTurn(false);
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = true; // Manually terminate loop
      return null;
    });

    EasyMock.replay(turnManager, gameEngine, ui);

    turnManager.playCardLoop();

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void playCardLoopImpossibleCase_UserInputSkip_getPlayableCardReturnsExplode_PlayerHasCardReturnsTrue_throwsException() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .addMockedMethod("getPlayableCard")
            .addMockedMethod("promptPlayNope")
            .addMockedMethod("printPlayerHand")
            .addMockedMethod("printTurnInfo")
            .createMock();

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);

    int currPlayerIndex = 0;
    turnManager.currPlayerIndex = currPlayerIndex;

    String userInput = "skip";
    Card impossibleCardReturn = Card.EXPLODE;
    boolean impossiblePlayerHasCard = true;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.getPlayableCard(userInput)).andReturn(impossibleCardReturn);
    EasyMock.expect(gameEngine.playerHasCard(impossibleCardReturn, currPlayerIndex)).andReturn(impossiblePlayerHasCard);
    boolean somebodyPlayedNope = false;
    EasyMock.expect(turnManager.promptPlayNope()).andReturn(somebodyPlayedNope);

    gameEngine.removeCardFromPlayer(impossibleCardReturn, currPlayerIndex);
    EasyMock.replay(turnManager, gameEngine, ui);

    String expectedMessage = "A card was played that should not have been played.";

    Exception exception = assertThrows(IllegalArgumentException.class, turnManager::playCardLoop);

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void playCardLoop_UserInputAlterTheFuture_callsAlterTheFuture_doesNotEndTurn_secondUserInputSkip_callsDoSkip_endsTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .addMockedMethod("getPlayableCard")
            .addMockedMethod("doAlterTheFuture")
            .addMockedMethod("doSkip")
            .addMockedMethod("promptPlayNope")
            .addMockedMethod("printPlayerHand")
            .addMockedMethod("printTurnInfo")
            .createMock();

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);

    int currPlayerIndex = 0;
    turnManager.currPlayerIndex = currPlayerIndex;

    String userInput = "alter the future";
    Card userInputCard = Card.ALTER_THE_FUTURE;
    boolean playerHasCard = true;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.getPlayableCard(userInput)).andReturn(userInputCard);
    EasyMock.expect(gameEngine.playerHasCard(userInputCard, currPlayerIndex)).andReturn(playerHasCard);
    boolean somebodyPlayedNope = false;
    EasyMock.expect(turnManager.promptPlayNope()).andReturn(somebodyPlayedNope);

    gameEngine.removeCardFromPlayer(userInputCard, currPlayerIndex);
    turnManager.doAlterTheFuture();
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = false; // do not terminate loop
      return null;
    });

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);
    String newUserInput = "skip";
    Card newUserInputCard = Card.SKIP;
    boolean newPlayerHasCard = true;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(newUserInput);
    EasyMock.expect(turnManager.getPlayableCard(newUserInput)).andReturn(newUserInputCard);
    EasyMock.expect(gameEngine.playerHasCard(newUserInputCard, currPlayerIndex)).andReturn(newPlayerHasCard);
    EasyMock.expect(turnManager.promptPlayNope()).andReturn(somebodyPlayedNope);

    gameEngine.removeCardFromPlayer(newUserInputCard, currPlayerIndex);
    turnManager.doSkip();
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = true; // Manually terminate loop
      return null;
    });

    EasyMock.replay(turnManager, gameEngine, ui);

    turnManager.playCardLoop();

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void playCardLoop_UserInputShuffle_callsShuffle_doesNotEndTurn_secondUserInputTargetedAttack_callsDoTargetedAttack_endsTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .addMockedMethod("getPlayableCard")
            .addMockedMethod("doShuffle")
            .addMockedMethod("doTargetedAttack")
            .addMockedMethod("promptPlayNope")
            .addMockedMethod("printPlayerHand")
            .addMockedMethod("printTurnInfo")
            .createMock();

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);

    int currPlayerIndex = 0;
    turnManager.currPlayerIndex = currPlayerIndex;

    String userInput = "shuffle";
    Card userInputCard = Card.SHUFFLE;
    boolean playerHasCard = true;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.getPlayableCard(userInput)).andReturn(userInputCard);
    EasyMock.expect(gameEngine.playerHasCard(userInputCard, currPlayerIndex)).andReturn(playerHasCard);
    boolean somebodyPlayedNope = false;
    EasyMock.expect(turnManager.promptPlayNope()).andReturn(somebodyPlayedNope);

    gameEngine.removeCardFromPlayer(userInputCard, currPlayerIndex);
    turnManager.doShuffle();
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = false; // do not terminate loop
      return null;
    });

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);
    String newUserInput = "targeted attack";
    Card newUserInputCard = Card.TARGETED_ATTACK;
    boolean newPlayerHasCard = true;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(newUserInput);
    EasyMock.expect(turnManager.getPlayableCard(newUserInput)).andReturn(newUserInputCard);
    EasyMock.expect(gameEngine.playerHasCard(newUserInputCard, currPlayerIndex)).andReturn(newPlayerHasCard);
    EasyMock.expect(turnManager.promptPlayNope()).andReturn(somebodyPlayedNope);

    gameEngine.removeCardFromPlayer(newUserInputCard, currPlayerIndex);
    turnManager.doTargetedAttack();
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = true; // Manually terminate loop
      return null;
    });

    EasyMock.replay(turnManager, gameEngine, ui);

    turnManager.playCardLoop();

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void playCardLoop_UserInputReverse_callsDoReverse_endsTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .addMockedMethod("getPlayableCard")
            .addMockedMethod("doReverse")
            .addMockedMethod("promptPlayNope")
            .addMockedMethod("printPlayerHand")
            .addMockedMethod("printTurnInfo")
            .createMock();

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);
    int currPlayerIndex = 0;
    turnManager.currPlayerIndex = currPlayerIndex;

    String userInput = "reverse";
    Card userInputCard = Card.REVERSE;
    boolean playerHasCard = true;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.getPlayableCard(userInput)).andReturn(userInputCard);
    EasyMock.expect(gameEngine.playerHasCard(userInputCard, currPlayerIndex)).andReturn(playerHasCard);
    boolean somebodyPlayedNope = false;
    EasyMock.expect(turnManager.promptPlayNope()).andReturn(somebodyPlayedNope);

    gameEngine.removeCardFromPlayer(userInputCard, currPlayerIndex);
    turnManager.doReverse();
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = true; // terminate loop
      return null;
    });

    EasyMock.replay(turnManager, gameEngine, ui);

    turnManager.playCardLoop();

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void playCardLoop_UserInputDrawFromBottom_callsDoDrawFromBottom_endsTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .addMockedMethod("getPlayableCard")
            .addMockedMethod("doDrawFromBottom")
            .addMockedMethod("promptPlayNope")
            .addMockedMethod("printPlayerHand")
            .addMockedMethod("printTurnInfo")
            .createMock();

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);
    int currPlayerIndex = 0;
    turnManager.currPlayerIndex = currPlayerIndex;

    String userInput = "draw from bottom";
    Card userInputCard = Card.DRAW_FROM_BOTTOM;
    boolean playerHasCard = true;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.getPlayableCard(userInput)).andReturn(userInputCard);
    EasyMock.expect(gameEngine.playerHasCard(userInputCard, currPlayerIndex)).andReturn(playerHasCard);
    boolean somebodyPlayedNope = true;
    EasyMock.expect(turnManager.promptPlayNope()).andReturn(somebodyPlayedNope);

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.getPlayableCard(userInput)).andReturn(userInputCard);
    EasyMock.expect(gameEngine.playerHasCard(userInputCard, currPlayerIndex)).andReturn(playerHasCard);
    boolean somebodyPlayedNope2ndTime = false;
    EasyMock.expect(turnManager.promptPlayNope()).andReturn(somebodyPlayedNope2ndTime);

    gameEngine.removeCardFromPlayer(userInputCard, currPlayerIndex);
    turnManager.doDrawFromBottom();
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = true; // terminate loop
      return null;
    });

    EasyMock.replay(turnManager, gameEngine, ui);

    turnManager.playCardLoop();

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void playCardLoop_UserInput2CatCards_returnsTrue_continueLoop() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .addMockedMethod("promptAndPlayCombo")
            .addMockedMethod("printPlayerHand")
            .addMockedMethod("printTurnInfo")
            .createMock();

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);

    boolean isRePrompting = true;
    String userInput = "2 cards";
    int numCatCards = 2;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.promptAndPlayCombo(numCatCards)).andReturn(isRePrompting);

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);

    EasyMock.expect(ui.promptPlayCard(isRePrompting)).andReturn("");

    turnManager.endTurn(false);
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = true; // Manually terminate loop
      return null;
    });

    EasyMock.replay(turnManager, gameEngine, ui);

    turnManager.playCardLoop();

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void playCardLoop_UserInput3CatCards_returnsFalse_continueLoop() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .addMockedMethod("promptAndPlayCombo")
            .addMockedMethod("printPlayerHand")
            .addMockedMethod("printTurnInfo")
            .createMock();

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);

    boolean isRePrompting = false;
    String userInput = "3 cards";
    int numCatCards = 3;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.promptAndPlayCombo(numCatCards)).andReturn(isRePrompting);

    turnManager.printTurnInfo();
    turnManager.printPlayerHand(turnManager.currPlayerIndex);

    EasyMock.expect(ui.promptPlayCard(isRePrompting)).andReturn("");

    turnManager.endTurn(false);
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = true; // Manually terminate loop
      return null;
    });

    EasyMock.replay(turnManager, gameEngine, ui);

    turnManager.playCardLoop();

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void promptAndPlayCombo_2Cards_inputFeralCatAndTacoCat_cardsAreAsExpected_returnsFalse() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("do2CardCombo")
            .createMock();

    int currPlayerIndex = 0;
    turnManager.currPlayerIndex = 0;

    int numCards = 2;
    String[] stringCards = new String[] { "feral cat", "taco cat" };
    EasyMock.expect(ui.promptPlayComboCards(numCards)).andReturn(stringCards);

    Card c1 = Card.FERAL_CAT;
    Card c2 = Card.TACO_CAT;
    Card[] cards = new Card[] { c1, c2 };
    EasyMock.expect(gameEngine.validateComboCards(stringCards, currPlayerIndex)).andReturn(cards);

    turnManager.do2CardCombo();
    gameEngine.removeCardFromPlayer(c1, currPlayerIndex);
    gameEngine.removeCardFromPlayer(c2, currPlayerIndex);

    EasyMock.replay(turnManager, gameEngine, ui);

    boolean expectedReturn = false;
    boolean actualReturn = turnManager.promptAndPlayCombo(numCards);
    assertEquals(expectedReturn, actualReturn);

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void promptAndPlayCombo_2Cards_inputShuffleCatAndTacoCat_validateCardsThrowsException_returnTrue() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("do2CardCombo")
            .createMock();

    int numCards = 2;
    String[] stringCards = new String[] { "feral cat", "taco cat" };
    EasyMock.expect(ui.promptPlayComboCards(numCards)).andReturn(stringCards);

    String exceptionMessage = "Cards do not match.";
    EasyMock.expect(
            gameEngine.validateComboCards(stringCards, turnManager.currPlayerIndex)
    ).andThrow(
            new IllegalArgumentException(exceptionMessage)
    );

    ui.printValidateComboCardErrorMessage(exceptionMessage);

    EasyMock.replay(turnManager, gameEngine, ui);

    boolean expectedReturn = true;
    boolean actualReturn = turnManager.promptAndPlayCombo(numCards);
    assertEquals(expectedReturn, actualReturn);

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void promptAndPlayCombo_3Cards_input3Attack_cardsAreAsExpected_returnsFalse() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("do3CardCombo")
            .createMock();

    int currPlayerIndex = 0;
    turnManager.currPlayerIndex = 0;

    int numCards = 3;
    String[] stringCards = new String[] { "attack", "attack", "attack" };
    EasyMock.expect(ui.promptPlayComboCards(numCards)).andReturn(stringCards);

    Card card = Card.ATTACK;
    Card[] cards = new Card[] { card, card, card };
    EasyMock.expect(gameEngine.validateComboCards(stringCards, currPlayerIndex)).andReturn(cards);

    turnManager.do3CardCombo();
    gameEngine.removeCardFromPlayer(card, currPlayerIndex);
    EasyMock.expectLastCall().times(3);

    EasyMock.replay(turnManager, gameEngine, ui);

    boolean expectedReturn = false;
    boolean actualReturn = turnManager.promptAndPlayCombo(numCards);
    assertEquals(expectedReturn, actualReturn);

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void promptAndPlayCombo_3Cards_inputIsEmpty_throwException() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("do3CardCombo")
            .createMock();

    int numCards = 3;
    String[] stringCards = new String[0];
    EasyMock.expect(ui.promptPlayComboCards(numCards)).andReturn(stringCards);

    String expectedMessage = "Number of cards returned by user does not match combo count.";
    EasyMock.expect(ui.printMismatchUserCardsAndComboCount()).andReturn(expectedMessage);

    EasyMock.replay(turnManager, gameEngine, ui);

    Exception exception = assertThrows(IllegalStateException.class, () -> {
      turnManager.promptAndPlayCombo(numCards);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void promptAndPlayCombo_2Cards_inputIsValid_validateIsEmpty_throwException() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("do3CardCombo")
            .createMock();

    int numCards = 2;
    String[] stringCards = new String[] { "beard cat", "beard cat" };
    EasyMock.expect(ui.promptPlayComboCards(numCards)).andReturn(stringCards);

    Card[] validateCardsReturn = new Card[0];
    EasyMock.expect(
            gameEngine.validateComboCards(stringCards, turnManager.currPlayerIndex)
    ).andReturn(validateCardsReturn);

    String expectedMessage = "Number of cards returned by card validation does not match combo count.";
    EasyMock.expect(ui.printMismatchCardValidationCardsAndComboCount()).andReturn(expectedMessage);

    EasyMock.replay(turnManager, gameEngine, ui);

    Exception exception = assertThrows(IllegalStateException.class, () -> {
      turnManager.promptAndPlayCombo(numCards);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(turnManager, gameEngine, ui);
  }

  @Test
  public void promptAndPlayCombo_1Card_throwException() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String expectedMessage = "You must play 2 or 3 cards as a combo.";
    int numCards = 1;
    EasyMock.expect(ui.printMustPlay2Or3CardsAsComboError()).andReturn(expectedMessage);

    EasyMock.replay(gameEngine, ui);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      turnManager.promptAndPlayCombo(numCards);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(gameEngine, ui);
  }

  @Test
  public void doTargetedAttack_invalidPlayerName_noExtraCardsToDraw_promptAgainForInput() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("printPlayers")
            .createMock();

    ui.printDoingTargetedAttack();

    String invalidPlayerName = "";
    int currPlayerIndex = 3;
    int expectedPlayerIndex = 0;
    int extraCards = 0;

    turnManager.printPlayers();
    EasyMock.expectLastCall().times(2);

    EasyMock.expect(ui.promptTargetedAttack(false)).andReturn(invalidPlayerName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(invalidPlayerName)).andThrow(new NoSuchElementException("No player with that name could be found."));
    EasyMock.expect(ui.promptTargetedAttack(true)).andReturn("John");
    EasyMock.expect(gameEngine.getPlayerIndexByName("John")).andReturn(expectedPlayerIndex);

    ui.printTargetedAttackResult(extraCards + 1);

    EasyMock.replay(ui, gameEngine, turnManager);

    turnManager.numExtraCardsToDraw = extraCards;
    turnManager.doTargetedAttack();

    int actualNumExtraCardsToDraw = turnManager.numExtraCardsToDraw;
    int actualPlayerIndex = turnManager.currPlayerIndex;
    int expectedNumExtraCardsToDraw = extraCards + 1;

    assertEquals(expectedNumExtraCardsToDraw, actualNumExtraCardsToDraw);
    assertEquals(expectedPlayerIndex, actualPlayerIndex);

    EasyMock.verify(ui, gameEngine, turnManager);
  }

  @Test
  public void doTargetedAttack_validPlayerName_noExtraCardsToDraw_extraCardsToDrawIncrementedByOneAndPlayerIndexUpdated() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("printPlayers")
            .createMock();

    ui.printDoingTargetedAttack();

    String validPlayerName = "John";
    int extraCards = 0;
    int currPlayerIndex = 0;
    int expectedPlayerIndex = 2;

    turnManager.printPlayers();
    EasyMock.expectLastCall();

    EasyMock.expect(ui.promptTargetedAttack(false)).andReturn(validPlayerName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(validPlayerName)).andReturn(expectedPlayerIndex);

    turnManager.numExtraCardsToDraw = extraCards;
    turnManager.currPlayerIndex = currPlayerIndex;

    int expectedNumExtraCardsToDraw = extraCards + 1;
    ui.printTargetedAttackResult(expectedNumExtraCardsToDraw);

    EasyMock.replay(ui, gameEngine, turnManager);


    turnManager.doTargetedAttack();

    int actualNumExtraCardsToDraw = turnManager.numExtraCardsToDraw;
    int actualPlayerIndex = turnManager.currPlayerIndex;

    assertEquals(expectedNumExtraCardsToDraw, actualNumExtraCardsToDraw);
    assertEquals(expectedPlayerIndex, actualPlayerIndex);

    EasyMock.verify(ui, gameEngine, turnManager);
  }

  @Test
  public void doTargetedAttack_validPlayerName_sevenExtraCardsToDraw_extraCardsToDrawIncrementedByTwoAndPlayerIndexUpdated() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("printPlayers")
            .createMock();

    ui.printDoingTargetedAttack();

    String validPlayerName = "Jane";
    int extraCards = 7;
    int currPlayerIndex = 2;
    int expectedPlayerIndex = 4;

    turnManager.numExtraCardsToDraw = extraCards;
    turnManager.currPlayerIndex = currPlayerIndex;

    turnManager.printPlayers();
    EasyMock.expectLastCall();

    EasyMock.expect(ui.promptTargetedAttack(false)).andReturn(validPlayerName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(validPlayerName)).andReturn(expectedPlayerIndex);

    int expectedNumExtraCardsToDraw = 9;
    ui.printTargetedAttackResult(expectedNumExtraCardsToDraw);

    EasyMock.replay(ui, gameEngine, turnManager);

    turnManager.doTargetedAttack();

    int actualNumExtraCardsToDraw = turnManager.numExtraCardsToDraw;
    int actualPlayerIndex = turnManager.currPlayerIndex;

    assertEquals(expectedNumExtraCardsToDraw, actualNumExtraCardsToDraw);
    assertEquals(expectedPlayerIndex, actualPlayerIndex);

    EasyMock.verify(ui, gameEngine, turnManager);
  }

  @Test
  public void doGameLoop_gameIsOver() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("playCardLoop")
            .createMock();

    boolean isGameOver = true;
    String winnerName = "Player 1";

    EasyMock.expect(gameEngine.isGameOver()).andReturn(isGameOver);
    EasyMock.expect(gameEngine.getPlayerByIndex(EasyMock.anyInt())).andReturn(new Player(winnerName));
    ui.printWinner(winnerName);

    EasyMock.replay(ui, gameEngine, turnManager);

    turnManager.doGameLoop();

    EasyMock.verify(ui, gameEngine, turnManager);
  }

  @Test
  public void doGameLoop_gameIsNotOverThenIsOver() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("playCardLoop")
            .createMock();

    boolean isGameOver = false;
    String winnerName = "Player 2";

    EasyMock.expect(gameEngine.isGameOver()).andReturn(isGameOver);
    turnManager.playCardLoop();

    isGameOver = true;
    EasyMock.expect(gameEngine.isGameOver()).andReturn(isGameOver);
    EasyMock.expect(gameEngine.getPlayerByIndex(EasyMock.anyInt())).andReturn(new Player(winnerName));
    ui.printWinner(winnerName);


    EasyMock.replay(ui, gameEngine, turnManager);

    turnManager.doGameLoop();

    EasyMock.verify(ui, gameEngine, turnManager);
  }

  @Test
  public void setupGameEngine_6Players_validNames_setsUp () {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    int numPlayers = 6;
    EasyMock.expect(ui.getNumberOfPlayers()).andReturn(numPlayers);
    String[] playerNames = {"Jane", "John", "Foo", "Bar", "Alice", "Joe"};
    EasyMock.expect(ui.getPlayerNames(numPlayers)).andReturn(playerNames);

    gameEngine.setUpPlayers(numPlayers, playerNames);
    gameEngine.createDrawPile();
    gameEngine.dealDefuses();
    gameEngine.shuffleDrawPile();
    gameEngine.dealCards();
    gameEngine.insertExplodingAndImplodingCards();
    gameEngine.shuffleDrawPile();

    EasyMock.replay(ui, gameEngine);

    turnManager.setupGameEngine();

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void setupGameEngine_1Player_throwsException () {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    int numPlayers = 1;
    EasyMock.expect(ui.getNumberOfPlayers()).andReturn(numPlayers);

    EasyMock.replay(ui, gameEngine);

    String expectedMessage = "Invalid number of players.";
    Exception exception = assertThrows(IllegalArgumentException.class, turnManager::setupGameEngine);

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void setupGameEngine_3Players_2PlayerNames_throwsException () {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    int numPlayers = 3;
    EasyMock.expect(ui.getNumberOfPlayers()).andReturn(numPlayers);
    String[] playerNames = {"Bob", "Jeff"};
    EasyMock.expect(ui.getPlayerNames(numPlayers)).andReturn(playerNames);

    EasyMock.replay(ui, gameEngine);

    String expectedMessage = "Invalid number of player names.";
    Exception exception = assertThrows(IllegalArgumentException.class, turnManager::setupGameEngine);

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void setupGameEngine_7Player_throwsException () {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    int numPlayers = 7;
    EasyMock.expect(ui.getNumberOfPlayers()).andReturn(numPlayers);

    EasyMock.replay(ui, gameEngine);

    String expectedMessage = "Invalid number of players.";
    Exception exception = assertThrows(IllegalArgumentException.class, turnManager::setupGameEngine);

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void setupGameEngine_2Players_validNames_setsUp() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    int numPlayers = 2;
    EasyMock.expect(ui.getNumberOfPlayers()).andReturn(numPlayers);
    String[] playerNames = {"Jane", "John"};
    EasyMock.expect(ui.getPlayerNames(numPlayers)).andReturn(playerNames);

    gameEngine.setUpPlayers(numPlayers, playerNames);
    gameEngine.createDrawPile();
    gameEngine.dealDefuses();
    gameEngine.shuffleDrawPile();
    gameEngine.dealCards();
    gameEngine.insertExplodingAndImplodingCards();
    gameEngine.shuffleDrawPile();

    EasyMock.replay(ui, gameEngine);

    turnManager.setupGameEngine();

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void do2CardCombo_emptyTargetName_invalidCardName_targetHandSingleCard_repromptForInputAndModifyBothHands() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("printPlayerHand")
            .addMockedMethod("printPlayers")
            .createMock();

    ui.printDoingCardCombo(2);

    String invalidTargetName = "";
    String invalidCardName = "explode";
    String targetName = "John";
    String cardName = "attack";
    int targetIndex = 0;
    int currPlayerIndex = 1;
    Card targetCard = Card.ATTACK;

    Player targetPlayer = EasyMock.createMock(Player.class);
    Player currPlayer = EasyMock.createMock(Player.class);

    turnManager.printPlayers();
    EasyMock.expectLastCall().times(2);

    // Target name selection invalid on first attempt
    EasyMock.expect(ui.prompt2CardCombo(false)).andReturn(invalidTargetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(invalidTargetName)).andThrow(new NoSuchElementException("No player with that name could be found."));
    EasyMock.expect(ui.prompt2CardCombo(true)).andReturn(targetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(targetName)).andReturn(targetIndex);

    // Set expectations for the target's hand check
    Card[] targetHand = {Card.ATTACK};
    EasyMock.expect(gameEngine.getPlayerByIndex(targetIndex)).andReturn(targetPlayer);
    EasyMock.expect(targetPlayer.getHand()).andReturn(targetHand);

    // Target card selection invalid on first attempt
    turnManager.printPlayerHand(targetIndex);
    EasyMock.expectLastCall();
    EasyMock.expect(ui.prompt2CardComboTarget(false)).andReturn(invalidCardName);
    EasyMock.expect(gameEngine.getCardByName(invalidCardName)).andThrow(new IllegalArgumentException("Could not parse input."));
    turnManager.printPlayerHand(targetIndex);
    EasyMock.expectLastCall();
    EasyMock.expect(ui.prompt2CardComboTarget(true)).andReturn(cardName);
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(targetCard);

    EasyMock.expect(gameEngine.playerHasCard(targetCard, targetIndex)).andReturn(true);

    // Modify both hands
    gameEngine.removeCardFromPlayer(targetCard, targetIndex);
    EasyMock.expect(gameEngine.getPlayerByIndex(currPlayerIndex)).andReturn(currPlayer);
    currPlayer.addCardToHand(targetCard);

    // REPLAY
    EasyMock.replay(ui, gameEngine, targetPlayer, currPlayer, turnManager);

    turnManager.currPlayerIndex = currPlayerIndex;
    turnManager.do2CardCombo();

    EasyMock.verify(ui, gameEngine, targetPlayer, currPlayer, turnManager);
  }

  @Test
  public void do2CardCombo_invalidTargetName_validCardName_targetHandMultipleCards_repromptForInputAndModifyBothHands() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("printPlayerHand")
            .addMockedMethod("printPlayers")
            .createMock();

    ui.printDoingCardCombo(2);

    String invalidTargetName = "invalid";
    String targetName = "Jane";
    String cardName = "skip";
    int targetIndex = 2;
    int currPlayerIndex = 0;
    Card targetCard = Card.SKIP;

    Player targetPlayer = EasyMock.createMock(Player.class);
    Player currPlayer = EasyMock.createMock(Player.class);

    turnManager.printPlayers();
    EasyMock.expectLastCall().times(2);

    // Target name selection requires re-prompting
    EasyMock.expect(ui.prompt2CardCombo(false)).andReturn(invalidTargetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(invalidTargetName)).andThrow(new NoSuchElementException("No player with that name could be found."));
    EasyMock.expect(ui.prompt2CardCombo(true)).andReturn(targetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(targetName)).andReturn(targetIndex);

    // Set expectations for the target's hand check
    Card[] targetHand = {Card.SKIP, Card.SEE_THE_FUTURE};
    EasyMock.expect(gameEngine.getPlayerByIndex(targetIndex)).andReturn(targetPlayer);
    EasyMock.expect(targetPlayer.getHand()).andReturn(targetHand);

    turnManager.printPlayerHand(targetIndex);
    EasyMock.expectLastCall();

    // Target card selection good on first attempt
    EasyMock.expect(ui.prompt2CardComboTarget(false)).andReturn(cardName);
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(targetCard);

    EasyMock.expect(gameEngine.playerHasCard(targetCard, targetIndex)).andReturn(true);

    // Modify both hands
    gameEngine.removeCardFromPlayer(targetCard, targetIndex);
    EasyMock.expect(gameEngine.getPlayerByIndex(currPlayerIndex)).andReturn(currPlayer);
    currPlayer.addCardToHand(targetCard);

    // REPLAY
    EasyMock.replay(ui, gameEngine, targetPlayer, currPlayer, turnManager);

    turnManager.currPlayerIndex = currPlayerIndex;
    turnManager.do2CardCombo();

    EasyMock.verify(ui, gameEngine, targetPlayer, currPlayer, turnManager);
  }

  @Test
  public void do2CardCombo_validTargetName_emptyTargetHand_noEffectOnPlayerHands() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("printPlayers")
            .createMock();

    ui.printDoingCardCombo(2);

    String targetName = "John";

    int targetIndex = 3;

    Player targetPlayer = EasyMock.createMock(Player.class);

    turnManager.printPlayers();
    EasyMock.expectLastCall();

    // Target name selection valid on first attempt
    EasyMock.expect(ui.prompt2CardCombo(false)).andReturn(targetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(targetName)).andReturn(targetIndex);

    // Set expectations for the target's hand check
    Card[] targetHand = {};
    EasyMock.expect(gameEngine.getPlayerByIndex(targetIndex)).andReturn(targetPlayer);
    EasyMock.expect(targetPlayer.getHand()).andReturn(targetHand);
    ui.printCardComboErrorTargetPlayerHasNoCards();

    // REPLAY
    EasyMock.replay(ui, gameEngine, targetPlayer, turnManager);

    turnManager.do2CardCombo();

    EasyMock.verify(ui, gameEngine, targetPlayer, turnManager);
  }


  @Test
  public void do2CardCombo_validTargetName_validCardName_targetHandMaxCards_modifyBothPlayerHands() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("printPlayerHand")
            .addMockedMethod("printPlayers")
            .createMock();

    ui.printDoingCardCombo(2);

    String targetName = "Jane";
    String cardName = "shuffle";
    int targetIndex = 5;
    int currPlayerIndex = 4;
    Card targetCard = Card.SHUFFLE;

    Player targetPlayer = EasyMock.createMock(Player.class);
    Player currPlayer = EasyMock.createMock(Player.class);

    turnManager.printPlayers();
    EasyMock.expectLastCall();

    // Target name selection is valid on first attempt
    EasyMock.expect(ui.prompt2CardCombo(false)).andReturn(targetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(targetName)).andReturn(targetIndex);

    // Set expectations for the target's hand check
    Card[] targetHand = {Card.SKIP, Card.ATTACK, Card.ATTACK, Card.ATTACK, Card.TARGETED_ATTACK, Card.TARGETED_ATTACK, Card.TARGETED_ATTACK,
                        Card.SHUFFLE, Card.SHUFFLE, Card.SHUFFLE, Card.SHUFFLE,
                        Card.REVERSE, Card.REVERSE, Card.REVERSE, Card.REVERSE,
                        Card.DRAW_FROM_BOTTOM, Card.DRAW_FROM_BOTTOM, Card.DRAW_FROM_BOTTOM, Card.DRAW_FROM_BOTTOM,
                        Card.ALTER_THE_FUTURE, Card.ALTER_THE_FUTURE, Card.ALTER_THE_FUTURE, Card.ALTER_THE_FUTURE,
                        Card.SEE_THE_FUTURE, Card.SEE_THE_FUTURE, Card.SEE_THE_FUTURE, Card.SEE_THE_FUTURE,
                        Card.NOPE, Card.NOPE, Card.NOPE, Card.NOPE,
                        Card.TACO_CAT, Card.TACO_CAT, Card.TACO_CAT, Card.TACO_CAT,
                        Card.BEARD_CAT, Card.BEARD_CAT, Card.BEARD_CAT, Card.BEARD_CAT,
                        Card.RAINBOW_CAT, Card.RAINBOW_CAT, Card.RAINBOW_CAT, Card.RAINBOW_CAT,
                        Card.FERAL_CAT, Card.FERAL_CAT, Card.FERAL_CAT, Card.FERAL_CAT,
                        Card.HAIRY_POTATO_CAT, Card.HAIRY_POTATO_CAT, Card.HAIRY_POTATO_CAT, Card.HAIRY_POTATO_CAT};
    EasyMock.expect(gameEngine.getPlayerByIndex(targetIndex)).andReturn(targetPlayer);
    EasyMock.expect(targetPlayer.getHand()).andReturn(targetHand);

    // Target card selection is valid on first attempt
    turnManager.printPlayerHand(targetIndex);
    EasyMock.expectLastCall();
    EasyMock.expect(ui.prompt2CardComboTarget(false)).andReturn(cardName);
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(targetCard);

    EasyMock.expect(gameEngine.playerHasCard(targetCard, targetIndex)).andReturn(true);

    // Modify both hands
    gameEngine.removeCardFromPlayer(targetCard, targetIndex);
    EasyMock.expect(gameEngine.getPlayerByIndex(currPlayerIndex)).andReturn(currPlayer);
    currPlayer.addCardToHand(targetCard);

    // REPLAY
    EasyMock.replay(ui, gameEngine, targetPlayer, currPlayer, turnManager);

    turnManager.currPlayerIndex = currPlayerIndex;
    turnManager.do2CardCombo();

    EasyMock.verify(ui, gameEngine, targetPlayer, currPlayer, turnManager);
  }

  @Test
  public void do2CardCombo_validTargetName_targetHandMissingCardName_targetHandSingleCard_repromptForInputAndModifyBothHands() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("printPlayerHand")
            .addMockedMethod("printPlayers")
            .createMock();

    ui.printDoingCardCombo(2);

    String missingCardName = "exploding kitten";
    String currPlayerName = "John";
    String targetName = "Smith";
    String cardName = "defuse";
    int targetIndex = 1;
    int currPlayerIndex = 0;
    Card missingCard = Card.EXPLODE;
    Card targetCard = Card.DEFUSE;

    Player targetPlayer = EasyMock.createMock(Player.class);
    Player currPlayer = EasyMock.createMock(Player.class);

    turnManager.printPlayers();
    EasyMock.expectLastCall().times(2);

    // Target name selection invalid on first attempt
    EasyMock.expect(ui.prompt2CardCombo(false)).andReturn(currPlayerName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(currPlayerName)).andReturn(currPlayerIndex);
    EasyMock.expect(ui.prompt2CardCombo(true)).andReturn(targetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(targetName)).andReturn(targetIndex);

    // Set expectations for the target's hand check
    Card[] targetHand = {Card.DEFUSE};
    EasyMock.expect(gameEngine.getPlayerByIndex(targetIndex)).andReturn(targetPlayer);
    EasyMock.expect(targetPlayer.getHand()).andReturn(targetHand);

    // Target card selection valid on first attempt
    turnManager.printPlayerHand(targetIndex);
    EasyMock.expectLastCall();
    EasyMock.expect(ui.prompt2CardComboTarget(false)).andReturn(missingCardName);
    EasyMock.expect(gameEngine.getCardByName(missingCardName)).andReturn(missingCard);

    // Check that the target player doesn't have the card and re-prompt
    EasyMock.expect(gameEngine.playerHasCard(missingCard, targetIndex)).andReturn(false);
    turnManager.printPlayerHand(targetIndex);
    EasyMock.expectLastCall();
    EasyMock.expect(ui.prompt2CardComboTarget(true)).andReturn(cardName);
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(targetCard);

    // Now check that the target player has the target card
    EasyMock.expect(gameEngine.playerHasCard(targetCard, targetIndex)).andReturn(true);

    // Modify both hands
    gameEngine.removeCardFromPlayer(targetCard, targetIndex);
    EasyMock.expect(gameEngine.getPlayerByIndex(currPlayerIndex)).andReturn(currPlayer);
    currPlayer.addCardToHand(targetCard);

    // REPLAY
    EasyMock.replay(ui, gameEngine, targetPlayer, currPlayer, turnManager);

    turnManager.currPlayerIndex = currPlayerIndex;
    turnManager.do2CardCombo();

    EasyMock.verify(ui, gameEngine, targetPlayer, currPlayer, turnManager);
  }

  @Test
  public void do3CardCombo_emptyTargetName_invalidCardName_targetHandSingleCard_repromptForInputAndModifyBothHands() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("printPlayers")
            .createMock();

    ui.printDoingCardCombo(3);

    String invalidTargetName = "";
    String invalidCardName = "explode";
    String targetName = "John";
    String cardName = "attack";
    int targetIndex = 0;
    int currPlayerIndex = 1;
    Card targetCard = Card.ATTACK;

    Player targetPlayer = EasyMock.createMock(Player.class);
    Player currPlayer = EasyMock.createMock(Player.class);

    turnManager.printPlayers();
    EasyMock.expectLastCall().times(2);

    // Target name selection invalid on first attempt
    EasyMock.expect(ui.prompt3CardComboTargetName(false)).andReturn(invalidTargetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(invalidTargetName)).andThrow(new NoSuchElementException("No player with that name could be found."));
    EasyMock.expect(ui.prompt3CardComboTargetName(true)).andReturn(targetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(targetName)).andReturn(targetIndex);

    // Set expectations for the target's hand check
    Card[] targetHand = {Card.ATTACK};
    EasyMock.expect(gameEngine.getPlayerByIndex(targetIndex)).andReturn(targetPlayer);
    EasyMock.expect(targetPlayer.getHand()).andReturn(targetHand);

    // Target card selection invalid on first attempt
    EasyMock.expect(ui.prompt3CardComboTargetCard(false)).andReturn(invalidCardName);
    EasyMock.expect(gameEngine.getCardByName(invalidCardName)).andThrow(new IllegalArgumentException("Could not parse input."));
    EasyMock.expect(ui.prompt3CardComboTargetCard(true)).andReturn(cardName);
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(targetCard);

    EasyMock.expect(gameEngine.playerHasCard(targetCard, targetIndex)).andReturn(true);

    // Modify both hands
    gameEngine.removeCardFromPlayer(targetCard, targetIndex);
    EasyMock.expect(gameEngine.getPlayerByIndex(currPlayerIndex)).andReturn(currPlayer);
    currPlayer.addCardToHand(targetCard);

    // REPLAY
    EasyMock.replay(ui, gameEngine, targetPlayer, currPlayer, turnManager);

    turnManager.currPlayerIndex = currPlayerIndex;
    turnManager.do3CardCombo();

    EasyMock.verify(ui, gameEngine, targetPlayer, currPlayer, turnManager);
  }

  @Test
  public void do3CardCombo_invalidTargetName_validCardName_targetHandMultipleCards_repromptForInputAndModifyBothHands() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("printPlayers")
            .createMock();

    ui.printDoingCardCombo(3);

    String invalidTargetName = "invalid";
    String targetName = "Jane";
    String cardName = "skip";
    int targetIndex = 2;
    int currPlayerIndex = 0;
    Card targetCard = Card.SKIP;

    Player targetPlayer = EasyMock.createMock(Player.class);
    Player currPlayer = EasyMock.createMock(Player.class);

    turnManager.printPlayers();
    EasyMock.expectLastCall().times(2);

    // Target name selection requires re-prompting
    EasyMock.expect(ui.prompt3CardComboTargetName(false)).andReturn(invalidTargetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(invalidTargetName)).andThrow(new NoSuchElementException("No player with that name could be found."));
    EasyMock.expect(ui.prompt3CardComboTargetName(true)).andReturn(targetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(targetName)).andReturn(targetIndex);

    // Set expectations for the target's hand check
    Card[] targetHand = {Card.SKIP, Card.SEE_THE_FUTURE};
    EasyMock.expect(gameEngine.getPlayerByIndex(targetIndex)).andReturn(targetPlayer);
    EasyMock.expect(targetPlayer.getHand()).andReturn(targetHand);

    // Target card selection good on first attempt
    EasyMock.expect(ui.prompt3CardComboTargetCard(false)).andReturn(cardName);
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(targetCard);

    EasyMock.expect(gameEngine.playerHasCard(targetCard, targetIndex)).andReturn(true);

    // Modify both hands
    gameEngine.removeCardFromPlayer(targetCard, targetIndex);
    EasyMock.expect(gameEngine.getPlayerByIndex(currPlayerIndex)).andReturn(currPlayer);
    currPlayer.addCardToHand(targetCard);

    // REPLAY
    EasyMock.replay(ui, gameEngine, targetPlayer, currPlayer, turnManager);

    turnManager.currPlayerIndex = currPlayerIndex;
    turnManager.do3CardCombo();

    EasyMock.verify(ui, gameEngine, targetPlayer, currPlayer, turnManager);
  }

  @Test
  public void do3CardCombo_validTargetName_emptyTargetHand_noEffectOnPlayerHands() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("printPlayers")
            .createMock();

    ui.printDoingCardCombo(3);

    String targetName = "John";

    int targetIndex = 3;

    Player targetPlayer = EasyMock.createMock(Player.class);

    turnManager.printPlayers();
    EasyMock.expectLastCall();

    // Target name selection valid on first attempt
    EasyMock.expect(ui.prompt3CardComboTargetName(false)).andReturn(targetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(targetName)).andReturn(targetIndex);

    // Set expectations for the target's hand check
    Card[] targetHand = {};
    EasyMock.expect(gameEngine.getPlayerByIndex(targetIndex)).andReturn(targetPlayer);
    EasyMock.expect(targetPlayer.getHand()).andReturn(targetHand);
    ui.printCardComboErrorTargetPlayerHasNoCards();

    // REPLAY
    EasyMock.replay(ui, gameEngine, targetPlayer, turnManager);

    turnManager.do3CardCombo();

    EasyMock.verify(ui, gameEngine, targetPlayer, turnManager);
  }

  @Test
  public void do3CardCombo_validTargetName_validCardName_targetHandMaxCards_modifyBothPlayerHands() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("printPlayers")
            .createMock();

    ui.printDoingCardCombo(3);

    String targetName = "Jane";
    String cardName = "shuffle";
    int targetIndex = 5;
    int currPlayerIndex = 4;
    Card targetCard = Card.SHUFFLE;

    Player targetPlayer = EasyMock.createMock(Player.class);
    Player currPlayer = EasyMock.createMock(Player.class);

    turnManager.printPlayers();
    EasyMock.expectLastCall();

    // Target name selection is valid on first attempt
    EasyMock.expect(ui.prompt3CardComboTargetName(false)).andReturn(targetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(targetName)).andReturn(targetIndex);

    // Set expectations for the target's hand check
    Card[] targetHand = {Card.SKIP, Card.ATTACK, Card.ATTACK, Card.ATTACK, Card.TARGETED_ATTACK, Card.TARGETED_ATTACK, Card.TARGETED_ATTACK,
            Card.SHUFFLE, Card.SHUFFLE, Card.SHUFFLE, Card.SHUFFLE,
            Card.REVERSE, Card.REVERSE, Card.REVERSE, Card.REVERSE,
            Card.DRAW_FROM_BOTTOM, Card.DRAW_FROM_BOTTOM, Card.DRAW_FROM_BOTTOM, Card.DRAW_FROM_BOTTOM,
            Card.ALTER_THE_FUTURE, Card.ALTER_THE_FUTURE, Card.ALTER_THE_FUTURE, Card.ALTER_THE_FUTURE,
            Card.SEE_THE_FUTURE, Card.SEE_THE_FUTURE, Card.SEE_THE_FUTURE, Card.SEE_THE_FUTURE,
            Card.NOPE, Card.NOPE, Card.NOPE, Card.NOPE,
            Card.TACO_CAT, Card.TACO_CAT, Card.TACO_CAT, Card.TACO_CAT,
            Card.BEARD_CAT, Card.BEARD_CAT, Card.BEARD_CAT, Card.BEARD_CAT,
            Card.RAINBOW_CAT, Card.RAINBOW_CAT, Card.RAINBOW_CAT, Card.RAINBOW_CAT,
            Card.FERAL_CAT, Card.FERAL_CAT, Card.FERAL_CAT, Card.FERAL_CAT,
            Card.HAIRY_POTATO_CAT, Card.HAIRY_POTATO_CAT, Card.HAIRY_POTATO_CAT, Card.HAIRY_POTATO_CAT};
    EasyMock.expect(gameEngine.getPlayerByIndex(targetIndex)).andReturn(targetPlayer);
    EasyMock.expect(targetPlayer.getHand()).andReturn(targetHand);

    // Target card selection is valid on first attempt
    EasyMock.expect(ui.prompt3CardComboTargetCard(false)).andReturn(cardName);
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(targetCard);

    EasyMock.expect(gameEngine.playerHasCard(targetCard, targetIndex)).andReturn(true);

    // Modify both hands
    gameEngine.removeCardFromPlayer(targetCard, targetIndex);
    EasyMock.expect(gameEngine.getPlayerByIndex(currPlayerIndex)).andReturn(currPlayer);
    currPlayer.addCardToHand(targetCard);

    // REPLAY
    EasyMock.replay(ui, gameEngine, targetPlayer, currPlayer, turnManager);

    turnManager.currPlayerIndex = currPlayerIndex;
    turnManager.do3CardCombo();

    EasyMock.verify(ui, gameEngine, targetPlayer, currPlayer, turnManager);
  }

  @Test
  public void do3CardCombo_validTargetName_targetHandMissingCardName_targetHandSingleCard_noModificationAndReturn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("printPlayers")
            .createMock();

    ui.printDoingCardCombo(3);

    String missingCardName = "exploding kitten";
    String currName = "John";
    String targetName = "Smith";
    int currIndex = 0;
    int targetIndex = 1;
    Card missingCard = Card.EXPLODE;

    Player targetPlayer = EasyMock.createMock(Player.class);
    Player currPlayer = EasyMock.createMock(Player.class);

    turnManager.printPlayers();
    EasyMock.expectLastCall().times(2);

    // Target name selection valid on first attempt
    EasyMock.expect(ui.prompt3CardComboTargetName(false)).andReturn(currName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(currName)).andReturn(currIndex);
    EasyMock.expect(ui.prompt3CardComboTargetName(true)).andReturn(targetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(targetName)).andReturn(targetIndex);

    // Set expectations for the target's hand check
    Card[] targetHand = {Card.DEFUSE};
    EasyMock.expect(gameEngine.getPlayerByIndex(targetIndex)).andReturn(targetPlayer);
    EasyMock.expect(targetPlayer.getHand()).andReturn(targetHand);

    // Target card selection valid on first attempt
    EasyMock.expect(ui.prompt3CardComboTargetCard(false)).andReturn(missingCardName);
    EasyMock.expect(gameEngine.getCardByName(missingCardName)).andReturn(missingCard);

    // Check that the target player doesn't have the card and return
    EasyMock.expect(gameEngine.playerHasCard(missingCard, targetIndex)).andReturn(false);

    // REPLAY
    EasyMock.replay(ui, gameEngine, targetPlayer, currPlayer, turnManager);

    turnManager.do3CardCombo();

    EasyMock.verify(ui, gameEngine, targetPlayer, currPlayer, turnManager);
  }

  @Test
  public void printPlayerHand_minIndex_multiplePlayers_printsPlayerHand() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    int playerIndex = 0;

    Player john = EasyMock.createMock(Player.class);
    Player jane = EasyMock.createMock(Player.class);
    Card[] hand = {Card.ATTACK, Card.SKIP, Card.SHUFFLE};

    // Get all players in GameEngine, if players are less than index then expect exception
    List<Player> players = new ArrayList<>();
    players.add(john);
    players.add(jane);
    EasyMock.expect(gameEngine.getPlayers()).andReturn(players);

    EasyMock.expect(gameEngine.getPlayerByIndex(playerIndex)).andReturn(john);
    EasyMock.expect(john.getHand()).andReturn(hand);

    // Turn hand from Card[] to String[]
    String[] handString = new String[hand.length];
    for (int i = 0; i < hand.length; i++) {
      handString[i] = hand[i].toString();
    }

    ui.printPlayerHand(handString);

    EasyMock.replay(gameEngine, ui, john);

    turnManager.printPlayerHand(playerIndex);

    EasyMock.verify(gameEngine, ui, john);
  }

  @Test
  public void printPlayerHand_maxIndex_maxPlayers_printsPlayerHand() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    int playerIndex = 5;

    Player john = EasyMock.createMock(Player.class);
    Player jane = EasyMock.createMock(Player.class);
    Player brennan = EasyMock.createMock(Player.class);
    Player foo = EasyMock.createMock(Player.class);
    Player bar = EasyMock.createMock(Player.class);
    Player baz = EasyMock.createMock(Player.class);
    Card[] hand = {Card.ATTACK, Card.SKIP, Card.SHUFFLE};

    // Get all players in GameEngine, if players are less than index then expect exception
    List<Player> players = new ArrayList<>();
    players.add(john);
    players.add(jane);
    players.add(brennan);
    players.add(foo);
    players.add(bar);
    players.add(baz);
    EasyMock.expect(gameEngine.getPlayers()).andReturn(players);

    EasyMock.expect(gameEngine.getPlayerByIndex(playerIndex)).andReturn(baz);
    EasyMock.expect(baz.getHand()).andReturn(hand);

    // Turn hand from Card[] to String[]
    String[] handString = new String[hand.length];
    for (int i = 0; i < hand.length; i++) {
      handString[i] = hand[i].toString();
    }

    ui.printPlayerHand(handString);

    EasyMock.replay(gameEngine, ui, baz);

    turnManager.printPlayerHand(playerIndex);

    EasyMock.verify(gameEngine, ui, baz);
  }

  @Test
  public void printPlayerHand_invalidIndex_maxPlayers_throwsException() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    int playerIndex = 6;

    Player john = EasyMock.createMock(Player.class);
    Player jane = EasyMock.createMock(Player.class);
    Player brennan = EasyMock.createMock(Player.class);
    Player foo = EasyMock.createMock(Player.class);
    Player bar = EasyMock.createMock(Player.class);
    Player baz = EasyMock.createMock(Player.class);

    // Get all players in GameEngine, if players are less than index then expect exception
    List<Player> players = new ArrayList<>();
    players.add(john);
    players.add(jane);
    players.add(brennan);
    players.add(foo);
    players.add(bar);
    players.add(baz);
    EasyMock.expect(gameEngine.getPlayers()).andReturn(players);

    EasyMock.replay(gameEngine);

    String expectedMessage = "Player index is out of bounds.";
    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      turnManager.printPlayerHand(playerIndex);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(gameEngine);
  }

  @Test
  public void printPlayerHand_invalidIndexNegativeOne_maxPlayers_throwsException() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    int playerIndex = -1;

    EasyMock.replay(gameEngine);

    String expectedMessage = "Player index is out of bounds.";
    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      turnManager.printPlayerHand(playerIndex);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(gameEngine);
  }

  @Test
  public void printPlayers_maxPlayers_printSuccessful() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    // Mock players
    Player john = EasyMock.createMock(Player.class);
    Player jane = EasyMock.createMock(Player.class);
    Player brennan = EasyMock.createMock(Player.class);
    Player foo = EasyMock.createMock(Player.class);
    Player bar = EasyMock.createMock(Player.class);
    Player baz = EasyMock.createMock(Player.class);

    // Add players to the GameEngine mock
    List<Player> players = new ArrayList<>(Arrays.asList(john, jane, brennan, foo, bar, baz));
    EasyMock.expect(gameEngine.getPlayers()).andReturn(players);

    // Mock player names
    EasyMock.expect(john.getName()).andReturn("John");
    EasyMock.expect(jane.getName()).andReturn("Jane");
    EasyMock.expect(brennan.getName()).andReturn("Brennan");
    EasyMock.expect(foo.getName()).andReturn("Foo");
    EasyMock.expect(bar.getName()).andReturn("Bar");
    EasyMock.expect(baz.getName()).andReturn("Baz");

    // Define expected behavior for UserInterface
    ui.printPlayers(new String[] {"John", "Jane", "Brennan", "Foo", "Bar", "Baz"});
    EasyMock.expectLastCall(); // This ensures the method gets called with the expected array

    // Replay mocks
    EasyMock.replay(gameEngine, ui, john, jane, brennan, foo, bar, baz);

    // Execute the method under test
    turnManager.printPlayers();

    // Verify that expectations were met
    EasyMock.verify(gameEngine, ui, john, jane, brennan, foo, bar, baz);
  }

  @Test
  public void printPlayers_multiplePlayers_printSuccessful() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    // Mock players
    Player john = EasyMock.createMock(Player.class);
    Player jane = EasyMock.createMock(Player.class);

    // Add players to the GameEngine mock
    List<Player> players = new ArrayList<>(Arrays.asList(john, jane));
    EasyMock.expect(gameEngine.getPlayers()).andReturn(players);

    // Mock player names
    EasyMock.expect(john.getName()).andReturn("John");
    EasyMock.expect(jane.getName()).andReturn("Jane");


    // Define expected behavior for UserInterface
    ui.printPlayers(new String[] {"John", "Jane"});
    EasyMock.expectLastCall(); // This ensures the method gets called with the expected array

    // Replay mocks
    EasyMock.replay(gameEngine, ui, john, jane);

    // Execute the method under test
    turnManager.printPlayers();

    // Verify that expectations were met
    EasyMock.verify(gameEngine, ui, john, jane);
  }

  @Test
  public void eliminateCurrentPlayer_NegOne_ThrowsException() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .createMock();


    // Class state set up
    turnManager.currPlayerIndex = -1;

    // Test Input
    int playerIndex = -1;

    // Expected Values
    String expectedMessage = "Player does not exist at this index";

    // Expectations
    gameEngine.eliminatePlayer(playerIndex);
    EasyMock.expectLastCall().andThrow(new IndexOutOfBoundsException(expectedMessage));

    EasyMock.replay(gameEngine, turnManager);

    // Test
    Exception exception = assertThrows(IndexOutOfBoundsException.class, turnManager::eliminateCurrentPlayer);

    // Actual Values
    String actualMessage = exception.getMessage();

    // Assertion
    assertEquals(expectedMessage, actualMessage);

    // Verification
    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void eliminateCurrentPlayer_Zero_EliminatesPlayerAndAdvancesTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .createMock();

    // Class state set up
    turnManager.currPlayerIndex = 0;

    // Test Input
    int playerIndex = 0;
    String playerName = "name";
    Player player = EasyMock.createMock(Player.class);
    EasyMock.expect(player.getName()).andReturn(playerName);

    // Expectations
    gameEngine.eliminatePlayer(playerIndex);
    turnManager.advanceTurn(false);

    ui.printPlayerEliminated();

    EasyMock.replay(gameEngine, turnManager, ui);

    // Test
    turnManager.eliminateCurrentPlayer();

    // Verification
    EasyMock.verify(gameEngine, turnManager, ui);
  }

  @Test
  public void eliminateCurrentPlayer_Five_EliminatesPlayerAndAdvancesTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .createMock();

    // Class state set up
    turnManager.currPlayerIndex = 5;

    // Test Input
    int playerIndex = 5;

    // Expectations
    gameEngine.eliminatePlayer(playerIndex);
    turnManager.advanceTurn(false);
    ui.printPlayerEliminated();

    EasyMock.replay(gameEngine, turnManager, ui);

    // Test
    turnManager.eliminateCurrentPlayer();

    // Verification
    EasyMock.verify(gameEngine, turnManager, ui);
  }

  @Test
  public void eliminateCurrentPlayer_Six_ThrowsException() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .createMock();

    // Class state set up
    turnManager.currPlayerIndex = 6;

    // Test Input
    int playerIndex = 6;

    // Expected Values
    String expectedMessage = "Player does not exist at this index";

    // Expectations
    gameEngine.eliminatePlayer(playerIndex);
    EasyMock.expectLastCall().andThrow(new IndexOutOfBoundsException(expectedMessage));

    EasyMock.replay(gameEngine, turnManager);

    // Test
    Exception exception = assertThrows(IndexOutOfBoundsException.class, turnManager::eliminateCurrentPlayer);

    // Actual Values
    String actualMessage = exception.getMessage();

    // Assertion
    assertEquals(expectedMessage, actualMessage);

    // Verification
    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void printTurnInfo_2Players_notReversed_implodingNotFaceUp_peekEmpty() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Player joe = EasyMock.createMock(Player.class);
    int currPlayerIndex = 0;
    turnManager.currPlayerIndex = currPlayerIndex;
    EasyMock.expect(gameEngine.getPlayerByIndex(currPlayerIndex)).andReturn(joe);

    String joeName = "Joe";
    EasyMock.expect(joe.getName()).andReturn(joeName).anyTimes();

    String bobName = "Bob";
    Player bob = EasyMock.createMock(Player.class);
    EasyMock.expect(bob.getName()).andReturn(bobName).anyTimes();

    List<Player> playerList = List.of((new Player[]{joe, bob}));
    EasyMock.expect(gameEngine.getPlayers()).andReturn(playerList);

    int numExtraCardsToDraw = 0;
    turnManager.numExtraCardsToDraw = numExtraCardsToDraw;

    boolean isImplodingCatFaceUp = false;
    turnManager.isImplodingCatFaceUp = isImplodingCatFaceUp;

    boolean isTurnOrderReversed = false;
    EasyMock.expect(gameEngine.getIsTurnOrderReversed()).andReturn(isTurnOrderReversed);

    String[] playerNames = { joeName, bobName };
    ui.printGameState(joeName, playerNames, numExtraCardsToDraw, isTurnOrderReversed, isImplodingCatFaceUp);

    EasyMock.replay(gameEngine, ui, joe, bob);

    turnManager.printTurnInfo();

    EasyMock.verify(gameEngine, ui, joe, bob);
  }

  @Test
  public void printTurnInfo_3Players_isReversed_implodingFaceUp_peekNonImplodingTopCard() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Player joe = EasyMock.createMock(Player.class);
    int currPlayerIndex = 0;
    turnManager.currPlayerIndex = currPlayerIndex;
    EasyMock.expect(gameEngine.getPlayerByIndex(currPlayerIndex)).andReturn(joe);

    String joeName = "Joe";
    EasyMock.expect(joe.getName()).andReturn(joeName).anyTimes();

    String bobName = "Bob";
    Player bob = EasyMock.createMock(Player.class);
    EasyMock.expect(bob.getName()).andReturn(bobName).anyTimes();

    String jeffName = "Jeff";
    Player jeff = EasyMock.createMock(Player.class);
    EasyMock.expect(jeff.getName()).andReturn(jeffName).anyTimes();

    List<Player> playerList = List.of((new Player[]{joe, bob, jeff}));
    EasyMock.expect(gameEngine.getPlayers()).andReturn(playerList);

    int numExtraCardsToDraw = 1;
    turnManager.numExtraCardsToDraw = numExtraCardsToDraw;

    turnManager.isImplodingCatFaceUp = true;

    boolean isTurnOrderReversed = true;
    EasyMock.expect(gameEngine.getIsTurnOrderReversed()).andReturn(isTurnOrderReversed);

    Card[] topCards = { Card.ATTACK };
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(topCards);
    boolean printImplodingIsNext = false;

    String[] playerNames = { joeName, bobName, jeffName };
    ui.printGameState(joeName, playerNames, numExtraCardsToDraw, isTurnOrderReversed, printImplodingIsNext);

    EasyMock.replay(gameEngine, ui, joe, bob, jeff);

    turnManager.printTurnInfo();

    EasyMock.verify(gameEngine, ui, joe, bob, jeff);
  }

  @Test
  public void printTurnInfo_noPlayers_isReversed_implodingFaceUp_peekDrawPileNoCards() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String emptyPlayerName = "";
    Player emptyPlayer = EasyMock.createMock(Player.class);
    int currPlayerIndex = 0;
    turnManager.currPlayerIndex = currPlayerIndex;
    EasyMock.expect(gameEngine.getPlayerByIndex(currPlayerIndex)).andReturn(emptyPlayer);
    EasyMock.expect(emptyPlayer.getName()).andReturn(emptyPlayerName);

    List<Player> playerList = new ArrayList<>();
    EasyMock.expect(gameEngine.getPlayers()).andReturn(playerList);

    int numExtraCardsToDraw = 1;
    turnManager.numExtraCardsToDraw = numExtraCardsToDraw;

    turnManager.isImplodingCatFaceUp = true;

    boolean isTurnOrderReversed = true;
    EasyMock.expect(gameEngine.getIsTurnOrderReversed()).andReturn(isTurnOrderReversed);

    Card[] topCards = new Card[0];
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(topCards);
    boolean printImplodingIsNext = false;

    String[] playerNames = new String[0];
    ui.printGameState(emptyPlayerName, playerNames, numExtraCardsToDraw, isTurnOrderReversed, printImplodingIsNext);

    EasyMock.replay(gameEngine, ui, emptyPlayer);

    turnManager.printTurnInfo();

    EasyMock.verify(gameEngine, ui, emptyPlayer);
  }

  @Test
  public void printTurnInfo_4Players_isNotReversed_implodingFaceUp_peekDrawPileTopNotImplode() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Player jane = EasyMock.createMock(Player.class);
    String janeName = "Jane";

    int currPlayerIndex = 0;
    turnManager.currPlayerIndex = currPlayerIndex;
    EasyMock.expect(gameEngine.getPlayerByIndex(currPlayerIndex)).andReturn(jane);
    EasyMock.expect(jane.getName()).andReturn(janeName).anyTimes();

    String joeName = "Joe";
    Player joe = EasyMock.createMock(Player.class);
    EasyMock.expect(joe.getName()).andReturn(joeName).anyTimes();

    String bobName = "Bob";
    Player bob = EasyMock.createMock(Player.class);
    EasyMock.expect(bob.getName()).andReturn(bobName).anyTimes();

    String jeffName = "Jeff";
    Player jeff = EasyMock.createMock(Player.class);
    EasyMock.expect(jeff.getName()).andReturn(jeffName).anyTimes();

    List<Player> playerList = List.of((new Player[]{joe, bob, jeff, jane}));
    EasyMock.expect(gameEngine.getPlayers()).andReturn(playerList);

    int numExtraCardsToDraw = 3;
    turnManager.numExtraCardsToDraw = numExtraCardsToDraw;

    turnManager.isImplodingCatFaceUp = true;

    boolean isTurnOrderReversed = false;
    EasyMock.expect(gameEngine.getIsTurnOrderReversed()).andReturn(isTurnOrderReversed);

    Card[] topCards = { Card.EXPLODE, Card.IMPLODE, Card.SKIP };
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(topCards);
    boolean printImplodingIsNext = false;

    String[] playerNames = { joeName, bobName, jeffName, janeName };
    ui.printGameState(janeName, playerNames, numExtraCardsToDraw, isTurnOrderReversed, printImplodingIsNext);

    EasyMock.replay(gameEngine, ui, jane, joe, jeff, bob);

    turnManager.printTurnInfo();

    EasyMock.verify(gameEngine, ui, jane, joe, jeff, bob);
  }

  @Test
  public void printTurnInfo_4Players_isNotReversed_implodingFaceUp_peekDrawPileIs2CardsTopImplode() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Player jane = EasyMock.createMock(Player.class);
    String janeName = "Jane";

    int currPlayerIndex = 0;
    turnManager.currPlayerIndex = currPlayerIndex;
    EasyMock.expect(gameEngine.getPlayerByIndex(currPlayerIndex)).andReturn(jane);
    EasyMock.expect(jane.getName()).andReturn(janeName).anyTimes();

    String joeName = "Joe";
    Player joe = EasyMock.createMock(Player.class);
    EasyMock.expect(joe.getName()).andReturn(joeName).anyTimes();

    String bobName = "Bob";
    Player bob = EasyMock.createMock(Player.class);
    EasyMock.expect(bob.getName()).andReturn(bobName).anyTimes();

    String jeffName = "Jeff";
    Player jeff = EasyMock.createMock(Player.class);
    EasyMock.expect(jeff.getName()).andReturn(jeffName).anyTimes();

    List<Player> playerList = List.of((new Player[]{joe, bob, jeff, jane}));
    EasyMock.expect(gameEngine.getPlayers()).andReturn(playerList);

    int numExtraCardsToDraw = 0;
    turnManager.numExtraCardsToDraw = numExtraCardsToDraw;

    turnManager.isImplodingCatFaceUp = true;

    boolean isTurnOrderReversed = false;
    EasyMock.expect(gameEngine.getIsTurnOrderReversed()).andReturn(isTurnOrderReversed);

    Card[] topCards = { Card.IMPLODE, Card.SKIP };
    EasyMock.expect(gameEngine.peekDrawPile()).andReturn(topCards);
    boolean printImplodingIsNext = true;

    String[] playerNames = { joeName, bobName, jeffName, janeName };
    ui.printGameState(janeName, playerNames, numExtraCardsToDraw, isTurnOrderReversed, printImplodingIsNext);

    EasyMock.replay(gameEngine, ui, jane, joe, jeff, bob);

    turnManager.printTurnInfo();

    EasyMock.verify(gameEngine, ui, jane, joe, jeff, bob);
  }

  @Test
  public void turnManagerConstructorTest() {
    String language = "spanish";
    TurnManager turnManager = new TurnManager(language);
    assertNotNull(turnManager);
  }
}



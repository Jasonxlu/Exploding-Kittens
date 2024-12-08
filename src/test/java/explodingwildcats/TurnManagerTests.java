package explodingwildcats;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ui.UserInterface;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
  public void doShuffle_singleCardInDrawPile_shuffleDrawPileCalled() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .createMock();

    // One card in the draw pile
    Card[] drawPile = new Card[] { Card.DEFUSE };
    EasyMock.expect(gameEngine.getDrawPile()).andReturn(drawPile);

    gameEngine.shuffleDrawPile();
    turnManager.endTurn();

    EasyMock.replay(turnManager, gameEngine);

    turnManager.doShuffle();

    // Verify that there is one card in the draw pile
    Card[] actualDrawPile = gameEngine.getDrawPile();
    assertEquals(drawPile.length, actualDrawPile.length);

    EasyMock.verify(turnManager, gameEngine);
  }

  @Test
  public void doShuffle_multipleCardsInDrawPile_shuffleDrawPileCalled() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .createMock();


    Card[] drawPile = new Card[] { Card.ATTACK, Card.REVERSE, Card.NOPE };
    EasyMock.expect(gameEngine.getDrawPile()).andReturn(drawPile);

    gameEngine.shuffleDrawPile();
    turnManager.endTurn();

    EasyMock.replay(turnManager, gameEngine);

    turnManager.doShuffle();

    // Verify that there is one card in the draw pile
    Card[] actualDrawPile = gameEngine.getDrawPile();
    assertEquals(drawPile.length, actualDrawPile.length);

    EasyMock.verify(turnManager, gameEngine);
  }

  @Test
  public void doShuffle_maxCardsInDrawPile_shuffleDrawPileCalled() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .createMock();


    Card[] drawPile = new Card[] { Card.SKIP, Card.ATTACK, Card.TARGETED_ATTACK, Card.SKIP, Card.ATTACK, Card.TARGETED_ATTACK,
            Card.SKIP, Card.ATTACK, Card.TARGETED_ATTACK, Card.SHUFFLE, Card.SEE_THE_FUTURE, Card.NOPE, Card.REVERSE, Card.DRAW_FROM_BOTTOM,
            Card.ALTER_THE_FUTURE, Card.TACO_CAT, Card.HAIRY_POTATO_CAT, Card.BEARD_CAT, Card.RAINBOW_CAT, Card.FERAL_CAT, Card.SHUFFLE, Card.SEE_THE_FUTURE, Card.NOPE, Card.REVERSE, Card.DRAW_FROM_BOTTOM,
            Card.ALTER_THE_FUTURE, Card.TACO_CAT, Card.HAIRY_POTATO_CAT, Card.BEARD_CAT, Card.RAINBOW_CAT, Card.FERAL_CAT,Card.SHUFFLE, Card.SEE_THE_FUTURE, Card.NOPE, Card.REVERSE, Card.DRAW_FROM_BOTTOM,
            Card.ALTER_THE_FUTURE, Card.TACO_CAT, Card.HAIRY_POTATO_CAT, Card.BEARD_CAT, Card.RAINBOW_CAT, Card.FERAL_CAT,Card.SHUFFLE, Card.SEE_THE_FUTURE, Card.NOPE, Card.REVERSE, Card.DRAW_FROM_BOTTOM,
            Card.ALTER_THE_FUTURE, Card.TACO_CAT, Card.HAIRY_POTATO_CAT, Card.BEARD_CAT, Card.RAINBOW_CAT, Card.FERAL_CAT };
    EasyMock.expect(gameEngine.getDrawPile()).andReturn(drawPile);

    gameEngine.shuffleDrawPile();
    turnManager.endTurn();

    EasyMock.replay(turnManager, gameEngine);

    turnManager.doShuffle();

    // Verify that there is one card in the draw pile
    Card[] actualDrawPile = gameEngine.getDrawPile();
    assertEquals(drawPile.length, actualDrawPile.length);

    EasyMock.verify(turnManager, gameEngine);
  }


  @ParameterizedTest
  @CsvSource({
          "6, 0", "6, 5", "6, 3",
          "2, 0", "2, 1",
          "4, 0", "4, 3", "4, 2",
  })
  public void advanceTurn_ReversedOrderFalse(int numOfPlayers, int currPlayerIndex) {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    EasyMock.expect(gameEngine.getIsTurnOrderReversed()).andReturn(false).anyTimes();
    EasyMock.expect(gameEngine.getNumberOfPlayers()).andReturn(numOfPlayers).anyTimes();
    EasyMock.replay(gameEngine);

    turnManager.numExtraCardsToDraw = 0;
    turnManager.currPlayerIndex = currPlayerIndex;

    turnManager.advanceTurn();

    int expected = (currPlayerIndex + 1) % numOfPlayers;
    int actual = turnManager.currPlayerIndex;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine);
  }

  @ParameterizedTest
  @CsvSource({
          "6, 0", "6, 5", "6, 3",
          "2, 0", "2, 1",
          "4, 0", "4, 3", "4, 2",
  })
  public void advanceTurn_ReversedOrderTrue(int numOfPlayers, int currPlayerIndex) {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    EasyMock.expect(gameEngine.getIsTurnOrderReversed()).andReturn(true).anyTimes();
    EasyMock.expect(gameEngine.getNumberOfPlayers()).andReturn(numOfPlayers).anyTimes();
    EasyMock.replay(gameEngine);

    turnManager.numExtraCardsToDraw = 0;
    turnManager.currPlayerIndex = currPlayerIndex;

    turnManager.advanceTurn();

    int expected = (currPlayerIndex - 1 + numOfPlayers) % numOfPlayers;
    int actual = turnManager.currPlayerIndex;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine);
  }

  @Test
  public void drawAndProcessCard_regularCardFromTop_callsHandleRegularCard() {
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

    EasyMock.replay(gameEngine, turnManager);

    turnManager.drawAndProcessCard(false);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void drawAndProcessCard_regularCardFromBottom_callsHandleRegularCard() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("handleRegularCard")
            .addMockedMethod("handleExplodingKitten")
            .addMockedMethod("handleImplodingCat")
            .createMock();

    Card regularCard = Card.SKIP;

    EasyMock.expect(gameEngine.popBottomCard()).andReturn(regularCard);
    turnManager.handleRegularCard(regularCard);

    EasyMock.replay(gameEngine, turnManager);

    turnManager.drawAndProcessCard(true);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void drawAndProcessCard_explodeCardFromTop_callsHandleExplodingKitten() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("handleRegularCard")
            .addMockedMethod("handleExplodingKitten")
            .addMockedMethod("handleImplodingCat")
            .createMock();

    Card explodingCard = Card.EXPLODE;

    EasyMock.expect(gameEngine.popTopCard()).andReturn(explodingCard);
    turnManager.handleExplodingKitten();

    EasyMock.replay(gameEngine, turnManager);

    turnManager.drawAndProcessCard(false);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void drawAndProcessCard_explodeCardFromBottom_callsHandleExplodingKitten() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("handleRegularCard")
            .addMockedMethod("handleExplodingKitten")
            .addMockedMethod("handleImplodingCat")
            .createMock();

    Card explodingCard = Card.EXPLODE;

    EasyMock.expect(gameEngine.popBottomCard()).andReturn(explodingCard);
    turnManager.handleExplodingKitten();

    EasyMock.replay(gameEngine, turnManager);

    turnManager.drawAndProcessCard(true);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void drawAndProcessCard_implodeCardFromTop_callsHandleImplodingCat() {
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
    turnManager.handleImplodingCat();

    EasyMock.replay(gameEngine, turnManager);

    turnManager.drawAndProcessCard(false);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void drawAndProcessCard_implodeCardFromBottom_callsHandleImplodingCat() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("handleRegularCard")
            .addMockedMethod("handleExplodingKitten")
            .addMockedMethod("handleImplodingCat")
            .createMock();

    Card implodingCard = Card.IMPLODE;

    EasyMock.expect(gameEngine.popBottomCard()).andReturn(implodingCard);
    turnManager.handleImplodingCat();

    EasyMock.replay(gameEngine, turnManager);

    turnManager.drawAndProcessCard(true);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void handleRegularCard_addsCardToPlayerHand() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .createMock();

    Player player = EasyMock.createMock(Player.class);
    Card card = Card.SKIP;
    List<Player> players = List.of(player);

    EasyMock.expect(gameEngine.getPlayers()).andReturn(players);
    player.addCardToHand(card);
    turnManager.endTurn();

    EasyMock.replay(gameEngine, player, ui, turnManager);

    turnManager.handleRegularCard(card);

    EasyMock.verify(gameEngine, player, ui, turnManager);
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
  public void endTurn_drawCounterZero_callsAdvanceTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .addMockedMethod("drawAndProcessCard")
            .createMock();

    turnManager.numExtraCardsToDraw = 0;

    turnManager.advanceTurn();

    EasyMock.replay(gameEngine, turnManager);

    turnManager.endTurn();

    int expected = 0;
    int actual = turnManager.numExtraCardsToDraw;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void endTurn_drawCounterOne_callsDrawAndProcessCard() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .addMockedMethod("drawAndProcessCard")
            .createMock();

    turnManager.numExtraCardsToDraw = 1;

    turnManager.drawAndProcessCard(false);

    EasyMock.replay(gameEngine, turnManager);

    turnManager.endTurn();

    int expected = 0;
    int actual = turnManager.numExtraCardsToDraw;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void endTurn_drawCounterGreaterThanOne_callsDrawAndProcessCard() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .addMockedMethod("drawAndProcessCard")
            .createMock();

    turnManager.numExtraCardsToDraw = 3;

    turnManager.drawAndProcessCard(false);

    EasyMock.replay(gameEngine, turnManager);

    turnManager.endTurn();

    int expected = 2;
    int actual = turnManager.numExtraCardsToDraw;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void endTurn_drawCounterMaxValue_callsDrawAndProcessCard() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("advanceTurn")
            .addMockedMethod("drawAndProcessCard")
            .createMock();

    turnManager.numExtraCardsToDraw = 7;

    turnManager.drawAndProcessCard(false);

    EasyMock.replay(gameEngine, turnManager);

    turnManager.endTurn();

    int expected = 6;
    int actual = turnManager.numExtraCardsToDraw;
    assertEquals(expected, actual);

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void handleExplodingKitten_hasDefuseFalse_EliminatesPlayer() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .createMock();

    turnManager.currPlayerIndex = 0;
    boolean hasDefuse = false;

    EasyMock.expect(gameEngine.playerHasCard(Card.DEFUSE, turnManager.currPlayerIndex)).andReturn(hasDefuse);
    gameEngine.eliminatePlayer(turnManager.currPlayerIndex);
    turnManager.endTurn();

    EasyMock.replay(gameEngine, turnManager);

    turnManager.handleExplodingKitten();

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void handleExplodingKitten_hasDefuseTrue() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .createMock();

    turnManager.currPlayerIndex = 0;
    boolean hasDefuse = true;
    int placementLocation = 0;
    int drawPileSize = 3;

    EasyMock.expect(gameEngine.playerHasCard(Card.DEFUSE, turnManager.currPlayerIndex)).andReturn(hasDefuse);
    gameEngine.removeCardFromPlayer(Card.DEFUSE, turnManager.currPlayerIndex);
    gameEngine.discardCard(Card.DEFUSE);
    EasyMock.expect(gameEngine.getDrawPile()).andReturn(new Card[drawPileSize]);
    EasyMock.expect(ui.promptPlacementForExplodeOrImplode(drawPileSize, true)).andReturn(placementLocation);
    gameEngine.addCardToDrawPileAt(Card.EXPLODE, placementLocation);
    turnManager.endTurn();

    EasyMock.replay(gameEngine, ui, turnManager);

    turnManager.handleExplodingKitten();

    EasyMock.verify(gameEngine, ui, turnManager);
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
  public void handleImplodingCat_faceUp_EliminatesPlayer() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .createMock();

    turnManager.currPlayerIndex = 0;
    turnManager.isImplodingCatFaceUp = true;

    gameEngine.eliminatePlayer(turnManager.currPlayerIndex);
    turnManager.endTurn();

    EasyMock.replay(gameEngine, turnManager);

    turnManager.handleImplodingCat();

    EasyMock.verify(gameEngine, turnManager);
  }

  @Test
  public void handleImplodingCat_faceDown_CardInsertedBack() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .createMock();

    turnManager.currPlayerIndex = 0;
    turnManager.isImplodingCatFaceUp = false;
    int placementLocation = 0;
    int drawPileSize = 3;

    EasyMock.expect(gameEngine.getDrawPile()).andReturn(new Card[drawPileSize]);
    EasyMock.expect(ui.promptPlacementForExplodeOrImplode(drawPileSize, false)).andReturn(placementLocation);
    gameEngine.addCardToDrawPileAt(Card.IMPLODE, placementLocation);
    turnManager.endTurn();

    EasyMock.replay(gameEngine, ui, turnManager);

    turnManager.handleImplodingCat();

    EasyMock.verify(gameEngine, ui, turnManager);
  }

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

    EasyMock.expect(ui.promptNope(false)).andReturn(validPlayerName);
    EasyMock.expect(gameEngine.getPlayerByName(validPlayerName)).andReturn(player);
    EasyMock.expect(player.removeCardFromHand(Card.NOPE)).andReturn(true);

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

    EasyMock.expect(ui.promptNope(false)).andReturn(validPlayerName);
    EasyMock.expect(gameEngine.getPlayerByName(validPlayerName)).andReturn(player);
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

    EasyMock.expect(ui.promptNope(false)).andReturn(validPlayerName1);
    EasyMock.expect(gameEngine.getPlayerByName(validPlayerName1)).andReturn(player1);
    EasyMock.expect(player1.removeCardFromHand(Card.NOPE)).andReturn(false);
    EasyMock.expect(player1.getName()).andReturn(validPlayerName1);
    EasyMock.expect(ui.printLastPlayerDidNotHaveNopeAndGetNewPlayer(validPlayerName1)).andReturn(validPlayerName2);
    EasyMock.expect(gameEngine.getPlayerByName(validPlayerName2)).andReturn(player2);
    EasyMock.expect(player2.removeCardFromHand(Card.NOPE)).andReturn(true);

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

    EasyMock.expect(ui.promptNope(false)).andReturn(validPlayerName);
    EasyMock.expect(gameEngine.getPlayerByName(validPlayerName)).andThrow(new NoSuchElementException("No player with that name could be found."));
    EasyMock.expect(ui.promptNope(true)).andReturn("");

    EasyMock.replay(gameEngine, ui, player);

    boolean actualReturnValue = turnManager.promptAndValidateNopePlayerAndPlayNopeIfSo();
    boolean expectedReturnValue = false;

    assertEquals(expectedReturnValue, actualReturnValue);

    EasyMock.verify(gameEngine, ui, player);
  }

  @Test
  public void doSkip_numExtraCardsToDrawOne_numExtraCardsToDrawDecremented() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    int numOfPlayers = 2;
    int currPlayerInd = 0;
    int extraCards = 1;
    EasyMock.expect(gameEngine.getIsTurnOrderReversed()).andReturn(false).anyTimes();
    EasyMock.expect(gameEngine.getNumberOfPlayers()).andReturn(numOfPlayers).anyTimes();
    EasyMock.replay(gameEngine);

    turnManager.numExtraCardsToDraw = extraCards;
    turnManager.currPlayerIndex = currPlayerInd;

    turnManager.doSkip();

    int actualNumExtraCardsToDraw = turnManager.numExtraCardsToDraw;
    int expectedNumExtraCardsToDraw = 0;
    int actualPlayerInd = turnManager.currPlayerIndex;
    int expectedPlayerInd = 0;

    assertEquals(expectedNumExtraCardsToDraw, actualNumExtraCardsToDraw);
    assertEquals(expectedPlayerInd, actualPlayerInd);

    EasyMock.verify(gameEngine);
  }
}



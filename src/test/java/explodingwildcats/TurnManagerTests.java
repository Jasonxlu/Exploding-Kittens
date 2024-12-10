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
            .addMockedMethod("advanceTurn")
            .createMock();

    turnManager.advanceTurn();
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
            .addMockedMethod("advanceTurn")
            .createMock();

    turnManager.numExtraCardsToDraw = 7;

    turnManager.advanceTurn();
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

    turnManager.numExtraCardsToDraw = 1;

    turnManager.doSkip();

    int actualNumExtraCardsToDraw = turnManager.numExtraCardsToDraw;
    int expectedNumExtraCardsToDraw = 0;

    assertEquals(expectedNumExtraCardsToDraw, actualNumExtraCardsToDraw);
  }

  @Test
  public void doSkip_numExtraCardsToDrawTwo_numExtraCardsToDrawDecremented() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    int extraCards = 2;

    turnManager.numExtraCardsToDraw = 2;

    turnManager.doSkip();

    int actualNumExtraCardsToDraw = turnManager.numExtraCardsToDraw;
    int expectedNumExtraCardsToDraw = extraCards - 1;

    assertEquals(expectedNumExtraCardsToDraw, actualNumExtraCardsToDraw);
  }

  @Test
  public void doSkip_NoExtraCardsToDraw_turnEnded() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .createMock();

    int extraCards = 0;
    turnManager.numExtraCardsToDraw = extraCards;
    turnManager.endTurn();

    EasyMock.replay(turnManager);

    turnManager.doSkip();

    int actualNumExtraCardsToDraw = turnManager.numExtraCardsToDraw;

    assertEquals(extraCards, actualNumExtraCardsToDraw);

    EasyMock.verify(turnManager);
  }

  @Test
  public void getPlayableCard_attack() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "attack";
    Card expectedCard = Card.ATTACK;
    Card actualCard = turnManager.getPlayableCard(cardName);

    assertEquals(expectedCard, actualCard);
  }

  @Test
  public void getPlayableCard_skip() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "skip";
    Card expectedCard = Card.SKIP;
    Card actualCard = turnManager.getPlayableCard(cardName);

    assertEquals(expectedCard, actualCard);
  }

  @Test
  public void getPlayableCard_targetedAttack() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "targeted attack";
    Card expectedCard = Card.TARGETED_ATTACK;
    Card actualCard = turnManager.getPlayableCard(cardName);

    assertEquals(expectedCard, actualCard);
  }

  @Test
  public void getPlayableCard_shuffle() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "shuffle";
    Card expectedCard = Card.SHUFFLE;
    Card actualCard = turnManager.getPlayableCard(cardName);

    assertEquals(expectedCard, actualCard);
  }

  @Test
  public void getPlayableCard_seeTheFuture() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "see the future";
    Card expectedCard = Card.SEE_THE_FUTURE;
    Card actualCard = turnManager.getPlayableCard(cardName);

    assertEquals(expectedCard, actualCard);
  }

  @Test
  public void getPlayableCard_reverse() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "reverse";
    Card expectedCard = Card.REVERSE;
    Card actualCard = turnManager.getPlayableCard(cardName);

    assertEquals(expectedCard, actualCard);
  }

  @Test
  public void getPlayableCard_drawFromBottom() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "draw from bottom";
    Card expectedCard = Card.DRAW_FROM_BOTTOM;
    Card actualCard = turnManager.getPlayableCard(cardName);

    assertEquals(expectedCard, actualCard);
  }

  @Test
  public void getPlayableCard_alterTheFuture() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "alter the future";
    Card expectedCard = Card.ALTER_THE_FUTURE;
    Card actualCard = turnManager.getPlayableCard(cardName);

    assertEquals(expectedCard, actualCard);
  }

  @Test
  public void getPlayableCard_invalidInput() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "invalid";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      turnManager.getPlayableCard(cardName);
    });

    String expectedMessage = "Could not parse input.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void getPlayableCard_playedNopeCard() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String cardName = "nope";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      turnManager.getPlayableCard(cardName);
    });

    String expectedMessage = "You cannot play a nope right now.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @ParameterizedTest
  @CsvSource({
    "taco cat", "beard cat", "rainbow cat", "feral cat", "hairy potato cat"
  })
  public void getPlayableCard_playedCatCards(String cardName) {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      turnManager.getPlayableCard(cardName);
    });

    String expectedMessage = "You must play a cat card as a combo.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }


  @Test
  public void playCardLoop_UserInputEmpty_callsEndTurn_endsTurn() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = EasyMock.partialMockBuilder(TurnManager.class)
            .withConstructor(ui, gameEngine)
            .addMockedMethod("endTurn")
            .createMock();

    gameEngine.printCurrentPlayerHand();

    EasyMock.expect(ui.promptPlayCard(false)).andReturn("");

    turnManager.endTurn();
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
            .createMock();

    gameEngine.printCurrentPlayerHand();

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
            .createMock();

    gameEngine.printCurrentPlayerHand();

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

    turnManager.doSeeTheFuture();
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = false; // do not terminate loop
      return null;
    });

    gameEngine.printCurrentPlayerHand();
    String newUserInput = "";
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(newUserInput);
    turnManager.endTurn();
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
            .createMock();

    gameEngine.printCurrentPlayerHand();

    int currPlayerIndex = 0;
    turnManager.currPlayerIndex = currPlayerIndex;

    String userInput = "alter the future";
    Card userInputCard = Card.ALTER_THE_FUTURE;
    boolean playerHasCard = false;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.getPlayableCard(userInput)).andReturn(userInputCard);
    EasyMock.expect(gameEngine.playerHasCard(userInputCard, currPlayerIndex)).andReturn(playerHasCard);

    boolean rePrompting = true;

    gameEngine.printCurrentPlayerHand();
    String newUserInput = "";
    EasyMock.expect(ui.promptPlayCard(rePrompting)).andReturn(newUserInput);
    turnManager.endTurn();
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
            .addMockedMethod("promptAndPlayComboCatCards")
            .createMock();

    gameEngine.printCurrentPlayerHand();

    String userInput = "invalid";
    String exceptionMessage = "Could not parse input";
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.getPlayableCard(userInput)).andThrow(
            new IllegalArgumentException(exceptionMessage)
    );

    ui.println(exceptionMessage);

    boolean isRePrompting = true;

    gameEngine.printCurrentPlayerHand();
    String newUserInput = "";
    EasyMock.expect(ui.promptPlayCard(isRePrompting)).andReturn(newUserInput);
    turnManager.endTurn();
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
            .createMock();

    gameEngine.printCurrentPlayerHand();

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
            .createMock();

    gameEngine.printCurrentPlayerHand();

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

    turnManager.doAlterTheFuture();
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = false; // do not terminate loop
      return null;
    });

    gameEngine.printCurrentPlayerHand();
    String newUserInput = "skip";
    Card newUserInputCard = Card.SKIP;
    boolean newPlayerHasCard = true;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(newUserInput);
    EasyMock.expect(turnManager.getPlayableCard(newUserInput)).andReturn(newUserInputCard);
    EasyMock.expect(gameEngine.playerHasCard(newUserInputCard, currPlayerIndex)).andReturn(newPlayerHasCard);
    EasyMock.expect(turnManager.promptPlayNope()).andReturn(somebodyPlayedNope);

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
            .createMock();

    gameEngine.printCurrentPlayerHand();

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

    turnManager.doShuffle();
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = false; // do not terminate loop
      return null;
    });

    gameEngine.printCurrentPlayerHand();
    String newUserInput = "targeted attack";
    Card newUserInputCard = Card.TARGETED_ATTACK;
    boolean newPlayerHasCard = true;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(newUserInput);
    EasyMock.expect(turnManager.getPlayableCard(newUserInput)).andReturn(newUserInputCard);
    EasyMock.expect(gameEngine.playerHasCard(newUserInputCard, currPlayerIndex)).andReturn(newPlayerHasCard);
    EasyMock.expect(turnManager.promptPlayNope()).andReturn(somebodyPlayedNope);

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
            .createMock();

    gameEngine.printCurrentPlayerHand();
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
            .createMock();

    gameEngine.printCurrentPlayerHand();
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

    gameEngine.printCurrentPlayerHand();
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.getPlayableCard(userInput)).andReturn(userInputCard);
    EasyMock.expect(gameEngine.playerHasCard(userInputCard, currPlayerIndex)).andReturn(playerHasCard);
    boolean somebodyPlayedNope2ndTime = false;
    EasyMock.expect(turnManager.promptPlayNope()).andReturn(somebodyPlayedNope2ndTime);

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
            .addMockedMethod("promptAndPlayComboCatCards")
            .createMock();

    gameEngine.printCurrentPlayerHand();

    boolean isRePrompting = true;
    String userInput = "2 cat cards";
    int numCatCards = 2;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.promptAndPlayComboCatCards(numCatCards)).andReturn(isRePrompting);

    gameEngine.printCurrentPlayerHand();

    EasyMock.expect(ui.promptPlayCard(isRePrompting)).andReturn("");

    turnManager.endTurn();
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
            .addMockedMethod("promptAndPlayComboCatCards")
            .createMock();

    gameEngine.printCurrentPlayerHand();

    boolean isRePrompting = false;
    String userInput = "3 cat cards";
    int numCatCards = 3;
    EasyMock.expect(ui.promptPlayCard(false)).andReturn(userInput);
    EasyMock.expect(turnManager.promptAndPlayComboCatCards(numCatCards)).andReturn(isRePrompting);

    gameEngine.printCurrentPlayerHand();

    EasyMock.expect(ui.promptPlayCard(isRePrompting)).andReturn("");

    turnManager.endTurn();
    EasyMock.expectLastCall().andAnswer(() -> {
      turnManager.playerTurnHasEnded = true; // Manually terminate loop
      return null;
    });

    EasyMock.replay(turnManager, gameEngine, ui);

    turnManager.playCardLoop();

    EasyMock.verify(turnManager, gameEngine, ui);
  }
  @Test
  public void doTargetedAttack_invalidPlayerName_noExtraCardsToDraw_promptAgainForInput() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String invalidPlayerName = "";
    int currPlayerIndex = 3;
    int expectedPlayerIndex = 0;
    int extraCards = 0;

    EasyMock.expect(ui.promptTargetedAttack(false)).andReturn(invalidPlayerName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(invalidPlayerName)).andThrow(new NoSuchElementException("No player with that name could be found."));
    EasyMock.expect(ui.promptTargetedAttack(true)).andReturn("John");
    EasyMock.expect(gameEngine.getPlayerIndexByName("John")).andReturn(expectedPlayerIndex);

    EasyMock.replay(ui, gameEngine);

    turnManager.numExtraCardsToDraw = extraCards;
    turnManager.doTargetedAttack();

    int actualNumExtraCardsToDraw = turnManager.numExtraCardsToDraw;
    int actualPlayerIndex = turnManager.currPlayerIndex;
    int expectedNumExtraCardsToDraw = extraCards + 1;

    assertEquals(expectedNumExtraCardsToDraw, actualNumExtraCardsToDraw);
    assertEquals(expectedPlayerIndex, actualPlayerIndex);

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void doTargetedAttack_validPlayerName_noExtraCardsToDraw_extraCardsToDrawIncrementedByOneAndPlayerIndexUpdated() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String validPlayerName = "John";
    int extraCards = 0;
    int currPlayerIndex = 0;
    int expectedPlayerIndex = 2;

    EasyMock.expect(ui.promptTargetedAttack(false)).andReturn(validPlayerName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(validPlayerName)).andReturn(expectedPlayerIndex);

    EasyMock.replay(ui, gameEngine);

    turnManager.numExtraCardsToDraw = extraCards;
    turnManager.currPlayerIndex = currPlayerIndex;
    turnManager.doTargetedAttack();

    int actualNumExtraCardsToDraw = turnManager.numExtraCardsToDraw;
    int actualPlayerIndex = turnManager.currPlayerIndex;
    int expectedNumExtraCardsToDraw = extraCards + 1;

    assertEquals(expectedNumExtraCardsToDraw, actualNumExtraCardsToDraw);
    assertEquals(expectedPlayerIndex, actualPlayerIndex);

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void doTargetedAttack_validPlayerName_sevenExtraCardsToDraw_extraCardsToDrawIncrementedByTwoAndPlayerIndexUpdated() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String validPlayerName = "Jane";
    int extraCards = 7;
    int currPlayerIndex = 2;
    int expectedPlayerIndex = 4;

    EasyMock.expect(ui.promptTargetedAttack(false)).andReturn(validPlayerName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(validPlayerName)).andReturn(expectedPlayerIndex);

    EasyMock.replay(ui, gameEngine);

    turnManager.numExtraCardsToDraw = extraCards;
    turnManager.currPlayerIndex = currPlayerIndex;
    turnManager.doTargetedAttack();

    int actualNumExtraCardsToDraw = turnManager.numExtraCardsToDraw;
    int actualPlayerIndex = turnManager.currPlayerIndex;
    int expectedNumExtraCardsToDraw = extraCards + 2;

    assertEquals(expectedNumExtraCardsToDraw, actualNumExtraCardsToDraw);
    assertEquals(expectedPlayerIndex, actualPlayerIndex);

    EasyMock.verify(ui, gameEngine);
  }

  @Test
  public void do2CardCombo_emptyTargetName_invalidCardName_targetHandSingleCard_repromptForInputAndModifyBothHands() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String invalidTargetName = "";
    String invalidCardName = "explode";
    String targetName = "John";
    String cardName = "attack";
    int targetIndex = 0;
    int currPlayerIndex = 1;
    Card targetCard = Card.ATTACK;

    Player targetPlayer = EasyMock.createMock(Player.class);
    Player currPlayer = EasyMock.createMock(Player.class);

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
    EasyMock.expect(ui.prompt2CardComboTarget(targetIndex, false)).andReturn(invalidCardName);
    EasyMock.expect(gameEngine.getCardByName(invalidCardName)).andThrow(new IllegalArgumentException("Could not parse input."));
    EasyMock.expect(ui.prompt2CardComboTarget(targetIndex, true)).andReturn(cardName);
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(targetCard);

    EasyMock.expect(gameEngine.playerHasCard(targetCard, targetIndex)).andReturn(true);

    // Modify both hands
    gameEngine.removeCardFromPlayer(targetCard, targetIndex);
    EasyMock.expect(gameEngine.getPlayerByIndex(currPlayerIndex)).andReturn(currPlayer);
    currPlayer.addCardToHand(targetCard);

    // REPLAY
    EasyMock.replay(ui, gameEngine, targetPlayer, currPlayer);

    turnManager.currPlayerIndex = currPlayerIndex;
    turnManager.do2CardCombo();

    EasyMock.verify(ui, gameEngine, targetPlayer, currPlayer);
  }

  @Test
  public void do2CardCombo_invalidTargetName_validCardName_targetHandMultipleCards_repromptForInputAndModifyBothHands() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String invalidTargetName = "invalid";
    String targetName = "Jane";
    String cardName = "skip";
    int targetIndex = 2;
    int currPlayerIndex = 0;
    Card targetCard = Card.SKIP;

    Player targetPlayer = EasyMock.createMock(Player.class);
    Player currPlayer = EasyMock.createMock(Player.class);

    // Target name selection requires re-prompting
    EasyMock.expect(ui.prompt2CardCombo(false)).andReturn(invalidTargetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(invalidTargetName)).andThrow(new NoSuchElementException("No player with that name could be found."));
    EasyMock.expect(ui.prompt2CardCombo(true)).andReturn(targetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(targetName)).andReturn(targetIndex);

    // Set expectations for the target's hand check
    Card[] targetHand = {Card.SKIP, Card.SEE_THE_FUTURE};
    EasyMock.expect(gameEngine.getPlayerByIndex(targetIndex)).andReturn(targetPlayer);
    EasyMock.expect(targetPlayer.getHand()).andReturn(targetHand);

    // Target card selection good on first attempt
    EasyMock.expect(ui.prompt2CardComboTarget(targetIndex, false)).andReturn(cardName);
    EasyMock.expect(gameEngine.getCardByName(cardName)).andReturn(targetCard);

    EasyMock.expect(gameEngine.playerHasCard(targetCard, targetIndex)).andReturn(true);

    // Modify both hands
    gameEngine.removeCardFromPlayer(targetCard, targetIndex);
    EasyMock.expect(gameEngine.getPlayerByIndex(currPlayerIndex)).andReturn(currPlayer);
    currPlayer.addCardToHand(targetCard);

    // REPLAY
    EasyMock.replay(ui, gameEngine, targetPlayer, currPlayer);

    turnManager.currPlayerIndex = currPlayerIndex;
    turnManager.do2CardCombo();

    EasyMock.verify(ui, gameEngine, targetPlayer, currPlayer);
  }

  @Test
  public void do2CardCombo_validTargetName_emptyTargetHand_noEffectOnPlayerHands() {
    GameEngine gameEngine = EasyMock.createMock(GameEngine.class);
    UserInterface ui = EasyMock.createMock(UserInterface.class);
    TurnManager turnManager = new TurnManager(ui, gameEngine);

    String targetName = "John";

    int targetIndex = 3;

    Player targetPlayer = EasyMock.createMock(Player.class);

    // Target name selection valid on first attempt
    EasyMock.expect(ui.prompt2CardCombo(false)).andReturn(targetName);
    EasyMock.expect(gameEngine.getPlayerIndexByName(targetName)).andReturn(targetIndex);

    // Set expectations for the target's hand check
    Card[] targetHand = {};
    EasyMock.expect(gameEngine.getPlayerByIndex(targetIndex)).andReturn(targetPlayer);
    EasyMock.expect(targetPlayer.getHand()).andReturn(targetHand);
    ui.println("The target player has no cards to give.");

    // REPLAY
    EasyMock.replay(ui, gameEngine, targetPlayer);

    turnManager.do2CardCombo();

    EasyMock.verify(ui, gameEngine, targetPlayer);
  }


}



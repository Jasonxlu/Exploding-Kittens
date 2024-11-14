package explodingwildcats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;


/**
 * Test suite for the game engine class.
 */
public class GameEngineTests {
  @Test
  public void setUpPlayers_OnePlayerOneName_ThrowException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    final int numPlayers = 1;
    String[] names = {"John"};

    EasyMock.replay(playerFactory);

    String expectedMessage = "Not enough players";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      game.setUpPlayers(numPlayers, names);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(playerFactory);
  }

  @Test
  public void setUpPlayers_OnePlayerNoNames_ThrowException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    final int numPlayers = 1;
    String[] names = {};

    EasyMock.replay(playerFactory);

    String expectedMessage = "Number of players and number names mismatch";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      game.setUpPlayers(numPlayers, names);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(playerFactory);
  }

  @Test
  public void setUpPlayers_TwoPlayersTwoNames() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);
    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);

    final int numPlayers = 2;
    String[] names = {"John", "Jane"};

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);

    EasyMock.replay(playerFactory, cardPileFactory);

    game.setUpPlayers(numPlayers, names);

    final int expectedNumPlayers = 2;
    int actualNumPlayers = game.getNumberOfPlayers();
    assertEquals(expectedNumPlayers, actualNumPlayers);

    Player[] players = game.getPlayers();
    assertEquals(numPlayers, players.length);

    EasyMock.verify(playerFactory, cardPileFactory);
  }

  @Test
  public void setUpPlayers_MaxPlayersMaxNames() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    final int numPlayers = 4;
    String[] names = {"John", "Jane", "Alice", "Bob"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);
    Player p3 = EasyMock.createMock(Player.class);
    Player p4 = EasyMock.createMock(Player.class);
    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(playerFactory.createPlayer("Alice", playerHand)).andReturn(p3);
    EasyMock.expect(playerFactory.createPlayer("Bob", playerHand)).andReturn(p4);

    EasyMock.replay(playerFactory, cardPileFactory);

    game.setUpPlayers(numPlayers, names);

    final int expectedNumPlayers = 4;
    int actualNumPlayers = game.getNumberOfPlayers();
    assertEquals(expectedNumPlayers, actualNumPlayers);

    Player[] players = game.getPlayers();
    assertEquals(numPlayers, players.length);

    EasyMock.verify(playerFactory, cardPileFactory);
  }

  @Test
  public void setUpPlayers_TooManyPlayers_ThrowException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    EasyMock.replay(playerFactory, cardPileFactory);

    final int numPlayers = 5;
    String[] names = {"John", "Jane", "Alice", "Bob", "Charlie"};

    String expectedMessage = "Too many players";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      game.setUpPlayers(numPlayers, names);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(playerFactory, cardPileFactory);
  }

  @Test
  public void dealDefuses_TwoPlayers() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    final int numPlayers = 2;
    String[] names = {"John", "Jane"};
    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);

    p1.addCardToHand(Card.DEFUSE);
    p2.addCardToHand(Card.DEFUSE);
    drawPile.addCard(Card.DEFUSE);
    drawPile.addCard(Card.DEFUSE);
    drawPile.addCard(Card.DEFUSE);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);

    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andStubReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andStubReturn(p2);

    EasyMock.replay(playerFactory, p1, p2, drawPile, cardPileFactory);

    game.setUpPlayers(numPlayers, names);
    game.dealDefuses();

    EasyMock.verify(playerFactory, p1, p2, drawPile, cardPileFactory);
  }

  @Test
  public void dealDefuses_MaxPlayers() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    final int numPlayers = 4;
    String[] names = {"John", "Jane", "Alice", "Bob"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);
    Player p3 = EasyMock.createMock(Player.class);
    Player p4 = EasyMock.createMock(Player.class);

    p1.addCardToHand(Card.DEFUSE);
    p2.addCardToHand(Card.DEFUSE);
    p3.addCardToHand(Card.DEFUSE);
    p4.addCardToHand(Card.DEFUSE);
    drawPile.addCard(Card.DEFUSE);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(playerFactory.createPlayer("Alice", playerHand)).andReturn(p3);
    EasyMock.expect(playerFactory.createPlayer("Bob", playerHand)).andReturn(p4);

    EasyMock.replay(playerFactory, p1, p2, p3, p4, drawPile, cardPileFactory);

    game.setUpPlayers(numPlayers, names);
    game.dealDefuses();

    EasyMock.verify(playerFactory, p1, p2, p3, p4,drawPile, cardPileFactory);
  }

  @Test
  public void dealCards_TwoPlayers_CorrectHandsAndDrawPile() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    final int numPlayers = 2;
    String[] names = {"John", "Jane"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);

    p1.addCardToHand(Card.DEFUSE);
    p2.addCardToHand(Card.DEFUSE);

    // Expect 3 since there are 2 players
    drawPile.addCard(Card.DEFUSE);
    drawPile.addCard(Card.DEFUSE);
    drawPile.addCard(Card.DEFUSE);

    final int numCardsDistributedToEachPlayer = 5;
    for(int x = 0; x < numCardsDistributedToEachPlayer; x++) {
      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p1.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p2.addCardToHand(EasyMock.anyObject(Card.class));
    }

    EasyMock.replay(playerFactory, p1, p2, drawPile, cardPileFactory);

    game.setUpPlayers(numPlayers, names);
    game.dealDefuses();
    game.dealCards();

    EasyMock.verify(playerFactory, p1, p2, drawPile, cardPileFactory);
  }

  @Test
  public void dealCards_MaxPlayers_CorrectHandsAndPiles() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    final int numPlayers = 4;
    String[] names = {"John", "Jane", "Bob", "Job"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);
    Player p3 = EasyMock.createMock(Player.class);
    Player p4 = EasyMock.createMock(Player.class);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(playerFactory.createPlayer("Bob", playerHand)).andReturn(p3);
    EasyMock.expect(playerFactory.createPlayer("Job", playerHand)).andReturn(p4);

    p1.addCardToHand(Card.DEFUSE);
    p2.addCardToHand(Card.DEFUSE);
    p3.addCardToHand(Card.DEFUSE);
    p4.addCardToHand(Card.DEFUSE);

    // Expect 1 since there are 4 players
    drawPile.addCard(Card.DEFUSE);

    final int numCardsDistributedToEachPlayer = 5;
    for(int x = 0; x < numCardsDistributedToEachPlayer; x++) {
      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p1.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p2.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p3.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p4.addCardToHand(EasyMock.anyObject(Card.class));
    }

    EasyMock.replay(playerFactory, p1, p2, p3, p4, drawPile, cardPileFactory);

    game.setUpPlayers(numPlayers, names);
    game.dealDefuses();
    game.dealCards();

    EasyMock.verify(playerFactory, p1, p2, p3, p4, drawPile, cardPileFactory);
  }

  @Test
  public void insertExplodingCards_TwoPlayers_DrawPileAltered() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    final int numPlayers = 2;
    String[] names = {"John", "Jane"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);

    p1.addCardToHand(Card.DEFUSE);
    p2.addCardToHand(Card.DEFUSE);

    // Expect 3 since there are 2 players
    drawPile.addCard(Card.DEFUSE);
    drawPile.addCard(Card.DEFUSE);
    drawPile.addCard(Card.DEFUSE);

    final int numCardsDistributedToEachPlayer = 5;
    for(int x = 0; x < numCardsDistributedToEachPlayer; x++) {
      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p1.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p2.addCardToHand(EasyMock.anyObject(Card.class));
    }

    // Expect one since there are 2 players
    drawPile.addCard(Card.EXPLODE);

    EasyMock.replay(playerFactory, p1, p2, drawPile, cardPileFactory);

    game.setUpPlayers(numPlayers, names);
    game.dealDefuses();
    game.dealCards();
    game.insertExplodingCards();

    EasyMock.verify(playerFactory, p1, p2, drawPile, cardPileFactory);
  }

  @Test
  public void insertExplodingCards_MaxPlayers_DrawPileAltered() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    final int numPlayers = 4;
    String[] names = {"John", "Jane", "Bob", "Job"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);
    Player p3 = EasyMock.createMock(Player.class);
    Player p4 = EasyMock.createMock(Player.class);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(playerFactory.createPlayer("Bob", playerHand)).andReturn(p3);
    EasyMock.expect(playerFactory.createPlayer("Job", playerHand)).andReturn(p4);

    p1.addCardToHand(Card.DEFUSE);
    p2.addCardToHand(Card.DEFUSE);
    p3.addCardToHand(Card.DEFUSE);
    p4.addCardToHand(Card.DEFUSE);

    // Expect 1 since there are 4 players
    drawPile.addCard(Card.DEFUSE);

    final int numCardsDistributedToEachPlayer = 5;
    for(int x = 0; x < numCardsDistributedToEachPlayer; x++) {
      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p1.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p2.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p3.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p4.addCardToHand(EasyMock.anyObject(Card.class));
    }

    // Expect 3 since there are 4 players
    drawPile.addCard(Card.EXPLODE);
    drawPile.addCard(Card.EXPLODE);
    drawPile.addCard(Card.EXPLODE);

    EasyMock.replay(playerFactory, p1, p2, p3, p4, drawPile, cardPileFactory);

    game.setUpPlayers(numPlayers, names);
    game.dealDefuses();
    game.dealCards();
    game.insertExplodingCards();

    EasyMock.verify(playerFactory, p1, p2, p3, p4, drawPile, cardPileFactory);
  }

  @Test
  public void createDrawPile_CorrectCardCounts() {
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.niceMock(CardPile.class);
    GameEngine game = new GameEngine(null, cardPileFactory, drawPile);

    final int expectedAttacks = 3;
    final int expectedShuffles = 4;
    final int expectedSkips = 3;
    final int expectedFutures = 4;
    final int expectedNopes = 4;
    final int expectedCats = 16; // 4 each, 4 cat types, 16 total

    for (int i = 0; i < expectedAttacks; i++) {
      drawPile.addCard(Card.ATTACK);
    }

    for (int i = 0; i < expectedShuffles; i++) {
      drawPile.addCard(Card.SHUFFLE);
    }

    for (int i = 0; i < expectedSkips; i++) {
      drawPile.addCard(Card.SKIP);
    }

    for (int i = 0; i < expectedFutures; i++) {
      drawPile.addCard(Card.SEE_THE_FUTURE);
    }

    for (int i = 0; i < expectedNopes; i++) {
      drawPile.addCard(Card.NOPE);
    }

    for (int i = 0; i < expectedCats; i++) {
      drawPile.addCard(Card.CAT);
    }

    EasyMock.replay(drawPile);

    game.createDrawPile();

    EasyMock.verify(drawPile);
  }


  @Test
  public void reverseTurnOrder_isTurnOrderReversedIsFalse() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    game.reverseTurnOrder();

    boolean expectedTurnOrder = true;
    boolean actualTurnOrder = game.getIsTurnOrderReversed();
    assertEquals(expectedTurnOrder, actualTurnOrder);
  }
}

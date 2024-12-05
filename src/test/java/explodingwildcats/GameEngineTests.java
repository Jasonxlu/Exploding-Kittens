package explodingwildcats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.List;


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

    List<Player> players = game.getPlayers();
    assertEquals(numPlayers, players.size());

    EasyMock.verify(playerFactory, cardPileFactory);
  }

  @Test
  public void setUpPlayers_MaxPlayersMaxNames() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    final int numPlayers = 6;
    String[] names = {"John", "Jane", "Alice", "Bob", "Charlie", "David"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);
    Player p3 = EasyMock.createMock(Player.class);
    Player p4 = EasyMock.createMock(Player.class);
    Player p5 = EasyMock.createMock(Player.class);
    Player p6 = EasyMock.createMock(Player.class);
    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(playerFactory.createPlayer("Alice", playerHand)).andReturn(p3);
    EasyMock.expect(playerFactory.createPlayer("Bob", playerHand)).andReturn(p4);
    EasyMock.expect(playerFactory.createPlayer("Charlie", playerHand)).andReturn(p5);
    EasyMock.expect(playerFactory.createPlayer("David", playerHand)).andReturn(p6);

    EasyMock.replay(playerFactory, cardPileFactory);

    game.setUpPlayers(numPlayers, names);

    final int expectedNumPlayers = 6;
    int actualNumPlayers = game.getNumberOfPlayers();
    assertEquals(expectedNumPlayers, actualNumPlayers);

    List<Player> players = game.getPlayers();
    assertEquals(numPlayers, players.size());

    EasyMock.verify(playerFactory, cardPileFactory);
  }

  @Test
  public void setUpPlayers_TooManyPlayers_ThrowException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    EasyMock.replay(playerFactory, cardPileFactory);

    final int numPlayers = 7;
    String[] names = {"John", "Jane", "Alice", "Bob", "Charlie", "David", "Emma"};

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

    final int numPlayers = 6;
    String[] names = {"John", "Jane", "Alice", "Bob", "Charlie", "David"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);
    Player p3 = EasyMock.createMock(Player.class);
    Player p4 = EasyMock.createMock(Player.class);
    Player p5 = EasyMock.createMock(Player.class);
    Player p6 = EasyMock.createMock(Player.class);

    p1.addCardToHand(Card.DEFUSE);
    p2.addCardToHand(Card.DEFUSE);
    p3.addCardToHand(Card.DEFUSE);
    p4.addCardToHand(Card.DEFUSE);
    p5.addCardToHand(Card.DEFUSE);
    p6.addCardToHand(Card.DEFUSE);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(playerFactory.createPlayer("Alice", playerHand)).andReturn(p3);
    EasyMock.expect(playerFactory.createPlayer("Bob", playerHand)).andReturn(p4);
    EasyMock.expect(playerFactory.createPlayer("Charlie", playerHand)).andReturn(p5);
    EasyMock.expect(playerFactory.createPlayer("David", playerHand)).andReturn(p6);

    EasyMock.replay(playerFactory, p1, p2, p3, p4, p5, p6, drawPile, cardPileFactory);

    game.setUpPlayers(numPlayers, names);
    game.dealDefuses();

    EasyMock.verify(playerFactory, p1, p2, p3, p4, p5, p6, drawPile, cardPileFactory);
  }

  @Test
  public void dealDefuses_FivePlayers() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    final int numPlayers = 5;
    String[] names = {"John", "Jane", "Alice", "Bob", "Charlie"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);
    Player p3 = EasyMock.createMock(Player.class);
    Player p4 = EasyMock.createMock(Player.class);
    Player p5 = EasyMock.createMock(Player.class);

    p1.addCardToHand(Card.DEFUSE);
    p2.addCardToHand(Card.DEFUSE);
    p3.addCardToHand(Card.DEFUSE);
    p4.addCardToHand(Card.DEFUSE);
    p5.addCardToHand(Card.DEFUSE);
    drawPile.addCard(Card.DEFUSE);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(playerFactory.createPlayer("Alice", playerHand)).andReturn(p3);
    EasyMock.expect(playerFactory.createPlayer("Bob", playerHand)).andReturn(p4);
    EasyMock.expect(playerFactory.createPlayer("Charlie", playerHand)).andReturn(p5);

    EasyMock.replay(playerFactory, p1, p2, p3, p4, p5, drawPile, cardPileFactory);

    game.setUpPlayers(numPlayers, names);
    game.dealDefuses();

    EasyMock.verify(playerFactory, p1, p2, p3, p4, p5, drawPile, cardPileFactory);
  }

  @Test
  public void dealDefuses_ThreePlayers() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    final int numPlayers = 3;
    String[] names = {"John", "Jane", "Alice"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);
    Player p3 = EasyMock.createMock(Player.class);

    p1.addCardToHand(Card.DEFUSE);
    p2.addCardToHand(Card.DEFUSE);
    p3.addCardToHand(Card.DEFUSE);
    drawPile.addCard(Card.DEFUSE);
    drawPile.addCard(Card.DEFUSE);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(playerFactory.createPlayer("Alice", playerHand)).andReturn(p3);

    EasyMock.replay(playerFactory, p1, p2, p3, drawPile, cardPileFactory);

    game.setUpPlayers(numPlayers, names);
    game.dealDefuses();

    EasyMock.verify(playerFactory, p1, p2, p3, drawPile, cardPileFactory);
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

    // Expect 2 since there are 2 players
    drawPile.addCard(Card.DEFUSE);
    drawPile.addCard(Card.DEFUSE);

    final int numCardsDistributedToEachPlayer = 7;
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

    final int numPlayers = 6;
    String[] names = {"John", "Jane", "Bob", "Job", "Charlie", "David"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);
    Player p3 = EasyMock.createMock(Player.class);
    Player p4 = EasyMock.createMock(Player.class);
    Player p5 = EasyMock.createMock(Player.class);
    Player p6 = EasyMock.createMock(Player.class);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(playerFactory.createPlayer("Bob", playerHand)).andReturn(p3);
    EasyMock.expect(playerFactory.createPlayer("Job", playerHand)).andReturn(p4);
    EasyMock.expect(playerFactory.createPlayer("Charlie", playerHand)).andReturn(p5);
    EasyMock.expect(playerFactory.createPlayer("David", playerHand)).andReturn(p6);

    p1.addCardToHand(Card.DEFUSE);
    p2.addCardToHand(Card.DEFUSE);
    p3.addCardToHand(Card.DEFUSE);
    p4.addCardToHand(Card.DEFUSE);
    p5.addCardToHand(Card.DEFUSE);
    p6.addCardToHand(Card.DEFUSE);

    final int numCardsDistributedToEachPlayer = 7;
    for(int x = 0; x < numCardsDistributedToEachPlayer; x++) {
      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p1.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p2.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p3.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p4.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p5.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p6.addCardToHand(EasyMock.anyObject(Card.class));
    }

    EasyMock.replay(playerFactory, p1, p2, p3, p4, p5, p6, drawPile, cardPileFactory);

    game.setUpPlayers(numPlayers, names);
    game.dealDefuses();
    game.dealCards();

    EasyMock.verify(playerFactory, p1, p2, p3, p4, p5, p6, drawPile, cardPileFactory);
  }

  @Test
  public void dealCards_FivePlayers_CorrectHandsAndPiles() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    final int numPlayers = 5;
    String[] names = {"John", "Jane", "Bob", "Job", "Charlie"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);
    Player p3 = EasyMock.createMock(Player.class);
    Player p4 = EasyMock.createMock(Player.class);
    Player p5 = EasyMock.createMock(Player.class);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(playerFactory.createPlayer("Bob", playerHand)).andReturn(p3);
    EasyMock.expect(playerFactory.createPlayer("Job", playerHand)).andReturn(p4);
    EasyMock.expect(playerFactory.createPlayer("Charlie", playerHand)).andReturn(p5);

    p1.addCardToHand(Card.DEFUSE);
    p2.addCardToHand(Card.DEFUSE);
    p3.addCardToHand(Card.DEFUSE);
    p4.addCardToHand(Card.DEFUSE);
    p5.addCardToHand(Card.DEFUSE);

    // Expect 1 since there are 5 players
    drawPile.addCard(Card.DEFUSE);

    final int numCardsDistributedToEachPlayer = 7;
    for(int x = 0; x < numCardsDistributedToEachPlayer; x++) {
      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p1.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p2.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p3.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p4.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p5.addCardToHand(EasyMock.anyObject(Card.class));
    }

    EasyMock.replay(playerFactory, p1, p2, p3, p4, p5, drawPile, cardPileFactory);

    game.setUpPlayers(numPlayers, names);
    game.dealDefuses();
    game.dealCards();

    EasyMock.verify(playerFactory, p1, p2, p3, p4, p5, drawPile, cardPileFactory);
  }

  @Test
  public void insertExplodingAndImplodingCards_TwoPlayers_DrawPileAltered() {
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

    // Expect 2 since there are 2 players
    drawPile.addCard(Card.DEFUSE);
    drawPile.addCard(Card.DEFUSE);

    final int numCardsDistributedToEachPlayer = 7;
    for(int x = 0; x < numCardsDistributedToEachPlayer; x++) {
      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p1.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p2.addCardToHand(EasyMock.anyObject(Card.class));
    }

    // Expect one exploding and one imploding since there are 2 players
    drawPile.addCard(Card.EXPLODE);
    drawPile.addCard(Card.IMPLODE);

    EasyMock.replay(playerFactory, p1, p2, drawPile, cardPileFactory);

    game.setUpPlayers(numPlayers, names);
    game.dealDefuses();
    game.dealCards();
    game.insertExplodingAndImplodingCards();

    EasyMock.verify(playerFactory, p1, p2, drawPile, cardPileFactory);
  }

  @Test
  public void insertExplodingAndImplodingCards_MaxPlayers_DrawPileAltered() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    final int numPlayers = 6;
    String[] names = {"John", "Jane", "Bob", "Job", "Charlie", "David"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);
    Player p3 = EasyMock.createMock(Player.class);
    Player p4 = EasyMock.createMock(Player.class);
    Player p5 = EasyMock.createMock(Player.class);
    Player p6 = EasyMock.createMock(Player.class);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(playerFactory.createPlayer("Bob", playerHand)).andReturn(p3);
    EasyMock.expect(playerFactory.createPlayer("Job", playerHand)).andReturn(p4);
    EasyMock.expect(playerFactory.createPlayer("Charlie", playerHand)).andReturn(p5);
    EasyMock.expect(playerFactory.createPlayer("David", playerHand)).andReturn(p6);

    p1.addCardToHand(Card.DEFUSE);
    p2.addCardToHand(Card.DEFUSE);
    p3.addCardToHand(Card.DEFUSE);
    p4.addCardToHand(Card.DEFUSE);
    p5.addCardToHand(Card.DEFUSE);
    p6.addCardToHand(Card.DEFUSE);

    final int numCardsDistributedToEachPlayer = 7;
    for(int x = 0; x < numCardsDistributedToEachPlayer; x++) {
      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p1.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p2.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p3.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p4.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p5.addCardToHand(EasyMock.anyObject(Card.class));

      EasyMock.expect(drawPile.drawCard()).andStubReturn(Card.SKIP);
      p6.addCardToHand(EasyMock.anyObject(Card.class));
    }

    // Expect 4 exploding and 1 imploding since there are 6 players
    drawPile.addCard(Card.EXPLODE);
    drawPile.addCard(Card.EXPLODE);
    drawPile.addCard(Card.EXPLODE);
    drawPile.addCard(Card.EXPLODE);
    drawPile.addCard(Card.IMPLODE);

    EasyMock.replay(playerFactory, p1, p2, p3, p4, p5, p6, drawPile, cardPileFactory);

    game.setUpPlayers(numPlayers, names);
    game.dealDefuses();
    game.dealCards();
    game.insertExplodingAndImplodingCards();

    EasyMock.verify(playerFactory, p1, p2, p3, p4, p5, p6, drawPile, cardPileFactory);
  }

  @Test
  public void createDrawPile_CorrectCardCounts() {
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(null, cardPileFactory, drawPile);

    final int expectedAttacks = 3;
    final int expectedShuffles = 4;
    final int expectedSkips = 3;
    final int expectedFutures = 4;
    final int expectedNopes = 4;
    final int expectedOfEachCats = 4;
    final int expectedReverses = 4;
    final int expectedDrawBottoms = 4;
    final int expectedAlterFutures = 4;
    final int expectedTargetedAttacks = 3;

    drawPile.addCard(Card.ATTACK);
    EasyMock.expectLastCall().times(expectedAttacks);

    drawPile.addCard(Card.SHUFFLE);
    EasyMock.expectLastCall().times(expectedShuffles);

    drawPile.addCard(Card.SKIP);
    EasyMock.expectLastCall().times(expectedSkips);

    drawPile.addCard(Card.SEE_THE_FUTURE);
    EasyMock.expectLastCall().times(expectedFutures);

    drawPile.addCard(Card.NOPE);
    EasyMock.expectLastCall().times(expectedNopes);

    drawPile.addCard(Card.TACO_CAT);
    EasyMock.expectLastCall().times(expectedOfEachCats);

    drawPile.addCard(Card.HAIRY_POTATO_CAT);
    EasyMock.expectLastCall().times(expectedOfEachCats);

    drawPile.addCard(Card.BEARD_CAT);
    EasyMock.expectLastCall().times(expectedOfEachCats);

    drawPile.addCard(Card.RAINBOW_CAT);
    EasyMock.expectLastCall().times(expectedOfEachCats);

    drawPile.addCard(Card.FERAL_CAT);
    EasyMock.expectLastCall().times(expectedOfEachCats);

    drawPile.addCard(Card.REVERSE);
    EasyMock.expectLastCall().times(expectedReverses);

    drawPile.addCard(Card.DRAW_FROM_BOTTOM);
    EasyMock.expectLastCall().times(expectedDrawBottoms);

    drawPile.addCard(Card.ALTER_THE_FUTURE);
    EasyMock.expectLastCall().times(expectedAlterFutures);

    drawPile.addCard(Card.TARGETED_ATTACK);
    EasyMock.expectLastCall().times(expectedTargetedAttacks);

    EasyMock.replay(drawPile);

    game.createDrawPile();

    EasyMock.verify(drawPile);
  }

  @Test
  public void replaceTopDrawPileCards_emptyDrawPile_oneCardToSet() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);
    EasyMock.expect(drawPile.getCards()).andReturn(new Card[0]);
    EasyMock.replay(drawPile);

    Card[] toSet = new Card[] {Card.SKIP};

    String expectedMessage = "Number of cards passed is greater than the number of cards in draw pile.";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      game.replaceTopDrawPileCards(toSet);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(drawPile);
  }

  @Test
  public void replaceTopDrawPileCards_drawPileContainsOneCard_oneCardToSet() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    EasyMock.expect(drawPile.getCards()).andReturn(new Card[] {Card.TACO_CAT});
    Card newCard = Card.SKIP;
    Card[] toSet = new Card[] {newCard};

    drawPile.setCard(0, newCard);
    EasyMock.replay(drawPile);

    game.replaceTopDrawPileCards(toSet);

    EasyMock.verify(drawPile);
  }

  @Test
  public void replaceTopDrawPileCards_drawPileContainsTwoCards_twoCardsToSet() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    EasyMock.expect(drawPile.getCards()).andReturn(new Card[] {Card.TACO_CAT, Card.DEFUSE});
    Card newTopCard = Card.SKIP;
    Card newSecondFromTopCard = Card.EXPLODE;
    Card[] toSet = new Card[] { newTopCard, newSecondFromTopCard };

    drawPile.setCard(1, newTopCard);
    drawPile.setCard(0, newSecondFromTopCard);
    EasyMock.replay(drawPile);

    game.replaceTopDrawPileCards(toSet);

    EasyMock.verify(drawPile);
  }

  @Test
  public void replaceTopDrawPileCards_drawPileContainsTwoCards_noCardsToSet() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    EasyMock.expect(drawPile.getCards()).andReturn(new Card[] {Card.TACO_CAT, Card.DEFUSE});
    Card[] toSet = new Card[0];

    EasyMock.replay(drawPile);

    game.replaceTopDrawPileCards(toSet);

    EasyMock.verify(drawPile);
  }

  @Test
  public void replaceTopDrawPileCards_drawPileContainsFourCards_threeCardsToSet() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    EasyMock.expect(drawPile.getCards()).andReturn(
            new Card[] {Card.TACO_CAT, Card.DEFUSE, Card.ATTACK, Card.TACO_CAT});
    Card newTopCard = Card.ALTER_THE_FUTURE;
    Card newSecondFromTopCard = Card.ALTER_THE_FUTURE;
    Card newThirdFromTopCard = Card.DRAW_FROM_BOTTOM;
    Card[] toSet = new Card[] { newTopCard, newSecondFromTopCard, newThirdFromTopCard };

    drawPile.setCard(3, newTopCard);
    drawPile.setCard(2, newSecondFromTopCard);
    drawPile.setCard(1, newThirdFromTopCard);

    EasyMock.replay(drawPile);

    game.replaceTopDrawPileCards(toSet);

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

  @Test
  public void reverseTurnOrder_isTurnOrderReversedIsTrue() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    game.reverseTurnOrder();
    game.reverseTurnOrder();

    boolean expectedTurnOrder = false;
    boolean actualTurnOrder = game.getIsTurnOrderReversed();
    assertEquals(expectedTurnOrder, actualTurnOrder);
  }

  @Test
  public void popBottomCard_callsAndReturnsDrawPileDrawCardFromBottom() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile);

    Card returnedPoppedCard = Card.ATTACK;
    EasyMock.expect(drawPile.drawCardFromBottom()).andReturn(returnedPoppedCard);

    EasyMock.replay(drawPile);

    Card actualPoppedCard = game.popBottomCard();

    assertEquals(returnedPoppedCard, actualPoppedCard);

    EasyMock.verify(drawPile);
  }
}

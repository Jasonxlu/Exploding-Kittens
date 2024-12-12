package explodingwildcats;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test suite for the game engine class.
 */
public class GameEngineTests {
  @Test
  public void setUpPlayers_OnePlayerOneName_ThrowException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);
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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(null, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);
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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

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
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    Card returnedPoppedCard = Card.ATTACK;
    EasyMock.expect(drawPile.drawCardFromBottom()).andReturn(returnedPoppedCard);

    EasyMock.replay(drawPile);

    Card actualPoppedCard = game.popBottomCard();

    assertEquals(returnedPoppedCard, actualPoppedCard);

    EasyMock.verify(drawPile);
  }

  @Test
  public void popTopCard_callsAndReturnsDrawPileDrawCard() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    Card returnedPoppedCard = Card.ATTACK;
    EasyMock.expect(drawPile.drawCard()).andReturn(returnedPoppedCard);

    EasyMock.replay(drawPile);

    Card actualPoppedCard = game.popTopCard();

    assertEquals(returnedPoppedCard, actualPoppedCard);

    EasyMock.verify(drawPile);
  }

  @Test
  public void playerHasCard_IndexNeg1_IndexOutOfBoundsException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    int numPlayers = 3;
    String[] names = {"John", "Jane", "Alice"};
    CardPile playerHand = EasyMock.createMock(CardPile.class);

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);
    Player p3 = EasyMock.createMock(Player.class);

    String expectedMessage = "Player does not exist at this index";

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(playerFactory.createPlayer("Alice", playerHand)).andReturn(p3);
    EasyMock.expect(game.getPlayerByIndex(-1)).andThrow(new IndexOutOfBoundsException(expectedMessage));

    EasyMock.replay(playerFactory, cardPileFactory, game);

    game.setUpPlayers(numPlayers, names);

    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      boolean hasCard = game.playerHasCard(Card.DEFUSE, -1);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(playerFactory, cardPileFactory, game);
  }

  @Test
  public void playerHasCard_IndexZero_returnsTrue() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    int numPlayers = 2;
    String[] names = {"John", "Jane"};
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(game.getPlayerByIndex(0)).andReturn(p1);
    EasyMock.expect(p1.hasCard(Card.SHUFFLE)).andReturn(true);

    EasyMock.replay(playerFactory, cardPileFactory, game, p1, p2);

    game.setUpPlayers(numPlayers, names);

    boolean result = game.playerHasCard(Card.SHUFFLE, 0);
    assertTrue(result);

    EasyMock.verify(playerFactory, cardPileFactory, game, p1, p2);
  }

  @Test
  public void playerHasCard_IndexZero_returnsFalse() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    int numPlayers = 2;
    String[] names = {"John", "Jane"};
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(game.getPlayerByIndex(0)).andReturn(p1);
    EasyMock.expect(p1.hasCard(Card.SKIP)).andReturn(false);

    EasyMock.replay(playerFactory, cardPileFactory, game, p1, p2);

    game.setUpPlayers(numPlayers, names);

    boolean result = game.playerHasCard(Card.SKIP, 0);
    assertFalse(result);

    EasyMock.verify(playerFactory, cardPileFactory, game, p1, p2);
  }

  @Test
  public void playerHasCard_IndexFive_returnsTrue() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    int numPlayers = 6;
    String[] names = {"John", "Jane", "Bob", "Job", "Charlie", "David"};
    CardPile playerHand = EasyMock.createMock(CardPile.class);

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

    EasyMock.expect(game.getPlayerByIndex(5)).andReturn(p6);
    EasyMock.expect(p6.hasCard(Card.ATTACK)).andReturn(true);

    EasyMock.replay(playerFactory, cardPileFactory, game, p1, p2, p3, p4, p5, p6);

    game.setUpPlayers(numPlayers, names);
    boolean result = game.playerHasCard(Card.ATTACK, 5);
    assertTrue(result);

    EasyMock.verify(playerFactory, cardPileFactory, game, p1, p2, p3, p4, p5, p6);
  }

  @Test
  public void playerHasCard_IndexFive_returnsFalse() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    int numPlayers = 6;
    String[] names = {"John", "Jane", "Bob", "Job", "Charlie", "David"};
    CardPile playerHand = EasyMock.createMock(CardPile.class);

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

    EasyMock.expect(game.getPlayerByIndex(5)).andReturn(p6);
    EasyMock.expect(p6.hasCard(Card.BEARD_CAT)).andReturn(false);

    EasyMock.replay(playerFactory, cardPileFactory, game, p1, p2, p3, p4, p5, p6);

    game.setUpPlayers(numPlayers, names);
    boolean result = game.playerHasCard(Card.BEARD_CAT, 5);
    assertFalse(result);

    EasyMock.verify(playerFactory, cardPileFactory, game, p1, p2, p3, p4, p5, p6);
  }

  @Test
  public void playerHasCard_IndexSix_IndexOutOfBoundsException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    int numPlayers = 6;
    String[] names = {"John", "Jane", "Bob", "Job", "Charlie", "David"};
    CardPile playerHand = EasyMock.createMock(CardPile.class);

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);
    Player p3 = EasyMock.createMock(Player.class);
    Player p4 = EasyMock.createMock(Player.class);
    Player p5 = EasyMock.createMock(Player.class);
    Player p6 = EasyMock.createMock(Player.class);

    String expectedMessage = "Player does not exist at this index";

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(playerFactory.createPlayer("Bob", playerHand)).andReturn(p3);
    EasyMock.expect(playerFactory.createPlayer("Job", playerHand)).andReturn(p4);
    EasyMock.expect(playerFactory.createPlayer("Charlie", playerHand)).andReturn(p5);
    EasyMock.expect(playerFactory.createPlayer("David", playerHand)).andReturn(p6);

    EasyMock.expect(game.getPlayerByIndex(6)).andThrow(new IndexOutOfBoundsException(expectedMessage));

    EasyMock.replay(playerFactory, cardPileFactory, game, p1, p2, p3, p4, p5, p6);

    game.setUpPlayers(numPlayers, names);

    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      boolean hasCard = game.playerHasCard(Card.DRAW_FROM_BOTTOM, 6);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
    
    EasyMock.verify(playerFactory, cardPileFactory, game, p1, p2, p3, p4, p5, p6);
  }

  @Test
  public void removeCardFromPlayer_IndexNeg1_IndexOutOfBoundsException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("playerHasCard")
            .createMock();

    EasyMock.expect(game.playerHasCard(Card.DEFUSE, -1)).andThrow(new IndexOutOfBoundsException(("Player does not exist at this index")));

    EasyMock.replay(playerFactory, cardPileFactory, drawPile, game);

    String expectedMessage = "Player does not exist at this index";
    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      game.removeCardFromPlayer(Card.DEFUSE, -1);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(playerFactory, cardPileFactory, game);
  }

  @Test
  public void removeCardFromPlayer_IndexZero_hasCard_RemovesCard() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("playerHasCard")
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    Card card = Card.SKIP;
    int playerIndex = 0;

    int numPlayers = 2;
    String[] names = {"John", "Jane"};
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(game.playerHasCard(card, playerIndex)).andReturn(true);
    EasyMock.expect(game.getPlayerByIndex(playerIndex)).andReturn(p1);
    EasyMock.expect(p1.removeCardFromHand(card)).andReturn(true);

    EasyMock.replay(playerFactory, cardPileFactory, drawPile, game, p1, p2);

    game.setUpPlayers(numPlayers, names);
    game.removeCardFromPlayer(card, playerIndex);

    EasyMock.verify(playerFactory, cardPileFactory, drawPile, game, p1, p2);
  }

  @Test
  public void removeCardFromPlayer_IndexZero_doesNotHaveCard_NoSuchElementException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("playerHasCard")
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    Card card = Card.ATTACK;
    int playerIndex = 0;

    EasyMock.expect(game.playerHasCard(card, playerIndex)).andReturn(false);

    EasyMock.replay(playerFactory, cardPileFactory, drawPile, game);

    String expectedMessage = "Player does not have the specified card";
    Exception exception = assertThrows(NoSuchElementException.class, () -> {
      game.removeCardFromPlayer(card, playerIndex);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(playerFactory, cardPileFactory, drawPile, game);
  }

  @Test
  public void removeCardFromPlayer_IndexFive_hasCard_RemovesCard() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("playerHasCard")
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    Card card = Card.BEARD_CAT;
    int playerIndex = 5;

    int numPlayers = 6;
    String[] names = {"John", "Jane", "Bob", "Job", "Charlie", "David"};
    CardPile playerHand = EasyMock.createMock(CardPile.class);

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
    EasyMock.expect(game.playerHasCard(card, playerIndex)).andReturn(true);
    EasyMock.expect(game.getPlayerByIndex(playerIndex)).andReturn(p6);
    EasyMock.expect(p6.removeCardFromHand(card)).andReturn(true);

    EasyMock.replay(playerFactory, cardPileFactory, drawPile, game, p1, p2, p3, p4, p5, p6);

    game.setUpPlayers(numPlayers, names);
    game.removeCardFromPlayer(card, playerIndex);

    EasyMock.verify(playerFactory, cardPileFactory, drawPile, game, p1, p2, p3, p4, p5, p6);
  }

  @Test
  public void removeCardFromPlayer_IndexSix_IndexOutOfBoundsException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("playerHasCard")
            .createMock();

    Card card = Card.DRAW_FROM_BOTTOM;
    int playerIndex = 6;
    String expectedMessage = "Player does not exist at this index";

    EasyMock.expect(game.playerHasCard(card, playerIndex))
            .andThrow(new IndexOutOfBoundsException(expectedMessage));

    EasyMock.replay(playerFactory, cardPileFactory, drawPile, game);

    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      game.removeCardFromPlayer(card, playerIndex);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(playerFactory, cardPileFactory, drawPile, game);
  }

  @Test
  public void getPlayerByIndex_IndexNeg1_IndexOutOfBoundsException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    int numPlayers = 2;
    String[] names = {"John", "Jane"};
    CardPile playerHand = EasyMock.createMock(CardPile.class);

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);

    EasyMock.replay(playerFactory, cardPileFactory, p1, p2);

    game.setUpPlayers(numPlayers, names);

    String expectedMessage = "Player does not exist at this index";
    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      Player player = game.getPlayerByIndex(-1);
    });
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(playerFactory, cardPileFactory, p1, p2);
  }

  @Test
  public void getPlayerByIndex_IndexZero_ReturnsPlayer() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    int numPlayers = 2;
    String[] names = {"John", "Jane"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);

    EasyMock.replay(playerFactory, cardPileFactory);

    game.setUpPlayers(numPlayers, names);

    Player actualPlayer = game.getPlayerByIndex(0);
    assertEquals(p1, actualPlayer);

    EasyMock.verify(playerFactory, cardPileFactory);
  }

  @Test
  public void getPlayerByIndex_IndexFive_ReturnsPlayer() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    int numPlayers = 6;
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

    EasyMock.replay(playerFactory, cardPileFactory);

    game.setUpPlayers(numPlayers, names);

    Player actualPlayer = game.getPlayerByIndex(5);
    assertEquals(p6, actualPlayer);

    EasyMock.verify(playerFactory, cardPileFactory);
  }

  @Test
  public void getPlayerByIndex_IndexSix_IndexOutOfBoundsException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
		GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    int numPlayers = 6;
    String[] names = {"John", "Jane", "Bob", "Job", "Charlie", "David"};
    CardPile playerHand = EasyMock.createMock(CardPile.class);

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

    EasyMock.replay(playerFactory, cardPileFactory);

    game.setUpPlayers(numPlayers, names);

    String expectedMessage = "Player does not exist at this index";
    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      Player player = game.getPlayerByIndex(6);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(playerFactory, cardPileFactory);
  }

  @Test
  public void discardCard_EmptyDiscardPile() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    Card cardToDiscard = Card.DEFUSE;

    // Expect the CardPile's addCard function to be called with the card to discard
    discardPile.addCard(cardToDiscard);

    EasyMock.replay(discardPile);

    game.discardCard(cardToDiscard);

    EasyMock.verify(discardPile);
  }

  @Test
  public void discardCard_OneCardDiscardPile() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    Card cardToDiscard = Card.SKIP;

    // Expectations for class state
    discardPile.addCard(Card.ATTACK); // setup

    // Expect the CardPile's addCard function to be called with the card to discard
    discardPile.addCard(cardToDiscard);

    EasyMock.replay(discardPile);

    // Set class state based on expectations
    game.discardCard(Card.ATTACK);

    // Actual Test
    game.discardCard(cardToDiscard);

    EasyMock.verify(discardPile);
  }

  @Test
  public void discardCard_TwelveCardsDiscardPile() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    Card cardToDiscard = Card.SHUFFLE;

    Card[] discardPileMock = {
            Card.ATTACK, Card.DEFUSE, Card.SKIP, Card.NOPE,
            Card.ATTACK, Card.DEFUSE, Card.SKIP, Card.NOPE,
            Card.ATTACK, Card.DEFUSE, Card.SKIP, Card.NOPE,
            Card.ATTACK, Card.DEFUSE, Card.SKIP, Card.NOPE,
    };

    // Expectations for class state
    for (Card c : discardPileMock) {
      discardPile.addCard(c);
    }

    // Expect the CardPile's addCard function to be called with the card to discard
    discardPile.addCard(cardToDiscard);

    EasyMock.replay(discardPile);

    // Set class state based on expectations
    for (Card c : discardPileMock) {
      game.discardCard(c);
    }

    // Actual Test
    game.discardCard(cardToDiscard);

    EasyMock.verify(discardPile);
  }

  @ParameterizedTest
  @CsvSource({
          "attack", "skip", "targeted attack", "shuffle",
          "see the future", "reverse", "draw from bottom",
          "alter the future", "invalid", "nope", "rainbow cat",
          "taco cat", "beard cat", "feral cat", "hairy potato cat",
          "exploding kitten", "imploding kitten", "defuse"
  })
  public void getCardByName_allCards(String cardName) {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    Card expectedCard = null;
    switch (cardName) {
      case "attack":
        expectedCard = Card.ATTACK;
        break;
      case "skip":
        expectedCard = Card.SKIP;
        break;
      case "targeted attack":
        expectedCard = Card.TARGETED_ATTACK;
        break;
      case "shuffle":
        expectedCard = Card.SHUFFLE;
        break;
      case "see the future":
        expectedCard = Card.SEE_THE_FUTURE;
        break;
      case "reverse":
        expectedCard = Card.REVERSE;
        break;
      case "draw from bottom":
        expectedCard = Card.DRAW_FROM_BOTTOM;
        break;
      case "alter the future":
        expectedCard = Card.ALTER_THE_FUTURE;
        break;
      case "nope":
        expectedCard = Card.NOPE;
        break;
      case "taco cat":
        expectedCard = Card.TACO_CAT;
        break;
      case "beard cat":
        expectedCard = Card.BEARD_CAT;
        break;
      case "rainbow cat":
        expectedCard = Card.RAINBOW_CAT;
        break;
      case "feral cat":
        expectedCard = Card.FERAL_CAT;
        break;
      case "hairy potato cat":
        expectedCard = Card.HAIRY_POTATO_CAT;
        break;
      case "exploding kitten":
        expectedCard = Card.EXPLODE;
        break;
      case "imploding kitten":
        expectedCard = Card.IMPLODE;
        break;
      case "defuse":
        expectedCard = Card.DEFUSE;
        break;
      default:
        break;
    }
    if (expectedCard == null) {
      Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        game.getCardByName(cardName);
      });
      String expectedMessage = "Could not parse input.";
      String actualMessage = exception.getMessage();
      assertEquals(expectedMessage, actualMessage);
    } else {
      Card actualCard = game.getCardByName(cardName);
      assertEquals(expectedCard, actualCard);
    }
  }

  @Test
  public void validateComboCards_emptyInput_emptyCardList_throwException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getCardByName")
            .createMock();

    int playerIndex = 0;

    String[] cardStrings = new String[0];

    EasyMock.replay(game);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      game.validateComboCards(cardStrings, playerIndex);
    });
    String expectedMessage = "Not a valid combo size.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(game);
  }

  @Test
  public void validateComboCards_singleElementInput_singleElementCardList_throwException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getCardByName")
            .createMock();

    int playerIndex = 0;

    String[] cardStrings = new String[] { "taco cat" };

    EasyMock.replay(game);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      game.validateComboCards(cardStrings, playerIndex);
    });
    String expectedMessage = "Not a valid combo size.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(game);
  }

  @Test
  public void validateComboCards_catCard2x_validCardList_validPlayer_returnCardList() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getCardByName")
            .addMockedMethod("playerHasAtLeastCards")
            .createMock();

    int playerIndex = 0;

    String c1String = "beard cat";
    Card c1 = Card.BEARD_CAT;
    String[] cardStrings = new String[] { c1String, c1String };
    Card[] cards = new Card[] { c1, c1 };

    boolean playerHasC1 = true;

    EasyMock.expect(game.getCardByName(c1String)).andReturn(c1).times(2);
    EasyMock.expect(game.playerHasAtLeastCards(c1, playerIndex, 2)).andReturn(playerHasC1);

    EasyMock.replay(game);

    Card[] actualCards = game.validateComboCards(cardStrings, playerIndex);
    assertArrayEquals(cards, actualCards);

    EasyMock.verify(game);
  }

  @Test
  public void validateComboCards_nonCatCard2x_validCardList_validPlayer_returnCardList() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getCardByName")
            .addMockedMethod("playerHasAtLeastCards")
            .createMock();

    int playerIndex = 0;

    String c1String = "shuffle";
    Card c1 = Card.SHUFFLE;
    String[] cardStrings = new String[] { c1String, c1String };
    Card[] cards = new Card[] { c1, c1 };

    boolean playerHasC1 = true;

    EasyMock.expect(game.getCardByName(c1String)).andReturn(c1).times(2);
    EasyMock.expect(game.playerHasAtLeastCards(c1, playerIndex, 2)).andReturn(playerHasC1);

    EasyMock.replay(game);

    Card[] actualCards = game.validateComboCards(cardStrings, playerIndex);
    assertArrayEquals(cards, actualCards);

    EasyMock.verify(game);
  }

  @Test
  public void validateComboCards_catCard3x_validCardList_validPlayer_returnCardList() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getCardByName")
            .addMockedMethod("playerHasAtLeastCards")
            .createMock();

    int playerIndex = 0;

    String c1String = "rainbow cat";
    Card c1 = Card.RAINBOW_CAT;
    String[] cardStrings = new String[] { c1String, c1String, c1String };
    Card[] cards = new Card[] { c1, c1, c1 };

    boolean playerHasC1 = true;

    EasyMock.expect(game.getCardByName(c1String)).andReturn(c1).times(3);
    EasyMock.expect(game.playerHasAtLeastCards(c1, playerIndex, 3)).andReturn(playerHasC1);

    EasyMock.replay(game);

    Card[] actualCards = game.validateComboCards(cardStrings, playerIndex);
    assertArrayEquals(cards, actualCards);

    EasyMock.verify(game);
  }

  @Test
  public void validateComboCards_nonCatCard3x_validCardList_validPlayer_returnCardList() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getCardByName")
            .addMockedMethod("playerHasAtLeastCards")
            .createMock();

    int playerIndex = 0;

    String c1String = "see the future";
    Card c1 = Card.SEE_THE_FUTURE;
    String[] cardStrings = new String[] { c1String, c1String, c1String };
    Card[] cards = new Card[] { c1, c1, c1 };

    boolean playerHasC1 = true;

    EasyMock.expect(game.getCardByName(c1String)).andReturn(c1).times(3);
    EasyMock.expect(game.playerHasAtLeastCards(c1, playerIndex, 3)).andReturn(playerHasC1);

    EasyMock.replay(game);

    Card[] actualCards = game.validateComboCards(cardStrings, playerIndex);
    assertArrayEquals(cards, actualCards);

    EasyMock.verify(game);
  }

  @Test
  public void validateComboCards_catCard2xWithFeral_validCardList_validPlayer_returnCardList() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getCardByName")
            .addMockedMethod("playerHasAtLeastCards")
            .createMock();

    int playerIndex = 0;

    String c1String = "feral cat";
    Card c1 = Card.FERAL_CAT;
    String c2String = "beard cat";
    Card c2 = Card.BEARD_CAT;
    String[] cardStrings = new String[] { c1String, c2String };
    Card[] cards = new Card[] { c1, c2 };

    boolean playerHasC1 = true;
    boolean playerHasC2 = true;

    EasyMock.expect(game.getCardByName(c1String)).andReturn(c1);
    EasyMock.expect(game.getCardByName(c2String)).andReturn(c2);
    EasyMock.expect(game.playerHasAtLeastCards(c1, playerIndex, 1)).andReturn(playerHasC1);
    EasyMock.expect(game.playerHasAtLeastCards(c2, playerIndex, 1)).andReturn(playerHasC2);

    EasyMock.replay(game);

    Card[] actualCards = game.validateComboCards(cardStrings, playerIndex);
    assertArrayEquals(cards, actualCards);

    EasyMock.verify(game);
  }

  @Test
  public void validateComboCards_catCard3xWith2Feral_validCardList_validPlayer_returnCardList() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getCardByName")
            .addMockedMethod("playerHasAtLeastCards")
            .createMock();

    int playerIndex = 0;

    String c1And3String = "feral cat";
    Card c1And3 = Card.FERAL_CAT;
    String c2String = "beard cat";
    Card c2 = Card.BEARD_CAT;
    String[] cardStrings = new String[] { c1And3String, c2String, c1And3String };
    Card[] cards = new Card[] { c1And3, c2, c1And3 };

    boolean playerHasC1And3 = true;
    boolean playerHasC2 = true;

    EasyMock.expect(game.getCardByName(c1And3String)).andReturn(c1And3);
    EasyMock.expect(game.getCardByName(c2String)).andReturn(c2);
    EasyMock.expect(game.getCardByName(c1And3String)).andReturn(c1And3);
    EasyMock.expect(game.playerHasAtLeastCards(c1And3, playerIndex, 2)).andReturn(playerHasC1And3);
    EasyMock.expect(game.playerHasAtLeastCards(c2, playerIndex, 1)).andReturn(playerHasC2);

    EasyMock.replay(game);

    Card[] actualCards = game.validateComboCards(cardStrings, playerIndex);
    assertArrayEquals(cards, actualCards);

    EasyMock.verify(game);
  }

  @Test
  public void validateComboCards_invalidNonCatCard2x_validCardList_validPlayer_throwException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getCardByName")
            .addMockedMethod("playerHasAtLeastCards")
            .createMock();

    int playerIndex = 0;

    String c1String = "shuffle";
    Card c1 = Card.SHUFFLE;
    String c2String = "attack";
    Card c2 = Card.ATTACK;
    String[] cardStrings = new String[] { c1String, c2String };

    boolean playerHasC1 = true;
    boolean playerHasC2 = true;

    EasyMock.expect(game.getCardByName(c1String)).andReturn(c1);
    EasyMock.expect(game.getCardByName(c2String)).andReturn(c2);
    EasyMock.expect(game.playerHasAtLeastCards(c1, playerIndex, 1)).andReturn(playerHasC1);
    EasyMock.expect(game.playerHasAtLeastCards(c2, playerIndex, 1)).andReturn(playerHasC2);

    EasyMock.replay(game);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      game.validateComboCards(cardStrings, playerIndex);
    });
    String expectedMessage = "Cards must be matching.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(game);
  }

  @Test
  public void validateComboCards_feralAndNonCat2x_validCardList_validPlayer_throwException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getCardByName")
            .addMockedMethod("playerHasAtLeastCards")
            .createMock();

    int playerIndex = 0;

    String c1String = "shuffle";
    Card c1 = Card.SHUFFLE;
    String c2String = "feral cat";
    Card c2 = Card.FERAL_CAT;
    String[] cardStrings = new String[] { c1String, c2String };

    boolean playerHasC1 = true;
    boolean playerHasC2 = true;

    EasyMock.expect(game.getCardByName(c1String)).andReturn(c1);
    EasyMock.expect(game.getCardByName(c2String)).andReturn(c2);
    EasyMock.expect(game.playerHasAtLeastCards(c1, playerIndex, 1)).andReturn(playerHasC1);
    EasyMock.expect(game.playerHasAtLeastCards(c2, playerIndex, 1)).andReturn(playerHasC2);

    EasyMock.replay(game);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      game.validateComboCards(cardStrings, playerIndex);
    });
    String expectedMessage = "Cards must be matching.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(game);
  }

  @Test
  public void validateComboCards_feralAndTwoDifferentCats_validCardList_validPlayer_throwException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getCardByName")
            .addMockedMethod("playerHasAtLeastCards")
            .createMock();

    int playerIndex = 0;

    String c1String = "feral cat";
    Card c1 = Card.FERAL_CAT;
    String c2String = "taco cat";
    Card c2 = Card.TACO_CAT;
    String c3String = "hairy potato cat";
    Card c3 = Card.HAIRY_POTATO_CAT;
    String[] cardStrings = new String[] { c1String, c2String, c3String };

    boolean playerHasC1 = true;
    boolean playerHasC2 = true;
    boolean playerHasC3 = true;

    EasyMock.expect(game.getCardByName(c1String)).andReturn(c1);
    EasyMock.expect(game.getCardByName(c2String)).andReturn(c2);
    EasyMock.expect(game.getCardByName(c3String)).andReturn(c3);
    EasyMock.expect(game.playerHasAtLeastCards(c1, playerIndex, 1)).andReturn(playerHasC1);
    EasyMock.expect(game.playerHasAtLeastCards(c2, playerIndex, 1)).andReturn(playerHasC2);
    EasyMock.expect(game.playerHasAtLeastCards(c3, playerIndex, 1)).andReturn(playerHasC3);

    EasyMock.replay(game);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      game.validateComboCards(cardStrings, playerIndex);
    });
    String expectedMessage = "Cat cards must be matching or feral.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(game);
  }

  @Test
  public void validateComboCards_4x_validCardList_validPlayer_throwException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getCardByName")
            .addMockedMethod("playerHasAtLeastCards")
            .createMock();

    int playerIndex = 0;

    String c1String = "feral cat";
    String c2String = "taco cat";
    String c3String = "hairy potato cat";
    String c4String = "shuffle";
    String[] cardStrings = new String[] { c1String, c2String, c3String, c4String };

    EasyMock.replay(game);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      game.validateComboCards(cardStrings, playerIndex);
    });
    String expectedMessage = "Not a valid combo size.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(game);
  }

  @Test
  public void validateComboCards_validCardStrings_validCardList_invalidPlayer_throwException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getCardByName")
            .addMockedMethod("playerHasAtLeastCards")
            .createMock();

    int playerIndex = 0;

    String c1String = "feral cat";
    Card c1 = Card.FERAL_CAT;
    String c2String = "taco cat";
    Card c2 = Card.TACO_CAT;
    String[] cardStrings = new String[] { c1String, c2String };

    EasyMock.expect(game.getCardByName(c1String)).andReturn(c1);
    EasyMock.expect(game.getCardByName(c2String)).andReturn(c2);

    String exceptionMessage = "Player does not exist at this index";

    // Allow any of the cards to trigger the exception
    EasyMock.expect(game.playerHasAtLeastCards(EasyMock.or(
                    EasyMock.eq(c1),
                    EasyMock.eq(c2)
            ), EasyMock.eq(playerIndex), EasyMock.eq(1)))
            .andThrow(new IndexOutOfBoundsException(exceptionMessage));

    EasyMock.replay(game);

    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      game.validateComboCards(cardStrings, playerIndex);
    });
    String actualMessage = exception.getMessage();
    assertEquals(exceptionMessage, actualMessage);

    EasyMock.verify(game);
  }

  @Test
  public void validateComboCards_3xFeral_validCardList_validPlayer_returnCardList() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getCardByName")
            .addMockedMethod("playerHasAtLeastCards")
            .createMock();

    int playerIndex = 0;

    String c1String = "feral cat";
    Card c1 = Card.FERAL_CAT;
    String[] cardStrings = new String[] { c1String, c1String, c1String };
    Card[] cards = new Card[] { c1, c1, c1 };

    boolean playerHasC1 = true;

    EasyMock.expect(game.getCardByName(c1String)).andReturn(c1).times(3);
    EasyMock.expect(game.playerHasAtLeastCards(c1, playerIndex, 3)).andReturn(playerHasC1);

    EasyMock.replay(game);

    Card[] actualCards = game.validateComboCards(cardStrings, playerIndex);
    assertArrayEquals(cards, actualCards);

    EasyMock.verify(game);
  }

  @Test
  public void validateComboCards_validCardStrings_validCardList_playerDoesNotHaveCards_throwException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getCardByName")
            .addMockedMethod("playerHasAtLeastCards")
            .createMock();

    int playerIndex = 0;

    String c1String = "beard cat";
    Card c1 = Card.BEARD_CAT;
    String[] cardStrings = new String[]{c1String, c1String};

    EasyMock.expect(game.getCardByName(c1String)).andReturn(c1).times(2);

    boolean playerHasCards = false;
    // Allow any of the cards to trigger the exception
    EasyMock.expect(game.playerHasAtLeastCards(c1, playerIndex, 2)).andReturn(playerHasCards);

    EasyMock.replay(game);


    String expectedExceptionMessage = "Player does not have the input cards.";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      game.validateComboCards(cardStrings, playerIndex);
    });
    String actualMessage = exception.getMessage();
    assertEquals(expectedExceptionMessage, actualMessage);

    EasyMock.verify(game);
  }

  @Test
  public void getPlayerIndexByName_validName_mulitplePlayers_ReturnsPlayerIndex() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    Player john = EasyMock.createMock(Player.class);
    Player jane = EasyMock.createMock(Player.class);

    int numPlayers = 2;
    String[] names = {"John", "Jane"};

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(null).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", null)).andReturn(john);
    EasyMock.expect(playerFactory.createPlayer("Jane", null)).andReturn(jane);

    // Expectations for player names
    EasyMock.expect(john.getName()).andReturn("John").anyTimes();
    EasyMock.expect(jane.getName()).andReturn("Jane").anyTimes();

    EasyMock.replay(playerFactory, cardPileFactory, drawPile, john, jane);

    // Set up players
    game.setUpPlayers(numPlayers, names);

    int expectedIndex = 0;
    int actualIndex = game.getPlayerIndexByName("John");

    assertEquals(expectedIndex, actualIndex);

    EasyMock.verify(playerFactory, cardPileFactory, drawPile, john, jane);
  }

  @Test
  public void getPlayerIndexByName_invalidPlayerName_multiplePlayers_ThrowsException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    Player john = EasyMock.createMock(Player.class);
    Player jane = EasyMock.createMock(Player.class);
    Player smith = EasyMock.createMock(Player.class);

    int numPlayers = 3;
    String[] names = {"John", "Jane", "Smith"};

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(null).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", null)).andReturn(john);
    EasyMock.expect(playerFactory.createPlayer("Jane", null)).andReturn(jane);
    EasyMock.expect(playerFactory.createPlayer("Smith", null)).andReturn(smith);

    // Expectations for player names
    EasyMock.expect(john.getName()).andReturn("John").anyTimes();
    EasyMock.expect(jane.getName()).andReturn("Jane").anyTimes();
    EasyMock.expect(smith.getName()).andReturn("Smith").anyTimes();

    EasyMock.replay(playerFactory, cardPileFactory, drawPile, john, jane, smith);

    // Set up players
    game.setUpPlayers(numPlayers, names);

    String expectedMessage = "No player with that name could be found.";
    Exception exception = assertThrows(NoSuchElementException.class, () -> {
      game.getPlayerIndexByName("Brennan");
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);


    EasyMock.verify(playerFactory, cardPileFactory, drawPile, john, jane, smith);
  }

  @Test
  public void getPlayerIndexByName_emptyStringName_maxPlayers_ThrowsException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    Player john = EasyMock.createMock(Player.class);
    Player jane = EasyMock.createMock(Player.class);
    Player smith = EasyMock.createMock(Player.class);
    Player foo = EasyMock.createMock(Player.class);
    Player bar = EasyMock.createMock(Player.class);
    Player baz = EasyMock.createMock(Player.class);

    int numPlayers = 6;
    String[] names = {"John", "Jane", "Smith", "Foo", "Bar", "Baz"};

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(null).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", null)).andReturn(john);
    EasyMock.expect(playerFactory.createPlayer("Jane", null)).andReturn(jane);
    EasyMock.expect(playerFactory.createPlayer("Smith", null)).andReturn(smith);
    EasyMock.expect(playerFactory.createPlayer("Foo", null)).andReturn(foo);
    EasyMock.expect(playerFactory.createPlayer("Bar", null)).andReturn(bar);
    EasyMock.expect(playerFactory.createPlayer("Baz", null)).andReturn(baz);

    // Expectations for player names
    EasyMock.expect(john.getName()).andReturn("John").anyTimes();
    EasyMock.expect(jane.getName()).andReturn("Jane").anyTimes();
    EasyMock.expect(smith.getName()).andReturn("Smith").anyTimes();
    EasyMock.expect(foo.getName()).andReturn("Foo").anyTimes();
    EasyMock.expect(bar.getName()).andReturn("Bar").anyTimes();
    EasyMock.expect(baz.getName()).andReturn("Baz").anyTimes();

    EasyMock.replay(playerFactory, cardPileFactory, drawPile, john, jane, smith, foo, bar, baz);

    // Set up players
    game.setUpPlayers(numPlayers, names);

    String expectedMessage = "No player with that name could be found.";
    Exception exception = assertThrows(NoSuchElementException.class, () -> {
      game.getPlayerIndexByName("");
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);


    EasyMock.verify(playerFactory, cardPileFactory, drawPile, john, jane, smith, foo, bar, baz);
  }

  @Test
  public void getPlayerIndexByName_validPlayerName_maxPlayers_returnsIndex() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    Player john = EasyMock.createMock(Player.class);
    Player jane = EasyMock.createMock(Player.class);
    Player smith = EasyMock.createMock(Player.class);
    Player foo = EasyMock.createMock(Player.class);
    Player bar = EasyMock.createMock(Player.class);
    Player baz = EasyMock.createMock(Player.class);

    int numPlayers = 6;
    String[] names = {"John", "Jane", "Smith", "Foo", "Bar", "Baz"};

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(null).times(numPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", null)).andReturn(john);
    EasyMock.expect(playerFactory.createPlayer("Jane", null)).andReturn(jane);
    EasyMock.expect(playerFactory.createPlayer("Smith", null)).andReturn(smith);
    EasyMock.expect(playerFactory.createPlayer("Foo", null)).andReturn(foo);
    EasyMock.expect(playerFactory.createPlayer("Bar", null)).andReturn(bar);
    EasyMock.expect(playerFactory.createPlayer("Baz", null)).andReturn(baz);

    // Expectations for player names
    EasyMock.expect(john.getName()).andReturn("John").anyTimes();
    EasyMock.expect(jane.getName()).andReturn("Jane").anyTimes();
    EasyMock.expect(smith.getName()).andReturn("Smith").anyTimes();
    EasyMock.expect(foo.getName()).andReturn("Foo").anyTimes();
    EasyMock.expect(bar.getName()).andReturn("Bar").anyTimes();
    EasyMock.expect(baz.getName()).andReturn("Baz").anyTimes();

    EasyMock.replay(playerFactory, cardPileFactory, drawPile, john, jane, smith, foo, bar, baz);

    // Set up players
    game.setUpPlayers(numPlayers, names);

    int expectedIndex = 5;
    int actualIndex = game.getPlayerIndexByName("Baz");

    assertEquals(expectedIndex, actualIndex);

    EasyMock.verify(playerFactory, cardPileFactory, drawPile, john, jane, smith, foo, bar, baz);
  }

  @Test
  public void eliminatePlayer_NegOne_ThrowsException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    int playerIndex = -1;

    String expectedMessage = "Player does not exist at this index";
    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      game.eliminatePlayer(playerIndex);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void eliminatePlayer_Zero_RemovesPlayer() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    // Class state set up
    int numOfPlayers = 2;
    String[] names = {"John", "Jane"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numOfPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);

    EasyMock.replay(playerFactory, cardPileFactory, playerHand, p1, p2);

    game.setUpPlayers(numOfPlayers, names);

    // Test Input
    int playerIndex = 0;

    // Expected values
    int expectedNumOfPlayers = 1;

    // Function call
    game.eliminatePlayer(playerIndex);

    // Actual values
    int actualNumOfPlayers = game.numOfPlayers;
    List<Player> players = game.getPlayers();

    // Assertions
    assertEquals(expectedNumOfPlayers, actualNumOfPlayers);
    assertEquals(expectedNumOfPlayers, players.size());
    assertFalse(players.contains(p1));
    assertTrue(players.contains(p2));

    // Verification
    EasyMock.verify(playerFactory, cardPileFactory, playerHand, p1, p2);
  }

  @Test
  public void eliminatePlayer_Five_RemovesPlayer() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    // Class state set up
    int numOfPlayers = 6;
    String[] names = {"John", "Jane", "Alice", "Bob", "Charlie", "David"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);
    Player p3 = EasyMock.createMock(Player.class);
    Player p4 = EasyMock.createMock(Player.class);
    Player p5 = EasyMock.createMock(Player.class);
    Player p6 = EasyMock.createMock(Player.class);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numOfPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(playerFactory.createPlayer("Alice", playerHand)).andReturn(p3);
    EasyMock.expect(playerFactory.createPlayer("Bob", playerHand)).andReturn(p4);
    EasyMock.expect(playerFactory.createPlayer("Charlie", playerHand)).andReturn(p5);
    EasyMock.expect(playerFactory.createPlayer("David", playerHand)).andReturn(p6);

    EasyMock.replay(playerFactory, cardPileFactory, playerHand, p1, p2, p3, p4, p5, p6);

    game.setUpPlayers(numOfPlayers, names);

    // Test Input
    int playerIndex = 5;

    // Expected values
    int expectedNumOfPlayers = 5;

    // Function call
    game.eliminatePlayer(playerIndex);

    // Actual values
    int actualNumOfPlayers = game.numOfPlayers;
    List<Player> players = game.getPlayers();

    // Assertions
    assertEquals(expectedNumOfPlayers, actualNumOfPlayers);
    assertEquals(expectedNumOfPlayers, players.size());
    assertFalse(players.contains(p6));
    assertTrue(players.contains(p1));
    assertTrue(players.contains(p2));
    assertTrue(players.contains(p3));
    assertTrue(players.contains(p4));
    assertTrue(players.contains(p5));

    // Verification
    EasyMock.verify(playerFactory, cardPileFactory, playerHand, p1, p2, p3, p4, p5, p6);
  }

  @Test
  public void eliminatePlayer_Six_ThrowsException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    CardPile playerHand = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    // Class state set up
    int numOfPlayers = 6;
    String[] names = {"John", "Jane", "Alice", "Bob", "Charlie", "David"};

    Player p1 = EasyMock.createMock(Player.class);
    Player p2 = EasyMock.createMock(Player.class);
    Player p3 = EasyMock.createMock(Player.class);
    Player p4 = EasyMock.createMock(Player.class);
    Player p5 = EasyMock.createMock(Player.class);
    Player p6 = EasyMock.createMock(Player.class);

    EasyMock.expect(cardPileFactory.createCardPile()).andReturn(playerHand).times(numOfPlayers);
    EasyMock.expect(playerFactory.createPlayer("John", playerHand)).andReturn(p1);
    EasyMock.expect(playerFactory.createPlayer("Jane", playerHand)).andReturn(p2);
    EasyMock.expect(playerFactory.createPlayer("Alice", playerHand)).andReturn(p3);
    EasyMock.expect(playerFactory.createPlayer("Bob", playerHand)).andReturn(p4);
    EasyMock.expect(playerFactory.createPlayer("Charlie", playerHand)).andReturn(p5);
    EasyMock.expect(playerFactory.createPlayer("David", playerHand)).andReturn(p6);

    EasyMock.replay(playerFactory, cardPileFactory, playerHand, p1, p2, p3, p4, p5, p6);

    game.setUpPlayers(numOfPlayers, names);

    // Test Input
    int playerIndex = 6;

    // Expected values
    String expectedMessage = "Player does not exist at this index";

    // Function call
    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      game.eliminatePlayer(playerIndex);
    });

    // Actual values
    List<Player> players = game.getPlayers();
    String actualMessage = exception.getMessage();

    // Assertions
    assertEquals(expectedMessage, actualMessage);
    assertTrue(players.contains(p1));
    assertTrue(players.contains(p2));
    assertTrue(players.contains(p3));
    assertTrue(players.contains(p4));
    assertTrue(players.contains(p5));
    assertTrue(players.contains(p6));

    // Verification
    EasyMock.verify(playerFactory, cardPileFactory, playerHand, p1, p2, p3, p4, p5, p6);
  }

  @Test
  public void playerHasAtLeastCards_playerIndexOutOfRange_throwsException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    Card cardToGet = Card.DEFUSE;
    int numCards = 1;
    int outOfRangePlayerIndex = -1;
    String exceptionMessage = "Player does not exist at this index";
    EasyMock.expect(game.getPlayerByIndex(outOfRangePlayerIndex)).andThrow(
            new IndexOutOfBoundsException(exceptionMessage)
    );

    EasyMock.replay(game);

    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      game.playerHasAtLeastCards(cardToGet, outOfRangePlayerIndex, numCards);
    });
    String actualMessage = exception.getMessage();
    assertEquals(exceptionMessage, actualMessage);

    EasyMock.verify(game);
  }

  @Test
  public void playerHasAtLeastCards_numCardsIs0_throwsException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    Card cardToGet = Card.SHUFFLE;
    int numCards = 0;
    int playerIndex = 0;
    Player player = EasyMock.createMock(Player.class);
    EasyMock.expect(game.getPlayerByIndex(playerIndex)).andReturn(player);

    EasyMock.replay(game);

    String expectedMessage = "Number of cards must be greater than 0.";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      game.playerHasAtLeastCards(cardToGet, playerIndex, numCards);
    });
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(game);
  }

  @Test
  public void playerHasAtLeastCards_singleCard_hasCard_returnTrue() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    Card cardToGet = Card.ATTACK;
    int numCards = 1;
    int playerIndex = 0;
    Player player = EasyMock.createMock(Player.class);
    EasyMock.expect(game.getPlayerByIndex(playerIndex)).andReturn(player);

    Card[] playerHand = { cardToGet };
    EasyMock.expect(player.getHand()).andReturn(playerHand);

    EasyMock.replay(game, player);

    boolean expectedResult = true;
    boolean actualResult = game.playerHasAtLeastCards(cardToGet, playerIndex, numCards);

    assertEquals(expectedResult, actualResult);

    EasyMock.verify(game, player);
  }

  @Test
  public void playerHasAtLeastCards_singleCard_doesNotHaveCard_returnFalse() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    Card cardToGet = Card.BEARD_CAT;
    int numCards = Integer.MAX_VALUE;
    int playerIndex = 5;
    Player player = EasyMock.createMock(Player.class);
    EasyMock.expect(game.getPlayerByIndex(playerIndex)).andReturn(player);

    Card otherCard = Card.DEFUSE;
    Card[] playerHand = { otherCard };
    EasyMock.expect(player.getHand()).andReturn(playerHand);

    EasyMock.replay(game, player);

    boolean expectedResult = false;
    boolean actualResult = game.playerHasAtLeastCards(cardToGet, playerIndex, numCards);

    assertEquals(expectedResult, actualResult);

    EasyMock.verify(game, player);
  }

  @Test
  public void playerHasAtLeastCards_playerDoesNotExistAtIndex_throwsException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    Card cardToGet = Card.DEFUSE;
    int numCards = 1;
    int outOfRangePlayerIndex = 4;
    String exceptionMessage = "Player does not exist at this index";
    EasyMock.expect(game.getPlayerByIndex(outOfRangePlayerIndex)).andThrow(
            new IndexOutOfBoundsException(exceptionMessage)
    );

    EasyMock.replay(game);

    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      game.playerHasAtLeastCards(cardToGet, outOfRangePlayerIndex, numCards);
    });
    String actualMessage = exception.getMessage();
    assertEquals(exceptionMessage, actualMessage);

    EasyMock.verify(game);
  }

  @Test
  public void playerHasAtLeastCards_indexOutOfRangeUpper_throwsException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    Card cardToGet = Card.DRAW_FROM_BOTTOM;
    int numCards = 1;
    int outOfRangePlayerIndex = 6;
    String exceptionMessage = "Player does not exist at this index";
    EasyMock.expect(game.getPlayerByIndex(outOfRangePlayerIndex)).andThrow(
            new IndexOutOfBoundsException(exceptionMessage)
    );

    EasyMock.replay(game);

    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      game.playerHasAtLeastCards(cardToGet, outOfRangePlayerIndex, numCards);
    });
    String actualMessage = exception.getMessage();
    assertEquals(exceptionMessage, actualMessage);

    EasyMock.verify(game);
  }

  @Test
  public void playerHasAtLeastCards_2Cards_onlyHasOne_returnFalse() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    Card cardToGet = Card.SHUFFLE;
    int numCards = 2;
    int playerIndex = 2;
    Player player = EasyMock.createMock(Player.class);
    EasyMock.expect(game.getPlayerByIndex(playerIndex)).andReturn(player);

    Card[] playerHand = { cardToGet };
    EasyMock.expect(player.getHand()).andReturn(playerHand);

    EasyMock.replay(game, player);

    boolean expectedResult = false;
    boolean actualResult = game.playerHasAtLeastCards(cardToGet, playerIndex, numCards);

    assertEquals(expectedResult, actualResult);

    EasyMock.verify(game, player);
  }

  @Test
  public void playerHasAtLeastCards_1Cards_has2_returnTrue() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    Card cardToGet = Card.ATTACK;
    int numCards = 1;
    int playerIndex = 2;
    Player player = EasyMock.createMock(Player.class);
    EasyMock.expect(game.getPlayerByIndex(playerIndex)).andReturn(player);

    Card[] playerHand = { cardToGet, cardToGet };
    EasyMock.expect(player.getHand()).andReturn(playerHand);

    EasyMock.replay(game, player);

    boolean expectedResult = true;
    boolean actualResult = game.playerHasAtLeastCards(cardToGet, playerIndex, numCards);

    assertEquals(expectedResult, actualResult);

    EasyMock.verify(game, player);
  }

  @Test
  public void playerHasAtLeastCards_3Cards_has3PlusOthers_returnTrue() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    Card cardToGet = Card.RAINBOW_CAT;
    int numCards = 3;
    int playerIndex = 2;
    Player player = EasyMock.createMock(Player.class);
    EasyMock.expect(game.getPlayerByIndex(playerIndex)).andReturn(player);

    Card[] playerHand = { Card.ATTACK, cardToGet, Card.SHUFFLE, cardToGet, cardToGet, Card.ATTACK };
    EasyMock.expect(player.getHand()).andReturn(playerHand);

    EasyMock.replay(game, player);

    boolean expectedResult = true;
    boolean actualResult = game.playerHasAtLeastCards(cardToGet, playerIndex, numCards);

    assertEquals(expectedResult, actualResult);

    EasyMock.verify(game, player);
  }

  @Test
  public void playerHasAtLeastCards_1Card_hasNone_returnFalse() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    Card cardToGet = Card.DEFUSE;
    int numCards = 1;
    int playerIndex = 2;
    Player player = EasyMock.createMock(Player.class);
    EasyMock.expect(game.getPlayerByIndex(playerIndex)).andReturn(player);

    Card[] playerHand = new Card[0];
    EasyMock.expect(player.getHand()).andReturn(playerHand);

    EasyMock.replay(game, player);

    boolean expectedResult = false;
    boolean actualResult = game.playerHasAtLeastCards(cardToGet, playerIndex, numCards);

    assertEquals(expectedResult, actualResult);

    EasyMock.verify(game, player);
  }

  @Test
  public void playerHasAtLeastCards_differentCardInHand_returnsFalse() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = EasyMock.partialMockBuilder(GameEngine.class)
            .withConstructor(playerFactory, cardPileFactory, drawPile, discardPile)
            .addMockedMethod("getPlayerByIndex")
            .createMock();

    Card cardToGet = Card.BEARD_CAT;
    int numCards = 1;
    int playerIndex = 2;
    Player player = EasyMock.createMock(Player.class);
    EasyMock.expect(game.getPlayerByIndex(playerIndex)).andReturn(player);

    // Player hand has a card, but not the requested one
    Card[] playerHand = { Card.DEFUSE };
    EasyMock.expect(player.getHand()).andReturn(playerHand);

    EasyMock.replay(game, player);

    boolean expectedResult = false;
    boolean actualResult = game.playerHasAtLeastCards(cardToGet, playerIndex, numCards);

    assertEquals(expectedResult, actualResult);

    EasyMock.verify(game, player);
  }

  @Test
  public void addCardToDrawPileAt_NegOne_ThrowsException() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    Card cardToAdd = Card.DEFUSE;
    int pileIndex = -1;

    String expectedMessage = "Cannot insert a card out of bounds";

    drawPile.addCardAt(cardToAdd, pileIndex);
    EasyMock.expectLastCall().andThrow(new IndexOutOfBoundsException(expectedMessage));

    EasyMock.replay(drawPile);

    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      game.addCardToDrawPileAt(cardToAdd, pileIndex);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);

    EasyMock.verify(drawPile);
  }

  @Test
  public void addCardToDrawPileAt_Zero_AddsCard() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    CardPile drawPile = EasyMock.createMock(CardPile.class);
    CardPile discardPile = EasyMock.createMock(CardPile.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory, drawPile, discardPile);

    Card cardToAdd = Card.SHUFFLE;
    int pileIndex = 0;

    drawPile.addCardAt(cardToAdd, pileIndex);
    EasyMock.expectLastCall();

    EasyMock.replay(drawPile);

    game.addCardToDrawPileAt(cardToAdd, pileIndex);

    EasyMock.verify(drawPile);
  }

  @Test
  public void isGameOver_onePlayer_returnTrue() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory);

    int numPlayers = 1;
    game.numOfPlayers = numPlayers;

    boolean expectedResult = true;
    boolean actualResult = game.isGameOver();

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void isGameOver_noPlayers_returnTrue() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory);

    int numPlayers = 0;
    game.numOfPlayers = numPlayers;

    boolean expectedResult = true;
    boolean actualResult = game.isGameOver();

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void isGameOver_twoPlayers_returnFalse() {
    PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);
    GameEngine game = new GameEngine(playerFactory, cardPileFactory);

    int numPlayers = 2;
    game.numOfPlayers = numPlayers;

    boolean expectedResult = false;
    boolean actualResult = game.isGameOver();

    assertEquals(expectedResult, actualResult);
  }
}

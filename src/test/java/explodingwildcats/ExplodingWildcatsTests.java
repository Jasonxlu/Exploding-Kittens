package explodingwildcats;

import org.easymock.EasyMock;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ExplodingWildcatsTests {
    @Test
    public void setUpPlayers_OnePlayerOneName_ThrowException() {
        PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
        DrawPile drawPile = EasyMock.createMock(DrawPile.class);
        GameEngine game = new GameEngine(playerFactory, drawPile);

        int numPlayers = 1;
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
        DrawPile drawPile = EasyMock.createMock(DrawPile.class);
        GameEngine game = new GameEngine(playerFactory, drawPile);

        int numPlayers = 1;
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
        DrawPile drawPile = EasyMock.createMock(DrawPile.class);
        GameEngine game = new GameEngine(playerFactory, drawPile);
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);

        int numPlayers = 2;
        String[] names = {"John", "Jane"};

        EasyMock.expect(playerFactory.createPlayer("John")).andReturn(p1);
        EasyMock.expect(playerFactory.createPlayer("Jane")).andReturn(p2);

        EasyMock.replay(playerFactory);

        game.setUpPlayers(numPlayers, names);

        int expectedNumPlayers = 2;
        int actualNumPlayers = game.getNumberOfPlayers();
        assertEquals(expectedNumPlayers, actualNumPlayers);

        Player[] players = game.getPlayers();
        assertEquals(numPlayers, players.length);

        EasyMock.verify(playerFactory);
    }

    @Test
    public void setUpPlayers_MaxPlayersMaxNames() {
        PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
        DrawPile drawPile = EasyMock.createMock(DrawPile.class);
        GameEngine game = new GameEngine(playerFactory, drawPile);

        int numPlayers = 4;
        String[] names = {"John", "Jane", "Alice", "Bob"};

        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Player p3 = EasyMock.createMock(Player.class);
        Player p4 = EasyMock.createMock(Player.class);
        EasyMock.expect(playerFactory.createPlayer("John")).andReturn(p1);
        EasyMock.expect(playerFactory.createPlayer("Jane")).andReturn(p2);
        EasyMock.expect(playerFactory.createPlayer("Alice")).andReturn(p3);
        EasyMock.expect(playerFactory.createPlayer("Bob")).andReturn(p4);

        EasyMock.replay(playerFactory);

        game.setUpPlayers(numPlayers, names);

        int expectedNumPlayers = 4;
        int actualNumPlayers = game.getNumberOfPlayers();
        assertEquals(expectedNumPlayers, actualNumPlayers);

        Player[] players = game.getPlayers();
        assertEquals(numPlayers, players.length);

        EasyMock.verify(playerFactory);
    }

    @Test
    public void setUpPlayers_TooManyPlayers_ThrowException() {
        PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
        DrawPile drawPile = EasyMock.createMock(DrawPile.class);
        GameEngine game = new GameEngine(playerFactory, drawPile);

        EasyMock.replay(playerFactory);

        int numPlayers = 5;
        String[] names = {"John", "Jane", "Alice", "Bob", "Charlie"};

        String expectedMessage = "Too many players";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            game.setUpPlayers(numPlayers, names);
        });

        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        EasyMock.verify(playerFactory);
    }

    @Test
    public void dealDefuses_TwoPlayers() {
        PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
        DrawPile drawPile = EasyMock.createMock(DrawPile.class);
        GameEngine game = new GameEngine(playerFactory, drawPile);

        int numPlayers = 2;
        String[] names = {"John", "Jane"};
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        p1.AddCardToHand(Card.DEFUSE);
        EasyMock.expectLastCall();
        p2.AddCardToHand(Card.DEFUSE);
        EasyMock.expectLastCall();
        drawPile.AddCard(Card.DEFUSE);
        EasyMock.expectLastCall();
        drawPile.AddCard(Card.DEFUSE);
        EasyMock.expectLastCall();
        drawPile.AddCard(Card.DEFUSE);
        EasyMock.expectLastCall();

        EasyMock.expect(playerFactory.createPlayer("John")).andStubReturn(p1);
        EasyMock.expect(playerFactory.createPlayer("Jane")).andStubReturn(p2);


        Card[] cards = new Card[37];
        for (int i = 0; i < 34; i++) {
            cards[i] = Card.SKIP;
        }
        cards[34] = Card.DEFUSE;
        cards[35] = Card.DEFUSE;
        cards[36] = Card.DEFUSE;
        EasyMock.expect(drawPile.getCards()).andStubReturn(cards);

        EasyMock.replay(playerFactory, p1, p2, drawPile);

        game.setUpPlayers(numPlayers, names);
        game.dealDefuses();
        Card[] actualDrawPile = game.getDrawPile();

        EasyMock.verify(playerFactory, p1, p2, drawPile);

        // test that remaining defuses were inserted into draw pile
        int expectedNumDefusesInDrawPile = 3;
        int expectedDrawPileLength = 37;


        assertEquals(expectedDrawPileLength, actualDrawPile.length);

        int actualNumDefusesInDrawPile = 0;
        for (Card card : actualDrawPile) {
            if (card == Card.DEFUSE) {
                actualNumDefusesInDrawPile++;
            }
        }
        assertEquals(expectedNumDefusesInDrawPile, actualNumDefusesInDrawPile);
    }

    @Test
    public void dealDefuses_MaxPlayers() {
        PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
        DrawPile drawPile = EasyMock.createMock(DrawPile.class);
        GameEngine game = new GameEngine(playerFactory, drawPile);

        int numPlayers = 4;
        String[] names = {"John", "Jane", "Alice", "Bob"};

        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Player p3 = EasyMock.createMock(Player.class);
        Player p4 = EasyMock.createMock(Player.class);
        p1.AddCardToHand(Card.DEFUSE);
        EasyMock.expectLastCall();
        p2.AddCardToHand(Card.DEFUSE);
        EasyMock.expectLastCall();
        p3.AddCardToHand(Card.DEFUSE);
        EasyMock.expectLastCall();
        p4.AddCardToHand(Card.DEFUSE);
        EasyMock.expectLastCall();
        drawPile.AddCard(Card.DEFUSE);
        EasyMock.expectLastCall();

        EasyMock.expect(playerFactory.createPlayer("John")).andReturn(p1);
        EasyMock.expect(playerFactory.createPlayer("Jane")).andReturn(p2);
        EasyMock.expect(playerFactory.createPlayer("Alice")).andReturn(p3);
        EasyMock.expect(playerFactory.createPlayer("Bob")).andReturn(p4);

        Card[] cards = new Card[35];
        for (int i = 0; i < 34; i++) {
            cards[i] = Card.SKIP;
        }
        cards[34] = Card.DEFUSE;
        EasyMock.expect(drawPile.getCards()).andStubReturn(cards);

        EasyMock.replay(playerFactory, p1, p2, p3, p4, drawPile);

        game.setUpPlayers(numPlayers, names);
        game.dealDefuses();
        Card[] actualDrawPile = game.getDrawPile();

        EasyMock.verify(playerFactory, p1, p2, p3, p4,drawPile);

        // test that remaining defuses were inserted into draw pile
        int expectedNumDefusesInDrawPile = 1;
        int expectedDrawPileLength = 35;

        assertEquals(expectedDrawPileLength, actualDrawPile.length);

        int actualNumDefusesInDrawPile = 0;
        for (Card card : actualDrawPile) {
            if (card == Card.DEFUSE) {
                actualNumDefusesInDrawPile++;
            }
        }
        assertEquals(expectedNumDefusesInDrawPile, actualNumDefusesInDrawPile);
    }

    @Test
    public void dealCards_TwoPlayers_CorrectHandsAndPiles() {
        PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
        DrawPile drawPile = EasyMock.createMock(DrawPile.class);
        GameEngine game = new GameEngine(playerFactory, drawPile);

        int numPlayers = 2;
        String[] names = {"John", "Jane"};

        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);

        EasyMock.expect(playerFactory.createPlayer("John")).andReturn(p1);
        EasyMock.expect(playerFactory.createPlayer("Jane")).andReturn(p2);

        p1.AddCardToHand(Card.DEFUSE);
        p2.AddCardToHand(Card.DEFUSE);

        // Expect 3 since there are 2 players
        drawPile.AddCard(Card.DEFUSE);
        drawPile.AddCard(Card.DEFUSE);
        drawPile.AddCard(Card.DEFUSE);

        for(int x = 0; x < 5; x++) {
            EasyMock.expect(drawPile.popCard()).andStubReturn(Card.SKIP);
            p1.AddCardToHand(EasyMock.anyObject(Card.class));

            EasyMock.expect(drawPile.popCard()).andStubReturn(Card.SKIP);
            p2.AddCardToHand(EasyMock.anyObject(Card.class));
        }

        int expectedDrawPileLength = 27;
        int expectedPlayerHandSize = 6;

        // Create arrays based on the expected size
        Card[] p1ExpectedCards = new Card[expectedPlayerHandSize];
        Card[] p2ExpectedCards = new Card[expectedPlayerHandSize];
        Card[] remainingCards = new Card[expectedDrawPileLength ];

        // Populate the arrays with expected card types or nulls (if you just need size)
        Arrays.fill(p2ExpectedCards, Card.SKIP);
        Arrays.fill(p1ExpectedCards, Card.SKIP);
        Arrays.fill(remainingCards, Card.SKIP);

        // Set up the expectations
        EasyMock.expect(drawPile.getCards()).andReturn(remainingCards);
        EasyMock.expect(p1.getHand()).andReturn(p1ExpectedCards);
        EasyMock.expect(p2.getHand()).andReturn(p2ExpectedCards);

        EasyMock.replay(playerFactory, p1, p2, drawPile);

        game.setUpPlayers(numPlayers, names);
        game.dealDefuses();
        game.dealCards();
        Card[] actualDrawPile = game.getDrawPile();
        Card[] p1Hand = p1.getHand();
        Card[] p2Hand = p2.getHand();

        EasyMock.verify(playerFactory, p1, p2, drawPile);

        assertEquals(expectedDrawPileLength, actualDrawPile.length);
        assertEquals(expectedPlayerHandSize, p1Hand.length);
        assertEquals(expectedPlayerHandSize, p2Hand.length);

    }

    @Test
    public void dealCards_MaxPlayers_CorrectHandsAndPiles() {
        PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
        DrawPile drawPile = EasyMock.createMock(DrawPile.class);
        GameEngine game = new GameEngine(playerFactory, drawPile);

        int numPlayers = 4;
        String[] names = {"John", "Jane", "Bob", "Job"};

        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Player p3 = EasyMock.createMock(Player.class);
        Player p4 = EasyMock.createMock(Player.class);

        EasyMock.expect(playerFactory.createPlayer("John")).andReturn(p1);
        EasyMock.expect(playerFactory.createPlayer("Jane")).andReturn(p2);
        EasyMock.expect(playerFactory.createPlayer("Bob")).andReturn(p3);
        EasyMock.expect(playerFactory.createPlayer("Job")).andReturn(p4);

        p1.AddCardToHand(Card.DEFUSE);
        p2.AddCardToHand(Card.DEFUSE);
        p3.AddCardToHand(Card.DEFUSE);
        p4.AddCardToHand(Card.DEFUSE);

        // Expect 1 since there are 4 players
        drawPile.AddCard(Card.DEFUSE);

        for(int x = 0; x < 5; x++) {
            EasyMock.expect(drawPile.popCard()).andStubReturn(Card.SKIP);
            p1.AddCardToHand(EasyMock.anyObject(Card.class));

            EasyMock.expect(drawPile.popCard()).andStubReturn(Card.SKIP);
            p2.AddCardToHand(EasyMock.anyObject(Card.class));

            EasyMock.expect(drawPile.popCard()).andStubReturn(Card.SKIP);
            p3.AddCardToHand(EasyMock.anyObject(Card.class));

            EasyMock.expect(drawPile.popCard()).andStubReturn(Card.SKIP);
            p4.AddCardToHand(EasyMock.anyObject(Card.class));
        }

        int expectedDrawPileLength = 15;
        int expectedPlayerHandSize = 6;

        // Create arrays based on the expected size
        Card[] p1ExpectedCards = new Card[expectedPlayerHandSize];
        Card[] p2ExpectedCards = new Card[expectedPlayerHandSize];
        Card[] p3ExpectedCards = new Card[expectedPlayerHandSize];
        Card[] p4ExpectedCards = new Card[expectedPlayerHandSize];
        Card[] remainingCards = new Card[expectedDrawPileLength ];

        // Populate the arrays with expected card types or nulls (if you just need size)
        Arrays.fill(p2ExpectedCards, Card.SKIP);
        Arrays.fill(p1ExpectedCards, Card.SKIP);
        Arrays.fill(p3ExpectedCards, Card.SKIP);
        Arrays.fill(p4ExpectedCards, Card.SKIP);
        Arrays.fill(remainingCards, Card.SKIP);

        // Set up the expectations
        EasyMock.expect(drawPile.getCards()).andReturn(remainingCards);
        EasyMock.expect(p1.getHand()).andReturn(p1ExpectedCards);
        EasyMock.expect(p2.getHand()).andReturn(p2ExpectedCards);
        EasyMock.expect(p3.getHand()).andReturn(p3ExpectedCards);
        EasyMock.expect(p4.getHand()).andReturn(p4ExpectedCards);

        EasyMock.replay(playerFactory, p1, p2, p3, p4, drawPile);

        game.setUpPlayers(numPlayers, names);
        game.dealDefuses();
        game.dealCards();
        Card[] actualDrawPile = game.getDrawPile();
        Card[] p1Hand = p1.getHand();
        Card[] p2Hand = p2.getHand();
        Card[] p3Hand = p3.getHand();
        Card[] p4Hand = p4.getHand();

        EasyMock.verify(playerFactory, p1, p2, p3, p4, drawPile);

        assertEquals(expectedDrawPileLength, actualDrawPile.length);
        assertEquals(expectedPlayerHandSize, p1Hand.length);
        assertEquals(expectedPlayerHandSize, p2Hand.length);
        assertEquals(expectedPlayerHandSize, p3Hand.length);
        assertEquals(expectedPlayerHandSize, p4Hand.length);
    }



}

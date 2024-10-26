package explodingwildcats;

import org.easymock.EasyMock;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

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
        EasyMock.expect(playerFactory.createPlayer("John")).andStubReturn(p1);
        EasyMock.expect(playerFactory.createPlayer("Jane")).andStubReturn(p2);
        EasyMock.replay(playerFactory, p1, p2);

        game.setUpPlayers(numPlayers, names);

        game.dealDefuses();

        EasyMock.verify(playerFactory, p1, p2);

        // test that remaining defuses were inserted into draw pile
        int expectedNumDefusesInDrawPile = 3;
        int expectedDrawPileLength = 37;

        Card[] actualDrawPile = game.getDrawPile();
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
        EasyMock.expect(playerFactory.createPlayer("John")).andReturn(p1);
        EasyMock.expect(playerFactory.createPlayer("Jane")).andReturn(p2);
        EasyMock.expect(playerFactory.createPlayer("Alice")).andReturn(p3);
        EasyMock.expect(playerFactory.createPlayer("Bob")).andReturn(p4);
        EasyMock.replay(playerFactory, p1, p2, p3, p4);

        game.setUpPlayers(numPlayers, names);

        game.dealDefuses();

        EasyMock.verify(playerFactory, p1, p2, p3, p4);

        // test that remaining defuses were inserted into draw pile
        int expectedNumDefusesInDrawPile = 1;
        int expectedDrawPileLength = 35;

        Card[] actualDrawPile = game.getDrawPile();
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
    public void dealCards_TwoPlayers() {
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


        Card[] remainingCards = {Card.CAT,Card.CAT,Card.CAT,Card.SHUFFLE, Card.SEE_THE_FUTURE, Card.NOPE,Card.CAT,
                Card.CAT,Card.CAT,Card.CAT,Card.SHUFFLE, Card.SEE_THE_FUTURE, Card.NOPE,Card.CAT,Card.CAT,Card.CAT,Card.CAT,
                Card.SHUFFLE, Card.SEE_THE_FUTURE, Card.NOPE,Card.CAT,Card.CAT,Card.CAT,Card.CAT, Card.DEFUSE, Card.DEFUSE, Card.DEFUSE};

        EasyMock.expect(drawPile.getCards()).andReturn(remainingCards);

        Card[] p1ExpectedCards = {Card.DEFUSE, Card.SKIP, Card.ATTACK,Card.SKIP, Card.ATTACK,Card.SKIP};
        Card[] p2ExpectedCards = {Card.DEFUSE, Card.ATTACK, Card.SHUFFLE, Card.SEE_THE_FUTURE, Card.NOPE, Card.CAT};

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

        int expectedDrawPileLength = 27;
        int expectedPlayerHandSize = 6;

        assertEquals(expectedDrawPileLength, actualDrawPile.length);

        assertEquals(expectedPlayerHandSize, p1Hand.length);
        assertEquals(expectedPlayerHandSize, p2Hand.length);

    }

}

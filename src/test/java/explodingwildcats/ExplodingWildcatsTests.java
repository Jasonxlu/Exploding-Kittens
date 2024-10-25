package explodingwildcats;

import org.easymock.EasyMock;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ExplodingWildcatsTests {
    @Test
    public void setUpPlayers_OnePlayerOneName_ThrowException() {
        PlayerFactory playerFactory = EasyMock.createMock(PlayerFactory.class);
        GameEngine game = new GameEngine(playerFactory);

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
        GameEngine game = new GameEngine(playerFactory);

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
        GameEngine game = new GameEngine(playerFactory);
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
        GameEngine game = new GameEngine(playerFactory);

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
        GameEngine game = new GameEngine(playerFactory);

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
        GameEngine game = new GameEngine(playerFactory);

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
        GameEngine game = new GameEngine(playerFactory);

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

}

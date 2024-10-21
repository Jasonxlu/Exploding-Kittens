package explodingwildcats;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ExplodingWildcatsTests {
    @Test
    public void setUpPlayers_OnePlayerOneName_ThrowException() {
        ExplodingWildcats game = new ExplodingWildcats();

        int numPlayers = 1;
        String[] names = {"John"};

        String expectedMessage = "Not enough players";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            game.setUpPlayers(numPlayers, names);
        });

        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void setUpPlayers_OnePlayerNoNames_ThrowException() {
        ExplodingWildcats game = new ExplodingWildcats();

        int numPlayers = 1;
        String[] names = {};

        String expectedMessage = "Number of players and number names mismatch";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            game.setUpPlayers(numPlayers, names);
        });

        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void setUpPlayers_TwoPlayersTwoNames() {
        ExplodingWildcats game = new ExplodingWildcats();

        int numPlayers = 2;
        String[] names = {"John", "Jane"};

        game.setUpPlayers(numPlayers, names);

        int expectedNumPlayers = 2;
        int actualNumPlayers = game.getNumberOfPlayers();
        assertEquals(expectedNumPlayers, actualNumPlayers);

        Player[] players = game.getPlayers();
        assertEquals(numPlayers, players.length);
        assertEquals("John", players[0].getName());
        assertEquals("Jane", players[1].getName());
    }

    @Test
    public void setUpPlayers_MaxPlayersMaxNames() {
        ExplodingWildcats game = new ExplodingWildcats();

        int numPlayers = 4;
        String[] names = {"John", "Jane", "Alice", "Bob"};

        game.setUpPlayers(numPlayers, names);

        int expectedNumPlayers = 4;
        int actualNumPlayers = game.getNumberOfPlayers();
        assertEquals(expectedNumPlayers, actualNumPlayers);

        Player[] players = game.getPlayers();
        assertEquals(numPlayers, players.length);
        for (int i = 0; i < numPlayers; i++) {
            assertEquals(names[i], players[i].getName());
        }
    }

    @Test
    public void setUpPlayers_TooManyPlayers_ThrowException() {
        ExplodingWildcats game = new ExplodingWildcats();

        int numPlayers = 5;
        String[] names = {"John", "Jane", "Alice", "Bob", "Charlie"};

        String expectedMessage = "Too many players";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            game.setUpPlayers(numPlayers, names);
        });

        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void dealDefuses_TwoPlayers() {
        ExplodingWildcats game = new ExplodingWildcats();
        int numPlayers = 2;
        String[] names = {"John", "Jane"};
        game.setUpPlayers(numPlayers, names);

        game.dealDefuses();

        // test that defuse was inserted into each player's hand
        int expectedPlayerHandSize = 1;
        Card expectedCardClass = Card.DEFUSE;

        for (Player p : game.getPlayers()) {
            Card[] actualPlayerHand = p.getHand();
            assertEquals(expectedPlayerHandSize, actualPlayerHand.length);
            assertEquals(expectedCardClass, actualPlayerHand[0]);
        }

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
        ExplodingWildcats game = new ExplodingWildcats();
        int numPlayers = 4;
        String[] names = {"John", "Jane", "Alice", "Bob"};
        game.setUpPlayers(numPlayers, names);

        game.dealDefuses();

        // test that defuse was inserted into each player's hand
        int expectedPlayerHandSize = 1;
        Card expectedCardClass = Card.DEFUSE;

        for (Player p : game.getPlayers()) {
            Card[] actualPlayerHand = p.getHand();
            assertEquals(expectedPlayerHandSize, actualPlayerHand.length);
            assertEquals(expectedCardClass, actualPlayerHand[0]);
        }

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

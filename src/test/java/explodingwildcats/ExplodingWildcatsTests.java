package explodingwildcats;

import org.junit.jupiter.api.*;
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
}

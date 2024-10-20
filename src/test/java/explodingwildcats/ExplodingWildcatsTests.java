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
}

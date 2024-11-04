package explodingwildcats;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the player factory class.
 */
public class PlayerFactoryTests {

  @Test
  public void createPlayer_EmptyString_ThrowsIllegalArgumentException() {
    PlayerFactory factory = new PlayerFactory();

    String expectedMessage = "Player name must be non-empty";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      Player player = factory.createPlayer("");
      assertNull(player);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }
}

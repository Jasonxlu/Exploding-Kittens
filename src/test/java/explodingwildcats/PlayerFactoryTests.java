package explodingwildcats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Test suite for the player factory class.
 */
public class PlayerFactoryTests {

  @Test
  public void createPlayer_EmptyString_ThrowsIllegalArgumentException() {
    PlayerFactory factory = new PlayerFactory();

    String expectedMessage = "Player name must be non-empty";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      factory.createPlayer("");
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }
}

package explodingwildcats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

/**
 * Test suite for the player factory class.
 */
public class PlayerFactoryTests {

  @Test
  public void createPlayer_EmptyString_ThrowsIllegalArgumentException() {
    PlayerFactory factory = new PlayerFactory();
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);

    String expectedMessage = "Player name must be non-empty";
    String name = "";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      factory.createPlayer(name, cardPileFactory.createCardPile());
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void createPlayer_NonEmptyString() {
    PlayerFactory factory = new PlayerFactory();
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);

    String name = "John";

    Player player = factory.createPlayer(name, cardPileFactory.createCardPile());
    assertNotNull(player);
  }

  @Test
  public void createPlayer_NameIsWhitespace_ThrowsIllegalArgumentException() {
    PlayerFactory factory = new PlayerFactory();
    CardPileFactory cardPileFactory = EasyMock.createMock(CardPileFactory.class);

    String expectedMessage = "Player name must be non-empty";
    String name = "   ";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      factory.createPlayer(name, cardPileFactory.createCardPile());
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }
}

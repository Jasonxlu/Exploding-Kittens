package explodingwildcats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;


public class CardPileTests {
  @Test
  public void addCard_EmptyPile_ExplodeAdded() {
    CardPile pile = new CardPile();

    pile.addCard(Card.EXPLODE);
    Card[] cards = pile.getCards();

    int expectedLength = 1;
    Card expectedCard = Card.EXPLODE;

    assertEquals(expectedLength, cards.length);
    assertEquals(expectedCard, pile.getCards()[cards.length - 1]);
  }

  @Test
  public void addCard_NullCard_ThrowException() {
    CardPile pile = new CardPile();

    pile.addCard(Card.CAT);
    Card[] cards = pile.getCards();

    String expectedMsg = "Null Card Object";
    assertEquals(1, cards.length);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> pile.addCard(null));
    assertEquals(expectedMsg, exception.getMessage());
  }

  @Test
  public void addCard_MultipleCardsInPile_DefuseAdded() {
    CardPile pile = new CardPile();

    pile.addCard(Card.ATTACK);
    pile.addCard(Card.ATTACK);
    pile.addCard(Card.DEFUSE);
    Card[] cards = pile.getCards();

    int expectedLength = 3;
    Card expectedCard = Card.DEFUSE;

    assertEquals(expectedLength, cards.length);
    assertEquals(expectedCard, pile.getCards()[cards.length - 1]);
  }

  @Test
  public void peek_EmptyPile_ReturnsEmpty() {
    CardPile pile = new CardPile();
    Card[] cards = pile.getCards();
    assertEquals(0, cards.length);

    Card[] peeked = pile.peek();
    assertEquals(0, peeked.length);
  }
}

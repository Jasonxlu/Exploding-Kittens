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

  @Test
  public void peek_OneCard_ReturnsOneCardArr() {
    CardPile pile = new CardPile();

    pile.addCard(Card.EXPLODE);
    Card[] cards = pile.getCards();
    assertEquals(1, cards.length);

    cards = pile.peek();
    assertEquals(1, cards.length);
    assertEquals(Card.EXPLODE, cards[0]);
  }

  @Test
  public void peek_TwoCards_ReturnsTwoCardArr() {
    CardPile pile = new CardPile();

    pile.addCard(Card.EXPLODE);
    pile.addCard(Card.CAT);
    Card[] cards = pile.peek();
    assertEquals(2, cards.length);

    cards = pile.peek();
    assertEquals(2, cards.length);
    assertEquals(Card.CAT, cards[0]);
    assertEquals(Card.EXPLODE, cards[1]);
  }

  @Test
  public void peek_FourCards_ReturnsThreeCardArr() {
    CardPile pile = new CardPile();

    pile.addCard(Card.EXPLODE);
    pile.addCard(Card.CAT);
    pile.addCard(Card.DEFUSE);
    pile.addCard(Card.SKIP);

    Card[] cards = pile.getCards();
    assertEquals(4, cards.length);

    cards = pile.peek();
    assertEquals(3, cards.length);
    assertEquals(Card.SKIP, cards[0]);
    assertEquals(Card.DEFUSE, cards[1]);
    assertEquals(Card.CAT, cards[2]);
  }

  @Test
  public void drawCard_EmptyPile_ThrowException() {
    CardPile pile = new CardPile();

    Card[] cards = pile.getCards();
    assertEquals(0, cards.length);

    String expectedMessage = "Empty pile on draw";
    Exception exception = assertThrows(IllegalStateException.class, () -> pile.drawCard());

    assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  public void drawCard_OneCard_ReturnsCorrectCard() {
    CardPile pile = new CardPile();

    pile.addCard(Card.ATTACK);
    Card[] cards = pile.getCards();
    assertEquals(1, cards.length);

    Card card = pile.drawCard();
    assertEquals(Card.ATTACK, card);

    cards = pile.getCards();
    assertEquals(0, cards.length);
  }

  @Test
  public void drawCard_TwoCards_ReturnsCorrectCard() {
    CardPile pile = new CardPile();

    pile.addCard(Card.DEFUSE);
    pile.addCard(Card.IMPLODE);
    Card[] cards = pile.getCards();
    assertEquals(2, cards.length);

    Card card = pile.drawCard();
    assertEquals(Card.IMPLODE, card);

    cards = pile.getCards();
    assertEquals(1, cards.length);

  }
}

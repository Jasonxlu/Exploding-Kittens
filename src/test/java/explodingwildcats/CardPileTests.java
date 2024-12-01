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
  public void addCard_OneCardInPile_AttackAdded() {
    CardPile pile = new CardPile();

    pile.addCard(Card.EXPLODE);
    Card[] cards = pile.getCards();

    assertEquals(1, cards.length);
    pile.addCard(Card.ATTACK);
    cards = pile.getCards();

    int expectedLength = 2;
    Card expectedCard = Card.ATTACK;

    assertEquals(expectedLength, cards.length);
    assertEquals(expectedCard, pile.getCards()[cards.length - 1]);
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
    pile.addCard(Card.TACO_CAT);
    Card[] cards = pile.peek();
    assertEquals(2, cards.length);

    cards = pile.peek();
    assertEquals(2, cards.length);
    assertEquals(Card.TACO_CAT, cards[0]);
    assertEquals(Card.EXPLODE, cards[1]);
  }

  @Test
  public void peek_FourCards_ReturnsThreeCardArr() {
    CardPile pile = new CardPile();

    pile.addCard(Card.EXPLODE);
    pile.addCard(Card.TACO_CAT);
    pile.addCard(Card.DEFUSE);
    pile.addCard(Card.SKIP);

    Card[] cards = pile.getCards();
    assertEquals(4, cards.length);

    cards = pile.peek();
    assertEquals(3, cards.length);
    assertEquals(Card.SKIP, cards[0]);
    assertEquals(Card.DEFUSE, cards[1]);
    assertEquals(Card.TACO_CAT, cards[2]);
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

  @Test
  public void setCard_index0_ATTACK_emptyPile() {
    CardPile pile = new CardPile();

    int index = 0;
    Card c = Card.ATTACK;

    String expectedMessage = "Index is out of range.";
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      pile.setCard(index, c);
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void setCard_index0_SHUFFLE_pileContainsOneCard() {
    CardPile pile = new CardPile();

    pile.addCard(Card.NOPE);

    int index = 0;
    Card c = Card.SHUFFLE;

    pile.setCard(index, c);

    Card[] expectedPile = new Card[] { c };
    Card[] actualPile = pile.getCards();
    for (int i = 0; i < actualPile.length && i < expectedPile.length; i++) {
      assertEquals(expectedPile[i], actualPile[i]);
    }
  }

  @Test
  public void setCard_index0_DEFUSE_pileContainsTwoCards() {
    CardPile pile = new CardPile();

    Card card1 = Card.NOPE;
    Card card2 = Card.SEE_THE_FUTURE;
    pile.addCard(card1);
    pile.addCard(card2);

    int index = 0;
    Card c = Card.DEFUSE;

    pile.setCard(index, c);

    Card[] expectedPile = new Card[] { c, card2 };
    Card[] actualPile = pile.getCards();
    for (int i = 0; i < actualPile.length && i < expectedPile.length; i++) {
      assertEquals(expectedPile[i], actualPile[i]);
    }
  }

  @Test
  public void setCard_index2_SEE_THE_FUTURE_pileContainsThreeCards() {
    CardPile pile = new CardPile();

    Card card1 = Card.SEE_THE_FUTURE;
    Card card2 = Card.TARGETED_ATTACK;
    Card card3 = Card.DRAW_FROM_BOTTOM;
    pile.addCard(card1);
    pile.addCard(card2);
    pile.addCard(card3);

    int index = 2;
    Card c = Card.SEE_THE_FUTURE;

    pile.setCard(index, c);

    Card[] expectedPile = new Card[] { card1, card2, c };
    Card[] actualPile = pile.getCards();
    for (int i = 0; i < actualPile.length && i < expectedPile.length; i++) {
      assertEquals(expectedPile[i], actualPile[i]);
    }
  }

  @Test
  public void drawCardFromBottom_singleCardEXPLODE() {
    CardPile pile = new CardPile();

    Card card1 = Card.EXPLODE;
    pile.addCard(card1);

    Card actualDrawnCard = pile.drawCardFromBottom();

    assertEquals(card1, actualDrawnCard);

    int expectedCardPileLength = 0;
    Card[] actualPile = pile.getCards();
    assertEquals(expectedCardPileLength, actualPile.length);
  }

  @Test
  public void drawCardFromBottom_twoCards_SEE_THE_FUTUREAndEXPLODE() {
    CardPile pile = new CardPile();

    Card card1 = Card.SEE_THE_FUTURE;
    pile.addCard(card1);
    Card card2 = Card.EXPLODE;
    pile.addCard(card2);

    Card actualDrawnCard = pile.drawCardFromBottom();

    assertEquals(card1, actualDrawnCard);

    Card[] expectedPile = new Card[] { card2};
    Card[] actualPile = pile.getCards();
    for (int i = 0; i < actualPile.length && i < expectedPile.length; i++) {
      assertEquals(expectedPile[i], actualPile[i]);
    }
  }
}

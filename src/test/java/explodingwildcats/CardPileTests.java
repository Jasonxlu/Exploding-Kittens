package explodingwildcats;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


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
  public void contains_EmptyPile_NoMatch() {
    CardPile pile = new CardPile();
    assertFalse(pile.contains(Card.ATTACK));
  }

  @Test
  public void contains_OneCardPile_Match() {
    CardPile pile = new CardPile();
    pile.addCard(Card.ATTACK);
    assertTrue(pile.contains(Card.ATTACK));
  }

  @Test
  public void contains_OneCardPile_NoMatch() {
    CardPile pile = new CardPile();
    pile.addCard(Card.ATTACK);
    assertFalse(pile.contains(Card.SKIP));
  }

  @Test
  public void contains_TwoCardPile_NoMatch() {
    CardPile pile = new CardPile();
    pile.addCard(Card.DEFUSE);
    pile.addCard(Card.IMPLODE);
    assertFalse(pile.contains(Card.ATTACK));
  }

  @Test
  public void contains_TwoCardPile_Match() {
    CardPile pile = new CardPile();
    pile.addCard(Card.DEFUSE);
    pile.addCard(Card.IMPLODE);
    assertTrue(pile.contains(Card.DEFUSE));
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

    Card[] expectedPile = new Card[]{c};
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

    Card[] expectedPile = new Card[]{c, card2};
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

    Card[] expectedPile = new Card[]{card1, card2, c};
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

    Card[] expectedPile = new Card[]{card2};
    Card[] actualPile = pile.getCards();
    for (int i = 0; i < actualPile.length && i < expectedPile.length; i++) {
      assertEquals(expectedPile[i], actualPile[i]);
    }
  }

  @Test
  public void shuffle_oneCardInPile_noChange() {
    CardPile pile = new CardPile();

    pile.addCard(Card.DEFUSE);

    Card[] expectedPile = pile.getCards();
    pile.shuffle();
    Card[] actualPile = pile.getCards();

    for (int i = 0; i < actualPile.length && i < expectedPile.length; i++) {
      assertEquals(expectedPile[i], actualPile[i]);
    }
  }

  @Test
  public void shuffle_multipleCardsInPile_noChange() {
    CardPile pile = EasyMock.partialMockBuilder(CardPile.class)
        .addMockedMethod("shuffleList")
            .withConstructor()
        .createMock();

    // Expect that shuffleList is called and override it to do nothing
    pile.shuffleList(EasyMock.anyObject());
    // make sure shuffleList does not do anything
    EasyMock.expectLastCall().andAnswer(() -> null).anyTimes();
    EasyMock.replay(pile);


    pile.addCard(Card.ATTACK);
    pile.addCard(Card.REVERSE);
    pile.addCard(Card.NOPE);

    Card[] expectedPile = pile.getCards();
    pile.shuffle();
    Card[] actualPile = pile.getCards();

    for (int i = 0; i < actualPile.length && i < expectedPile.length; i++) {
      assertEquals(expectedPile[i], actualPile[i]);
    }
    EasyMock.verify(pile);
  }

  @Test
  public void shuffle_maxCardsInPile_noChange() {
    CardPile pile = EasyMock.partialMockBuilder(CardPile.class)
            .addMockedMethod("shuffleList")
            .withConstructor()
            .createMock();

    // Expect that shuffleList is called and override it to do nothing
    pile.shuffleList(EasyMock.anyObject());
    // make sure shuffleList does not do anything
    EasyMock.expectLastCall().andAnswer(() -> null).anyTimes();
    EasyMock.replay(pile);

    final int numSkipsAttacksAndTargetedAttacks = 3;
    for (int i = 0; i < numSkipsAttacksAndTargetedAttacks; i++) {
      pile.addCard(Card.SKIP);
      pile.addCard(Card.ATTACK);
      pile.addCard(Card.TARGETED_ATTACK);
    }
    int numShufflesFuturesNopesCatTypesReversesDrawBottomsAlterFuturesCats = 4;
    for (int i = 0; i < numShufflesFuturesNopesCatTypesReversesDrawBottomsAlterFuturesCats; i++) {
      pile.addCard(Card.SHUFFLE);
      pile.addCard(Card.SEE_THE_FUTURE);
      pile.addCard(Card.NOPE);
      pile.addCard(Card.REVERSE);
      pile.addCard(Card.DRAW_FROM_BOTTOM);
      pile.addCard(Card.ALTER_THE_FUTURE);
      pile.addCard(Card.TACO_CAT);
      pile.addCard(Card.HAIRY_POTATO_CAT);
      pile.addCard(Card.BEARD_CAT);
      pile.addCard(Card.RAINBOW_CAT);
      pile.addCard(Card.FERAL_CAT);
    }

    Card[] expectedPile = pile.getCards();
    pile.shuffle();
    Card[] actualPile = pile.getCards();

    for (int i = 0; i < actualPile.length && i < expectedPile.length; i++) {
      assertEquals(expectedPile[i], actualPile[i]);
    }
    EasyMock.verify(pile);
  }

  @Test
  public void shuffle_multipleCardsInPile_cardsAreShuffled() {
    CardPile pile = EasyMock.partialMockBuilder(CardPile.class)
            .addMockedMethod("shuffleList")
            .withConstructor()
            .createMock();


    pile.shuffleList(EasyMock.anyObject());
    EasyMock.expectLastCall().andAnswer(() -> {
      List<Card> list = (List<Card>) EasyMock.getCurrentArguments()[0]; // Get the argument passed to shuffleList
      Collections.swap(list, 0, list.size() - 1); // Swap the first and last cards as a simple shuffle
      return null; // shuffleList is void, so return null
    }).anyTimes();
    EasyMock.replay(pile);

    pile.addCard(Card.ATTACK);
    pile.addCard(Card.REVERSE);
    pile.addCard(Card.NOPE);

    Card[] expectedPile = pile.getCards();
    pile.shuffle();
    Card[] actualPile = pile.getCards();

    boolean isShuffled = false;
    for (int i = 0; i < actualPile.length && i < expectedPile.length; i++) {
      if (expectedPile[i] != actualPile[i]) {
        isShuffled = true;
        break;
      }
    }

    // Check size of each pile
    assertEquals(expectedPile.length, actualPile.length);

    assertTrue(isShuffled);
    EasyMock.verify(pile);
  }

  @Test
  public void shuffle_maxCardsInPile_cardsAreShuffled() {
    CardPile pile = EasyMock.partialMockBuilder(CardPile.class)
            .addMockedMethod("shuffleList")
            .withConstructor()
            .createMock();

    pile.shuffleList(EasyMock.anyObject());
    EasyMock.expectLastCall().andAnswer(() -> {
      List<Card> list = (List<Card>) EasyMock.getCurrentArguments()[0]; // Get the argument passed to shuffleList
      Collections.swap(list, 0, list.size() - 1); // Swap the first and last cards as a simple shuffle
      return null; // shuffleList is void, so return null
    }).anyTimes();
    EasyMock.replay(pile);

    final int numSkipsAttacksAndTargetedAttacks = 3;
    for (int i = 0; i < numSkipsAttacksAndTargetedAttacks; i++) {
      pile.addCard(Card.SKIP);
      pile.addCard(Card.ATTACK);
      pile.addCard(Card.TARGETED_ATTACK);
    }
    int numShufflesFuturesNopesCatTypesReversesDrawBottomsAlterFuturesCats = 4;
    for (int i = 0; i < numShufflesFuturesNopesCatTypesReversesDrawBottomsAlterFuturesCats; i++) {
      pile.addCard(Card.SHUFFLE);
      pile.addCard(Card.SEE_THE_FUTURE);
      pile.addCard(Card.NOPE);
      pile.addCard(Card.REVERSE);
      pile.addCard(Card.DRAW_FROM_BOTTOM);
      pile.addCard(Card.ALTER_THE_FUTURE);
      pile.addCard(Card.TACO_CAT);
      pile.addCard(Card.HAIRY_POTATO_CAT);
      pile.addCard(Card.BEARD_CAT);
      pile.addCard(Card.RAINBOW_CAT);
      pile.addCard(Card.FERAL_CAT);
    }

    Card[] expectedPile = pile.getCards();
    pile.shuffle();
    Card[] actualPile = pile.getCards();

    boolean isShuffled = false;
    for (int i = 0; i < actualPile.length && i < expectedPile.length; i++) {
      if (expectedPile[i] != actualPile[i]) {
        isShuffled = true;
        break;
      }
    }

    // Check size of each pile
    assertEquals(expectedPile.length, actualPile.length);

    assertTrue(isShuffled);

    EasyMock.verify(pile);
  }

  @Test
  public void removeCardFromPile_emptyPile_isPlayerHand_attackCard_returnFalse() {
    CardPile pile = new CardPile();
    Card card = Card.ATTACK;
    boolean isPlayerHand = true;

    int expectedLength = 0;

    assertFalse(pile.removeCardFromPile(card, isPlayerHand));

    assertEquals(expectedLength, pile.getCards().length);
  }

  @Test
  public void removeCardFromPile_oneCardPile_notPlayerHand_skipCard_returnFalse() {
    CardPile pile = new CardPile();
    Card card = Card.SKIP;
    Card pileCard = Card.ATTACK;
    boolean isPlayerHand = false;
    pile.addCard(pileCard);

    assertFalse(pile.removeCardFromPile(card, isPlayerHand));
  }

  @Test
  public void removeCardFromPile_multipleCardPile_notPlayerHand_explodeCard_returnFalse() {
    CardPile pile = new CardPile();
    Card card = Card.EXPLODE;

    Card pileCard = Card.SEE_THE_FUTURE;
    Card pileCard2 = Card.SHUFFLE;
    Card pileCard3 = Card.NOPE;

    boolean isPlayerHand = false;
    pile.addCard(pileCard);
    pile.addCard(pileCard2);
    pile.addCard(pileCard3);

    assertFalse(pile.removeCardFromPile(card, isPlayerHand));
  }

  @Test
  public void removeCardFromPile_singleCardPile_isPlayerHand_defuseCard_returnTrue() {
    CardPile pile = new CardPile();
    Card card = Card.DEFUSE;
    boolean isPlayerHand = true;
    pile.addCard(card);

    assertTrue(pile.removeCardFromPile(card, isPlayerHand));
  }

  @Test
  public void removeCardFromPile_multipleCardPile_notPlayerHand_implodeCard_returnTrue() {
    CardPile pile = new CardPile();
    Card card = Card.IMPLODE;

    Card pileCard = Card.SEE_THE_FUTURE;
    Card pileCard2 = Card.SHUFFLE;
    Card pileCard3 = Card.IMPLODE;

    boolean isPlayerHand = false;
    pile.addCard(pileCard);
    pile.addCard(pileCard2);
    pile.addCard(pileCard3);

    assertTrue(pile.removeCardFromPile(card, isPlayerHand));
  }


}

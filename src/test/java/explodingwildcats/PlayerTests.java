package explodingwildcats;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
* Test suite for the Player class.
*/
public class PlayerTests {
  @Test
  public void addCardToHand_ATTACK() {
    CardPile hand = EasyMock.createMock(CardPile.class);
    Player player = new Player("Bob", hand);
    Card attackCard = Card.ATTACK;

    hand.addCard(attackCard);
    EasyMock.replay(hand);

    player.addCardToHand(attackCard);

    EasyMock.verify(hand);
  }

  @Test
  public void addCardToHand_CAT() {
    CardPile hand = EasyMock.createMock(CardPile.class);
    Player player = new Player("Bob", hand);
    Card attackCard = Card.TACO_CAT; // TODO: revise addCardToHand Player BVA

    hand.addCard(attackCard);
    EasyMock.replay(hand);

    player.addCardToHand(attackCard);

    EasyMock.verify(hand);
  }

  @Test
  public void addCardToHand_DEFUSE() {
    CardPile hand = EasyMock.createMock(CardPile.class);
    Player player = new Player("Bob", hand);
    Card attackCard = Card.DEFUSE;

    hand.addCard(attackCard);
    EasyMock.replay(hand);

    player.addCardToHand(attackCard);

    EasyMock.verify(hand);
  }

  @Test
  public void addCardToHand_EXPLODE() {
    CardPile hand = EasyMock.createMock(CardPile.class);
    Player player = new Player("Bob", hand);
    Card attackCard = Card.EXPLODE;

    hand.addCard(attackCard);
    EasyMock.replay(hand);

    player.addCardToHand(attackCard);

    EasyMock.verify(hand);
  }

  @Test
  public void addCardToHand_SEE_THE_FUTURE() {
    CardPile hand = EasyMock.createMock(CardPile.class);
    Player player = new Player("Bob", hand);
    Card attackCard = Card.SEE_THE_FUTURE;

    hand.addCard(attackCard);
    EasyMock.replay(hand);

    player.addCardToHand(attackCard);

    EasyMock.verify(hand);
  }

  @Test
  public void addCardToHand_SHUFFLE() {
    CardPile hand = EasyMock.createMock(CardPile.class);
    Player player = new Player("Bob", hand);
    Card attackCard = Card.SHUFFLE;

    hand.addCard(attackCard);
    EasyMock.replay(hand);

    player.addCardToHand(attackCard);

    EasyMock.verify(hand);
  }

  @Test
  public void addCardToHand_SKIP() {
    CardPile hand = EasyMock.createMock(CardPile.class);
    Player player = new Player("Bob", hand);
    Card attackCard = Card.SKIP;

    hand.addCard(attackCard);
    EasyMock.replay(hand);

    player.addCardToHand(attackCard);

    EasyMock.verify(hand);
  }

  @Test
  public void hasCard_ReturnsTrue() {
    CardPile hand = EasyMock.createMock(CardPile.class);
    Player player = new Player("Bob", hand);

    Card card = Card.ATTACK;

    EasyMock.expect(hand.contains(card)).andReturn(true);

    EasyMock.replay(hand);

    boolean result = player.hasCard(card);
    assertTrue(result);

    EasyMock.verify(hand);
  }
}

package explodingwildcats;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

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
    Card attackCard = Card.CAT;

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
}

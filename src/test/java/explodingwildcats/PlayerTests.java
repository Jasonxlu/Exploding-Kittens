package explodingwildcats;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

  @Test
  public void hasCard_ReturnsFalse() {
    CardPile hand = EasyMock.createMock(CardPile.class);
    Player player = new Player("Bob", hand);

    Card card = Card.TACO_CAT;

    EasyMock.expect(hand.contains(card)).andReturn(false);

    EasyMock.replay(hand);

    boolean result = player.hasCard(card);
    assertFalse(result);

    EasyMock.verify(hand);
  }


  @ParameterizedTest
  @EnumSource(
          value = Card.class,
          names = {"IMPLODE", "EXPLODE"},
          mode = EnumSource.Mode.EXCLUDE
  ) // Exclude specific cards from the test
  public void removeCardFromHand_returnTrue(Card card) {
    CardPile hand = EasyMock.createMock(CardPile.class);
    Player player = new Player("Bob", hand);

    // Mock the behavior of removeCardFromPile to always return false
    EasyMock.expect(hand.removeCardFromPile(card)).andReturn(false);

    EasyMock.replay(hand);

    // Test the removeCardFromHand method
    boolean result = player.removeCardFromHand(card);

    // Assert that the method returns false
    assertFalse(result);

    EasyMock.verify(hand);
  }



}

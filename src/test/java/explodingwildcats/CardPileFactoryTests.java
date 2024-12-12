package explodingwildcats;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CardPileFactoryTests {

  @Test
  public void createCardPile() {
    CardPileFactory factory = new CardPileFactory();
    CardPile cardPile = factory.createCardPile();

    assertNotNull(cardPile);
  }

}

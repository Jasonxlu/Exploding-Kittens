package explodingwildcats;

/**
 * Class representing decks of cards (DrawPile, DiscardPile, Player hand, etc.).
 */
public class CardPile {
  /**
   * Adds a card to the pile.
   *
   * @param c Card being added
   */
  public void addCard(Card c) {

    if(c == null) {
      throw new IllegalArgumentException("Null Card Object");
    }
  }

  /**
   * Returns all the cards in the pile.
   *
   * @return Card array of all the cards
   */
  public Card[] getCards() {
    Card[] arr = new Card[1];
    arr[0] = Card.EXPLODE;
    return arr;
  }

  /**
   * Removes and returns the first card in the pile.
   *
   * @return The card popped from the card pile
   */
  public Card drawCard() {
    return null;
  }
}

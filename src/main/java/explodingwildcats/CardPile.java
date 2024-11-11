package explodingwildcats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing decks of cards (DrawPile, DiscardPile, Player hand, etc.).
 */
public class CardPile {
  private final ArrayList<Card> cardList;

  /**
   * Constructor for CardPile object.
   */
  public CardPile() {
    cardList = new ArrayList<Card>();
  }

  /**
   * Adds a card to the pile.
   *
   * @param c Card being added
   * @throws IllegalArgumentException if the card is null
   */
  public void addCard(Card c) {
    cardList.add(c);
  }

  /**
   * Returns all the cards in the pile.
   *
   * @return Card array of all the cards
   */
  public Card[] getCards() {
    return cardList.toArray(new Card[0]);
  }

  /**
   * Removes and returns the first card in the pile.
   *
   * @return The card popped from the card pile
   */
  public Card drawCard() {
    if (cardList.isEmpty()) {
      throw new IllegalStateException("Empty pile on draw");
    }
    return cardList.remove(cardList.size() - 1);
  }

  /**
   * Returns an array of the cards at the top of the pile in the order of being drawn from the top.
   *
   * @return 0-3 card array representing the cards at the top of the pile
   */
  public Card[] peek() {
    ArrayList<Card> cards = new ArrayList<Card>();

    for (int i = 0; i < 3 && i < cardList.size(); i++) {
      cards.add(cardList.get(cardList.size() - 1 - i));
    }
    return cards.toArray(new Card[0]);
  }

  public boolean contains(Card c) {
    return cardList.contains(c);
  }
}

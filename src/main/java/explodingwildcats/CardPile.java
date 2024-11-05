package explodingwildcats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing decks of cards (DrawPile, DiscardPile, Player hand, etc.).
 */
public class CardPile {
  private ArrayList<Card> cardList;

  public CardPile() {
    cardList = new ArrayList<Card>();
  }

  /**
   * Adds a card to the pile.
   *
   * @param c Card being added
   */
  public void addCard(Card c) {
    if(c == null) {
      throw new IllegalArgumentException("Null Card Object");
    }
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
    return null;
  }

  /**
   * Returns an array of the cards at the top of the pile
   *
   * @return 0-3 card array representing the cards at the top of the pile
   */
  public Card[] peek() {
    ArrayList<Card> cards = new ArrayList<Card>();

    for(int i = 0; i < 3 && i < cardList.size(); i++) {
      cards.add(cardList.get(cardList.size()-1-i));
    }
    return cards.toArray(new Card[0]);

  }
}

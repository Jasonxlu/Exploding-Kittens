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
   * Adds a card to the pile at the index provided or errors if not possible.
   *
   * @param c Card being added
   * @param index desired index to add to
   */
  public void addCardAt(Card c, int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException("Index cannot be negative");
    } else if (index >= cardList.size()) {
      cardList.add(c);
    } else {
      cardList.add(index, c);
    }
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

  /**
   * Checks if a specified card is in the pile list.
   *
   * @return true if the card is in the pile, false otherwise.
   */
  public boolean contains(Card c) {
    return cardList.contains(c);
  }

  /**
   * Sets the ith card in the pile to c.
   *
   * @param i the index in the array to set to c.
   * @param c the card to set.
   */
  public void setCard(int i, Card c) {
    if (i >= cardList.size()) {
      throw new IllegalArgumentException("Index is out of range.");
    }
    cardList.set(i, c);
  }

  /**
   * Removes and returns the bottom card in the pile.
   *
   * @return The card popped from the bottom of the card pile
   */
  public Card drawCardFromBottom() {
    return cardList.remove(0);
  }

  /**
   * Shuffles the cards in the pile.
   */
  public void shuffle() {
    shuffleList(cardList);
  }

  /**
   * Shuffles the given list of cards.
   *
   * @param list the list of cards to shuffle.
   */
  void shuffleList(List<Card> list) {
    Collections.shuffle(list);
  }

  /**
   * Removes a card from the pile.
   *
   * @param card the card to remove
   * @param isPlayerHand whether the cardpile is a player hand.
   * @return true if the card was removed, false otherwise
   */
  public boolean removeCardFromPile(Card card, boolean isPlayerHand) {
    if (isPlayerHand && (card == Card.EXPLODE || card == Card.IMPLODE)) {
      return false;
    } else if (cardList.contains(card)) {
      cardList.remove(card);
      return true;
    }
    return false;
  }
}

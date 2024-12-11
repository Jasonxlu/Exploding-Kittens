package explodingwildcats;

import java.util.ArrayList;

/**
 * Class representing a player in the game.
 */
public class Player {
  private String name;
  private CardPile hand;

  /**
   * Unit testing constructor for player object.
   *
   * @param name name of the player
   * @param hand the player's hand
   */
  Player(String name, CardPile hand) {
    this.name = name;
    this.hand = hand;
  }

  /**
   * Constructor for player object.
   *
   * @param name name of the player
   */
  public Player(String name) {
    this.name = name;
    this.hand = new CardPile();
  }

  /**
   * Returns the name of the player.
   *
   * @return string name of player
   */
  public String getName() {
    return name;
  }

  /**
   * Adds a card to the player's hand.
   *
   * @param c card to add to player hand
   */
  public void addCardToHand(Card c) {
    this.hand.addCard(c);
  }

  /**
   * Returns the entirety of player's hand.
   *
   * @return card array of the players hand
   */
  public Card[] getHand() {
    return hand.getCards();
  }

  /**
   * Returns true if the player has the card, false otherwise.
   *
   * @param card card to check if the player has
   */
  public boolean hasCard(Card card) {
    return hand.contains(card);
  }

  /**
   * Removes one instance of the specified card from the player's hand.
   *
   * @param card the card to remove.
   * @return true if successful and false otherwise.
   */
  public boolean removeCardFromHand(Card card) {
    if (card == Card.EXPLODE || card == Card.IMPLODE) {
      return false;
    }
    return hand.removeCardFromPile(card);
  }
}

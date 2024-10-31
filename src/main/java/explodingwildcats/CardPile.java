package explodingwildcats;

import java.util.ArrayList;

public class CardPile {
    private final ArrayList<Card> cards = new ArrayList<Card>();

    public void AddCard(Card c) {
        cards.add(c);
    }

    public Card[] getCards() {
        return cards.toArray(new Card[0]);
    }

    public Card popCard() {
        return cards.remove(0);
    }
}

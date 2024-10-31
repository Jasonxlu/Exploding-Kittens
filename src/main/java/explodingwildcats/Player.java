package explodingwildcats;

import java.util.ArrayList;

public class Player {
    private String name;
    private final CardPile hand = new CardPile();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void AddCardToHand(Card c) {
        this.hand.AddCard(c);
    }

    public Card[] getHand() {
        return this.hand.getCards();
    }
}

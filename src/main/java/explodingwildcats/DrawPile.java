package explodingwildcats;

public class DrawPile extends CardPile {
    public DrawPile() {
        // add all the basic cards.
        for(int i = 0; i < 3; i++) {
            AddCard(Card.SKIP);
            AddCard(Card.ATTACK);
        }
        for(int i = 0; i < 4; i++) {
            AddCard(Card.SHUFFLE);
            AddCard(Card.SEE_THE_FUTURE);
            AddCard(Card.NOPE);
            for(int j = 0; j < 4; j++) {
                AddCard(Card.CAT);
            }
        }
    }
}

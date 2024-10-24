package explodingwildcats;

public class DrawPile extends CardPile {
    public DrawPile() {
        // add all the basic cards.
        int numSkipsAndAttacks = 3;
        for(int i = 0; i < numSkipsAndAttacks; i++) {
            AddCard(Card.SKIP);
            AddCard(Card.ATTACK);
        }
        int numShufflesSeeTheFuturesNopesAndCatCardTypes = 4;
        int numberOfEachCatCard = 4;
        for(int i = 0; i < numShufflesSeeTheFuturesNopesAndCatCardTypes; i++) {
            AddCard(Card.SHUFFLE);
            AddCard(Card.SEE_THE_FUTURE);
            AddCard(Card.NOPE);
            for(int j = 0; j < numberOfEachCatCard; j++) {
                AddCard(Card.CAT);
            }
        }
    }
}

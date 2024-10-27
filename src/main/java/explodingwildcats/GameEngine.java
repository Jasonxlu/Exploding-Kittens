package explodingwildcats;

public class GameEngine {
    private int numOfPlayers = 2;

    private Player[] players = null;

    private DrawPile drawPile;
    private PlayerFactory playerFactory;

    public GameEngine(PlayerFactory playerFactory, DrawPile drawPile) {
        this.playerFactory = playerFactory;
        this.drawPile = drawPile;
    }

    public void setUpPlayers(int numberOfPlayers, String[] names) {
        if (names.length != numberOfPlayers) {
            throw new IllegalArgumentException("Number of players and number names mismatch");
        }

        if (numberOfPlayers < 2) {
            throw new IllegalArgumentException("Not enough players");
        }

        if (numberOfPlayers > 4) {
            throw new IllegalArgumentException("Too many players");
        }

        this.numOfPlayers = numberOfPlayers;
        this.players = new Player[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = playerFactory.createPlayer(names[i]);
        }
    }

    public int getNumberOfPlayers() {
        return numOfPlayers;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void dealDefuses() {
        int totalNumDefuses = 5;
        for (Player p : players) {
            p.AddCardToHand(Card.DEFUSE);
        }
        for (int i = 0; i < totalNumDefuses-numOfPlayers; i++) {
            drawPile.AddCard(Card.DEFUSE);
        }
    }

    public void dealCards() {
        for(Player p : players) {
            int cardsToDealPerPlayer = 5;
            for(int i = 0; i<cardsToDealPerPlayer; i++) {
                Card cardToAdd = drawPile.popCard();
                p.AddCardToHand(cardToAdd);
            }
        }
    }
    public void insertExplodingCards() {
        for (int i = 0; i < numOfPlayers-1; i++) {
            drawPile.AddCard(Card.EXPLODE);
        }
    }

    public Card[] getDrawPile() {
        return drawPile.getCards();
    }
}

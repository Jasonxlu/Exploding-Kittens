package explodingwildcats;

public class GameEngine {
    private int numOfPlayers = 2;

    private Player[] players = null;

    private DrawPile drawPile = new DrawPile();
    private PlayerFactory playerFactory;

    public GameEngine(PlayerFactory playerFactory) {
        this.playerFactory = playerFactory;
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

    public Card[] getDrawPile() {
        return drawPile.getCards();
    }
}

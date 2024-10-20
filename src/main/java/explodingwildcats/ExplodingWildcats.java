package explodingwildcats;

public class ExplodingWildcats {
    private int numOfPlayers = 2;
    private Player[] players = null;

    public void setUpPlayers(int numberOfPlayers, String[] names) {
        if (names.length != numberOfPlayers) {
            throw new IllegalArgumentException("Number of players and number names mismatch");
        }

        if (numberOfPlayers < 2) {
            throw new IllegalArgumentException("Not enough players");
        }

        this.players = new Player[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = new Player(names[i]);
        }

        System.out.println("Number of players: " + this.numOfPlayers);
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Player " + i + ": " + players[i].getName());
        }
    }

    public int getNumberOfPlayers() {
        return numOfPlayers;
    }

    public Player[] getPlayers() {
        return players;
    }
}

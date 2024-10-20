package explodingwildcats;

public class ExplodingWildcats {

    public void setUpPlayers(int numberOfPlayers, String[] names) {
        if (names.length != numberOfPlayers) {
            throw new IllegalArgumentException("Number of players and number names mismatch");
        }

        throw new IllegalArgumentException("Not enough players");
    }
}

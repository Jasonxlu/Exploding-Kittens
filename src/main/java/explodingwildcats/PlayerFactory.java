package explodingwildcats;

public class PlayerFactory {

    public Player createPlayer(String name) {
        return new Player(name);
    }
}

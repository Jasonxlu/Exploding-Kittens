package explodingwildcats;

/**
 * Class handling player construction.
 */
public class PlayerFactory {
  /**
   * Creates and returns a new player.
   *
   * @param name name of player
   * @return Player object
   */
  public Player createPlayer(String name) {
    if (name.isEmpty()) {
      throw new IllegalArgumentException("Player name must be non-empty");
    }

    return new Player(name);
  }
}

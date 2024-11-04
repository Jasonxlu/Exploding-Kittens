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
    return new Player(name);
  }
}

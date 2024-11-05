package explodingwildcats;

/**
 * Class handling player construction.
 */
public class PlayerFactory {
  /**
   * Creates and returns a new player.
   *
   * @param name name of player
   * @param hand player hand CardPile
   * @return Player object
   */
  public Player createPlayer(String name, CardPile hand) {
    if (name.trim().isEmpty()) {
      throw new IllegalArgumentException("Player name must be non-empty");
    }

    return new Player(name, hand);
  }
}

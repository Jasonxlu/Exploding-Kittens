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
    return new Player(name, hand);
  }
}

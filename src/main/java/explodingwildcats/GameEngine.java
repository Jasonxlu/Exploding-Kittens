package explodingwildcats;

/**
 * Class responsible for setting up the game logic.
 */
public class GameEngine {
  private int numOfPlayers = 2;

  private Player[] players = null;

  private CardPile drawPile;
  private PlayerFactory playerFactory;

  /**
   * Constructor for GameEngine.
   *
   * @param playerFactory PlayerFactory object responsible for creating player instances
   * @param drawPile CardPile that players draw from
   */
  public GameEngine(PlayerFactory playerFactory, CardPile drawPile) {
    this.playerFactory = playerFactory;
    this.drawPile = drawPile;
  }

  /**
   * Adds the starting deck of cards to the drawPile.
   */
  public void createDrawPile() {
    // add all the basic cards.
    int numSkipsAndAttacks = 3;
    for (int i = 0; i < numSkipsAndAttacks; i++) {
      drawPile.addCard(Card.SKIP);
      drawPile.addCard(Card.ATTACK);
    }
    int numShufflesSeeTheFuturesNopesAndCatCardTypes = 4;
    int numberOfEachCatCard = 4;
    for (int i = 0; i < numShufflesSeeTheFuturesNopesAndCatCardTypes; i++) {
      drawPile.addCard(Card.SHUFFLE);
      drawPile.addCard(Card.SEE_THE_FUTURE);
      drawPile.addCard(Card.NOPE);
      for (int j = 0; j < numberOfEachCatCard; j++) {
        drawPile.addCard(Card.CAT);
      }
    }
  }

  /**
   * Deals with creating player objects and setting number of players.
   *
   * @param numberOfPlayers the number of players playing the game
   * @param names the names of each player
   */
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

  /**
   * Add defuse cards to both player hands and the draw pile.
   */
  public void dealDefuses() {
    int totalNumDefuses = 5;
    for (Player p : players) {
      p.addCardToHand(Card.DEFUSE);
    }
    for (int i = 0; i < totalNumDefuses - numOfPlayers; i++) {
      drawPile.addCard(Card.DEFUSE);
    }
  }

  /**
   * Deals each player 5 cards from the drawPile.
   */
  public void dealCards() {
    for (Player p : players) {
      int cardsToDealPerPlayer = 5;
      for (int i = 0; i < cardsToDealPerPlayer; i++) {
        Card cardToAdd = drawPile.popCard();
        p.addCardToHand(cardToAdd);
      }
    }
  }

  /**
   * Insert the exploding bomb cards into the drawPile.
   */
  public void insertExplodingCards() {
    for (int i = 0; i < numOfPlayers - 1; i++) {
      drawPile.addCard(Card.EXPLODE);
    }
  }

  public Card[] getDrawPile() {
    return drawPile.getCards();
  }
}

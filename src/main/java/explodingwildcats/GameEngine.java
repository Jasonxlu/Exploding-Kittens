package explodingwildcats;

import java.util.Arrays;

/**
 * Class responsible for setting up the game logic.
 */
public class GameEngine {
  private int numOfPlayers = 2;

  private Player[] players = null;

  private CardPile drawPile;
  private PlayerFactory playerFactory;
  private CardPileFactory cardPileFactory;

  private boolean isTurnOrderReversed;

  /**
   * Unit testing constructor for GameEngine.
   *
   * @param playerFactory PlayerFactory object responsible for creating player instances
   * @param cardPileFactory CardPileFactory object responsible for creating CardPile instances
   * @param drawPile CardPile that players draw from
   */
  GameEngine(PlayerFactory playerFactory,
                    CardPileFactory cardPileFactory,
                    CardPile drawPile) {
    this.playerFactory = playerFactory;
    this.cardPileFactory = cardPileFactory;
    this.drawPile = drawPile;
    isTurnOrderReversed = false;
  }

  /**
   *  Constructor for GameEngine.
   *
   * @param playerFactory PlayerFactory object responsible for creating player instances
   * @param cardPileFactory CardPileFactory object responsible for creating CardPile instances
   */
  public GameEngine(PlayerFactory playerFactory,
                    CardPileFactory cardPileFactory) {
    this.playerFactory = playerFactory;
    this.cardPileFactory = cardPileFactory;
    this.drawPile = new CardPile();
    isTurnOrderReversed = false;
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
      CardPile newHand = cardPileFactory.createCardPile();
      players[i] = playerFactory.createPlayer(names[i], newHand);
    }
  }

  public int getNumberOfPlayers() {
    return numOfPlayers;
  }

  public Player[] getPlayers() {
    return Arrays.copyOf(players, players.length);
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
        Card cardToAdd = drawPile.drawCard();
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

  /**
   * Getter for draw pile cards.
   */
  public Card[] getDrawPile() {
    return drawPile.getCards();
  }

  /**
   * Getter method for drawPile.peek().
   */
  public Card[] peekDrawPile() {
    return drawPile.peek();
  }

  /**
   * Reverses the turn order.
   */
  public void reverseTurnOrder() {
    isTurnOrderReversed = !isTurnOrderReversed;
  }

  /**
   * Getter for isTurnOrderReversed.
   */
  public boolean getIsTurnOrderReversed() {
    return isTurnOrderReversed;
  }

  /**
   * Replace the top cards in the draw pile with the cards in toSet.
   *
   * @param toSet the cards to set as the top of the draw pile.
   */
  public void replaceTopDrawPileCards(Card[] toSet) {
    int numToSet = toSet.length;
    int numInDrawPile = getDrawPile().length;
    if (numToSet > numInDrawPile) {
      throw new IllegalArgumentException(
              "Number of cards passed is greater than the number of cards in draw pile.");
    }
    for (int i = 0; i < numToSet; i++) {
      drawPile.setCard(numInDrawPile - 1 - i, toSet[i]);
    }
  }
}

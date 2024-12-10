package explodingwildcats;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class responsible for setting up the game logic.
 */
public class GameEngine {
  private int numOfPlayers = 2;

  private List<Player> players = new ArrayList<>();

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
    final int numSkipsAttacksAndTargetedAttacks = 3;
    for (int i = 0; i < numSkipsAttacksAndTargetedAttacks; i++) {
      drawPile.addCard(Card.SKIP);
      drawPile.addCard(Card.ATTACK);
      drawPile.addCard(Card.TARGETED_ATTACK);
    }
    int numShufflesFuturesNopesCatTypesReversesDrawBottomsAlterFuturesCats = 4;
    for (int i = 0; i < numShufflesFuturesNopesCatTypesReversesDrawBottomsAlterFuturesCats; i++) {
      drawPile.addCard(Card.SHUFFLE);
      drawPile.addCard(Card.SEE_THE_FUTURE);
      drawPile.addCard(Card.NOPE);
      drawPile.addCard(Card.REVERSE);
      drawPile.addCard(Card.DRAW_FROM_BOTTOM);
      drawPile.addCard(Card.ALTER_THE_FUTURE);
      drawPile.addCard(Card.TACO_CAT);
      drawPile.addCard(Card.HAIRY_POTATO_CAT);
      drawPile.addCard(Card.BEARD_CAT);
      drawPile.addCard(Card.RAINBOW_CAT);
      drawPile.addCard(Card.FERAL_CAT);
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

    if (numberOfPlayers > 6) {
      throw new IllegalArgumentException("Too many players");
    }

    this.numOfPlayers = numberOfPlayers;

    for (int i = 0; i < numberOfPlayers; i++) {
      CardPile newHand = cardPileFactory.createCardPile();
      players.add(playerFactory.createPlayer(names[i], newHand));
    }
  }

  public int getNumberOfPlayers() {
    return numOfPlayers;
  }

  public List<Player> getPlayers() {
    return new ArrayList<>(players);
  }

  /**
   * Add defuse cards to both player hands and the draw pile.
   */
  public void dealDefuses() {
    final int totalNumDefuses = 6;
    for (Player p : players) {
      p.addCardToHand(Card.DEFUSE);
    }

    int numOfDefuses = 2;
    if (numOfPlayers > 3) {
      numOfDefuses = totalNumDefuses - numOfPlayers;
    }

    for (int i = 0; i < numOfDefuses; i++) {
      drawPile.addCard(Card.DEFUSE);
    }
  }

  /**
   * Deals each player 5 cards from the drawPile.
   */
  public void dealCards() {
    for (Player p : players) {
      final int cardsToDealPerPlayer = 7;
      for (int i = 0; i < cardsToDealPerPlayer; i++) {
        Card cardToAdd = drawPile.drawCard();
        p.addCardToHand(cardToAdd);
      }
    }
  }

  /**
   * Insert the exploding bomb cards and imploding card into the drawPile.
   */
  public void insertExplodingAndImplodingCards() {
    drawPile.addCard(Card.IMPLODE);

    int numOfExploding = numOfPlayers - 2;
    if (numOfPlayers == 2) {
      numOfExploding = 1;
    }
    for (int i = 0; i < numOfExploding; i++) {
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
   * Method for shuffling the draw pile.
   */
  public void shuffleDrawPile() {
    drawPile.shuffle();
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

  /**
   * Removes and returns the bottom card in the draw pile.
   *
   */
  public Card popBottomCard() {
    return drawPile.drawCardFromBottom();
  }

  /**
   * Removes and returns the top card in the draw pile.
   *
   */
  public Card popTopCard() {
    return drawPile.drawCard();
  }

  /**
   * Checks if a player has a specified card.
   *
   * @param card card to check if a player has.
   * @param playerIndex index of the player in the players list.
   */
  public boolean playerHasCard(Card card, int playerIndex) {
    Player targetPlayer = getPlayerByIndex(playerIndex);
    return targetPlayer.hasCard(card);
  }

  /**
   * TODO: eliminates the player at that index.
   */
  public void eliminatePlayer(int playerIndex) {}

  /**
   * Removes specified card from the player at that index.
   *
   * @param card card to remove from player.
   * @param playerIndex index of the player in the players list.
   */
  public void removeCardFromPlayer(Card card, int playerIndex) {
    if (!playerHasCard(card, playerIndex)) {
      throw new NoSuchElementException("Player does not have the specified card");
    }

    Player player = getPlayerByIndex(playerIndex);
    player.removeCardFromHand(card);
  }

  /**
   * Returns the player at the index in the list or errors if it doesn't exist.
   *
   * @param playerIndex  index of the player in the players list.
   */
  public Player getPlayerByIndex(int playerIndex) {
    if (playerIndex < 0 || playerIndex >= numOfPlayers) {
      throw new IndexOutOfBoundsException("Player does not exist at this index");
    }

    return players.get(playerIndex);
  }

  /**
   * TODO: add a card to the discard pile.
   */
  public void discardCard(Card card) {}

  /**
   * TODO: add the specified card to a specified location in the card pile.
   */
  public void addCardToDrawPileAt(Card card, int index) {}

  /**
   * TODO: Gets the player in the GameEngine's Player List by their name.
   * Throws an exception if the name does not exist in the List.
   *
   * @param name the name to look for.
   *
   * @return the Player object that has that name.
   */
  public Player getPlayerByName(String name) {
    return new Player("");
  }

  /**
   * TODO: Gets the index of the player in the GameEngine's Player List by their name.
   *
   * @param name the player name to look for.
   * @return the index of the player in the list.
   */
  public int getPlayerIndexByName(String name) {
    return 0;
  }
}

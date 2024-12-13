package explodingwildcats;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.easymock.EasyMock;
import ui.UserInterface;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class EliminatePlayerSteps {

  TurnManager turnManager;
  Player playerToBeEliminated;
  private int initialNumPlayers;
  private String[] initialPlayerNames;
  Card[] initialDrawPile;

  @Given("a TurnManager with {int} players")
  public void a_turn_manager_with_players(Integer numPlayers) {
    UserInterface uiMock = EasyMock.createMock(UserInterface.class);

    String[] sampleNames = new String[numPlayers];
    for (int i = 0; i < numPlayers; i++) {
      sampleNames[i] = Integer.toString(i);
    }

    this.initialPlayerNames = sampleNames;
    initialNumPlayers = numPlayers;
    turnManager = new TurnManager(uiMock);
    turnManager.gameEngine.setUpPlayers(numPlayers, sampleNames);
  }

  @Given("current player index {int}")
  public void current_player_index(Integer currPlayerIndex) {
    turnManager.currPlayerIndex = currPlayerIndex;
    playerToBeEliminated = turnManager.gameEngine.getPlayerByIndex(currPlayerIndex);
  }

  @ParameterType(value = "true|false")
  public Boolean booleanValue(String value) {
    return Boolean.valueOf(value);
  }

  @Given("turn order reversed set to {booleanValue}")
  public void turn_order_reversed_set_to(Boolean isReversed) {
    turnManager.gameEngine.isTurnOrderReversed = isReversed;
  }

  @Given("an exploding kitten at the top of the draw pile")
  public void an_exploding_kitten_at_the_top_of_the_draw_pile() {
    Card explodingKitten = Card.EXPLODE;
    turnManager.gameEngine.addCardToDrawPileAt(explodingKitten,0);

    Card secondCard = Card.ATTACK;
    turnManager.gameEngine.addCardToDrawPileAt(secondCard,1);

    Card thirdCard = Card.SKIP;
    turnManager.gameEngine.addCardToDrawPileAt(thirdCard,2);

    initialDrawPile = new Card[] {thirdCard, secondCard, explodingKitten};
  }

  @Given("the current player has no defuses")
  public void the_current_player_has_no_defuses() {
    while (playerToBeEliminated.hasCard(Card.DEFUSE)) {
      playerToBeEliminated.removeCardFromHand(Card.DEFUSE);
    }
  }

  @When("the player draws a card to end their turn")
  public void the_player_draws_a_card_to_end_their_turn() {
    turnManager.endTurn(false);
  }

  @Then("the player is removed from the game")
  public void the_player_is_removed_from_the_game() {
    int actualNumPlayers = turnManager.gameEngine.numOfPlayers;
    int expectedNumPlayers = initialNumPlayers - 1;
    assertEquals(expectedNumPlayers, actualNumPlayers);

    List<Player> actualPlayers = turnManager.gameEngine.getPlayers();
    String[] actualPlayerNames = actualPlayers.stream()
            .map(Player::getName).toArray(String[]::new);

    // expected player names should be
    // the initial names minus the eliminated player's name.
    String[] expectedPlayerNames = Arrays.stream(initialPlayerNames).filter(name ->
            !name.equals(playerToBeEliminated.getName())).toArray(String[]::new);

    assertArrayEquals(expectedPlayerNames, actualPlayerNames);


    String expectedMessage = "No player with that name could be found.";
    Exception exception = assertThrows(NoSuchElementException.class, () -> {
      turnManager.gameEngine.getPlayerIndexByName(playerToBeEliminated.getName());
    });

    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }


  @Then("the current player index changes to {int}")
  public void the_current_player_index_changes_to(Integer newCurrPlayerIndex) {
    int actualNewIndex = turnManager.currPlayerIndex;
    assertEquals(newCurrPlayerIndex, actualNewIndex);
  }

  @Then("the exploding kitten is removed from the draw pile")
  public void the_exploding_kitten_is_removed_from_the_draw_pile() {
    Card[] actualDrawPile = turnManager.gameEngine.getDrawPile();

    // expected draw pile should be the same minus the exploding kitten.
    Card[] expectedDrawPile = new Card[2];
    expectedDrawPile[0] = initialDrawPile[0];
    expectedDrawPile[1] = initialDrawPile[1];

    assertArrayEquals(expectedDrawPile, actualDrawPile);
  }
}

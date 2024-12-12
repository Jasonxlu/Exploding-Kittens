package explodingwildcats;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.easymock.EasyMock;
import ui.UserInterface;

public class EliminatePlayerSteps {

  private TurnManager turnManager;
  private UserInterface uiMock;
  private Player playerToBeEliminated;

  @Given("a TurnManager with {int} players")
  public void a_turn_manager_with_players(Integer numPlayers) {
    uiMock = EasyMock.createMock(UserInterface.class);

    String[] sampleNames = new String[numPlayers];
    for (int i = 0; i < numPlayers; i++) {
      sampleNames[i] = Integer.toString(i);
    }

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
    Card thirdCard = Card.SKIP;
    turnManager.gameEngine.addCardToDrawPileAt(thirdCard,0);

    Card secondCard = Card.ATTACK;
    turnManager.gameEngine.addCardToDrawPileAt(secondCard,1);

    Card explodingKitten = Card.EXPLODE;
    turnManager.gameEngine.addCardToDrawPileAt(explodingKitten,2);
  }

  @Given("the current player has no defuses")
  public void the_current_player_has_no_defuses() {
    while (playerToBeEliminated.hasCard(Card.DEFUSE)) {
      playerToBeEliminated.removeCardFromHand(Card.DEFUSE);
    }
  }

  @When("the player draws a card to end their turn")
  public void the_player_draws_a_card_to_end_their_turn() {
    turnManager.endTurn();
  }

  @Then("the player is removed from the game")
  public void the_player_is_removed_from_the_game() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the turn is advanced to the next player")
  public void the_turn_is_advanced_to_the_next_player() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the exploding kitten is removed from the draw pile")
  public void the_exploding_kitten_is_removed_from_the_draw_pile() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Given("turn order is reversed true")
  public void turn_order_is_reversed_true() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

}

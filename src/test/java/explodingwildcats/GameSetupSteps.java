package explodingwildcats;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.easymock.EasyMock;
import ui.UserInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameSetupSteps {

  private TurnManager turnManager;
  private UserInterface uiMock;

  private String[] names;
  private int numPlayers;

  @Given("a newly created Turn Manager")
  public void a_newly_created_turn_manager() {
    uiMock = EasyMock.createMock(UserInterface.class);
    turnManager = new TurnManager(uiMock);
  }

  @When("setupGameEngine is called with inputs {int} and {string}")
  public void setup_game_engine_is_called_with_player_info_inputs(
          Integer numPlayers,
          String playerNames) {
    names = playerNames.split(",");
    this.numPlayers = numPlayers;

    EasyMock.expect(uiMock.getNumberOfPlayers()).andReturn(numPlayers);
    EasyMock.expect(uiMock.getPlayerNames(numPlayers)).andReturn(names);
    EasyMock.replay(uiMock);

    turnManager.setupGameEngine();

    EasyMock.verify(uiMock);
  }

  @Then("the game engine sets up the players with empty hands")
  public void the_game_engine_sets_up_the_players_with_empty_hands() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the game engine sets up the draw pile with the default cards")
  public void the_game_engine_sets_up_the_draw_pile_with_the_default_cards() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the game engine sets up the discard pile as empty")
  public void the_game_engine_sets_up_the_discard_pile_as_empty() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the game engine adds defuse cards to the player hands and draw pile")
  public void the_game_engine_adds_defuse_cards_to_the_player_hands_and_draw_pile() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the game engine deals cards to each player")
  public void the_game_engine_deals_cards_to_each_player() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the game engine inserts the exploding cards into the draw pile")
  public void the_game_engine_inserts_the_exploding_cards_into_the_draw_pile() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }
}

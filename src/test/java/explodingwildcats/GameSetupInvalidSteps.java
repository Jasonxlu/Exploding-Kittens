package explodingwildcats;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GameSetupInvalidSteps {

  @When("setupGameEngine is called with invalid inputs {int} and {string}")
  public void setup_game_engine_is_called_with_invalid_inputs_and(
          Integer numPlayers, String playerNames) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("Turn Manager throws IllegalArgumentException")
  public void turn_manager_throws_illegal_argument_exception() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }
}

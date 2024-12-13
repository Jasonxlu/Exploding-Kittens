package explodingwildcats;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class EliminatePlayerImplodingSteps {
  EliminatePlayerSteps eliminatePlayerSteps;

  public EliminatePlayerImplodingSteps(EliminatePlayerSteps eliminatePlayerSteps) {
    this.eliminatePlayerSteps = eliminatePlayerSteps;
  }

  @Given("an imploding kitten at the top of the draw pile")
  public void an_imploding_kitten_at_the_top_of_the_draw_pile() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Given("the current player has defuses")
  public void the_current_player_has_defuses() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the imploding kitten is removed from the draw pile")
  public void the_imploding_kitten_is_removed_from_the_draw_pile() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }
}

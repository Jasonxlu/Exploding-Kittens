package explodingwildcats;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class EliminatePlayerImplodingSteps {
  EliminatePlayerSteps eliminatePlayerSteps;

  public EliminatePlayerImplodingSteps(EliminatePlayerSteps eliminatePlayerSteps) {
    this.eliminatePlayerSteps = eliminatePlayerSteps;
  }

  @Given("a face up imploding kitten at the top of the draw pile")
  public void a_face_up_imploding_kitten_at_the_top_of_the_draw_pile() {
    Card implodingKitten = Card.IMPLODE;
    eliminatePlayerSteps.turnManager.gameEngine.addCardToDrawPileAt(implodingKitten,0);

    Card secondCard = Card.ATTACK;
    eliminatePlayerSteps.turnManager.gameEngine.addCardToDrawPileAt(secondCard,1);

    Card thirdCard = Card.SKIP;
    eliminatePlayerSteps.turnManager.gameEngine.addCardToDrawPileAt(thirdCard,2);

    eliminatePlayerSteps.initialDrawPile = new Card[] {thirdCard, secondCard, implodingKitten};

    eliminatePlayerSteps.turnManager.isImplodingCatFaceUp = true;
  }

  @Given("the current player has defuses")
  public void the_current_player_has_defuses() {
    // give them 3 defuses.
    eliminatePlayerSteps.playerToBeEliminated.addCardToHand(Card.DEFUSE);
    eliminatePlayerSteps.playerToBeEliminated.addCardToHand(Card.DEFUSE);
    eliminatePlayerSteps.playerToBeEliminated.addCardToHand(Card.DEFUSE);
  }

  @Then("the imploding kitten is removed from the draw pile")
  public void the_imploding_kitten_is_removed_from_the_draw_pile() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }
}

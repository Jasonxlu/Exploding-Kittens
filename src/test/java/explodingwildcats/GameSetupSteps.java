package explodingwildcats;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.easymock.EasyMock;
import ui.UserInterface;

import java.util.ArrayList;
import java.util.Arrays;
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

  @Then("the game engine adds defuse cards to the player hands and draw pile")
  public void the_game_engine_adds_defuse_cards_to_the_player_hands_and_draw_pile() {
    List<Player> actualPlayers = turnManager.gameEngine.getPlayers();

    // assert right number of defuses in player hands
    List<Card[]> hands = actualPlayers.stream()
            .map(Player::getHand).collect(Collectors.toList());

    long actualTotalNumDefuses = 0;
    for (Card[] hand : hands) {
      long actualNumberOfDefuses = Arrays.stream(hand).filter(c ->
              c == Card.DEFUSE).count();
      actualTotalNumDefuses += actualNumberOfDefuses;
      boolean playerHasAtLeast1Defuse = actualNumberOfDefuses >= 1;
      assertTrue(playerHasAtLeast1Defuse);
    }

    Card[] actualDrawPile = turnManager.gameEngine.getDrawPile();

    // assert right number of defuses across draw pile + player hands
    long actualNumDefusesInPile = Arrays.stream(actualDrawPile).filter(c ->
            c == Card.DEFUSE).count();
    actualTotalNumDefuses += actualNumDefusesInPile;
    long expectedNumDefusesInPileToBegin = (numPlayers == 2 || numPlayers == 3) ? 2 : 6 - numPlayers;
    long expectedTotalNumDefuses = expectedNumDefusesInPileToBegin + numPlayers;
    assertEquals(expectedTotalNumDefuses, actualTotalNumDefuses);
  }

  @Then("the game engine deals cards to each player")
  public void the_game_engine_deals_cards_to_each_player() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the game engine draw pile is properly set up")
  public void the_game_engine_draw_pile_is_properly_set_up() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the game engine sets up the discard pile as empty")
  public void the_game_engine_sets_up_the_discard_pile_as_empty() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }
}

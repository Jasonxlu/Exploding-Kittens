package explodingwildcats;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.easymock.EasyMock;
import ui.UserInterface;

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

  @Then("the game engine sets up the players")
  public void the_game_engine_sets_up_the_players() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
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

  @Then("the game engine sets up the draw pile and deals cards to each player")
  public void the_game_engine_sets_up_the_draw_pile_and_deals_cards_to_each_player() {
    // game engine has at least one of each card across players + draw pile
    Card[] actualDrawPile = turnManager.gameEngine.getDrawPile();
    List<Player> actualPlayers = turnManager.gameEngine.getPlayers();
    List<Card[]> actualHands = actualPlayers.stream()
            .map(Player::getHand).collect(Collectors.toList());
    long actualNumAttacks = 0;
    long actualNumDefuses = 0;
    long actualNumSeeTheFutures = 0;
    long actualNumSkips = 0;
    long actualNumTargetedAttacks = 0;
    long actualNumShuffles = 0;
    long actualNumReverses = 0;
    long actualNumDrawFromBottoms = 0;
    long actualNumAlterTheFutures = 0;
    long actualNumNopes = 0;
    long actualNumTacoCats = 0;
    long actualNumBeardCats = 0;
    long actualNumRainbowCats = 0;
    long actualNumFeralCats = 0;
    long actualNumHairyPotatoCats = 0;
    long actualNumExplodingKittens = 0;
    long actualNumImplodingKittens = 0;

    // in the players' hands
    for (Card[] hand : actualHands) {
      actualNumAttacks += Arrays.stream(hand).filter(c -> c == Card.ATTACK).count();
      actualNumDefuses += Arrays.stream(hand).filter(c -> c == Card.DEFUSE).count();
      actualNumSeeTheFutures += Arrays.stream(hand).filter(c -> c == Card.SEE_THE_FUTURE).count();
      actualNumSkips += Arrays.stream(hand).filter(c -> c == Card.SKIP).count();
      actualNumTargetedAttacks += Arrays.stream(hand).filter(c -> c == Card.TARGETED_ATTACK).count();
      actualNumShuffles += Arrays.stream(hand).filter(c -> c == Card.SHUFFLE).count();
      actualNumReverses += Arrays.stream(hand).filter(c -> c == Card.REVERSE).count();
      actualNumDrawFromBottoms += Arrays.stream(hand).filter(c -> c == Card.DRAW_FROM_BOTTOM).count();
      actualNumAlterTheFutures += Arrays.stream(hand).filter(c -> c == Card.ALTER_THE_FUTURE).count();
      actualNumNopes += Arrays.stream(hand).filter(c -> c == Card.NOPE).count();
      actualNumTacoCats += Arrays.stream(hand).filter(c -> c == Card.TACO_CAT).count();
      actualNumBeardCats += Arrays.stream(hand).filter(c -> c == Card.BEARD_CAT).count();
      actualNumRainbowCats += Arrays.stream(hand).filter(c -> c == Card.RAINBOW_CAT).count();
      actualNumFeralCats += Arrays.stream(hand).filter(c -> c == Card.FERAL_CAT).count();
      actualNumHairyPotatoCats += Arrays.stream(hand).filter(c -> c == Card.HAIRY_POTATO_CAT).count();
      actualNumExplodingKittens += Arrays.stream(hand).filter(c -> c == Card.EXPLODE).count();
      actualNumImplodingKittens += Arrays.stream(hand).filter(c -> c == Card.IMPLODE).count();
    }

    // players have no exploding/imploding kittens
    long actualTotalNumExplodingAndImplodingKittensInPlayerHands =
            actualNumExplodingKittens + actualNumImplodingKittens;
    long expectedTotalNumExplodingAndImplodingKittensInPlayerHands = 0;
    assertEquals(expectedTotalNumExplodingAndImplodingKittensInPlayerHands,
            actualTotalNumExplodingAndImplodingKittensInPlayerHands);

    // in the draw pile
    actualNumAttacks += Arrays.stream(actualDrawPile).filter(c -> c == Card.ATTACK).count();
    actualNumDefuses += Arrays.stream(actualDrawPile).filter(c -> c == Card.DEFUSE).count();
    actualNumSeeTheFutures += Arrays.stream(actualDrawPile).filter(c -> c == Card.SEE_THE_FUTURE).count();
    actualNumSkips += Arrays.stream(actualDrawPile).filter(c -> c == Card.SKIP).count();
    actualNumTargetedAttacks += Arrays.stream(actualDrawPile).filter(c -> c == Card.TARGETED_ATTACK).count();
    actualNumShuffles += Arrays.stream(actualDrawPile).filter(c -> c == Card.SHUFFLE).count();
    actualNumReverses += Arrays.stream(actualDrawPile).filter(c -> c == Card.REVERSE).count();
    actualNumDrawFromBottoms += Arrays.stream(actualDrawPile).filter(c -> c == Card.DRAW_FROM_BOTTOM).count();
    actualNumAlterTheFutures += Arrays.stream(actualDrawPile).filter(c -> c == Card.ALTER_THE_FUTURE).count();
    actualNumNopes += Arrays.stream(actualDrawPile).filter(c -> c == Card.NOPE).count();
    actualNumTacoCats += Arrays.stream(actualDrawPile).filter(c -> c == Card.TACO_CAT).count();
    actualNumBeardCats += Arrays.stream(actualDrawPile).filter(c -> c == Card.BEARD_CAT).count();
    actualNumRainbowCats += Arrays.stream(actualDrawPile).filter(c -> c == Card.RAINBOW_CAT).count();
    actualNumFeralCats += Arrays.stream(actualDrawPile).filter(c -> c == Card.FERAL_CAT).count();
    actualNumHairyPotatoCats += Arrays.stream(actualDrawPile).filter(c -> c == Card.HAIRY_POTATO_CAT).count();
    actualNumExplodingKittens += Arrays.stream(actualDrawPile).filter(c -> c == Card.EXPLODE).count();
    actualNumImplodingKittens += Arrays.stream(actualDrawPile).filter(c -> c == Card.IMPLODE).count();

    long expectedNumAttacks = 3;
    assertEquals(expectedNumAttacks, actualNumAttacks);

    long expectedNumDefusesInPileToBegin = (numPlayers == 2 || numPlayers == 3) ? 2 : 6 - numPlayers;
    long expectedNumDefuses = expectedNumDefusesInPileToBegin + numPlayers;
    assertEquals(expectedNumDefuses, actualNumDefuses);

    long expectedNumSeeTheFuture = 4;
    assertEquals(expectedNumSeeTheFuture, actualNumSeeTheFutures);

    long expectedNumSkips = 3;
    assertEquals(expectedNumSkips, actualNumSkips);

    long expectedNumTargetedAttacks = 3;
    assertEquals(expectedNumTargetedAttacks, actualNumTargetedAttacks);

    long expectedNumShuffles = 4;
    assertEquals(expectedNumShuffles, actualNumShuffles);

    long expectedNumReverses = 4;
    assertEquals(expectedNumReverses, actualNumReverses);

    long expectedNumDrawFromBottom = 4;
    assertEquals(expectedNumDrawFromBottom, actualNumDrawFromBottoms);

    long expectedNumAlterTheFutures = 4;
    assertEquals(expectedNumAlterTheFutures, actualNumAlterTheFutures);

    long expectedNumNopes = 4;
    assertEquals(expectedNumNopes, actualNumNopes);

    long expectedNumTacoCats = 4;
    assertEquals(expectedNumTacoCats, actualNumTacoCats);

    long expectedNumBeardCats = 4;
    assertEquals(expectedNumBeardCats, actualNumBeardCats);

    long expectedNumRainbowCats = 4;
    assertEquals(expectedNumRainbowCats, actualNumRainbowCats);

    long expectedNumFeralCats = 4;
    assertEquals(expectedNumFeralCats, actualNumFeralCats);

    long expectedNumHairyPotatoCats = 4;
    assertEquals(expectedNumHairyPotatoCats, actualNumHairyPotatoCats);

    // according to the rules, the total number of exploding Kittens
    // (exploding and imploding), should equal the number of players - 1.
    // We always insert one imploding kitten,
    // so it should equal the number of players - 2.
    long expectedNumExplodingKittens = numPlayers == 2 ? 1 : numPlayers - 2;
    assertEquals(expectedNumExplodingKittens, actualNumExplodingKittens);

    long expectedNumImplodingKittens = 1;
    assertEquals(expectedNumImplodingKittens, actualNumImplodingKittens);
  }

  @Then("the game engine sets up the discard pile as empty")
  public void the_game_engine_sets_up_the_discard_pile_as_empty() {
    Card[] actualDiscardPile = turnManager.gameEngine.discardPile.getCards();
    int expectedNumberOfCardsInDiscardPile = 0;
    int actualNumberOfCardsInDiscardPile = actualDiscardPile.length;
    assertEquals(expectedNumberOfCardsInDiscardPile, actualNumberOfCardsInDiscardPile);
  }
}

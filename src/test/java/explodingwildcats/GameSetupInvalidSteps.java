package explodingwildcats;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameSetupInvalidSteps {

  // This is a test, immutability is not critical here.
  @SuppressFBWarnings(value = "EI_EXPOSE_REP2",
          justification = "This is intentional for test setup in BDD.")
  GameSetupSteps gameSetupSteps;

  public GameSetupInvalidSteps(GameSetupSteps gameSetupSteps) {
    this.gameSetupSteps = gameSetupSteps;
  }

  @Then("Turn Manager throws IllegalArgumentException")
  public void turn_manager_throws_illegal_argument_exception() {
    Exception setupException = gameSetupSteps.caughtSetupException;
    Class<? extends Throwable> expectedExceptionClass = IllegalArgumentException.class;
    Class<? extends Throwable> actualExceptionClass = setupException.getClass();
    assertEquals(expectedExceptionClass, actualExceptionClass);

    String expectedExceptionMessage;
    if (gameSetupSteps.numPlayers < 2 || gameSetupSteps.numPlayers > 6) {
      expectedExceptionMessage = "Invalid number of players.";
    } else {
      expectedExceptionMessage = "Invalid number of player names.";
    }

    String actualExceptionMessage = setupException.getMessage();
    assertEquals(expectedExceptionMessage, actualExceptionMessage);
  }
}

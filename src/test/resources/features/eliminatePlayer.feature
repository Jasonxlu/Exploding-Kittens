Feature: Eliminate Player
  In order to have a player be eliminated,
  I want to test that my Turn Manager handles a player drawing an exploding kitten
  - Set up Turn Manager/Game Engine to have an exploding kitten and no defuses,
  - or a face up imploding kitten,
  - and a set number of players, and the current player being at different places in the list,
  - and finally whether the turn order is reversed
  - So that when a single turn is taken, it
  - Removes the player from the game
  - Advances the turn properly
  - Removes the Exploding/Imploding kitten from the draw pile

  Scenario Outline: Player is eliminated by exploding kitten
    Given a TurnManager with <numPlayers> players
    And current player index <currPlayerIndex>
    And turn order reversed set to <turnOrderIsReversed>
    And an exploding kitten at the top of the draw pile
    And the current player has no defuses
    When the player draws a card to end their turn
    Then the player is removed from the game
    And the current player index changes to <newCurrPlayerIndex>
    And the exploding kitten is removed from the draw pile

  Examples:
    |numPlayers|currPlayerIndex|turnOrderIsReversed|newCurrPlayerIndex|
    |2         |0              |false              |0                 |
    |3         |2              |false              |0                 |
    |4         |2              |false              |2                 |
    |6         |0              |false              |0                 |
    |2         |0              |true               |0                 |
    |3         |0              |true               |1                 |
    |4         |3              |true               |2                 |
    |6         |5              |true               |4                 |


  Scenario Outline: Player is eliminated by imploding kitten
    Given a TurnManager with <numPlayers> players
    And current player index <currPlayerIndex>
    And turn order reversed set to <turnOrderIsReversed>
    And a face up imploding kitten at the top of the draw pile
    And the current player has defuses
    When the player draws a card to end their turn
    Then the player is removed from the game
    And the current player index changes to <newCurrPlayerIndex>
    And the imploding kitten is removed from the draw pile

  Examples:
    |numPlayers|currPlayerIndex|turnOrderIsReversed|newCurrPlayerIndex|
    |2         |0              |false              |0                 |
    |3         |2              |false              |0                 |
    |4         |2              |false              |2                 |
    |6         |0              |false              |0                 |
    |2         |0              |true               |0                 |
    |3         |0              |true               |1                 |
    |4         |3              |true               |2                 |
    |6         |5              |true               |4                 |
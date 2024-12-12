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

  Scenario Outline:
    Given a TurnManager with <numPlayers> players
    And current player index <currPlayerIndex>
    And turn order reversed set to <turnOrderIsReversed>
    And an exploding kitten at the top of the draw pile
    And the current player has no defuses
    When the player draws a card to end their turn
    Then the player is removed from the game
    And the turn is advanced to the next player
    And the exploding kitten is removed from the draw pile

  Examples:
    |numPlayers|currPlayerIndex|turnOrderIsReversed|
    |2         |0              |false              |
    |3         |2              |false              |
    |6         |0              |false              |
    |2         |0              |true               |
    |4         |3              |true               |
    |6         |5              |true               |
Feature: Game setup
  In order to set up the game,
  I want my Turn Manager to set up the Game Engine:
  - Take input: number of players
  - Take input: player names
  - Set up the game engine with the number of players and their names
  - Set up the draw pile
  - Deal defuses
  - Deal cards
  - Insert exploding and imploding cards

  Scenario Outline: Properly sets up game
    Given a newly created Turn Manager
    When setupGameEngine is called
    Then the turnManager takes in the input <numPlayers>
    And the turnManager validates the number of players
    And the turnManager takes in the input <playerNames>
    And the game engine sets up <numPlayers> players with names <playerNames> and empty hands
    And the game engine sets up the draw pile with the default cards
    And the game engine sets up the discard pile as empty
    And the game engine adds defuse cards to the player hands and draw pile
    And the game engine deals cards to each player
    And the game engine inserts the exploding cards into the draw pile
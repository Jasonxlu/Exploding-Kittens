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
    When setupGameEngine is called with inputs <numPlayers> and <playerNames>
    Then the game engine adds defuse cards to the player hands and draw pile
    And the game engine sets up the draw pile and deals cards to each player
    And the game engine sets up the discard pile as empty

  Examples:
    |numPlayers|playerNames                  |
    |2         |"Jane,John"                  |
    |6         |"Jane,John,Foo,Bar,Alice,Joe"|
    |3         |"Jason,Isa,Brennan"          |
    |4         |"Brennan,Isa,Jason,Joe"      |
    |5         |"Brennan,Isa,Jason,Joe,Ava"  |


  Scenario Outline: Game setup with invalid input
    Given a newly created Turn Manager
    When setupGameEngine is called with invalid inputs <numPlayers> and <playerNames>
    Then Turn Manager throws IllegalArgumentException

  Examples:
    |numPlayers|playerNames                       |
    |1         |"Jane"                            |
    |7         |"Jane,John,Foo,Bar,Alice,Joe,Rick"|
    |2         |"Jane"                            |
    |3         |"Jane,John,Foo,Bar"               |
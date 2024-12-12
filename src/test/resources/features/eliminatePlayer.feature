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
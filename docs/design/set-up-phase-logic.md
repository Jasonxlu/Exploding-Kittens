# Setup Phase Algorithm/Logic

## Setting up the GameEngine

### Collecting User Input
- Get the number of players from the user
- Get the names of each player from the user

### Setting up players
- Create the player objects given the number of players and their names
- Create the card piles for each player's hand

### Forming the deck
- Create the game's draw pile
  - Fills the draw pile with all the playable cards in the game other than defuses

### Dealing defuses
- Each player is dealt a defuse card
- Remaining defuse cards are added to the draw pile

### Shuffling the deck
- Once the defuses are dealt, the draw pile is shuffled

### Dealing cards
- Once shuffled, Each player is dealt 7 cards from the draw pile

### Exploding/Imploding cards inserted
- An imploding card is inserted into the draw pile
- Exploding cards are inserted into the draw pile based on the number of players

### Shuffling the deck again
- Once the exploding/imploding cards are inserted, the draw pile is shuffled again to get ready for play


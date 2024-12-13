# README

This folder contains the design documents for the project.

## Design Documents

- [Game Loop Logic](game-loop-logic.md)
- [Set-Up Phase Logic](set-up-phase-logic.md)
- [Single Turn Logic](single-turn-logic.md)
- [Card Actions Logic](../requirements/characters-cards.md)

## Design Diagrams
- [Single Turn Diagram Draft](Single%20Turn%20Logic%20Diagram%20(Rough%20Draft).png)


## Design Justifications

### Design Notes
- Our `Skip` card interpretation:
    - The skip card advances the turn to the next player if the player has no extra cards to draw other than the one that ends the turn.
    - If the player has extra cards to draw, the skip card will not advance the turn to the next player, instead, it will subtract one from the number of cards the player has to draw and the player can once again play more cards before drawing again.
- The number of available cards are taken from the following game rules [base game rules](https://cdn.shopify.com/s/files/1/0345/9180/1483/files/Exploding-Kittens_Grab-N-Game_Instructions_2023.pdf?v=1712786226) and [Imploding kittens expansion](https://dumekj556jp75.cloudfront.net/imploding-kittens/imploding-english.pdf)
    - Some differences in card numbers:
        - Added an extra defuse (6 defuses available, not 5)
        - Added an extra exploding kitten (4 exploding kittens available, not 3)
- `Favor` card not included since it was omitted in rule book

### Mutation Notes:
- The mutation in `CardPile.addCardAt` is an equivalent mutation

### General testing justifications:
- Suppressed unchecked type casting warnings in card pile tests due to needing a workaround for mocking shuffling feature

### BVA Justifications:
- CardPileFactory does not have a BVA because it doesn't take input and it is a factory class
- Other BVA justifications are noted in the respective BVA doc files
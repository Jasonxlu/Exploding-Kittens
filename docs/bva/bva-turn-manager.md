# BVA Analysis for TurnManager

## Method 1: ```public void doAlterTheFuture()```
### Step 1-3 Results
|        | Input 1                                                         | Input 2                                                         | Output                                                                                                                                                                         |
|--------|-----------------------------------------------------------------|-----------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Step 1 | Card array returned by GameEngine's peekDrawPile                | Result of UI.promptNewOrder                                     | Calls GameEngine.peek(), then UI.print 3x with the resulting cards, then calls UI.promptNewOrder(# cards), then GameEngine.replaceTopDrawPileCards with the new ordered cards. |
| Step 2 | Collection (of Cards - cases)                                   | Collection (of intervals)                                       | None (outputs the current top 3 cards to the user via UI, modifies GameEngine's draw pile via GameEngine.replaceTopDrawPileCards.                                              |
| Step 3 | [one element], [more than one element], [max size (3 elements)] | [one element], [more than one element], [max size (3 elements)] | N/A (calls UI & GameEngine functions)                                                                                                                                          |
# Note: drawpile will never be empty; UI class will ensure that promptNewOrder(n) returns an array of size n that contains elements 1-n.

### Step 4:
##### All-combination or each-choice: each-choice
|              | System under test                                                                         | Expected output                                                                                                                                                                          | Implemented? |
|--------------|-------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| Test Case 1  | Card[]: [one element] (SKIP), newOrder: [one element] (1)                                 | GameEngine.peek() called, UI.print("Top: SKIP") called, UI.promptNewOrder(1) called, GameEngine.replaceTopDrawPileCards([Skip]) called                                                   | yes          |
| Test Case 2  | Card[]: [two elements] (NOPE, ATTACK), newOrder: [two elements] (2, 1)                    | GameEngine.peek() called, UI.print("Top: NOPE, 2nd: ATTACK") called, UI.promptNewOrder(2) called, GameEngine.replaceTopDrawPileCards([ATTACK, NOPE]) called                              | yes          |
| Test Case 3  | Card[]: [three elements] (IMPLODE, DEFUSE, REVERSE), newOrder: [three elements] (3, 2, 1) | GameEngine.peek() called, UI.print("Top: IMPLODE, 2nd: DEFUSE, 3rd: REVERSE") called, UI.promptNewOrder(3) called, GameEngine.replaceTopDrawPileCards([REVERSE, DEFUSE, IMPLODE]) called | yes          |
# Note: drawPile could have four elements, but we can't unit test this, because 
# in the unit test, we test the behavior of the TurnManager by setting the results of gameEngine.peek(),
# so we never actually set the number of cards in the GameEngine's draw pile.


## Method 2: ```public void doSeeTheFuture()```
### Step 1-3 Results
|        | Input                                                           | Output                                                              |
|--------|-----------------------------------------------------------------|---------------------------------------------------------------------|
| Step 1 | Card array returned by GameEngine's peekDrawPile                | Calls GameEngine.peek(), then UI.print 3x with the resulting cards. |
| Step 2 | Collection (of Card enums - cases)                              | None (outputs top 3 cards to the user via UI)                       |
| Step 3 | [one element], [more than one element], [max size (3 elements)] | None (Calls UI and GameEngine functions)                            |
### Step 4:
##### All-combination or each-choice: each-choice

|              | System under test                                 | Expected output                                     | Implemented? |
|--------------|---------------------------------------------------|-----------------------------------------------------|--------------|
| Test Case 1  | Card[]: [one element] (TARGETED_ATTACK)           | GameEngine.peek() called, UI prints the single card | yes          |
| Test Case 2  | Card[]: [two elements] (DEFUSE, IMPLODE)          | GameEngine.peek() called, UI prints two cards       | yes          |
| Test Case 3  | Card[]: [three elements] (NOPE, EXPLODE, REVERSE) | GameEngine.peek() called, UI prints three cards     | yes          |


## Method 3: ```public void doReverse()```
### Step 1-3 Results
|        | Input 1                                                             | Input 2                                                   | Output                                                                                                                        |
|--------|---------------------------------------------------------------------|-----------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------|
| Step 1 | Current player's turn                                               | GameEngine.isTurnOrderReversed                            | Calls GameEngine.reverseTurnOrder, then UserInterface.println("Turn order was reversed."), then TurnManager.endTurn().        |
| Step 2 | Cases (different Players) (changed by TurnManager.endTurn function) | Boolean (changed by GameEngine.reverseTurnOrder function) | None (Calls GameEngine.reverseTurnOrder, then UserInterface.println("Turn order was reversed."), then TurnManager.endTurn()). |
| Step 3 | TurnManager.endTurn function                                        | GameEngine.reverseTurnOrder                               | N/A (Calls GameEngine.reverseTurnOrder, then UserInterface.println("Turn order was reversed."), then TurnManager.endTurn()).  |

### Step 4:
##### All-combination or each-choice: each-choice

|              | System under test                                          | Expected output                                                                                                          | Implemented? |
|--------------|------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|--------------|
| Test Case 1  | Current state of GameEngine.isTurnReversed & player's turn | GameEngine.reverseTurnOrder(), then UserInterface.println("Turn order was reversed."), then TurnManager.endTurn() called | yes          |


## Method 4: ```public void doDrawFromBottom()```
### Step 1-3 Results
|        | Input 1                                                                                              | Output                                                                                                                                                |
|--------|------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------|
| Step 1 | GameEngine's draw pile                                                                               | Calls drawAndProcessCard(drawFromBottom = true), so that gameEngine.popBottomCard() is called instead of gameEngine.drawCard(). Then calls endTurn(). |
| Step 2 | Collection (of Cards - cases)                                                                        | None (calls drawAndProcessCard(drawFromBottom = true), then endTurn()).                                                                               |
| Step 3 | [one element], [more than one element] (Both tests covered by mocked drawAndProcessCard() function.) | None (calls drawAndProcessCard(drawFromBottom = true), then endTurn()).                                                                               |


### Step 4:
##### All-combination or each-choice: each-choice
|              | System under test                                       | Expected output                                                                    | Implemented? |
|--------------|---------------------------------------------------------|------------------------------------------------------------------------------------|--------------|
| Test Case 1  | Current state of GameEngine's draw pile & player's turn | drawAndProcessCard(drawFromBottom = true) is called, and then endTurn() is called. | yes          |


## Method 5: ```public void doAttack()```
### Step 1-3 Results
|        | Input 1                                                     | Output                                                                           |
|--------|-------------------------------------------------------------|----------------------------------------------------------------------------------|
| Step 1 | Current number of extra cards to draw (numExtraCardsToDraw) | Adds 1 to numExtraCardsToDraw if it is 0, and 2 otherwise, then calls endTurn(). |
| Step 2 | Interval                                                    | None (if numExtraCardsToDraw=0, adds 1, otherwise adds 2, then calls endTurn()). |
| Step 3 | [0,7]                                                       | None (if numExtraCardsToDraw=0, adds 1, otherwise adds 2, then calls endTurn()). |
## Note: max number of extra cards to draw is 7. There is 1 other attack + 3 targeted attacks. 
## First attack type card play = +1 extra card to draw, all next = +2. 1 + 2 * 3 = 7.


### Step 4:
##### All-combination or each-choice: each-choice
|             | System under test                              | Expected output                          | Implemented? |
|-------------|------------------------------------------------|------------------------------------------|--------------|
| Test Case 1 | numExtraCardsToDraw = 0, current player's turn | numCardsToDraw = 1, endTurn() is called. | yes          |
| Test Case 2 | numExtraCardsToDraw = 7, current player's turn | numCardsToDraw = 9, endTurn() is called. | yes          |


## Method 6: ```public void advanceTurn()```
### Step 1-3 Results
|        | Input 1           | Input 2                        | Input 3                        | Output                                                        |
|--------|-------------------|--------------------------------|--------------------------------|---------------------------------------------------------------|
| Step 1 | Number of players | Current player index           | GameEngine.isTurnOrderReversed | Current player index is updated                               |
| Step 2 | Interval          | Interval                       | Boolean                        | None (sets currPlayerIndex to [0, GameEngine.numOfPlayers-1]) |
| Step 3 | [2, 6]            | [0, GameEngine.numOfPlayers-1] | True, False                    | None                                                          |

_Note: By the game rules and previous checks, there can only be up to 6 players, therefore, the indices can only range from [0,5]_


### Step 4:
##### All-combination or each-choice: each-choice
|              | System under test                                          | Expected output    | Implemented? |
|--------------|------------------------------------------------------------|--------------------|--------------|
| Test Case 1  | numOfPlayers: 6, currPlayerIndex: 0, Reversed order: false | currPlayerIndex: 1 | yes          |
| Test Case 2  | numOfPlayers: 6, currPlayerIndex: 5, Reversed order: false | currPlayerIndex: 0 | yes          |
| Test Case 3  | numOfPlayers: 6, currPlayerIndex: 3, Reversed order: false | currPlayerIndex: 4 | yes          |
| Test Case 4  | numOfPlayers: 2, currPlayerIndex: 0, Reversed order: false | currPlayerIndex: 1 | yes          |
| Test Case 5  | numOfPlayers: 2, currPlayerIndex: 1, Reversed order: false | currPlayerIndex: 0 | yes          |
| Test Case 6  | numOfPlayers: 4, currPlayerIndex: 0, Reversed order: false | currPlayerIndex: 1 | yes          |
| Test Case 7  | numOfPlayers: 4, currPlayerIndex: 3, Reversed order: false | currPlayerIndex: 0 | yes          |
| Test Case 8  | numOfPlayers: 4, currPlayerIndex: 2, Reversed order: false | currPlayerIndex: 3 | yes          |
| Test Case 9  | numOfPlayers: 6, currPlayerIndex: 0, Reversed order: true  | currPlayerIndex: 5 | yes          |
| Test Case 10 | numOfPlayers: 6, currPlayerIndex: 5, Reversed order: true  | currPlayerIndex: 4 | yes          |
| Test Case 11 | numOfPlayers: 6, currPlayerIndex: 3, Reversed order: true  | currPlayerIndex: 2 | yes          |
| Test Case 12 | numOfPlayers: 2, currPlayerIndex: 0, Reversed order: true  | currPlayerIndex: 1 | yes          |
| Test Case 13 | numOfPlayers: 2, currPlayerIndex: 1, Reversed order: true  | currPlayerIndex: 0 | yes          |
| Test Case 14 | numOfPlayers: 4, currPlayerIndex: 0, Reversed order: true  | currPlayerIndex: 3 | yes          |
| Test Case 15 | numOfPlayers: 4, currPlayerIndex: 3, Reversed order: true  | currPlayerIndex: 2 | yes          |
| Test Case 16 | numOfPlayers: 4, currPlayerIndex: 2, Reversed order: true  | currPlayerIndex: 1 | yes          |


## Method 7: ```public void drawAndProcessCard(boolean drawFromBottom)```
### Step 1-3 Results
|        | Input 1                           | Input 2        | Output                                                                                                                           |
|--------|-----------------------------------|----------------|----------------------------------------------------------------------------------------------------------------------------------|
| Step 1 | Card drawn from the draw pile     | drawFromBottom | Executes the respective function based on the type (either calls handleExplodingKitten, handleImplodingCat or handleRegularCard) |
| Step 2 | Case                              | Boolean        |                                                                                                                                  |
| Step 3 | EXPLODE, IMPLODE, all other cases | True, False    |                                                                                                                                  |

### Step 4:
##### All-combination or each-choice: each-choice
|             | System under test                                  | Expected output               | Implemented? |
|-------------|----------------------------------------------------|-------------------------------|--------------|
| Test Case 1 | Card drawn: all other cases, drawFromBottom: False | Calls handleRegularCard()     | yes          |
| Test Case 2 | Card drawn: all other cases, drawFromBottom: True  | Calls handleRegularCard()     | yes          |
| Test Case 3 | Card drawn: EXPLODE, drawFromBottom: False         | Calls handleExplodingKitten() | yes          |
| Test Case 4 | Card drawn: EXPLODE, drawFromBottom: True          | Calls handleExplodingKitten() | yes          |
| Test Case 5 | Card drawn: IMPLODE, drawFromBottom: False         | Calls handleImplodingCat()    | yes          |
| Test Case 6 | Card drawn: IMPLODE, drawFromBottom: True          | Calls handleImplodingCat()    | yes          |


## Method 8: ```public void handleRegularCard(Card drawnCard)```
### Step 1-3 Results
|        | Input 1                                                                                                                                                                                            | Output                                                                      |
|--------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------|
| Step 1 | Drawn card                                                                                                                                                                                         | Adds the card to the player's hand or Exception (if EXPLODE/IMPLODE)        |
| Step 2 | Cases                                                                                                                                                                                              | None (adds the card to the current player's hand) or Exception              |
| Step 3 | ATTACK, DEFUSE, NOPE, SEE_THE_FUTURE, SHUFFLE, SKIP, EXPLODE, ALTER_THE_FUTURE, DRAW_FROM_BOTTOM, IMPLODE, REVERSE, TARGETED_ATTACK, FERAL_CAT, TACO_CAT, HAIRY_POTATO_CAT, BEARD_CAT, RAINBOW_CAT | None (player hand is updated and turn proceeds) or IllegalArgumentException |

### Step 4:
##### All-combination or each-choice: each-choice
|             | System under test | Expected output                      | Implemented? |
|-------------|-------------------|--------------------------------------|--------------|
| Test Case 1 | Card: SKIP        | SKIP gets added to the player's hand | yes          |
| Test Case 2 | Card: IMPLODE     | IllegalArgumentException             | yes          |
| Test Case 3 | Card: EXPLODE     | IllegalArgumentException             | yes          |


## Method 9: ```public void endTurn()```
### Step 1-3 Results
|        | Input 1                 | Output                                                                                  |
|--------|-------------------------|-----------------------------------------------------------------------------------------|
| Step 1 | numExtraCardsToDraw     | None, either calls drawAndProcessCard or advanceTurn and decrements numExtraCardsToDraw |
| Step 2 | Counts                  | None, either calls drawAndProcessCard or advanceTurn and decrements numExtraCardsToDraw |
| Step 3 | 0, 1, >1, max value (7) | None, either calls drawAndProcessCard or advanceTurn and decrements numExtraCardsToDraw |

### Step 4:
##### All-combination or each-choice: each-choice
|             | System under test | Expected output                                             | Implemented? |
|-------------|-------------------|-------------------------------------------------------------|--------------|
| Test Case 1 | draw counter: 0   | Calls advanceTurn and decrements numExtraCardsToDraw        | yes          |
| Test Case 2 | draw counter: 1   | Calls drawAndProcessCard and decrements numExtraCardsToDraw | yes          |
| Test Case 3 | draw counter: >1  | Calls drawAndProcessCard and decrements numExtraCardsToDraw | yes          |
| Test Case 4 | draw counter: 7   | Calls drawAndProcessCard and decrements numExtraCardsToDraw | yes          |


## Method 10: ```public void handleExplodingKitten()```
### Step 1-3 Results
|        | Input 1 | Output |
|--------|---------|--------|
| Step 1 |         |        |
| Step 2 |         |        |
| Step 3 |         |        |

### Step 4:
##### All-combination or each-choice: each-choice
|             | System under test | Expected output | Implemented? |
|-------------|-------------------|-----------------|--------------|
| Test Case 1 |                   |                 | no           |
| Test Case 2 |                   |                 | no           |


## Method 11: ```public void handleImplodingCat()```
### Step 1-3 Results
|        | Input 1 | Output |
|--------|---------|--------|
| Step 1 |         |        |
| Step 2 |         |        |
| Step 3 |         |        |

### Step 4:
##### All-combination or each-choice: each-choice
|             | System under test | Expected output | Implemented? |
|-------------|-------------------|-----------------|--------------|
| Test Case 1 |                   |                 | no           |
| Test Case 2 |                   |                 | no           |


## Method 12: ```public boolean promptPlayNope()```
### Step 1-3 Results
|        | Input 1                                                                                           | Input 2                                                                              | Output                                                                                                                              |
|--------|---------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------|
| Step 1 | Someone wants to play a nope card (from TurnManager.promptAndValidateNopePlayerAndPlayNopeIfSo()) | Someone wants to play a Nope card on the Nope (from recursive call to this function) | Whether or not to Nope a previously played card (T if a single Nope was played, F if input 1 is F or input 1 is T and input 2 is T) |
| Step 2 | Boolean                                                                                           | Boolean                                                                              | Boolean                                                                                                                             |
| Step 3 | T/F                                                                                               | T/F                                                                                  | T/F                                                                                                                                 |


### Step 4:
##### All-combination or each-choice: all-combination
|             | System under test                                                                                  | Expected output | Implemented? |
|-------------|----------------------------------------------------------------------------------------------------|-----------------|--------------|
| Test Case 1 | TurnManager.promptAndValidateNopePlayerAndPlayNopeIfSo() returns false. Input 2 is never obtained. | false           | no           |
| Test Case 2 | TurnManager.promptAndValidateNopePlayerAndPlayNopeIfSo() returns true. Input 2 returns true.       | false           | no           |
| Test Case 3 | TurnManager.promptAndValidateNopePlayerAndPlayNopeIfSo() returns true. Input 2 returns false.      | true            | no           |
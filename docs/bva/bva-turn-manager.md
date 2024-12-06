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



## Method 6: ```public void doShuffle()```
### Step 1-3 Results
|        | Input                                                                                                           | Output                                  |
|--------|-----------------------------------------------------------------------------------------------------------------|-----------------------------------------|
| Step 1 | Game Engine's State                                                                                             | Game Engine's shuffleDrawPile is called |
| Step 2 | Cases (Different game engine draw piles)                                                                        | None (shuffleDrawPile is called)        |
| Step 3 | draw pile [one element], draw pile [more than one element], draw pile [max size (53 elements at start of game)] | None (shuffleDrawPile is called)        |
### Step 4:
##### All-combination or each-choice: each-choice

|             | System under test                       | Expected output        | Implemented? |
|-------------|-----------------------------------------|------------------------|--------------|
| Test Case 1 | Draw pile: [DEFUSE]                     | shuffleDrawPile Called | yes          |
| Test Case 2 | Draw pile: [ATTACK, REVERSE, NOPE]      | shuffleDrawPile Called | yes          |
| Test Case 3 | Draw pile: [All 53 Cards]               | shuffleDrawPile Called | yes          |

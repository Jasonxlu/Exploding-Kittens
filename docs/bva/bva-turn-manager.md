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
|        | Input 1                                                     | Output                                                                               |
|--------|-------------------------------------------------------------|--------------------------------------------------------------------------------------|
| Step 1 | Current number of extra cards to draw (numExtraCardsToDraw) | Adds 1 to numExtraCardsToDraw if it is 0, and 2 otherwise, then calls advanceTurn(). |
| Step 2 | Cases                                                       | None (if numExtraCardsToDraw=0, adds 1, otherwise adds 2, then calls advanceTurn()). |
| Step 3 | numExtraCardsToDraw > 0, numExtraCardsToDraw = 0            | None (if numExtraCardsToDraw=0, adds 1, otherwise adds 2, then calls advanceTurn()). |

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
|        | Input 1                                         | Output                                                                                                |
|--------|-------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| Step 1 | Player has defuse                               | Cases: player either gets eliminated or not (discards defuse, adds exploding kitten back to draw pile |
| Step 2 | Boolean - comes from gameEngine.playerHasCard() | None, cases: player elimination or not                                                                |
| Step 3 | True, False                                     | None, cases: player elimination or not                                                                |

### Step 4:
##### All-combination or each-choice: each-choice
|             | System under test | Expected output                                                                                                                                                                | Implemented? |
|-------------|-------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| Test Case 1 | hasDefuse: False  | gameEngine.eliminatePlayer(currPlayerIndex) gets called with the current player index                                                                                          | yes          |
| Test Case 2 | hasDefuse: True   | Calls gameEngine.removeCardFromPlayer(Card.DEFUSE, currPlayerIndex), calls adds gameEngine.discardCard(Card.DEFUSE), calls GameEngine.addCardToDrawPileAt(Card.EXPLODE, index) | yes          |


## Method 11: ```public void handleImplodingCat()```
### Step 1-3 Results
|        | Input 1              | Output                                                                             |
|--------|----------------------|------------------------------------------------------------------------------------|
| Step 1 | isImplodingCatFaceUp | Cases: player gets eliminated or imploding card is placed in the draw pile face up |
| Step 2 | Boolean              | None, cases: player elimination or not                                             |
| Step 3 | True, False          | None, cases: player elimination or not                                             |

### Step 4:
##### All-combination or each-choice: each-choice
|             | System under test            | Expected output                                                                           | Implemented? |
|-------------|------------------------------|-------------------------------------------------------------------------------------------|--------------|
| Test Case 1 | isImplodingCardFaceUp: True  | gameEngine.eliminatePlayer(currPlayerIndex) gets called with the current player index     | yes          |
| Test Case 2 | isImplodingCardFaceUp: False | Calls GameEngine.addCardToDrawPileAt(Card.IMPLODE, index) and sets the face to be face up | yes          |


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
| Test Case 1 | TurnManager.promptAndValidateNopePlayerAndPlayNopeIfSo() returns false. Input 2 is never obtained. | false           | yes          |
| Test Case 2 | TurnManager.promptAndValidateNopePlayerAndPlayNopeIfSo() returns true. Input 2 returns true.       | false           | yes          |
| Test Case 3 | TurnManager.promptAndValidateNopePlayerAndPlayNopeIfSo() returns true. Input 2 returns false.      | true            | yes          |


## Method 13: ```public void doShuffle()```
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


## Method 14: ```public boolean promptAndValidateNopePlayerAndPlayNopeIfSo()```
### Step 1-3 Results
|        | Input 1                                    | Input 2                                                 | Input 3                                                                   | Output                                                     |
|--------|--------------------------------------------|---------------------------------------------------------|---------------------------------------------------------------------------|------------------------------------------------------------|
| Step 1 | name of the player (from ui.promptNope)    | Player with that name (from gameEngine.getPlayerByName) | Player's hand contains a Nope card (from p.removeCardFromHand(Card.NOPE)) | Whether or not a Nope card was played, T if so & F if not. |
| Step 2 | String                                     | Cases                                                   | Boolean                                                                   | Boolean                                                    |
| Step 3 | "", valid player name, invalid player name | One of the Players in the GameEngine, Exception         | T/F                                                                       | T/F                                                        |
### Note: inputs 1-3 are taken again if 1 is true and 2 is an exception or 3 is false.

### Step 4:
##### All-combination or each-choice: each-choice
|             | System under test                                                                                                                                                | Expected output | Implemented? |
|-------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------|--------------|
| Test Case 1 | input1 = "". Inputs 2 and 3 are never obtained.                                                                                                                  | false           | yes          |
| Test Case 2 | input1 = valid player name, input2 = Player with that name, input3 = true                                                                                        | true            | yes          |
| Test Case 3 | input1 = valid player name, input2 = Player with that name, input3 = false, retry with input1 = ""                                                               | false           | yes          |
| Test Case 4 | input1 = valid player name, input2 = Player with that name, input3 = false, retry with input1 = valid player name, input2 = Player with that name, input3 = true | true            | yes          |
| Test Case 5 | input1 = invalid player name, input2 = exception thrown, retry with input1 = ""                                                                                  | false           | yes          |


## Method 15: ```public void doSkip()```
### Step 1-3 Results
|        | Input                                            | Output                                                                 |
|--------|--------------------------------------------------|------------------------------------------------------------------------|
| Step 1 | TurnManager's numExtraCardsToDraw                | Turn manager's endTurn is called or numExtraCardsToDraw is decremented |
| Step 2 | Cases                                            | None (endTurn is called or number of extra cards to draw is modified)  |
| Step 3 | numExtraCardsToDraw > 0, numExtraCardsToDraw = 0 | None, turn ended or extra cards to draw is decremented                 |
### Step 4:
##### All-combination or each-choice: each-choice

|              | System under test                                              | Expected output                 | Implemented? |
|--------------|----------------------------------------------------------------|---------------------------------|--------------|
| Test Case 1  | currPlayerIndex: 0, numExtraCardsToDraw: 1                     | numExtraCardsToDraw decremented | yes          |
| Test Case 2  | currPlayerIndex: Number of players - 1, numExtraCardsToDraw: 2 | numExtraCardsToDraw decremented | yes          |
| Test Case 3  | currPlayerIndex: 0, numExtraCardsToDraw: 0                     | endTurn() is called             | yes          |


## Method 16: ```public void getPlayableCard()```
### Step 1-3 Results
|        | Input                                                                                                                                                                                                           | Output                                                                                                                  |
|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------|
| Step 1 | String representation of card                                                                                                                                                                                   | The Card object, or exception if it is not a playable card.                                                             |
| Step 2 | String                                                                                                                                                                                                          | Cases (or exception)                                                                                                    |
| Step 3 | "attack", "skip", "targeted attack", "shuffle", "see the future", "reverse", "draw from bottom", "alter the future", "invalid", "nope", "rainbow cat", "taco cat", "beard cat", "feral cat", "hairy potato cat" | Card.ATTACK, SKIP, TARGETED_ATTACK, SHUFFLE, SEE_THE_FUTURE, REVERSE, DRAW_FROM_BOTTOM, ALTER_THE_FUTURE, or exception. |
### Step 4:
##### All-combination or each-choice: all-combination

|              | System under test  | Expected output          | Implemented? |
|--------------|--------------------|--------------------------|--------------|
| Test Case 1  | "attack"           | Card.ATTACK              | yes          |
| Test Case 2  | "skip"             | Card.ATTACK              | yes          |
| Test Case 3  | "targeted attack"  | Card.TARGETED_ATTACK     | yes          |
| Test Case 4  | "shuffle"          | Card.SHUFFLE             | yes          |
| Test Case 5  | "see the future"   | Card.SEE_THE_FUTURE      | yes          |
| Test Case 6  | "reverse"          | Card.REVERSE             | yes          |
| Test Case 7  | "draw from bottom" | Card.DRAW_FROM_BOTTOM    | yes          |
| Test Case 8  | "alter the future" | Card.ALTER_THE_FUTURE    | yes          |
| Test Case 9  | "invalid"          | IllegalArgumentException | yes          |
| Test Case 10 | "nope"             | IllegalArgumentException | yes          |
| Test Case 11 | "taco cat"         | IllegalArgumentException | yes          |
| Test Case 12 | "beard cat"        | IllegalArgumentException | yes          |
| Test Case 13 | "rainbow cat"      | IllegalArgumentException | yes          |
| Test Case 14 | "feral cat"        | IllegalArgumentException | yes          |
| Test Case 15 | "hairy potato cat" | IllegalArgumentException | yes          |


## Method 17: ```public void playTurnLoop()```
### Step 1-3 Results
|        | Input 1                                                   | Input 2                                                                                                           | Input 3                                                                                            | Input 4                                                                                                                 | Input 5                               | Input 6                        | Output                                                                 |
|--------|-----------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------|---------------------------------------|--------------------------------|------------------------------------------------------------------------|
| Step 1 | userInputCard - the user's input from ui.promptPlayCard() | cardToPlay - the Card object to play (from getPlayableCard())                                                     | canPlayCard - whether the card is playable by the current player (from gameEngine.playerHasCard()) | Output from playing cat card combo - whether it was played or not (only obtained if getPlayableCard threw an exception) | Whether the player's turn has ended.  | Whether somebody played a nope | None - continue or end the turn, depending on the validity of the card |
| Step 2 | String                                                    | Cases or Exception                                                                                                | Boolean                                                                                            | Boolean                                                                                                                 | Boolean                               | Boolean                        | None - continue or end the turn, depending on the validity of the card |
| Step 3 | "", valid user input, invalid input                       | ATTACK, SKIP, TARGETED_ATTACK, SHUFFLE, SEE_THE_FUTURE, REVERSE, DRAW_FROM_BOTTOM, ALTER_THE_FUTURE, or exception | T/F                                                                                                | T/F                                                                                                                     | T/F                                   | T/F                            | Continue or end turn based on card playability and game state          |
Note: Inputs 1-3 are handled with retries if an invalid input is provided or a reprompt is needed. Input 5 doesn't matter as input because the function sets it to false at the beginning. It is only for reprompting.

### Step 4:
##### All-combination or each-choice: each-choice

|              | System under test                                                                                                                                                                                                                                                                                                               | Expected output                                                                  | Implemented? |
|--------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------|--------------|
| Test Case 1  | input1 = "". Inputs 2, 3, and 4, and 6 are never obtained. Make input 5 true so it doesn't prompt again.                                                                                                                                                                                                                        | Calls `endTurn()`                                                                | yes          |
| Test Case 2  | input1 = valid card ("attack"), input2 = ATTACK, input3 = true (can play). Input 4 = N/A. Input 6 = false. Make input 5 true so it doesn't prompt again.                                                                                                                                                                        | Calls `doAttack()`                                                               | yes          |
| Test Case 3  | input1 = valid card ("see the future"), input2 = SEE_THE_FUTURE, input3 = true (can play). Input 4 = N/A. Input 6 = false. This would make it prompt again in reality, so make input 5 false and input 1 = "". This is test case 1.                                                                                             | Calls `doSeeTheFuture()` and continues loop                                      | yes          |
| Test Case 4  | input1 = valid card ("alter the future"), input2 = ALTER_THE_FUTURE, input3 = false (cannot play it). Input 4 = N/A. Input 6 = false. This would make it reprompt. Make input 5 false and input 1 = "". This is test case 1.                                                                                                    | Calls `ui.printPlayCardDoesNotHaveCardError()` and takes input 1 again           | yes          |
| Test Case 5  | input1 = invalid card ("invalid"), input2 = exception (invalid card), input 3 and 4 = N/A. It should call promptAndPlayComboCatCards, which would make it reprompt. Input 6 = N/A. Make input 5 false and input 1 = "".                                                                                                         | Calls ui.println with the exception message and takes input 1 again              | yes          |
| Test Case 6  | (Impossible case.) input1 = valid card ("skip"), input2 = EXPLODE (impossible case), input 3 = true (also impossible) and 4 = N/A. Input 6 = false. It should throw an IllegalArgumentException.                                                                                                                                | Throws IllegalArgumentException.                                                 | yes          |
| Test Case 7  | input1 = valid card ("alter the future"), input2 = ALTER_THE_FUTURE, input3 = true (can play). Input 4 = N/A. Input 6 = false. Make input 5 false so it prompts again. input1 = valid card ("skip"), input2 = SKIP, input3 = true (can play). Input 4 = N/A. Input 6 = false. Make input 5 true so it doesn't prompt again.     | Calls `doAlterTheFuture()` and continues loop. Calls `doSkip()` and ends loop    | yes          |
| Test Case 8  | input1 = valid card ("shuffle"), input2 = SHUFFLE, input3 = true (can play). Input 4 = N/A. Input 6 = false. Make input 5 false so it prompts again. input1 = valid card ("targeted attack"), input2 = TARGETED_ATTACK, input3 = true (can play). Input 4 = N/A. Input 6 = false. Make input 5 true so it doesn't prompt again. | Calls `doShuffle()` and continues loop. Calls `doTargetedAttack()` and ends loop | yes          |
| Test Case 9  | input1 = valid card ("reverse"), input2 = REVERSE, input3 = true (can play). Input 4 = N/A. Input 6 = false. Make input 5 true so it doesn't prompt again.                                                                                                                                                                      | Calls `doReverse()` and ends loop                                                | yes          |
| Test Case 10 | input1 = valid card ("draw from bottom"), input2 = DRAW_FROM_BOTTOM, input3 = true (can play). Input 4 = N/A. Input 6 = true. Should reprompt. Use the same inputs, and make input 5 true so it doesn't prompt again.                                                                                                           | Continue loop, then calls `doDrawFromBottom()` and ends loop                     | yes          |
| Test Case 11 | input1 = valid play combo input ("2 cat cards"), input 2, 3, and 4 = N/A. Should call promptAndPlayComboCatCards(2), which returns false. Make input 5 false so it reprompts, then input 1 = "".                                                                                                                                | Calls `promptAndPlayComboCatCards(2)` and continues loop.                        | yes          |
| Test Case 12 | input1 = valid play combo input ("3 cat cards"), input 2, 3, and 4 = N/A. Should call promptAndPlayComboCatCards(3), which returns true. Make input 5 false so it reprompts, then input 1 = "".                                                                                                                                 | Calls `promptAndPlayComboCatCards(3)` and continues loop.                        | yes          |


## Method 18: ```public void doTargetedAttack()```
### Step 1-3 Results
|        | Input                                                | Input 2                                                     | Output                                                                                                                             |
|--------|------------------------------------------------------|-------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------|
| Step 1 | Name of the player from UI prompting                 | Current number of extra cards to draw (numExtraCardsToDraw) | Turn is advanced to targeted player or user is re-prompted for input, number of extra cards to draw is incremented by 1 or 2 cards |
| Step 2 | Cases                                                | Cases                                                       | User is re-prompted for input or currPlayerIndex is updated, number of extra cards to draw is modified                             |
| Step 3 | Empty string, Valid Player name, Invalid Player name | numExtraCardsToDraw > 0, numExtraCardsToDraw = 0            | User is re-prompted for input or currPlayerIndex is updated, number of extra cards to draw is modified                             |
### Step 4:
##### All-combination or each-choice: each-choice
 
|             | System under test                               | Expected output                                                                                                       | Implemented? |
|-------------|-------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------|--------------|
| Test Case 1 | Player name: ""; "John", numExtraCardsToDraw: 0 | User is re-prompted for input and currPlayerIndex is updated to John's Index, numExtraCardsToDraw is incremented by 1 | yes          |
| Test Case 2 | Player name: "John", numExtraCardsToDraw: 0     | currPlayerIndex is updated to John's index, numExtraCardsToDraw is incremented by 1                                   | yes          |
| Test Case 3 | Player name: "Jane, numExtraCardsToDraw: 7      | currPlayerIndex is updated to Jane's index, numExtraCardsToDraw is incremented by 2                                   | yes          |


## Method 20: ```public void setupGameEngine()```
### Step 1-3 Results
|        | Input                                          | Input 2             | Output                                                              |
|--------|------------------------------------------------|---------------------|---------------------------------------------------------------------|
| Step 1 | Number of players (from ui.getNumberOfPlayers) | Player names        | None, TurnManager is set up by calling the right functions in order |
| Step 2 | Interval                                       | Collection          | None, TurnManager is set up by calling the right functions in order |
| Step 3 | [2,6]                                          | [number of players] | None, TurnManager is set up by calling the right functions in order |
### Step 4:
##### All-combination or each-choice: each-choice

|             | System under test                                                   | Expected output                                                | Implemented? |
|-------------|---------------------------------------------------------------------|----------------------------------------------------------------|--------------|
| Test Case 1 | input 1: 2, input 2: ["Jane", "John"]                               | TurnManager is set up by calling the setup functions in order. | yes          |
| Test Case 2 | input 1: 6, input 2: ["Jane", "John", "Foo", "Bar", "Alice", "Joe"] | TurnManager is set up by calling the setup functions in order. | yes          |
| Test Case 3 | input 1: 1, input 2: N/A                                            | Throw exception                                                | yes          |
| Test Case 4 | input 1: 3, input 2: ["Bob", "Jeff"]                                | Throw exception                                                | yes          |
| Test Case 5 | input 1: 7, input 2: N/A                                            | Throw exception                                                | no           |
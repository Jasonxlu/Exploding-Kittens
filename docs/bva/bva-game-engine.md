# BVA Analysis for GameEngine

## Method 1: ```public void setUpPlayers(int numberOfPlayers, String[] names)```
### Step 1-3 Results
|        | Input 1           | Input 2                                                | Output                                                          |
|--------|-------------------|--------------------------------------------------------|-----------------------------------------------------------------|
| Step 1 | number of players | list of player names                                   | modified Player and numberOfPlayer fields of the class or error |
| Step 2 | interval [2, 6]   | Collection                                             | None (modified fields) or Exception                             |
| Step 3 | 1, 2, 6, 7        | [], [one element], [more than one element], [max size] | N/A (modified fields) or Exception                              |
### Step 4:
##### All-combination or each-choice: each-choice

|              | System under test                     | Expected output          | Implemented? |
|--------------|---------------------------------------|--------------------------|--------------|
| Test Case 1  | int: 1, list: [one elements]          | IllegalArgumentException | yes          |
| Test Case 2  | int: 1, list: []                      | IllegalArgumentException | yes          |
| Test Case 3  | int: 2, list: [two elements]          | None, modified fields    | yes          |
| Test Case 4  | int: 6, list: [max size]              | None, modified fields    | yes          |
| Test Case 5  | int: 7, list: [more than one element] | IllegalArgumentException | yes          |



## Method 2: ```public void dealDefuses()```
### Step 1-3 Results
|        | Input 1                             | Input 2    | Output                                                         |
|--------|-------------------------------------|------------|----------------------------------------------------------------|
| Step 1 | list of players                     | draw pile  | None, each player gets one defuse and draw pile gets remaining |
| Step 2 | Collection                          | Collection | None, modified player hand and draw pile                       |
| Step 3 | [more than one element], [max size] | [53 cards] | N/A, modified fields                                           |
### Step 4:
##### All-combination or each-choice: each-choice

|             | System under test                          | Expected output                                                                       | Implemented? |
|-------------|--------------------------------------------|---------------------------------------------------------------------------------------|--------------|
| Test Case 1 | list1: [two elements], list2: [53 cards]   | None, each player gets 1 defuse, list2: [55 cards], 2 defuses inserted into draw pile | yes          |
| Test Case 2 | list1: [max size], list2: [53 cards]       | None, each player gets 1 defuse, list2: [53 cards], 0 defuse inserted into draw pile  | yes          |
| Test Case 3 | list1: [five elements], list2: [53 cards]  | None, each player gets 1 defuse, list2: [54 cards], 1 defuses inserted into draw pile | yes          |
| Test Case 4 | list1: [three elements], list2: [53 cards] | None, each player gets 1 defuse, list2: [55 cards], 2 defuses inserted into draw pile | yes          |



## Method 3: ```public void dealCards()```
### Step 1-3 Results
|        | Input 1                                               | Input 2                             | Output                                           |
|--------|-------------------------------------------------------|-------------------------------------|--------------------------------------------------|
| Step 1 | draw pile                                             | list of player objects              | each player gets 7 more cards                    |
| Step 2 | Collection                                            | Collection                          | None (players are dealt correct number of cards) |
| Step 3 | [max size based on number of players], 53 to 55 cards | [more than one element], [max size] | N/A (players hand altered, draw pile altered)    |
### Step 4:
##### All-combination or each-choice: each-choice

|             | System under test                         | Expected output                                                                       | Implemented? |
|-------------|-------------------------------------------|---------------------------------------------------------------------------------------|--------------|
| Test Case 1 | list1: [55 cards], list2: [two elements]  | None, list1: [41 cards], list2: [two elements], with player objects card hand altered | yes          |
| Test Case 2 | list1: [53 cards], list2: [max size]      | None, list1: [11 cards], list2: [max size], with player objects card hand altered     | yes          |
| Test Case 3 | list1: [54 cards], list2: [five elements] | None, list1: [19 cards], list2: [max size], with player objects card hand altered     | yes          |



## Method 4: ```public void insertExplodingAndImplodingCards()```
### Step 1-3 Results
|        | Input 1                                               | Input 2           | Output                                                                                                                                                               |
|--------|-------------------------------------------------------|-------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Step 1 | draw pile                                             | number of players | none, draw pile gets exploding cards and imploding card                                                                                                              |
| Step 2 | Collection                                            | Interval [2, 6]   | None, draw pile field altered                                                                                                                                        |
| Step 3 | [max size based on number of players], 11 to 41 cards | 2, 6              | None, draw pile field altered if 2 players, then it's 1 exploding card and 1 imploding card, otherwise, (number of players - 2) exploding cards and 1 imploding card |
### Step 4:
##### All-combination or each-choice: each-choice

|              | System under test        | Expected output                                                 | Implemented? |
|--------------|--------------------------|-----------------------------------------------------------------|--------------|
| Test Case 1  | list: [41 cards], int: 2 | list: [43 cards], one exploding and one imploding card inserted | yes          |
| Test Case 2  | list: [11 cards], int: 6 | list: [16 cards], four exploding and 1 imploding cards inserted | yes          |


## Method 5: ```public void createDrawPile()```
### Step 1-3 Results
|        | Input 1                            | Output                                                                     |
|--------|------------------------------------|----------------------------------------------------------------------------|
| Step 1 | draw pile, always starts off empty | none, draw pile gets all starting cards with no defuses or exploding cards |
| Step 2 | Collection                         | None, draw pile field altered                                              |
| Step 3 | []                                 | None, draw pile field altered                                              |
### Step 4:
##### All-combination or each-choice: each-choice

|              | System under test | Expected output                                                                                                                                                                                                   | Implemented? |
|--------------|-------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| Test Case 1  | list: []          | list: [53 cards], 3 attacks, 4 shuffles, 3 skips, 4 future, 4 nope, 4 alter the future, 3 targeted attack, 4 reverse, 4 draw bottom, 4 taco cats, 4 hairy potato cats, 4 beard cats, 4 rainbow cats, 4 feral cats | yes          |


## Method 6: ```public void replaceTopDrawPileCards(Card[] toSet)```
### Step 1-3 Results
|        | Input                                      | Input2                                                              | Output                                                                                                                                                                  |
|--------|--------------------------------------------|---------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Step 1 | the cards in the draw pile                 | the new top cards in the draw pile                                  | CardPile.setCard should be called with the top n indices and top n cards, or an exception should be thrown.                                                             |
| Step 2 | Collection                                 | Collection                                                          | The collection contains all original elements, except the last n cards are replaced with the n cards in input2.                                                         |
| Step 3 | [], [one element], [more than one element] | [], [one element], [more than one element], [max size] (3 elements) | draw pile is altered with the last n cards, or an exception is thrown if the number if the number of cards passed is greater than the number of cards in the draw pile. |
### Step 4:
##### All-combination or each-choice: Each-choice

|             | System under test                                              | Expected output                                                                               | Implemented? |
|-------------|----------------------------------------------------------------|-----------------------------------------------------------------------------------------------|--------------|
| Test Case 1 | draw pile: [], input2: [one element]                           | Exception thrown: too many cards in input2 array                                              | yes          |
| Test Case 2 | draw pile: [one element], input2: [one element]                | CardPile.setCard(0, input2[0]) is called                                                      | yes          |
| Test Case 3 | draw pile: [two elements], input2: [two elements]              | CardPile.setCard(1, input2[0]) and CardPile.setCard(0, input2[1]) are called                  | yes          |
| Test Case 4 | draw pile: [two elements], input2: []                          | CardPile.setCard is not called.                                                               | yes          |
| Test Case 5 | draw pile: [four elements], input2: [max size, three elements] | CardPile.setCard(2, input2[0]), .setCard(1, input2[1]), and .setCard(0, input2[2]) are called | yes          |


## Method 7: ```public void reverseTurnOrder()```
### Step 1-3 Results
|        | Input 1                                                    | Output                                                   |
|--------|------------------------------------------------------------|----------------------------------------------------------|
| Step 1 | Current turn order (state of isTurnOrderReversed variable) | none, isTurnOrderReversed toggled                        |
| Step 2 | Boolean (True = reversed, False = normal order)            | None, isTurnOrderReversed is set to !isTurnOrderReversed |
| Step 3 | True, False                                                | None, isTurnOrderReversed set to !isTurnOrderReversed    |
### Step 4:
##### All-combination or each-choice: each-choice

|             | System under test          | Expected output             | Implemented? |
|-------------|----------------------------|-----------------------------|--------------|
| Test Case 1 | isTurnOrderReversed: False | isTurnOrderReversed = True  | yes          |
| Test Case 2 | isTurnOrderReversed: True  | isTurnOrderReversed = False | yes          |


## Method 8: ```public Card popBottomCard()```
### Step 1-3 Results
|        | Input 1                                | Output                                                                                                          |
|--------|----------------------------------------|-----------------------------------------------------------------------------------------------------------------|
| Step 1 | draw pile                              | The card at the bottom of draw pile is removed from the pile and returned (drawPile.popBottomCard() is called). |
| Step 2 | Collection                             | Card (cases)                                                                                                    |
| Step 3 | [one element], [more than one element] | One of the possible cards in the card enum.                                                                     |
(In step 3, the array cannot be empty, as there will always be at least one exploding kitten in the draw pile.)

### Step 4:
##### All-combination or each-choice: each-choice

|             | System under test                                                                      | Expected output                                            | Implemented? |
|-------------|----------------------------------------------------------------------------------------|------------------------------------------------------------|--------------|
| Test Case 1 | draw pile (the object will be mocked, and drawCardFromBottom set to return an ATTACK.) | drawPile.drawCardFromBottom() called, and result returned. | yes          |


## Method 9: ```public Card popTopCard()```
### Step 1-3 Results
|        | Input 1                                | Output                                                                                                    |
|--------|----------------------------------------|-----------------------------------------------------------------------------------------------------------|
| Step 1 | draw pile                              | The card at the top of draw pile is removed from the pile and returned (drawPile.popTopCard() is called). |
| Step 2 | Collection                             | Card (cases)                                                                                              |
| Step 3 | [one element], [more than one element] | One of the possible cards in the card enum.                                                               |
(In step 3, the array cannot be empty, as there will always be at least one exploding kitten in the draw pile.)

### Step 4:
##### All-combination or each-choice: each-choice

|             | System under test                                                                      | Expected output                                  | Implemented? |
|-------------|----------------------------------------------------------------------------------------|--------------------------------------------------|--------------|
| Test Case 1 | draw pile (the object will be mocked, and drawCardFromBottom set to return an ATTACK.) | drawPile.drawCard() called, and result returned. | yes          |


## Method 10: ```public boolean playerHasCard(Card card, int playerIndex)```
### Step 1-3 Results
|        | Input 1             | Input 2               | Output                                                                     |
|--------|---------------------|-----------------------|----------------------------------------------------------------------------|
| Step 1 | card                | player index in array | checks whether the player has a card or errors if the player doesn't exist |
| Step 2 | cases               | interval [0, 5]       | Boolean or exception                                                       |
| Step 3 | All Card enum cases | -1, 0, 5, 6           | True, False or IndexOutOfBoundsException                                   |

### Step 4:
##### All-combination or each-choice: each-choice
|             | System under test                | Expected output                                     | Implemented? |
|-------------|----------------------------------|-----------------------------------------------------|--------------|
| Test Case 1 | card: DEFUSE, index: -1          | IndexOutOfBoundsException                           | yes          |
| Test Case 2 | card: SHUFFLE, index: 0          | Calls Player.hasCard() with the card, returns true  | yes          |
| Test Case 3 | card: SKIP, index: 0             | Calls Player.hasCard() with the card, returns false | yes          |
| Test Case 4 | card: ATTACK, index: 5           | Calls Player.hasCard() with the card, returns true  | yes          |
| Test Case 5 | card: BEARD_CAT, index: 5        | Calls Player.hasCard() with the card, returns false | yes          |
| Test Case 6 | card: DRAW_FROM_BOTTOM, index: 6 | IndexOutOfBoundsException                           | yes          |


## Method 11: ```public void removeCardFromPlayer(Card card, int playerIndex)```
### Step 1-3 Results
|        | Input 1             | Input 2               | Input               | Output                                                                                                          |
|--------|---------------------|-----------------------|---------------------|-----------------------------------------------------------------------------------------------------------------|
| Step 1 | card                | player index in array | player has the card | removes the specified card from the player or errors if the player doesn't exist or if they don't have the card |
| Step 2 | cases               | interval [0, 5]       | Boolean             | Boolean or exception                                                                                            |
| Step 3 | All Card enum cases | -1, 0, 5, 6           | True, False         | True, False or IndexOutOfBoundsException or NoSuchElementException                                              |

### Step 4:
##### All-combination or each-choice: each-choice
|             | System under test                         | Expected output                                  | Implemented? |
|-------------|-------------------------------------------|--------------------------------------------------|--------------|
| Test Case 1 | card: DEFUSE, index: -1                   | IndexOutOfBoundsException                        | yes          |
| Test Case 2 | card: SKIP, index: 0, has card: true      | Calls Player.removeCardFromHandCard to remove it | yes          |
| Test Case 2 | card: ATTACK, index: 0, has card: false   | NoSuchElementException                           | yes          |
| Test Case 3 | card: BEARD_CAT, index: 5, has card: true | Calls Player.removeCardFromHandCard to remove it | yes          |
| Test Case 4 | card: DRAW_FROM_BOTTOM, index: 6          | IndexOutOfBoundsException                        | yes          |


## Method 12: ```public Player getPlayerByIndex(int playerIndex)```
### Step 1-3 Results
|        | Input 1               | Input 2           | Output                                                                              |
|--------|-----------------------|-------------------|-------------------------------------------------------------------------------------|
| Step 1 | player index in array | number of players | Returns the player specified by an index in the players list if it exists or errors |
| Step 2 | interval [0, 5]       | interval [2, 6]   | Pointer (reference to a player) reference or Exception                              |
| Step 3 | -1, 0, 5, 6           | 1, 2, 6, 7        | Valid pointer or IndexOutOfBoundsException                                          |

### Step 4:
##### All-combination or each-choice: each-choice
|             | System under test            | Expected output                          | Implemented? |
|-------------|------------------------------|------------------------------------------|--------------|
| Test Case 1 | index: -1, num of players: 2 | IndexOutOfBoundsException                | yes          |
| Test Case 2 | index: 0, num of players: 2  | Returns the player at 0 in the list      | yes          |
| Test Case 3 | index: 5, num of players: 6  | Returns the player at 5 in the list      | yes          |
| Test Case 4 | index: 6, num of players: 6  | IndexOutOfBoundsException                | yes          |


## Method 13: ```public void getCardByName(String cardName)```
### Step 1-3 Results
|        | Input                                                                                                                                                                                                                                                             | Output                                                      |
|--------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------|
| Step 1 | String representation of card                                                                                                                                                                                                                                     | The Card object, or exception if it is not a playable card. |
| Step 2 | String                                                                                                                                                                                                                                                            | Cases (or exception)                                        |
| Step 3 | "attack", "skip", "targeted attack", "shuffle", "see the future", "reverse", "draw from bottom", "alter the future", "invalid", "nope", "rainbow cat", "taco cat", "beard cat", "feral cat", "hairy potato cat", "exploding kitten", "imploding kitten", "defuse" | Each of the Cards in the enum or exception.                 |
### Step 4:
##### All-combination or each-choice: all-combination

|              | System under test  | Expected output          | Implemented? |
|--------------|--------------------|--------------------------|--------------|
| Test Case 1  | "attack"           | Card.ATTACK              | no           |
| Test Case 2  | "skip"             | Card.ATTACK              | no           |
| Test Case 3  | "targeted attack"  | Card.TARGETED_ATTACK     | no           |
| Test Case 4  | "shuffle"          | Card.SHUFFLE             | no           |
| Test Case 5  | "see the future"   | Card.SEE_THE_FUTURE      | no           |
| Test Case 6  | "reverse"          | Card.REVERSE             | no           |
| Test Case 7  | "draw from bottom" | Card.DRAW_FROM_BOTTOM    | no           |
| Test Case 8  | "alter the future" | Card.ALTER_THE_FUTURE    | no           |
| Test Case 9  | "invalid"          | IllegalArgumentException | no           |
| Test Case 10 | "nope"             | Card.NOPE                | no           |
| Test Case 11 | "taco cat"         | Card.TACO_CAT            | no           |
| Test Case 12 | "beard cat"        | Card.BEARD_CAT           | no           |
| Test Case 13 | "rainbow cat"      | Card.RAINBOW_CAT         | no           |
| Test Case 14 | "feral cat"        | Card.FERAL_CAT           | no           |
| Test Case 15 | "hairy potato cat" | Card.HAIRY_POTATO_CAT    | no           |
| Test Case 16 | "exploding kitten" | Card.EXPLODE             | no           |
| Test Case 17 | "imploding kitten" | Card.IMPLODE             | no           |
| Test Case 18 | "defuse"           | Card.DEFUSE              | no           |


## Method 14: ```public Card[] validateComboCards(String[] cards)```
### Step 1-3 Results
|        | Input 1                                     | Input 2                                                                                                                                           | Input 3              | Output                                            |
|--------|---------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|----------------------|---------------------------------------------------|
| Step 1 | string array of cards                       | array of Card objects                                                                                                                             | Player has each card | The validated array of Card objects, or exception |
| Step 2 | Collection                                  | Collection of Cases                                                                                                                               | Cases                | Collection of Cases or exception                  |
| Step 3 | [], [one element], [more than one element]  | [], [one element], [2x cat card], [3x cat card], [2x non-cat-card], [3x non-cat-card], [cat card combo with one feral cat], [invalid pair/triple] | T/F/exception        | input2 or IllegalArgumentException                |

### Step 4:
##### All-combination or each-choice: each-choice
|              | System under test                                                                                                    | Expected output          | Implemented? |
|--------------|----------------------------------------------------------------------------------------------------------------------|--------------------------|--------------|
| Test Case 1  | input1: [], input 2: N/A, input 3: N/A,                                                                              | IllegalArgumentException | yes          |
| Test Case 2  | input1: ["taco cat"], input2: N/A, input 3: N/A,                                                                     | IllegalArgumentException | yes          |
| Test Case 3  | input1: ["beard cat", "beard cat"], input2: [BEARD_CAT, BEARD_CAT], input 3: F                                       | input 2                  | yes          |
| Test Case 4  | input1: ["rainbow cat", "rainbow cat", "rainbow cat"], input2: [RAINBOW_CAT, RAINBOW_CAT, RAINBOW_CAT], input 3: T   | input 2                  | yes          |
| Test Case 5  | input1: ["shuffle", "shuffle"], input2: [SHUFFLE, SHUFFLE], input 3: T                                               | input 2                  | yes          |
| Test Case 6  | input1: ["see the future", "see the future", "see the future"], input2: [SEE_THE_FUTURE, SEE_THE_FUTURE], input 3: T | input 2                  | yes          |
| Test Case 7  | input1: ["feral cat", "beard cat"], input2: [FERAL_CAT, HAIRY_BEARD_CAT], input 3: T                                 | input 2                  | yes          |
| Test Case 8  | input1: ["feral cat", "beard cat", "feral cat"], input2: [FERAL_CAT, HAIRY_BEARD_CAT, FERAL_CAT], input 3: T         | input 2                  | yes          |
| Test Case 9  | input1: ["shuffle", "attack"], input2: [SHUFFLE, ATTACK], input 3: T                                                 | IllegalArgumentException | yes          |
| Test Case 10 | input1: ["shuffle", "feral cat"], input2: [SHUFFLE, FERAL_CAT], input 3: T                                           | IllegalArgumentException | yes          |
| Test Case 11 | input1: ["feral cat", "taco cat", "hairy potato cat"], input2: [FERAL_CAT, TACO_CAT, HAIRY_POTATO_CAT], input 3: T   | IllegalArgumentException | yes          |
| Test Case 12 | input1: ["feral cat", "taco cat", "hairy potato cat", "shuffle"], input2: N/A, input 3: N/A                          | IllegalArgumentException | yes          |
| Test Case 13 | input1: ["feral cat", "taco cat"], input2: [FERAL_CAT, TACO_CAT], input 3: Exception                                 | Exception from input 3   | no           |
| Test Case 14 | input1: ["feral cat", "feral cat", "feral cat"], input2: [FERAL_CAT, FERAL_CAT, FERAL_CAT], input 3: T               | input 2                  | no           |

## Recall the 4 steps of BVA
### Step 1: Describe the input and output in terms of the domain.
### Step 2: Choose the data type for the input and the output from the BVA Catalog.
### Step 3: Select concrete values along the edges for the input and the output.
### Step 4: Determine the test cases using either all-combination or each-choice strategy.

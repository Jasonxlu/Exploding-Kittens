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

|              | System under test                         | Expected output                                                                       | Implemented? |
|--------------|-------------------------------------------|---------------------------------------------------------------------------------------|--------------|
| Test Case 1  | list1: [55 cards], list2: [two elements]  | None, list1: [41 cards], list2: [two elements], with player objects card hand altered | yes          |
| Test Case 2  | list1: [53 cards], list2: [max size]      | None, list1: [11 cards], list2: [max size], with player objects card hand altered     | yes          |
| Test Case 2  | list1: [54 cards], list2: [five elements] | None, list1: [19 cards], list2: [max size], with player objects card hand altered     | yes          |



## Method 4: ```public void insertExplodingAndImplodingCards()```
### Step 1-3 Results
|        | Input 1                                               | Input 2           | Output                                                                                                                                                                 |
|--------|-------------------------------------------------------|-------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Step 1 | draw pile                                             | number of players | none, draw pile gets exploding cards and imploding card                                                                                                                |
| Step 2 | Collection                                            | Interval [2, 6]   | None, draw pile field altered                                                                                                                                          |
| Step 3 | [max size based on number of players], 11 to 41 cards | 2, 6              | None, draw pile field altered if 2-3 players, then it's 1 exploding card and 1 imploding card, otherwise, (number of players - 2) exploding cards and 1 imploding card |
### Step 4:
##### All-combination or each-choice: YOUR-DECISION

|              | System under test        | Expected output                                                 | Implemented? |
|--------------|--------------------------|-----------------------------------------------------------------|--------------|
| Test Case 1  | list: [41 cards], int: 2 | list: [43 cards], one exploding and one imploding card inserted | no           |
| Test Case 2  | list: [11 cards], int: 6 | list: [16 cards], four exploding and 1 imploding cards inserted | no           |


## Method 5: ```public void createDrawPile()```
### Step 1-3 Results
|        | Input 1                            | Output                                                                     |
|--------|------------------------------------|----------------------------------------------------------------------------|
| Step 1 | draw pile, always starts off empty | none, draw pile gets all starting cards with no defuses or exploding cards |
| Step 2 | Collection                         | None, draw pile field altered                                              |
| Step 3 | []                                 | None, draw pile field altered                                              |
### Step 4:
##### All-combination or each-choice: YOUR-DECISION

|              | System under test | Expected output                                                                                                                                             | Implemented? |
|--------------|-------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| Test Case 1  | list: []          | list: [53 cards], 3 attacks, 4 shuffles, 3 skips, 4 future, 4 nope, 4 alter the future, 3 targeted attack, 4 reverse, 4 draw bottom, 4 of each cat (5 cats) | yes          |


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
| Test Case 1 | isTurnOrderReversed: True  | isTurnOrderReversed = False | yes          |


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


## Recall the 4 steps of BVA
### Step 1: Describe the input and output in terms of the domain.
### Step 2: Choose the data type for the input and the output from the BVA Catalog.
### Step 3: Select concrete values along the edges for the input and the output.
### Step 4: Determine the test cases using either all-combination or each-choice strategy.

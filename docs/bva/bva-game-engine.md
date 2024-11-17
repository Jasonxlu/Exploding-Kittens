# BVA Analysis for GameEngine

## Method 1: ```public void setUpPlayers(int numberOfPlayers, String[] names)```
### Step 1-3 Results
|        | Input 1           | Input 2                                                | Output                                                          |
|--------|-------------------|--------------------------------------------------------|-----------------------------------------------------------------|
| Step 1 | number of players | list of player names                                   | modified Player and numberOfPlayer fields of the class or error |
| Step 2 | interval [2, 4]   | Collection                                             | None (modified fields) or Exception                             |
| Step 3 | 1, 2, 4, 5        | [], [one element], [more than one element], [max size] | N/A (modified fields) or Exception                              |
### Step 4:
##### All-combination or each-choice: each-choice

|              | System under test                     | Expected output          | Implemented? |
|--------------|---------------------------------------|--------------------------|--------------|
| Test Case 1  | int: 1, list: [one elements]          | IllegalArgumentException | yes          |
| Test Case 2  | int: 1, list: []                      | IllegalArgumentException | yes          |
| Test Case 3  | int: 2, list: [two elements]          | None, modified fields    | yes          |
| Test Case 4  | int: 4, list: [max size]              | None, modified fields    | yes          |
| Test Case 5  | int: 5, list: [more than one element] | IllegalArgumentException | yes          |



## Method 2: ```public void dealDefuses()```
### Step 1-3 Results
|        | Input 1                             | Input 2    | Output                                                         |
|--------|-------------------------------------|------------|----------------------------------------------------------------|
| Step 1 | list of players                     | draw pile  | None, each player gets one defuse and draw pile gets remaining |
| Step 2 | Collection                          | Collection | None, modified player hand and draw pile                       |
| Step 3 | [more than one element], [max size] | [34 cards] | N/A, modified fields                                           |
### Step 4:
##### All-combination or each-choice: each-choice

|              | System under test                        | Expected output                                                                        | Implemented? |
|--------------|------------------------------------------|----------------------------------------------------------------------------------------|--------------|
| Test Case 1  | list1: [two elements], list2: [34 cards] | None, each player gets 1 defuse, list2: [37 cards], 3 defuses inserted into draw pile  | yes          |
| Test Case 2  | list1: [max size], list2: [34 cards]     | None, each player gets 1 defuse, list2: [35 cards], 1 defuse inserted into draw pile   | yes          |



## Method 3: ```public void dealCards()```
### Step 1-3 Results
|        | Input 1                                               | Input 2                             | Output                                           |
|--------|-------------------------------------------------------|-------------------------------------|--------------------------------------------------|
| Step 1 | draw pile                                             | list of player objects              | each player gets 5 cards and 1 defuse cards      |
| Step 2 | Collection                                            | Collection                          | None (players are dealt correct number of cards) |
| Step 3 | [max size based on number of players], 35 to 37 cards | [more than one element], [max size] | N/A (players hand altered, draw pile altered)    |
### Step 4:
##### All-combination or each-choice: each-choice

|              | System under test                        | Expected output                                                                       | Implemented? |
|--------------|------------------------------------------|---------------------------------------------------------------------------------------|--------------|
| Test Case 1  | list1: [37 cards], list2: [two elements] | None, list1: [27 cards], list2: [two elements], with player objects card hand altered | yes          |
| Test Case 2  | list1: [35 cards], list2: [max size]     | None, list1: [15 cards], list2: [max size], with player objects card hand altered     | yes          |



## Method 4: ```public void insertExplodingCards()```
### Step 1-3 Results
|        | Input 1                                               | Input 2           | Output                                                               |
|--------|-------------------------------------------------------|-------------------|----------------------------------------------------------------------|
| Step 1 | draw pile                                             | number of players | none, draw pile gets exploding cards                                 |
| Step 2 | Collection                                            | Interval [2, 4]   | None, draw pile field altered                                        |
| Step 3 | [max size based on number of players], 15 to 27 cards | 2, 4              | None, draw pile field altered (number of player - 1) exploding cards |
### Step 4:
##### All-combination or each-choice: YOUR-DECISION

|              | System under test        | Expected output                                  | Implemented? |
|--------------|--------------------------|--------------------------------------------------|--------------|
| Test Case 1  | list: [27 cards], int: 2 | list: [28 cards], one exploding card inserted    | yes          |
| Test Case 2  | list: [15 cards], int: 4 | list: [18 cards], three exploding cards inserted | yes          |


## Method 5: ```public void createDrawPile()```
### Step 1-3 Results
|        | Input 1                            | Output                                                                     |
|--------|------------------------------------|----------------------------------------------------------------------------|
| Step 1 | draw pile, always starts off empty | none, draw pile gets all starting cards with no defuses or exploding cards |
| Step 2 | Collection                         | None, draw pile field altered                                              |
| Step 3 | []                                 | None, draw pile field altered                                              |
### Step 4:
##### All-combination or each-choice: YOUR-DECISION

|              | System under test | Expected output                                                                            | Implemented? |
|--------------|-------------------|--------------------------------------------------------------------------------------------|--------------|
| Test Case 1  | list: []          | list: [34 cards], 3 attacks, 4 shuffles, 3 skips, 4 future, 4 nope, 4 of each cat (4 cats) | yes          |



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


## Recall the 4 steps of BVA
### Step 1: Describe the input and output in terms of the domain.
### Step 2: Choose the data type for the input and the output from the BVA Catalog.
### Step 3: Select concrete values along the edges for the input and the output.
### Step 4: Determine the test cases using either all-combination or each-choice strategy.

# BVA Analysis for CardPile

## Method 1: ```public void addCard(Card c)```
### Step 1-3 Results
|        | Input                                                            | Input2                                                                                                                                                     | Output                                                                       |
|--------|------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------|
| Step 1 | arraylist of the cards belonging to the pile                     | the card to add to the pile                                                                                                                                | arraylist of cards should have the original cards plus the new card          |
| Step 2 | Collection                                                       | Case                                                                                                                                                       | The collection contains original elements and is appended an additional case |
| Step 3 | [], [one element], [more than one element]                       | Possible: ATTACK, CAT, DEFUSE, NOPE, SEE_THE_FUTURE, SHUFFLE, SKIP, EXPLODE, ALTER_THE_FUTURE, DRAW_FROM_BOTTOM, FERAL, IMPLODE, REVERSE, TARGETED_ATTACK  | cardpile is altered with the card input appended                             |
### Step 4:
##### All-combination or each-choice: Each-choice

|             | System under test                                | Expected output                                          | Implemented? |
|-------------|--------------------------------------------------|----------------------------------------------------------|--------------|
| Test Case 1 | ArrayList: [], Card: EXPLODE                     | Explode card appended to Arraylist                       | yes          |
| Test Case 2 | ArrayList: [one element], Card: ATTACK           | Attack card appended to Arraylist with one element       | yes          |
| Test Case 3 | ArrayList: [more than one element], Card: DEFUSE | Defuse card appended to Arraylist with multiple elements | yes          |


## Method 2: ```public Card[] peek()```
### Step 1-3 Results
|        | Input                                        | Output                                                       |
|--------|----------------------------------------------|--------------------------------------------------------------|
| Step 1 | Arraylist of the cards belonging to the pile | 0, 1, 2, or 3 card array at the "top" of the Cardpile        |
| Step 2 | Collection                                   | Collection of 0-3 case elements                              |
| Step 3 | [], [one element], [more than one element]   | 0-3 element card array with the top three cards to be peeked |
### Step 4:
##### All-combination or each-choice: All-combination

|              | System under test          | Expected output            | Implemented? |
|--------------|----------------------------|----------------------------|--------------|
| Test Case 1  | ArrayList: []              | Empty card array           | yes          |
| Test Case 2  | ArrayList: [one element]   | card array with 1 card     | yes          |
| Test Case 3  | ArrayList: [two elements]  | card array with 2 elements | yes          |
| Test Case 4  | ArrayList: [four elements] | card array with 3 elements | yes          |


## Method 3: ```public Card drawCard()```
### Step 1-3 Results
|        | Input                                       | Output                                                                                                                                                                                                                                 |
|--------|---------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Step 1 | ArrayList of the cards in the pile          | The card at the top of the card pile or exception, cardpile altered                                                                                                                                                                    |
| Step 2 | Collection                                  | Case (a card enum) or exception, collection altered                                                                                                                                                                                    |
| Step 3 | [], [one element], [more than one element]  | One of the following: ATTACK, CAT, DEFUSE, NOPE, SEE_THE_FUTURE, SHUFFLE, SKIP, EXPLODE, ALTER_THE_FUTURE, DRAW_FROM_BOTTOM, FERAL, IMPLODE, REVERSE, TARGETED_ATTACK or IllegalStateException, and cardpile has one less case element |
### Step 4:
##### All-combination or each-choice: each-choice

|              | System under test            | Expected output                   | Implemented? |
|--------------|------------------------------|-----------------------------------|--------------|
| Test Case 1  | ArrayList: []                | IllegalStateException             | yes          |
| Test Case 2  | ArrayList: [ATTACK]          | ATTACK card, Arraylist is size 0  | yes          |
| Test Case 3  | ArrayList: [DEFUSE, IMPLODE] | IMPLODE card, Arraylist is size 1 | yes          |

## Method 4: ```public boolean contains(Card c)```
### Step 1-3 Results
|        | Input                                      | Input2                                                                                                                                                                | Output                                           |
|--------|--------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------|
| Step 1 | the cards in the pile                      | a card                                                                                                                                                                | Checks whether the specified card is in the list |
| Step 2 | Collection                                 | Case                                                                                                                                                                  | Boolean                                          |
| Step 3 | [], [one element], [more than one element] | One of the following: ATTACK, CAT, DEFUSE, NOPE, SEE_THE_FUTURE, SHUFFLE, SKIP, EXPLODE, ALTER_THE_FUTURE, DRAW_FROM_BOTTOM, FERAL, IMPLODE, REVERSE, TARGETED_ATTACK | Match not found (false), match found (true)      |
### Step 4:
##### All-combination or each-choice: each-choice

|             | System under test                     | Expected output | Implemented? |
|-------------|---------------------------------------|-----------------|--------------|
| Test Case 1 | Pile: [], Card: ATTACK                | false           | yes          |
| Test Case 3 | Pile: [ATTACK], Card: ATTACK          | true            | yes          |
| Test Case 4 | Pile: [ATTACK], Card: SKIP            | false           | yes          |
| Test Case 5 | Pile: [DEFUSE, IMPLODE], Card: ATTACK | false           | yes          |
| Test Case 6 | Pile: [DEFUSE, IMPLODE], Card: DEFUSE | true            | yes          |

## Method 4: ```public Card setCard(int i, Card c)```
### Step 1-3 Results
|        | Input                           | Input2                                                                                                                                           | Input3                                      | Output                                                                                         |
|--------|---------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------|------------------------------------------------------------------------------------------------|
| Step 1 | Index of element to set         | Card to set                                                                                                                                      | Cards in pile                               | Card at index input1 set to input2 in the pile, or exception if the index is out of the range. |
| Step 2 | Interval                        | Cases                                                                                                                                            | Collection                                  | Card pile altered to set card at index input1 to input2, or IllegalArgumentException.          |
| Step 3 | [0 - number of cards in pile-1] | ATTACK, CAT, DEFUSE, NOPE, SEE_THE_FUTURE, SHUFFLE, SKIP, EXPLODE, ALTER_THE_FUTURE, DRAW_FROM_BOTTOM, FERAL, IMPLODE, REVERSE, TARGETED_ATTACK  | [], [one element], [more than one element]  | Cards in pile[input1] = input2, or IllegalArgumentException if input1 >= length of card pile.  |
### Step 4:
##### All-combination or each-choice: each-choice

|             | System under test                                      | Expected output                                  | Implemented? |
|-------------|--------------------------------------------------------|--------------------------------------------------|--------------|
| Test Case 1 | Index: 0, card: ATTACK, pile: []                       | IllegalArgumentException                         | yes          |
| Test Case 2 | Index: 0, card: SHUFFLE, pile: [one element]           | pile[0] = SHUFFLE                                | yes          |
| Test Case 3 | Index: 0, card: DEFUSE, pile: [two elements]           | pile[0] = DEFUSE, other card unchanged.          | yes          |
| Test Case 4 | Index: 2, card: SEE_THE_FUTURE, pile: [three elements] | pile[2] = SEE_THE_FUTURE, other cards unchanged. | yes          |


## Method 4: ```public Card drawCardFromBottom()```
### Step 1-3 Results
|        | Input                                  | Output                                   |
|--------|----------------------------------------|------------------------------------------|
| Step 1 | cardList                               | Card at index 0 in cardList popped       |
| Step 2 | Collection (of cases)                  | cardList[0] removed and returned         |
| Step 3 | [one element], [more than one element] | Card at cardList[0] removed and returned |
### Step 4:
##### All-combination or each-choice: each-choice

|             | System under test                                 | Expected output                              | Implemented? |
|-------------|---------------------------------------------------|----------------------------------------------|--------------|
| Test Case 1 | cardList: [one element: EXPLODE]                  | EXPLODE returned, cardList: []               | yes          |
| Test Case 2 | cardList: [two elements: SEE_THE_FUTURE, EXPLODE] | SEE_THE_FUTURE returned, cardList: [EXPLODE] | yes          |


## Method 5: ```public void shuffle()```
### Step 1-3 Results
|        | Input                                                                             | Output                                                                                         |
|--------|-----------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------|
| Step 1 | Card Pile array list of card objects                                              | Changes the order of the draw pile card arraylist randomly, could end up being the same order  |
| Step 2 | Collection (of Card enums - cases)                                                | None (Changes order of arraylist of cases or keeps the ordering the same)                      |
| Step 3 | [one element], [more than one element], [max size (53 elements at start of game)] | None (Changes order of arraylist of cases or keeps the ordering the same)                      | 
### Step 4:
##### All-combination or each-choice: each-choice

|              | System under test                       | Expected output    | Implemented? |
|--------------|-----------------------------------------|--------------------|--------------|
| Test Case 1  | Card Pile: [DEFUSE]                     | Same Ordering      | yes          |
| Test Case 2  | Card Pile: [ATTACK, REVERSE, NOPE]      | Same Ordering      | yes          |
| Test Case 3  | Card Pile: [Max Cards: All 53 Cards]    | Same Ordering      | yes          |
| Test Case 4  | Card Pile: [IMPLODE, EXPLODE, TACO_CAT] | Different Ordering | yes          |
| Test Case 5  | Card Pile: [Max Cards: All 53 Cards]    | Different Ordering | yes          |


## Method 6: ```public void addCardAt(Card c, int index)```
### Step 1-3 Results
|        | Input 1                     | Input 2                                                                                           | Output                                                                                                                                  |
|--------|-----------------------------|---------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| Step 1 | the card to add to the pile | index to add the card to                                                                          | the card should be added at the specified index (if it's greater than or equal to the size, just adds it to the end) or throws an error |
| Step 2 | case                        | cases                                                                                             | None (adds the card at the semantic index) or Exception                                                                                 |
| Step 3 | all card enum cases         | negative index (invalid), index within the list size (valid), index greater than the list (valid) | None (adds the card at the semantic index) or IndexOutOfBoundsException                                                                 |
### Step 4:
##### All-combination or each-choice: Each-choice

|             | System under test                                   | Expected output                                                | Implemented? |
|-------------|-----------------------------------------------------|----------------------------------------------------------------|--------------|
| Test Case 1 | index: -1, card: EXPLODE                            | Throws IndexOutOfBoundsException                               | yes          |
| Test Case 2 | index: 0, within the list size, card: ATTACK        | Adds attack to the front of the list (end of array)            | yes          |
| Test Case 3 | index: 3, within the list size, card: SHUFFLE       | Adds shuffle at index 3 (4th card from the end of the array)   | yes          |
| Test Case 4 | index: 20, greater than the list size, card: DEFUSE | Adds defuse at the bottom of the list (the front of the array) | yes          |


## Method 7: ```public boolean removeCardFromPile(Card card)```
### Step 1-3 Results
|        | Input                                  | Input 2                                      | Input 3                                                                                                                                                                                            | Output                                          |  
|--------|----------------------------------------|----------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------|  
| Step 1 | The cardpile arraylist                 | Boolean for if the cardpile is a player hand | The card being removed                                                                                                                                                                             | Boolean True/False and the CardPile is modified |  
| Step 2 | Collection                             | Boolean                                      | Cases                                                                                                                                                                                              | Boolean and collection modified                 |  
| Step 3 | [], [one element], [multiple elements] | True/False                                   | ATTACK, DEFUSE, NOPE, SEE_THE_FUTURE, SHUFFLE, SKIP, EXPLODE, ALTER_THE_FUTURE, DRAW_FROM_BOTTOM, IMPLODE, REVERSE, TARGETED_ATTACK, FERAL_CAT, TACO_CAT, HAIRY_POTATO_CAT, BEARD_CAT, RAINBOW_CAT | True/False and cardpile arraylist is modified   |  
### Step 4:
##### All-combination or each-choice: each-choice

|             | System under test                                                                | Expected output              | Implemented? |  
|-------------|----------------------------------------------------------------------------------|------------------------------|--------------|  
| Test Case 1 | Cardpile: [], isPlayerHand: true, Card: ATTACK                                   | FALSE, CardPile not modified | yes          |  
| Test Case 2 | Cardpile: [ATTACK], isPlayerHand: false, Card: SKIP                              | FALSE, CardPile not modified | yes          |  
| Test Case 3 | Cardpile: [SEE_THE_FUTURE, SHUFFLE, NOPE], isPlayerHand: false, Card: EXPLODE    | FALSE, CardPile not modified | yes          |  
| Test Case 4 | Cardpile: [DEFUSE], isPlayerHand: true, Card: DEFUSE                             | TRUE, CardPile modified      | yes          |  
| Test Case 5 | Cardpile: [SEE_THE_FUTURE, SHUFFLE, IMPLODE], isPlayerHand: false, Card: IMPLODE | TRUE, CardPile modified      | yes          |
| Test Case 6 | Cardpile: [SEE_THE_FUTURE, SHUFFLE, IMPLODE], isPlayerHand: true, Card: IMPLODE  | FALSE, CardPile not modified | yes          |
| Test Case 7 | Cardpile: [SEE_THE_FUTURE, SHUFFLE], isPlayerHand: true, Card: EXPLODE           | FALSE, CardPile not modified | yes          |



## Recall the 4 steps of BVA
### Step 1: Describe the input and output in terms of the domain.
### Step 2: Choose the data type for the input and the output from the BVA Catalog.
### Step 3: Select concrete values along the edges for the input and the output.
### Step 4: Determine the test cases using either all-combination or each-choice strategy.

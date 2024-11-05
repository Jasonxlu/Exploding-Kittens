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


## Recall the 4 steps of BVA
### Step 1: Describe the input and output in terms of the domain.
### Step 2: Choose the data type for the input and the output from the BVA Catalog.
### Step 3: Select concrete values along the edges for the input and the output.
### Step 4: Determine the test cases using either all-combination or each-choice strategy.

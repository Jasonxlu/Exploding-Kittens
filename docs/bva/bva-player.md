# BVA Analysis for Player

## Method 1: ```public void addCardToHand(Card c)```
### Step 1-3 Results
|        | Input 1                                                           | Output                            |
|--------|-------------------------------------------------------------------|-----------------------------------|
| Step 1 | card                                                              | Card added to player hand         |
| Step 2 | Case                                                              | None, player hand modified        |
| Step 3 | ATTACK, CAT, DEFUSE, NOPE, SEE_THE_FUTURE, SHUFFLE, SKIP, EXPLODE | CardPile.addCard called with card |
### Step 4:
##### All-combination or each-choice: each-choice

|             | System under test | Expected output                                  | Implemented? |
|-------------|-------------------|--------------------------------------------------|--------------|
| Test Case 1 | ATTACK            | CardPile.addCard called with ATTACK card         | yes          |
| Test Case 2 | CAT               | CardPile.addCard called with CAT card            | yes          |
| Test Case 3 | DEFUSE            | CardPile.addCard called with DEFUSE card         | yes          |
| Test Case 4 | EXPLODE           | CardPile.addCard called with EXPLODE card        | yes          |
| Test Case 5 | SEE_THE_FUTURE    | CardPile.addCard called with SEE_THE_FUTURE card | yes          |
| Test Case 6 | NOPE              | CardPile.addCard called with NOPE card           | yes          |
| Test Case 7 | SHUFFLE           | CardPile.addCard called with SHUFFLE card        | yes          |
| Test Case 7 | SKIP              | CardPile.addCard called with SKIP card           | yes          |


## Method 2: ```public boolean hasCard(Card c)```
### Step 1-3 Results
|        | Input 1                       | Output                                       |
|--------|-------------------------------|----------------------------------------------|
| Step 1 | a card                        | whether the player hand's contains that card |
| Step 2 | Case                          | Boolean                                      |
| Step 3 | All card cases from Card enum | True, False                                  |
### Step 4:
##### All-combination or each-choice: each-choice

|             | System under test              | Expected output                           | Implemented? |
|-------------|--------------------------------|-------------------------------------------|--------------|
| Test Case 1 | ATTACK, contains card: true    | Calls CardPile.contains and returns true  | yes          |
| Test Case 2 | TACO_CAT, contains card: false | Calls CardPile.contains and returns false | yes          |


## Method 3: ```public boolean removeCardFromHand(Card card)```
### Step 1-3 Results
|        | Input                                   | Input 2                                                                                                                                                                                            | Output             |
|--------|-----------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------|
| Step 1 | The player's hand (a cardpile object)   | The card being removed                                                                                                                                                                             | Boolean True/False |
| Step 2 | Collection                              | Cases                                                                                                                                                                                              | Boolean            |
| Step 3 | [], [one element], [multiple elements]  | ATTACK, DEFUSE, NOPE, SEE_THE_FUTURE, SHUFFLE, SKIP, EXPLODE, ALTER_THE_FUTURE, DRAW_FROM_BOTTOM, IMPLODE, REVERSE, TARGETED_ATTACK, FERAL_CAT, TACO_CAT, HAIRY_POTATO_CAT, BEARD_CAT, RAINBOW_CAT | True/False         |
### Step 4:
##### All-combination or each-choice: each-choice

|              | System under test                                           | Expected output | Implemented? |
|--------------|-------------------------------------------------------------|-----------------|--------------|
| Test Case 1  | Player Hand: [], Card: ATTACK                               | FALSE           |              |
| Test Case 2  | Player Hand: [ATTACK], Card: SKIP                           | FALSE           |              |
| Test Case 3  | Player Hand: [SEE_THE_FUTURE, SHUFFLE, NOPE], Card: EXPLODE | FALSE           |              |
| Test Case 4  | Player Hand: [DEFUSE], Card: DEFUSE                         | TRUE            |              |
| Test Case 5  | Player Hand: [SEE_THE_FUTURE, SHUFFLE, NOPE], Card: NOPE    | TRUE            |              |


## Recall the 4 steps of BVA
### Step 1: Describe the input and output in terms of the domain.
### Step 2: Choose the data type for the input and the output from the BVA Catalog.
### Step 3: Select concrete values along the edges for the input and the output.
### Step 4: Determine the test cases using either all-combination or each-choice strategy.

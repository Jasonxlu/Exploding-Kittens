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
|        | Input 2                                                                                                                                                                                            | Output             |
|--------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------|
| Step 1 | The card being removed                                                                                                                                                                             | Boolean True/False |
| Step 2 | Cases                                                                                                                                                                                              | Boolean            |
| Step 3 | ATTACK, DEFUSE, NOPE, SEE_THE_FUTURE, SHUFFLE, SKIP, EXPLODE, ALTER_THE_FUTURE, DRAW_FROM_BOTTOM, IMPLODE, REVERSE, TARGETED_ATTACK, FERAL_CAT, TACO_CAT, HAIRY_POTATO_CAT, BEARD_CAT, RAINBOW_CAT | True/False         |
### Step 4:
##### All-combination or each-choice: all-combination

|              | System under test      | Expected output | Implemented? |
|--------------|------------------------|-----------------|--------------|
| Test Case 1  | Card: ATTACK           | TRUE            |              |
| Test Case 2  | Card: DEFUSE           | TRUE            |              |
| Test Case 3  | Card: NOPE             | TRUE            |              |
| Test Case 4  | Card: SEE_THE_FUTURE   | TRUE            |              |
| Test Case 5  | Card: SHUFFLE          | TRUE            |              |
| Test Case 6  | Card: SKIP             | TRUE            |              |
| Test Case 7  | Card: EXPLODE          | FALSE           |              |
| Test Case 8  | Card: ALTER_THE_FUTURE | TRUE            |              |
| Test Case 9  | Card: DRAW_FROM_BOTTOM | TRUE            |              |
| Test Case 10 | Card: IMPLODE          | FALSE           |              |
| Test Case 11 | Card: REVERSE          | TRUE            |              |
| Test Case 12 | Card: TARGETED_ATTACK  | TRUE            |              |
| Test Case 13 | Card: FERAL_CAT        | TRUE            |              |
| Test Case 14 | Card: TACO_CAT         | TRUE            |              |
| Test Case 15 | Card: HAIRY_POTATO_CAT | TRUE            |              |
| Test Case 16 | Card: BEARD_CAT        | TRUE            |              |
| Test Case 17 | Card: RAINBOW_CAT      | TRUE            |              |


## Recall the 4 steps of BVA
### Step 1: Describe the input and output in terms of the domain.
### Step 2: Choose the data type for the input and the output from the BVA Catalog.
### Step 3: Select concrete values along the edges for the input and the output.
### Step 4: Determine the test cases using either all-combination or each-choice strategy.

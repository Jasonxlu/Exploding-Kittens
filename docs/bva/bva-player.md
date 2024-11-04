# BVA Analysis for Player

## Method 1: ```public void addCardToHand(Card c)```
### Step 1-3 Results
|        | Input 1                                                 | Input 2                                                           | Output                            |
|--------|---------------------------------------------------------|-------------------------------------------------------------------|-----------------------------------|
| Step 1 | hand                                                    | card                                                              | Player hand with card added       |
| Step 2 | Collection                                              | Case                                                              | None, player hand modified        |
| Step 3 | [], [one element], [more than one element], [max size]  | ATTACK, CAT, DEFUSE, NOPE, SEE_THE_FUTURE, SHUFFLE, SKIP, EXPLODE | CardPile.addCard called with card |
### Step 4:
##### All-combination or each-choice: each-choice

|             | System under test                      | Expected output                                  | Implemented? |
|-------------|----------------------------------------|--------------------------------------------------|--------------|
| Test Case 1 | list: [one element], card: ATTACK      | CardPile.addCard called with ATTACK card         |              |
| Test Case 2 | list: [], card: CAT                    | CardPile.addCard called with CAT card            |              |
| Test Case 3 | list: [two elements], card: DEFUSE     | CardPile.addCard called with DEFUSE card         |              |
| Test Case 4 | list: [max size] (39), card: EXPLODE   | CardPile.addCard called with EXPLODE card        |              |
| Test Case 5 | list: [three elements], SEE_THE_FUTURE | CardPile.addCard called with SEE_THE_FUTURE card |              |
| Test Case 6 | list: [one element], NOPE              | CardPile.addCard called with NOPE card           |              |
| Test Case 7 | list: [four elements], SHUFFLE         | CardPile.addCard called with SHUFFLE card        |              |
| Test Case 7 | list: [], SKIP                         | CardPile.addCard called with SKIP card           |              |


## Method 1: ```public void getHand()```
### Step 1-3 Results
|        | Input 1                                                | Output                                                      |
|--------|--------------------------------------------------------|-------------------------------------------------------------|
| Step 1 | hand                                                   | Player hand (CardPile.getCards called)                      |
| Step 2 | Collection                                             | Player hand as an array of cards (CardPile.getCards called) |
| Step 3 | [], [one element], [more than one element], [max size] | CardPile.getCards called                                    |
### Step 4:
##### All-combination or each-choice: each-choice

|             | System under test     | Expected output           | Implemented? |
|-------------|-----------------------|---------------------------|--------------|
| Test Case 1 | list: []              | CardPile.getCards called  |              |
| Test Case 2 | list: [one element]   | CardPile.getCards called  |              |
| Test Case 3 | list: [two elements]  | CardPile.getCards called  |              |
| Test Case 4 | list: [max size] (40) | CardPile.getCards called  |              |


## Recall the 4 steps of BVA
### Step 1: Describe the input and output in terms of the domain.
### Step 2: Choose the data type for the input and the output from the BVA Catalog.
### Step 3: Select concrete values along the edges for the input and the output.
### Step 4: Determine the test cases using either all-combination or each-choice strategy.

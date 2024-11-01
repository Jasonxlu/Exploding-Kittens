# BVA Analysis for GameEngine

## Method 1: ```public void AddCardToHand(Card c)```
### Step 1-3 Results
|        | Input 1                                                 | Input 2                                                           | Output                      |
|--------|---------------------------------------------------------|-------------------------------------------------------------------|-----------------------------|
| Step 1 | hand                                                    | card                                                              | Player hand with card added |
| Step 2 | Collection                                              | Case                                                              | None, player hand modified  |
| Step 3 | [], [one element], [more than one element], [max size]  | ATTACK, CAT, DEFUSE, NOPE, SEE_THE_FUTURE, SHUFFLE, SKIP, EXPLODE | Modified player hand        |
### Step 4:
##### All-combination or each-choice: each-choice

|             | System under test                      | Expected output                          | Implemented? |
|-------------|----------------------------------------|------------------------------------------|--------------|
| Test Case 1 | list: [one element], card: ATTACK      | ATTACK card added to player hand         |              |
| Test Case 2 | list: [], card: CAT                    | CAT card added to player hand            |              |
| Test Case 3 | list: [two elements], card: DEFUSE     | DEFUSE card added to player hand         |              |
| Test Case 4 | list: [max size] (39), card: EXPLODE   | EXPLODE card added to player hand        |              |
| Test Case 5 | list: [three elements], SEE_THE_FUTURE | SEE_THE_FUTURE card added to player hand |              |
| Test Case 6 | list: [one element], NOPE              | NOPE card added to player hand           |              |
| Test Case 7 | list: [four elements], SHUFFLE         | SHUFFLE card added to player hand        |              |
| Test Case 7 | list: [], SKIP                         | SKIP card added to player hand           |              |


## Recall the 4 steps of BVA
### Step 1: Describe the input and output in terms of the domain.
### Step 2: Choose the data type for the input and the output from the BVA Catalog.
### Step 3: Select concrete values along the edges for the input and the output.
### Step 4: Determine the test cases using either all-combination or each-choice strategy.

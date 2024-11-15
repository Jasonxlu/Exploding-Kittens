# BVA Analysis for TurnManager

## Method 1: ```public void doAlterTheFuture()```
### Step 1-3 Results
|        | Input 1                                | Input 2                                                         | Output                                                                                                                                                                         |
|--------|----------------------------------------|-----------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Step 1 | GameEngine's drawPile                  | Result of UI.promptNewOrder                                     | Calls GameEngine.peek(), then UI.print 3x with the resulting cards, then calls UI.promptNewOrder(# cards), then GameEngine.replaceTopDrawPileCards with the new ordered cards. |
| Step 2 | Collection (of Cards - cases)          | Collection (of intervals)                                       | None (outputs the current top 3 cards to the user via UI, modifies GameEngine's draw pile via GameEngine.replaceTopDrawPileCards.                                              |
| Step 3 | [one element], [more than one element] | [one element], [more than one element], [max size (3 elements)] | N/A (calls UI & GameEngine functions)                                                                                                                                          |
# Note: drawpile will never be empty; UI class will ensure that promptNewOrder(n) returns an array of size n that contains elements 1-n.

### Step 4:
##### All-combination or each-choice: each-choice

|              | System under test                                                                                          | Expected output                                                                                                                                                                          | Implemented? |
|--------------|------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| Test Case 1  | drawPile: [one element] (SKIP), newOrder: [one element] (1)                                                | GameEngine.peek() called, UI.print("Top: SKIP") called, UI.promptNewOrder(1) called, GameEngine.replaceTopDrawPileCards([Skip]) called                                                   | yes          |
| Test Case 2  | drawPile: [two elements] (NOPE, ATTACK), newOrder: [two elements] (2, 1)                                   | GameEngine.peek() called, UI.print("Top: ATTACK, 2nd: NOPE") called, UI.promptNewOrder(2) called, GameEngine.replaceTopDrawPileCards([ATTACK, NOPE]) called                              | yes          |
| Test Case 3  | drawPile: [three elements] (IMPLODE, DEFUSE, REVERSE), newOrder: [two elements] (2, 1, 3)                  | GameEngine.peek() called, UI.print("Top: REVERSE, 2nd: DEFUSE, 3rd: IMPLODE") called, UI.promptNewOrder(3) called, GameEngine.replaceTopDrawPileCards([DEFUSE, REVERSE, IMPLODE]) called | yes          |
| Test Case 4  | drawPile: [four elements] (FERAL, DRAW_FROM_BOTTOM, EXPLODE, DEFUSE), newOrder: [three elements] (3, 1, 2) | GameEngine.peek() called, UI.print("Top: IMPLODE, 2nd: DEFUSE, 3rd: REVERSE") called, UI.promptNewOrder(3) called, GameEngine.replaceTopDrawPileCards([REVERSE, IMPLODE, DEFUSE]) called | no           |

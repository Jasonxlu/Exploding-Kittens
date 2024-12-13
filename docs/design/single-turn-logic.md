# Single Turn Algorithm

## Playing a Single Turn

### Turn Initialization
- **Set `playerTurnHasEnded` to `false`.**  
  Ensures the turn continues until explicitly ended by the player.

### Turn Execution Loop
- **Repeat while `playerTurnHasEnded` is `false`:**
    1. **Display the player's current hand.**
        - Call `printPlayerHand(currPlayerIndex)` to show the cards held by the current player.

    2. **Prompt the player to play a card.**
        - Use `ui.promptPlayCard(shouldReprompt)` to request input.
        - If the input is empty, the player chooses to end their turn:
            - Call `endTurn()` to end their turn.
            - Set `shouldReprompt` to `false` and continue to the next turn.

    3. **Handle combo actions:**
        - If the player selects "2 cards," call `promptAndPlayCombo(2)`.
            - Continue the loop after the action is resolved.
        - If the player selects "3 cards," call `promptAndPlayCombo(3)`.
            - Continue the loop after the action is resolved.

    4. **Validate card selection:**
        - Attempt to retrieve the selected card using `getPlayableCard(userInputCard)`.
        - If invalid, catch the exception and set `shouldReprompt` to `true` to re-prompt the player.

    5. **Check if the player has the selected card:**
        - Use `gameEngine.playerHasCard(cardToPlay, currPlayerIndex)` to confirm the card is in their hand.
        - If not, set `shouldReprompt` to `true` to request valid input.

    6. **Handle "Nope" interruptions:**
        - Prompt other players to play a "Nope" card using `promptPlayNope()`.
            - If a "Nope" is played, set `shouldReprompt` to `false` and continue the turn loop.

    7. **Resolve the card's effect:**
        - Based on the card type, execute its corresponding action:
            - `ATTACK`: Call `doAttack()`.
            - `SKIP`: Call `doSkip()`.
            - `TARGETED_ATTACK`: Call `doTargetedAttack()`.
            - `SHUFFLE`: Call `doShuffle()`.
            - `SEE_THE_FUTURE`: Call `doSeeTheFuture()`.
            - `REVERSE`: Call `doReverse()`.
            - `DRAW_FROM_BOTTOM`: Call `doDrawFromBottom()`.
            - `ALTER_THE_FUTURE`: Call `doAlterTheFuture()`.
            - **Default behavior:** Throw an exception if an invalid card type is attempted.

    8. **Reset reprompt status after a valid play:**
        - Once a valid card is played and resolved, set `shouldReprompt` to `false`.

### Ending the Turn
- **When the player decides not to play any more cards and if none of the previously played cards have ended their turn:**
    - The loop terminates, and the turn moves to the next player.
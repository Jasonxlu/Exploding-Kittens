# BVA Analysis for PlayerFactory

## Method 1: ```public Player createPlayer(String name)```
### Step 1-3 Results
|        | Input                                        | Output                                                |
|--------|----------------------------------------------|-------------------------------------------------------|
| Step 1 | player name                                  | a player or error                                     |
| Step 2 | String                                       | Custom Object - Player (with input name) or Exception |
| Step 3 | an empty string, a non-empty (normal) string | Custom Object - Player (with input name) or Exception |
### Step 4:
##### All-combination or each-choice: YOUR-DECISION

|              | System under test | Expected output          | Implemented? |
|--------------|-------------------|--------------------------|--------------|
| Test Case 1  | ""                | IllegalArgumentException | yes          |
| Test Case 2  | "John"            | Player (name = John)     | yes          |
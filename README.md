Connect-Four (Terminal)
========================

A minimal console-based Connect-Four implementation used for course exercises.

Group Members
-----
- Patrick Casseus
- Dante Hurr
- Jaksh Patel

Files
-----
- `Board.java`      — board state and win/draw logic
- `Game.java`       — game loop and user command handling
- `Hint.java`       — simple two-ply hint engine classifying safe/unsafe moves
- `Main.java`       — application entry point
- `Move.java`       — immutable move record (row/col/token)
- `MoveStack.java`  — LIFO stack used for undo functionality
- `MyLinkedList.java` — tiny linked list used by the hint engine
- `Player.java`     — player descriptor (name and token)
- `TurnQueue.java`  — circular queue to manage player turns
- `AIPlayer.java` — abstract superclass for AI implementations (easy, medium, hard)
- `AVLTree.java` — self-balancing tree storing tournament standings sorted by wins and name
- `EasyAI.java` — basic AI that selects random valid columns
- `HardAI.java` — advanced AI using recursive minimax search with scoring
- `Match.java` — stores metadata and outcome for a single tournament match
- `MediumAI.java` — intermediate AI that blocks wins, favors center, avoids traps
- `PlayerManager.java` — handles login, registration, profile lookup, and data persistence
- `PlayerProfile.java` — persistent stats for a registered player, including rolling history
- `Tournament.java` — manages round-robin scheduling, match queue, and match execution
- `TournamentEntry.java` — wrapper for a player's tournament stats (wins, losses, name)
- `TournamentStats.java` — node wrapper used by AVLTree for sorting and output


Prerequisites
-------------
- Java 11+ (javac and java on PATH)
- macOS / Linux / Windows terminal

Compile
-------
From the project directory (where the `.java` files live) run:

```bash
javac *.java
```

This will produce `.class` files next to each source file.

Run / Play (interactive)
------------------------
Start the game:

```bash
java Main
```

The game runs interactively in the terminal. Prompts are displayed for the current player.

Available commands (type at the prompt):
- `[0-<cols-1>]` — enter a column number to drop your token in that column (0-based index)
- `undo` — undo the last move (restores the board and the turn order)
- `hint` — compute and print a simple hint: recommended, safe and unsafe moves
- `board` — reprint the current board snapshot
- `restart` — clear the board and start a new game (player order is preserved)
- `help` — show the help menu
- `quit` — exit the game

Newer Commands (type at the prompt):
- `register <name>` — create a new player account
- `login <name>` — login as an existing player
- `logout` — logout of the current session
- `whoami` — show the current logged-in user
- `profile <name>` — show stats and history for a user
- `leaderboard top N` — show top N players by win count
- `game start human [X O]` — start human vs human
- `game start ai <level>` — start human vs AI (easy, med, hard)
- `tournament create <id> <p1,p2,...>` — create a tournament
- `tournament start <id>` — schedule all matches
- `next` — play the next match in the queue (AI vs AI)
- `tournament standings <id>` — show AVL-based standings


Notes on gameplay and features
-----------------------------
- Two players are configured by default: Player 1 uses `X`, Player 2 uses `O`.
- The board default is 6 rows x 7 columns and connect-4 win condition.
- The hint engine is a shallow two-ply simulation that detects immediate
  opponent wins after your candidate move (it does not perform deep search).
- The undo feature reverses the last move(s); in AI games, both the player and AI moves are undone.
- The hint engine avoids unsafe moves that allow immediate losses on the opponent’s next turn.

-----------------------------

**AI difficulty levels include:**
- Easy — selects random valid columns.
- Medium — blocks wins, prefers center, avoids traps.
- Hard — uses recursive minimax search with a static evaluation function.

-----------------------------

- Player accounts persist stats across sessions, including total and AI-specific win/draw/loss counts.
- Each player profile keeps a rolling history of their last 10 game outcomes.
- Tournaments simulate round-robin play between players using AI.
- Tournament standings are stored in an AVL tree and ranked by win count (descending), then player name (ascending).

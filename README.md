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

Notes on gameplay and features
-----------------------------
- Two players are configured by default: Player 1 uses `X`, Player 2 uses `O`.
- The board default is 6 rows x 7 columns and connect-4 win condition.
- The hint engine is a shallow two-ply simulation that detects immediate
  opponent wins after your candidate move (it does not perform deep search).

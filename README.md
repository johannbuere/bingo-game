# bingo game

a graphical bingo game implementation with customizable cards and call limits.

## features

- **multiple cards**: play with multiple bingo cards simultaneously
- **custom settings**: configure number of calls and cards
- **visual interface**: color-coded cards with marked numbers
- **win detection**: automatic detection of rows, columns, and diagonals
- **game controls**: reset, continue, or exit at any time

## getting started

### compile

```bash
javac src/*.java
```

### run

```bash
java -cp src BingoGame
```

## how to play

1. launch the game and click "start game"
2. set your preferred number of calls and cards
3. click "next call" to draw numbers
4. numbers are automatically marked on matching cards
5. first card to complete a line (row, column, or diagonal) wins

## game rules

- standard 5x5 bingo cards with free center space
- numbers range from B1-B15, I16-I30, N31-N45, G46-G60, O61-O75
- win conditions: any complete row, column, or diagonal
- game resets automatically after a win

## project structure

```
src/
├── BingoGame.java       # entry point
├── BingoMainMenu.java   # menu and settings
├── BingoUI.java         # game interface
├── BingoCard.java       # card generation and logic
└── BingoCaller.java     # number calling system
```

## requirements

- java 8 or higher
- swing support (included in standard jdk)

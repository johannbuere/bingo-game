# bingo game

a modern graphical bingo game with enhanced UI, real-time tracking, and customizable gameplay.

## features

- **modern dark theme**: sleek interface with color-coded cards
- **real-time call tracking**: side panel displays all called numbers
- **multiple winner detection**: identifies and announces all winning cards
- **visual feedback**: marked numbers highlighted with distinct colors
- **call counter**: tracks progress with current/maximum call display
- **spinner controls**: easy number selection with validation
- **game state management**: proper handling of win conditions and game over
- **responsive design**: adapts to different numbers of cards

## getting started

### compile

```bash
javac src/*.java
```

### run

```bash
java -cp src BingoGame
```

## gameplay

1. launch the game from the main menu
2. configure settings using spinners:
   - **calls**: 10-75 (default: 30)
   - **cards**: 1-6 (default: 3)
3. click "start game" to begin
4. press "next call" to draw numbers
5. watch as numbers are automatically marked
6. winning cards display with green borders
7. reset anytime or return to menu

## game rules

- standard 5x5 cards with free center space
- number ranges: B(1-15), I(16-30), N(31-45), G(46-60), O(61-75)
- win conditions: complete row, column, or diagonal
- multiple cards can win simultaneously
- game continues until winner or calls exhausted

## ui enhancements

- **color palette**: dark theme with amber accents
- **card identification**: numbered cards with unique colors
- **marked cells**: golden yellow highlighting
- **winner indication**: bright green borders
- **hover effects**: button interactions with color transitions
- **call history**: scrollable list with formatted display

## project structure

```
src/
├── BingoGame.java       # entry point and main menu
├── BingoUI.java         # enhanced game interface
├── BingoCard.java       # card generation and win logic
└── BingoCaller.java     # number calling system
```

## requirements

- java 8 or higher
- swing support (included in standard jdk)


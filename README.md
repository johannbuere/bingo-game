# bingo game

a java-based graphical bingo game with multiple cards and customizable settings.

## requirements

- java development kit (jdk) 8 or higher
- no external libraries needed (uses built-in swing)

## setup

1. **install java** (if not already installed)
   - download from [oracle](https://www.oracle.com/java/technologies/downloads/) or [openjdk](https://openjdk.org/)
   - verify installation: `java -version`

2. **clone or download** this repository

3. **navigate to project directory**
   ```bash
   cd bingo-game
   ```

## how to run

### compile the game

```bash
javac src/*.java
```

this compiles all four java files:
- `BingoGame.java` - main entry point and menu
- `BingoUI.java` - game interface and controls
- `BingoCard.java` - card generation and win detection
- `BingoCaller.java` - random number calling system

### launch the game

```bash
java -cp src BingoGame
```

## how to play

1. **main menu** opens automatically
   - adjust number of calls (10-75)
   - adjust number of cards (1-6)
   - click "start game"

2. **game screen** shows your cards
   - click "next call" to draw a number
   - matching numbers are marked automatically
   - called numbers appear in the side panel

3. **winning**
   - complete any row, column, or diagonal to win
   - game announces winners automatically
   - click "reset" to play again

## game components

### BingoGame.java
main entry point that displays the menu where you configure game settings before starting.

### BingoUI.java
handles the game interface including card display, number calling, win detection, and all visual elements.

### BingoCard.java
generates random 5x5 bingo cards with proper number ranges (B: 1-15, I: 16-30, N: 31-45, G: 46-60, O: 61-75). includes logic for marking numbers and checking win conditions.

### BingoCaller.java
manages the random number calling system, ensuring no duplicates and tracking call history.

## troubleshooting

**compilation errors?**
- ensure you're in the project root directory
- check that jdk is properly installed
- verify all `.java` files are in the `src/` folder

**game won't launch?**
- make sure you compiled first with `javac src/*.java`
- check classpath: use `-cp src` when running
- verify java version is 8 or higher

## license

this project is open source and available under the [MIT License](LICENSE).



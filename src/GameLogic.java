import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLogic {
    private List<String> calls;
    private int currentCallIndex;
    int totalCalls;
    String gameType;

    public GameLogic(String gameType, int totalCalls) {
        this.gameType = gameType;
        this.totalCalls = totalCalls;
        resetGame();
    }

    public void startGame() {
        currentCallIndex = 0;  // Reset the current call index when the game starts
        Collections.shuffle(calls);  // Shuffle the list of calls to ensure randomness
    }

    public void resetGame() {
        calls = new ArrayList<>();
        // Create the list of bingo calls
        for (int i = 1; i <= 75; i++) {
            String prefix = i <= 15 ? "B" : i <= 30 ? "I" : i <= 45 ? "N" : i <= 60 ? "G" : "O";
            calls.add(prefix + i);
        }
        Collections.shuffle(calls);  // Shuffle at reset so calls are random
        currentCallIndex = 0;  // Reset the current call index
    }

    // Return a random Bingo number for a given column (row used to determine the column range)
    public String getRandomBingoNumber(int row) {
        return calls.get((int) (Math.random() * 15 + row * 15));
    }

    // Get the next Bingo call in the shuffled list
    public String getNextCall() {
        if (currentCallIndex < totalCalls) {
            return calls.get(currentCallIndex++);  // Get the next call and increment index
        }
        return null;  // No more calls if we've reached the total number of calls
    }

    // Get the current call (the one that was last used)
    public String getCurrentCall() {
        if (currentCallIndex > 0 && currentCallIndex <= totalCalls) {
            return calls.get(currentCallIndex - 1);  // Return the current call
        }
        return null;  // No current call yet
    }

    // Check if the game is won (you can define your own win logic)
    public boolean hasWon() {
        return currentCallIndex >= totalCalls;  // If we've called enough numbers, it's a win (or game over)
    }
}

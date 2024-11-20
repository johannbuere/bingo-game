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
        currentCallIndex = 0;
        Collections.shuffle(calls);
    }

    public void resetGame() {
        calls = new ArrayList<>();
        for (int i = 1; i <= 75; i++) {
            String prefix = i <= 15 ? "B" : i <= 30 ? "I" : i <= 45 ? "N" : i <= 60 ? "G" : "O";
            calls.add(prefix + i);
        }
        currentCallIndex = 0;
    }

    public String getRandomBingoNumber(int row) {
        return calls.get((int) (Math.random() * 15 + row * 15));
    }

    public String getNextCall() {
        if (currentCallIndex < totalCalls) {
            return calls.get(currentCallIndex++);
        }
        return null;
    }

    public String getCurrentCall() {
        if (currentCallIndex > 0 && currentCallIndex <= totalCalls) {
            return calls.get(currentCallIndex - 1);
        }
        return null;
    }

    public boolean hasWon() {
        return currentCallIndex <= totalCalls;
    }
}

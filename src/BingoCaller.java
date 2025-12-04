import java.util.ArrayList;
import java.util.Collections;

public class BingoCaller {
    private ArrayList<String> calls;
    private int currentCallIndex;
    private int maxCalls;

    public BingoCaller(int maxCalls) {
        this.maxCalls = maxCalls;
        calls = new ArrayList<>();
        char[] letters = {'B', 'I', 'N', 'G', 'O'};

        for (int i = 0; i < letters.length; i++) {
            int start = i * 15 + 1;
            int end = start + 15;
            for (int num = start; num < end; num++) {
                calls.add(letters[i] + String.valueOf(num));
            }
        }
        Collections.shuffle(calls);
        currentCallIndex = -1;
    }

    public String getNextCall() {
        if (currentCallIndex < maxCalls - 1 && currentCallIndex < calls.size() - 1) {
            return calls.get(++currentCallIndex);
        }
        return null;
    }

    public boolean hasCallsLeft() {
        return currentCallIndex < maxCalls - 1;
    }

    public ArrayList<String> getCalledNumbers() {
        return new ArrayList<>(calls.subList(0, currentCallIndex + 1));
    }
}

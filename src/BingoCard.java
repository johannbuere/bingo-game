import java.util.ArrayList;
import java.util.Collections;

public class BingoCard {
    private String[][] card; // Bingo card numbers
    private boolean[][] marked; // Tracks marked cells

    public BingoCard() {
        card = new String[5][5];
        marked = new boolean[5][5];

        // Fill each column with appropriate numbers
        char[] letters = {'B', 'I', 'N', 'G', 'O'};
        for (int col = 0; col < 5; col++) {
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int num = col * 15 + 1; num <= col * 15 + 15; num++) {
                numbers.add(num);
            }
            Collections.shuffle(numbers);

            for (int row = 0; row < 5; row++) {
                if (col == 2 && row == 2) {
                    card[row][col] = "FREE"; // Center space
                    marked[row][col] = true;
                } else {
                    card[row][col] = String.valueOf(numbers.get(row));
                    marked[row][col] = false;
                }
            }
        }
    }

    public String[][] getCard() {
        return card;
    }

    public boolean[][] getMarked() {
        return marked;
    }

    public void markNumber(String call) {
        if (call == null || call.length() < 2) return;

        char letter = call.charAt(0);
        int number;

        try {
            number = Integer.parseInt(call.substring(1));
        } catch (NumberFormatException e) {
            return; // Ignore invalid calls
        }

        // Map letter to column index
        int colIndex = switch (letter) {
            case 'B' -> 0;
            case 'I' -> 1;
            case 'N' -> 2;
            case 'G' -> 3;
            case 'O' -> 4;
            default -> -1; // Invalid column
        };

        if (colIndex < 0 || colIndex >= 5) return; // Ignore invalid indices

        for (int row = 0; row < 5; row++) {
            if (card[row][colIndex].equals(String.valueOf(number))) {
                marked[row][colIndex] = true; // Mark the number
                break; // No need to continue searching this column
            }
        }
    }

    public boolean hasWon() {
        // Check rows
        for (int row = 0; row < 5; row++) {
            boolean rowWin = true;
            for (int col = 0; col < 5; col++) {
                if (!marked[row][col]) {
                    rowWin = false;
                    break;
                }
            }
            if (rowWin) return true;
        }

        // Check columns
        for (int col = 0; col < 5; col++) {
            boolean colWin = true;
            for (int row = 0; row < 5; row++) {
                if (!marked[row][col]) {
                    colWin = false;
                    break;
                }
            }
            if (colWin) return true;
        }

        // Check diagonals
        boolean diagonalWin1 = true;
        boolean diagonalWin2 = true;

        for (int i = 0; i < 5; i++) {
            if (!marked[i][i]) diagonalWin1 = false; // Top-left to bottom-right
            if (!marked[i][4 - i]) diagonalWin2 = false; // Top-right to bottom-left
        }

        return diagonalWin1 || diagonalWin2;
    }

}

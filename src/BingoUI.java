import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BingoUI {

    public BingoUI() {
        new MainMenu();
    }

    // MainMenu Class
    class MainMenu {
        JFrame frame;
        JButton singleLineButton, twoLineButton, blackoutButton, quitButton;

        public MainMenu() {
            frame = new JFrame("Bingo Main Menu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(4, 1, 10, 10));

            JLabel titleLabel = new JLabel("Welcome to Bingo!", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            frame.add(titleLabel);

            singleLineButton = new JButton("Single Line");
            twoLineButton = new JButton("Two Lines");
            blackoutButton = new JButton("Blackout");
            quitButton = new JButton("Quit");

            singleLineButton.addActionListener(e -> handleGameType("Single Line", 20, 25));
            twoLineButton.addActionListener(e -> handleGameType("Two Lines", 30, 40));
            blackoutButton.addActionListener(e -> handleGameType("Blackout", 60, 70));
            quitButton.addActionListener(e -> System.exit(0));

            frame.add(singleLineButton);
            frame.add(twoLineButton);
            frame.add(blackoutButton);
            frame.add(quitButton);

            frame.setSize(400, 300);
            frame.setVisible(true);
        }

        private void handleGameType(String gameType, int minCalls, int maxCalls) {
            String input = JOptionPane.showInputDialog(frame,
                    "Enter the number of calls for " + gameType + " (between " + minCalls + " and " + maxCalls + "):");
            try {
                int calls = Integer.parseInt(input);
                if (calls >= minCalls && calls <= maxCalls) {
                    frame.dispose();
                    new BingoCardUI(gameType, calls);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid number. Please try again.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
            }
        }
    }

    // BingoCardUI Class
    class BingoCardUI {
        JFrame frame;
        JPanel cardPanel;
        JLabel statusLabel, callLabel;
        JButton[][] bingoButtons;
        JButton startButton, resetButton, backButton;
        final int GRID_SIZE = 5;

        String[] headers = {"B", "I", "N", "G", "O"};
        GameLogic gameLogic;

        public BingoCardUI(String gameType, int totalCalls) {
            gameLogic = new GameLogic(gameType, totalCalls);

            frame = new JFrame("Bingo Card");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Create Bingo card panel
            cardPanel = new JPanel(new GridLayout(GRID_SIZE + 1, GRID_SIZE));
            bingoButtons = new JButton[GRID_SIZE][GRID_SIZE];

            // Add headers for "BINGO"
            for (String header : headers) {
                JLabel headerLabel = new JLabel(header, SwingConstants.CENTER);
                headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
                cardPanel.add(headerLabel);
            }

            // Add number buttons
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    bingoButtons[i][j] = new JButton(gameLogic.getRandomBingoNumber(j).substring(1));
                    bingoButtons[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                    bingoButtons[i][j].setEnabled(false); // Disabled until the game starts
                    bingoButtons[i][j].addActionListener(new BingoButtonListener(i, j));
                    cardPanel.add(bingoButtons[i][j]);
                }
            }

            // Create status and call labels
            statusLabel = new JLabel("Press Start to Begin!", SwingConstants.CENTER);
            statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

            callLabel = new JLabel("Call: ", SwingConstants.CENTER);
            callLabel.setFont(new Font("Arial", Font.BOLD, 20));

            // Create buttons
            startButton = new JButton("Start Game");
            resetButton = new JButton("Reset Card");
            backButton = new JButton("Back to Main Menu");

            startButton.addActionListener(e -> startGame());
            resetButton.addActionListener(e -> resetCard());
            backButton.addActionListener(e -> {
                frame.dispose();
                new MainMenu();
            });

            JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
            buttonPanel.add(startButton);
            buttonPanel.add(resetButton);
            buttonPanel.add(backButton);

            frame.add(statusLabel, BorderLayout.NORTH);
            frame.add(cardPanel, BorderLayout.CENTER);
            frame.add(callLabel, BorderLayout.SOUTH);
            frame.add(buttonPanel, BorderLayout.SOUTH);

            frame.setSize(500, 600);
            frame.setVisible(true);
        }

        private void startGame() {
            gameLogic.startGame();
            for (JButton[] row : bingoButtons) {
                for (JButton button : row) {
                    button.setEnabled(true);
                }
            }
            statusLabel.setText("Game Started! Click the called number.");
            callNextNumber();
        }

        private void callNextNumber() {
            String call = gameLogic.getNextCall();
            if (call != null) {
                callLabel.setText("Call: " + call);
                statusLabel.setText("Match the number: " + call);
            } else {
                statusLabel.setText("Game Over! " + (gameLogic.hasWon() ? "You won!" : "You lost!"));
                for (JButton[] row : bingoButtons) {
                    for (JButton button : row) {
                        button.setEnabled(false);
                    }
                }
            }
        }

        private void resetCard() {
            gameLogic.resetGame();
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    bingoButtons[i][j].setText(gameLogic.getRandomBingoNumber(j).substring(1)); // Reset to numbers only
                    bingoButtons[i][j].setEnabled(false);
                }
            }
            statusLabel.setText("Card Reset! Press Start to Begin.");
            callLabel.setText("Call: ");
        }

        private class BingoButtonListener implements ActionListener {
            private final int row;
            private final int col;

            public BingoButtonListener(int row, int col) {
                this.row = row;
                this.col = col;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                JButton clicked = (JButton) e.getSource();
                String call = gameLogic.getCurrentCall();
                String expectedCall = headers[col] + clicked.getText();

                if (expectedCall.equals(call)) {
                    clicked.setEnabled(false);
                    statusLabel.setText("Correct! Waiting for the next call.");
                    callNextNumber();
                } else {
                    statusLabel.setText("Incorrect! The call was: " + call);
                }
            }
        }
    }
}

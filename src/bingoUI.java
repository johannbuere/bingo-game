import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class bingoUI {

    JFrame frame;
    JPanel cardPanel;
    JLabel statusLabel;
    JButton[][] bingoButtons;
    final int GRID_SIZE = 5;

    public bingoUI() {
        // Create frame
        frame = new JFrame("Bingo UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create Bingo card panel
        cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        bingoButtons = new JButton[GRID_SIZE][GRID_SIZE];

        // Initialize buttons on the Bingo card
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                bingoButtons[i][j] = new JButton();
                bingoButtons[i][j].setText(randomBingoNumber(i)); // Set a random number
                bingoButtons[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                bingoButtons[i][j].setFocusPainted(false);
                bingoButtons[i][j].addActionListener(new BingoButtonListener());
                cardPanel.add(bingoButtons[i][j]);
            }
        }

        // Create status label
        statusLabel = new JLabel("Welcome to Bingo!", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Create New Game button
        JButton newGameButton = new JButton("New Game");
        newGameButton.setFont(new Font("Arial", Font.BOLD, 16));
        newGameButton.addActionListener(e -> resetBingoCard());

        // Add components to frame
        frame.add(statusLabel, BorderLayout.NORTH);
        frame.add(cardPanel, BorderLayout.CENTER);
        frame.add(newGameButton, BorderLayout.SOUTH);

        // Finalize frame
        frame.setSize(400, 500);
        frame.setVisible(true);
    }
    // Generate a random Bingo number as a placeholder
    private String randomBingoNumber(int row) {
        return String.valueOf((int) (Math.random() * 15 + 1 + row * 15));
    }

    // Reset the Bingo card to start a new game
    private void resetBingoCard() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                bingoButtons[i][j].setText(randomBingoNumber(i));
                bingoButtons[i][j].setEnabled(true);
            }
        }
        statusLabel.setText("New Game Started!");
    }

    // Action listener for Bingo buttons
    private class BingoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton buttonClicked = (JButton) e.getSource();
            buttonClicked.setEnabled(false);
            statusLabel.setText("You clicked: " + buttonClicked.getText());
        }
    }
}

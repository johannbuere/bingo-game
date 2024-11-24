import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class BingoUI {
    private int maxCalls;
    private int numCards;
    private BingoCaller caller;
    private ArrayList<BingoCard> cards;
    private JFrame frame;
    private JLabel callLabel;
    private JPanel cardPanel;

    public BingoUI(int maxCalls, int numCards) {
        this.maxCalls = maxCalls;
        this.numCards = numCards;
        this.caller = new BingoCaller(maxCalls);
        this.cards = new ArrayList<>();

        for (int i = 0; i < numCards; i++) {
            cards.add(new BingoCard());
        }
    }

    public void launch() {
        frame = new JFrame("Bingo Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10)); // Add spacing around components

        callLabel = new JLabel("Next Call: ", SwingConstants.CENTER);
        callLabel.setFont(new Font("Arial", Font.BOLD, 24));
        callLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(callLabel, BorderLayout.NORTH);

        cardPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // Allow for multiple rows/columns
        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        updateCardPanel();
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10)); // Center buttons with spacing
        JButton nextCall = new JButton("Next Call");
        JButton resetGame = new JButton("Reset Game");
        JButton exitGame = new JButton("Exit Game");

        nextCall.addActionListener(e -> handleNextCall());
        resetGame.addActionListener(e -> resetGame());
        exitGame.addActionListener(e -> System.exit(0));

        controls.add(nextCall);
        controls.add(resetGame);
        controls.add(exitGame);
        frame.add(controls, BorderLayout.SOUTH);

        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setVisible(true);
    }

    private void handleNextCall() {
        if (caller.hasCallsLeft()) {
            String nextCall = caller.getNextCall();
            callLabel.setText("Next Call: " + nextCall);

            boolean someoneWon = false;

            for (BingoCard card : cards) {
                card.markNumber(nextCall);
                if (card.hasWon()) {
                    someoneWon = true;
                }
            }
            updateCardPanel();

            if (someoneWon) {
                JOptionPane.showMessageDialog(frame, "We have a winner!");
                resetGame();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No more calls left. Game over!");
        }
    }

    private void updateCardPanel() {
        cardPanel.removeAll();

        for (int i = 0; i < cards.size(); i++) {
            JPanel singleCardPanel = new JPanel(new GridLayout(6, 5));
            singleCardPanel.setBorder(BorderFactory.createLineBorder(getColorForCard(i), 3));

            // Add the BINGO header
            String[] headers = {"B", "I", "N", "G", "O"};
            for (String header : headers) {
                JLabel headerLabel = new JLabel(header, SwingConstants.CENTER);
                headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
                headerLabel.setOpaque(true);
                headerLabel.setBackground(Color.LIGHT_GRAY);
                headerLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                singleCardPanel.add(headerLabel);
            }

            // Add card's numbers and marked status
            String[][] numbers = cards.get(i).getCard();
            boolean[][] marked = cards.get(i).getMarked();

            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
                    JLabel cell = new JLabel(numbers[row][col], SwingConstants.CENTER);
                    cell.setOpaque(true);
                    cell.setFont(new Font("Arial", Font.BOLD, 18));
                    cell.setBackground(marked[row][col] ? Color.GREEN : Color.WHITE);
                    cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    singleCardPanel.add(cell);
                }
            }

            cardPanel.add(singleCardPanel);
        }

        cardPanel.revalidate();
        cardPanel.repaint();
    }

    private void resetGame() {
        caller = new BingoCaller(maxCalls);
        cards.clear();
        for (int i = 0; i < numCards; i++) {
            cards.add(new BingoCard());
        }
        updateCardPanel();
        callLabel.setText("Next Call: ");
    }

    private Color getColorForCard(int index) {
        Color[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.PINK};
        return colors[index % colors.length];
    }
}

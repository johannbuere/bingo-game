import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BingoUI {
    private final int maxCalls;
    private final int numCards;
    private BingoCaller caller;
    private ArrayList<BingoCard> cards;
    private JFrame frame;
    private JLabel callLabel;
    private JLabel callCountLabel;
    private JPanel cardPanel;
    private JTextArea calledNumbersArea;
    private JButton nextCallButton;
    private boolean gameOver = false;

    private static final Color DARK_BG = new Color(45, 52, 54);
    private static final Color CARD_BG = new Color(240, 240, 245);
    private static final Color MARKED_COLOR = new Color(255, 193, 7);
    private static final Color FREE_SPACE = new Color(108, 117, 125);
    private static final Color WINNER_BORDER = new Color(46, 213, 115);
    private static final Color[] CARD_COLORS = {
        new Color(231, 76, 60), new Color(52, 152, 219), new Color(155, 89, 182),
        new Color(26, 188, 156), new Color(241, 196, 15), new Color(230, 126, 34)
    };

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
        frame = new JFrame("Bingo Game - Play");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(15, 15));
        frame.getContentPane().setBackground(DARK_BG);

        JPanel topPanel = createTopPanel();
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(DARK_BG);

        cardPanel = new JPanel(new GridLayout(0, Math.min(3, numCards), 15, 15));
        cardPanel.setBackground(DARK_BG);
        updateCardPanel();

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setBackground(DARK_BG);
        scrollPane.getViewport().setBackground(DARK_BG);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel sidePanel = createSidePanel();
        mainPanel.add(sidePanel, BorderLayout.EAST);

        frame.add(mainPanel, BorderLayout.CENTER);

        JPanel controlPanel = createControlPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        callLabel = new JLabel("Press 'Next Call' to start", SwingConstants.CENTER);
        callLabel.setFont(new Font("Arial", Font.BOLD, 36));
        callLabel.setForeground(new Color(255, 234, 167));
        panel.add(callLabel, BorderLayout.CENTER);

        callCountLabel = new JLabel("Calls: 0 / " + maxCalls, SwingConstants.RIGHT);
        callCountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        callCountLabel.setForeground(new Color(178, 190, 195));
        panel.add(callCountLabel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createSidePanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(DARK_BG);
        panel.setPreferredSize(new Dimension(250, 0));

        JLabel historyLabel = new JLabel("Called Numbers", SwingConstants.CENTER);
        historyLabel.setFont(new Font("Arial", Font.BOLD, 18));
        historyLabel.setForeground(Color.WHITE);
        historyLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        panel.add(historyLabel, BorderLayout.NORTH);

        calledNumbersArea = new JTextArea();
        calledNumbersArea.setEditable(false);
        calledNumbersArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        calledNumbersArea.setBackground(new Color(33, 37, 41));
        calledNumbersArea.setForeground(new Color(248, 249, 250));
        calledNumbersArea.setLineWrap(true);
        calledNumbersArea.setWrapStyleWord(true);
        calledNumbersArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(calledNumbersArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(108, 117, 125), 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panel.setBackground(DARK_BG);

        nextCallButton = createStyledButton("NEXT CALL", new Color(52, 152, 219), new Color(41, 128, 185));
        JButton resetButton = createStyledButton("RESET", new Color(230, 126, 34), new Color(211, 84, 0));
        JButton exitButton = createStyledButton("EXIT", new Color(231, 76, 60), new Color(192, 57, 43));

        nextCallButton.addActionListener(e -> handleNextCall());
        resetButton.addActionListener(e -> resetGame());
        exitButton.addActionListener(e -> {
            frame.dispose();
            new BingoGame().showMainMenu();
        });

        panel.add(nextCallButton);
        panel.add(resetButton);
        panel.add(exitButton);

        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void handleNextCall() {
        if (gameOver) {
            JOptionPane.showMessageDialog(frame, "Game is over! Please reset to play again.");
            return;
        }

        if (caller.hasCallsLeft()) {
            String nextCall = caller.getNextCall();
            callLabel.setText(nextCall);
            
            int callNumber = caller.getCalledNumbers().size();
            callCountLabel.setText("Calls: " + callNumber + " / " + maxCalls);

            updateCalledNumbersDisplay();

            ArrayList<Integer> winners = new ArrayList<>();
            for (int i = 0; i < cards.size(); i++) {
                cards.get(i).markNumber(nextCall);
                if (cards.get(i).hasWon()) {
                    winners.add(i + 1);
                }
            }

            updateCardPanel();

            if (!winners.isEmpty()) {
                gameOver = true;
                nextCallButton.setEnabled(false);
                String winnerText = winners.size() == 1 
                    ? "Card " + winners.get(0) + " wins!" 
                    : "Cards " + winners + " win!";
                
                JOptionPane.showMessageDialog(frame, 
                    winnerText + "\n\nCongratulations! 🎉", 
                    "BINGO!", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            gameOver = true;
            nextCallButton.setEnabled(false);
            JOptionPane.showMessageDialog(frame, 
                "No more calls available!\n\nGame Over", 
                "End of Game", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateCalledNumbersDisplay() {
        ArrayList<String> called = caller.getCalledNumbers();
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < called.size(); i++) {
            sb.append(called.get(i));
            if ((i + 1) % 5 == 0) {
                sb.append("\n");
            } else if (i < called.size() - 1) {
                sb.append("  ");
            }
        }
        
        calledNumbersArea.setText(sb.toString());
        calledNumbersArea.setCaretPosition(calledNumbersArea.getDocument().getLength());
    }

    private void updateCardPanel() {
        cardPanel.removeAll();

        for (int i = 0; i < cards.size(); i++) {
            JPanel cardContainer = new JPanel(new BorderLayout(5, 5));
            cardContainer.setBackground(DARK_BG);

            JLabel cardNumberLabel = new JLabel("Card " + (i + 1), SwingConstants.CENTER);
            cardNumberLabel.setFont(new Font("Arial", Font.BOLD, 16));
            cardNumberLabel.setForeground(CARD_COLORS[i % CARD_COLORS.length]);
            cardContainer.add(cardNumberLabel, BorderLayout.NORTH);

            JPanel singleCardPanel = new JPanel(new GridLayout(6, 5, 2, 2));
            singleCardPanel.setBackground(CARD_BG);
            
            boolean isWinner = cards.get(i).hasWon();
            int borderWidth = isWinner ? 5 : 3;
            Color borderColor = isWinner ? WINNER_BORDER : CARD_COLORS[i % CARD_COLORS.length];
            singleCardPanel.setBorder(BorderFactory.createLineBorder(borderColor, borderWidth));

            String[] headers = {"B", "I", "N", "G", "O"};
            for (String header : headers) {
                JLabel headerLabel = new JLabel(header, SwingConstants.CENTER);
                headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
                headerLabel.setOpaque(true);
                headerLabel.setBackground(CARD_COLORS[i % CARD_COLORS.length]);
                headerLabel.setForeground(Color.WHITE);
                headerLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                singleCardPanel.add(headerLabel);
            }

            String[][] numbers = cards.get(i).getCard();
            boolean[][] marked = cards.get(i).getMarked();

            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
                    JLabel cell = new JLabel(numbers[row][col], SwingConstants.CENTER);
                    cell.setOpaque(true);
                    cell.setFont(new Font("Arial", Font.BOLD, 18));
                    
                    if (numbers[row][col].equals("FREE")) {
                        cell.setBackground(FREE_SPACE);
                        cell.setForeground(Color.WHITE);
                    } else if (marked[row][col]) {
                        cell.setBackground(MARKED_COLOR);
                        cell.setForeground(Color.WHITE);
                    } else {
                        cell.setBackground(Color.WHITE);
                        cell.setForeground(Color.BLACK);
                    }
                    
                    cell.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
                    singleCardPanel.add(cell);
                }
            }

            cardContainer.add(singleCardPanel, BorderLayout.CENTER);
            cardPanel.add(cardContainer);
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
        gameOver = false;
        nextCallButton.setEnabled(true);
        updateCardPanel();
        callLabel.setText("Press 'Next Call' to start");
        callCountLabel.setText("Calls: 0 / " + maxCalls);
        calledNumbersArea.setText("");
    }
}

import javax.swing.*;
import java.awt.*;

public class BingoGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BingoGame().showMainMenu());
    }

    public void showMainMenu() {
        JFrame frame = new JFrame("Bingo Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(45, 52, 54));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("BINGO");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 72));
        titleLabel.setForeground(new Color(255, 234, 167));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Classic Game");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        subtitleLabel.setForeground(new Color(178, 190, 195));
        gbc.gridy = 1;
        mainPanel.add(subtitleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel callsLabel = new JLabel("Number of Calls:");
        callsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        callsLabel.setForeground(Color.WHITE);
        mainPanel.add(callsLabel, gbc);

        SpinnerNumberModel callsModel = new SpinnerNumberModel(30, 10, 75, 5);
        JSpinner callsSpinner = new JSpinner(callsModel);
        callsSpinner.setFont(new Font("Arial", Font.PLAIN, 16));
        ((JSpinner.DefaultEditor) callsSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        mainPanel.add(callsSpinner, gbc);

        JLabel cardsLabel = new JLabel("Number of Cards:");
        cardsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        cardsLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(cardsLabel, gbc);

        SpinnerNumberModel cardsModel = new SpinnerNumberModel(3, 1, 6, 1);
        JSpinner cardsSpinner = new JSpinner(cardsModel);
        cardsSpinner.setFont(new Font("Arial", Font.PLAIN, 16));
        ((JSpinner.DefaultEditor) cardsSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        mainPanel.add(cardsSpinner, gbc);

        JButton startButton = createStyledButton("START GAME", new Color(46, 213, 115), new Color(39, 174, 96));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 10, 10, 10);
        mainPanel.add(startButton, gbc);

        JButton exitButton = createStyledButton("EXIT", new Color(235, 77, 75), new Color(214, 48, 49));
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(exitButton, gbc);

        startButton.addActionListener(e -> {
            int numCalls = (Integer) callsSpinner.getValue();
            int numCards = (Integer) cardsSpinner.getValue();
            frame.dispose();
            new BingoUI(numCalls, numCards).launch();
        });

        exitButton.addActionListener(e -> System.exit(0));

        frame.add(mainPanel);
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
}

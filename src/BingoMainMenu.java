import javax.swing.*;

public class BingoMainMenu {
    public void launch() {
        JFrame frame = new JFrame("Bingo Game - Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton startGame = new JButton("Start Game");
        JButton exitGame = new JButton("Exit");

        startGame.addActionListener(e -> {
            frame.dispose();
            showGameSettings();
        });

        exitGame.addActionListener(e -> System.exit(0));

        panel.add(startGame);
        panel.add(exitGame);
        frame.add(panel);

        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showGameSettings() {
        JFrame frame = new JFrame("Bingo Game - Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel callsLabel = new JLabel("Number of Calls: ");
        JLabel cardsLabel = new JLabel("Number of Cards: ");

        JTextField callsField = new JTextField(5);
        JTextField cardsField = new JTextField(5);

        JButton startGame = new JButton("Start Game");

        startGame.addActionListener(e -> {
            try {
                int numCalls = Integer.parseInt(callsField.getText());
                int numCards = Integer.parseInt(cardsField.getText());
                frame.dispose();
                new BingoUI(numCalls, numCards).launch();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers!");
            }
        });

        panel.add(callsLabel);
        panel.add(callsField);
        panel.add(cardsLabel);
        panel.add(cardsField);
        panel.add(startGame);

        frame.add(panel);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

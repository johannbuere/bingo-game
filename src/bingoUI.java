import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class bingoUI implements ActionListener {

    int count = 0;
    JLabel label;
    JPanel panel;

    public bingoUI() {
        JFrame frame = new JFrame();

        JButton button = new JButton("Test Click Me");
        button.addActionListener(this);

        label = new JLabel("Number of Test Clicks: 0");



        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Bingo Test Ui");
        frame.pack();
        frame.setVisible(true);

    }
    public void actionPerformed(ActionEvent e){
        count++;
        label.setText("Number of Test CLicks: " + count);
    }

}

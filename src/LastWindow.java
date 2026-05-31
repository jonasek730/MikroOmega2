import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static java.awt.Color.*;

/**
 * class for the last window
 */
public class LastWindow extends JFrame {
    /**
     * Paint the last window
     * @param playedTime Players score
     */
        public LastWindow(String playedTime) {
            setTitle("Konec hry");
            setSize(700, 450);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            getContentPane().setBackground(BLACK);
            setLayout(new GridBagLayout());

            JPanel contentPanel = new JPanel();
            contentPanel.setOpaque(false);
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

            JLabel titleLabel = new JLabel("Vyhral jsi!");
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titleLabel.setForeground(WHITE);
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 54));

            JLabel timeLabel = new JLabel("Hral jsi: " + playedTime);
            timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            timeLabel.setForeground(WHITE);
            timeLabel.setFont(new Font("SansSerif", Font.BOLD, 30));

            JButton resetButton = new JButton("Reset");
            resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            resetButton.setFont(new Font("SansSerif", Font.BOLD, 30));
            resetButton.setPreferredSize(new Dimension(240, 80));
            resetButton.setMaximumSize(new Dimension(240, 80));
            resetButton.setFocusPainted(false);
            resetButton.addActionListener(e -> {
                dispose();
                new UserInterface(new Random());
            });

            contentPanel.add(titleLabel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            contentPanel.add(timeLabel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            contentPanel.add(resetButton);

            add(contentPanel);
            setVisible(true);

    JButton endButton = new JButton("Konec");
        endButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        endButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        endButton.setPreferredSize(new Dimension(240, 80));
        endButton.setMaximumSize(new Dimension(240, 80));
        resetButton.setFocusPainted(false);
        endButton.addActionListener(e -> System.exit(0));

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(timeLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(resetButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(endButton);

    add(contentPanel);
    setVisible(true);
}


}

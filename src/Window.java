import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Window extends JFrame {
    /**
     * create the welcome window
     */
    public Window() {
        setTitle("Maze Runner");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Maze Runner");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 54));
        String welcomeText = "Vítej v mojí hře.\n Cíl hry je najít klíč zobrazený žlutě a dojít do cíle\n Pohyb je umožněn šipkamy.";

        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>" + welcomeText + "</div></html>");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));

        JButton startButton = new JButton("Start");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        startButton.setPreferredSize(new Dimension(240, 80));
        startButton.setMaximumSize(new Dimension(240, 80));
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> {
            dispose();
            new UserInterface(new Random());
        });

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(welcomeLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(startButton);

        add(contentPanel);
        setVisible(true);
    }
}

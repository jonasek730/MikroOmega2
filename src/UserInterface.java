import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class UserInterface extends JFrame {
    private final JLabel timerLabel;
    private long startTimeMillis;

    public UserInterface(Random rnd) {
        setTitle("MikroOmega");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
        setLayout(new BorderLayout());
        timerLabel = new JLabel("Time: 00:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        timerLabel.setForeground(Color.WHITE);
        add(timerLabel, BorderLayout.NORTH);
        Pole pole = new Pole();
        pole.createMaze();
        pole.BuidlMazeDFS(rnd);
        MazeWindow panel = new MazeWindow(pole);
        add(panel, BorderLayout.CENTER);
        SwingUtilities.invokeLater(panel::requestFocusInWindow);
        startTimeMillis = System.currentTimeMillis();
        Timer timer = new Timer(1000, e -> updateTimer());
        timer.setInitialDelay(0);
        timer.start();

    }
    private void updateTimer() {
        long elapsedMillis = System.currentTimeMillis() - startTimeMillis;
        long elapsedSeconds = elapsedMillis / 1000;
        long minutes = elapsedSeconds / 60;
        long seconds = elapsedSeconds % 60;
        timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }



}




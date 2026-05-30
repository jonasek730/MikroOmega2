import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class UserInterface extends JFrame {
    private final JLabel timerLabel;
    private long startTimeMillis;
    private static final int EXTRA_OPEN_WALLS = 300;
    private final Timer timer;


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
        pole.destroyRandomWalls(rnd, EXTRA_OPEN_WALLS);
        MazeWindow panel = new MazeWindow(pole, this::finishGame, rnd);

        add(panel, BorderLayout.CENTER);
        SwingUtilities.invokeLater(panel::requestFocusInWindow);
        startTimeMillis = System.currentTimeMillis();
        timer = new Timer(1000, e -> updateTimer());
        timer.setInitialDelay(0);
        timer.start();

    }
    private void updateTimer() {
        timerLabel.setText("Time: " + formatPlayedTime(getElapsedMillis()));
    }

    private void finishGame() {
        timer.stop();
        String playedTime = formatPlayedTime(getElapsedMillis());
        dispose();
        new LastWindow(playedTime);
    }

    private long getElapsedMillis() {
        return System.currentTimeMillis() - startTimeMillis;
    }

    private String formatPlayedTime(long elapsedMillis) {

        long elapsedSeconds = elapsedMillis / 1000;
        long minutes = elapsedSeconds / 60;
        long seconds = elapsedSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }



}




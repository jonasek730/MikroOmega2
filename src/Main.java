import javax.swing.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Pole pole =new Pole();
        Random rnd = new Random();
        pole.createMaze();
        pole.BuidlMazeDFS(rnd);
        pole.printMaze();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bludiště");

            Game panel = new Game();
            frame.add(panel);
            frame.pack();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
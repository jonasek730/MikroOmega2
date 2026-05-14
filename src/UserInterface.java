import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class UserInterface extends JFrame {
    public UserInterface(Random rnd) {
        setTitle("MikroOmega");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
        Pole pole = new Pole();
        pole.createMaze();
        pole.BuidlMazeDFS(rnd);
        MazeWindow panel = new MazeWindow(pole);
        add(panel);
    }


}




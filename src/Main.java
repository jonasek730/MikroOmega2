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
            UserInterface ui = new UserInterface(pole);
            ui.setVisible(true);
        });
    }
}
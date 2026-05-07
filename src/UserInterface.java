import javax.swing.*;
import java.awt.*;

public class UserInterface extends JFrame {
    private Pole pole;
    public UserInterface(Pole pole) {
        this.pole = pole;
        setTitle("Bludiště");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(new MazePanel());
        pack();
        setLocationRelativeTo(null);
    }
    private class MazePanel extends JPanel {
        private static int CELL_SIZE = 20;
    }
        public Dimension getPreferredSize() {
            return new Dimension(pole.getCols() * MazePanel.CELL_SIZE, pole.getRows() * MazePanel.CELL_SIZE);
        }
    protected void paintComponent(Graphics g) {
        paintComponent(g);
        for (int i = 0; i < pole.getRows(); i++) {
            for (int j = 0; j < pole.getCols(); j++) {
                Wall cell = pole.getCell(i, j);
                if (cell != null && cell.isPermWall()) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * MazePanel.CELL_SIZE, i * MazePanel.CELL_SIZE, MazePanel.CELL_SIZE, MazePanel.CELL_SIZE);

                g.setColor(Color.GRAY);
                g.drawRect(j * MazePanel.CELL_SIZE, i * MazePanel.CELL_SIZE, MazePanel.CELL_SIZE, MazePanel.CELL_SIZE);
            }
        }
    }
}



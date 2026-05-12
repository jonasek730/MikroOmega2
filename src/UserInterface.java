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
        revalidate();
        repaint();
        setVisible(true);
    }

    private class MazePanel extends JPanel {
        private static int CELL_SIZE = 20;
        private static int MARGIN_PX = 190;
    }

    public Dimension getPreferredSize() {
        return new Dimension(
                pole.getCols() * MazePanel.CELL_SIZE + (MazePanel.MARGIN_PX * 2),
                pole.getRows() * MazePanel.CELL_SIZE + (MazePanel.MARGIN_PX * 2)
        );
    }

    protected void paintComponent(Graphics g) {
        super.paintComponents(g);


        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setStroke(new BasicStroke(2));

        g2.setColor(Color.WHITE);
        for (int i = 0; i < pole.getRows(); i++) {
            for (int j = 0; j < pole.getCols(); j++) {
                int x = MazePanel.MARGIN_PX + (j * MazePanel.CELL_SIZE);
                int y = MazePanel.MARGIN_PX + (i * MazePanel.CELL_SIZE);
                g2.fillRect(x, y, MazePanel.CELL_SIZE, MazePanel.CELL_SIZE);
            }
        }

        for (int i = 0; i < pole.getRows(); i++) {
            for (int j = 0; j < pole.getCols(); j++) {
                Wall cell = pole.getCell(i, j);
                int x = MazePanel.MARGIN_PX + (j * MazePanel.CELL_SIZE);
                int y = MazePanel.MARGIN_PX + (i * MazePanel.CELL_SIZE);


                if (!cell.isUp()) {
                    g2.drawLine(x, y, x + MazePanel.CELL_SIZE, y);
                }
                if (!cell.isRight()) {
                    g2.drawLine(x + MazePanel.CELL_SIZE, y, x + MazePanel.CELL_SIZE, y + MazePanel.CELL_SIZE);
                }
                if (!cell.isDown()) {
                    g2.drawLine(x, y + MazePanel.CELL_SIZE, x + MazePanel.CELL_SIZE, y + MazePanel.CELL_SIZE);
                }
                if (!cell.isLeft()) {
                    g2.drawLine(x, y, x, y + MazePanel.CELL_SIZE);
                }
            }
        }
        g2.dispose();
    }

}


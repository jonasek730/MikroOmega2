import javax.swing.*;
import java.awt.*;

public class MazeWindow extends JPanel {
    private final int cols = 50;
    private final int rows = 30;
    private Pole pole;

    private final int cellSize = 20;


    public MazeWindow(Pole pole) {
        this.pole = pole;

        setBackground(Color.BLACK);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        int gridWidth = cols * cellSize;
        int gridHeight = rows * cellSize;


        int marginX = (getWidth() - gridWidth) / 2;
        int marginY = (getHeight() - gridHeight) / 2;

        g.setColor(Color.WHITE);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                Wall cell = pole.getCell(row, col);

                int x = marginX + col * cellSize;
                int y = marginY + row * cellSize;

                if (!cell.isUp()) {
                    g.drawLine(x, y, x + cellSize, y);
                }

                // pravá zeď
                if (!cell.isRight()) {
                    g.drawLine(x + cellSize, y,
                            x + cellSize, y + cellSize);
                }

                // dolní zeď
                if (!cell.isDown()) {
                    g.drawLine(x, y + cellSize,
                            x + cellSize, y + cellSize);
                }

                // levá zeď
                if (!cell.isLeft()) {
                    g.drawLine(x, y,
                            x, y + cellSize);
                }
            }
        }
    }
}

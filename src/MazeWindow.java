import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;



public class MazeWindow extends JPanel {
    private final int cols = 50;
    private final int rows = 30;
    private Pole pole;
    private final int cellSize = 20;
    private final Player player;
    private final int goalRow;
    private final int goalCol;
    private boolean gameFinished;



    public MazeWindow(Pole pole) {
        this.pole = pole;
        this.player = new Player(1, 1);
        setBackground(Color.BLACK);
        setFocusable(true);
        setupControls();
        this.goalRow = rows / 2;
        this.goalCol = cols / 2;
        this.gameFinished = false;
        pole.getCell(goalRow, goalCol).setEnd(true);



    }
    private void setupControls() {
        bindMovementKey("moveUp", KeyEvent.VK_UP, -1, 0);
        bindMovementKey("moveDown", KeyEvent.VK_DOWN, 1, 0);
        bindMovementKey("moveLeft", KeyEvent.VK_LEFT, 0, -1);
        bindMovementKey("moveRight", KeyEvent.VK_RIGHT, 0, 1);
    }
    private void bindMovementKey(String actionName, int keyCode, int rowChange, int colChange) {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0), actionName);
        getActionMap().put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(rowChange, colChange);
            }
        });
    }
    private boolean canMoveTo(int nextRow, int nextCol, int rowChange, int colChange) {
        if (nextRow < 1 || nextRow >= rows - 1) {
            return false;
        }

        if (nextCol < 1 || nextCol >= cols - 1) {
            return false;
        }

        if (pole.getCell(nextRow, nextCol).isPermWall()) {
            return false;
        }

        Wall currentCell = pole.getCell(player.getRow(), player.getCol());

        if (rowChange == -1) {
            return currentCell.isUp();
        }

        if (rowChange == 1) {
            return currentCell.isDown();
        }

        if (colChange == -1) {
            return currentCell.isLeft();
        }

        if (colChange == 1) {
            return currentCell.isRight();
        }

        return false;
    }

    private void movePlayer(int rowChange, int colChange) {
        int nextRow = player.getRow() + rowChange;
        int nextCol = player.getCol() + colChange;

        if (canMoveTo(nextRow, nextCol, rowChange, colChange)) {
            player.moveTo(nextRow, nextCol);
            repaint();
            checkGoalReached();
        }
    }
    private void checkGoalReached() {
        if (player.getRow() == goalRow && player.getCol() == goalCol) {
            gameFinished = true;
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "Vyhral jsi! Dosel jsi do cile.");
                System.exit(0);
            });
        }
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

                if (cell.isPermWall()) {
                    g.fillRect(x, y, cellSize, cellSize);
                    continue;
                }



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
        drawPlayer(g, marginX, marginY);
        drawGoal(g, marginX, marginY);
    }
private void drawGoal(Graphics g, int marginX, int marginY) {
    int goalX = marginX + goalCol * cellSize;
    int goalY = marginY + goalRow * cellSize;
    int padding = 5;

    g.setColor(Color.GREEN);
    g.fillRect(
            goalX + padding,
            goalY + padding,
            cellSize - padding * 2,
            cellSize - padding * 2
    );
}


private void drawPlayer(Graphics g, int marginX, int marginY) {
        int playerX = marginX + player.getCol() * cellSize;
        int playerY = marginY + player.getRow() * cellSize;
        int padding = 4;

        g.setColor(Color.RED);
        g.fillOval(
                playerX + padding,
                playerY + padding,
                cellSize - padding * 2,
                cellSize - padding * 2
        );

    }
}

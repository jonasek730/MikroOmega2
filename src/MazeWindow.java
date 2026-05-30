import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Random;


public class MazeWindow extends JPanel {
    private final int cols = 50;
    private final int rows = 30;
    private Pole pole;
    private final int cellSize = 20;
    private final Player player;
    private final int goalRow;
    private final int goalCol;
    private boolean hasKey;
    private final Runnable goalReachedAction;
    private boolean gameFinished;



    public MazeWindow(Pole pole, Runnable goalReachedAction, Random rnd) {
        this.pole = pole;
        this.player = new Player(1, 1);
        setBackground(Color.BLACK);
        setFocusable(true);
        setupControls();
        this.goalRow = rows / 2;
        this.goalCol = cols / 2;
        this.gameFinished = false;
        pole.getCell(goalRow, goalCol).setEnd(true);
        this.goalReachedAction = goalReachedAction;
        this.hasKey = false;
        placeRandomKey(rnd);
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
        if (gameFinished) {
            return;
        }

        int nextRow = player.getRow() + rowChange;
        int nextCol = player.getCol() + colChange;

        if (canMoveTo(nextRow, nextCol, rowChange, colChange)) {
            player.moveTo(nextRow, nextCol);
            checkKeyCollected();
            repaint();
            checkGoalReached();
        }
    }
    private void checkKeyCollected() {
        Wall currentCell = pole.getCell(player.getRow(), player.getCol());

        if (currentCell.isKey()) {
            hasKey = true;
            currentCell.setKey(false);
        }
    }

    private void checkGoalReached() {
        if (hasKey && player.getRow() == goalRow && player.getCol() == goalCol) {
            gameFinished = true;
            SwingUtilities.invokeLater(goalReachedAction);
        }
    }
    private void placeRandomKey(Random rnd) {
        int keyRow;
        int keyCol;

        do {
            keyRow = 1 + rnd.nextInt(rows - 2);
            keyCol = 1 + rnd.nextInt(cols - 2);
        } while (pole.getCell(keyRow, keyCol).isPermWall()
                || isPlayerStart(keyRow, keyCol)
                || isGoal(keyRow, keyCol));

        pole.getCell(keyRow, keyCol).setKey(true);
    }

    private boolean isPlayerStart(int row, int col) {
        return row == player.getRow() && col == player.getCol();
    }

    private boolean isGoal(int row, int col) {
        return row == goalRow && col == goalCol;
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
        drawGoal(g, marginX, marginY);
        drawKey(g, marginX, marginY);
        drawPlayer(g, marginX, marginY);

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
    private void drawKey(Graphics g, int marginX, int marginY) {
        int padding = 6;

        g.setColor(Color.YELLOW);

        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col < cols - 1; col++) {
                if (pole.getCell(row, col).isKey()) {
                    int keyX = marginX + col * cellSize;
                    int keyY = marginY + row * cellSize;
                    g.fillOval(
                            keyX + padding,
                            keyY + padding,
                            cellSize - padding * 2,
                            cellSize - padding * 2
                    );
                    return;
                }
            }
        }
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

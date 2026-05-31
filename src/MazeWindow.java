import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Random;


public class MazeWindow extends JPanel {
    private int cols = 50;
    private int rows = 28;
    private Pole pole;
    private int cellSize = 20;
    private Player player;
    private int goalRow;
    private int goalCol;
    private boolean hasKey;
    private Runnable goalReachedAction;
    private boolean gameFinished;


    /**
     * Maze panel. Generate start,player,key,end
     * @param pole Maze for painting
     * @param goalReachedAction
     * @param rnd random for placing key
     */
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

    /**
     * set up keyboard for playing
     */
    private void setupControls() {
        bindMovementKey("moveUp", KeyEvent.VK_UP, -1, 0);
        bindMovementKey("moveDown", KeyEvent.VK_DOWN, 1, 0);
        bindMovementKey("moveLeft", KeyEvent.VK_LEFT, 0, -1);
        bindMovementKey("moveRight", KeyEvent.VK_RIGHT, 0, 1);
    }

    /**
     * conect controls to the calling movePlayer metod
     * @param actionName action from the previous metod
     * @param keyCode
     * @param rowChange for moving player
     * @param colChange for moving player
     */
    private void bindMovementKey(String actionName, int keyCode, int rowChange, int colChange) {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0), actionName);
        getActionMap().put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(rowChange, colChange);
            }
        });
    }

    /**
     * control if the player can move
     * @param nextRow place to move
     * @param nextCol place to move
     * @param rowChange from where
     * @param colChange from where
     * @return if can
     */
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

    /**
     * move player repaint the panel
     * @param rowChange new place
     * @param colChange new place
     */
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

    /**
     * check if key is colected for the end
     */
    private void checkKeyCollected() {
        Wall currentCell = pole.getCell(player.getRow(), player.getCol());

        if (currentCell.isKey()) {
            hasKey = true;
            currentCell.setKey(false);
        }
    }

    /**
     * check is player is on the end
     */
    private void checkGoalReached() {
        if (hasKey && player.getRow() == goalRow && player.getCol() == goalCol) {
            gameFinished = true;
            SwingUtilities.invokeLater(goalReachedAction);
        }
    }

    /**
     * place key random in the map
     * @param rnd random placemet of the key
     */
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

    /**
     * true if player starts at starting position
     * @param row position
     * @param col position
     * @return
     */
    private boolean isPlayerStart(int row, int col) {
        return row == player.getRow() && col == player.getCol();
    }

    /**
     * return true if player is at the end position
     * @param row position
     * @param col position
     * @return
     */
    private boolean isGoal(int row, int col) {
        return row == goalRow && col == goalCol;
    }


    /**
     * paint the whole maze and center it
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        int gridWidth = cols * cellSize;
        int gridHeight = rows * cellSize;


        int marginX = (getWidth() - gridWidth) / 2;
        int marginY = (getHeight() - gridHeight) / 2;

        g.setColor(Color.BLUE);

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

                if (!cell.isRight()) {
                    g.drawLine(x + cellSize, y,
                            x + cellSize, y + cellSize);
                }

                if (!cell.isDown()) {
                    g.drawLine(x, y + cellSize,
                            x + cellSize, y + cellSize);
                }

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

    /**
     * paint the end
     * @param g
     * @param marginX
     * @param marginY
     */
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

    /**
     * paint the key
     * @param g
     * @param marginX
     * @param marginY
     */
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

    /**
     * paint the player
     * @param g
     * @param marginX
     * @param marginY
     */
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

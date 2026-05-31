import java.util.*;

public class Pole {
   private Wall[][] pole =new Wall[28][50];

    /**
     * create a pole and set perm wall     */
    public void createMaze(){
        for (int i = 0; i < pole.length; i++) {
            for (int j = 0; j < pole[i].length; j++) {
               pole[i][j] = new Wall(false,false,false);
                if(i==0||j==0){pole[i][j].setPermWall(true);}
                if(i== pole.length-1||j== pole[i].length-1){pole[i][j].setPermWall(true);}
                if (pole[i][j].isPermWall()) {
                    pole[i][j].setVisited(true);
                }
            }

        }

    }

    /**
     * create a walls and the whole maze
     * @param rnd
     */
    public void BuidlMazeDFS(Random rnd) {
    Stack<int[]> stack = new Stack<>();
    int x = pole.length / 2;
    int y = pole[0].length / 2;

    pole[x][y].setVisited(true);
    stack.push(new int[]{x, y});
    while (!stack.isEmpty()) {

        int[] current = stack.peek();
        int cx = current[0];
        int cy = current[1];

        List<int[]> neighbors = getUnvisitedNeighbors(cx, cy);

        if (!neighbors.isEmpty()) {
            int[] next = neighbors.get(rnd.nextInt(neighbors.size()));
            int nx = next[0];
            int ny = next[1];

            removeWall(cx, cy, nx, ny);

            pole[nx][ny].setVisited(true);
            stack.push(new int[]{nx, ny});

        } else {
            stack.pop();
        }
    }
    openMazeFromAllEdges();
}

    /**
     * open one direction in the maze
     */
    private void openMazeFromAllEdges() {
        int middleCol = getCols() / 2;
        int middleRow = getRows() / 2;

        Wall.drill(pole[0][middleCol], 0);
        Wall.drill(pole[getRows() - 1][middleCol], 2);
        Wall.drill(pole[middleRow][0], 3);
        Wall.drill(pole[middleRow][getCols() - 1], 1);
    }

    /**
     * make the maze easier by destroing walls
     * @param rnd
     * @param wallCount number of the walls
     */
    public void destroyRandomWalls(Random rnd, int wallCount) {
        int destroyedWalls = 0;
        int attempts = 0;
        int maxAttempts = wallCount * 20;
        int[][] directions = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1}
        };

        while (destroyedWalls < wallCount && attempts < maxAttempts) {
            attempts++;

            int row = 1 + rnd.nextInt(getRows() - 2);
            int col = 1 + rnd.nextInt(getCols() - 2);
            int[] direction = directions[rnd.nextInt(directions.length)];
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];

            if (pole[nextRow][nextCol].isPermWall()) {
                continue;
            }

            if (!hasWallBetween(row, col, nextRow, nextCol)) {
                continue;
            }

            removeWall(row, col, nextRow, nextCol);
            destroyedWalls++;
        }
    }

    /**
     * control if there is a wall between two places
     */
    private boolean hasWallBetween(int row, int col, int nextRow, int nextCol) {
        Wall cell = pole[row][col];

        if (nextRow == row - 1 && nextCol == col) {
            return !cell.isUp();
        }

        if (nextRow == row + 1 && nextCol == col) {
            return !cell.isDown();
        }

        if (nextRow == row && nextCol == col - 1) {
            return !cell.isLeft();
        }

        if (nextRow == row && nextCol == col + 1) {
            return !cell.isRight();
        }

        return false;
    }

    /**
     * check for the unvisited directions
     * @param x
     * @param y
     * @return the list of unvisited
     */
    List<int[]> getUnvisitedNeighbors(int x, int y) {
        List<int[]> list = new ArrayList<>();

        if (x > 0 && !pole[x-1][y].isVisited()) list.add(new int[]{x-1, y});
        if (x < pole.length-1 && !pole[x+1][y].isVisited()) list.add(new int[]{x+1, y});
        if (y > 0 && !pole[x][y-1].isVisited()) list.add(new int[]{x, y-1});
        if (y < pole[0].length-1 && !pole[x][y+1].isVisited()) list.add(new int[]{x, y+1});

        return list;
    }

    /**
     * remove wall from the direction
     * @param x
     * @param y
     * @param nx
     * @param ny
     */
    void removeWall(int x, int y, int nx, int ny) {
        if (nx == x - 1 && ny == y) {
            Wall.drill(pole[x][y], 0);
            Wall.drill(pole[nx][ny], 2);
        } else if (nx == x + 1 && ny == y) {
            Wall.drill(pole[x][y], 2);
            Wall.drill(pole[nx][ny], 0);
        } else if (nx == x && ny == y - 1) {
            Wall.drill(pole[x][y], 3);
            Wall.drill(pole[nx][ny], 1);
        } else if (nx == x && ny == y + 1) {
            Wall.drill(pole[x][y], 1);
            Wall.drill(pole[nx][ny], 3);
        }
    }
    public int getRows() {
        return pole.length;
    }
    public int getCols() {
        return pole[0].length;
    }
    public Wall getCell(int row, int col) {
        return pole[row][col];
    }
}

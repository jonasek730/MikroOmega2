import java.util.*;

public class Pole {
   private Wall[][] pole =new Wall[30][50];



    public void setPole(Wall[][] pole) {
        this.pole = pole;
    }
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

    public boolean printMaze(){
        for (int i = 0; i < pole.length; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < pole[i].length; j++) {
                if (pole[i][j] != null && pole[i][j].isPermWall()) {

                    row.append(" # ");
                }
                //Todo vyobrazeni
                if (pole[i][j]!=null&&!pole[i][j].isPermWall()){
                    row.append("(_)");
                }
            }

            System.out.println(row);
        }
        return true;
    }
public void BuidlMazeDFS(Random rnd){
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
                stack.pop(); // backtracking
            }
        }
    }
    List<int[]> getUnvisitedNeighbors(int x, int y) {
        List<int[]> list = new ArrayList<>();

        if (x > 0 && !pole[x-1][y].isVisited()) list.add(new int[]{x-1, y});
        if (x < pole.length-1 && !pole[x+1][y].isVisited()) list.add(new int[]{x+1, y});
        if (y > 0 && !pole[x][y-1].isVisited()) list.add(new int[]{x, y-1});
        if (y < pole[0].length-1 && !pole[x][y+1].isVisited()) list.add(new int[]{x, y+1});

        return list;
    }
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

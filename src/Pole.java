import java.util.Arrays;

public class Pole {
    Wall [][] pole = new Wall[50][50];

    public Wall[][] getPole() {
        return pole;
    }
    pj

    public void setPole(Wall[][] pole) {
        this.pole = pole;
    }
    public void createMaze(){
        for (int i = 0; i < pole.length; i++) {
            for (int j = 0; j < pole[i].length; j++) {
            pole[i][j].setWall(true);
            }

        }
    }

    public boolean printMaze(){
        for (int i = 0; i < pole.length; i++) {
            for (int j = 0; j < pole[i].length; j++) {
                if(pole[i][j].isWall()){
                    System.out.println("#");
                }else{
                    System.out.println(",");
                }
            }
        }
        return true;
    }

}

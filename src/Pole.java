import java.util.Arrays;

public class Pole {
   private Wall[][] pole =new Wall[30][50];



    public void setPole(Wall[][] pole) {
        this.pole = pole;
    }
    public void createMaze(){
        for (int i = 0; i < pole.length; i++) {
            for (int j = 0; j < pole[i].length; j++) {
               pole[i][j] = new Wall(false,false,false,false);
                if(i==0||j==0){pole[i][j].setPermWall(true);}
                if(i== pole.length-1||j== pole[i].length-1){pole[i][j].setPermWall(true);}
            }

        }
    }

    public boolean printMaze(){
        for (int i = 0; i < pole.length; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < pole[i].length; j++) {
                if (pole[i][j] != null && pole[i][j].isPermWall()) {
                    pole[i][j].setWall(true);
                    row.append("#");
                }
                if (pole[i][j]!=null&&!pole[i][j].isWall()){
                    row.append(".");
                }
            }

            System.out.println(row);
        }
        return true;
    }

}

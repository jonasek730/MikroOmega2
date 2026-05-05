import java.util.Random;

public class Wall {
    private boolean left,right,up,down;
    private boolean isKey;
    private boolean isEnd;
    private boolean permWall;
    private boolean visited;



    public boolean isKey() {
        return isKey;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isPermWall() {
        return permWall;
    }

    public void setPermWall(boolean permWall) {
        this.permWall = permWall;
    }

    public Wall(boolean isKey, boolean isEnd, boolean permWall) {
        this.isKey = isKey;
        this.isEnd = isEnd;
        this.permWall= permWall;
        this.up=false;
        this.down=false;
        this.left=false;
        this.right=false;
        this.visited=false;

    }
    public static void drill(Wall e,int c){

        if(c==0){
            e.setUp(true);
        }
        if(c==1){
            e.setRight(true);
        }
        if(c==2){
            e.setDown(true);
        }
        if(c==3){
            e.setLeft(true);
        }
    }
}




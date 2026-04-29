public class Wall {
    private boolean isWall;
    private boolean isKey;
    private boolean isEnd;
    private boolean permWall;

    public boolean isWall() {
        return isWall;
    }

    public boolean isKey() {
        return isKey;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    public boolean isPermWall() {
        return permWall;
    }

    public void setPermWall(boolean permWall) {
        this.permWall = permWall;
    }

    public Wall(boolean isWall, boolean isKey, boolean isEnd, boolean permWall) {
        this.isWall = isWall;
        this.isKey = isKey;
        this.isEnd = isEnd;
        this.permWall= permWall;
    }
}

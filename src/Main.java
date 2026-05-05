import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Pole pole =new Pole();
        Random rnd = new Random();
        pole.createMaze();
        pole.buildMaze(rnd);
        pole.printMaze();
    }
}
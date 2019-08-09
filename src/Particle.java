import java.util.LinkedList;
import java.util.List;

public class Particle {
    private double x;
    private double y;
    private List<Particle> neighbours;

    public Particle(double x, double y) {
        this.x = x;
        this.y = y;
        this.neighbours = new LinkedList<>();
    }

    public void addNeighbour(Particle p) {
        this.neighbours.add(p);
    }
}

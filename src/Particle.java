import java.util.LinkedList;
import java.util.List;

public class Particle {
    private  int id;
    private double x;
    private double y;
    private List<Particle> neighbours;

    public Particle(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.neighbours = new LinkedList<>();
    }

    public void addNeighbour(Particle p) {
        this.neighbours.add(p);
    }

    public int getId() {
        return id;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public boolean isAdjacent(Particle p, double radius, double rc) {
        return Math.sqrt(Math.pow(x-p.getX(), 2) + Math.pow(y - p.getY(), 2)) + 2*radius <= rc;
    }

    public String toString() {
        StringBuilder neighboursString = new StringBuilder();
        for(Particle p : neighbours) {
            neighboursString.append(p.getId());
            neighboursString.append(", ");
        }
        StringBuilder ans = new StringBuilder();
        ans.append("Id: ");
        ans.append(id);
        ans.append(", X: ");
        ans.append(x);
        ans.append(", Y: ");
        ans.append(y);
        ans.append(", Neighbours: ");
        ans.append(neighboursString);
        return ans.toString();
    }
}

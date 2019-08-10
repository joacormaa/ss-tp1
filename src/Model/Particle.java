package Model;

import Constants.Config;

import java.util.LinkedList;
import java.util.List;

public class Particle {
    private  int id;
    private double x;
    private double y;

    public Particle(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
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

    public boolean isAdjacent(Particle p) {
        Config c = Config.getInstance();

        return Math.sqrt(Math.pow(x-p.getX(), 2) + Math.pow(y - p.getY(), 2)) <= c.PARTICLE_INFLUENCE_RADIUS();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Particle particle = (Particle) o;

        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

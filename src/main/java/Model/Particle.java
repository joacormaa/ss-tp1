package Model;

import Constants.Config;
import Constants.ConfigSingleton;

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

    public boolean isAdjacentTo(Particle p) {
        Config c = ConfigSingleton.getInstance();
        double systemLength = c.SYSTEM_LENGTH();
        double xDifference = Math.abs(x - p.getX());
        if (xDifference > systemLength / 2)
            xDifference = systemLength - xDifference;

        double yDifference = Math.abs(y - p.getY());
        if (yDifference > systemLength / 2)
            yDifference = systemLength - yDifference;

        return Math.sqrt(Math.pow(xDifference,2) + Math.pow(yDifference,2)) <= c.PARTICLE_INFLUENCE_RADIUS();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Particle)) return false;

        Particle particle = (Particle) o;

        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

package Model;

import Constants.Config;
import NeighbourLogic.Helper;

import java.util.LinkedList;
import java.util.List;

public class Particle {
    private  int id;
    private double x;
    private double y;
    private double radius;
    private double mass;
    private transient double speed;
    private double angle;

    public Particle(int id, double x, double y, double radius, double speed, double angle, double mass) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.mass = mass;
        this.speed=speed;
        this.angle=angle;
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

    public double getRadius() {
        return radius;
    }

    public boolean isAdjacentTo(Particle p) {
        Config c = Config.getInstance();
        double systemLength = /*c.SYSTEM_LENGTH()*/0;
        double xDifference = Math.abs(x - p.getX());
        if (xDifference > systemLength / 2)
            xDifference = systemLength - xDifference;

        double yDifference = Math.abs(y - p.getY());
        if (yDifference > systemLength / 2)
            yDifference = systemLength - yDifference;

        return false;
        //return Math.sqrt(Math.pow(xDifference,2) + Math.pow(yDifference,2)) <= c.PARTICLE_INFLUENCE_RADIUS();
    }

    public double getSpeed() {
        return speed;
    }

    public double getXSpeed() { return speed * Math.cos(angle);}

    public double getYSpeed() { return speed * Math.sin(angle);}

    public double getMass() { return mass; }

    public static double getSpeed(double xspeed, double yspeed){ return Math.sqrt(Math.pow(xspeed,2) + Math.pow(yspeed,2)); }

    public static double getAngle(double xspeed, double yspeed){ return Math.atan(yspeed/xspeed);}

    public double getAngle() {
        return angle;
    }

    public String stringify(){
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(' ');
        sb.append(x);
        sb.append(' ');
        sb.append(y);
        sb.append(' ');
        sb.append(speed * Math.cos(angle));
        sb.append(' ');
        sb.append(speed * Math.sin(angle));
        sb.append(' ');
        sb.append(radius);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Particle) || o instanceof StaticParticle) return false;

        Particle particle = (Particle) o;

        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

package Model;

import Constants.Config;
import NeighbourLogic.Helper;

import java.util.LinkedList;
import java.util.List;

public class Particle implements Interactable{
    protected  int id;
    protected double x;
    protected double y;
    protected double radius;
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

    public boolean isAdjacentTo(Interactable p) {
        return true;

        //todo
//        Config c = Config.getInstance();
//        double systemLength = /*c.SYSTEM_LENGTH()*/0;
//        double xDifference = Math.abs(x - p.getX());
//        if (xDifference > systemLength / 2)
//            xDifference = systemLength - xDifference;
//
//        double yDifference = Math.abs(y - p.getY());
//        if (yDifference > systemLength / 2)
//            yDifference = systemLength - yDifference;
//
//        return false;
        //return Math.sqrt(Math.pow(xDifference,2) + Math.pow(yDifference,2)) <= c.PARTICLE_INFLUENCE_RADIUS();
    }

    public double getSpeed() {
        return speed;
    }

    public double getXSpeed() { return speed * Math.cos(angle);}

    public double getYSpeed() { return speed * Math.sin(angle);}

    public double getMass() { return mass; }

    public static double getSpeed(double xspeed, double yspeed){ return Math.sqrt(Math.pow(xspeed,2) + Math.pow(yspeed,2)); }

    public static double getAngle(double xspeed, double yspeed){ return Math.atan2(yspeed,xspeed);}

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



    @Override
    public double getXIncidentalForce(Particle p) {
        double r = Math.hypot(this.x-p.getX(),this.y-p.getY());
        double ex = (this.x-p.getX())/r;
        return getFN(p)*ex;
    }

    @Override
    public double getYIncidentalForce(Particle p) {
        double r = Math.hypot(this.x-p.getX(),this.y-p.getY());
        double ex = (this.y-p.getY())/r;
        return getFN(p)*ex;
    }

    private double getFN(Particle p) {
        Config c = Config.getInstance();
        double epsilon =  c.EPSILON();
        double rm = c.RM();

        double r = Math.hypot(this.x-p.getX(),this.y-p.getY());

        double coef = rm/r;

        return (12*epsilon/rm) *(Math.pow(coef,13)-Math.pow(coef,7));
    }
}

package Model;

import Constants.Config;
import ForceSimulator.ForceSimulatorHelper;
import GrainSimulator.GrainSimulatorHelper;
import Log.Logger;
import NeighbourLogic.Helper;

import java.util.LinkedList;
import java.util.List;

public class Particle implements Interactable{
    protected  int id;
    protected double x;
    protected double y;
    protected double radius;
    private double interactionRadius;
    private double mass;
    private transient double speed;
    private double angle;
    private ForceSimulatorHelper.Acceleration acceleration;

    public Particle(int id, double x, double y, double radius, double speed, double angle, double mass, double interactionRadius, ForceSimulatorHelper.Acceleration accel) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.mass = mass;
        this.speed=speed;
        this.angle=angle;
        this.interactionRadius = interactionRadius;
        this.acceleration = accel;
    }

    public ForceSimulatorHelper.Acceleration getAcceleration(){
        return acceleration;
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
        if(p instanceof Wall){
            Wall w = (Wall) p;
            return w.getMinimumDistance(this)<Config.getInstance().SIGMA();
        }
        Particle q = (Particle) p;
        double xdif = q.getX()-this.x;
        double ydif = q.getY()-this.y;

        return Math.hypot(xdif,ydif)<Config.getInstance().SIGMA();
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
        sb.append(getXSpeed());
        sb.append(' ');
        sb.append(getYSpeed());
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
    public double[] getXYIncidentalForce(Particle p) {
        Vector v = GrainSimulatorHelper.getForceP1ExertsOnP2(this,p);
        return new double[]{v.getX(),v.getY()};
    }

    private double getFN(Particle p) {
        Config c = Config.getInstance();
        double epsilon =  c.EPSILON();
        double rm = c.RM();

        double r = Math.hypot(this.x-p.getX(),this.y-p.getY());

        double coef = rm/r;

        return (12*epsilon/rm) *(Math.pow(coef,13)-Math.pow(coef,7));
    }

    public double getInteractionRadius() {
        return interactionRadius;
    }

    public void setInteractionRadius(double interactionRadius) {
        this.interactionRadius = interactionRadius;
    }

    public double getDistanceTo(Particle p) {
        return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2));
    }

    public static double getRandomInteractionRatio() {
        return (Math.random()/100)+0.02; //returns random number between 0.02 and 0.03
    }
}

package Metrics;

import Model.CollisionCourse;

public class SystemMetrics {
    private CollisionCourse collisionCourse;
    private int newGoals;
    private double distanceCovered;
    private double instantSpeed;
    private double time;
    private double x;
    private double y;

    public SystemMetrics(CollisionCourse collisionCourse, double distanceCovered, double instantSpeed, double x, double y,  int newGoals){
        this.collisionCourse=collisionCourse;
        this.time = collisionCourse.getTime();
        this.newGoals = newGoals;
        this.distanceCovered = distanceCovered;
        this.instantSpeed = instantSpeed;
        this.x=x;
        this.y=y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getTime() {
        return time;
    }

    public int getGoals(){
        return newGoals;
    }

    public double getDistanceCovered() {
        return distanceCovered;
    }

    public double getInstantSpeed() {
        return instantSpeed;
    }
}

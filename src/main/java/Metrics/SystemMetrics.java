package Metrics;

import Model.CollisionCourse;

public class SystemMetrics {
    private CollisionCourse collisionCourse;
    private int newGoals;
    private double time;

    public SystemMetrics(CollisionCourse collisionCourse, int newGoals){
        this.collisionCourse=collisionCourse;
        this.time = collisionCourse.getTime();
        this.newGoals = newGoals;
    }

    public double getTime() {
        return time;
    }

    public int getGoals(){
        return newGoals;
    }
}

package Metrics;

import Model.CollisionCourse;

public class SystemMetrics {
    private CollisionCourse collisionCourse;
    private double time;

    public SystemMetrics(CollisionCourse collisionCourse){
        this.collisionCourse=collisionCourse;
        this.time = collisionCourse.getTime();
    }

    public double getTime() {
        return time;
    }
}

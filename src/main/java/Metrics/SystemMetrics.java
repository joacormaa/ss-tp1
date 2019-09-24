package Metrics;

import CollisionSimulator.Collision;
import Constants.Config;
import Model.Particle;
import Model.System;
import Model.Wall;

public class SystemMetrics {
    private transient System system;
    private double fp;
    private double time;

    private static double BOLTZMANN_CONSTANT = 1.38066E-23;

    public SystemMetrics(System system){
        this.system=system;
        this.time = system.getTime();
        this.fp = calculateFP();
    }

    private float calculateFP() {
        int counter=0;
        float sum=0;
        Config c = Config.getInstance();
        double limitPosition = c.HORIZONTAL_WALL_LENGTH()/2;
        for(Particle p : system.getParticles().values()){
            counter++;
            sum+=(p.getX()<limitPosition)?0:1;
        }
        return sum/counter;
    }

    public double getFp() {
        return fp;
    }

    public double getTime() {
        return time;
    }
}

package Metrics;

import CollisionSimulator.Collision;
import Constants.Config;
import Model.Particle;
import Model.System;
import Model.Wall;

public class SystemMetrics {
    private transient System system;
    private double fp;
    private double temperature;
    private double time;
    private double pressure;


    private static double BOLTZMANN_CONSTANT = 1.38066E-23;

    public SystemMetrics(System system, Collision collision){
        this.system=system;
        this.time = system.getTime();
        this.fp = calculateFP();
        this.temperature = calculateTemperature();
        this.pressure = calculatePressure(collision);
    }

    private double calculatePressure(Collision collision) {
        if(collision.getType()!=Collision.Type.ParticleWall)
            return 0;
        Wall w = system.getWalls().get(collision.getQId());
        Particle p = system.getParticles().get(collision.getPid());

        double perpendicularVelocity = (w.isVertical())?p.getXSpeed():p.getYSpeed();

        return Math.abs(2*p.getMass()*perpendicularVelocity);
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

    private double calculateTemperature(){
        return calculateAvgKE()*3f/2*BOLTZMANN_CONSTANT;
    }

    private double calculateAvgKE() {
        double kineticEnergySum = 0;
        for(Particle p : system.getParticles().values()){
            kineticEnergySum+=calculateKE(p);
        }
        return kineticEnergySum/system.getParticles().size();
    }
    private double calculateKE(Particle p){
        return 1f/2*p.getMass()*Math.pow(p.getSpeed(),2);
    }

    public double getFp() {
        return fp;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public double getTime() {
        return time;
    }
}

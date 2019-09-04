package Metrics;

import CollisionSimulator.Collision;
import Constants.Config;
import Model.Particle;
import Model.System;

public class SystemMetrics {
    private transient System system;
    private double fp;
    private double temperature;
    private double time;
    private double pressure;


    private static double BOLTZMANN_CONSTANT = 1.38066E-23;

    public SystemMetrics(System system){
        this.system=system;
        this.time = system.getTime();
        this.fp = calculateFP();
        this.temperature = calculateTemperature();
        this.pressure = calculatePressure();
    }

    private double calculatePressure() {
        return 0; //todo: encontrar como hacerlo
    }

    private float calculateFP() {
        int counter=0;
        float sum=0;
        Config c = Config.getInstance();
        double limitPosition = c.HORIZONTAL_WALL_LENGTH()/2;
        for(Particle p : system.getParticles()){
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
        for(Particle p : system.getParticles()){
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

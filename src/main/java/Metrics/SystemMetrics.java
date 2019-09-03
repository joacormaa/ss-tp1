package Metrics;

import Constants.Config;
import Model.Particle;
import Model.System;

public class SystemMetrics {
    private transient System system;
    private double fp;
    private double temperature;
    private double time;

    public SystemMetrics(System system){
        this.system=system;
        this.time = system.getTime();
        this.fp = calculateFP();
        this.temperature = calculateTemperature();
        //todo: encontrar otras metricas
        //this.orden = calculateOrden();
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
        return calculateKE(); //todo: dividir por constante de boltzman etc...
    }

    private double calculateKE() {
        double kineticEnergySum = 0;
        for(Particle p : system.getParticles()){
            kineticEnergySum+=calculateKE(p);
        }
        return kineticEnergySum/system.getParticles().size();
    }
    private double calculateKE(Particle p){
        return 1/2*p.getMass()*Math.pow(p.getSpeed(),2);
    }

    private double calculateOrden() {

        double vxSum =0;
        double vySum =0;
        Config c = Config.getInstance();

        for (Particle p : system.getParticles()) {
            vxSum+= p.getSpeed()*Math.cos(p.getAngle());
            vySum+= p.getSpeed()*Math.sin(p.getAngle());
        }

        double mod = Math.hypot(vxSum,vySum);

        return mod / (system.getParticles().size()*c.PARTICLE_SPEED());
    }

    public double getTime() {
        return time;
    }
}

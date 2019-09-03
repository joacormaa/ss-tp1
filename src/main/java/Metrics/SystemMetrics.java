package Metrics;

import Constants.Config;
import Model.Particle;
import Model.System;

public class SystemMetrics {
    private transient System system;
    private double orden;
    private double time;

    public SystemMetrics(System system){
        this.system=system;
        this.time = system.getTime();
        //todo: encontrar otras metricas
        //this.orden = calculateOrden();
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

    public double getOrden() {
        return orden;
    }
}

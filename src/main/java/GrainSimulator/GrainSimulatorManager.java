package GrainSimulator;

import Metrics.SystemMetrics;
import Model.System;

public class GrainSimulatorManager {

    private System lastSystem;
    private SystemMetrics lastSystemMetrics;

    private GrainSimulatorPrinter gsp;


    public GrainSimulatorManager(){
        this.lastSystem = GrainSimulatorCreator.createInitialGrainSystem();
        this.gsp = new GrainSimulatorPrinter();
    }

    public SystemMetrics stepForward(double deltaT, boolean hasToPrint){
        System nextSystem = getNextSystem(deltaT);
        SystemMetrics nextSystemMetrics = new SystemMetrics(nextSystem);

        if(hasToPrint){
            gsp.outputStep(nextSystem);
            gsp.outputMetrics(nextSystemMetrics);
        }

        this.lastSystem = nextSystem;
        this.lastSystemMetrics = nextSystemMetrics;

        return lastSystemMetrics;

    }

    private System getNextSystem(double deltaT) {
        return lastSystem; //todo:
    }
}

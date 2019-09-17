package OscillationSimulator;

import Model.System;

public class OscillationManager {
    private System lastSystem;
    private OscillationPrinter op;

    public OscillationManager(){
        lastSystem = OscillationCreator.createInitialOscillationSystem();
        op = new OscillationPrinter();
    }

    public double getTime(){
        return lastSystem.getTime();
    }

    //ToDo: pido la posicion para GPCo5
    public double getGPCo5(){
        return 0;
    }

    //ToDo: pido la posicion para Beeman
    public double getBeeman(){
        return 0;
    }

    //ToDo: pido la posicion para Verlet
    public double getVerlet(){
        return 0;
    }
}

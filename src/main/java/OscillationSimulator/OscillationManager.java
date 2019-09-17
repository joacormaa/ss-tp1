package OscillationSimulator;

import Model.System;

public class OscillationManager {
    private System lastSystem;
    private OscillationPrinter op;
    private boolean htp;

    public OscillationManager(boolean hasToPrint){
        lastSystem = OscillationCreator.createInitialOscillationSystem();
        op = new OscillationPrinter();
        htp = hasToPrint;
    }

    //ToDo: avanzar el sistema un delta t
    public boolean stepForward(){
        return true;
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

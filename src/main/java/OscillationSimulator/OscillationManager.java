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
    public boolean stepForward() {
        return true;
    }
}

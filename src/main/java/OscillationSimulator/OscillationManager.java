package OscillationSimulator;

import Metrics.SystemMetrics;
import Model.System;

public class OscillationManager {
    private System lastSystem;
    private SystemMetrics sm;
    private OscillationPrinter op;

    public OscillationManager(){
        lastSystem = OscillationCreator.createInitialOscillationSystem();
        op = new OscillationPrinter();
    }
}

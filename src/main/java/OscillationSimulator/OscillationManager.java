package OscillationSimulator;

import Metrics.SystemMetrics;
import Model.System;

public class OscillationManager {
    private System lastSystem;
    private SystemMetrics sm;

    public OscillationManager(){
        lastSystem = OscillationCreator.createInitialOscillationSystem();
    }
}

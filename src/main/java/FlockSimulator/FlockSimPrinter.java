package FlockSimulator;

import Constants.Config;
import Metrics.SystemMetrics;
import Model.System;
import NeighbourLogic.Helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FlockSimPrinter {


    private static String PARTICLE_OUTPUT_PATH = "particles.ov";
    private static String METRIC_OUTPUT_PATH = "metrics.csv";
    private Config c;


    FlockSimPrinter(){
        this.c = Config.getInstance();
        Helper.resetFile(c.OUTPUT_PATH()+"/"+PARTICLE_OUTPUT_PATH);
        Helper.resetFile(c.OUTPUT_PATH()+"/"+METRIC_OUTPUT_PATH);
        addHeader(c.OUTPUT_PATH()+"/"+METRIC_OUTPUT_PATH);
    }

    private void addHeader(String path) {
        StringBuilder sb = new StringBuilder();
        sb.append("time").append(',').append("orden").append('\n');
        Helper.appendToFile(sb.toString(),path);
    }

    void outputStep(System system, SystemMetrics systemMetrics){
        Helper.appendToFile(printSystem(system),c.OUTPUT_PATH()+"/"+PARTICLE_OUTPUT_PATH);
        Helper.appendToFile(printSystemMetrics(systemMetrics),c.OUTPUT_PATH()+"/"+METRIC_OUTPUT_PATH);
    }


    private String printSystemMetrics(SystemMetrics m){
        StringBuilder sb = new StringBuilder();
        sb.append(m.getTime());
        sb.append(',');
        sb.append(m.getOrden());
        sb.append('\n');
        return sb.toString();
    }

    private String printSystem(System system){
        StringBuilder sb = new StringBuilder();
        sb.append(system.getParticles().size());
        sb.append('\n');
        sb.append('\n');
        sb.append(system.stringify());
        return sb.toString();
    }
}

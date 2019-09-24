package ForceSimulator;

import Constants.Config;
import Metrics.SystemMetrics;
import Model.System;
import NeighbourLogic.Helper;

@SuppressWarnings("ALL")
public class ForceSimulatorPrinter {

    private static String PARTICLE_OUTPUT_PATH = "particles.ov";
    private static String METRIC_OUTPUT_PATH = "metrics.csv";
    private static String WITNESS_OUTPUT_PATH = "witness.csv";
    private Config c;

    public ForceSimulatorPrinter(){
        this.c = Config.getInstance();
        Helper.resetFile(c.OUTPUT_PATH()+"/"+PARTICLE_OUTPUT_PATH);
        Helper.resetFile(c.OUTPUT_PATH()+"/"+METRIC_OUTPUT_PATH);
        Helper.resetFile(c.OUTPUT_PATH()+"/"+WITNESS_OUTPUT_PATH);
        addHeader(c.OUTPUT_PATH()+"/"+METRIC_OUTPUT_PATH);
    }

    private void addHeader(String path) {
        StringBuilder sb = new StringBuilder();
        sb.append("time").append(',').append("temperature").append(',').append("pressure")
                .append(',').append("FP").append('\n');
        Helper.appendToFile(sb.toString(),path);
    }

    void outputMetrics(SystemMetrics systemMetrics){
        Helper.appendToFile(printSystemMetrics(systemMetrics),c.OUTPUT_PATH()+"/"+METRIC_OUTPUT_PATH);
    }
    void outputStep(System system){
        Helper.appendToFile(printSystem(system),c.OUTPUT_PATH()+"/"+PARTICLE_OUTPUT_PATH);
    }

    private String printSystemMetrics(SystemMetrics m){
        StringBuilder sb = new StringBuilder();
        sb.append(m.getTime());
        sb.append(',');
        sb.append(m.getFp());
        sb.append('\n');
        return sb.toString();
    }

    private String printSystem(System system){
        StringBuilder sb = new StringBuilder();

        sb.append(system.getParticles().size() +c.PARTICLES_PER_WALL()*system.getWalls().size());
        sb.append('\n');
        sb.append('\n');
        sb.append(system.stringify());
        return sb.toString();
    }
}

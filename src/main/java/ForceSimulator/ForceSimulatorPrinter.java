package ForceSimulator;

import Constants.Config;
import Metrics.SystemMetrics;
import Model.System;
import NeighbourLogic.Helper;

import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class ForceSimulatorPrinter {

    private static String PARTICLE_OUTPUT_PATH = "particles.ov";
    private static String METRIC_OUTPUT_PATH = "metrics.csv";
    private static String COMPARISON_OUTPUT_PATH = "comparison.csv";
    private Config c;

    public ForceSimulatorPrinter(){
        this.c = Config.getInstance();
        Helper.resetFile(c.OUTPUT_PATH()+"/"+PARTICLE_OUTPUT_PATH);
        Helper.resetFile(c.OUTPUT_PATH()+"/"+METRIC_OUTPUT_PATH);
        Helper.resetFile(c.OUTPUT_PATH()+"/"+ COMPARISON_OUTPUT_PATH);
        //addHeader(c.OUTPUT_PATH()+"/"+METRIC_OUTPUT_PATH);
    }

    private void addHeader(String path, int simulationRuns) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hw");
        for(int i=0;i<simulationRuns;i++){
            sb.append(',').append("time").append(i+1);
        }
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

    public void outputComparisons(Map<Double, List<Double>> equilibriumTimes, int simulationRuns) {
        String path = c.OUTPUT_PATH()+"/"+COMPARISON_OUTPUT_PATH;
        addHeader(path, simulationRuns);
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Double, List<Double>> entry : equilibriumTimes.entrySet()){
            sb.append(entry.getKey());
            for(Double equilibriumTime : entry.getValue()){
                sb.append(',').append(equilibriumTime);
            }
            sb.append('\n');
        }
        Helper.appendToFile(sb.toString(),path);
    }
}

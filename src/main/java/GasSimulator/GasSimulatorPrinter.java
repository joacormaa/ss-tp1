package GasSimulator;

import Constants.Config;
import Metrics.SystemMetrics;
import Model.System;
import NeighbourLogic.Helper;

public class GasSimulatorPrinter {
    private static String PARTICLE_OUTPUT_PATH = "particles.ov";
    private static String METRIC_OUTPUT_PATH = "metrics.csv";
    private static String WITNESS_OUTPUT_PATH = "witness.csv";
    private Config c;

    GasSimulatorPrinter(){
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
        Helper.appendToFile(printWitnessPositions(systemMetrics), c.OUTPUT_PATH() + "/" +WITNESS_OUTPUT_PATH);
    }
    void outputStep(System system){
        Helper.appendToFile(printSystem(system),c.OUTPUT_PATH()+"/"+PARTICLE_OUTPUT_PATH);
    }


     private String printWitnessPositions(SystemMetrics systemMetrics){
        StringBuilder sb = new StringBuilder();
        Double[] xPos = systemMetrics.getWitnessParticleX();
        Double[] yPos = systemMetrics.getWitnessParticleY();
        sb.append(systemMetrics.getTime()).append(',');
        for(int i=0; i<xPos.length;i++){
            Double currX = xPos[i];
            Double currY = yPos[i];

            String x = (currX==null)?"":currX.toString();
            String y = (currY==null)?"":currY.toString();
            sb.append(x).append(',').append(y);

            if(i+1<xPos.length){
                sb.append(',');
            }
        }
        sb.append('\n');
        return sb.toString();


     }
    private String printSystemMetrics(SystemMetrics m){
        StringBuilder sb = new StringBuilder();
        sb.append(m.getTime());
        sb.append(',');
        sb.append(m.getTemperature());
        sb.append(',');
        sb.append(m.getPressure());
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

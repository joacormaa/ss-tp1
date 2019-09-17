package OscillationSimulator;

import Constants.Config;
import Metrics.SystemMetrics;
import NeighbourLogic.Helper;

public class OscillationPrinter {
    private static String PARTICLE_OUTPUT_PATH = "particle.ov";
    private static String METRIC_OUTPUT_PATH = "metrics.csv";
    private Config c;

    OscillationPrinter(){
        this.c = Config.getInstance();
        Helper.resetFile(c.OUTPUT_PATH()+"/"+PARTICLE_OUTPUT_PATH);
        Helper.resetFile(c.OUTPUT_PATH()+"/"+METRIC_OUTPUT_PATH);
        addHeader(c.OUTPUT_PATH()+"/"+METRIC_OUTPUT_PATH);
    }

    private void addHeader(String path) {
        StringBuilder sb = new StringBuilder();
        sb.append("time").append(',').append("positionGPCo5")
                .append(',').append("positionBeeman")
                .append(",").append("positionVerlet")
                .append(',').append("FP").append('\n');
        Helper.appendToFile(sb.toString(),path);
    }

    //ToDo: pasarle el systema del que va a conseguir la posicion en cada una de las aproximaciones
    void outputMetrics(OscillationManager om){
        Helper.appendToFile(printOscillationMetrics(om),c.OUTPUT_PATH()+"/"+METRIC_OUTPUT_PATH);
    }

    private String printOscillationMetrics(OscillationManager om){
        StringBuilder sb = new StringBuilder();
        sb.append(om.getTime());
        sb.append(',');
        sb.append(om.getGPCo5());
        sb.append(',');
        sb.append(om.getBeeman());
        sb.append(',');
        sb.append(om.getVerlet());
        sb.append('\n');
        return sb.toString();
    }
}

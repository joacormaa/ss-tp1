package OscillationSimulator;

import Constants.Config;
import NeighbourLogic.Helper;

public class OscillationPrinter {
    private static String PARTICLE_OUTPUT_PATH = "particle.ov";
    private static String METRIC_OUTPUT_PATH = "metrics.csv";
    private Config c;

    OscillationPrinter(){
        this.c = Config.getInstance();
        Helper.resetFile(c.OUTPUT_PATH()+"/"+PARTICLE_OUTPUT_PATH);
        Helper.resetFile(c.OUTPUT_PATH()+"/"+METRIC_OUTPUT_PATH);
    }
}

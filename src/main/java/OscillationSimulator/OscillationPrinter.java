package OscillationSimulator;

import Constants.Config;
import Model.System;
import NeighbourLogic.Helper;

public class OscillationPrinter {
    private static String PARTICLE_OUTPUT_PATH = "Particle.csv";
    private Config c;

    OscillationPrinter(){
        String[] method = {"GPCo5", "Beeman", "Verlet"};
        this.c = Config.getInstance();
        Helper.resetFile(c.OUTPUT_PATH()+"/"+method[c.NUMERIC_METHOD()]+PARTICLE_OUTPUT_PATH);
        addHeader(c.OUTPUT_PATH()+"/"+method[c.NUMERIC_METHOD()]+PARTICLE_OUTPUT_PATH);
    }

    private void addHeader(String path) {
        StringBuilder sb = new StringBuilder();
        sb.append("time").append(',').append("position").append('\n');
        Helper.appendToFile(sb.toString(),path);
    }

    void outputStep(System system){
        Helper.appendToFile(printOscillation(system),c.OUTPUT_PATH()+"/"+PARTICLE_OUTPUT_PATH);
    }

    private String printOscillation(System s){
        StringBuilder sb = new StringBuilder();
        sb.append(s.getTime());
        sb.append(',');
        sb.append(s.getOscillationParticle().getY());
        sb.append('\n');
        return sb.toString();
    }
}

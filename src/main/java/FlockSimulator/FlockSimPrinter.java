package FlockSimulator;

import Constants.Config;
import Metrics.SystemMetrics;
import Model.System;

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
        resetFile(c.OUTPUT_PATH()+"/"+PARTICLE_OUTPUT_PATH);
        resetFile(c.OUTPUT_PATH()+"/"+METRIC_OUTPUT_PATH);
        addHeader(c.OUTPUT_PATH()+"/"+METRIC_OUTPUT_PATH);
    }

    private void addHeader(String path) {
        StringBuilder sb = new StringBuilder();
        sb.append("time").append(',').append("orden").append('\n');
        appendToFile(sb.toString(),path);
    }

    public void outputStep(System system, SystemMetrics systemMetrics){
        appendToFile(printSystem(system),c.OUTPUT_PATH()+"/"+PARTICLE_OUTPUT_PATH);
        appendToFile(printSystemMetrics(systemMetrics),c.OUTPUT_PATH()+"/"+METRIC_OUTPUT_PATH);
    }

    void resetFile(String str){
        File f = new File(str);
        if(f.exists())
            f.delete();

        File parent = f.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }

        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void appendToFile(String str, String path) {
        try {
            Files.write(Paths.get(path), str.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String printSystemMetrics(SystemMetrics m){
        StringBuilder sb = new StringBuilder();
        sb.append(m.getTime());
        sb.append(',');
        sb.append(m.getOrden());
        sb.append('\n');
        return sb.toString();
    }

    String printSystem(System system){
        StringBuilder sb = new StringBuilder();
        sb.append(system.getParticles().size());
        sb.append('\n');
        sb.append('\n');
        sb.append(system.stringify());
        return sb.toString();
    }
}

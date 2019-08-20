import Constants.Config;
import FlockSimulator.FlockSimManager;
import Model.Particle;
import Model.System;
import NeighbourLogic.SystemNeighbourManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {


    private static String PARTICLE_OUTPUT_PATH = "particles.txt";
    private static String METRIC_OUTPUT_PATH = "metrics.txt";

    public static void main(String[] args) {
        runFlockSimulation();
        //runNeighbourOutput();
    }


    private static void runFlockSimulation() {
        System system = new System(0);
        FlockSimManager flockSimManager = new FlockSimManager(system);
        Config c = Config.getInstance();

        for(int i=0; i<c.AMOUNT_OF_FRAMES(); i++){
            flockSimManager.stepForward(1);
        }
        String particles = flockSimManager.printSystemOverTime();
        String metrics = flockSimManager.printMetricsOverTime();

        outputToFile(particles, c.OUTPUT_PATH()+ File.pathSeparator+PARTICLE_OUTPUT_PATH);
        outputToFile(metrics, c.OUTPUT_PATH()+ File.pathSeparator+METRIC_OUTPUT_PATH);

    }

    private static void outputToFile(String str, String path) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(path);
            fileWriter.write(str);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void runNeighbourOutput(){

        System system = new System(0);

        SystemNeighbourManager snm = new SystemNeighbourManager();

        snm.getNeighbours(system);
        snm.outputNeighbours();
    }
}
import FlockSimulator.FlockSimManager;
import Model.Particle;
import Model.System;
import NeighbourLogic.SystemNeighbourManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        runFlockSimulation();
        //runNeighbourOutput();
    }


    private static void runFlockSimulation() {
        System system = new System(0);
        FlockSimManager flockSimManager = new FlockSimManager(system);

        for(int i=0; i<1000; i++){
            flockSimManager.stepForward(1);
        }
        flockSimManager.printSystemOverTime();

    }

    public static void runNeighbourOutput(){

        System system = new System(0);

        SystemNeighbourManager snm = new SystemNeighbourManager();

        snm.getNeighbours(system);
        snm.outputNeighbours();
    }
}
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
    }

    public static void runNeighbourOutput(){

        System system = new System(0);

        SystemNeighbourManager snm = new SystemNeighbourManager();

        snm.getNeighbours(system);
        snm.outputNeighbours();
    }
}
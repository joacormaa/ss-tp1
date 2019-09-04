import Constants.Config;
import GasSimulator.GasSimulatorManager;
import Log.Logger;
import Model.Particle;
import Model.System;
import NeighbourLogic.SystemNeighbourManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //runOrderComparison();
        //runFlockSimulation();
        //runNeighbourOutput();
        runGasSimulation();
    }

    private static void runGasSimulation() {
        GasSimulatorManager gsm = new GasSimulatorManager();
        Config c = Config.getInstance();
        double time=0;
        for(int i=0; i<c.AMOUNT_OF_FRAMES();i++){
            Logger.print("Running Step '"+i+"'");
            time = gsm.stepForward();
            Logger.print("time '"+time+"'");
        }
    }

    private static void runOrderComparison() {
//        FlockSimOrderComparer oc = new FlockSimOrderComparer();
//        oc.compareDensity(5,1000,50,10);
//        oc.compareNoise(0,5,0.25,10);
    }


    private static void runFlockSimulation() {
//        System system = new System(0);
//        FlockSimManager flockSimManager = new FlockSimManager(system,true);
//        Config c = Config.getInstance();
//
//        for(int i=0; i<c.AMOUNT_OF_FRAMES(); i++) {
//            flockSimManager.stepForward(1);
//        }
//
//        flockSimManager.printSystemsOverTime();
    }

    public static void runNeighbourOutput(){
//
//        System system = new System(0);
//
//        SystemNeighbourManager snm = new SystemNeighbourManager();
//
//        snm.getNeighbours(system);
//        snm.outputNeighbours();
    }
}
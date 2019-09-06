import Constants.Config;
import GasSimulator.GasSimulatorComparer;
import GasSimulator.GasSimulatorManager;
import Log.Logger;
import Metrics.SystemMetrics;
import Model.Particle;
import Model.System;
import NeighbourLogic.SystemNeighbourManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

    private static int framesOfBalance =100;
    private static float tolerance = 0.05f;
    private static int currentFramesOfBalance =0;

    public static void main(String[] args) {
        Logger.loggerInit();
        //runOrderComparison();
        //runFlockSimulation();
        //runNeighbourOutput();
        //runGasSimulation();
        runComparison();
    }

    private static void runComparison() {
        GasSimulatorComparer gsc = new GasSimulatorComparer();
        gsc.comparePositions(0,0.04,0.005,10);
    }

    private static void runGasSimulation() {
        GasSimulatorManager gsm = new GasSimulatorManager(true);
        Config c = Config.getInstance();
        double time;
        boolean isBalanced = false;
        int i=0;
        while(!isBalanced){
            Logger.print("Running Step '"+i+++"'");
            time = gsm.stepForward();
            Logger.print("time '"+time+"'");
            isBalanced = checkBalance(gsm.getLastSystemMetrics());
        }
    }

    private static boolean checkBalance(SystemMetrics lastSystemMetrics) {
        if( Math.abs(lastSystemMetrics.getFp()-0.5) <= tolerance){
            currentFramesOfBalance++;
            Logger.print("Frame: "+currentFramesOfBalance+" of balance");
        }
        else{
            if(currentFramesOfBalance!=0)
                Logger.print("Reset balance");
            currentFramesOfBalance=0;
        }
        return currentFramesOfBalance==framesOfBalance;

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
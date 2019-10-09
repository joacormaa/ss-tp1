import Constants.Config;
import ForceSimulator.ForceSimulatorComparer;
import ForceSimulator.ForceSimulatorManager;
import GrainSimulator.GrainSimulatorManager;
import Log.Logger;
import Metrics.SystemMetrics;
import Model.Particle;
import Model.System;
import NeighbourLogic.SystemNeighbourManager;
import OscillationSimulator.OscillationComparer;
import OscillationSimulator.OscillationCreator;
import OscillationSimulator.OscillationManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("Duplicates")
public class Main {

    private static int framesOfBalance =20;
    private static float tolerance = 0.05f;
    private static int currentFramesOfBalance =0;

    public static void main(String[] args) {
        Logger.loggerInit();
        //runOrderComparison();
        //runFlockSimulation();
        //runNeighbourOutput();
        //runGasSimulation();
        //runComparison();
        //runOscillationSimulation();
        //runOscillationComparison();
        //runForceSimulation();
        //runForceComparison();
        runGrainSimultaion();
    }

    private static void runGrainSimultaion(){
        GrainSimulatorManager gsm = new GrainSimulatorManager();

        Config c = Config.getInstance();

        boolean stopped = false;
        int deltasPerPrint = (int)(0.01/c.SIMULATION_DELTA_TIME());
        int deltasSinceLastPrint = 100000;
        int i=0;
        while(!stopped){
            Logger.print("Running Step '"+i+++"'");
            SystemMetrics lastSystemMetrics = gsm.stepForward(c.SIMULATION_DELTA_TIME(),/*deltasSinceLastPrint>=deltasPerPrint*/true);
            deltasSinceLastPrint=(deltasSinceLastPrint>=deltasPerPrint)?0:deltasSinceLastPrint;
            deltasSinceLastPrint++;
            stopped = stopCondition(lastSystemMetrics);
        }
    }

    private static boolean stopCondition(SystemMetrics systemMetrics){
        return false;
    }

    private static void runForceComparison() {
        ForceSimulatorComparer fsc = new ForceSimulatorComparer();
        fsc.compareForces(50,200,5,5);
        fsc.outputComparisons(5);
    }

    private static void runOscillationComparison() {
        OscillationComparer comparer = new OscillationComparer();
        comparer.compareAllTypes();
    }

    private static void runComparison() {
//        GasSimulatorComparer gsc = new GasSimulatorComparer();
//        gsc.compareVelocity(0.01,0.1,0.01,10);
    }

    private static void runOscillationSimulation(){
        OscillationManager om = new OscillationManager(true);
        Config c = Config.getInstance();
        //Esta es la condicion de corte. Cuando el desplazamiento sea menor a un error, consideramos que el sistema
        //dejo de progresar.
        boolean stopped = false;
        int i=0;
        while(!stopped){
            //Con solamente logear el step nos alcanza ya que cada paso representa i*deltaT (tiempo)
            Logger.print("Running Step '"+i+"'");
            stopped = om.stepForward();
        }
    }

    private static void runForceSimulation(){
        ForceSimulatorManager fsm = new ForceSimulatorManager();
        Config c = Config.getInstance();

        boolean stopped = false;
        int deltasPerPrint = (int)(0.01/c.SIMULATION_DELTA_TIME());
        int deltasSinceLastPrint = 100000;
        int i=0;
        while(!stopped){
            Logger.print("Running Step '"+i+++"'");
            SystemMetrics lastSystemMetrics = fsm.stepForward(c.SIMULATION_DELTA_TIME(),deltasSinceLastPrint>=deltasPerPrint);
            deltasSinceLastPrint=(deltasSinceLastPrint>=deltasPerPrint)?0:deltasSinceLastPrint;
            deltasSinceLastPrint++;
            stopped = stopCondition(lastSystemMetrics);
        }
    }

    static boolean aquiredEquilibrium = false;
    static double equilibriumtime = 0;
    static double lastFp = 0;

//    private static boolean stopCondition(SystemMetrics lastSystemMetrics) {
//        if(!aquiredEquilibrium){
//            double fp = lastSystemMetrics.getFp();
//            aquiredEquilibrium = fp >= 0.5;
//            equilibriumtime = lastSystemMetrics.getTime();
//            if(Math.abs(lastFp-fp)>0.001){
//                Logger.print("fp change from '"+lastFp+"' to '"+fp+"'");
//                lastFp=fp;
//            }
//        }
//        else{
//            return lastSystemMetrics.getTime()>=equilibriumtime*2;
//        }
//        return false;
//    }

    private static void runGasSimulation() {
//        GasSimulatorManager gsm = new GasSimulatorManager(true);
//        Config c = Config.getInstance();
//        double time;
//        boolean isBalanced = false;
//        int i=0;
//        while(!isBalanced){
//            Logger.print("Running Step '"+i+++"'");
//            time = gsm.stepForward();
//            Logger.print("time '"+time+"'");
//            isBalanced = checkBalance(gsm.getLastSystemMetrics());
//        }
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
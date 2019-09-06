package GasSimulator;

import Constants.Config;
import Metrics.SystemMetrics;
import NeighbourLogic.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.System;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class GasSimulatorComparer {
    private Map<Double, Double[][]> positionMap;
    private Map<Double, List<Double>> noiseMap;

    private Config c;
    private static String POSITION_OUTPUT_PATH = "position.csv";
    private static String HOLE_LENGTH_OUTPUT_PATH = "holeLength.csv";
//    private static String NOISE_OUTPUT_PATH = "noise.csv";

    public GasSimulatorComparer(){
        this.positionMap = new HashMap<>();
        this.noiseMap = new HashMap<>();
        this.c = Config.getInstance();
    }

    public void comparePositions(double positionMin, double positionMax, double step, int simulationRuns){
        double holePositionBK = c.HOLE_POSITION();

        double currPosition = positionMin;

        while(currPosition<=positionMax){
            c.setHOLE_POSITION(currPosition);
            String printPath= c.OUTPUT_PATH() + "/"+currPosition + POSITION_OUTPUT_PATH;
            Helper.resetFile(printPath);

            printGasSims(simulationRuns,printPath);

            currPosition+=step;
        }
        c.setHOLE_POSITION(holePositionBK);
    }

    public void compareHoleLengths(double holeLengthMin, double holeLengthMax, double step, int simulationRuns){
        double holeLengthBK = c.HOLE_LENGTH();

        double currLength = holeLengthMin;

        while(currLength<=holeLengthMax){
            c.setHOLE_LENGTH(currLength);
            String printPath= c.OUTPUT_PATH() + "/"+currLength + HOLE_LENGTH_OUTPUT_PATH;
            Helper.resetFile(printPath);

            printGasSims(simulationRuns,printPath);

            currLength+=step;
        }
        c.setHOLE_LENGTH(holeLengthBK);
    }

    private void printGasSims(int simulationRuns, String printPath) {
        GasSimulatorManager[] gasSimulatorManagers = new GasSimulatorManager[simulationRuns];
        for(int i=0; i<simulationRuns; i++){
            gasSimulatorManagers[i]=new GasSimulatorManager(false);
        }

        Boolean[] conditionMet = new Boolean[simulationRuns];
        for(int i=0; i<conditionMet.length; i++){
            conditionMet[i]=false;
        }
        boolean condition=false;
        while(!condition){
            Double[] fps = new Double[simulationRuns];
            Double[] times = new Double[simulationRuns];
            for(int i=0; i<gasSimulatorManagers.length; i++){
                GasSimulatorManager gsm = gasSimulatorManagers[i];
                gsm.stepForward();

                fps[i]=gsm.getLastSystemMetrics().getFp();
                times[i]=gsm.getLastSystemMetrics().getTime();
                conditionMet[i] |= checkBalance(gsm);
            }

            condition = conditionMet[0];
            for(int i=1; i<gasSimulatorManagers.length; i++){
                condition &= conditionMet[i];
            }

            double avgTime = getAverage(times);

            printSimulationsStep(avgTime, fps, printPath);
        }
    }

    private void printSimulationsStep(double avgTime, Double[] fps, String printPath) {
        StringBuilder sb = new StringBuilder();
        sb.append(avgTime);
        for(int i=0; i<fps.length; i++){
            sb.append(',');
            sb.append(fps[i]);
        }
        sb.append('\n');
        Helper.appendToFile(sb.toString(),printPath);
    }

    private double getAverage(Double[] times) {
        double ret = 0;
        for (Double time : times) {
            ret += time;
        }
        return ret/times.length;
    }

    private static float tolerance = 0.05f;

    private static boolean checkBalance(GasSimulatorManager gasSimulatorManager) {
        SystemMetrics lastSystemMetrics = gasSimulatorManager.getLastSystemMetrics();
        if(lastSystemMetrics==null) return false;

        return Math.abs(lastSystemMetrics.getFp()-0.5) <= tolerance;
    }
}

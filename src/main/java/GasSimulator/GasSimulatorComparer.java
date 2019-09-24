//package GasSimulator;
//
//import Constants.Config;
//import Metrics.SystemMetrics;
//import NeighbourLogic.Helper;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import Model.System;
//import com.sun.org.apache.xpath.internal.operations.Bool;
//
//@SuppressWarnings("ALL")
//public class GasSimulatorComparer {
//    private Map<Double, Double[][]> positionMap;
//    private Map<Double, List<Double>> noiseMap;
//
//    private Config c;
//    private static String POSITION_OUTPUT_PATH = "position.csv";
//    private static String HOLE_LENGTH_OUTPUT_PATH = "holeLength.csv";
////    private static String NOISE_OUTPUT_PATH = "noise.csv";
//    private static String BALANCE_OUTPUT_PATH = "balance.csv";
//
//    public GasSimulatorComparer(){
//        this.positionMap = new HashMap<>();
//        this.noiseMap = new HashMap<>();
//        this.c = Config.getInstance();
//    }
//
//    private double T=0;
//
//    public void compareVelocity(double velocityMin, double velocityMax, double step, int simulationRuns){
//        double holePositionBK = c.HOLE_POSITION();
//
//        double velocity = velocityMin;
//        String balancePath = c.OUTPUT_PATH()+"/"+BALANCE_OUTPUT_PATH;
//        Helper.resetFile(balancePath);
//
//        printHeaderBalancePressure(simulationRuns, balancePath);
//
//        while(velocity<=velocityMax){
//            c.setVelocity(velocity);
//            String printPath= c.OUTPUT_PATH() + "/"+velocity + POSITION_OUTPUT_PATH;
//            Helper.resetFile(printPath);
//            printHeaderPressure(simulationRuns,printPath);
//
//            Double[] avgPressure = printGasPressureSims(simulationRuns,printPath);
//
//
//            appendAveragePressure(avgPressure,balancePath);
//
//            velocity+=step;
//            this.T=0;
//        }
//        c.setHOLE_POSITION(holePositionBK);
//    }
//
//    private void appendAveragePressure(Double[] avgPressure, String balancePath) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(T);
//        for(int i=0; i<avgPressure.length; i++){
//            sb.append(",");
//            sb.append(avgPressure[i]);
//        }
//        sb.append('\n');
//        Helper.appendToFile(sb.toString(),balancePath);
//    }
//
//    private void printHeaderBalancePressure(int simulationRuns, String printPath){
//        StringBuilder sb = new StringBuilder();
//        sb.append("Temp");
//        for(int i=0; i<simulationRuns; i++){
//            sb.append(",avgPressure");
//            sb.append(i);
//        }
//        sb.append('\n');
//        Helper.appendToFile(sb.toString(),printPath);
//
//    }
//
//    private void printHeaderPressure(int simulationRuns, String printPath) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("time");
//        for(int i=0; i<simulationRuns; i++){
//            sb.append(",pressure");
//            sb.append(i);
//        }
//        sb.append('\n');
//        Helper.appendToFile(sb.toString(),printPath);
//
//    }
//
//    public void comparePositions(double positionMin, double positionMax, double step, int simulationRuns){
//        double holePositionBK = c.HOLE_POSITION();
//
//        double currPosition = positionMin;
//        String printBalancePath = c.OUTPUT_PATH()+"/"+BALANCE_OUTPUT_PATH;
//        Helper.resetFile(printBalancePath);
//        printHeaderBalance(simulationRuns,printBalancePath);
//
//        while(currPosition<=positionMax){
//            c.setHOLE_POSITION(currPosition);
//            String printPath= c.OUTPUT_PATH() + "/"+currPosition + POSITION_OUTPUT_PATH;
//            Helper.resetFile(printPath);
//            printHeader(simulationRuns,printPath);
//
//            Double[] conditionTimes = printGasSims(simulationRuns,printPath);
//
//            appendBalanceData(currPosition,conditionTimes,printBalancePath);
//
//            currPosition+=step;
//        }
//        c.setHOLE_POSITION(holePositionBK);
//    }
//
//    public void compareHoleLengths(double holeLengthMin, double holeLengthMax, double step, int simulationRuns) {
//        double holeLengthBK = c.HOLE_LENGTH();
//
//        double currLength = holeLengthMin;
//
//        while (currLength <= holeLengthMax) {
//            c.setHOLE_LENGTH(currLength);
//            String printPath = c.OUTPUT_PATH() + "/" + currLength + HOLE_LENGTH_OUTPUT_PATH;
//            Helper.resetFile(printPath);
//            printHeader(simulationRuns,printPath);
//
//            printGasSims(simulationRuns, printPath);
//
//            currLength += step;
//        }
//        c.setHOLE_LENGTH(holeLengthBK);
//    }
//
//    private void appendBalanceData(double currPosition,Double[] conditionTimes, String printPath) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(currPosition);
//        for(int i=0; i<conditionTimes.length; i++){
//            sb.append(",");
//            sb.append(conditionTimes[i]);
//        }
//        sb.append('\n');
//        Helper.appendToFile(sb.toString(),printPath);
//    }
//
//    private void printHeaderBalance(int simulationRuns, String printPath) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Hp");
//        for(int i=0; i<simulationRuns; i++){
//            sb.append(",time");
//            sb.append(i);
//        }
//        sb.append('\n');
//        Helper.appendToFile(sb.toString(),printPath);
//
//    }
//
//    private void printHeader(int simulationRuns, String printPath){
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("time");
//        for(int i=0; i<simulationRuns; i++){
//            sb.append(",fp");
//            sb.append(i);
//        }
//        sb.append('\n');
//        Helper.appendToFile(sb.toString(),printPath);
//    }
//
//    private Double[] printGasSims(int simulationRuns, String printPath) {
//        GasSimulatorManager[] gasSimulatorManagers = new GasSimulatorManager[simulationRuns];
//        for(int i=0; i<simulationRuns; i++){
//            gasSimulatorManagers[i]=new GasSimulatorManager(false);
//        }
//
//        Boolean[] conditionMet = new Boolean[simulationRuns];
//        Double[] conditionTime = new Double[simulationRuns];
//        for(int i=0; i<conditionMet.length; i++){
//            conditionMet[i]=false;
//        }
//        boolean condition=false;
//        while(!condition){
//            Double[] fps = new Double[simulationRuns];
//            Double[] times = new Double[simulationRuns];
//            for(int i=0; i<gasSimulatorManagers.length; i++){
//                GasSimulatorManager gsm = gasSimulatorManagers[i];
//                gsm.stepForward();
//
//                fps[i]=gsm.getLastSystemMetrics().getFp();
//                times[i]=gsm.getLastSystemMetrics().getTime();
//
//                if(!conditionMet[i] && checkBalance(gsm)){
//                    conditionMet[i]=true;
//                    conditionTime[i]=gsm.getLastSystemMetrics().getTime();
//                }
//            }
//
//            condition = conditionMet[0];
//            for(int i=1; i<gasSimulatorManagers.length; i++){
//                condition &= conditionMet[i];
//            }
//
//            double avgTime = getAverage(times);
//
//            printSimulationsStep(avgTime, fps, printPath);
//        }
//        lastPrint=0;
//        return conditionTime;
//    }
//
//
//    private Double[] printGasPressureSims(int simulationRuns, String printPath) {
//        GasSimulatorManager[] gasSimulatorManagers = new GasSimulatorManager[simulationRuns];
//        for(int i=0; i<simulationRuns; i++){
//            gasSimulatorManagers[i]=new GasSimulatorManager(false);
//        }
//
//
//        Double[] pressureSum = new Double[simulationRuns];
//        for(int i=0; i<simulationRuns; i++) pressureSum[i] = 0.0;
//
//        Double[] ret = new Double[simulationRuns];
//
//        Boolean[] conditionMet = new Boolean[simulationRuns];
//        Double[] conditionTime = new Double[simulationRuns];
//        for(int i=0; i<conditionMet.length; i++){
//            conditionMet[i]=false;
//        }
//        boolean condition=false;
//        while(!condition){
//            Double[] pressures = new Double[simulationRuns];
//            Double[] times = new Double[simulationRuns];
//            for(int i=0; i<gasSimulatorManagers.length; i++){
//                GasSimulatorManager gsm = gasSimulatorManagers[i];
//                gsm.stepForward();
//
//                pressures[i]=gsm.getLastSystemMetrics().getPressure();
//                times[i]=gsm.getLastSystemMetrics().getTime();
//
//
//                pressureSum[i]+=pressures[i];
//                if(!conditionMet[i]){
//                    if(checkBalance(gsm)){
//                        if(conditionTime[i]==null)
//                            conditionTime[i]=gsm.getLastSystemMetrics().getTime();
//                        if(gsm.getLastSystemMetrics().getTime()-conditionTime[i]>10){
//                            this.T+=gsm.getLastSystemMetrics().getTemperature();
//                            ret[i] = pressureSum[i]/times[i];
//                            conditionMet[i]=true;
//                            conditionTime[i]=gsm.getLastSystemMetrics().getTime();
//
//                        }
//                    }else{
//                        conditionTime[i]=null;
//                    }
//                }
//
//
//            }
//
//            condition = conditionMet[0];
//            for(int i=1; i<gasSimulatorManagers.length; i++){
//                condition &= conditionMet[i];
//            }
//
//            double avgTime = getAverage(times);
//
//            printSimulationsStep(avgTime, pressures, printPath);
//        }
//        this.T/=simulationRuns;
//        lastPrint=0;
//        return ret;
//    }
//
//
//    private double lastPrint = 0;
//
//    private void printSimulationsStep(double avgTime, Double[] fps, String printPath) {
//        if(avgTime<lastPrint+c.PRINT_TIME()){
//            return;
//        }
//        lastPrint=avgTime;
//        StringBuilder sb = new StringBuilder();
//        sb.append(avgTime);
//        for(int i=0; i<fps.length; i++){
//            sb.append(',');
//            sb.append(fps[i]);
//        }
//        sb.append('\n');
//        Helper.appendToFile(sb.toString(),printPath);
//    }
//
//    private double getAverage(Double[] times) {
//        double ret = 0;
//        for (Double time : times) {
//            ret += time;
//        }
//        return ret/times.length;
//    }
//
//    private static float tolerance = 0.20f;
//
//    private static boolean checkBalance(GasSimulatorManager gasSimulatorManager) {
//        SystemMetrics lastSystemMetrics = gasSimulatorManager.getLastSystemMetrics();
//        if(lastSystemMetrics==null) return false;
//
//        return Math.abs(lastSystemMetrics.getFp()-0.5) <= tolerance;
//    }
//}

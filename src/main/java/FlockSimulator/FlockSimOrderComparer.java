//package FlockSimulator;
//
//import Constants.Config;
//import Model.System;
//import NeighbourLogic.Helper;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class FlockSimOrderComparer {
//    private Map<Double, List<Double>> densityMap;
//    private Map<Double, List<Double>> noiseMap;
//
//    private Config c;
//    private static String DENSITY_OUTPUT_PATH = "density.csv";
//    private static String NOISE_OUTPUT_PATH = "noise.csv";
//
//    public FlockSimOrderComparer(){
//        this.densityMap = new HashMap<>();
//        this.noiseMap = new HashMap<>();
//        this.c = Config.getInstance();
//    }
//
//    public void compareDensity(int particleMin, int particleMax, int step, int simulationRuns){
//        int amountOfParticlesBK = c.PARTICLES_QUANTITY();
//        Helper.resetFile(c.OUTPUT_PATH()+"/"+DENSITY_OUTPUT_PATH);
//
//        int currParticle = particleMin;
//
//        while(currParticle<=particleMax){
//            c.setAmountOfParticles(currParticle);
//
//            List<FlockSimManager> flockSimulations = getFlockSimManagers(simulationRuns);
//
//            double density = currParticle / (c.SYSTEM_LENGTH()*c.SYSTEM_LENGTH());
//            for(FlockSimManager fsm : flockSimulations){
//                double order = fsm.getLastMetric().getOrden();
//                putValue(densityMap,density,order);
//            }
//            currParticle+=step;
//        }
//        outputDensityComparison(simulationRuns);
//        c.setAmountOfParticles(amountOfParticlesBK);
//    }
//
//
//
//    public void compareNoise(double noiseMin, double noiseMax, double step, int simulationRuns){
//        double noiseBK = c.NOISE_COEFFICIENT();
//        Helper.resetFile(c.OUTPUT_PATH()+"/"+NOISE_OUTPUT_PATH);
//
//        double currNoise = noiseMin;
//
//        while(currNoise<=noiseMax){
//            c.setNoiseCoefficient(currNoise);
//
//            List<FlockSimManager> flockSimulations = getFlockSimManagers(simulationRuns);
//
//            for(FlockSimManager fsm : flockSimulations){
//                double order = fsm.getLastMetric().getOrden();
//                putValue(noiseMap,currNoise,order);
//            }
//            currNoise+=step;
//        }
//        outputNoiseComparison(simulationRuns);
//        c.setNoiseCoefficient(noiseBK);
//    }
//
//    private List<FlockSimManager> getFlockSimManagers(int simulationRuns) {
//        List<FlockSimManager> flockSimulations = new ArrayList<>();
//        for(int i=0; i<simulationRuns; i++){
//            System system = new System(0);
//            flockSimulations.add(new FlockSimManager(system,false));
//        }
//        Config c = Config.getInstance();
//
//        for(int i=0; i<c.AMOUNT_OF_FRAMES(); i++){
//            for(FlockSimManager fsm : flockSimulations){
//                fsm.stepForward(1);
//            }
//        }
//        return flockSimulations;
//    }
//
//    private void putValue(Map<Double, List<Double>> map, double key, double value) {
//        List<Double> list = map.getOrDefault(key,new ArrayList<>());
//        list.add(value);
//        map.put(key,list);
//
//    }
//    private void outputDensityComparison(int simulationRuns){
//        Helper.appendToFile(outputHeader(simulationRuns,"density"),c.OUTPUT_PATH()+"/"+DENSITY_OUTPUT_PATH);
//        Helper.appendToFile(outputMap(densityMap),c.OUTPUT_PATH()+"/"+DENSITY_OUTPUT_PATH );
//    }
//
//    private void outputNoiseComparison(int simulationRuns){
//        Helper.appendToFile(outputHeader(simulationRuns,"noise"),c.OUTPUT_PATH()+"/"+NOISE_OUTPUT_PATH);
//        Helper.appendToFile(outputMap(noiseMap),c.OUTPUT_PATH()+"/"+NOISE_OUTPUT_PATH );
//    }
//
//    private String outputHeader(int simulationRuns, String mapName){
//        StringBuilder sb = new StringBuilder();
//        sb.append(mapName);
//        for(int i=0; i<simulationRuns;i++){
//            sb.append(",order").append(i);
//        }
//        sb.append('\n');
//        return sb.toString();
//
//    }
//
//    private String outputMap(Map<Double, List<Double>> map){
//        StringBuilder sb = new StringBuilder();
//        for(Double key : map.keySet()){
//            List<Double> vals = map.get(key);
//            sb.append(key);
//            for(Double val : vals){
//                sb.append(',').append(val.toString());
//            }
//            sb.append('\n');
//        }
//        return sb.toString();
//    }
//}

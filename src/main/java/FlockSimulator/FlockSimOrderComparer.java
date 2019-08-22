package FlockSimulator;

import Constants.Config;
import Model.System;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlockSimOrderComparer {
    private Map<Double, List<Double>> densityMap;
    private Map<Double, List<Double>> noiseMap;

    private Config c;
    private static String DENSITY_OUTPUT_PATH = "density.csv";
    private static String NOISE_OUTPUT_PATH = "noise.csv";

    public FlockSimOrderComparer(){
        this.densityMap = new HashMap<>();
        this.noiseMap = new HashMap<>();
        this.c = Config.getInstance();
    }

    public void compareDensity(int particleMin, int particleMax, int step, int simulationRuns){
        int amountOfParticlesBK = c.PARTICLES_QUANTITY();
        resetFile(c.OUTPUT_PATH()+"/"+DENSITY_OUTPUT_PATH);

        int currParticle = particleMin;

        while(currParticle<=particleMax){
            c.setAmountOfParticles(currParticle);
            List<FlockSimManager> flockSimulations = new ArrayList<>();
            for(int i=0; i<simulationRuns;i++){
                System system = new System(0);
                flockSimulations.add(new FlockSimManager(system,false));
            }
            Config c = Config.getInstance();


            for(int i=0; i<c.AMOUNT_OF_FRAMES(); i++){
                for(FlockSimManager fsm : flockSimulations){
                    fsm.stepForward(1);
                }
            }

            double density = currParticle / (c.SYSTEM_LENGTH()*c.SYSTEM_LENGTH());
            for(FlockSimManager fsm : flockSimulations){
                double order = fsm.getLastMetric().getOrden();
                putValue(densityMap,density,order);
            }
            currParticle+=step;
        }
        outputDensityComparison(simulationRuns);
        c.setAmountOfParticles(amountOfParticlesBK);
    }

    private void putValue(Map<Double, List<Double>> map, double key, double value) {
        List<Double> list = map.getOrDefault(key,new ArrayList<>());
        list.add(value);
        map.put(key,list);

    }

    public void compareNoise(double noiseMin, double noiseMax, double step, int simulationRuns){
        double noiseBK = c.NOISE_COEFFICIENT();
        resetFile(c.OUTPUT_PATH()+"/"+NOISE_OUTPUT_PATH);

        double currNoise = noiseMin;

        while(currNoise<=noiseMax){
            c.setNoiseCoefficient(currNoise);
            List<FlockSimManager> flockSimulations = new ArrayList<>();
            for(int i=0; i<simulationRuns; i++){
                System system = new System(0);
                flockSimulations.add(new FlockSimManager(system,false));
            }
            Config c = Config.getInstance();

            for(int i=0; i<c.AMOUNT_OF_FRAMES(); i++){
                for(FlockSimManager fsm : flockSimulations){
                    fsm.stepForward(1);
                }
            }

            for(FlockSimManager fsm : flockSimulations){
                double order = fsm.getLastMetric().getOrden();
                putValue(noiseMap,currNoise,order);
            }
            currNoise+=step;
        }
        outputNoiseComparison(simulationRuns);
        c.setNoiseCoefficient(noiseBK);
    }

    private void outputDensityComparison(int simulationRuns){
        StringBuilder sb = new StringBuilder();
        sb.append("noise\n");
        for(int i=0; i<simulationRuns;i++){
            sb.append(",order").append(i);
        }
        sb.append('\n');
        for(Double density : densityMap.keySet()){
            List<Double> orders = densityMap.get(density);
            sb.append(density);
            for(Double order : orders){
                sb.append(',').append(order.toString());
            }
            sb.append('\n');
        }
        appendToFile(sb.toString(),c.OUTPUT_PATH()+"/"+DENSITY_OUTPUT_PATH );
    }

    private void outputNoiseComparison(int simulationRuns){
        StringBuilder sb = new StringBuilder();
        sb.append("noise");
        for(int i=0; i<simulationRuns;i++){
            sb.append(",order").append(i);
        }
        sb.append('\n');
        for(Double noise : noiseMap.keySet()){
            List<Double> orders = noiseMap.get(noise);
            sb.append(noise);
            for(Double order : orders){
                sb.append(',').append(order.toString());
            }
            sb.append('\n');
        }
        appendToFile(sb.toString(),c.OUTPUT_PATH()+"/"+NOISE_OUTPUT_PATH );
    }



    private void resetFile(String str){
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

    private void appendToFile(String str, String path) {
        try {
            Files.write(Paths.get(path), str.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

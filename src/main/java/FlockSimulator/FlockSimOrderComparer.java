package FlockSimulator;

import Constants.Config;
import Model.System;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class FlockSimOrderComparer {
    private Map<Double, Double> densityMap;
    private Map<Double, Double> noiseMap;

    private Config c;
    private static String DENSITY_OUTPUT_PATH = "density.csv";
    private static String NOISE_OUTPUT_PATH = "noise.csv";

    public FlockSimOrderComparer(){
        this.densityMap = new HashMap<>();
        this.noiseMap = new HashMap<>();
        this.c = Config.getInstance();
    }

    public void compareDensity(int particleMin, int particleMax, int step){
        int amountOfParticlesBK = c.PARTICLES_QUANTITY();
        resetFile(c.OUTPUT_PATH()+"/"+DENSITY_OUTPUT_PATH);

        int currParticle = particleMin;

        while(currParticle<=particleMax){
            c.setAmountOfParticles(currParticle);
            System system = new System(0);
            FlockSimManager flockSimManager = new FlockSimManager(system,false);
            Config c = Config.getInstance();


            for(int i=0; i<c.AMOUNT_OF_FRAMES(); i++){
                flockSimManager.stepForward(1);
            }

            double density = currParticle / (c.SYSTEM_LENGTH()*c.SYSTEM_LENGTH());
            double order = flockSimManager.getLastMetric().getOrden();

            densityMap.put(density,order);
            currParticle+=step;
        }
        outputDensityComparison();
        c.setAmountOfParticles(amountOfParticlesBK);
    }

    public void compareNoise(double noiseMin, double noiseMax, double step){
        double noiseBK = c.NOISE_COEFFICIENT();
        resetFile(c.OUTPUT_PATH()+"/"+NOISE_OUTPUT_PATH);

        double currNoise = noiseMin;

        while(currNoise<=noiseMax){
            c.setNoiseCoefficient(currNoise);
            System system = new System(0);
            FlockSimManager flockSimManager = new FlockSimManager(system,false);
            Config c = Config.getInstance();

            for(int i=0; i<c.AMOUNT_OF_FRAMES(); i++){
                flockSimManager.stepForward(1);
            }

            double order = flockSimManager.getLastMetric().getOrden();

            noiseMap.put(currNoise,order);
            currNoise+=step;
        }
        outputNoiseComparison();
        c.setNoiseCoefficient(noiseBK);
    }

    private void outputDensityComparison(){
        StringBuilder sb = new StringBuilder();
        sb.append("density, order\n");
        for(Double noise : densityMap.keySet()){
            double density = densityMap.get(noise);
            sb.append(noise).append(", ").append(density).append('\n');
        }
        appendToFile(sb.toString(),c.OUTPUT_PATH()+"/"+DENSITY_OUTPUT_PATH );
    }

    private void outputNoiseComparison(){
        StringBuilder sb = new StringBuilder();
        sb.append("noise, order\n");
        for(Double noise : noiseMap.keySet()){
            double order = noiseMap.get(noise);
            sb.append(noise).append(", ").append(order).append('\n');
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

package GrainSimulator;

import Constants.Config;
import ForceSimulator.ForceSimulatorHelper;
import Metrics.SystemMetrics;
import Model.*;
import Model.System;
import NeighbourLogic.SystemNeighbourManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("Duplicates")
public class GrainSimulatorManager {

    private System prevSystem;
    private System lastSystem;
    private SystemMetrics lastSystemMetrics;
    private SystemNeighbourManager snm;

    private GrainSimulatorPrinter gsp;
    private ForceSimulatorHelper fsh;


    public GrainSimulatorManager(){
        this.gsp = new GrainSimulatorPrinter();
        this.snm = new SystemNeighbourManager();
        this.fsh = new ForceSimulatorHelper();

        this.lastSystem = GrainSimulatorCreator.createInitialGrainSystem();
        this.prevSystem = GrainSimulatorCreator.createPreviousGrainSystem(lastSystem,snm, Config.getInstance().SIMULATION_DELTA_TIME());

        this.gsp.outputStep(prevSystem);
        this.gsp.outputStep(lastSystem);
    }

    public SystemMetrics stepForward(double deltaT, boolean hasToPrint){
        System nextSystem = getNextSystem(deltaT);
        SystemMetrics nextSystemMetrics = new SystemMetrics(nextSystem);

        if(hasToPrint){
            gsp.outputStep(nextSystem);
            gsp.outputMetrics(nextSystemMetrics);
        }

        this.prevSystem = this.lastSystem;
        this.lastSystem = nextSystem;
        this.lastSystemMetrics = nextSystemMetrics;

        return lastSystemMetrics;

    }

    private System getNextSystem(double deltaT) {
        Collection<Particle> nextParticles = getNextParticles(deltaT);
        Collection<Wall> nextWalls = lastSystem.getWalls().values();
        Collection<StaticParticle> staticParticles = lastSystem.getStaticParticles().values();

        double nextTime = lastSystem.getTime()+deltaT;

        return new System(nextTime,nextParticles,staticParticles,nextWalls); //todo:
    }

    private Collection<Particle> getNextParticles(double deltaT) {
        Collection<Particle> nextParticles = new ArrayList<>();
        Map<Particle, Set<Interactable>> neighbourMap = snm.getNeighbours(lastSystem);

        for(Particle p : lastSystem.getParticles().values()){
            Collection<Interactable> neighbours = neighbourMap.get(p);
            Particle prevP = prevSystem.getParticles().get(p.getId());
            Particle nextP = fsh.getNextParticle(p,prevP, neighbours, deltaT,lastSystem.getParticles().values());
            nextParticles.add(nextP);
        }
        return nextParticles;
    }
}

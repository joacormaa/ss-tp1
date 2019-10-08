package ForceSimulator;

import Constants.Config;
import Model.Interactable;
import Model.Particle;
import Model.System;
import Metrics.SystemMetrics;
import NeighbourLogic.SystemNeighbourManager;
import OscillationSimulator.OscillationManager;

import java.util.*;

public class ForceSimulatorManager {
    private System lastSystem;
    private System prevSystem;
    private SystemNeighbourManager snm;
    private ForceSimulatorHelper fsh;
    private ForceSimulatorPrinter fsp;
    private Config c;

    public ForceSimulatorManager(){
        //Inicio el sistema
        this.lastSystem = ForceSimulatorCreator.createInitialForceSystem();
        this.snm = new SystemNeighbourManager();
        //Inicio hacia atras en delta t
        this.prevSystem = ForceSimulatorCreator.createInitialPreviousForceSystem(lastSystem,snm);
        this.c = Config.getInstance();
        this.fsp = new ForceSimulatorPrinter();
        this.fsh = new ForceSimulatorHelper();

    }

    public SystemMetrics stepForward(double delta,boolean hasToPrint){
        Map<Particle, Set<Interactable>> neighbours = snm.getNeighbours(lastSystem);
        System nextSystem = getNextSystem(neighbours, delta);
        prevSystem = lastSystem;
        lastSystem = nextSystem;
        if(hasToPrint){
            fsp.outputStep(lastSystem);
        }
        return new SystemMetrics(lastSystem);
    }

    private System getNextSystem(Map<Particle, Set<Interactable>> neighbourMap, double delta) {
        List<Particle> nextParticles = new ArrayList<>();
        Collection<Particle> particles = lastSystem.getParticles().values();
        for(Particle p : particles){
            Collection<Interactable> neighbours = neighbourMap.get(p);
            Particle prevP = prevSystem.getParticles().get(p.getId());
            Particle nextP = fsh.getNextParticle(p,prevP, neighbours, delta,lastSystem.getParticles().values());
            nextParticles.add(nextP);
        }

        return new System(lastSystem.getTime()+delta,nextParticles,lastSystem.getStaticParticles().values(),lastSystem.getWalls().values());
    }
}

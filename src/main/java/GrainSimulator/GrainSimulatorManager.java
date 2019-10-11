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

        return new System(nextTime,nextParticles,staticParticles,nextWalls); //todo: (nadie se hace cargo de haberlo escrito)
    }

    private Collection<Particle> getNextParticles(double deltaT) {
        Collection<Particle> nextParticles = new ArrayList<>();
        Map<Particle, Set<Interactable>> neighbourMap = snm.getNeighbours(lastSystem);

        Config c = Config.getInstance();

        Collection<Particle> topRowParticles = new ArrayList<>();
        Collection<Particle> fallenParticles = new ArrayList<>();
        double topboundary = c.VERTICAL_WALL_LENGTH();
        double bottomboundary = c.VERTICAL_WALL_LENGTH() - 5*c.OFFSET();

        for(Particle p : lastSystem.getParticles().values()){
            Collection<Interactable> neighbours = neighbourMap.get(p);
            Particle prevP = prevSystem.getParticles().get(p.getId());
            Particle nextP = fsh.getNextParticle(p,prevP, neighbours, deltaT);
            if(nextP==null)
                fallenParticles.add(p);
            else{
                if(nextP.getY()>bottomboundary && nextP.getY()<topboundary)
                    topRowParticles.add(nextP);
                nextParticles.add(nextP);
            }
        }
        nextParticles.addAll(updateFallenParticles(fallenParticles,topRowParticles));

        return nextParticles;
    }

    private Collection<Particle> updateFallenParticles(Collection<Particle> fallenParticles, Collection<Particle> topRowParticles) {
        Collection<Particle> ret = new ArrayList<>();
        for(Particle oldP : fallenParticles){
            Particle newP = getRandomParticleFromTop(oldP,topRowParticles);
            topRowParticles.add(newP);
            ret.add(newP);
            lastSystem.getParticles().put(oldP.getId(),newP);
        }
        return ret;
    }

    private Particle getRandomParticleFromTop(Particle lastP, Collection<Particle> particles) {
        Config c = Config.getInstance();
        Particle newP;
        int multiplier = 1;
        do {
            multiplier = Math.min(multiplier+1, 5);
            double max_y = c.VERTICAL_WALL_LENGTH() - c.OFFSET();
            double min_y = c.VERTICAL_WALL_LENGTH() - multiplier * c.OFFSET();
            double max_x = c.HORIZONTAL_WALL_LENGTH() - c.OFFSET();
            double min_x = c.OFFSET();
            double x = (max_x-min_x)*Math.random() + min_x;
            double y = (max_y-min_y)*Math.random() + min_y;
            //ToDo: HotFix accel
            ForceSimulatorHelper.Acceleration accel = new ForceSimulatorHelper.Acceleration(0,0);
            newP =  new Particle(lastP.getId(),x,y,lastP.getRadius(),0,0, lastP.getMass(),0,accel);

        } while (thereIsCollision(newP, particles));
        return newP;
    }

    private boolean thereIsCollision(Particle p, Collection<Particle> particles){
        for(Particle q : particles){
            if(p.getDistanceTo(q)<(p.getRadius()+q.getRadius()))
                return true;
        }
        return false;
    }
}

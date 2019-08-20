package FlockSimulator;

import Constants.Config;
import Metrics.SystemMetrics;
import Model.Particle;
import Model.System;
import NeighbourLogic.Helper;
import NeighbourLogic.SystemNeighbourManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class FlockSimManager {


    private List<System> systems;
    private List<SystemMetrics> metrics;
    private SystemNeighbourManager snm;
    private Config c;
    private FlockSimPrinter printer;

    public FlockSimManager(System system){
        this.snm=new SystemNeighbourManager();
        this.systems=new LinkedList<>();
        this.metrics= new LinkedList<>();
        this.c=Config.getInstance();
        this.printer = new FlockSimPrinter();
        systems.add(system);
        metrics.add(new SystemMetrics(system));
    }

    public void stepForward(int delta){
        System nextSystem = getNextSystem(delta);
        systems.add(nextSystem);
        SystemMetrics nextSystemMetrics = new SystemMetrics(nextSystem);
        metrics.add(nextSystemMetrics);

        printer.outputStep(nextSystem,nextSystemMetrics);
    }

    private System getNextSystem(int delta) {
        System lastSystem = getLastSystem();
        Map<Particle, Set<Particle>> neighbours = snm.getNeighbours(lastSystem);

        Collection<Particle> previousParticles = lastSystem.getParticles();
        Collection<Particle> nextParticles = new ArrayList<>();
        for(Particle p : previousParticles){
            Particle next = getNextParticle(p,neighbours,delta);
            nextParticles.add(next);
        }
        return new System(lastSystem.getTime()+delta,nextParticles);
    }

    private Particle getNextParticle(Particle p, Map<Particle, Set<Particle>> neighbourMap, int delta) {
        Collection<Particle> neighbours = neighbourMap.get(p);

        double x_velocity = p.getSpeed()*Math.cos(p.getAngle());
        double y_velocity = p.getSpeed()*Math.sin(p.getAngle());

        final double x = Helper.getModule(p.getX()+x_velocity*delta,c.SYSTEM_LENGTH());

        final double y = Helper.getModule(p.getY()+y_velocity*delta,c.SYSTEM_LENGTH());


        final double angle = getAngle(p, neighbours);

        return new Particle(p.getId(),x,y,p.getSpeed(),angle);
    }

    private double getAngle(Particle p, Collection<Particle> neighbours) {
        int i=1;
        double cosineSum = Math.cos(p.getAngle());
        double sineSum = Math.sin(p.getAngle());
        for(Particle q : neighbours){
            cosineSum+=Math.cos(q.getAngle());
            sineSum+=Math.sin(q.getAngle());
            i++;
        }
        double baseValue = Math.atan2(sineSum/i,cosineSum/i);

        return baseValue + getNoise();
    }

    private double getNoise() {
        c.MAX_NOISE();
        double rand = Math.random() * c.MAX_NOISE()*2;

        return rand - c.MAX_NOISE();

    }

    private System getLastSystem() {
        return systems.get(systems.size()-1);
    }
    private SystemMetrics getLastSystemMetrics() {
        return metrics.get(metrics.size()-1);
    }
}

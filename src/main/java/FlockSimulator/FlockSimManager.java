package FlockSimulator;

import Constants.Config;
import Metrics.SystemMetrics;
import Model.Particle;
import Model.System;
import NeighbourLogic.Helper;
import NeighbourLogic.SystemNeighbourManager;
import java.util.*;

public class FlockSimManager {
    private List<System> systems;
    private List<SystemMetrics> metrics;
    private SystemNeighbourManager snm;
    private Config c;
    private FlockSimPrinter printer;
    private boolean printOutput;

    public FlockSimManager(System system,boolean printOutput){
        this.snm=new SystemNeighbourManager();
        this.systems= new LinkedList<>();
        this.systems.add(system);
        this.metrics=new LinkedList<>();
        this.metrics.add(new SystemMetrics(system));
        this.c=Config.getInstance();
        this.printer = new FlockSimPrinter();
        this.printOutput=printOutput;
    }

    public void stepForward(int delta){
        System nextSystem = getNextSystem(delta);
        SystemMetrics nextSystemMetrics = new SystemMetrics(nextSystem);

        this.systems.add(nextSystem);
        this.metrics.add(nextSystemMetrics);
    }

    private System getNextSystem(int delta) {
        Map<Particle, Set<Particle>> neighbours = snm.getNeighbours(getLastSystem());

        Collection<Particle> previousParticles = getLastSystem().getParticles();
        Collection<Particle> nextParticles = new ArrayList<>();
        for(Particle p : previousParticles){
            Particle next = getNextParticle(p,neighbours,delta);
            nextParticles.add(next);
        }
        return new System(getLastSystem().getTime()+delta,nextParticles);
    }

    private System getLastSystem() {
        return systems.get(systems.size()-1);
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
        double rand = Math.random()*2-1;
        return rand * c.NOISE_COEFFICIENT()/2;
    }

    public SystemMetrics getLastMetric() {
        return metrics.get(metrics.size()-1);
    }

    public void printSystemsOverTime() {
        if(printOutput){
            for(int i = 0; i < systems.size(); i++)
            printer.outputStep(systems.get(i),metrics.get(i));
        }
    }
}

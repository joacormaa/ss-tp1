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
    private Double[][] collisionTimesWithVerticalWalls;
    private Double[][] collisionTimesWithHorizontalWalls;
    private Double[][] collisionTimesBetweenParticles;
    private Config c;
    private FlockSimPrinter printer;
    private boolean printOutput;

    public FlockSimManager(System system,boolean printOutput){
        this.snm=new SystemNeighbourManager();
        this.systems= new LinkedList<>();
        this.systems.add(system);
        this.metrics=new LinkedList<>();
        this.metrics.add(new SystemMetrics(system));
        this.collisionTimesWithVerticalWalls = new Double[c.PARTICLES_QUANTITY()][2];
        this.collisionTimesWithHorizontalWalls = new Double[c.PARTICLES_QUANTITY()][2];
        this.collisionTimesBetweenParticles = new Double[c.PARTICLES_QUANTITY()][c.PARTICLES_QUANTITY()];
        this.c=Config.getInstance();
        this.printer = new FlockSimPrinter();
        this.printOutput=printOutput;

        initializeCollisionTimes();
    }

    private void initializeCollisionTimes() {
        List<Particle> particles = (List)getLastSystem().getParticles();
        Double collisionTime = Double.valueOf(0);
        for(int i = 0; i < c.PARTICLES_QUANTITY(); i++) {
            for(int j = 0; j < c.PARTICLES_QUANTITY(); j++) {
                if(j < 2) {
                    Particle particle = particles.get(i);
                    if(particle.getXSpeed() < 0) {
                        collisionTime = (c.VERTICAL_WALL_1_X() + particle.getRadius() - particle.getX())/particle.getXSpeed();
                        collisionTimesWithVerticalWalls[i][0] = collisionTime;
                        collisionTimesWithVerticalWalls[i][1] = null;
                    }
                    else {
                        collisionTime = (c.VERTICAL_WALL_2_X() - particle.getRadius() - particle.getX())/particle.getXSpeed();
                        collisionTimesWithVerticalWalls[i][1] = collisionTime;
                        collisionTimesWithVerticalWalls[i][0] = null;
                    }

                    if(particle.getYSpeed() < 0) {
                        collisionTime = (c.HORIZONTAL_WALL_1_Y() + particle.getRadius() - particle.getY())/particle.getYSpeed();
                        collisionTimesWithHorizontalWalls[i][0] = collisionTime;
                        collisionTimesWithHorizontalWalls[i][1] = null;
                    }
                    else {
                        collisionTime = (c.HORIZONTAL_WALL_2_Y() - particle.getRadius() - particle.getY())/particle.getYSpeed();
                        collisionTimesWithHorizontalWalls[i][1] = collisionTime;
                        collisionTimesWithHorizontalWalls[i][0] = null;
                    }
                }
                if(i < j) {
                    collisionTime = Double.valueOf(423); //TODO
                    collisionTimesBetweenParticles[i][j] = collisionTime;
                }
                else {
                    collisionTimesBetweenParticles[i][j] = null;
                }
            }
        }


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

        return new Particle(p.getId(),x,y, p.getRadius(), p.getSpeed(),angle, false);
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

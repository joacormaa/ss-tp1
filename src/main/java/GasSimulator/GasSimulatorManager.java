package GasSimulator;

import CollisionSimulator.Collision;
import CollisionSimulator.CollisionManager;
import Constants.Config;
import FlockSimulator.FlockSimPrinter;
import Metrics.SystemMetrics;
import Model.Particle;
import Model.System;
import NeighbourLogic.Helper;
import NeighbourLogic.SystemNeighbourManager;
import java.util.*;

public class GasSimulatorManager {
    private System lastSystem;
    private SystemMetrics lastSystemMetrics;
    private CollisionManager cm;
    private GasSimulatorPrinter gsp;
    private Config c;

    private double lastPrintTime;

    public GasSimulatorManager(){
        this.lastSystem = GasSystemCreator.createInitialGasSystem();
        this.cm = new CollisionManager(lastSystem);
        this.gsp = new GasSimulatorPrinter();
        this.lastPrintTime = 0;
        this.c=Config.getInstance();
    }

    public void stepForward(){
        Collision<?> collision = cm.getNextCollision();
        System nextSystem = getNextSystem(collision);
        SystemMetrics nextSystemMetrics = new SystemMetrics(nextSystem);

        this.lastSystem=nextSystem;
        this.lastSystemMetrics=nextSystemMetrics;
        cm.updateCollisions(nextSystem,collision);
        if(hasToPrint()){
            gsp.outputStep(lastSystem,lastSystemMetrics);
        }

    }

    private boolean hasToPrint() {
        if(lastPrintTime+c.PRINT_TIME()<lastSystem.getTime()){
            lastPrintTime=lastSystem.getTime();
            return true;
        }
        return false;
    }

    private System getNextSystem(Collision<?> collision) {
        List<Particle> nextParticles = new ArrayList<>();
        double delta = collision.getCollisionTime()-lastSystem.getTime();
        for(Particle p : lastSystem.getParticles()){
            if(p.equals(collision.getP())||p.equals(collision.getQ())){
                //todo: collided particle
            }
            nextParticles.add(getNextParticle(p,delta));
        }
        return new System(collision.getCollisionTime(), nextParticles,lastSystem.getStaticParticles(),lastSystem.getWalls());
    }

    private Particle getNextParticle(Particle p, double delta) {

        double x_velocity = p.getSpeed()*Math.cos(p.getAngle());
        double y_velocity = p.getSpeed()*Math.sin(p.getAngle());

        final double x = p.getX()+x_velocity*delta;
        final double y = p.getY()+y_velocity*delta;

        return new Particle(p.getId(),x,y, p.getRadius(), p.getSpeed(),p.getAngle(),p.getMass());
    }
}

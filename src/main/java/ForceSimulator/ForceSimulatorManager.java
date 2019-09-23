package ForceSimulator;

import Constants.Config;
import Model.Interactable;
import Model.Particle;
import Model.System;
import Metrics.SystemMetrics;
import NeighbourLogic.SystemNeighbourManager;

import java.util.*;

public class ForceSimulatorManager {
    private System lastSystem;
    private SystemMetrics lsMetrics;
    private System prevSystem;
    private SystemMetrics psMetrics;
    private SystemNeighbourManager snm;
    private ForceSimulatorPrinter fsp;
    private Config c;

    public ForceSimulatorManager(){
        //Inicio el sistema
        this.lastSystem = ForceSimulatorCreator.createInitialForceSystem();
        this.snm = new SystemNeighbourManager();
        //Inicio hacia atras en delta t
        this.prevSystem = ForceSimulatorCreator.createInitialPreviousForceSystem(lastSystem.getParticles());
        this.c = Config.getInstance();
        this.fsp = new ForceSimulatorPrinter();

    }

    public System stepForward(double delta,boolean hasToPrint){
        Map<Particle, Set<Interactable>> neighbours = snm.getNeighbours(lastSystem);
        lastSystem = getNextSystem(neighbours, delta);
        if(hasToPrint){
            fsp.outputStep(lastSystem);
        }
        return lastSystem;
    }

    private System getNextSystem(Map<Particle, Set<Interactable>> neighbourMap, double delta) {
        List<Particle> nextParticles = new ArrayList<>();
        Collection<Particle> particles = lastSystem.getParticles().values();
        for(Particle p : particles){
            Collection<Interactable> neighbours = neighbourMap.get(p);
            Particle nextP = getNextParticle(p, neighbours, delta);
            nextParticles.add(nextP);
        }

        return new System(lastSystem.getTime()+delta,nextParticles,lastSystem.getStaticParticles().values(),lastSystem.getWalls().values());
    }

    private Particle getNextParticle(Particle prevP,Collection<Interactable> neighbours, double delta) {
        double sumFx=0, sumFy=0;
        for(Interactable neighbour : neighbours){
            sumFx += neighbour.getXIncidentalForce(prevP);
            sumFy += neighbour.getYIncidentalForce(prevP);
        }
        double xAcc = sumFx/prevP.getMass();
        double yAcc = sumFy/prevP.getMass();

        double newX = prevP.getX() + prevP.getXSpeed()*delta + 1f/2 * xAcc*delta*delta;
        double newY = prevP.getY() + prevP.getYSpeed()*delta + 1f/2 * yAcc*delta*delta;

        double newXSpeed = prevP.getXSpeed() + xAcc*delta;
        double newYSpeed = prevP.getYSpeed() + yAcc*delta;

        double newSpeed = Math.hypot(newXSpeed,newYSpeed);
        double newAngle = Math.atan2(newYSpeed,newXSpeed);

        return new Particle(prevP.getId(),newX,newY,prevP.getRadius(),newSpeed,newAngle,prevP.getMass());
    }

}

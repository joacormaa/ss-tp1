package GasSimulator;

import CollisionSimulator.Collision;
import CollisionSimulator.CollisionManager;
import Constants.Config;
import Log.Logger;
import Metrics.SystemMetrics;
import Model.Particle;
import Model.StaticParticle;
import Model.System;
import Model.Wall;
import java.util.*;

public class GasSimulatorManager {
    private System lastSystem;
    private SystemMetrics lastSystemMetrics;
    private CollisionManager cm;
    private GasSimulatorPrinter gsp;
    private Config c;

    private double lastPrintTime;
    private boolean[] witnessParticlesCrashed;


    public GasSimulatorManager(){
        this.lastSystem = GasSystemCreator.createInitialGasSystem();
        this.cm = new CollisionManager(lastSystem);
        this.gsp = new GasSimulatorPrinter();
        this.lastPrintTime = 0;
        this.c=Config.getInstance();

        int witnessParticles = Math.min(c.PARTICLES_QUANTITY()/10,10);
        this.witnessParticlesCrashed = new boolean[witnessParticles];
    }

    public SystemMetrics getLastSystemMetrics(){
        return lastSystemMetrics;
    }

    public double stepForward(){
        Collision collision = cm.getNextCollision();
        System nextSystem = getNextSystem(collision);
        SystemMetrics nextSystemMetrics = new SystemMetrics(nextSystem,collision, witnessParticlesCrashed);

        this.lastSystem=nextSystem;
        this.lastSystemMetrics=nextSystemMetrics;
        cm.updateCollisions(nextSystem,collision);

        gsp.outputMetrics(lastSystemMetrics);
        if(hasToPrint()){
            Logger.print("Printing Step");
            gsp.outputStep(lastSystem);
        }

        return nextSystem.getTime();
    }

    private boolean hasToPrint() {
        if(lastPrintTime+c.PRINT_TIME()<lastSystem.getTime()){
            lastPrintTime=lastSystem.getTime();
            return true;
        }
        return false;
    }

    private System getNextSystem(Collision collision) {
        List<Particle> nextParticles = new ArrayList<>();
        double delta = collision.getCollisionTime()-lastSystem.getTime();

        Particle oldP =lastSystem.getParticles().get(collision.getPid());
        Particle oldQ;
        if(collision.getType()== Collision.Type.ParticleParticle){
            oldQ = lastSystem.getParticles().get(collision.getQId());
        }
        else{
            oldQ=null;
        }

        for(Particle p : lastSystem.getParticles().values()){
            if(!p.equals(oldP) && !p.equals(oldQ)){
                nextParticles.add(getNextParticle(p,delta));
            }
        }

        Particle newP = getNextParticle(oldP,delta);
        if(collision.getType() == Collision.Type.ParticleWall){
            Wall w = lastSystem.getWalls().get(collision.getQId());
            nextParticles.add(cm.getCollisionResult(newP, w));
        }
        else if (collision.getType() == Collision.Type.ParticleStaticParticle){
            StaticParticle p = lastSystem.getStaticParticles().get(collision.getQId());
            nextParticles.add(cm.getCollisionResult(newP, p));
        }
        else {
            Particle newQ = getNextParticle(oldQ,delta);
            nextParticles.addAll(cm.getCollisionResult(newP, newQ));
        }
        return new System(collision.getCollisionTime(), nextParticles,lastSystem.getStaticParticles().values(),lastSystem.getWalls().values());
    }

    private Particle getNextParticle(Particle p, double delta) {

        double x_velocity = p.getSpeed()*Math.cos(p.getAngle());
        double y_velocity = p.getSpeed()*Math.sin(p.getAngle());

        final double x = p.getX()+x_velocity*delta;
        final double y = p.getY()+y_velocity*delta;

        return new Particle(p.getId(),x,y, p.getRadius(), p.getSpeed(),p.getAngle(),p.getMass());
    }
}

package GrainSimulator;

import Constants.Config;
import ForceSimulator.ForceSimulatorHelper;
import Log.Logger;
import Model.*;
import Model.System;
import NeighbourLogic.SystemNeighbourManager;

import java.util.*;


@SuppressWarnings("ALL")
public class GrainSimulatorCreator {

    public static System createPreviousGrainSystem(System initialSystem, SystemNeighbourManager snm){
        Logger.print("Creating Initial Previous System");
        Map<Integer, Particle> particleList = initializePreviousParticles(initialSystem, snm);
        Logger.print("Finished Creating Initial System");
        return new System(particleList);
    }

    private static Map<Integer, Particle> initializePreviousParticles(System initialSystem, SystemNeighbourManager snm){
        Map<Particle, Set<Interactable>> neighbourMap = snm.getNeighbours(initialSystem);
        ForceSimulatorHelper fsh = new ForceSimulatorHelper();

        Map<Integer,Particle> ret = new HashMap<>();
        for(Map.Entry<Particle, Set<Interactable>> entry : neighbourMap.entrySet()){
            Particle p = fsh.getInitialPreviousParticle(entry.getKey(),entry.getValue(),Config.getInstance().SIMULATION_DELTA_TIME());
            ret.put(p.getId(),p);
        }
        return ret;
    }


    public static System createInitialGrainSystem(){

        Collection<Wall> walls = initializeWalls();
        Collection<Particle> particles = initializeParticles(walls);
        Collection<StaticParticle> staticParticles = new ArrayList<>(); //no static particles

        return new System(0,particles,staticParticles,walls);
    }

    private static Collection<Particle> initializeParticles(Collection<Wall> walls) {
        Config c = Config.getInstance();
        List<Particle> particles = new ArrayList<>();

//        int pq = c.PARTICLES_QUANTITY();
        int pq = 0;

        for(int i = 0; i < 0; i++) {
            Particle newParticle;
            do {
                double x = Math.random() * c.HORIZONTAL_WALL_LENGTH();
                double y = Math.random() * c.VERTICAL_WALL_LENGTH();
                newParticle = new Particle(i, x, y, c.PARTICLE_RADIUS(), 0,0,c.PARTICLE_MASS());

            } while (thereIsCollision(newParticle, particles,walls));
            particles.add(newParticle);
            //Logger.print("Added Particle #"+i);
        }
        return particles;
    }

    private static Collection<Wall> initializeWalls() {

        Config c = Config.getInstance();
        List<Wall> walls = new ArrayList<>();
        walls.add(new Wall(false, 0.0,c.VERTICAL_WALL_LENGTH(), c.HORIZONTAL_WALL_LENGTH(),0));
        walls.add(new Wall(true, 0.0,0.0, c.VERTICAL_WALL_LENGTH(),1));
        walls.add(new Wall(true, c.HORIZONTAL_WALL_LENGTH(), 0.0,c.VERTICAL_WALL_LENGTH(),2));

        double wall2Length = c.HOLE_POSITION();
        double wall2Position = c.HORIZONTAL_WALL_LENGTH() - c.HOLE_POSITION();
        double wall1Length = c.HORIZONTAL_WALL_LENGTH() - wall2Length - c.HOLE_LENGTH();

        walls.add(new Wall(false, 0, 0.0, wall1Length,3));
        walls.add(new Wall(false, wall2Position, 0,wall2Length,4));

        return walls;
    }


    private static boolean thereIsCollision(Particle newParticle, Collection<Particle> particles, Collection<Wall> walls) {
        for(Particle p : particles) {
            if(thereIsCollision(p, newParticle)) return true;
        }
        for(Wall w : walls){
            if(thereIsCollision(w,newParticle)) return true;
        }
        return false;
    }

    private static boolean thereIsCollision(Particle p1, Particle p2) {
        return Math.hypot(p1.getX() - p2.getX(), p1.getY() - p2.getY()) <= p1.getRadius() + p2.getRadius();
    }
    private static boolean thereIsCollision(Wall w, Particle p2) {
        return w.getMinimumDistance(p2)<w.getWidth() + p2.getRadius(); //todo: revisar colision
    }
}

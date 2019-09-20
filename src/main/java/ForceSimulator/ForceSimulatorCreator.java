package ForceSimulator;

import Constants.Config;
import Log.Logger;
import Model.Particle;
import Model.StaticParticle;
import Model.System;
import Model.Wall;
import sun.nio.cs.ext.IBM037;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class ForceSimulatorCreator {

    public static System createInitialForceSystem(){
        Logger.print("Creating Initial System");
        List<StaticParticle> staticParticleList = initializeStaticParticles();
        List<Wall> wallList = initializeWalls();
        List<Particle> particleList = initializeParticles(staticParticleList,wallList);
        Logger.print("Finished Creating Initial System");
        return new System(0,particleList,staticParticleList,wallList);
    }

    public static System createInitialPreviousForceSystem(Map<Integer, Particle> particles){
        Logger.print("Creating Initial Previous System");
        Map<Integer, Particle> particleList = initializePreviousParticles(particles);
        Logger.print("Finished Creating Initial System");
        return new System(particleList);
    }

    private static Map<Integer, Particle> initializePreviousParticles(Map<Integer,Particle> p){
        //ToDo: inicializar las particulas en una posicion anterior a la original. Esto se usara para los calculos.
        return null;
    }

    private static List<Particle> initializeParticles(List<StaticParticle> staticParticles, List<Wall> walls) {
        Config c = Config.getInstance();
        List<Particle> particles = new ArrayList<>();

        double maxHorizontal = (c.INNER_WALL())?c.HORIZONTAL_WALL_LENGTH()/2:c.HORIZONTAL_WALL_LENGTH();
        for(int i = 0; i < c.PARTICLES_QUANTITY(); i++) {
            Particle newParticle;
            do {
                double x = Math.random() * maxHorizontal;
                double y = Math.random() * c.VERTICAL_WALL_LENGTH();
                double angle = Math.random() * 2* Math.PI - Math.PI;
                newParticle = new Particle(i, x, y, c.PARTICLE_RADIUS(), c.PARTICLE_SPEED(),angle,c.PARTICLE_MASS());

            } while (thereIsCollision(newParticle, particles,staticParticles,walls));
            particles.add(newParticle);
            Logger.print("Added Particle #"+i);
        }
        return particles;
    }

    private static List<Wall> initializeWalls() {
        Config c = Config.getInstance();
        List<Wall> walls = new ArrayList<>();
        walls.add(new Wall(false, 0.0,c.VERTICAL_WALL_LENGTH(), c.HORIZONTAL_WALL_LENGTH(),0));
        walls.add(new Wall(false, 0.0,0.0, c.HORIZONTAL_WALL_LENGTH(),1));
        walls.add(new Wall(true, 0.0,0.0, c.VERTICAL_WALL_LENGTH(),2));
        walls.add(new Wall(true, c.HORIZONTAL_WALL_LENGTH(), 0.0,c.VERTICAL_WALL_LENGTH(),3));
        if(c.INNER_WALL()){
            double midBottomWalWidth = c.VERTICAL_WALL_LENGTH()-c.HOLE_POSITION()-c.HOLE_LENGTH();
            walls.add(new Wall(true, c.HORIZONTAL_WALL_LENGTH()/2, 0.0, midBottomWalWidth,4));
            walls.add(new Wall(true, c.HORIZONTAL_WALL_LENGTH()/2, midBottomWalWidth+c.HOLE_LENGTH(),c.HOLE_POSITION(),5));
        }
        return walls;
    }

    private static List<StaticParticle> initializeStaticParticles() {
        List<StaticParticle> staticParticles = new ArrayList<>();
        Config c = Config.getInstance();
        if(c.INNER_WALL()){
            staticParticles.add(new StaticParticle(0,c.HORIZONTAL_WALL_LENGTH()/2, c.VERTICAL_WALL_LENGTH()-c.HOLE_POSITION(), c.WALL_WIDTH()/2));
            staticParticles.add(new StaticParticle(1,c.HORIZONTAL_WALL_LENGTH()/2, c.VERTICAL_WALL_LENGTH()-c.HOLE_POSITION()-c.HOLE_LENGTH(), c.WALL_WIDTH()/2));
        }
        return staticParticles;
    }

    private static boolean thereIsCollision(Particle newParticle, Collection<Particle> particles, Collection<StaticParticle> staticParticles, Collection<Wall> walls) {
        for(Particle p : particles) {
            if(thereIsCollision(p, newParticle)) return true;
        }
        for(Wall w : walls){
            if(thereIsCollision(w,newParticle)) return true;
        }
        for(StaticParticle p: staticParticles){
            if(thereIsCollision(p,newParticle)) return true;
        }
        return false;
    }

    private static boolean thereIsCollision(Particle p1, Particle p2) {
        return Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2) <= Math.pow(p1.getRadius() + p2.getRadius(), 2);
    }
    private static boolean thereIsCollision(Wall w, Particle p2) {
        if(w.isVertical()){
            if(p2.getX()-p2.getRadius()<w.getX()+w.getWidth() && p2.getX()+p2.getRadius()> w.getX()){
                if(p2.getY()-p2.getRadius()<w.getY()+w.getLength() && p2.getY()+p2.getRadius()>w.getY())
                    return true;
            }
        }
        else{
            if(p2.getY()-p2.getRadius()<w.getY()+w.getWidth() && p2.getY()+p2.getRadius()> w.getY()){
                if(p2.getX()-p2.getRadius()<w.getX()+w.getLength() && p2.getX()+p2.getRadius()>w.getX())
                    return true;
            }

        }
        return false;
    }
}
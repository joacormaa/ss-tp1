package GasSimulator;

import Constants.Config;
import Model.Particle;
import Model.StaticParticle;
import Model.Wall;
import Model.System;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GasSystemCreator {

    public static System createInitialGasSystem(){
        List<Particle> particleList = initializeParticles();
        List<StaticParticle> staticParticleList = initializeStaticParticles();
        List<Wall> wallList = initializeWalls();
        return new System(0,particleList,staticParticleList,wallList);
    }

    private static List<Particle> initializeParticles() {
        Config c = Config.getInstance();
        List<Particle> particles = new ArrayList<>();
        for(int i = 0; i < c.PARTICLES_QUANTITY(); i++) {
            Particle newParticle;
            do {
                double x = Math.random() * c.SYSTEM_LENGTH()/2;
                double y = Math.random() * c.SYSTEM_LENGTH();
                double angle = Math.random() * 2* Math.PI - Math.PI;
                newParticle = new Particle(i, x, y, c.PARTICLE_RADIUS(), c.PARTICLE_SPEED(),angle);

            } while (thereIsCollision(newParticle, particles));
            particles.add(newParticle);
        }
        return particles;
    }

    private static List<Wall> initializeWalls() {
        Config c = Config.getInstance();
        List<Wall> walls = new ArrayList<>();
        walls.add(new Wall(false, 0.0,c.VERTICAL_WALL_LENGTH(), c.HORIZONTAL_WALL_LENGTH(),0));
        walls.add(new Wall(false, 0.0,0.0, c.VERTICAL_WALL_LENGTH(),1));
        walls.add(new Wall(true, 0.0,0.0, c.VERTICAL_WALL_LENGTH(),2));
        double midBottomWalWidth = c.VERTICAL_WALL_LENGTH()-c.HOLE_POSITION()-c.HOLE_LENGTH();
        walls.add(new Wall(true, c.HORIZONTAL_WALL_LENGTH()/2, 0.0, midBottomWalWidth,3));
        walls.add(new Wall(true, c.HORIZONTAL_WALL_LENGTH()/2, c.VERTICAL_WALL_LENGTH()-c.HOLE_LENGTH(),c.HOLE_POSITION(),4));
        walls.add(new Wall(true, c.HORIZONTAL_WALL_LENGTH(), 0.0,c.VERTICAL_WALL_LENGTH(),5));
        return walls;
    }

    private static List<StaticParticle> initializeStaticParticles() {
        List<StaticParticle> staticParticles = new ArrayList<>();
        Config c = Config.getInstance();
        staticParticles.add(new StaticParticle(0,c.HORIZONTAL_WALL_LENGTH()/2, c.VERTICAL_WALL_LENGTH()-c.HOLE_POSITION(), c.WALL_WIDTH()/2));
        return staticParticles;
    }

    private static boolean thereIsCollision(Particle newParticle, Collection<Particle> particles) { //todo: chequear colisiones con paredes tambien
        if(particles.isEmpty()) return false;
        for(Particle p : particles) {
            if(thereIsCollision(p, newParticle)) return true;
        }
        return false;
    }

    private static boolean thereIsCollision(Particle p1, Particle p2) {
        return Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2) <= Math.pow(p1.getRadius() + p2.getRadius(), 2);
    }
}

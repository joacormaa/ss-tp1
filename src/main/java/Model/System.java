package Model;

import Constants.Config;
import NeighbourLogic.Cell;


import java.util.*;

public class System {
    private transient Config c;
    private double time;
    private Collection<Particle> particles;
    private Collection<StaticParticle> staticParticles;
    private Collection<Wall> walls;

    public System(double time){
        this.time=time;
        this.particles=new ArrayList<>();
        this.c = Config.getInstance();

        initializeParticles();
        initializeStaticParticles();
        initializeWalls();
    }

    public System(double time, Collection<Particle> particles){
        this.time=time;
        this.particles=particles;
        this.c = Config.getInstance();
    }

    public double getTime() {
        return time;
    }

    public Collection<Particle> getParticles() {
        return particles;
    }

    private void initializeParticles() {
        for(int i = 0; i < c.PARTICLES_QUANTITY(); i++) {
            Particle newParticle = new Particle(0,0,0,0,0,0);
            do {
                double x = Math.random() * c.SYSTEM_LENGTH()/2;
                double y = Math.random() * c.SYSTEM_LENGTH();
                double angle = Math.random() * 2* Math.PI - Math.PI;
                newParticle = new Particle(i, x, y, c.PARTICLE_RADIUS(), c.PARTICLE_SPEED(),angle);

            } while (thereIsCollision(newParticle, particles));
            particles.add(newParticle);
        }
    }

    private void initializeWalls() { //todo: check
        walls.add(new Wall(false, 0.0, c.HORIZONTAL_WALL_WIDTH(),0));
        walls.add(new Wall(false, c.VERTICAL_WALL_WIDTH(), c.HORIZONTAL_WALL_WIDTH(),1));
        walls.add(new Wall(true, 0.0, c.VERTICAL_WALL_WIDTH(),2));
        walls.add(new Wall(true, c.HORIZONTAL_WALL_WIDTH()/2, c.VERTICAL_WALL_HOLE())/2,3));
        walls.add(new Wall(true, (c.HORIZONTAL_WALL_WIDTH()+c.VERTICAL_WALL_HOLE())/2, (c.HORIZONTAL_WALL_WIDTH()-c.VERTICAL_WALL_HOLE())/2,4));
        walls.add(new Wall(true, c.HORIZONTAL_WALL_WIDTH(), c.VERTICAL_WALL_WIDTH(),5));
    }

    private void initializeStaticParticles() {//todo: check
       staticParticles.add(new StaticParticle(0,c.HORIZONTAL_WALL_WIDTH()/2))

    }

    private boolean thereIsCollision(Particle newParticle, Collection<Particle> particles) {
        if(particles.isEmpty()) return true;
        for(Particle p : particles) {
            if(thereIsCollision(p, newParticle)) return false;
        }
        return true;
    }

    private boolean thereIsCollision(Particle p1, Particle p2) {
        return Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2) <= Math.pow(p1.getRadius() + p2.getRadius(), 2);
    }

    public String stringify(){
        StringBuilder sb = new StringBuilder();
        for (Particle p : particles) {
            sb.append(p.stringify());
            sb.append('\n');
        }
        return sb.toString();
    }

    public Collection<Wall> getWalls() {
        return walls;
    }

    public Collection<StaticParticle> getStaticParticles(){
        return staticParticles;
    }
}

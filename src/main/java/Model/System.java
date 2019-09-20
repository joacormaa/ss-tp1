package Model;

import Constants.Config;
import NeighbourLogic.Cell;


import java.util.*;

public class System {
    private transient Config c;
    private double time;
    private Map<Integer,Particle> particles;
    private Map<Integer,StaticParticle> staticParticles;
    private Map<Integer,Wall> walls;
    private Particle oscillationParticle;


    public System(double time, Collection<Particle> particles, Collection<StaticParticle> staticParticles, Collection<Wall> walls){
        this.time=time;
        this.particles=initializeParticleMap(particles);
        this.c = Config.getInstance();
        this.staticParticles=initializeStaticParticleMap(staticParticles);
        this.walls=initializeWallMap(walls);
    }

    //Oscillation Single Particle System
    public System(double time, Particle particle){
        this.time = time;
        this.oscillationParticle = particle;
        this.c = Config.getInstance();
    }

    public System(Collection<Particle> particles){
        this.particles=initializeParticleMap(particles);
    }

    public System(Map<Integer, Particle> particles){
        this.particles = particles;
    }

    private Map<Integer, Particle> initializeParticleMap(Collection<Particle> particles) {
        Map<Integer, Particle> map = new HashMap<>();
        for(Particle p : particles){
            map.put(p.getId(),p);
        }
        return map;
    }
    private Map<Integer, Wall> initializeWallMap(Collection<Wall> particles) {
        Map<Integer, Wall> map = new HashMap<>();
        for(Wall p : particles){
            map.put(p.getId(),p);
        }
        return map;
    }
    private Map<Integer, StaticParticle> initializeStaticParticleMap(Collection<StaticParticle> particles) {
        Map<Integer, StaticParticle> map = new HashMap<>();
        for(StaticParticle p : particles){
            map.put(p.getId(),p);
        }
        return map;
    }

    public double getTime() {
        return time;
    }

    public Particle getOscillationParticle() { return oscillationParticle; }

    public Map<Integer,Particle> getParticles() {
        return particles;
    }

    public String stringify(){
        StringBuilder sb = new StringBuilder();
        for (Particle p : particles.values()) {
            sb.append(p.stringify());
            sb.append('\n');
        }
//        for (StaticParticle p : staticParticles.values()) {
//            sb.append(p.stringify());
//            sb.append('\n');
//        }
        int i=0;
        for (Wall w: walls.values()){
            sb.append(w.stringify(particles.size()+c.PARTICLES_PER_WALL()*i++));
        }
        return sb.toString();
    }

    public Map<Integer,Wall> getWalls() {
        return walls;
    }

    public Map<Integer,StaticParticle> getStaticParticles(){
        return staticParticles;
    }
}

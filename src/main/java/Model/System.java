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


    public System(double time, Collection<Particle> particles, Collection<StaticParticle> staticParticles, Collection<Wall> walls){
        this.time=time;
        this.particles=particles;
        this.c = Config.getInstance();
        this.staticParticles=staticParticles;
        this.walls=walls;
    }

    public double getTime() {
        return time;
    }

    public Collection<Particle> getParticles() {
        return particles;
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

package Model;

import Constants.Config;
import NeighbourLogic.Cell;


import java.util.*;

public class System {
    private transient Config c;
    private int time;
    private Collection<Particle> particles;

    public System(int time){
        this.time=time;
        this.particles=new ArrayList<>();
        this.c = Config.getInstance();

        initializeParticles();
    }

    public System(int time, Collection<Particle> particles){
        this.time=time;
        this.particles=particles;
        this.c = Config.getInstance();
    }

    public int getTime() {
        return time;
    }

    public Collection<Particle> getParticles() {
        return particles;
    }

    private void initializeParticles() {
        for(int i = 0; i < c.PARTICLES_QUANTITY(); i++) {
            double x = Math.random() * c.SYSTEM_LENGTH();
            double y = Math.random() * c.SYSTEM_LENGTH();
            double angle = Math.random() * 2* Math.PI - Math.PI;
            particles.add(new Particle(i, x, y,c.PARTICLE_SPEED(),angle));
        }
    }

    public String stringify(){
        StringBuilder sb = new StringBuilder();
        for (Particle p : particles) {
            sb.append(p.stringify());
            sb.append('\n');
        }
        return sb.toString();
    }
}

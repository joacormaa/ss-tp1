package Model;

import Constants.Config;
import NeighbourLogic.Cell;


import java.util.*;

public class System {
    private Config c;
    private int time;
    private Collection<Particle> particles;

    public System(int time){
        this.time=time;
        this.particles=new ArrayList<>();
        this.c = Config.getInstance();

        initializeParticles();
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
            particles.add(new Particle(i, x, y));
        }
    }
}

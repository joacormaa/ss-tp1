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

        initializeParticles(c.RANDOM_PARTICLE_CREATION());
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

    private void initializeParticles(boolean isRandom) {
        if(isRandom)
        {
            for(int i = 0; i < c.PARTICLES_QUANTITY(); i++) {
                Particle newParticle = new Particle(0,0,0,0,0,0,false);
                do {
                    double x = Math.random() * c.SYSTEM_LENGTH();
                    double y = Math.random() * c.SYSTEM_LENGTH();
                    double angle = Math.random() * 2* Math.PI - Math.PI;
                    newParticle = new Particle(i, x, y, c.PARTICLE_RADIUS(), c.PARTICLE_SPEED(),angle, false);

                } while (thereIsCollision(newParticle, particles));
                particles.add(newParticle);
            }
        }else{
            int sqrtParticles = (int) Math.ceil(Math.sqrt(c.PARTICLES_QUANTITY()));
            double step = c.SYSTEM_LENGTH()/sqrtParticles;
            int id=0;
            for(int i=0; i< sqrtParticles;i++){
                for(int j=0; j<sqrtParticles;j++){
                    double angle = ((i+j)%4-1) * Math.PI/2;
                    particles.add(new Particle(id++,step*j,step*i, c.PARTICLE_RADIUS(), c.PARTICLE_SPEED(),angle, false));
                }
            }
        }
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
}

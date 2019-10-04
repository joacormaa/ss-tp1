package OscillationSimulator;

import Constants.Config;
import Log.Logger;
import Model.Particle;
import Model.System;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OscillationCreator {

    public static System createInitialOscillationSystem(){
        Logger.print("Creating initial Oscillation System");
        return new System(0, initializeParticle());
    }

    private static Particle initializeParticle(){
        Config c = Config.getInstance();
        return new Particle(0,0,c.OSCILLATOR_A(),0,c.OSCILLATOR_A()*c.OSCILLATOR_G()/(2*c.PARTICLE_MASS()),-Math.PI/2,c.PARTICLE_MASS(), Particle.getRandomInteractionRatio());
    }

    public static Particle getInitialPreviousParticle(double deltaT, double force, Particle p0) {
        double currentAcceleration = force/p0.getMass();

        double previousPosition = p0.getY() - deltaT*p0.getYSpeed() + deltaT*deltaT*currentAcceleration/2;
        double previousSpeed = p0.getYSpeed() - deltaT*currentAcceleration;

        return new Particle(p0.getId(),p0.getX(),previousPosition,p0.getRadius(),previousSpeed,p0.getAngle(),p0.getMass(), Particle.getRandomInteractionRatio());
    }
}

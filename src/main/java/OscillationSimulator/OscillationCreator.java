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
        return new Particle(0,0,1,0,c.OSCILLATOR_A()*c.OSCILLATOR_G()/(2*c.PARTICLE_MASS()),-Math.PI/2,c.PARTICLE_MASS());
    }
}
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

    private static Collection<Particle> initializeParticle(){
        Config c = Config.getInstance();
        List<Particle> particles = new ArrayList<>();

        //Utilizando la configuracion inicial que le pasemos hay que darle la posicion y velocidad a la particula
        return particles;
    }
}

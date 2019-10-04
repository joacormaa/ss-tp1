package GrainSimulator;

import Model.Particle;
import Model.StaticParticle;
import Model.System;
import Model.Wall;

import java.util.ArrayList;
import java.util.Collection;

public class GrainSimulatorCreator {

    public static System createInitialGrainSystem(){

        Collection<Wall> walls = initializeWalls();
        Collection<Particle> particles = initializeParticles();
        Collection<StaticParticle> staticParticles = new ArrayList<>(); //no static particles

        return new System(0,particles,staticParticles,walls);
    }

    private static Collection<Particle> initializeParticles() {
        return null;
    }

    private static Collection<Wall> initializeWalls() {


    }
}

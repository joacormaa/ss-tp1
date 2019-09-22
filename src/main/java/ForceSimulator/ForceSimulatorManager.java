package ForceSimulator;

import Model.Interactable;
import Model.Particle;
import Model.System;
import Metrics.SystemMetrics;
import NeighbourLogic.SystemNeighbourManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ForceSimulatorManager {
    private System lastSystem;
    private SystemMetrics lsMetrics;
    private System prevSystem;
    private SystemMetrics psMetrics;
    private SystemNeighbourManager snm;

    public ForceSimulatorManager(){
        //Inicio el sistema
        this.lastSystem = ForceSimulatorCreator.createInitialForceSystem();
        //Inicio hacia atras en delta t
        this.prevSystem = ForceSimulatorCreator.createInitialPreviousForceSystem(lastSystem.getParticles());
    }

    public boolean stepForward(){
        //ToDo: Calculo las Fuerzas para cada particula
        Map<Particle, Set<Interactable>> neighbours = snm.getNeighbours(lastSystem);
        //ToDo: Calculo la nueva posicion de cada particula
        return true;
    }

}

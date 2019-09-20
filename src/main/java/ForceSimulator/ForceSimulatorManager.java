package ForceSimulator;

import Model.System;
import Metrics.SystemMetrics;
import NeighbourLogic.SystemNeighbourManager;

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
        snm.getNeighbours(lastSystem);
        //ToDo: Calculo la nueva posicion de cada particula
        return true;
    }

}

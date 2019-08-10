import Model.Particle;
import Model.System;
import NeighbourLogic.SystemNeighbourManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System system = new System(0);

        SystemNeighbourManager snm = new SystemNeighbourManager(system);
        snm.outputNeighbours();

    }
}
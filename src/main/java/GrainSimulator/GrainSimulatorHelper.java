package GrainSimulator;

import Constants.Config;
import Model.Particle;

public class GrainSimulatorHelper {
    Config c;

    public GrainSimulatorHelper() {
        c = Config.getInstance();
    }
    public double getForce(Particle p1, Particle p2) {

    }

    private double getTangencialForce(Particle p1, Particle p2) {

    }

    private double getNormalForce(Particle p1, Particle p2) {
        return -c.KN()*p1
    }
}

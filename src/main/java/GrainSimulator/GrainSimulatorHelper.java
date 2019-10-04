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
        double kn = c.KN();
        double sigma = calculateXi(p1, p2);
        return -kn*sigma;
    }

    private double calculateXi(Particle p1, Particle p2) {
        return p1.getInteractionRadius() + p2.getInteractionRadius() - getPositionDifference(p1, p2);
    }

    private double getPositionDifference(Particle p1, Particle p2) {
        return p1.getDistanceTo(p2);
    }


}

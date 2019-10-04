package GrainSimulator;

import Constants.Config;
import Model.Particle;

public class GrainSimulatorHelper {
    public class Force{
        double normalForce;
        double tangentialForce;

        public Force(double normalForce, double tangentialForce){
            this.normalForce = normalForce;
            this.tangentialForce = tangentialForce;
        }
    }
    Config c;

    public GrainSimulatorHelper() {
        c = Config.getInstance();
    }
    public Force getForce(Particle p1, Particle p2) {
        //calcular Xi
        double xi = calculateXi(p1, p2);
        //calculate VelRelTang
        double velRelTang = calculateVelRelTang(p1, p2);

        //Normal
        double normalForce = getNormalForce(xi);

        //Tangential
        double tangentialForce = getTangentialForce(xi, velRelTang);

        return new Force(normalForce, tangentialForce);
    }

    private double calculateXi(Particle p1, Particle p2) {
        return p1.getInteractionRadius() + p2.getInteractionRadius() - getPositionDifference(p1, p2);
    }

    private double calculateVelRelTang(Particle p1, Particle p2){
        return 0;
    }

    private double getPositionDifference(Particle p1, Particle p2) {
        return p1.getDistanceTo(p2);
    }

    private double getNormalForce(double xi) {
        return -c.KN()*xi;
    }


    private double getTangentialForce(double xi, double velRelTang) {
        return -c.KT()*xi*velRelTang;
    }

}

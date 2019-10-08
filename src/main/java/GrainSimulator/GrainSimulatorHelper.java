package GrainSimulator;

import Constants.Config;
import Model.Particle;

public final class GrainSimulatorHelper {
    Config c = Config.getInstance();
    static double normalVersor[];
    static double tangencialVersor[];

    private GrainSimulatorHelper() {
        throw new AssertionError();
    }

    public static double getXForce(Particle p1, Particle p2) {
        double[] x = new double[]{1,0};
        return getProduct(getForce(p1,p2), x);
    }

    public static double getYForce(Particle p1, Particle p2) {
        double[] y = new double[]{0,1};
        return getProduct(getForce(p1,p2), y);
    }

    private static double[] getForce(Particle p1, Particle p2) {
        double[] tangencialForce = getTangencialForce(p1,p2);
        double[] normalForce = getNormalForce(p1,p2);
        return arraySum(tangencialForce, normalForce);
    }

    private static double[] arraySum(double[] v1, double[] v2) {
        double[] ret = new double[v1.length];
        for(int i = 0; i < v1.length; i++) {
            ret[i] = v1[i] + v2[i];
        }
        return ret;
    }

    private static double getVectorAbs(double[] v) {
        double ret = 0;
        for(double num : v) {
            ret += Math.pow(num, 2);
        }
        return Math.sqrt(ret);
    }

    private static double[] getTangencialForce(Particle p1, Particle p2) {
        tangencialVersor = getTangencialVersor(p1, p2);
        double kt = Config.getInstance().KT();
        double xi = calculateXi(p1, p2);
        if(xi < 0)
            return new double[]{0,0};
        double[] relativeVelocity = calculateRelativeVelocity(p1, p2);
        return getProduct(-kt * xi * getProduct(relativeVelocity, tangencialVersor), tangencialVersor);
    }

    private static double[] calculateRelativeVelocity(Particle p1, Particle p2) {
        double[] p1Vel = new double[]{ p1.getXSpeed(), p1.getYSpeed() };
        double[] p2Vel = new double[]{ p2.getXSpeed(), p2.getYSpeed() };
        return calculateDifference(p1Vel, p2Vel);
    }

    private static double[] calculatePositionDifference(Particle p1, Particle p2) {
        double[] p1Pos = new double[]{ p1.getX(), p1.getY() };
        double[] p2Pos = new double[]{ p2.getX(), p2.getY() };
        return calculateDifference(p1Pos, p2Pos);
    }

    private static double[] calculateDifference(double[] v1, double[] v2) {
        double[] ret = new double[v1.length];
        for(int i = 0; i < v1.length; i++) {
            ret[i] = v1[i] - v2[i];
        }
        return ret;
    }

    private static double[] getNormalForce(Particle p1, Particle p2) {
        normalVersor = getNormalVersor(p1, p2);
        double kn = Config.getInstance().KN();
        double xi = calculateXi(p1, p2);
        if(xi < 0)
            return new double[]{0, 0};
        return getProduct(-kn*xi,normalVersor);
    }

    private static double[] getNormalVersor(Particle p1, Particle p2) {
        double[] positionDifference = calculatePositionDifference(p1,p2);
        return getVersorFromVector(positionDifference);
    }

    private static double[] getTangencialVersor(Particle p1, Particle p2) {
        double[] positionDifference = calculatePositionDifference(p1,p2);
        return getPerpendicularVector(getVersorFromVector(positionDifference));
    }

    private static double[] getPerpendicularVector(double[] v) {
        return new double[]{-v[1], v[0]};
    }

    private static double[] getVersorFromVector(double[] v) {
        return getDivision(v, getVectorAbs(v));
    }

    private static double[] getProduct(double number, double[] vector) {
        double ret[] = new double[vector.length];
        for(int i = 0; i < vector.length; i++) {
            ret[i] = number * vector[i];
        }
        return ret;
    }

    private static double[] getDivision(double[] vector, double number) {
        double ret[] = new double[vector.length];
        for(int i = 0; i < vector.length; i++) {
            ret[i] = vector[i] / number;
        }
        return ret;
    }

    private static double getProduct(double[] v1, double[] v2) {
        double ret = 0;
        for(int i = 0; i < v1.length; i++) {
            ret += v1[i] * v2[i];
        }
        return ret;
    }

    private static double calculateXi(Particle p1, Particle p2) {
        return p1.getRadius() + p2.getRadius() - getPositionDifference(p1, p2);
    }

    private static double getPositionDifference(Particle p1, Particle p2) {
        return p1.getDistanceTo(p2);
    }

    private double getNormalForce(double xi) {
        return -c.KN()*xi;
    }


    private double getTangentialForce(double xi, double velRelTang) {
        return -c.KT()*xi*velRelTang;
    }

}

package CollisionSimulator;

import Model.Particle;
import Model.Wall;

public class CollisionManager {
    public Double getCollisionTime(Particle p, Wall w) {
        Double particlePosition = w.isVertical() ? p.getX():p.getY();
        Double particleSpeed = w.isVertical() ? p.getXSpeed():p.getYSpeed();
        if((particlePosition < w.getPosition() && particleSpeed < 0) || (particlePosition > w.getPosition() && particleSpeed > 0))
            return null;

        Integer coefficient = particlePosition < w.getPosition() ? -1 : 1;
        return (w.getPosition() + coefficient * p.getRadius() - particlePosition)/particleSpeed;
    }

    public Double getCollisionTime(Particle p1, Particle p2) {
        double[] deltaR = new double[2];
        double[] deltaV = new double[2];
        double squareDeltaR;
        double squareDeltaV;
        double productDeltaVDeltaR;
        double d;
        double sigma;

        sigma = p1.getRadius() + p2.getRadius();

        deltaR[0] = p2.getX() - p1.getX();
        deltaR[1] = p2.getY() - p1.getY();

        deltaV[0] = p2.getXSpeed() - p1.getXSpeed();
        deltaV[1] = p2.getYSpeed() - p1.getYSpeed();

        squareDeltaR = Math.pow(deltaR[0], 2) + Math.pow(deltaR[1], 2);
        squareDeltaV = Math.pow(deltaV[0], 2) + Math.pow(deltaV[1], 2);
        productDeltaVDeltaR = deltaR[0]*deltaV[0] + deltaR[1]*deltaV[1];

        d = Math.pow(productDeltaVDeltaR, 2) - squareDeltaV*(squareDeltaR-Math.pow(sigma, 2));


        if(productDeltaVDeltaR >= 0 || d < 0) return null;
        return (productDeltaVDeltaR + Math.sqrt(d))/(-squareDeltaV);
    }
}

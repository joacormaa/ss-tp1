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
}

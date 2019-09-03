package Model;

public class StaticParticle extends Particle {

    public StaticParticle(int id, double x, double y, double radius) {
        super(id, x, y, radius, 0, 0,Double.MAX_VALUE);
    }
}

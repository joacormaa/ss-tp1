package Model;

import Constants.Config;

public class StaticParticle extends Particle {
    Config c = Config.getInstance();
    public StaticParticle(int id, double x, double y, double radius) {
        super(id, x, y, radius, 0, 0,Double.MAX_VALUE, Particle.getRandomInteractionRatio());
    }

    @Override
    public String stringify(){
        StringBuilder sb = new StringBuilder();
        sb.append(id+c.PARTICLES_QUANTITY());
        sb.append(' ');
        sb.append(x);
        sb.append(' ');
        sb.append(y);
        sb.append(' ');
        sb.append(0);
        sb.append(' ');
        sb.append(0);
        sb.append(' ');
        sb.append(getRadius());
        return sb.toString();
    }
}

public class Wall {

    public double initialX, initialY, finalX, finalY, enx, eny;

    public Wall(double initialX, double initialY, double finalX, double finalY, double enx, double eny) {
        this.initialX = initialX;
        this.initialY = initialY;
        this.finalX = finalX;
        this.finalY = finalY;
        this.enx = enx;
        this.eny = eny;
    }

    private double particleCenterToWall(Particle p) {
        if (initialX == finalX) {
            if (p.y >= initialY && p.y <= finalY)
                return Math.abs(p.x - initialX);
            else
                return p.r;
        } else {
            double dy = initialY - p.y;
            if (p.x > finalX) {
                double dx = finalX - p.x;
                return Math.sqrt(dx*dx + dy*dy);
            } else if (p.x < initialX) {
                double dx = initialX - p.x;
                return Math.sqrt(dx*dx + dy*dy);
            } else {
                return Math.abs(dy);
            }
        }
    }

    public double enx(Particle p) {
        if (initialX == finalX) {
            return enx;
        } else {
            double dx;
            if (p.x > finalX) {
                dx = finalX - p.x;
            } else if (p.x < initialX) {
                dx = initialX - p.x;
            } else {
                return 0;
            }
            return dx/particleCenterToWall(p);
        }
    }

    public double eny(Particle p) {
        if (initialX == finalX) {
            return eny;
        } else {
            return (initialY - p.y) / particleCenterToWall(p);
        }
    }

    public double getOverlap(Particle particle){
        double overlap = particle.r - particleCenterToWall(particle);
        return overlap >= 0 ? overlap : 0;
    }

    public double getNormalRelVel(Particle p) {
        return p.vx * enx(p) + p.vy * eny(p);
    }


}

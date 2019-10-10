package GrainSimulator;

import Constants.Config;
import Model.Particle;
import Model.Vector;
import Model.Wall;

@SuppressWarnings("Duplicates")
public final class GrainSimulatorHelper {
    Config c = Config.getInstance();
    static double normalVersor[];
    static double tangencialVersor[];

    private GrainSimulatorHelper() {
        throw new AssertionError();
    }

    private static Vector getFixForce(Particle p1, Particle p2){
        return getNormalFixForce(p1,p2);
    }
    private static Vector getFixForce(Wall w, Particle p2){
        return getNormalFixForce(w,p2);
    }

    public static Vector getNormalFixForce(Particle p1, Particle p2){
        Vector n = getNormalVersor(p1, p2);
        double kn = Config.getInstance().KN();
        double ga = Config.getInstance().GAMMA();
        double xi = calculateXi(p1, p2);
        Vector relativeVelocity = calculateRelativeVelocity(p1, p2);
        if(xi < 0)
            return Vector.NULL_VECTOR;
        return n.multiplyBy(-kn*xi-ga*relativeVelocity.dot(n));
    }

    public static Vector getNormalFixForce(Wall w, Particle p2){
        Vector n = w.getNormalVersor();
        double kn = Config.getInstance().KN();
        double xi = calculateXi(w, p2);
        double ga = Config.getInstance().GAMMA();
        Vector relativeVelocity = calculateRelativeVelocity(w, p2);
        if(xi < 0)
            return Vector.NULL_VECTOR;
        return n.multiplyBy(-kn*xi-ga*relativeVelocity.dot(n));
    }

    private static Vector getForce(Particle p1, Particle p2) {
        Vector tangencialForce = getTangencialForce(p1,p2);
        Vector normalForce = getNormalForce(p1,p2);

        if(normalForce.norm() > 1.5){
            normalForce.reduce(1.5);
        }
        if(tangencialForce.norm() > 2){
            tangencialForce.reduce(2);
        }

        return tangencialForce.sum(normalForce);
    }

    private static Vector getForce(Wall w, Particle p2) {
        Vector tangencialForce = getTangencialForce(w,p2);
        Vector normalForce = getNormalForce(w,p2);

        double multiplier = getNormalForceWallMultiplier(w,p2);
        if(normalForce.norm() > 1.5){
            normalForce.reduce(1.5);
        }
        if(tangencialForce.norm() > 2){
            tangencialForce.reduce(2);
        }

        return tangencialForce.sum(normalForce.multiplyBy(multiplier));
    }

    public static Vector getTangencialForce(Particle p1, Particle p2) {
        Vector t = getTangencialVersor(p1, p2);
        double kt = Config.getInstance().KT();
        double xi = calculateXi(p1, p2);
        if(xi < 0)
            return Vector.NULL_VECTOR;
//        Vector relativeVelocity = calculateRelativeVelocity(p1, p2);
//        Vector projection = t.getProyection(relativeVelocity);
//        return projection.multiplyBy(-kt * xi);

//        int multiplier = (new Vector(p2.getXSpeed(),p2.getYSpeed()).isAcute(t))? -1:1;

        double tangencialForceMod = - kt * xi * calculateSpecialRelativeVelocity(p1,p2,t);
        return new Vector (t.getX()*tangencialForceMod, t.getY()*tangencialForceMod);
    }

    private static Vector getTangencialForce(Wall w, Particle p2) {
        Vector t = w.getTangencialVersor();
        double kt = Config.getInstance().KT();
        double xi = calculateXi(w, p2);
        if(xi < 0)
            return Vector.NULL_VECTOR;
//        Vector relativeVelocity = calculateRelativeVelocity(w, p2);
//        Vector projection = t.getProyection(relativeVelocity);
//        return projection.multiplyBy(-kt * xi);
        double tangencialForceMod = -kt * xi * calculateSpecialRelativeVelocity(w,p2,t);
        return new Vector(t.getX()*tangencialForceMod, t.getY()*tangencialForceMod);
    }

    private static Vector calculateRelativeVelocity(Particle p1, Particle p2) {
        Vector v1 = new Vector(p1.getXSpeed(),p1.getYSpeed());
        Vector v2 = new Vector(p2.getXSpeed(),p2.getYSpeed());

        return v2.minus(v1);
    }

    private static Vector calculateRelativeVelocity(Wall w, Particle p2) {
        return new Vector(p2.getXSpeed(),p2.getYSpeed());
    }

    private static double calculateSpecialRelativeVelocity(Particle p1, Particle p2, Vector t) {
        Vector v1 = new Vector(p1.getXSpeed(),p1.getYSpeed());
        Vector v2 = new Vector(p2.getXSpeed(),p2.getYSpeed());

        return v2.dot(t) - v1.dot(t);
    }

    private static double calculateSpecialRelativeVelocity(Wall w, Particle p2, Vector t) {
        Vector v2 = new Vector(p2.getXSpeed(),p2.getYSpeed());

        return v2.dot(t);
    }

    private static Vector getNormalForce(Particle p1, Particle p2) {
        Vector n = getNormalVersor(p1, p2);
        double kn = Config.getInstance().KN();
        double xi = calculateXi(p1, p2);
        if(xi < 0)
            return Vector.NULL_VECTOR;
        return n.multiplyBy(-kn*xi);
    }

    private static Vector getNormalForce(Wall w, Particle p2) {
        Vector n = w.getNormalVersor();
        double kn = Config.getInstance().KN();
        double xi = calculateXi(w, p2);
        if(xi < 0)
            return Vector.NULL_VECTOR;
        return n.multiplyBy(-kn*xi);
    }

    public static Vector getNormalVersor(Particle p1, Particle p2) {
        double ex = (p1.getX()-p2.getX())/Math.hypot(p1.getX()-p2.getX(),p1.getY()-p2.getY());
        double ey = (p1.getY()-p2.getY())/Math.hypot(p1.getX()-p2.getX(),p1.getY()-p2.getY());

        return new Vector(ex,ey);
    }

    public static Vector getTangencialVersor(Particle p1, Particle p2) {
        double ex = (p1.getX()-p2.getX())/Math.hypot(p1.getX()-p2.getX(),p1.getY()-p2.getY());
        double ey = (p1.getY()-p2.getY())/Math.hypot(p1.getX()-p2.getX(),p1.getY()-p2.getY());

        return new Vector(-ey,ex);
    }

    private static double calculateXi(Particle p1, Particle p2) {
        double distance = Math.hypot(p1.getX()-p2.getX(),p1.getY()-p2.getY());
        return p1.getRadius() + p2.getRadius() - distance;
    }

    private static double calculateXi(Wall w, Particle p2) {
        return p2.getRadius() - w.getMinimumDistance(p2);
    }

    public static Vector getForceWallExertsOnP(Wall wall, Particle p) {
        return getForce(wall, p);
    }

    private static double getNormalForceWallMultiplier(Wall wall, Particle p) {
        if(wall.isVertical()){
            if(p.getX()<wall.getX()){
                return 1;
            }
            else{
                return -1;
            }
        }
        else{
            if(p.getY()<wall.getY()){
                return 1;
            }
            else{
                return-1;
            }
        }
    }

    public static Vector getForceP1ExertsOnP2(Particle p1, Particle p2) {
        return getForce(p1, p2);
    }

}

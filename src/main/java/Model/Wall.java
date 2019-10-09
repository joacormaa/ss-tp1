package Model;

import Constants.Config;
import GrainSimulator.GrainSimulatorHelper;
import Log.Logger;

public class Wall implements Interactable{
    private boolean isVertical;
    private double x;
    private double y;
    private double length;
    private int id;
    private double width;
    private Vector normalVersor;
    private Vector tangencialVersor;
    private Config c;

    public Wall(boolean isVertical, double x, double y, double length, int id) {
        c = Config.getInstance();
        this.width = c.WALL_WIDTH();
        this.isVertical = isVertical;
        this.x = x;
        this.y = y;
        this.length = length;
        this.id=id;

        calculateVersors();
    }

    private void calculateVersors() {
        this.normalVersor = calculateNormalVersor();
        this.tangencialVersor = normalVersor.ortho();
    }

    private Vector calculateNormalVersor() {
        if(this.isVertical()){
            return new Vector(1,0);
        }
        else{
            return new Vector(0,1);
        }
    }

    public int getId(){
        return id;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public Vector getTangencialVersor(){
        return tangencialVersor;
    }

    public Vector getNormalVersor(){
        return normalVersor;
    }

    public String stringify(int idBase) {
        StringBuilder sb = new StringBuilder();
        int pPerWall = Config.getInstance().PARTICLES_PER_WALL();
        double amount=length/pPerWall;
        for(int i=0; i<pPerWall; i++){

            sb.append(idBase+i);
            sb.append(' ');
            double x,y;
            if(isVertical){
                y=this.y+i*amount;
                x=this.x;
            }
            else{
                x=this.x+i*amount;
                y=this.y;
            }
            sb.append(x);
            sb.append(' ');
            sb.append(y);
            sb.append(' ');
            sb.append(0);
            sb.append(' ');
            sb.append(0);
            sb.append(' ');
            sb.append(0);
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public double[] getXYIncidentalForce(Particle p) {
        Vector v = GrainSimulatorHelper.getForceWallExertsOnP(this, p);
        return new double[]{v.getX(),v.getY()};
    }

    public double getMinimumDistance(Particle p) {
        if(isVertical){
            double minY = this.y;
            double maxY = this.y + this.length;

            if(p.getY()<=maxY && p.getY()>=minY){
                return Math.abs(p.getX()-this.getX());
            }

            double dist1 = Math.hypot(p.getX()-this.getX(),p.getY()-this.getY());
            double dist2 = Math.hypot(p.getX()-this.getX(),p.getY()-this.getY() + this.length);

            return Math.min(dist1,dist2);
        }
        else{
            double minX = this.x;
            double maxX = this.x + this.length;

            if(p.getX()<=maxX && p.getX()>=minX){
                return Math.abs(p.getY()-this.getY());
            }

            double dist1 = Math.hypot(p.getY()-this.getY(),p.getX()-this.getX());
            double dist2 = Math.hypot(p.getY()-this.getY(),p.getX()-this.getX() + this.length);

            return Math.min(dist1,dist2);
        }
    }
}

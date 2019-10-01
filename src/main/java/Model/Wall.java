package Model;

import Constants.Config;

public class Wall implements Interactable{
    private Vector vector;
    private int id;
    private double width;
    private Config c;

    public Wall(boolean isVertical, double x, double y, double length, int id) {
        c = Config.getInstance();

        Vector vec;
        if(isVertical){
            vec = new Vector(x,y,x+length,y);
        }else{
            vec = new Vector(x,y,x,y+length);
        }
        this.width = c.WALL_WIDTH();
        this.vector=vec;
        this.id=id;
    }

    public Wall(Vector vector, int id){
        c = Config.getInstance();
        this.width = c.WALL_WIDTH();
        this.vector=vector;
        this.id=id;

    }

    public int getId(){
        return id;
    }

    public boolean isVertical() {
        return Math.abs(vector.getAngle())==Math.PI/2;
    }

    public boolean isHorizontal() {
        return vector.getAngle()==0 || vector.getAngle()==Math.PI;
    }

    public double getX() {
        return vector.getX1();
    }

    public double getY() {
        return vector.getY1();
    }

    public double getLength() {
        return vector.getLength();
    }

    public double getWidth() {
        return width;
    }

    public String stringify(int idBase) {
        StringBuilder sb = new StringBuilder();
        int pPerWall = Config.getInstance().PARTICLES_PER_WALL();
        double xInc = vector.getXLength()/pPerWall;
        double yInc = vector.getYLength()/pPerWall;
        for(int i=0; i<pPerWall; i++){

            sb.append(idBase+i);
            sb.append(' ');

            double x = vector.getX1() + i *xInc;
            double y = vector.getY1() + i *yInc;


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
    public double getXIncidentalForce(Particle p) {
        if(!this.isVertical) return 0;
        double fn = getFN(p);
        if(this.x>p.x){
            fn = -fn;
        }
        return fn;
    }

    @Override
    public double getYIncidentalForce(Particle p) {
        if(this.isVertical) return 0;
        double fn = getFN(p);
        if(this.y>p.y){
            fn = -fn;
        }
        return fn;
    }

    //https://math.stackexchange.com/questions/2248617/shortest-distance-between-a-point-and-a-line-segment
    public double getMinimumDistance(Particle p) {
        Vector v = vector;
        double t = ((v.x1-p.x)*(v.x2-v.x1)+(v.y1-p.y)*(v.y2-v.y1))/((v.getXLength()*v.getXLength())+(v.getYLength()*v.getYLength()));

        if(0<=t && t<=1){
            return Math.abs(v.getXLength()*(v.y1-p.y)-v.getYLength()*(v.x1-p.x))/ v.getLength();
        }

        double distance1 = Math.hypot(v.x1-p.x,v.y1-p.y);
        double distance2 = Math.hypot(v.x2-p.x,v.y2-p.y);

        return Math.min(distance1,distance2);

    }
}

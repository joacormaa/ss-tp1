package Model;

import Constants.Config;

public class Wall implements Interactable{
    private double x;
    private double y;
    private Vector vector;
    private int id;
    private double width;
    private Config c;

    public Wall(boolean isVertical, double x, double y, double length, int id) {
        c = Config.getInstance();
        this.x=x;
        this.y=y;
        Vector vec;
        if(isVertical){
            vec = new Vector(length,0);
        }else{
            vec = new Vector(0,length);
        }
        this.width = c.WALL_WIDTH();
        this.vector=vec;
        this.id=id;
    }

    public Wall(double x, double y,Vector vector, int id){
        c = Config.getInstance();
        this.width = c.WALL_WIDTH();
        this.x=x;
        this.y=y;
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
        return x;
    }

    public double getY() {
        return y;
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
        double xInc = vector.x/pPerWall;
        double yInc = vector.y/pPerWall;
        for(int i=0; i<pPerWall; i++){

            sb.append(idBase+i);
            sb.append(' ');

            double x = this.x + i *xInc;
            double y = this.y + i *yInc;


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
        double t = ((x-p.x)*(v.x-v.x)+(y-p.y)*(v.y-y))/(v.getLength()*v.getLength());

        if(0<=t && t<=1){
            return Math.abs(v.x*(y-p.y)-v.y*(x-p.x))/ v.getLength();
        }

        double distance1 = Math.hypot(x-p.x,y-p.y);
        double distance2 = Math.hypot(x-p.x,y-p.y);

        return Math.min(distance1,distance2);

    }
}

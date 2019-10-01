package Model;

public class Vector {
    protected double x1;
    protected double y1;
    protected double x2;
    protected double y2;
    protected double length;
    protected double angle;

    public static Vector X_VERSOR = new Vector(0,0,1,0);
    public static Vector Y_VERSOR = new Vector(0,0,0,1);


    public Vector(double x1, double y1, double x2, double y2){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;

        this.length = Math.hypot(x2-x1,y2-y1);
        this.angle = Vector.getAngle(x1,y1,x2,y2);
    }

    private static double getAngle(double x1, double y1, double x2, double y2) {
        double finx = x2-x1;
        double finy = y2-y1;
        if(finx==0){
            double halfpi = Math.PI/2;
            return (finy>0)?halfpi:-halfpi;
        }
        return Math.tan(finy/finx);
    }

    public double getLength(){
        return length;
    }

    public double getAngle(){
        return angle;
    }

    public Vector getProyection(Vector vec){
        double multiplier = this.dot(vec)/(norm()*norm());

        return new Vector(x1*multiplier,y1*multiplier,x2*multiplier,y2*multiplier);
    }

    public double getX1(){
        return x1;
    }

    public double getXLength(){
        return x2-x1;
    }

    public double getYLength(){
        return y2-y1;
    }

    public double getY1(){
        return y1;
    }

    public double dot(Vector v){
        return getXLength()*v.getXLength() + getYLength() * v.getYLength();
    }

    public double norm(){
        return Math.hypot(getXLength(),getYLength());
    }

}

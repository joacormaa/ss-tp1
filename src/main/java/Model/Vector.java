package Model;

public class Vector {
    protected double x;
    protected double y;
    protected double length;
    protected double angle;

    public static Vector X_VERSOR = new Vector(1,0);
    public static Vector Y_VERSOR = new Vector(0,1);


    public Vector(double x, double y){
        this.x=x;
        this.y=y;

        this.length = Math.hypot(x,y);
        this.angle = Vector.getAngle(x,y);
    }

    private static double getAngle(double x,double y) {
        if(x==0){
            double halfpi = Math.PI/2;
            return (y>0)?halfpi:-halfpi;
        }
        return Math.tan(y/x);
    }

    public double getLength(){
        return length;
    }

    public double getAngle(){
        return angle;
    }

    public Vector getProyection(Vector vec){
        double multiplier = this.dot(vec)/(norm()*norm());

        return new Vector(x*multiplier,y*multiplier);
    }

    public double getX() {
        return x;
    }

    public double getY(){
        return y;
    }

    public double dot(Vector v){
        return x*v.getX() + y * v.getY();
    }

    public double norm(){
        return Math.hypot(x,y);
    }

    public Vector ortho(){
        return new Vector(-y,x);
    }

}

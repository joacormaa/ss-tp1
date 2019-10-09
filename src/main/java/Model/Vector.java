package Model;

public class Vector {
    protected double x;
    protected double y;
    protected double norm;
    protected double angle;

    public static Vector X_VERSOR = new Vector(1,0);
    public static Vector Y_VERSOR = new Vector(0,1);
    public static Vector NULL_VECTOR = new Vector(0,0);


    public Vector(double x, double y){
        this.x=x;
        this.y=y;

        this.norm = Math.hypot(x,y);
        this.angle = Vector.getAngle(x,y);
    }

    private static double getAngle(double x,double y) {
        return Math.atan2(y,x);
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

    public Vector multiplyBy(double scalar){
        return new Vector(this.x*scalar,this.y*scalar);
    }

    public double dot(Vector v){
        return x*v.getX() + y * v.getY();
    }

    public double norm(){
        return norm;
    }

    public Vector ortho(){
        return new Vector(-y,x);
    }

    public Vector minus(Vector v){
        return new Vector(this.x-v.x,this.y-v.y);
    }

    public Vector sum(Vector v){
        return new Vector(this.x+v.x,this.y+v.y);
    }

    public Vector versor(){
        double norm = norm();
        return new Vector(x/norm,y/norm);
    }

}

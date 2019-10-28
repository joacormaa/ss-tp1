package Model;

public class Person implements Interactable{

    private Vector position;
    private Vector velocity;
    private double r_min;
    private double r_max;
    private double current_r;
    private Goal goal;

    public Person(Vector position, Vector velocity, double r_min, double r_max, Goal goal) {
        this.position = position;
        this.velocity = velocity;
        this.r_min = r_min;
        this.r_max = r_max;
        this.current_r = r_max;
        this.goal = goal;
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    @Override
    public double getRadius() {
        return current_r;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public double getR_min() {
        return r_min;
    }

    public double getR_max() {
        return r_max;
    }

    public String stringify() {
        return position.x+" "+position.y+" "+current_r+" 0\n";
    }
}

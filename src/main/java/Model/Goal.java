package Model;

public class Goal {
    private Vector position;
    private Goal nextGoal;
    private double radius;

    public Goal(Vector position, double radius, Goal nextGoal){
        this.position=position;
        this.radius=radius;
        this.nextGoal=nextGoal;
    }

    public String stringify() {
        return position.x+" "+position.y+" "+radius+" 2\n";
    }

    public Goal nextGoal() {
        return nextGoal;
    }

    public Vector getPosition() {
        return position;
    }

    public double getRadius() {
        return radius;
    }
}

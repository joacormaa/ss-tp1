package Model;

public class Person implements Interactable{

    private Vector position;
    private Vector velocity;
    private double current_r;
    private Goal goal;

    public Person(Vector position, Vector velocity, double radius, Goal goal) {
        this.position = position;
        this.velocity = velocity;
        this.current_r = radius;
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

    public String stringify() {
        return position.x+" "+position.y+" "+current_r+" 0 0 255\n";
    }

    public Goal getGoal() {
        return goal;
    }

    public boolean achievedGoal() {
        if(goal==null) return false;
        return position.minus(goal.getPosition()).norm<current_r+goal.getRadius();

    }

    public Vector getDesiredDirection() {
        if(goal==null) return Vector.NULL_VECTOR;
        return goal.getPosition().minus(position).versor();
    }
}

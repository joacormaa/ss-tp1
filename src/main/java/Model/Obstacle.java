package Model;

import Constants.Config;

public class Obstacle implements Interactable {
    private double radius;
    private Vector position;
    private Vector velocity;

    public Obstacle(double radius, Vector position, Vector velocity) {
        this.radius = radius;
        this.position=position;
        this.velocity = velocity;
    }

    public boolean hasCollided(Person person){
        double distance = position.minus(person.getPosition()).norm;
        return distance<radius+ Config.getInstance().PERSON_MAX_R();
    }

    public Obstacle getNext(double delta){
        Config c = Config.getInstance();
        Vector newPos = position.sum(velocity.multiplyBy(delta));
        if(newPos.getY()>c.VERTICAL_WALL_LENGTH() || newPos.getY()<0){
            Vector newVel = velocity.multiplyBy(-1);
            newPos = position.sum(newVel.multiplyBy(delta));
            return new Obstacle(radius, newPos,newVel);
        }
        return new Obstacle(radius,newPos,velocity);
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public String stringify() {
        return position.x+" "+position.y+" "+radius+" 255 0 0\n";
    }
}

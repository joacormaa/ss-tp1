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
        Vector newPos = position.sum(velocity.multiplyBy(delta));
        return new Obstacle(radius,newPos,velocity); //todo: agregar la logica de que vuelva para atras
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
        return position.x+" "+position.y+" "+radius+" 1\n";
    }
}

package Model;

public interface Interactable {
    default boolean isAdjacentTo(Interactable q){
        Vector dist = this.getPosition().minus(q.getPosition());
        return dist.norm<this.getRadius()+q.getRadius();
    }

    Vector getPosition();
    Vector getVelocity();
    double getRadius();
}

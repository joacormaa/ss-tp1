package CollisionSimulator;

import Model.Particle;

public class Collision<T> {

    private Particle p;
    private T q;
    private Double collisionTime;

    public Collision(Particle p, T q, Double collisionTime)
    {
        this.p = p;
        this.q = q;
        this.collisionTime=collisionTime;
    }

    public Particle getP() {
        return p;
    }

    public void setP(Particle p) {
        this.p = p;
    }

    public T getQ() {
        return q;
    }

    public void setQ(T q) {
        this.q = q;
    }

    public Double getCollisionTime() {
        return collisionTime;
    }

    public void setCollisionTime(Double collisionTime) {
        this.collisionTime = collisionTime;
    }
}

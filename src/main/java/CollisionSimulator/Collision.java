package CollisionSimulator;

public class Collision{

    private int pId;
    private int qId;
    private Double collisionTime;
    private Type type;

    public Collision(int pId, int qId, Double collisionTime, Type type)
    {
        this.pId = pId;
        this.qId = qId;
        this.collisionTime=collisionTime;
        this.type=type;
    }

    public int getPid() {
        return pId;
    }


    public int getQId() {
        return qId;
    }


    public Double getCollisionTime() {
        return collisionTime;
    }

    public void setCollisionTime(Double collisionTime) {
        this.collisionTime = collisionTime;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        ParticleParticle,
        ParticleWall,
        ParticleStaticParticle;
    }
}

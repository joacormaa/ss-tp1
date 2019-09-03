package CollisionSimulator;

import Constants.Config;
import Model.Particle;
import Model.StaticParticle;
import Model.System;
import Model.Wall;

import java.util.Arrays;
import java.util.Collection;

public class CollisionManager {

    private Collision<Wall>[][] collisionsWithWalls;
    private Collision<StaticParticle>[][] collisionsWithStaticParticles;
    private Collision<Particle>[][] collisionsWithParticles;
    private MyBetterPriorityQueue<Collision<?>> pq;
    private System system;
    private Config c;

    public CollisionManager(System system){
        this.c = Config.getInstance();
        int particleQ =c.PARTICLES_QUANTITY();
        this.system=system;
        collisionsWithWalls = new Collision[particleQ][6]; // 6 paredes
        collisionsWithStaticParticles = new Collision[particleQ][2]; // 2 particulas estaticas
        collisionsWithParticles = new Collision[particleQ][particleQ];
        initializeCollisionTimes();
    }

    public Collision<?> getNextCollision() {
        for (Collision<Particle>[] collisionsWithParticle : collisionsWithParticles) {
            pq.addAll(Arrays.asList(collisionsWithParticle));
        }
        for (Collision<Wall>[] collisionsWithWall : collisionsWithWalls) {
            pq.addAll(Arrays.asList(collisionsWithWall));
        }
        for (Collision<StaticParticle>[] collisionsWithStaticParticle : collisionsWithStaticParticles) {
            pq.addAll(Arrays.asList(collisionsWithStaticParticle));
        }

        return pq.peek();
    }

    public void updateCollisions(System nextSystem, Collision<?> collision){
        Particle p = collision.getP();
        updateCollisionsForParticle(p,nextSystem);
        if(collision.getQ() instanceof Particle && !(collision.getQ() instanceof StaticParticle)){
            Particle q = (Particle) collision.getQ();
            updateCollisionsForParticle(q, nextSystem);
        }
        pq.heapify();
    }

    private void updateCollisionsForParticle(Particle p, System system) {
        int pId =p.getId();
        for(Particle q : system.getParticles()){
            int qId = q.getId();
            double collisionTime = getCollisionTime(p,q) +system.getTime(); //todo: check null
            if(q.getId()>p.getId()){
                collisionsWithParticles[qId][pId].setCollisionTime(collisionTime);
            }else if(qId!=pId){
                collisionsWithParticles[pId][qId].setCollisionTime(collisionTime);
            }
        }
        for(Wall q : system.getWalls()){
            int qId = q.getId();
            double collisionTime = getCollisionTime(p,q) +system.getTime();//todo: check null
            collisionsWithWalls[qId][pId].setCollisionTime(collisionTime);
        }
        for(StaticParticle q : system.getStaticParticles()){
            int qId = q.getId();
            double collisionTime = getCollisionTime(p,q) +system.getTime();//todo: check null
            collisionsWithStaticParticles[qId][pId].setCollisionTime(collisionTime);
        }
    }

    private void initializeCollisionTimes() {
        Collection<Particle> particles = system.getParticles();
        for(Particle p : particles){
            for(StaticParticle q : system.getStaticParticles()){
                int pId = p.getId();
                int qId = q.getId();

                Double collisionTime = getCollisionTime(p,q);
                collisionsWithStaticParticles[qId][pId] = new Collision<>(p,q,collisionTime);
            }
            for(Wall q : system.getWalls()){
                int pId = p.getId();
                int qId = q.getId();

                Double collisionTime = getCollisionTime(p,q);
                collisionsWithWalls[qId][pId] = new Collision<>(p,q,collisionTime);
            }
            for(Particle q : system.getParticles()){
                if(q.getId()>p.getId()){ //todo:improve... mucho chequeo al pedo.
                    int pId = p.getId();
                    int qId = q.getId();

                    Double collisionTime = getCollisionTime(p,q);
                    collisionsWithParticles[qId][pId] = new Collision<>(p,q,collisionTime);

                }
            }
        }
    }

    private Double getCollisionTime(Particle p, StaticParticle sp) {
        return null; //todo: missing
    }


    private Double getCollisionTime(Particle p, Wall w) {
        Double particlePosition = w.isVertical() ? p.getX():p.getY();
        Double particleSpeed = w.isVertical() ? p.getXSpeed():p.getYSpeed();
        if((particlePosition < w.getPosition() && particleSpeed < 0) || (particlePosition > w.getPosition() && particleSpeed > 0))
            return null;

        Integer coefficient = particlePosition < w.getPosition() ? -1 : 1;
        return (w.getPosition() + coefficient * p.getRadius() - particlePosition)/particleSpeed;
    }

    private Double getCollisionTime(Particle p1, Particle p2) {
        double[] deltaR = new double[2];
        double[] deltaV = new double[2];
        double squareDeltaR;
        double squareDeltaV;
        double productDeltaVDeltaR;
        double d;
        double sigma;

        sigma = p1.getRadius() + p2.getRadius();

        deltaR[0] = p2.getX() - p1.getX();
        deltaR[1] = p2.getY() - p1.getY();

        deltaV[0] = p2.getXSpeed() - p1.getXSpeed();
        deltaV[1] = p2.getYSpeed() - p1.getYSpeed();

        squareDeltaR = Math.pow(deltaR[0], 2) + Math.pow(deltaR[1], 2);
        squareDeltaV = Math.pow(deltaV[0], 2) + Math.pow(deltaV[1], 2);
        productDeltaVDeltaR = deltaR[0]*deltaV[0] + deltaR[1]*deltaV[1];

        d = Math.pow(productDeltaVDeltaR, 2) - squareDeltaV*(squareDeltaR-Math.pow(sigma, 2));


        if(productDeltaVDeltaR >= 0 || d < 0) return null;
        return (productDeltaVDeltaR + Math.sqrt(d))/(-squareDeltaV);
    }
}

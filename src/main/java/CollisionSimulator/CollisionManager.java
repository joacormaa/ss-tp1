package CollisionSimulator;

import Constants.Config;
import Model.Particle;
import Model.StaticParticle;
import Model.System;
import Model.Wall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

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
        collisionsWithWalls = new Collision[6][particleQ]; // 6 paredes
        collisionsWithStaticParticles = new Collision[2][particleQ]; // 2 particulas estaticas
        collisionsWithParticles = new Collision[particleQ][particleQ];
        initializeCollisionTimes();
        this.pq = new MyBetterPriorityQueue<>((collision, t1) -> {
            if(collision==t1) return 0;
            if(collision==null || collision.getCollisionTime()==null) return 1;
            if(t1==null || t1.getCollisionTime()==null) return -1;
            if(collision.collisionTime<t1.collisionTime)
                return -1;
            return 1;
        });
    }

    public Collision<?> getNextCollision() {
        for (int i=0; i<collisionsWithParticles.length;i++) {
            Collision<Particle>[] collisionsWithParticle = collisionsWithParticles[i];
            pq.addAll(Arrays.asList(collisionsWithParticle).subList(i+1,collisionsWithParticle.length));
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
        Collection<Particle> updatedParticles = getUpdatedParticles(nextSystem,collision);
        for(Particle p : updatedParticles){
            updateCollisionsForParticle(p, nextSystem);
        }
        pq.heapify();
    }

    private Collection<Particle> getUpdatedParticles(System nextSystem, Collision<?> collision) {
        List<Particle> updatedParticles = new ArrayList<>();
        for(Particle p : nextSystem.getParticles()){
            if(p.equals(collision.getQ())|| p.equals(collision.getP()))
                updatedParticles.add(p);
        }
        return updatedParticles;
    }

    private void updateCollisionsForParticle(Particle p, System system) {
        int pId =p.getId();
        for(Particle q : system.getParticles()){
            int qId = q.getId();
            Double collisionTime = getCollisionTime(p,q);
            Double newCollisionTime = (collisionTime!=null)? collisionTime +system.getTime(): null;
            if(q.getId()>p.getId()){
                collisionsWithParticles[pId][qId].setCollisionTime(newCollisionTime);
            }else if(qId<pId){
                collisionsWithParticles[qId][pId].setCollisionTime(newCollisionTime);
            }
        }
        for(Wall q : system.getWalls()){
            int qId = q.getId();
            Double collisionTime = getCollisionTime(p,q);
            Double newCollisionTime = (collisionTime!=null)? collisionTime +system.getTime(): null;
            collisionsWithWalls[qId][pId].setCollisionTime(newCollisionTime);
        }
        for(StaticParticle q : system.getStaticParticles()){
            int qId = q.getId();
            Double collisionTime = getCollisionTime(p,q);
            Double newCollisionTime = (collisionTime!=null)? collisionTime +system.getTime(): null;
            collisionsWithStaticParticles[qId][pId].setCollisionTime(newCollisionTime);
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
                if(q.getId()>p.getId()){ //triangular superior
                    int pId = p.getId();
                    int qId = q.getId();

                    Double collisionTime = getCollisionTime(p,q);
                    collisionsWithParticles[pId][qId] = new Collision<>(p,q,collisionTime); //triangular superior

                }
            }
        }
    }


    private Double getCollisionTime(Particle p, Wall w) {
        if(w.isVertical()){
            double pX = p.getX();
            double wX = w.getX();
            double vX = p.getXSpeed();
            if((pX < wX && vX < 0) || (pX > wX && vX > 0))
                return null;

            double offset =0;
            if(pX<wX){
                offset = -p.getRadius();
            }
            else{
                offset = p.getRadius() + w.getWidth();
            }
            double time = (wX + offset  - pX)/vX;

            double finalYPos = p.getYSpeed()*time+p.getY();

            if(finalYPos<w.getY() || finalYPos>(w.getY()+w.getLength())){
                return null;
            }
            return time;
        }
        else {
            double pY = p.getY();
            double wY = w.getY();
            double vY = p.getYSpeed();
            if((pY < wY && vY < 0) || (pY > wY && vY > 0))
                return null;

            double offset =0;
            if(pY<wY){
                offset = -p.getRadius();
            }
            else{
                offset = p.getRadius() + w.getWidth();
            }
            double time = (wY + offset  - pY)/vY;

            double finalXPos = p.getXSpeed()*time+p.getX();

            if(finalXPos<w.getX() || finalXPos>(w.getX()+w.getLength())){
                return null;
            }
            return time;
        }
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

    private Particle getCollisionResult(Particle p, Wall w){
        if(w.isVertical()){
            return new Particle(p.getId(), p.getX(), p.getY(), p.getRadius(), p.getSpeed(), 180-p.getAngle(), p.getMass());
        } else {
            return new Particle(p.getId(), p.getX(), p.getY(), p.getRadius(), p.getSpeed(), -p.getAngle(), p.getMass());
        }
    }

    private Collection<Particle> getCollisionResult(Particle p1, Particle p2){
        double[] deltaR = new double[2];
        deltaR[0] = p2.getX() - p1.getX();
        deltaR[1] = p2.getY() - p1.getY();

        double[] deltaV = new double[2];
        deltaV[0] = p2.getXSpeed() - p1.getXSpeed();
        deltaV[1] = p2.getYSpeed() - p1.getYSpeed();

        double productDeltaVDeltaR;
        productDeltaVDeltaR = deltaR[0]*deltaV[0] + deltaR[1]*deltaV[1];

        double sigma;
        sigma = p1.getRadius() + p2.getRadius();

        double j = 2*p1.getMass()*p2.getMass()*productDeltaVDeltaR/(sigma*(p1.getMass() + p2.getMass()));
        double jx = (j*deltaR[0])/sigma;
        double jy = (j*deltaR[1])/sigma;

        double vx1 = p1.getXSpeed() + jx/p1.getMass();
        double vy1 = p1.getYSpeed() + jy/p1.getMass();

        double vx2 = p2.getXSpeed() - jx/p2.getMass();
        double vy2 = p2.getYSpeed() - jy/p2.getMass();

        Particle p1new = new Particle(p1.getId(), p1.getX(), p1.getY(), p1.getRadius(), Particle.getSpeed(vx1, vy1), Particle.getAngle(vx1, vy1), p1.getMass());
        Particle p2new = new Particle(p2.getId(), p2.getX(), p2.getY(), p2.getRadius(), Particle.getSpeed(vx2, vy2), Particle.getAngle(vx2, vy2), p2.getMass());
        Collection<Particle> ret = new LinkedList<>();
        ret.add(p1new);
        ret.add(p2new);
        return ret;
    }

    private Particle getCollisionResult(Particle p, StaticParticle s){
        double[] deltaR = new double[2];
        deltaR[0] = s.getX() - p.getX();
        deltaR[1] = s.getY() - p.getY();

        double alpha = Math.acos(deltaR[0]/Math.sqrt(Math.pow(deltaR[0], 2) + Math.pow(deltaR[1], 2)));
        double cn = 1;
        double ct = 1;

        double[][] operator = new double[2][2];
        operator[0][0] = -cn*Math.pow(Math.cos(alpha), 2) + ct*Math.pow(Math.sin(alpha), 2);
        operator[0][1] = -(cn+ct)*Math.sin(alpha)*Math.cos(alpha);
        operator[1][0] = -(cn+ct)*Math.sin(alpha)*Math.cos(alpha);
        operator[0][0] = -cn*Math.pow(Math.sin(alpha), 2) + ct*Math.pow(Math.cos(alpha), 2);

        double lastVX = p.getXSpeed();
        double lastVY = p.getYSpeed();
        double newVX = operator[0][0] * lastVX + operator[0][1] * lastVY;
        double newVY = operator[1][0] * lastVX + operator[1][1] * lastVY;
        return new Particle(p.getId(), p.getX(), p.getY(), p.getRadius(), Particle.getSpeed(newVX, newVY), Particle.getAngle(newVX, newVY), p.getMass());
    }
}

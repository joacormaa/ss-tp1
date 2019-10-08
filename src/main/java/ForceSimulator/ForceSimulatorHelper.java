package ForceSimulator;

import Constants.Config;
import Model.Interactable;
import Model.Particle;
import OscillationSimulator.OscillationManager;

import java.util.Collection;

public class ForceSimulatorHelper {

    private OscillationManager om;

    public ForceSimulatorHelper(){
        om =  new OscillationManager(false);
    }

    public Particle getNextParticle(Particle lastP, Particle prevP, Collection<Interactable> neighbours, double delta, Collection<Particle> particles) {

        if(lastP.getY()<0.005){
            Particle newP = getRandomParticleFromTop(lastP, particles);
            return newP;
        }

        Acceleration acc = getAcceleration(lastP, neighbours);

        OscillationManager.PositionNVel xPosnVel = om.verlet(lastP.getX(),prevP.getX(),acc.xacc, delta);
        OscillationManager.PositionNVel yPosnVel = om.verlet(lastP.getY(),prevP.getY(),acc.yacc, delta);

        double newX = xPosnVel.getPosition();
        double newY = yPosnVel.getPosition();

        double newXSpeed = xPosnVel.getVel();
        double newYSpeed = yPosnVel.getVel();

        double newSpeed = Particle.getSpeed(newXSpeed,newYSpeed);
        double newAngle = Particle.getAngle(newXSpeed,newYSpeed);


        return new Particle(lastP.getId(),newX,newY,lastP.getRadius(),newSpeed,newAngle,lastP.getMass(), 0);
    }

    private Particle getRandomParticleFromTop(Particle lastP, Collection<Particle> particles) {
        Particle newP;
        do {
            double x = Math.random() * Config.getInstance().HORIZONTAL_WALL_LENGTH(); //todo check collision
            newP =  new Particle(lastP.getId(),x,Config.getInstance().VERTICAL_WALL_LENGTH() * 0.95,lastP.getRadius(),0,0,
                    lastP.getMass(),0);

        } while (thereIsCollision(newP, particles));
        return newP;
    }

    private boolean thereIsCollision(Particle p1, Collection<Particle> particles){
        for(Particle p2 : particles) {
            if(Math.hypot(p1.getX() - p2.getX(), p1.getY() - p2.getY()) <= p1.getRadius() + p2.getRadius()) return true;
        }
        return false;
    }

    private Acceleration getAcceleration(Particle lastP, Collection<Interactable> neighbours){
        double sumFx=0, sumFy=0;
        for(Interactable neighbour : neighbours){
            double[] xyforce = neighbour.getXYIncidentalForce(lastP);
            sumFx  += xyforce[0];
            sumFy += xyforce[1];
        }
        double xAcc = sumFx/lastP.getMass();
        double yAcc = sumFy/lastP.getMass();

        //Agrego gravedad
        yAcc -= 9.8;

        return new Acceleration(xAcc,yAcc);
    }

    private class Acceleration{
        double xacc;
        double yacc;

        Acceleration(double xacc, double yacc){
            this.xacc=xacc;
            this.yacc=yacc;
        }
    }

    public Particle getInitialPreviousParticle(Particle lastP, Collection<Interactable> neighbours, double delta){
        //Acceleration acc = getAcceleration(lastP,neighbours);
        Acceleration acc = new Acceleration(0,0);

        double prevY = lastP.getY() - delta*lastP.getYSpeed() - delta*delta*acc.yacc/2;
        double prevYSpeed = lastP.getYSpeed() - delta*acc.yacc;

        double prevX = lastP.getX() - delta*lastP.getXSpeed() - delta*delta*acc.xacc/2;
        double prevXspeed = lastP.getXSpeed() - delta*acc.xacc;

        double prevSpeed = Math.hypot(prevXspeed,prevYSpeed);
        double prevAngle = Math.atan2(prevYSpeed,prevXspeed);

        return new Particle(lastP.getId(),prevX,prevY,lastP.getRadius(),prevSpeed,prevAngle,lastP.getMass(), 0);
    }
}

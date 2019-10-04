package ForceSimulator;

import Model.Interactable;
import Model.Particle;
import OscillationSimulator.OscillationManager;

import java.util.Collection;
import java.util.Set;

public class ForceSimulatorHelper {

    private OscillationManager om;

    public ForceSimulatorHelper(){
        om =  new OscillationManager(false);
    }

    public Particle getNextParticle(Particle lastP, Particle prevP, Collection<Interactable> neighbours, double delta) {

        Acceleration acc = getAcceleration(lastP, neighbours);

        OscillationManager.PositionNVel xPosnVel = om.verlet(lastP.getX(),prevP.getX(),acc.xacc, delta);
        OscillationManager.PositionNVel yPosnVel = om.verlet(lastP.getY(),prevP.getY(),acc.yacc, delta);

        double newX = xPosnVel.getPosition();
        double newY = yPosnVel.getPosition();

        double newXSpeed = xPosnVel.getVel();
        double newYSpeed = yPosnVel.getVel();

        double newSpeed = Particle.getSpeed(newXSpeed,newYSpeed);
        double newAngle = Particle.getAngle(newXSpeed,newYSpeed);

        return new Particle(prevP.getId(),newX,newY,prevP.getRadius(),newSpeed,newAngle,prevP.getMass(), Particle.getRandomInteractionRatio());
    }

    private Acceleration getAcceleration(Particle lastP, Collection<Interactable> neighbours){
        double sumFx=0, sumFy=0;
        for(Interactable neighbour : neighbours){
            sumFx += neighbour.getXIncidentalForce(lastP);
            sumFy += neighbour.getYIncidentalForce(lastP);
        }
        double xAcc = sumFx/lastP.getMass();
        double yAcc = sumFy/lastP.getMass();

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
        Acceleration acc = getAcceleration(lastP,neighbours);

        double prevY = lastP.getY() - delta*lastP.getYSpeed() - delta*delta*acc.yacc/2;
        double prevYSpeed = lastP.getYSpeed() - delta*acc.yacc;

        double prevX = lastP.getX() - delta*lastP.getXSpeed() - delta*delta*acc.xacc/2;
        double prevXspeed = lastP.getXSpeed() - delta*acc.xacc;

        double prevSpeed = Math.hypot(prevXspeed,prevYSpeed);
        double prevAngle = Math.atan2(prevYSpeed,prevXspeed);

        return new Particle(lastP.getId(),prevX,prevY,lastP.getRadius(),prevSpeed,prevAngle,lastP.getMass(), Particle.getRandomInteractionRatio());
    }
}

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

    public Particle getNextParticle(Particle lastP, Particle prevP, Collection<Interactable> neighbours, double delta) {

        Config c = Config.getInstance();

        if(lastP.getY()< c.OFFSET()){
            return null; //salio del sistema.
        }

        Acceleration acc = getAcceleration(lastP, neighbours);

        OscillationManager.PositionNVel xPosnVel = om.verlet(lastP.getX(),prevP.getX(),acc.xacc, delta);
        OscillationManager.PositionNVel yPosnVel = om.verlet(lastP.getY(),prevP.getY(),acc.yacc, delta);

        double newX = xPosnVel.getPosition();
        double newY = yPosnVel.getPosition();

        double newXSpeed = xPosnVel.getVel();
        double newYSpeed = yPosnVel.getVel();

        double newSpeed = Particle.getSpeed(newXSpeed,newYSpeed);
        if(newSpeed > 1){
            newSpeed = 1;
        }
        double newAngle = Particle.getAngle(newXSpeed,newYSpeed);


        return new Particle(lastP.getId(),newX,newY,lastP.getRadius(),newSpeed,newAngle,lastP.getMass(), 0);
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
        if(lastP.getYSpeed()>-0.5){
            yAcc -= 9.8;
        }

        return new Acceleration(xAcc,yAcc);
    }

    private class Acceleration{
        double xacc;
        double yacc;

        Acceleration(double xacc, double yacc){
            double norm = Math.hypot(xacc, yacc);
            if(norm > 30){
                this.xacc = xacc/norm * 30;
                this.yacc = yacc/norm * 30;
            } else {
                this.xacc=xacc;
                this.yacc=yacc;
            }
//            this.xacc=xacc;
//            this.yacc=yacc;
        }
    }

    public Particle getInitialPreviousParticle(Particle lastP, Collection<Interactable> neighbours, double delta){
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

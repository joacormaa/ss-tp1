package OscillationSimulator;

import Constants.Config;
import Log.Logger;
import Model.Particle;
import Model.System;

public class OscillationManager {
    private System lastSystem;
    private Particle previousParticle;
    private OscillationPrinter op;
    private boolean htp;
    private  double deltaT;
    private double a;
    private double gama;
    private double k;
    private Config.NuMethod nM;

    public OscillationManager(boolean hasToPrint){
        Config c = Config.getInstance();
        lastSystem = OscillationCreator.createInitialOscillationSystem();
        op = new OscillationPrinter();
        htp = hasToPrint;
        deltaT = c.SIMULATION_DELTA_TIME();
        a = c.OSCILLATOR_A();
        gama = c.OSCILLATOR_G();
        k = c.OSCILLATOR_K();
        nM = c.NUMERIC_METHOD();

        Particle oPart = lastSystem.getOscillationParticle();
        previousParticle = OscillationCreator.getInitialPreviousParticle(deltaT,force(oPart.getY(),oPart.getSpeed()));
    }

    //ToDo: avanzar el sistema un delta t
    public boolean stepForward() {
        Particle lastParticle = lastSystem.getOscillationParticle();
        PositionNVel pvel = null;
        double currentAcceleration = force(lastParticle.getY(),lastParticle.getSpeed())/lastParticle.getMass();
        switch(nM){
            case GPCO5:
                //GPCo5
                break;
            case BEEMAN:
                //Beeman
                double previousAcceleration = force(previousParticle.getY(),previousParticle.getSpeed())/previousParticle.getMass();
                pvel = beeman(lastParticle.getMass(), currentAcceleration,previousAcceleration,lastParticle.getY(), lastParticle.getSpeed());
                break;
            case VERLET:
                //Verlet
                pvel = verlet(lastParticle.getY(), previousParticle.getY(), currentAcceleration);
                break;
        }
        Particle newParticle  = new Particle(lastParticle.getId(),lastParticle.getX(),pvel.position,lastParticle.getRadius(),pvel.vel,lastParticle.getAngle(),lastParticle.getMass());
        previousParticle = lastParticle;
        lastSystem = new System(lastSystem.getTime()+deltaT,newParticle);


        op.outputStep(lastSystem);
        Logger.print(""+lastSystem.getTime());
        return lastSystem.getTime()>100;
    }

    private void gearPredictorCorrectorO5(Particle p){

    }

    private PositionNVel verlet(double position, double prevPosition, double acceleration){
        double newPos = 2*position - prevPosition + Math.pow(deltaT,2)*acceleration;
        double newVel = (newPos - prevPosition)/(2*deltaT);

        return new PositionNVel(newPos, newVel);
    }

    private PositionNVel beeman(double mass, double currAcceleration, double prevAcceleration,double position, double velocity){
        double newPosUnEje = position + (velocity*deltaT) + ((2f/3)*currAcceleration*Math.pow(deltaT,2)) - ((1f/6)*prevAcceleration*Math.pow(deltaT,2));
        double velPredicted = velocity+(3f/2)*currAcceleration*deltaT-(1f/2)*prevAcceleration*deltaT;

        double nextAcceleration = force(newPosUnEje,velPredicted)/mass;

        double newVelUnEje = velocity + ((1f/3)*nextAcceleration*deltaT) + ((5f/6)*currAcceleration*deltaT) - ((1f/6)*prevAcceleration*deltaT);
        return new PositionNVel(newPosUnEje, newVelUnEje);
    }

    private class PositionNVel{
        double position;
        double vel;

        public PositionNVel(double p, double v){
            position = p;
            vel = v;
        }
    }

    private double force(double r, double v){
        return -k*r-gama*v;
    }

    private double position(double t){
        double m =lastSystem.getOscillationParticle().getMass();
        return a*Math.exp(-(gama/(2*m))*t)*Math.cos(Math.pow((k/m)-(Math.pow(gama,2)/(4*Math.pow(m,2))),0.5)*t);
    }

    private double velocity(double t){
        double m =lastSystem.getOscillationParticle().getMass();
        double term4kmmenosgama = Math.pow(4*k*m-Math.pow(gama,2),0.5);
        double firstTerm = a*Math.exp(-(gama*t)/(2*m));
        double secondTerm1 = term4kmmenosgama;
        double secondTerm2 = Math.sin((t*term4kmmenosgama)/(2*m));
        double secondTerm3 = gama*Math.cos((t*term4kmmenosgama)/(2*m));
        return (firstTerm*(secondTerm1*secondTerm2+secondTerm3))/(2*m);
    }

    private double acceleration(double t){
        double m =lastSystem.getOscillationParticle().getMass();
        double term4kmmenosgamacsobremc = Math.pow((4*k*m-Math.pow(gama,2))/Math.pow(m,2),0.5);
        double firstTerm = 1/(4*Math.pow(m,2));
        double secondTerm = a*Math.exp(-(gama*t)/(2*m));
        double thirdTerm1 = 2*gama*m;
        double thirdTerm2 = term4kmmenosgamacsobremc;
        double thirdTerm3 = Math.sin(0.5*t*term4kmmenosgamacsobremc);
        double fourthTerm1 = Math.pow(gama,2) - 4*k*m + Math.pow(gama,2);
        double fourthTerm2 = Math.cos(0.5*t*term4kmmenosgamacsobremc);
        return firstTerm * secondTerm * (thirdTerm1*thirdTerm2*thirdTerm3 + fourthTerm1*fourthTerm2);
    }
}

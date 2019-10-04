package OscillationSimulator;

import Constants.Config;
import Log.Logger;
import Model.Particle;
import Model.System;

public class OscillationManager {
    Config c;
    private System lastSystem;
    private Particle previousParticle;
    private OscillationPrinter op;
    private boolean htp;
    private  double deltaT;
    private double a;
    private double gama;
    private double k;
    private Config.NuMethod nM;
    private double predictedR;
    private double predictedR1;
    private double predictedR2;
    private double predictedR3;
    private double predictedR4;
    private double predictedR5;
    private Double r;
    private Double r1;
    private Double r2;
    private Double r3;
    private Double r4;
    private Double r5;


    public OscillationManager(boolean hasToPrint){
        c = Config.getInstance();
        lastSystem = OscillationCreator.createInitialOscillationSystem();
        op = new OscillationPrinter();
        htp = hasToPrint;
        deltaT = c.SIMULATION_DELTA_TIME();
        a = c.OSCILLATOR_A();
        gama = c.OSCILLATOR_G();
        k = c.OSCILLATOR_K();
        nM = c.NUMERIC_METHOD();

        Particle oPart = lastSystem.getOscillationParticle();
        previousParticle = OscillationCreator.getInitialPreviousParticle(deltaT,force(oPart.getY(),oPart.getSpeed()), oPart);
    }

    public boolean stepForward() {
        Particle lastParticle = lastSystem.getOscillationParticle();
        PositionNVel pvel = null;
        double currentAcceleration = force(lastParticle.getY(),lastParticle.getYSpeed())/lastParticle.getMass();
        switch(nM){
            case GPCO5:
                //GPCo5
                pvel = gearPredictorCorrectorO5(deltaT,lastParticle.getMass(), lastParticle.getY(), lastParticle.getYSpeed());
                break;
            case BEEMAN:
                //Beeman
                double previousAcceleration = force(previousParticle.getY(),previousParticle.getYSpeed())/previousParticle.getMass();
                pvel = beeman(lastParticle.getMass(), currentAcceleration,previousAcceleration,lastParticle.getY(), lastParticle.getYSpeed());
                break;
            case VERLET:
                //Verlet
                pvel = verlet(lastParticle.getY(), previousParticle.getY(), currentAcceleration, c.SIMULATION_DELTA_TIME());
                break;
            case ANALYTICAL:
                double position = position(lastSystem.getTime()+deltaT);
                double velocity = velocity(lastSystem.getTime()+deltaT);
                pvel = new PositionNVel(position,velocity);

        }
        Particle newParticle  = new Particle(lastParticle.getId(),lastParticle.getX(),pvel.position,lastParticle.getRadius(),Math.abs(pvel.vel),Particle.getAngle(0,pvel.vel),lastParticle.getMass(), Particle.getRandomInteractionRatio());
        previousParticle = lastParticle;
        lastSystem = new System(lastSystem.getTime()+deltaT,newParticle);


        op.outputStep(lastSystem);
        Logger.print(""+lastSystem.getTime());
        return lastSystem.getTime()>10;
    }

    private PositionNVel gearPredictorCorrectorO5(double deltaT, double mass, double position, double velocity){
        if(r == null) r = position;
        if(r1 == null) r1 = velocity;
        if(r2 == null) r2 = -k/mass * (position-c.OSCILLATOR_INITIAL_POSITION());
        if(r3 == null) r3 = -k/mass * velocity;
        if(r4 == null) r4 = Math.pow(k/mass, 2) * (position-c.OSCILLATOR_INITIAL_POSITION());
        if(r5 == null) r5 = Math.pow(k/mass, 2) * velocity;
        predictedR = r + r1*deltaT + r2*Math.pow(deltaT,2)/2 + r3*Math.pow(deltaT,3)/6 + r4*Math.pow(deltaT,4)/24 + r5*Math.pow(deltaT,5)/120;
        predictedR1 = r1 + r2*deltaT + r3*Math.pow(deltaT,2)/2 + r4*Math.pow(deltaT,3)/6 + r5*Math.pow(deltaT,4)/24;
        predictedR2 = r2 + r3*deltaT + r4*Math.pow(deltaT,2)/2 + r5*Math.pow(deltaT,3)/6;
        predictedR3 = r3 + r4*deltaT + r5*Math.pow(deltaT,2)/2;
        predictedR4 = r4 + r5*deltaT;
        predictedR5 = r5;

        double nextAcceleration = force(predictedR,predictedR1)/mass;
        double deltaA = nextAcceleration - predictedR2;
        double deltaR2 = deltaA * Math.pow(deltaT,2) / 2;

        double correctedR = predictedR + c.ALPHA_0_OSCILLATOR() * deltaR2;
        double correctedR1 = predictedR1 + c.ALPHA_1()  * deltaR2 / deltaT;
        double correctedR2 = predictedR2 + c.ALPHA_2() * deltaR2 * 2 / Math.pow(deltaT,2);
        double correctedR3 = predictedR3 + c.ALPHA_3() * deltaR2 * 6 / Math.pow(deltaT,3);
        double correctedR4 = predictedR4 + c.ALPHA_4() * deltaR2 * 24 / Math.pow(deltaT,4);
        double correctedR5 = predictedR5 + c.ALPHA_5() * deltaR2 * 120 / Math.pow(deltaT,5);

        r = correctedR;
        r1 = correctedR1;
        r2 = correctedR2;
        r3 = correctedR3;
        r4 = correctedR4;
        r5 = correctedR5;

        return new PositionNVel(correctedR, correctedR1);
    }

    public PositionNVel verlet(double position, double prevPosition, double acceleration, double delta){
        double newPos = 2*position - prevPosition + Math.pow(delta,2)*acceleration;
        double newVel = (newPos - prevPosition)/(2*delta);

        return new PositionNVel(newPos, newVel);
    }

    private PositionNVel beeman(double mass, double currAcceleration, double prevAcceleration,double position, double velocity){
        double newPosUnEje = position + (velocity*deltaT) + ((2f/3)*currAcceleration*Math.pow(deltaT,2)) - ((1f/6)*prevAcceleration*Math.pow(deltaT,2));
        double velPredicted = velocity+(3f/2)*currAcceleration*deltaT-(1f/2)*prevAcceleration*deltaT;

        double nextAcceleration = force(newPosUnEje,velPredicted)/mass;

        double newVelUnEje = velocity + ((1f/3)*nextAcceleration*deltaT) + ((5f/6)*currAcceleration*deltaT) - ((1f/6)*prevAcceleration*deltaT);
        return new PositionNVel(newPosUnEje, newVelUnEje);
    }

    public class PositionNVel{
        private double position;
        private double vel;

        public PositionNVel(double p, double v){
            position = p;
            vel = v;
        }

        public double getPosition() {
            return position;
        }

        public double getVel() {
            return vel;
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

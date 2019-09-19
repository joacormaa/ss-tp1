package OscillationSimulator;

import Constants.Config;
import Model.Particle;
import Model.System;

public class OscillationManager {
    private System lastSystem;
    private OscillationPrinter op;
    private boolean htp;
    private  double deltaT;
    private double a;
    private double gama;
    private double k;
    private int nM;

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
    }

    //ToDo: avanzar el sistema un delta t
    public boolean stepForward() {
        Particle lastParticle = lastSystem.getOscillationParticle();
        switch(nM){
            case 0:
                //GPCo5
                break;
            case 1:
                //Beeman
                beeman(lastSystem.getTime(), deltaT, lastParticle.getY(), lastParticle.getYSpeed());
                break;
            case 2:
                //Verlet
                break;
        }
        return true;
    }

    private void gearPredictorCorrectorO5(Particle p){

    }

    private PositionNVel beeman(double t, double deltaT, double p, double v){
        double newPosUnEje = position(t) + (velocity(t)*deltaT) + ((2/3)*acceleration(t)*Math.pow(deltaT,2)) - ((1/6)*acceleration((t-deltaT))*Math.pow(deltaT,2));
        double newVelUnEje = velocity(t) + ((1/3)*acceleration((t + deltaT))*deltaT) + ((5/6)*acceleration(t)*deltaT) - ((1/6)*acceleration((t-deltaT))*deltaT);
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

package ForceSimulator;

import Constants.Config;
import Log.Logger;
import Metrics.SystemMetrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForceSimulatorComparer {
    private Config c;
    private Map<Double, List<Double>> equilibriumTimes;
    private ForceSimulatorPrinter fsp;
    private double lastFp;

    public ForceSimulatorComparer(){
        c = Config.getInstance();
        equilibriumTimes = new HashMap<>();
        fsp = new ForceSimulatorPrinter();
    }

    public void compareForces(double initialHw, double finalHw, double steps, int simulationRuns){

        double currentHw = initialHw;
        double hwStep = (finalHw - initialHw) / steps;

        while(currentHw<=finalHw){
            double currentHp = (c.VERTICAL_WALL_LENGTH() - currentHw)/2;
            Logger.print("\nRunning comparisons with Hw: '"+currentHw+"' Hp: '"+currentHp+"'\n");
            c.setHOLE_POSITION(currentHp);
            c.setHOLE_LENGTH(currentHw);

            lastFp=0;
            List<Double> times = runNForceSimulations(simulationRuns);
            equilibriumTimes.put(currentHw,times);

            currentHw+=hwStep;
        }
    }

    public void outputComparisons(int simulationRuns){
        fsp.outputComparisons(equilibriumTimes, simulationRuns);
    }

    private List<Double> runNForceSimulations(int simulationRuns) {
        List<Double> ret = new ArrayList<>();

        for(int i=0; i<simulationRuns;i++){
            ForceSimulatorManager fsm = new ForceSimulatorManager();
            boolean stopped=false;
            SystemMetrics lastSystemMetrics = null;
            while(!stopped){
                lastSystemMetrics = fsm.stepForward(c.SIMULATION_DELTA_TIME(),false);
                stopped = stopCondition(lastSystemMetrics);
            }
            ret.add(lastSystemMetrics.getTime());
            Logger.print("Finished run number: "+i);
        }
        return ret;

    }

    private boolean stopCondition(SystemMetrics lastSystemMetrics) {
        double fp = lastSystemMetrics.getFp();
        if(Math.abs(lastFp-fp)>0.001){
            Logger.print("fp change from '"+lastFp+"' to '"+fp+"' currentTime "+lastSystemMetrics.getTime());
            lastFp=fp;
        }
        return fp>=0.5;
    }

}

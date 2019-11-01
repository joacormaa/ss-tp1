package main;

import Constants.Config;
import Log.Logger;
import Metrics.SystemMetrics;
import Model.CollisionCourse;
import PeopleSimulator.PeopleSimulatorCreator;
import PeopleSimulator.PeopleSimulatorManager;
import PeopleSimulator.PeopleSimulatorPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Logger.loggerInit();
        runPeopleSimulation(0);
    }

    public static void runPeopleSimulation(int i) {
        PeopleSimulatorManager psm = new PeopleSimulatorManager(i);
        boolean flag=true;
        int j=0;
        Config c = Config.getInstance();
        while(flag){
            boolean print= j%c.FRAMES_PER_PRINT()==0;
            SystemMetrics sm =psm.stepForward(c.SIMULATION_DELTA_TIME(),print);
            flag = sm.getGoals() == 0;
            j++;
        }
        Logger.print("Ended simulation in "+i+" steps");
    }

}
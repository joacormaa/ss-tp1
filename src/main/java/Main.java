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
        runPeopleSimulation();
    }

    private static void runPeopleSimulation() {
        PeopleSimulatorManager psm = new PeopleSimulatorManager();
        boolean flag=true;
        int i=0;
        Config c = Config.getInstance();
        while(flag){
            boolean print= i%c.FRAMES_PER_PRINT()==0;
            SystemMetrics sm =psm.stepForward(c.SIMULATION_DELTA_TIME(),print);
            flag = sm.getGoals() == 0;
            i++;
        }
        Logger.print("Ended simulation in "+i+" steps");
    }

}
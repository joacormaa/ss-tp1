import Constants.Config;
import Log.Logger;
import Model.CollisionCourse;
import PeopleSimulator.PeopleSimulatorCreator;
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
        Logger.print("Initializing People Simulation.");
        CollisionCourse initialCollisionCourse = PeopleSimulatorCreator.getInitialCollisionCourse();
        Logger.print("Initial System done.");

        PeopleSimulatorPrinter psp = new PeopleSimulatorPrinter();
        psp.printCollisionCourse(initialCollisionCourse);

        Logger.print("Initial system printed to file.");

    }

}
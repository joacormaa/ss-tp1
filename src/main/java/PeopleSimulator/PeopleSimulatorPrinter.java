package PeopleSimulator;

import Constants.Config;
import Metrics.SystemMetrics;
import Model.CollisionCourse;
import NeighbourLogic.Helper;

import java.util.List;
import java.util.Map;

public class PeopleSimulatorPrinter {

    private static String PARTICLE_OUTPUT_PATH = "particles.ov";
    private static String METRIC_OUTPUT_PATH = "metrics.csv";
    private static String COMPARISON_OUTPUT_PATH = "comparison.csv";
    private Config c;

    public PeopleSimulatorPrinter(){
        this.c = Config.getInstance();
        Helper.resetFile(c.OUTPUT_PATH()+"/"+PARTICLE_OUTPUT_PATH);
        Helper.resetFile(c.OUTPUT_PATH()+"/"+METRIC_OUTPUT_PATH);
        Helper.resetFile(c.OUTPUT_PATH()+"/"+ COMPARISON_OUTPUT_PATH);
    }

    public void printCollisionCourse(CollisionCourse collisionCourse){
        StringBuilder sb = new StringBuilder();

        sb.append(collisionCourse.getPersonMap().size() +collisionCourse.getObstacleMap().size() + collisionCourse.getGoalMap().size());
        sb.append('\n');
        sb.append('\n');
        sb.append(collisionCourse.stringify());

        Helper.appendToFile(sb.toString(),c.OUTPUT_PATH()+"/"+PARTICLE_OUTPUT_PATH);
    }
}

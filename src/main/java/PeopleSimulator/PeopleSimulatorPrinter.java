package PeopleSimulator;

import Constants.Config;
import Metrics.SystemMetrics;
import Model.CollisionCourse;
import NeighbourLogic.Helper;

public class PeopleSimulatorPrinter {

    private static String PARTICLE_OUTPUT_PATH;
    private static String METRIC_OUTPUT_PATH;
    private Config c;

    private static String header = "time,distance,speed,x,y\n";

    public PeopleSimulatorPrinter(int i){
        this.c = Config.getInstance();

        PARTICLE_OUTPUT_PATH = c.OUTPUT_PATH()+"/"+"particle.ov";
        METRIC_OUTPUT_PATH = c.OUTPUT_PATH()+"/metrics_"+i+".csv";

        Helper.resetFile(PARTICLE_OUTPUT_PATH);
        Helper.resetFile(METRIC_OUTPUT_PATH);
        Helper.appendToFile(header, METRIC_OUTPUT_PATH);
    }

    public void printCollisionCourse(CollisionCourse collisionCourse){
        StringBuilder sb = new StringBuilder();

        sb.append(collisionCourse.getPersonMap().size() +collisionCourse.getObstacleMap().size() + collisionCourse.getGoalMap().size());
        sb.append('\n');
        sb.append('\n');
        sb.append(collisionCourse.stringify());

        Helper.appendToFile(sb.toString(),PARTICLE_OUTPUT_PATH);
    }

    public void printCollisionCourseMetrics(SystemMetrics sm) {
        String sb = String.valueOf(sm.getTime()) + ',' +
                sm.getDistanceCovered() + ',' +
                sm.getInstantSpeed() + ',' +
                sm.getX() + ',' +
                sm.getY() + '\n';
        Helper.appendToFile(sb,METRIC_OUTPUT_PATH);
    }
}

package PeopleSimulator;

import Constants.Config;
import Model.*;
import Model.Vector;

import java.util.*;

public final class PeopleSimulatorCreator {
    private static Config c;
    private PeopleSimulatorCreator(){
        Config c  = Config.getInstance();
    }

    public static CollisionCourse getInitialCollisionCourse(){
        Map<Integer,Obstacle> obstacles = new HashMap<>();
        Map<Integer, Person> people = new HashMap<>();
        Map<Integer, Goal> goals = new HashMap<>();


        List<Double> xPositions = getXPositions();
        int obsid=0;
        for(Double pos : xPositions){
            Double y = Math.random()*c.VERTICAL_WALL_LENGTH();
            Vector position = new Vector(pos,y);
            obstacles.put(obsid++, new Obstacle(c.OBSTACLE_R(),position,new Vector(0,c.OBSTACLE_SPEED())));
        }

        Vector g_position = new Vector(c.HORIZONTAL_WALL_LENGTH(), Math.random()*c.VERTICAL_WALL_LENGTH());
        Goal goal = new Goal(g_position,c.GOAL_RADIUS(),null);

        Vector p_position = new Vector(0,Math.random()*c.VERTICAL_WALL_LENGTH());
        Vector p_velocity = Vector.NULL_VECTOR;

        people.put(0, new Person(p_position,p_velocity,c.PERSON_MIN_R(),c.PERSON_MAX_R(),goal));
        goals.put(0,goal);


        return new CollisionCourse(people,obstacles,goals,0);
    }

    private static List<Double> getXPositions() {
        double min = c.OBSTACLE_R()+c.PERSON_MAX_R();
        double max = c.HORIZONTAL_WALL_LENGTH() - (c.OBSTACLE_R()+ c.GOAL_RADIUS());

        int tolerance = c.MAX_TOLERANCE();

        List<Double> ret = new ArrayList<>();

        while(tolerance>0){
            double curr_x = Math.random()*(max-min)+min;

            if(hasNoCollisions(curr_x, ret)) {
                ret.add(curr_x);
                tolerance = c.MAX_TOLERANCE();
            }
            else{
                tolerance--;
            }
        }
        return ret;
    }


    //todo: este metodo seria mucho mejor si x_vals estuviera sorteado, no se si vale la pena. no se me ocurrio una forma de hacerlo....
    private static boolean hasNoCollisions(double curr_x, List<Double> x_vals) {
        for(Double x : x_vals){
            if(Math.abs(x-curr_x)<c.OBSTACLE_R())
                return false;
        }
        return true;
    }

}

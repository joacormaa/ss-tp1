package PeopleSimulator;

import Constants.Config;
import Model.*;
import Model.Vector;
import org.apache.commons.collections.map.HashedMap;

import java.util.*;

public final class PeopleSimulatorCreator {
    private PeopleSimulatorCreator(){}

    public static CollisionCourse getInitialCollisionCourse(){
        Map<Integer,Obstacle> obstacles = new HashMap<>();
        Map<Integer, Person> people = new HashMap<>();
        Map<Integer, Goal> goals = new HashMap<>();

        Config c  = Config.getInstance();

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
        return null; //todo
    }

}

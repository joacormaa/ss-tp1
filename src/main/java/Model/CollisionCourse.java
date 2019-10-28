package Model;

import java.util.Map;

public class CollisionCourse {
    private Map<Integer, Person> personMap;
    private Map<Integer, Obstacle> obstacleMap;
    private Map<Integer, Goal> goalMap;
    private double time;

    public CollisionCourse(Map<Integer, Person> personMap, Map<Integer, Obstacle> obstacleMap, Map<Integer, Goal> goalMap, double time) {
        this.personMap = personMap;
        this.obstacleMap = obstacleMap;
        this.goalMap= goalMap;
        this.time = time;
    }

    public Map<Integer, Person> getPersonMap() {
        return personMap;
    }

    public Map<Integer, Obstacle> getObstacleMap() {
        return obstacleMap;
    }

    public Map<Integer, Goal> getGoalMap() {
        return goalMap;
    }

    public double getTime() {
        return time;
    }

    public String stringify() {
        StringBuilder sb = new StringBuilder();

        for(Person person : personMap.values())
            sb.append(person.stringify());
        for(Obstacle obstacle : obstacleMap.values())
            sb.append(obstacle.stringify());
        for(Goal goal : goalMap.values())
            sb.append(goal.stringify());

        return sb.toString();
    }
}

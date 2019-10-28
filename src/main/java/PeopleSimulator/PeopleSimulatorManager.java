package PeopleSimulator;

import Constants.Config;
import Metrics.SystemMetrics;
import Model.*;
import Model.Vector;
import NeighbourLogic.SystemNeighbourManager;
import org.apache.commons.collections.map.HashedMap;

import java.util.*;

public class PeopleSimulatorManager {

    private Config c;
    private PeopleSimulatorPrinter psp;
    private SystemNeighbourManager snm;

    private CollisionCourse currStep;

    public PeopleSimulatorManager(){
        this.currStep = PeopleSimulatorCreator.getInitialCollisionCourse();
        this.c = Config.getInstance();
        this.psp = new PeopleSimulatorPrinter();
        this.snm = new SystemNeighbourManager();
    }

    public SystemMetrics stepForward(double delta){
        CollisionCourse nextStep = getNextCollisionCourse(delta);
        this.currStep=nextStep;
        return new SystemMetrics(currStep);
    }

    private CollisionCourse getNextCollisionCourse(double delta) {
        Map<Integer, Person> personMap = getNextPersonMap(delta);
        Map<Integer, Obstacle> obstacleMap = getNextObstacleMap(delta);
        Map<Integer, Goal> goalMap = getNextGoalMap();

        return new CollisionCourse(personMap,obstacleMap,goalMap,this.currStep.getTime()+delta);
    }

    private Map<Integer, Goal> getNextGoalMap() {
        return currStep.getGoalMap();
    }

    private Map<Integer, Obstacle> getNextObstacleMap(double delta) {
        Map<Integer, Obstacle> ret = new HashMap<>();
        for(Map.Entry<Integer,Obstacle> obstacle : currStep.getObstacleMap().entrySet()){
            ret.put(obstacle.getKey(),obstacle.getValue().getNext(delta));
        }
        return ret;
    }

    private Map<Integer, Person> getNextPersonMap(double delta) {
        Map<Person, Set<Interactable>> neighbourMap = snm.getNeighbours(this.currStep);
        Map<Integer,Person> ret = new HashMap<>();
        for(Map.Entry<Integer,Person> entry : currStep.getPersonMap().entrySet()){
            ret.put(entry.getKey(),getNextPerson(entry.getValue(),neighbourMap, delta));
        }
        return ret;
    }

    private Person getNextPerson(Person p, Map<Person, Set<Interactable>> neighbourMap, double delta) {
        Set<Interactable> neighbours = neighbourMap.get(p);
        MovementInfo movementInfo = getNextVelocity(p, neighbours);

        Vector movement = movementInfo.nextVelocity.multiplyBy(delta);
        Vector nextPosition = p.getPosition().sum(movement);
        Goal goal = (p.achievedGoal())?p.getGoal().nextGoal():p.getGoal();

        double radius = getNextRadius(p,movementInfo.isInContact, delta);

        return new Person(nextPosition,movementInfo.nextVelocity,radius,goal);

    }

    private double getNextRadius(Person p, boolean isInContact, double delta) {
        if(isInContact){
            return c.PERSON_MIN_R();
        }else{
            double nextRadius = p.getRadius() + c.PERSON_MAX_R() * (delta/10); //todo: unhardcode tau
            return Math.min(c.PERSON_MAX_R(),nextRadius);
        }
    }

    private MovementInfo getNextVelocity(Person p, Set<Interactable> neighbours) {
        List<Vector> velocityVectors = new ArrayList<>();
        for(Interactable neighbour : neighbours){
            if(p.isAdjacentTo(neighbour)){
                Vector direction = p.getPosition().minus(neighbour.getPosition());
                velocityVectors.add(direction);
            }
        }

        boolean isIncontact = false;
        Vector velocity;
        if(velocityVectors.isEmpty()){
            double multiplier = c.PERSON_SPEED() * Math.pow((p.getRadius()-c.PERSON_MIN_R())/(c.PERSON_MAX_R()-c.PERSON_MIN_R()),1);//todo: unhardcode Beta
            Vector direction = p.getDesiredDirection();
            velocity = direction.multiplyBy(multiplier);
        }
        else{
            velocity = Vector.AverageVector(velocityVectors).multiplyBy(3);//todo: unhardcode VE
            isIncontact = true;
        }
        return new MovementInfo(velocity,isIncontact);
    }

    class MovementInfo{
        Vector nextVelocity;
        boolean isInContact;
        public MovementInfo(Vector nextVelocity, boolean isInContact){
            this.nextVelocity=nextVelocity;
            this.isInContact=isInContact;
        }
    }
}

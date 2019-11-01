package PeopleSimulator;

import Constants.Config;
import Log.Logger;
import Metrics.SystemMetrics;
import Model.*;
import Model.Vector;
import NeighbourLogic.SystemNeighbourManager;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.*;

public class PeopleSimulatorManager {

    private Config c;
    private PeopleSimulatorPrinter psp;
    private SystemNeighbourManager snm;

    private CollisionCourse currStep;

    public PeopleSimulatorManager(){
        Logger.print("Initializing People Simulation.");
        this.currStep = PeopleSimulatorCreator.getInitialCollisionCourse();
        Logger.print("Initial System done.");
        this.c = Config.getInstance();
        this.psp = new PeopleSimulatorPrinter();
        this.snm = new SystemNeighbourManager();
    }

    public SystemMetrics stepForward(double delta, boolean hasToPrint){
        Logger.print("Calculating step t="+(delta+currStep.getTime()));
        CollisionCourse nextStep = getNextCollisionCourse(delta);
        this.currStep=nextStep;
        SystemMetrics sm = new SystemMetrics(currStep,movement.norm(),speedMultiplier,goalCounter);
        if(hasToPrint){
            psp.printCollisionCourse(nextStep);
            psp.printCollisionCourseMetrics(sm);
            Logger.print("Printed system and systemMetrics to file.");
        }
        return sm;
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

    private int goalCounter=0;

    private Map<Integer, Person> getNextPersonMap(double delta) {
        goalCounter=0;
        Map<Person, Set<Interactable>> neighbourMap = snm.getNeighbours(this.currStep);
        Map<Integer,Person> ret = new HashMap<>();
        for(Map.Entry<Integer,Person> entry : currStep.getPersonMap().entrySet()){
            ret.put(entry.getKey(),getNextPerson(entry.getValue(),neighbourMap, delta));
        }
        return ret;
    }

    Vector movement = null;

    private Person getNextPerson(Person p, Map<Person, Set<Interactable>> neighbourMap, double delta) {
        Set<Interactable> neighbours = neighbourMap.get(p);
        MovementInfo movementInfo = getNextVelocity(p, neighbours);


        movement = movementInfo.nextVelocity.multiplyBy(delta);
        Vector nextPosition = p.getPosition().sum(movement);
        Goal goal = p.getGoal();
        if(p.achievedGoal()){
          goal =p.getGoal().nextGoal();
          if(goal!=null)
            currStep.getGoalMap().remove(2);
        }

        double radius = getNextRadius(p,movementInfo.isInContact, delta);

        if(goal==null){
            goalCounter++;
        }
        crashedLastTime=movementInfo.isInContact;
        return new Person(nextPosition,movementInfo.nextVelocity,radius,goal);

    }

    private double getNextRadius(Person p, boolean isInContact, double delta) {
        if(isInContact){
            return c.PERSON_MIN_R();
        }else{
            double nextRadius = p.getRadius() + c.PERSON_MAX_R() * (delta/c.TAU());
            return Math.min(c.PERSON_MAX_R(),nextRadius);
        }
    }

    double speedMultiplier;

    boolean crashedLastTime=false;

    private MovementInfo getNextVelocity(Person p, Set<Interactable> neighbours) {
        List<Vector> velocityVectors = new ArrayList<>();
        for(Interactable neighbour : neighbours){
            if(p.isAdjacentTo(neighbour)){
                Vector direction = p.getPosition().minus(neighbour.getPosition());
                velocityVectors.add(direction);
            }
        }

        boolean isInContact = false;
        Vector velocity;
        speedMultiplier = c.PERSON_SPEED() * Math.pow((p.getRadius()-c.PERSON_MIN_R())/(c.PERSON_MAX_R()-c.PERSON_MIN_R()),c.BETA());
        if(velocityVectors.isEmpty()){
            if(!crashedLastTime){
                Vector newGoal = getNextCollisionWithNeighbors(p, neighbours);
                if(newGoal != null){
                    if(newGoal.getX()<=p.getPosition().getX()){
                        newGoal = new Vector(p.getPosition().getX()+c.PERSON_MAX_R(),newGoal.getY());
                    }
                    Goal g = p.addGoal(newGoal);
                    currStep.getGoalMap().put(2, g);
                }
            }
            Vector direction = p.getDesiredDirection();
            velocity = direction.multiplyBy(speedMultiplier);
        }
        else{
            velocity = Vector.averageVector(velocityVectors).versor().multiplyBy(c.VE());
            isInContact = true;
        }
        return new MovementInfo(velocity,isInContact);
    }

    private Vector getNextCollisionWithNeighbors(Person p, Set<Interactable> neig){
        if(neig.isEmpty())
            return null;
        double delta = c.SIMULATION_DELTA_TIME();
        Vector goal = null;
        Vector personPos = p.getPosition();
        Vector personVel = p.getVelocity();
        boolean collision = false;
        //ToDo: Deshardcodear el 5
        for(int i=0; i<100 && !collision; i++) {
            for (Interactable n : neig) {
                if(collision)
                    break;
                Vector personMovement = personVel.multiplyBy(delta*i);
                Vector newPersonPos = personPos.sum(personMovement);
                Vector neiMove = n.getVelocity().multiplyBy(delta*i);
                Vector newNeigPos = n.getPosition().sum(neiMove);
                Person auxPerson = new Person(newPersonPos, personMovement, p.getRadius(), p.getGoal());
                Obstacle auxObstacle = new Obstacle(n.getRadius(), newNeigPos, neiMove);

                //Si esta nueva proyeccion es un choque inminente, hago un cambio en la direccion actual de mi particula, original
                if(auxPerson.isAdjacentTo(auxObstacle)){
                    boolean pos = n.getPosition().getY()>p.getPosition().getY();
                    boolean vel = n.getVelocity().getY()>0;
                    Vector offset = auxObstacle.getVelocity().versor().multiplyBy(3*(c.PERSON_MAX_R() + auxObstacle.getRadius()));
                    boolean spd_mod = personVel.norm()>n.getVelocity().norm();
                    boolean xor = Boolean.logicalXor(pos, vel);

                    xor= spd_mod == xor;

                    if(xor){
                        goal = auxObstacle.getPosition().sum(offset);
                    } else {
                        goal = auxObstacle.getPosition().minus(offset);
                    }
                    collision = true;
                }
            }
        }
        return goal;
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

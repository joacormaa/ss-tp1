package FlockSimulator;

import Constants.Config;
import Model.Particle;
import Model.System;
import NeighbourLogic.Helper;
import NeighbourLogic.SystemNeighbourManager;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FlockSimManager {

    private List<System> systems;
    private SystemNeighbourManager snm;
    private Config c;

    public FlockSimManager(System system){
        this.snm=new SystemNeighbourManager();
        this.systems=new LinkedList<>();
        this.c=Config.getInstance();
        systems.add(system);
    }

    public void stepForward(int delta){
        System lastSystem = getLastSystem();
        Map<Particle, Set<Particle>> neighbours = snm.getNeighbours(lastSystem);

        Collection<Particle> previousParticles = lastSystem.getParticles();
        Collection<Particle> nextParticles = new ArrayList<>();
        for(Particle p : previousParticles){
            Particle next = getNextParticle(p,neighbours,delta);
            nextParticles.add(next);
        }
        System nextSystem = new System(lastSystem.getTime()+delta,nextParticles);
        systems.add(nextSystem);
    }

    private Particle getNextParticle(Particle p, Map<Particle, Set<Particle>> neighbourMap, int delta) {
        Collection<Particle> neighbours = neighbourMap.get(p);

        double x_velocity = p.getSpeed()*Math.cos(p.getAngle());
        double y_velocity = p.getSpeed()*Math.sin(p.getAngle());

        final double x = Helper.getModule(p.getX()+x_velocity*delta,c.SYSTEM_LENGTH());

        final double y = Helper.getModule(p.getY()+y_velocity*delta,c.SYSTEM_LENGTH());


        final double angle = getAngle(p, neighbours);

        return new Particle(p.getId(),x,y,p.getSpeed(),angle);
    }

    private double getAngle(Particle p, Collection<Particle> neighbours) {
        int i=1;
        double cosineSum = Math.cos(p.getAngle());
        double sineSum = Math.sin(p.getAngle());
        for(Particle q : neighbours){
            cosineSum+=Math.cos(q.getAngle());
            sineSum+=Math.sin(q.getAngle());
            i++;
        }
        return Math.atan2(cosineSum/i,sineSum/i);
    }

    private System getLastSystem() {
        return systems.get(systems.size()-1);
    }

    public void printSystemOverTime(){

        String json = new Gson().toJson(systems);
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(c.OUTPUT_PATH());
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getJsonRepresentation(){

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return gson.toJson(systems);
    }
}

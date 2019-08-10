package NeighbourLogic;

import Constants.Config;
import Model.Particle;
import Model.System;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SystemNeighbourManager {
    private System system;
    private Map<Particle, Set<Particle>> neighbours;
    private Map<Integer, Cell> cellMap;
    private Config c;

    public SystemNeighbourManager(System system){
        this.system=system;
        this.neighbours=new HashMap<>();
        this.cellMap=new HashMap<>();
        this.c = Config.getInstance();
        initializeCells();
        initializeNeighbourMap();

        assignCells();
        assignNeighbours();

    }

    private void initializeNeighbourMap() {
        for (Particle p : system.getParticles())  neighbours.put(p,new HashSet<>());
    }


    private void initializeCells() {
        for(int i = 0; i < c.CELL_AMOUNT(); i++) {
            for(int j = 0; j < c.CELL_AMOUNT(); j++) {
                int cellId =i + j * c.CELL_AMOUNT();
                cellMap.put(cellId, new Cell(i, j));
            }
        }
    }

    private void assignCells() {
        for(Particle p : system.getParticles()) {
            int cellX = (int) (p.getX()/c.CELL_LENGTH());
            int cellY = (int) (p.getY() / c.CELL_LENGTH());
            int cellId = cellX+cellY*c.CELL_AMOUNT();
            Cell cell = cellMap.get(cellId);
            cell.addParticle(p);
        }
    }

    private void assignNeighbours() {
        for(Cell c : cellMap.values()) {
            for(Particle p : c.getParticles()) {

                List<Cell> neighbours = new ArrayList<>();
                for(Integer i :c.getNeighnourIds()) neighbours.add(cellMap.get(i));

                for(Cell neighbour : neighbours) {
                    for(Particle q : neighbour.getParticles()) {
                        if(!p.equals(q) && p.isAdjacent(q)) {
                            addNeighbour(p,q);
                        }
                    }
                }
            }
        }
    }

    private void addNeighbour(Particle p, Particle q){
        Set<Particle> pNeighbours = neighbours.get(p);
        Set<Particle> qNeighbours = neighbours.get(q);

        pNeighbours.add(q);
        qNeighbours.add(p);

        neighbours.put(p,pNeighbours);
        neighbours.put(q,qNeighbours);
    }

    public void outputNeighbours(){
        String text = getNeighbourState();

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(c.OUTPUT_PATH());
            fileWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getNeighbourState() {
        StringBuilder sb = new StringBuilder();
        for(Particle p: system.getParticles()){

            sb.append("Id: ");
            sb.append(p.getId());
            sb.append(", X: ");
            sb.append(p.getX());
            sb.append(", Y: ");
            sb.append(p.getY());
            sb.append(", Neighbours: ");

            sb.append('[');

            appendNeigbhours(sb, p);
            sb.append(']');
            sb.append('\n');
        }

        return sb.toString();
    }

    private void appendNeigbhours(StringBuilder sb,Particle p) {
        Iterator<Particle> it = neighbours.get(p).iterator();

        if(it.hasNext())sb.append(it.next().getId());

        while(it.hasNext()){
            sb.append(',');
            sb.append(it.next().getId());
        }

    }
}

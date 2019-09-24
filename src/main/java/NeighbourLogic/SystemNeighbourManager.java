package NeighbourLogic;

import Constants.Config;
import Model.*;
import Model.System;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SystemNeighbourManager {
    private System system;
    private Map<Particle, Set<Interactable>> neighbours;
    private Map<Integer, Cell> cellMap;
    private Config c;

    public SystemNeighbourManager(){
        this.neighbours=new HashMap<>();
        this.cellMap=new HashMap<>();
        this.c = Config.getInstance();
    }

    public Map<Particle, Set<Interactable>> getNeighbours(System system){
        this.system=system;
        initializeCells();
        initializeNeighbourMap();

        assignCells();
        assignNeighbours();
        return neighbours;
    }

    private void initializeNeighbourMap() {
        for (Particle p : system.getParticles().values())  neighbours.put(p,new HashSet<>());
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

        for(Particle p : system.getParticles().values()) {
            addParticleToCell(p);
        }

        for(Particle p : system.getStaticParticles().values()) {
            addParticleToCell(p);
        }

        for(Wall w : system.getWalls().values()) {
            if(w.isVertical()){
                int cellX = (int) (w.getX()/c.CELL_LENGTH());
                if(cellX>=c.CELL_AMOUNT()) cellX=c.CELL_AMOUNT()-1; //workaround choto
                for(int cellY = 0; cellY<c.CELL_AMOUNT(); cellY++){
                    int cellId = cellX+cellY*c.CELL_AMOUNT();
                    Cell cell = cellMap.get(cellId);
                    cell.addWall(w);
                }
            }
            else{
                int cellY = (int) (w.getY()/c.CELL_LENGTH());
                if(cellY>=c.CELL_AMOUNT()) cellY=c.CELL_AMOUNT()-1; //workaround choto
                for(int cellX = 0; cellX<c.CELL_AMOUNT(); cellX++){
                    int cellId = cellX+cellY*c.CELL_AMOUNT();
                    Cell cell = cellMap.get(cellId);
                    cell.addWall(w);
                }

            }
        }
    }

    private void addParticleToCell(Particle p) {
        int cellX = (int) (p.getX()/c.CELL_LENGTH());
        int cellY = (int) (p.getY() / c.CELL_LENGTH());
        int cellId = cellX+cellY*c.CELL_AMOUNT();
        Cell cell = cellMap.get(cellId);
        cell.addParticle(p);
    }

    private void assignNeighbours() {
        for(Cell c : cellMap.values()) {
            for(Interactable in : c.getInteractables()) {
                if(in instanceof Wall || in instanceof StaticParticle) continue;

                Particle p = (Particle) in;
                List<Cell> neighbours = new ArrayList<>();
                for(Integer i :c.getNeighbourIds()) neighbours.add(cellMap.get(i));

                for(Cell neighbour : neighbours) {
                    for(Interactable q : neighbour.getInteractables()) {
                        if(!p.equals(q) && p.isAdjacentTo(q)) {
                            addNeighbour(p,q);
                        }
                    }
                }
            }
        }
    }

    private void addNeighbour(Particle p, Interactable q){
        Set<Interactable> pNeighbours = neighbours.get(p);
        if(q instanceof Particle && ! (q instanceof StaticParticle)){
            Particle qPart = (Particle) q;
            Set<Interactable> qNeighbours = neighbours.get(qPart);
            qNeighbours.add(p);
            neighbours.put(qPart,qNeighbours);

        }
        pNeighbours.add(q);
        neighbours.put(p,pNeighbours);
    }
}

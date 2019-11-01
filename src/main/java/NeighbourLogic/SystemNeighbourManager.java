package NeighbourLogic;

import Constants.Config;
import Model.*;
import java.util.*;

public class SystemNeighbourManager {
    private CollisionCourse collisionCourse;
    private Map<Person, Set<Interactable>> neighbours;
    private Map<Integer, Cell> cellMap;
    private Config c;

    public SystemNeighbourManager(){
        this.neighbours=new HashMap<>();
        this.cellMap=new HashMap<>();
        this.c = Config.getInstance();
    }

    public Map<Person, Set<Interactable>> getNeighbours(CollisionCourse collisionCourse){
        this.collisionCourse=collisionCourse;
        initializeCells();
        initializeNeighbourMap();

        assignCells();
        assignNeighbours();
        return neighbours;
    }

    private void initializeNeighbourMap() {
        for (Person p : collisionCourse.getPersonMap().values())  neighbours.put(p,new HashSet<>());
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
        for(Person p : collisionCourse.getPersonMap().values()) {
            addInteractableToCell(p);
        }
        for(Obstacle o : collisionCourse.getObstacleMap().values()){
            addInteractableToCell(o);
        }
    }

    private void addInteractableToCell(Interactable i) {
        int cellId = getCellId(i);
        Cell cell = cellMap.get(cellId);
        cell.addInteractable(i);
    }

    private int getCellId(Interactable i) {
        int cellX = (int) (i.getPosition().getX() / c.CELL_LENGTH());
        int cellY = (int) (i.getPosition().getY() / c.CELL_LENGTH());

        if(i.getPosition().getX()>c.HORIZONTAL_WALL_LENGTH())
            cellX = c.CELL_AMOUNT()-1;
        if(i.getPosition().getX()<0)
            cellX = 0;
        if(i.getPosition().getY()>c.VERTICAL_WALL_LENGTH())
            cellY = c.CELL_AMOUNT()-1;
        if(i.getPosition().getY()<0)
            cellY = 0;

        return cellX+cellY*c.CELL_AMOUNT();
    }

    private void assignNeighbours() {
        for(Person p : collisionCourse.getPersonMap().values()) {
            List<Cell> neighbours = new ArrayList<>();
            Cell c = cellMap.get(getCellId(p));
            for(Integer i :c.getNeighbourIds()) neighbours.add(cellMap.get(i));

            for(Cell neighbour : neighbours) {
                for(Interactable q : neighbour.getInteractables()) {
                    if(!p.equals(q)) {
                        addNeighbour(p,q);
                    }
                }
            }
        }
    }

    private void addNeighbour(Person p, Interactable q){
        Set<Interactable> pNeighbours = neighbours.get(p);
        pNeighbours.add(q);
        neighbours.put(p,pNeighbours);
    }
}

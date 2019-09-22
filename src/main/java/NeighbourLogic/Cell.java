package NeighbourLogic;

import Constants.Config;
import Model.Interactable;
import Model.Particle;
import Model.Wall;

import java.util.LinkedList;
import java.util.List;

public class Cell {
    private List<Interactable> interactables = new LinkedList<>();
    private int xPosition;
    private int yPosition;

    public Cell(int x, int y) {
        xPosition = x;
        yPosition = y;
    }

    public void addParticle(Particle p) {
        interactables.add(p);
    }
    public void addWall(Wall w) {
        interactables.add(w);
    }

    public List<Integer> getNeighbourIds() {
        List<Integer> neighbours = new LinkedList<>();

        neighbours.add(getNeighbourId(xPosition,yPosition));
        neighbours.add(getNeighbourId(xPosition,yPosition+1));
        neighbours.add(getNeighbourId(xPosition+1,yPosition+1));
        neighbours.add(getNeighbourId(xPosition+1,yPosition));
        neighbours.add(getNeighbourId(xPosition+1,yPosition-1));

        return neighbours;
    }

    public List<Interactable> getInteractables() {
        return interactables;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    private int getNeighbourId(int x, int y){
        Config c = Config.getInstance();

        int x_mod = Helper.getModule(x,c.CELL_AMOUNT());
        int y_mod = Helper.getModule(y,c.CELL_AMOUNT());

        return x_mod+y_mod*c.CELL_AMOUNT();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;

        Cell cell = (Cell) o;

        if (xPosition != cell.xPosition) return false;
        return yPosition == cell.yPosition;
    }

    @Override
    public int hashCode() {
        int result = xPosition;
        result = 31 * result + yPosition;
        return result;
    }
}

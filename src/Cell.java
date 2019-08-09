import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Cell {
    private List<Particle> particles = new LinkedList<>();
    private int xPosition;
    private int yPosition;

    public Cell(int x, int y) {
        xPosition = x;
        yPosition = y;
    }

    public void addParticle(Particle p) {
        particles.add(p);
    }

    public List<Cell> getNeighnours(Map<Integer,Cell> cellsMap, int cellAmount) {
        List<Cell> neighbours = new LinkedList<>();
        neighbours.add(cellsMap.get(Helper.getModule(xPosition, cellAmount)+Helper.getModule(yPosition, cellAmount)));
        neighbours.add(cellsMap.get(Helper.getModule(xPosition, cellAmount)+Helper.getModule(yPosition + 1, cellAmount)));
        neighbours.add(cellsMap.get(Helper.getModule(xPosition + 1, cellAmount)+Helper.getModule(yPosition, cellAmount)));
        neighbours.add(cellsMap.get(Helper.getModule(xPosition + 1, cellAmount)+Helper.getModule(yPosition + 1, cellAmount)));
        neighbours.add(cellsMap.get(Helper.getModule(xPosition - 1, cellAmount)+Helper.getModule(yPosition + 1, cellAmount)));
        return neighbours;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }
}

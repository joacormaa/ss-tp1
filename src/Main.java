import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
    private double BOARD_LENGTH = 100;
    private double CELL_LENGTH = 20;
    private int PARTICLES_QUANTITY = 50;
    private double RADIUS = 1;
    private double RC = 15;
    private String path = "output.txt";

    public static void main(String[] args) {
        List<Particle> particles = new LinkedList<>();
        Map<Integer,Cell> cellsMap = new HashMap<>();
        Main instance = new Main();
        instance.generateRandomPositions(particles);
        instance.initializeCells(cellsMap);
        instance.assignCells(particles, cellsMap);
        instance.assignNeighbours(cellsMap);
        instance.printNeighbours(particles);
    }

    private void printNeighbours(List<Particle> particles) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(path);
            for(Particle p : particles) fileWriter.write(p.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void assignNeighbours(Map<Integer, Cell> cellsMap) {
        for(Cell c : cellsMap.values()) {
            for(Particle p : c.getParticles()) {
                for(Cell neighbour : c.getNeighnours(cellsMap, (int) (BOARD_LENGTH/CELL_LENGTH))) {
                    for(Particle q : neighbour.getParticles()) {
                        if(p.isAdjacent(q, RADIUS, RC)) {
                            p.addNeighbour(q);
                            q.addNeighbour(p);
                        }
                    }
                }
            }
        }
    }

    private void initializeCells(Map<Integer,Cell> cellsMap) {
        for(int i = 0; i < BOARD_LENGTH/CELL_LENGTH; i++) {
            for(int j = 0; j < BOARD_LENGTH/CELL_LENGTH; j++) {
               cellsMap.put((int) (i + j * BOARD_LENGTH / CELL_LENGTH), new Cell(i, j));
            }
        }
    }

    private void assignCells(List<Particle> particles, Map<Integer,Cell> cellsMap) {
        for(Particle p : particles) {
            int cellX = (int) (p.getX()/CELL_LENGTH);
            int cellY = (int) (p.getY() / CELL_LENGTH);
            cellsMap.get(cellX+cellY*BOARD_LENGTH/CELL_LENGTH).addParticle(p);
        }
    }

    private void generateRandomPositions(List<Particle> particles) {
        for(int i = 0; i < PARTICLES_QUANTITY; i++) {
            double x = Math.random() * BOARD_LENGTH;
            double y = Math.random() * BOARD_LENGTH;
            particles.add(new Particle(i, x, y));
        }
    }
}
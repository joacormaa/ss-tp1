import Model.SystemInstant;
import NeighbourLogic.SystemNeighbourManager;

public class Main {

    public static void main(String[] args) {

        SystemInstant systemInstant = new SystemInstant(0);

        SystemNeighbourManager snm = new SystemNeighbourManager(systemInstant);
        snm.calculateNeighbours();
        snm.outputNeighbours();

    }
}
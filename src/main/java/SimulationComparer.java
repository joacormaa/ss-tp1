import java.io.FileNotFoundException;

public class SimulationComparer {
    public static void main(String args[]) throws FileNotFoundException {
        int amount_of_runs = 5;
        int slit_vals = 4;
        double min_slit = 0.15, max_slit=0.25;
        double step = (max_slit-min_slit)/slit_vals;


        for(int i=0; i<slit_vals+1; i++){
            double curr_slit = min_slit + i*step;
            Simulation.setSlitSize(curr_slit);
            for(int j=0; j<amount_of_runs; j++){
                Simulation.setNonce(j);
                Simulation.runSimulation(false);
            }
        }
    }
}

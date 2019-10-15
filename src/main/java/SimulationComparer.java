import java.io.FileNotFoundException;

public class SimulationComparer {
    public static void main(String args[]) throws FileNotFoundException {
        compareGamma();
    }

    public static void compareGamma() throws FileNotFoundException {
        int amount_of_runs = 1;
        int gamma_vals = 0;
        double min_gamma = 500, max_gamma=500;
        double step = (max_gamma-min_gamma)/(gamma_vals+1);

        Simulation.setSlitSize(0);


        for(int i=0; i<gamma_vals+1; i++){
            double curr_gamma = min_gamma + i*step;
            Simulation.setGamma(curr_gamma);
            for(int j=0; j<amount_of_runs; j++){
                Simulation.setNonce(j);
                System.out.printf("Running simulation #%d with Gamma=%f",j,curr_gamma);
                Simulation.runSimulation(false);
            }
        }
    }

    public static void compareSlit() throws FileNotFoundException{
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

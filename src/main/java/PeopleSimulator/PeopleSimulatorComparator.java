package PeopleSimulator;
import Constants.Config;
import Log.Logger;
import main.Main;

public class PeopleSimulatorComparator {
    public static void main(String[] args){
        double max_derail = 5d;
        double min_derail = 1d;
        double derail_step = 0.5d;
        int simulation_runs=25;
        double cur_derail = min_derail;

        Config c = Config.getInstance();
        Logger.loggerInit();

        while (cur_derail<=max_derail){
            Logger.print("\n\nRunning Simulations for derail : '"+cur_derail+"'");
            c.setOUTPUT_PATH("output_"+cur_derail);
            c.setDERAIL(cur_derail);
            for(int i=0; i<simulation_runs; i++){
                Main.runPeopleSimulation(i);
                Logger.print("\nSimulation : '"+i+"' complete");
            }
            cur_derail+=derail_step;
        }
    }
}

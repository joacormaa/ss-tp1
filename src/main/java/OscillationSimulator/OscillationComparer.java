package OscillationSimulator;

import Constants.Config;
import Log.Logger;

import java.util.ArrayList;
import java.util.List;

public class OscillationComparer {

    public void compareAllTypes(){
        Config c = Config.getInstance();
        for(Config.NuMethod method : Config.NuMethod.values()){
            Logger.print("Entering method "+method);
            c.setNUMERIC_METHOD(method);
            OscillationManager manager= new OscillationManager(true);
            while(!manager.stepForward());
        }
    }
}

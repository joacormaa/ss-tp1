import Constants.Config;
import Constants.ConfigSingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetricComparer {

    private Map<Config, List<Long>> times;


    private static MetricComparer instance;

    public static MetricComparer getInstance(){
        if(instance==null){
            instance = new MetricComparer();
        }
        return instance;
    }

    private MetricComparer(){
        times = new HashMap<>();
    }


    public void addTime(Config config, long time){
        List<Long> list = times.getOrDefault(config,new ArrayList<>());
        list.add(time);
        times.put(config,list);
    }

    public void printResults(){
        int i=0;
        for(Config c : times.keySet()){
            System.out.println("---------------------------Config #"+i+++"---------------------------");
            List<Long> list  = times.get(c);
            double average = calculateAverage(list);

            double elapsedTimeInSecond = (double) average / 1_000_000_000;
            System.out.println("\n\n"+c.PrintConfig()+"\n\nAmount of Tests = "+list.size()+"\nAverage time (seconds) ='"+elapsedTimeInSecond + "'\n");
            System.out.println("---------------------------------------------------------------");
        }
    }

    private long calculateAverage(List<Long> list) {
        long tot = 0;
        for(Long l :list){
            tot+=l;
        }
        return tot/ list.size();

    }
}

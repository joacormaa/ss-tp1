import Constants.Config;
import Constants.ConfigSingleton;
import Model.SystemInstant;
import NeighbourLogic.SystemNeighbourManager;
import org.junit.*;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class SystemNeighbourManagerTest {


    private Config config;
    private SystemInstant systemInstant;

    public SystemNeighbourManagerTest(Config config){
        this.config = config;
    }

    @Before
    public void setup(){
        this.systemInstant = new SystemInstant(0);
    }


    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void succeeded(long nanos, Description description) {
            MetricComparer.getInstance().addTime(config,nanos);
        }

        @Override
        protected void failed(long nanos, Throwable e, Description description) {
            System.out.println("Fail.");
        }
    };

    @Test
    public void testCalculateNeighbours(){
        SystemNeighbourManager snm = new SystemNeighbourManager(systemInstant);
        snm.setConfig(this.config);
        snm.calculateNeighbours();
    }

    @AfterClass
    public static void printResults(){
        MetricComparer.getInstance().printResults();
    }

    @Parameterized.Parameters
    public static Collection parameters(){


        List<Config> configList = new ArrayList<>();

        ConfigBuilder cb = new ConfigBuilder();
        configList.add(cb.PARTICLE_QUANTITY(100).make());
        configList.add(cb.PARTICLE_QUANTITY(500).make());
        configList.add(cb.PARTICLE_QUANTITY(1000).make());
        configList.add(cb.PARTICLE_QUANTITY(100).PARTICLE_RC(100).make());
        configList.add(cb.PARTICLE_QUANTITY(500).PARTICLE_RC(100).make());
        configList.add(cb.PARTICLE_QUANTITY(1000).PARTICLE_RC(100).make());


        return AddMultipleTestRunsForConfigList(configList,60);
    }

    private static List<Object[]> AddMultipleTestRunsForConfigList(List<Config> list, int num){
        List<Object[]> ret = new ArrayList<>();
        for (Config c: list) {
            Object[] arr = new Object[]{c};
            for(int i=0; i<num;i++){
                ret.add(arr);
            }
        }
        return ret;
    }
}

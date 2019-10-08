import GrainSimulator.GrainSimulatorHelper;
import Model.Particle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GrainSimulatorHelperTest {

    Particle p1;
    Particle p2;

    @Before
    public void setup(){
        p1 = new Particle(0,0,0,0,0,0,0,0);
    }

    @Test
    public void testNormalVersor11(){
        p2 = new Particle(0,1,1,0,0,0,0,0);
        double[] res = GrainSimulatorHelper.getNormalVersor(p1,p2);

        Assert.assertTrue(res[0]<0);
        Assert.assertTrue(res[1]<0);
    }
    @Test
    public void testNormalVersor1m1(){
        p2 = new Particle(0,1,-1,0,0,0,0,0);
        double[] res = GrainSimulatorHelper.getNormalVersor(p1,p2);

        Assert.assertTrue(res[0]<0);
        Assert.assertTrue(res[1]>0);
    }
    @Test
    public void testNormalVersorm11(){
        p2 = new Particle(0,-1,1,0,0,0,0,0);
        double[] res = GrainSimulatorHelper.getNormalVersor(p1,p2);

        Assert.assertTrue(res[0]>0);
        Assert.assertTrue(res[1]<0);
    }
    @Test
    public void testNormalVersorm1m1(){
        p2 = new Particle(0,-1,-1,0,0,0,0,0);
        double[] res = GrainSimulatorHelper.getNormalVersor(p1,p2);

        Assert.assertTrue(res[0]>0);
        Assert.assertTrue(res[1]>0);
    }

    @Test
    public void testTangentVersor11(){
        p2 = new Particle(0,1,1,0,0,0,0,0);
        double[] res = GrainSimulatorHelper.getTangencialVersor(p1,p2);

        Assert.assertTrue(res[0]>0);
        Assert.assertTrue(res[1]<0);
    }
    @Test
    public void testTangentVersor1m1(){
        p2 = new Particle(0,1,-1,0,0,0,0,0);
        double[] res = GrainSimulatorHelper.getTangencialVersor(p1,p2);

        Assert.assertTrue(res[0]<0);
        Assert.assertTrue(res[1]<0);
    }
    @Test
    public void testTangentVersorm11(){
        p2 = new Particle(0,-1,1,0,0,0,0,0);
        double[] res = GrainSimulatorHelper.getTangencialVersor(p1,p2);

        Assert.assertTrue(res[0]>0);
        Assert.assertTrue(res[1]>0);
    }
    @Test
    public void testTangentVersorm1m1(){
        p2 = new Particle(0,-1,-1,0,0,0,0,0);
        double[] res = GrainSimulatorHelper.getTangencialVersor(p1,p2);

        Assert.assertTrue(res[0]<0);
        Assert.assertTrue(res[1]>0);
    }

    @Test
    public void forceP1ExertsOnP211(){
        p1 = new Particle(0,0,0,0.1,1,Math.PI * 1f/4,0,0);
        p2 = new Particle(0,0.01,0.01,0.1,1,Math.PI * 5f/4,0,0);
        double[] res = GrainSimulatorHelper.getForceP1ExertsOnP2(p1,p2);

        Assert.assertTrue(res[0]>0);
        Assert.assertTrue(res[1]>0);
    }
    @Test
    public void forceP1ExertsOnP21m1(){
        p1 = new Particle(0,0,0,0.1,1,Math.PI * 7f/4,0,0);
        p2 = new Particle(0,0.01,-0.01,0.1,1,Math.PI * 3f/4,0,0);
        double[] res = GrainSimulatorHelper.getForceP1ExertsOnP2(p1,p2);

        Assert.assertTrue(res[0]>0);
        Assert.assertTrue(res[1]<0);
    }
    @Test
    public void forceP1ExertsOnP2m11(){
        p1 = new Particle(0,0,0,0.1,1,Math.PI * 3f/4,0,0);
        p2 = new Particle(0,-0.01,0.01,0.1,1,Math.PI * 7f/4,0,0);
        double[] res = GrainSimulatorHelper.getForceP1ExertsOnP2(p1,p2);

        Assert.assertTrue(res[0]<0);
        Assert.assertTrue(res[1]>0);
    }
    @Test
    public void forceP1ExertsOnP2m1m1(){
        p1 = new Particle(0,0,0,0.1,1,Math.PI * 5f/4,0,0);
        p2 = new Particle(0,-0.01,-0.01,0.1,1,Math.PI * 1f/4,0,0);
        double[] res = GrainSimulatorHelper.getForceP1ExertsOnP2(p1,p2);

        Assert.assertTrue(res[0]<0);
        Assert.assertTrue(res[1]<0);
    }
}

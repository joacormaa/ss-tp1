import GrainSimulator.GrainSimulatorCreator;
import GrainSimulator.GrainSimulatorHelper;
import Model.Particle;
import Model.Vector;
import Model.Wall;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GrainSimulatorHelperTest {

    Particle p1;
    Particle p2;
    Wall hWall;
    Wall vWall;

    @Before
    public void setup(){
        p1 = new Particle(0,0,0,0,0,0,0,0);
        hWall = new Wall(false,0,0,10,0);
        vWall = new Wall(true,0,0,10,0);

    }

    @Test
    public void testWallForce_Left_Vertical(){
        p1 = new Particle(0,-0.01,1,0.1,1,0,0,0);

        Vector res = GrainSimulatorHelper.getForceWallExertsOnP(vWall,p1);

        Assert.assertTrue(res.getX()<0);

    }
    @Test
    public void testWallForce_Right_Vertical(){
        p1 = new Particle(0,0.01,1,0.1,1,Math.PI,0,0);

        Vector res = GrainSimulatorHelper.getForceWallExertsOnP(vWall,p1);

        Assert.assertTrue(res.getX()>0);

    }
    @Test
    public void testWallForce_Top_Horizontal(){
        p1 = new Particle(0,1,0.01,0.1,1,3f/2*Math.PI,0,0);

        Vector res = GrainSimulatorHelper.getForceWallExertsOnP(hWall,p1);

        Assert.assertTrue(res.getY()>0);

    }
    @Test
    public void testWallForce_Bottom_Horizontal(){
        p1 = new Particle(0,1,-0.01,0.1,1,1f/2*Math.PI,0,0);

        Vector res = GrainSimulatorHelper.getForceWallExertsOnP(hWall,p1);

        Assert.assertTrue(res.getY()<0);
    }


    @Test
    public void testNormalVersor11(){
        p2 = new Particle(0,1,1,0,0,0,0,0);
        Vector res = GrainSimulatorHelper.getNormalVersor(p1,p2);

        Assert.assertTrue(res.getX()<0);
        Assert.assertTrue(res.getY()<0);
    }
    @Test
    public void testNormalVersor1m1(){
        p2 = new Particle(0,1,-1,0,0,0,0,0);
        Vector res = GrainSimulatorHelper.getNormalVersor(p1,p2);

        Assert.assertTrue(res.getX()<0);
        Assert.assertTrue(res.getY()>0);
    }
    @Test
    public void testNormalVersorm11(){
        p2 = new Particle(0,-1,1,0,0,0,0,0);
        Vector res = GrainSimulatorHelper.getNormalVersor(p1,p2);

        Assert.assertTrue(res.getX()>0);
        Assert.assertTrue(res.getY()<0);
    }
    @Test
    public void testNormalVersorm1m1(){
        p2 = new Particle(0,-1,-1,0,0,0,0,0);
        Vector res = GrainSimulatorHelper.getNormalVersor(p1,p2);

        Assert.assertTrue(res.getX()>0);
        Assert.assertTrue(res.getY()>0);
    }

    @Test
    public void testTangentVersor11(){
        p2 = new Particle(0,1,1,0,0,0,0,0);
        Vector res = GrainSimulatorHelper.getTangencialVersor(p1,p2);

        Assert.assertTrue(res.getX()>0);
        Assert.assertTrue(res.getY()<0);
    }
    @Test
    public void testTangentVersor1m1(){
        p2 = new Particle(0,1,-1,0,0,0,0,0);
        Vector res = GrainSimulatorHelper.getTangencialVersor(p1,p2);

        Assert.assertTrue(res.getX()<0);
        Assert.assertTrue(res.getY()<0);
    }
    @Test
    public void testTangentVersorm11(){
        p2 = new Particle(0,-1,1,0,0,0,0,0);
        Vector res = GrainSimulatorHelper.getTangencialVersor(p1,p2);

        Assert.assertTrue(res.getX()>0);
        Assert.assertTrue(res.getY()>0);
    }
    @Test
    public void testTangentVersorm1m1(){
        p2 = new Particle(0,-1,-1,0,0,0,0,0);
        Vector res = GrainSimulatorHelper.getTangencialVersor(p1,p2);

        Assert.assertTrue(res.getX()<0);
        Assert.assertTrue(res.getY()>0);
    }

    @Test
    public void forceP1ExertsOnP211(){
        p1 = new Particle(0,0,0,0.1,1,Math.PI * 1f/4,0,0);
        p2 = new Particle(0,0.01,0.01,0.1,1,Math.PI * 5f/4,0,0);
        Vector res = GrainSimulatorHelper.getForceP1ExertsOnP2(p1,p2);

        Assert.assertTrue(res.getX()>0);
        Assert.assertTrue(res.getY()>0);
    }
    @Test
    public void forceP1ExertsOnP21m1(){
        p1 = new Particle(0,0,0,0.1,1,Math.PI * 7f/4,0,0);
        p2 = new Particle(0,0.01,-0.01,0.1,1,Math.PI * 3f/4,0,0);
        Vector res = GrainSimulatorHelper.getForceP1ExertsOnP2(p1,p2);

        Assert.assertTrue(res.getX()>0);
        Assert.assertTrue(res.getY()<0);
    }
    @Test
    public void forceP1ExertsOnP2m11(){
        p1 = new Particle(0,0,0,0.1,1,Math.PI * 3f/4,0,0);
        p2 = new Particle(0,-0.01,0.01,0.1,1,Math.PI * 7f/4,0,0);
        Vector res = GrainSimulatorHelper.getForceP1ExertsOnP2(p1,p2);

        Assert.assertTrue(res.getX()<0);
        Assert.assertTrue(res.getY()>0);
    }
    @Test
    public void forceP1ExertsOnP2m1m1(){
        p1 = new Particle(0,0,0,0.1,1,Math.PI * 5f/4,0,0);
        p2 = new Particle(0,-0.01,-0.01,0.1,1,Math.PI * 1f/4,0,0);
        Vector res = GrainSimulatorHelper.getForceP1ExertsOnP2(p1,p2);

        Assert.assertTrue(res.getX()<0);
        Assert.assertTrue(res.getY()<0);
    }

    @Test
    public void tangentForceP1ExertsOnP211(){
        p1 = new Particle(0,0,0,0.1,0,0,0,0);
        p2 = new Particle(0,0.01,0.01,0.1,1,Math.PI,0,0);
        Vector res = GrainSimulatorHelper.getTangencialForce(p1,p2);

        Assert.assertTrue(res.getX()>0);
    }
    @Test
    public void tangentForceP1ExertsOnP21m1(){
        p1 = new Particle(0,0,0,0.1,0,0,0,0);
        p2 = new Particle(0,0.01,-0.01,0.1,1,Math.PI,0,0);
        Vector res = GrainSimulatorHelper.getTangencialForce(p1,p2);

        Assert.assertTrue(res.getX()>0);
    }
    @Test
    public void tangentForceP1ExertsOnP2m11(){
        p1 = new Particle(0,0,0,0.1,0,0,0,0);
        p2 = new Particle(0,-0.01,0.01,0.1,1,0,0,0);
        Vector res = GrainSimulatorHelper.getTangencialForce(p1,p2);

        Assert.assertTrue(res.getX()<0);
    }
    @Test
    public void tangentForceP1ExertsOnP2m1m1(){
        p1 = new Particle(0,0,0,0.1,0,0,0,0);
        p2 = new Particle(0,-0.01,-0.01,0.1,1,0,0,0);
        Vector res = GrainSimulatorHelper.getTangencialForce(p1,p2);

        Assert.assertTrue(res.getX()<0);
    }


    @Test
    public void headacheTest(){
        p1 = new Particle(0,0.35803729379571025,0.10891005789500094,0.02282671001766542,1.019277569466499,-2.9470793769704664,0.01,0);
        p2 = new Particle(2,0.31472973419663275,0.12792321214382832,0.02447230357007887,1.1888472135703987,-0.5713883219216436,0.01,0);

        Vector tangencialForce = GrainSimulatorHelper.getTangencialForce(p1,p2);
        Assert.assertFalse(tangencialForce.isAcute(new Vector(p2.getXSpeed(),p2.getYSpeed())));
    }
}

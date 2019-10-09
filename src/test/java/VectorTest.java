import Model.Vector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VectorTest {

    Vector testVector;
    Vector otherVector;

    @Before
    public void setup(){
        testVector = new Vector(1,3);
        otherVector = new Vector(2,-1);
    }

    @Test
    public void testSum(){
        Vector res = testVector.sum(otherVector);

        Assert.assertEquals(3, res.getX(), 0.0);
        Assert.assertEquals(2, res.getY(), 0.0);
    }
    @Test
    public void testMinus(){
        Vector res = testVector.minus(otherVector);

        Assert.assertEquals(-1, res.getX(), 0.0);
        Assert.assertEquals(4, res.getY(), 0.0);

    }
    @Test
    public void testMultiply(){
        Vector res = testVector.multiplyBy(1f/4);

        Assert.assertEquals(1f/4, res.getX(),0.0001);
        Assert.assertEquals(3f/4, res.getY(),0.0001);
    }
    @Test
    public void testDot(){
        double res = testVector.dot(otherVector);

        Assert.assertEquals(-1,res, 0.0);

    }
    @Test
    public void testProj(){
        Vector res = testVector.getProyection(otherVector);

        Assert.assertEquals(-1f/10, res.getX(),0.0001);
        Assert.assertEquals(-3f/10, res.getY(),0.0001);
    }
    @Test
    public void testNorm(){
        double res = testVector.norm();

        Assert.assertEquals(Math.sqrt(10),res, 0.0001);
    }
    @Test
    public void testOrtho(){
        Vector res = testVector.ortho();

        Assert.assertEquals(-3, res.getX(),0.0001);
        Assert.assertEquals(1, res.getY(),0.0001);

    }
    @Test
    public void testVersor(){
        Vector res = testVector.versor();

        Assert.assertEquals(1f/Math.sqrt(10), res.getX(),0.0001);
        Assert.assertEquals(3f/Math.sqrt(10), res.getY(),0.0001);

    }
    @Test
    public void testAngle(){
        double res = testVector.getAngle();

        Assert.assertEquals(1.2490,res, 0.0001);
    }
    @Test
    public void testIsAcute(){
        otherVector = new Vector(2,3);
        Assert.assertTrue(testVector.isAcute(otherVector));
    }
    @Test
    public void testIsAcute_NonAcute(){
        otherVector = new Vector(-1,-3);
        Assert.assertFalse(testVector.isAcute(otherVector));
    }
}

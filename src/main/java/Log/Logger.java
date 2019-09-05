package Log;

import Model.Particle;
import Model.StaticParticle;

public final class Logger {
    private Logger(){}
    public static void print(String s){
        System.out.println(s);
    }
    public static void print(Particle p){
        String s = "ID:" + p.getId() + " X:" + p.getX() + " Y:" + p.getY()+ " Vx:" + p.getXSpeed() + " Vy:" + p.getYSpeed();
        System.out.println(s);
    }
    public static void print(Particle p, Particle newP){
        String s = "Last - ID:" + p.getId() + " X:" + p.getX() + " Y:" + p.getY() + " Vx:" + p.getXSpeed() + " Vy:" + p.getYSpeed();
        String newS = "New - ID:" + newP.getId() + " X:" + newP.getX() + " Y:" + newP.getY() + " Vx:" + newP.getXSpeed() + " Vy:" + newP.getYSpeed();
        System.out.println(s);
        System.out.println(newS);
    }
    public static void printPolar(Particle p, Particle newP){
        String s = "Last - ID:" + p.getId() + " X:" + p.getX() + " Y:" + p.getY() + " V:" + p.getSpeed() + " Angle:" + Math.toDegrees(p.getAngle());
        String newS = "New - ID:" + newP.getId() + " X:" + newP.getX() + " Y:" + newP.getY() + " V:" + newP.getSpeed() + " Angle:" + Math.toDegrees(newP.getAngle());
        System.out.println(s);
        System.out.println(newS);
    }
}

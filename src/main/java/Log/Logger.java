package Log;

import Constants.Config;
import Model.Particle;
import Model.StaticParticle;

public final class Logger {
    private static boolean log;
    public static void loggerInit(){
        log = Config.getInstance().LOG_ON();
    }
    public static void print(String s){
        if(log){
            System.out.println(s);
        }
    }
    public static void print(Particle p){
        if(log){
            String s = "ID:" + p.getId() + " X:" + p.getX() + " Y:" + p.getY()+ " Vx:" + p.getXSpeed() + " Vy:" + p.getYSpeed();
            System.out.println(s);

        }
    }
    public static void print(Particle p, Particle newP){
        if(log){
            String s = "Last - ID:" + p.getId() + " X:" + p.getX() + " Y:" + p.getY() + " Vx:" + p.getXSpeed() + " Vy:" + p.getYSpeed();
            String newS = "New - ID:" + newP.getId() + " X:" + newP.getX() + " Y:" + newP.getY() + " Vx:" + newP.getXSpeed() + " Vy:" + newP.getYSpeed();
            System.out.println(s);
            System.out.println(newS);
        }
    }
    public static void printPolar(Particle p, Particle newP){
        if(log){
            String s = "Last - ID:" + p.getId() + " X:" + p.getX() + " Y:" + p.getY() + " V:" + p.getSpeed() + " Angle:" + Math.toDegrees(p.getAngle());
            String newS = "New - ID:" + newP.getId() + " X:" + newP.getX() + " Y:" + newP.getY() + " V:" + newP.getSpeed() + " Angle:" + Math.toDegrees(newP.getAngle());
            System.out.println(s);
            System.out.println(newS);
        }
    }
}

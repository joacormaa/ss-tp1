package NeighbourLogic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Helper {

    public static int getModule(int number, int mod) {
        int num = number%mod;
        if(num < 0) num = mod+num;
        return num;
    }

    public static boolean AngleIsRadians(double angle) {
        return -Math.PI<= angle && angle <= Math.PI;
    }

    public static double getModule(double number, double mod) {
        double num = number%mod;
        if(num < 0) num = mod+num;
        return num;
    }

    public static void resetFile(String str){
        File f = new File(str);
        if(f.exists())
            f.delete();

        File parent = f.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }

        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendToFile(String str, String path) {
        try {
            Files.write(Paths.get(path), str.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

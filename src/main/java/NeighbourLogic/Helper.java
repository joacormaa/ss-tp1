package NeighbourLogic;

public class Helper {

    public static int getModule(int number, int mod) {
        int num = number%mod;
        if(num < 0) num = mod+num;
        return num;
    }

    public static double getModule(double number, double mod) {
        double num = number%mod;
        if(num < 0) num = mod+num;
        return num;
    }
}

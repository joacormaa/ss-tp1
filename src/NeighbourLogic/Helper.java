package NeighbourLogic;

class Helper {

    static int getModule(int number, int mod) {
        int num = number%mod;
        if(num < 0) num = mod+num;
        return num;
    }
}

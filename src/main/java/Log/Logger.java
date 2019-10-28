package Log;

import Constants.Config;

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
}

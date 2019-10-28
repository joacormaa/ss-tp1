package Log;

import Constants.Config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Logger {
    private static boolean log;
    public static void loggerInit(){
        log = Config.getInstance().LOG_ON();
    }
    public static void print(String s){
        if(log){
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            System.out.println(date +": "+s);
        }
    }
}

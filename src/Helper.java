public class Helper {
    public Helper() {

    }

    public static int getModule(int number, int mod) {
        if(number < 0) return number + mod;
        return number % mod;
    }
}

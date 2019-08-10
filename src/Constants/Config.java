package Constants;

public class Config {
    private double SYSTEM_LENGTH = 100;
    private int PARTICLES_QUANTITY = 200;
    private double PARTICLE_RADIUS = 1;
    private double PARTICLE_RC = 1;
    private String OUTPUT_PATH = "output.txt";
    private double PARTICLE_INFLUENCE_RADIUS;
    private int CELL_AMOUNT;
    private double CELL_LENGTH;

    private static Config instance;

    public static Config getInstance(){
        if(instance==null){
            instance=new Config();
        }
        return instance;
    }

    private Config(){
        PARTICLE_INFLUENCE_RADIUS = 2*PARTICLE_RADIUS+PARTICLE_RC;
        CELL_AMOUNT = (int)Math.floor(SYSTEM_LENGTH/PARTICLE_INFLUENCE_RADIUS);
        CELL_LENGTH = SYSTEM_LENGTH/CELL_AMOUNT;
    }

    public double SYSTEM_LENGTH(){
        return SYSTEM_LENGTH;
    }

    public int PARTICLES_QUANTITY() {
        return PARTICLES_QUANTITY;
    }

    public double PARTICLE_RADIUS() {
        return PARTICLE_RADIUS;
    }

    public double PARTICLE_RC() {
        return PARTICLE_RC;
    }

    public String OUTPUT_PATH() {
        return OUTPUT_PATH;
    }

    public double PARTICLE_INFLUENCE_RADIUS() {
        return PARTICLE_INFLUENCE_RADIUS;
    }

    public int CELL_AMOUNT() {
        return CELL_AMOUNT;
    }

    public double CELL_LENGTH() {
        return CELL_LENGTH;
    }
}

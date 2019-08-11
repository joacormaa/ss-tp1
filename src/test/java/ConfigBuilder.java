import Constants.Config;

public class ConfigBuilder {
    private double SYSTEM_LENGTH;
    private int PARTICLE_QUANTITY;
    private double PARTICLE_RADIUS;
    private double PARTICLE_RC;
    private String OUTPUT_PATH;

    private double PARTICLE_INFLUENCE_RADIUS;
    private int CELL_AMOUNT;
    private double CELL_LENGTH;

    public ConfigBuilder() {
        SYSTEM_LENGTH = 100;
        PARTICLE_QUANTITY = 100;
        PARTICLE_RADIUS = 1;
        PARTICLE_RC = 1;
        OUTPUT_PATH = "output.txt";
    }

    public ConfigBuilder SYSTEM_LENGTH(double SYSTEM_LENGTH) {
        this.SYSTEM_LENGTH = SYSTEM_LENGTH;
        return this;
    }

    public ConfigBuilder PARTICLE_QUANTITY(int PARTICLE_QUANTITY) {
        this.PARTICLE_QUANTITY = PARTICLE_QUANTITY;
        return this;
    }

    public ConfigBuilder PARTICLE_RADIUS(double PARTICLE_RADIUS) {
        this.PARTICLE_RADIUS = PARTICLE_RADIUS;
        return this;
    }

    public ConfigBuilder PARTICLE_RC(double PARTICLE_RC) {
        this.PARTICLE_RC = PARTICLE_RC;
        return this;
    }

    public ConfigBuilder OUTPUT_PATH(String OUTPUT_PATH) {
        this.OUTPUT_PATH = OUTPUT_PATH;
        return this;
    }

    public Config make() {

        PARTICLE_INFLUENCE_RADIUS = 2 * PARTICLE_RADIUS + PARTICLE_RC;
        CELL_AMOUNT = (int) Math.floor(SYSTEM_LENGTH / PARTICLE_INFLUENCE_RADIUS);
        if (CELL_AMOUNT == 0) CELL_AMOUNT = 1;
        CELL_LENGTH = SYSTEM_LENGTH / CELL_AMOUNT;

        return new Config() {
            private double SYSTEM_LENGTH_VAL=SYSTEM_LENGTH;
            private int PARTICLE_QUANTITY_VAL=PARTICLE_QUANTITY;
            private double PARTICLE_RADIUS_VAL=PARTICLE_RADIUS;
            private double PARTICLE_RC_VAL=PARTICLE_RC;
            private String OUTPUT_PATH_VAL=OUTPUT_PATH;

            private double PARTICLE_INFLUENCE_RADIUS_VAL=PARTICLE_INFLUENCE_RADIUS;
            private int CELL_AMOUNT_VAL=CELL_AMOUNT;
            private double CELL_LENGTH_VAL=CELL_LENGTH;
            @Override
            public double SYSTEM_LENGTH() {
                return SYSTEM_LENGTH_VAL;
            }

            @Override
            public int PARTICLES_QUANTITY() {
                return PARTICLE_QUANTITY_VAL;
            }

            @Override
            public double PARTICLE_RADIUS() {
                return PARTICLE_RADIUS_VAL;
            }

            @Override
            public double PARTICLE_RC() {
                return PARTICLE_RC_VAL;
            }

            @Override
            public String OUTPUT_PATH() {
                return OUTPUT_PATH_VAL;
            }

            @Override
            public double PARTICLE_INFLUENCE_RADIUS() {
                return PARTICLE_INFLUENCE_RADIUS_VAL;
            }

            @Override
            public int CELL_AMOUNT() {
                return CELL_AMOUNT_VAL;
            }

            @Override
            public double CELL_LENGTH() {
                return CELL_LENGTH_VAL;
            }
        };
    }
}

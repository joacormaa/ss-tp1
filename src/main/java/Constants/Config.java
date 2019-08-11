package Constants;

public interface Config {

    double SYSTEM_LENGTH();

    int PARTICLES_QUANTITY();

    double PARTICLE_RADIUS();

    double PARTICLE_RC();

    String OUTPUT_PATH();

    double PARTICLE_INFLUENCE_RADIUS();

    int CELL_AMOUNT();

    double CELL_LENGTH();

    default String PrintConfig(){
      StringBuilder sb = new StringBuilder();
      sb.append("System Length '").append(SYSTEM_LENGTH()).append("'\nParticle Quantity '").append(PARTICLES_QUANTITY())
              .append("'\nCell Amount '").append(CELL_AMOUNT()).append("'\n");

      return  sb.toString();
    };
}

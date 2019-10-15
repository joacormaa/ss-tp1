import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Simulation {

    private static final int    BASE = 1;                       // DT base
    private static final int    EXP = 5;                        // DT exp
    private static final double DT = BASE * Math.pow(10, -EXP); // Step delta time
    private static final int    N = 140;                        // Number of particles
    private static final double G = -10;                        // Gravity on 'y' axis
    private static final double WIDTH = 0.4;
    private static final double HEIGHT = 1.5;
    private static double SLIT_SIZE = 0;
    private static final double k = 10e5;
    private static double gamma = 70;
    private static final double MIN_PARTICLE_R = 0.02;          // Min particle radius
    private static final double MAX_PARTICLE_R = 0.03;         // Max particle radius
    private static final double STEP_PRINT_DT = 0.1;
    private static final double ANIMATION_DT = 1.0 / 60;          // DT to save a simulation state
    private static final double MEASURE_DT = 1.0 / 10;                // DT to save a simulation state
    private static final double MAX_SIM_TIME = 10;             // Max simulation time in seconds
    private static int nonce =0;


    private static double              simTime = 0; //Simulation time in seconds
    private static List<Particle> particles = new ArrayList<>(N);
    private static ArrayList<Wall>     walls = new ArrayList<>(4);

    private static List<List<Particle>> savedStates = new ArrayList<>();
    private static Map<Double,Double> kineticEnergy = new HashMap<>();


    public static void main(String[] args) throws FileNotFoundException{
        runSimulation(true);
    }


    public static void runSimulation(boolean printState) throws FileNotFoundException {
        resetValues();
        System.out.println(String.format("N: %d", N));
        PrintWriter writer = null;

        if(printState)
            writer = new PrintWriter("data/" + SLIT_SIZE + "_"+ gamma + "_" + nonce + "_simulation.xyz");

        initWalls(WIDTH, HEIGHT, SLIT_SIZE);
        initParticles(N, WIDTH, HEIGHT, MIN_PARTICLE_R, MAX_PARTICLE_R);

        saveMeasures();

        if(printState)
            writeState(writer);

        int lastFrame = 1, lastMeasure = 1, lastStepPrint = 0;
        System.out.println("Starting simulation");

        List<Particle> outParticles;
        List<Double> exitTimes = new LinkedList<>();

        while(simTime < MAX_SIM_TIME) {
            // Clear forces and add interaction forces with walls to particles and add G force too
            particles.parallelStream().forEach(p -> {
                p.clearForces();
                p.fy += p.m * G;
                for (Wall w : walls) {
                    if (w.getOverlap(p) > 0) {
                        applyForce(w, p);
                    }
                }
            });

            // Add interaction forces between particles
            IntStream.range(0, particles.size()).forEach(i -> {
                Particle pi = particles.get(i);

                for (int j = i + 1; j < particles.size(); j++) {
                    Particle pj = particles.get(j);

                    if (pi.getOverlap(pj) > 0) {
                        applyForce(pi, pj);
                    }
                }
            });

            // Move particles a DT time and filter the ones that are out
            outParticles = particles.parallelStream().peek(p -> {
                p.move(DT);
            }).filter(Simulation::isOut).collect(Collectors.toList());

            // Get all in particles
            //particles = particles.stream().parallel().filter(Simulation::isIn).collect(Collectors.toList());

            // Add DT to simulation time
            simTime += DT;

            // Record exit times
            outParticles.forEach((p) -> exitTimes.add(simTime));

            // For each out particle reinsert it on top comparing to in particles
            outParticles.forEach(Simulation::reinsert);

            if (simTime / STEP_PRINT_DT > lastStepPrint) {
                System.out.println(String.format("simTime: %.2f", simTime));
                lastStepPrint++;
            }

            if (printState && simTime / ANIMATION_DT > lastFrame) {
                writeState(writer);
                lastFrame++;
            }

            if (simTime / MEASURE_DT > lastMeasure) {
                saveMeasures();
                lastMeasure++;
            }
        }
        saveMeasures();
        System.out.println("Finished simulation");

        if(printState)
            writer.close();

        System.out.println("Printing measures");
        System.out.println(String.format("Reinserted particles: %d", exitTimes.size()));

        printKE(kineticEnergy, "data/" + SLIT_SIZE + "_"+ gamma + "_"  + nonce + "_kineticEnergy.csv");
        printList(exitTimes, "data/" + SLIT_SIZE + "_"+ gamma + "_"  + nonce + "_exitTimes.csv");
    }

    private static void resetValues() {
        simTime = 0; //Simulation time in seconds
        particles = new ArrayList<>(N);
        walls = new ArrayList<>(4);
        savedStates = new ArrayList<>();
        kineticEnergy = new HashMap<>();

    }

    private static void reinsert(Particle p) {
        p.clearVelocities();

        boolean valid = false;
        while (!valid) {
            p.x = p.r + Math.random() * (WIDTH - 2 * p.r);
            p.y = (2 + Math.random()) * HEIGHT / 3;
            valid = particles.parallelStream().allMatch(p2 -> p2 == p || p2.getOverlap(p) == 0);
        }
        //particles.add(p);
    }

    private static boolean isOut(Particle p) {
        return p.y <= - HEIGHT / 10;
    }

    private static boolean isIn(Particle p) {
        return !isOut(p);
    }

    private static void applyForce(Particle p1, Particle p2) {

        double enx = p1.enx(p2);
        double eny = p1.eny(p2);

        double normalRelVel = p1.getNormalRelVel(p2);

        double overlap = p1.getOverlap(p2);

        double fn = -k*overlap - gamma*normalRelVel;

        double fx = fn * enx;
        double fy = fn * eny;

        double fn_mod = Math.abs(fn);
        p1.fn += fn_mod;
        p1.fx += fx;
        p1.fy += fy;
        p2.fn += fn_mod;
        p2.fx -= fx;
        p2.fy -= fy;
    }

    private static void applyForce(Wall w, Particle p) {

        double normalRelVel = w.getNormalRelVel(p);

        double overlap = w.getOverlap(p);

        double fn = -k*overlap - gamma*normalRelVel;

        double fx = fn * w.enx(p);
        double fy = fn * w.eny(p);

        p.fn += Math.abs(fn);
        p.fx += fx;
        p.fy += fy;
    }

    private static void initWalls(double width, double height, double slitSize) {
        walls.add(new Wall(0, 0, 0, height, -1, 0));
        walls.add(new Wall(width, 0, width, height, 1, 0));
        if(slitSize == 0) {
            walls.add(new Wall(0, 0, width, 0, 0, -1));
        }else{
            double bottomWallWidth = (width - slitSize) / 2;
            walls.add(new Wall(0, 0, bottomWallWidth, 0, 0, -1));
            walls.add(new Wall(width - bottomWallWidth, 0, width, 0, 0, -1));
        }
    }

    private static void initParticles(int n, double width, double height, double minRadius, double maxRadius) {
        while (particles.size() < n) {
            double particleRadius = minRadius + Math.random() * (maxRadius - minRadius);
            double x = particleRadius + Math.random() * (width - 2 * particleRadius);
            double y = particleRadius + Math.random() * (height - 2 * particleRadius);

            Particle newParticle = new Particle(particles.size(), x, y, particleRadius);

            boolean valid = particles.stream().parallel().allMatch(p -> p.getOverlap(newParticle) == 0);

            if (valid) {
                particles.add(newParticle);
            }
        }
    }

    private static void printList(List<Double> list, String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename);
            list.forEach(writer::println);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void printKE(Map<Double,Double> ke, String filename){
        try {
            PrintWriter writer = new PrintWriter(filename);
            String keOverTime = getKeOverTime(ke);
            writer.print(keOverTime);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String getKeOverTime(Map<Double,Double> ke) {
        StringBuilder sb = new StringBuilder();
        sb.append("times,ke\n");
        for(Map.Entry<Double,Double> entry : ke.entrySet()){
            sb.append(entry.getKey()).append(',').append(entry.getValue()).append('\n');
        }
        return sb.toString();
    }

    private static void saveMeasures() {
        kineticEnergy.put(simTime,particles.parallelStream().map(Particle::kineticEnergy).reduce(0.0, (d1, d2) -> d1 + d2));
    }

    static int ppw =1000;

    private static void writeState(PrintWriter writer) {
        writer.println(particles.size() + ppw*walls.size());
        writer.println();
        printWalls(writer);
        particles.stream().parallel().forEach(writer::println);
    }

    private static void printWalls(PrintWriter writer) {
        int i=0;
        for(Wall w : walls){
            StringBuilder sb = new StringBuilder();
            double stepx = (w.finalX-w.initialX)/ppw;
            double stepy = (w.finalY-w.initialY)/ppw;
            for(int j=0; j<ppw; j++){
                sb.append(-(i*ppw+j)).append(' ');
                sb.append(w.initialX+j*stepx).append(' ');
                sb.append(w.initialY+j*stepy).append(' ');
                sb.append(0).append(' ');
                sb.append(0).append(" \n");
            }
            writer.print(sb.toString());
        }
    }

    public static void setSlitSize(double slitSize){
        SLIT_SIZE = slitSize;
    }

    public static void setNonce(int n){
        nonce=n;
    }

    public static void setGamma(double curr_gamma) {
        gamma=curr_gamma;
    }
}

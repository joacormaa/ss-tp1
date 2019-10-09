package Constants;

import NeighbourLogic.Helper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

public class Config {
    private double SIMULATION_DELTA_TIME;
    private double PRINT_TIME;
    private int PARTICLES_PER_WALL;
    private boolean LOG_ON;

    private int OSCILLATOR_K;
    private int OSCILLATOR_G;
    private double OSCILLATOR_A;

    private double OSCILLATOR_INITIAL_POSITION;

    private double ALPHA_0_OSCILLATOR;
    private double ALPHA_0_GAS;
    private double ALPHA_1;
    private double ALPHA_2;
    private double ALPHA_3;
    private double ALPHA_4;
    private double ALPHA_5;

    private double SIGMA;
    private double RM;
    private double EPSILON;

    private NuMethod NUMERIC_METHOD;

    private int PARTICLE_QUANTITY;
    private double PARTICLE_RADIUS;
    private String OUTPUT_PATH;
    private int CELL_AMOUNT;
    private double CELL_LENGTH;
    private double PARTICLE_SPEED;
    private double NOISE_COEFFICIENT;
    private int AMOUNT_OF_FRAMES;

    private double HORIZONTAL_WALL_LENGTH;
    private double VERTICAL_WALL_LENGTH;
    private double HOLE_LENGTH;
    private double HOLE_POSITION;
    private boolean INNER_WALL;

    private double WALL_WIDTH;
    private double PARTICLE_MASS;

    private double HORIZONTAL_WALL_HOLE_LENGTH;
    private double KN;
    private double KT;
    private double GAMMA;

    private double OFFSET;

    private static Config instance;

    public static Config getInstance(){
        if(instance==null){
            instance=new Config();
        }
        return instance;
    }

    private Config() {

        try{
            recoverValuesFromXML();
        }
        catch (ParserConfigurationException | IOException |SAXException e){
            System.err.println("Invalid XML file. '"+e.getMessage()+"'");
            exit(-1);
        }

        if(!Helper.AngleIsRadians(this.NOISE_COEFFICIENT));
        CELL_AMOUNT = (int)Math.floor(VERTICAL_WALL_LENGTH/0.03); //deshardcodear
        if(CELL_AMOUNT==0)CELL_AMOUNT=1;
        CELL_LENGTH = VERTICAL_WALL_LENGTH/CELL_AMOUNT;
    }

    private void recoverValuesFromXML() throws ParserConfigurationException, IOException, SAXException {
        File file = new File("config.xml");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        String OSCILLATOR_K = document.getElementsByTagName("OSCILLATOR_K").item(0).getTextContent();
        String OSCILLATOR_G = document.getElementsByTagName("OSCILLATOR_G").item(0).getTextContent();
        String OSCILLATOR_A = document.getElementsByTagName("OSCILLATOR_A").item(0).getTextContent();
        String OSCILLATOR_INITIAL_POSITION = document.getElementsByTagName("OSCILLATOR_INITIAL_POSITION").item(0).getTextContent();
        String ALPHA_0_OSCILLATOR = document.getElementsByTagName("ALPHA_0_OSCILLATOR").item(0).getTextContent();
        String ALPHA_0_GAS = document.getElementsByTagName("ALPHA_0_GAS").item(0).getTextContent();
        String ALPHA_1 = document.getElementsByTagName("ALPHA_1").item(0).getTextContent();
        String ALPHA_2 = document.getElementsByTagName("ALPHA_2").item(0).getTextContent();
        String ALPHA_3 = document.getElementsByTagName("ALPHA_3").item(0).getTextContent();
        String ALPHA_4 = document.getElementsByTagName("ALPHA_4").item(0).getTextContent();
        String ALPHA_5 = document.getElementsByTagName("ALPHA_5").item(0).getTextContent();

        String methodString = document.getElementsByTagName("NUMERIC_METHOD").item(0).getTextContent();

        String PARTICLE_QUANTITY_STR = document.getElementsByTagName("PARTICLE_QUANTITY").item(0).getTextContent();
        String PARTICLE_RADIUS_STR = document.getElementsByTagName("PARTICLE_RADIUS").item(0).getTextContent();
        String PARTICLE_SPEED_STR = document.getElementsByTagName("PARTICLE_SPEED").item(0).getTextContent();
        String AMOUNT_OF_FRAMES_STR = document.getElementsByTagName("AMOUNT_OF_FRAMES").item(0).getTextContent();
        String MAX_NOISE_STR = document.getElementsByTagName("NOISE_COEFFICIENT").item(0).getTextContent();

        String HORIZONTAL_WALL_LENGTH = document.getElementsByTagName("HORIZONTAL_WALL_LENGTH").item(0).getTextContent();
        String VERTICAL_WALL_LENGTH = document.getElementsByTagName("VERTICAL_WALL_LENGTH").item(0).getTextContent();
        String HOLE_LENGTH = document.getElementsByTagName("HOLE_LENGTH").item(0).getTextContent();
        String HOLE_POSITION = document.getElementsByTagName("HOLE_POSITION").item(0).getTextContent();
        String WALL_WIDTH = document.getElementsByTagName("WALL_WIDTH").item(0).getTextContent();
        String PARTICLE_MASS = document.getElementsByTagName("PARTICLE_MASS").item(0).getTextContent();

        String SIMULATION_DELTA_TIME = document.getElementsByTagName("SIMULATION_DELTA_TIME").item(0).getTextContent();
        String PRINT_TIME = document.getElementsByTagName("PRINT_TIME").item(0).getTextContent();
        String PARTICLES_PER_WALL = document.getElementsByTagName("PARTICLES_PER_WALL").item(0).getTextContent();
        String LOG_ON = document.getElementsByTagName("LOG_ON").item(0).getTextContent();
        String INNER_WALL = document.getElementsByTagName("INNER_WALL").item(0).getTextContent();

        String RM = document.getElementsByTagName("RM").item(0).getTextContent();
        String EPSILON = document.getElementsByTagName("EPSILON").item(0).getTextContent();
        String SIGMA = document.getElementsByTagName("SIGMA").item(0).getTextContent();

        String HORIZONTAL_WALL_HOLE_LENGTH = document.getElementsByTagName("HORIZONTAL_WALL_HOLE_LENGTH").item(0).getTextContent();
        String KN = document.getElementsByTagName("KN").item(0).getTextContent();
        String KT = document.getElementsByTagName("KT").item(0).getTextContent();
        String GAMMA = document.getElementsByTagName("GAMMA").item(0).getTextContent();
        String OFFSET = document.getElementsByTagName("OFFSET").item(0).getTextContent();

        this.OUTPUT_PATH = document.getElementsByTagName("OUTPUT_PATH").item(0).getTextContent();
        this.PARTICLE_QUANTITY = Integer.parseInt(PARTICLE_QUANTITY_STR);
        this.PARTICLE_RADIUS = Double.parseDouble(PARTICLE_RADIUS_STR);
        this.PARTICLE_SPEED = Double.parseDouble(PARTICLE_SPEED_STR);
        this.AMOUNT_OF_FRAMES = Integer.parseInt(AMOUNT_OF_FRAMES_STR);
        this.NOISE_COEFFICIENT = Double.parseDouble(MAX_NOISE_STR);

        this.HORIZONTAL_WALL_LENGTH = Double.parseDouble(HORIZONTAL_WALL_LENGTH);
        this.VERTICAL_WALL_LENGTH = Double.parseDouble(VERTICAL_WALL_LENGTH);
        this.HOLE_LENGTH = Double.parseDouble(HOLE_LENGTH);
        this.HOLE_POSITION = Double.parseDouble(HOLE_POSITION);
        this.WALL_WIDTH = Double.parseDouble(WALL_WIDTH);
        this.PARTICLE_MASS = Double.parseDouble(PARTICLE_MASS);

        this.SIMULATION_DELTA_TIME = Double.parseDouble(SIMULATION_DELTA_TIME);
        this.PRINT_TIME = Double.parseDouble(PRINT_TIME);
        this.PARTICLES_PER_WALL = Integer.parseInt(PARTICLES_PER_WALL);
        this.LOG_ON = Boolean.parseBoolean(LOG_ON);
        this.INNER_WALL = Boolean.parseBoolean(INNER_WALL);

        this.OSCILLATOR_K = Integer.parseInt(OSCILLATOR_K);
        this.OSCILLATOR_G = Integer.parseInt(OSCILLATOR_G);
        this.OSCILLATOR_A = Double.parseDouble(OSCILLATOR_A);
        this.OSCILLATOR_INITIAL_POSITION = Double.parseDouble(OSCILLATOR_INITIAL_POSITION);
        this.ALPHA_0_OSCILLATOR = Double.parseDouble(ALPHA_0_OSCILLATOR);
        this.ALPHA_0_GAS = Double.parseDouble(ALPHA_0_GAS);
        this.ALPHA_1 = Double.parseDouble(ALPHA_1);
        this.ALPHA_2 = Double.parseDouble(ALPHA_2);
        this.ALPHA_3 = Double.parseDouble(ALPHA_3);
        this.ALPHA_4 = Double.parseDouble(ALPHA_4);
        this.ALPHA_5 = Double.parseDouble(ALPHA_5);

        this.NUMERIC_METHOD = NuMethod.valueOf(methodString);

        this.RM = Double.parseDouble(RM);
        this.EPSILON = Double.parseDouble(EPSILON);
        this.SIGMA = Double.parseDouble(SIGMA);

        this.HORIZONTAL_WALL_HOLE_LENGTH = Double.parseDouble(HORIZONTAL_WALL_HOLE_LENGTH);
        this.KN = Double.parseDouble(KN);
        this.KT = Double.parseDouble(KT);
        this.GAMMA = Double.parseDouble(GAMMA);
        this.OFFSET = Double.parseDouble(OFFSET);
    }

    public enum NuMethod{
        BEEMAN,
        GPCO5,
        ANALYTICAL,
        VERLET
    }

    public double PARTICLE_SPEED(){return PARTICLE_SPEED;}

    public Integer PARTICLES_QUANTITY() {
        return PARTICLE_QUANTITY;
    }

    public double PARTICLE_RADIUS() {
        return PARTICLE_RADIUS;
    }

    public String OUTPUT_PATH() {
        return OUTPUT_PATH;
    }

    public int CELL_AMOUNT() {
        return CELL_AMOUNT;
    }

    public double CELL_LENGTH() {
        return CELL_LENGTH;
    }

    public double NOISE_COEFFICIENT() {
        return NOISE_COEFFICIENT;
    }

    public double AMOUNT_OF_FRAMES() {
        return AMOUNT_OF_FRAMES;
    }

    public int OSCILLATOR_K() {
        return OSCILLATOR_K;
    }

    public void setOSCILLATOR_K(int OSCILLATOR_K) {
        this.OSCILLATOR_K = OSCILLATOR_K;
    }

    public int OSCILLATOR_G() {
        return OSCILLATOR_G;
    }

    public void setOSCILLATOR_G(int OSCILLATOR_G) {
        this.OSCILLATOR_G = OSCILLATOR_G;
    }

    public double OSCILLATOR_A() {
        return OSCILLATOR_A;
    }

    public void setOSCILLATOR_A(double OSCILLATOR_A) {
        this.OSCILLATOR_A = OSCILLATOR_A;
    }

    public double OSCILLATOR_INITIAL_POSITION() {
        return OSCILLATOR_INITIAL_POSITION;
    }

    public void setOSCILLATOR_INITIAL_POSITION(double OSCILLATOR_INITIAL_POSITION) {
        this.OSCILLATOR_INITIAL_POSITION = OSCILLATOR_INITIAL_POSITION;
    }

    public double ALPHA_0_OSCILLATOR() {
        return ALPHA_0_OSCILLATOR;
    }

    public void setALPHA_0_OSCILLATOR(double ALPHA_0_OSCILLATOR) {
        this.ALPHA_0_OSCILLATOR = ALPHA_0_OSCILLATOR;
    }

    public double ALPHA_0_GAS() {
        return ALPHA_0_GAS;
    }

    public void setALPHA_0_GAS(double ALPHA_0_GAS) {
        this.ALPHA_0_GAS = ALPHA_0_GAS;
    }

    public double ALPHA_1() {
        return ALPHA_1;
    }

    public void setALPHA_1(double ALPHA_1) {
        this.ALPHA_1 = ALPHA_1;
    }

    public double ALPHA_2() {
        return ALPHA_2;
    }

    public void setALPHA_2(double ALPHA_2) {
        this.ALPHA_2 = ALPHA_2;
    }

    public double ALPHA_3() {
        return ALPHA_3;
    }

    public void setALPHA_3(double ALPHA_3) {
        this.ALPHA_3 = ALPHA_3;
    }

    public double ALPHA_4() {
        return ALPHA_4;
    }

    public void setALPHA_4(double ALPHA_4) {
        this.ALPHA_4 = ALPHA_4;
    }

    public double ALPHA_5() {
        return ALPHA_5;
    }

    public void setALPHA_5(double ALPHA_5) {
        this.ALPHA_5 = ALPHA_5;
    }

    public NuMethod NUMERIC_METHOD() {
        return NUMERIC_METHOD;
    }

    public void setNUMERIC_METHOD(NuMethod NUMERIC_METHOD) {
        this.NUMERIC_METHOD = NUMERIC_METHOD;
    }

    public double SIMULATION_DELTA_TIME() {
        return SIMULATION_DELTA_TIME;
    }

    public void setSIMULATION_DELTA_TIME(double SIMULATION_DELTA_TIME) {
        this.SIMULATION_DELTA_TIME = SIMULATION_DELTA_TIME;
    }


    public double HORIZONTAL_WALL_LENGTH(){return HORIZONTAL_WALL_LENGTH;}

    public double VERTICAL_WALL_LENGTH(){return VERTICAL_WALL_LENGTH;}

    public double HOLE_LENGTH(){return HOLE_LENGTH;}

    public double HOLE_POSITION(){return HOLE_POSITION;}

    public void setNoiseCoefficient(double noiseCoefficient){
        this.NOISE_COEFFICIENT=noiseCoefficient;
    }

    public void setAmountOfParticles(int amountOfParticles){
        this.PARTICLE_QUANTITY = amountOfParticles;
    }

    public double WALL_WIDTH() {
        return WALL_WIDTH;
    }

    public double PRINT_TIME() {
        return PRINT_TIME;
    }

    public double PARTICLE_MASS() {return PARTICLE_MASS;}

    public int PARTICLES_PER_WALL() {return PARTICLES_PER_WALL;}

    public boolean LOG_ON() {
        return LOG_ON;
    }

    public boolean INNER_WALL(){return INNER_WALL;}

    public void setHOLE_POSITION(double hole_position){
        this.HOLE_POSITION=hole_position;
    }

    public void setHOLE_LENGTH(double hole_length){
        this.HOLE_LENGTH=hole_length;
    }

    public void setVelocity(double velocity) {
        this.PARTICLE_SPEED = velocity;
    }

    public double SIGMA(){
        return SIGMA;
    }
    public double RM(){
        return RM;
    }
    public double EPSILON(){
        return EPSILON;
    }

    public double HORIZONTAL_WALL_HOLE_LENGTH(){return HORIZONTAL_WALL_HOLE_LENGTH;}

    public double KN(){return KN;}

    public double KT(){return KT;}

    public double GAMMA(){return GAMMA;}

    public double OFFSET(){return OFFSET;}
}


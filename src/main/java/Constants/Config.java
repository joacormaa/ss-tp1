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
    private double PRINT_TIME;
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
    private double WALL_WIDTH;
    private double PARTICLE_MASS;

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
        if(CELL_AMOUNT==0)CELL_AMOUNT=1;
    }

    private void recoverValuesFromXML() throws ParserConfigurationException, IOException, SAXException {
        File file = new File("config.xml");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

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

        String PRINT_TIME = document.getElementsByTagName("PRINT_TIME").item(0).getTextContent();


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

        this.PRINT_TIME = Double.parseDouble(PRINT_TIME);

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
}


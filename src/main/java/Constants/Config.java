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
    private double SYSTEM_LENGTH;
    private int PARTICLE_QUANTITY;
    private double PARTICLE_RADIUS;
    private double PARTICLE_RC;
    private String OUTPUT_PATH;
    private double PARTICLE_INFLUENCE_RADIUS;
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
        PARTICLE_INFLUENCE_RADIUS = 2*PARTICLE_RADIUS+PARTICLE_RC;
        CELL_AMOUNT = (int)Math.floor(SYSTEM_LENGTH/PARTICLE_INFLUENCE_RADIUS);
        if(CELL_AMOUNT==0)CELL_AMOUNT=1;
        CELL_LENGTH = SYSTEM_LENGTH/CELL_AMOUNT;
    }

    private void recoverValuesFromXML() throws ParserConfigurationException, IOException, SAXException {
        File file = new File("config.xml");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        String SYSTEM_LENGTH_STR = document.getElementsByTagName("SYSTEM_LENGTH").item(0).getTextContent();
        String PARTICLE_QUANTITY_STR = document.getElementsByTagName("PARTICLE_QUANTITY").item(0).getTextContent();
        String PARTICLE_RADIUS_STR = document.getElementsByTagName("PARTICLE_RADIUS").item(0).getTextContent();
        String PARTICLE_RC_STR = document.getElementsByTagName("PARTICLE_RC").item(0).getTextContent();
        String PARTICLE_SPEED_STR = document.getElementsByTagName("PARTICLE_SPEED").item(0).getTextContent();
        String AMOUNT_OF_FRAMES_STR = document.getElementsByTagName("AMOUNT_OF_FRAMES").item(0).getTextContent();
        String MAX_NOISE_STR = document.getElementsByTagName("NOISE_COEFFICIENT").item(0).getTextContent();
        String HORIZONTAL_WALL_LENGTH = document.getElementsByTagName("HORIZONTAL_WALL_LENGTH").item(0).getTextContent();
        String VERTICAL_WALL_LENGTH = document.getElementsByTagName("VERTICAL_WALL_LENGTH").item(0).getTextContent();
        String HOLE_LENGTH = document.getElementsByTagName("HOLE_LENGTH").item(0).getTextContent();
        String HOLE_POSITION = document.getElementsByTagName("HOLE_POSITION").item(0).getTextContent();
        String WALL_WIDTH = document.getElementsByTagName("WALL_WIDTH").item(0).getTextContent();


        this.OUTPUT_PATH = document.getElementsByTagName("OUTPUT_PATH").item(0).getTextContent();
        this.SYSTEM_LENGTH=Double.parseDouble(SYSTEM_LENGTH_STR);
        this.PARTICLE_QUANTITY = Integer.parseInt(PARTICLE_QUANTITY_STR);
        this.PARTICLE_RADIUS = Double.parseDouble(PARTICLE_RADIUS_STR);
        this.PARTICLE_RC = Double.parseDouble(PARTICLE_RC_STR);
        this.PARTICLE_SPEED = Double.parseDouble(PARTICLE_SPEED_STR);
        this.AMOUNT_OF_FRAMES = Integer.parseInt(AMOUNT_OF_FRAMES_STR);
        this.NOISE_COEFFICIENT = Double.parseDouble(MAX_NOISE_STR);
        this.HORIZONTAL_WALL_LENGTH = Double.parseDouble(HORIZONTAL_WALL_LENGTH);
        this.VERTICAL_WALL_LENGTH = Double.parseDouble(VERTICAL_WALL_LENGTH);
        this.HOLE_LENGTH = Double.parseDouble(HOLE_LENGTH);
        this.HOLE_POSITION = Double.parseDouble(HOLE_POSITION);
        this.WALL_WIDTH = Double.parseDouble(WALL_WIDTH);

    }

    public double PARTICLE_SPEED(){return PARTICLE_SPEED;}

    public double SYSTEM_LENGTH(){
        return SYSTEM_LENGTH;
    }

    public Integer PARTICLES_QUANTITY() {
        return PARTICLE_QUANTITY;
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
}


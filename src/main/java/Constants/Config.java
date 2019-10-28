package Constants;

import Model.Person;
import NeighbourLogic.Helper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

public final class Config {
    private double SIMULATION_DELTA_TIME;
    private int FRAMES_PER_PRINT;
    private boolean LOG_ON;

    private int PERSON_QUANTITY;
    private double PERSON_SPEED;
    private double PERSON_MIN_R;
    private double PERSON_MAX_R;

    private double OBSTACLE_R;
    private double OBSTACLE_SPEED;

    private String OUTPUT_PATH;
    private int CELL_AMOUNT;
    private double CELL_LENGTH;

    private double HORIZONTAL_WALL_LENGTH;
    private double VERTICAL_WALL_LENGTH;

    private double GOAL_RADIUS;

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

        CELL_AMOUNT = (int) (VERTICAL_WALL_LENGTH/ PERSON_MAX_R);
        if(CELL_AMOUNT==0)CELL_AMOUNT=1;
        CELL_LENGTH = VERTICAL_WALL_LENGTH/CELL_AMOUNT;
    }

    private void recoverValuesFromXML() throws ParserConfigurationException, IOException, SAXException {
        File file = new File("config.xml");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        String HORIZONTAL_WALL_LENGTH = document.getElementsByTagName("HORIZONTAL_WALL_LENGTH").item(0).getTextContent();
        String VERTICAL_WALL_LENGTH = document.getElementsByTagName("VERTICAL_WALL_LENGTH").item(0).getTextContent();

        String SIMULATION_DELTA_TIME = document.getElementsByTagName("SIMULATION_DELTA_TIME").item(0).getTextContent();
        String FRAMES_PER_PRINT = document.getElementsByTagName("FRAMES_PER_PRINT").item(0).getTextContent();
        String LOG_ON = document.getElementsByTagName("LOG_ON").item(0).getTextContent();

        String PERSON_QUANTITY = document.getElementsByTagName("PERSON_QUANTITY").item(0).getTextContent();
        String PERSON_SPEED = document.getElementsByTagName("PERSON_SPEED").item(0).getTextContent();
        String PERSON_MAX_R = document.getElementsByTagName("PERSON_MAX_R").item(0).getTextContent();
        String PERSON_MIN_R = document.getElementsByTagName("PERSON_MIN_R").item(0).getTextContent();

        String OBSTACLE_R = document.getElementsByTagName("OBSTACLE_R").item(0).getTextContent();
        String OBSTACLE_SPEED = document.getElementsByTagName("OBSTACLE_SPEED").item(0).getTextContent();

        String GOAL_RADIUS = document.getElementsByTagName("GOAL_RADIUS").item(0).getTextContent();


        this.OUTPUT_PATH = document.getElementsByTagName("OUTPUT_PATH").item(0).getTextContent();

        this.HORIZONTAL_WALL_LENGTH = Double.parseDouble(HORIZONTAL_WALL_LENGTH);
        this.VERTICAL_WALL_LENGTH = Double.parseDouble(VERTICAL_WALL_LENGTH);

        this.SIMULATION_DELTA_TIME = Double.parseDouble(SIMULATION_DELTA_TIME);
        this.FRAMES_PER_PRINT = Integer.parseInt(FRAMES_PER_PRINT);
        this.LOG_ON = Boolean.parseBoolean(LOG_ON);

        this.PERSON_QUANTITY = Integer.parseInt(PERSON_QUANTITY);
        this.PERSON_SPEED = Double.parseDouble(PERSON_SPEED);
        this.PERSON_MAX_R = Double.parseDouble(PERSON_MAX_R);
        this.PERSON_MIN_R = Double.parseDouble(PERSON_MIN_R);

        this.OBSTACLE_R = Double.parseDouble(OBSTACLE_R);
        this.OBSTACLE_SPEED = Double.parseDouble(OBSTACLE_SPEED);

        this.GOAL_RADIUS = Double.parseDouble(GOAL_RADIUS);
    }


    public double SIMULATION_DELTA_TIME(){
        return SIMULATION_DELTA_TIME;
    }
    public int FRAMES_PER_PRINT(){
        return FRAMES_PER_PRINT;
    }
    public boolean LOG_ON(){
        return LOG_ON;
    }

    public int PERSON_QUANTITY(){
        return PERSON_QUANTITY;
    }
    public double PERSON_SPEED(){
        return PERSON_SPEED;
    }
    public double PERSON_MIN_R(){
        return PERSON_MIN_R;
    }
    public double PERSON_MAX_R(){
        return PERSON_MAX_R;
    }

    public double OBSTACLE_R(){
        return OBSTACLE_R;
    }
    public double OBSTACLE_SPEED(){
        return OBSTACLE_SPEED;
    }

    public String OUTPUT_PATH(){
        return OUTPUT_PATH;
    }
    public int CELL_AMOUNT(){
        return CELL_AMOUNT;
    }
    public double CELL_LENGTH(){
        return CELL_LENGTH;
    }

    public double HORIZONTAL_WALL_LENGTH(){
        return HORIZONTAL_WALL_LENGTH;
    }
    public double VERTICAL_WALL_LENGTH(){
        return VERTICAL_WALL_LENGTH;
    }

    public double GOAL_RADIUS(){
        return GOAL_RADIUS;
    }

}


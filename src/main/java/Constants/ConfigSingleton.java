package Constants;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

public class ConfigSingleton implements Config{
    private double SYSTEM_LENGTH;
    private int PARTICLE_QUANTITY;
    private double PARTICLE_RADIUS;
    private double PARTICLE_RC;
    private String OUTPUT_PATH;
    private double PARTICLE_INFLUENCE_RADIUS;
    private int CELL_AMOUNT;
    private double CELL_LENGTH;

    private static Config instance;

    public static Config getInstance(){
        if(instance==null){
            instance=new ConfigSingleton();
        }
        return instance;
    }

    private ConfigSingleton() {

        try{
            recoverValuesFromXML();
        }
        catch (ParserConfigurationException | IOException |SAXException e){
            System.err.println("Invalid XML file. '"+e.getMessage()+"'");
            exit(-1);
        }

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

        this.OUTPUT_PATH = document.getElementsByTagName("OUTPUT_PATH").item(0).getTextContent();
        this.SYSTEM_LENGTH=Double.parseDouble(SYSTEM_LENGTH_STR);
        this.PARTICLE_QUANTITY = Integer.parseInt(PARTICLE_QUANTITY_STR);
        this.PARTICLE_RADIUS = Double.parseDouble(PARTICLE_RADIUS_STR);
        this.PARTICLE_RC = Double.parseDouble(PARTICLE_RC_STR);
    }

    public double SYSTEM_LENGTH(){
        return SYSTEM_LENGTH;
    }

    public int PARTICLES_QUANTITY() {
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

    public static void setInstance(Config config){
        instance=config; //Method shouldn/t exist but spring doesnt fix the issue
    }
}


package Model;

import Constants.Config;

public class Wall implements Interactable{
    private boolean isVertical;
    private double x;
    private double y;
    private double length;
    private int id;
    private double width;

    public Wall(boolean isVertical, double x, double y, double length, int id) {
        this.width = Config.getInstance().WALL_WIDTH();
        this.isVertical = isVertical;
        this.x = x;
        this.y = y;
        this.length = length;
        this.id=id;
    }

    public int getId(){
        return id;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public String stringify(int idBase) {
        StringBuilder sb = new StringBuilder();
        int pPerWall = Config.getInstance().PARTICLES_PER_WALL();
        double amount=length/pPerWall;
        for(int i=0; i<pPerWall; i++){

            sb.append(idBase+i);
            sb.append(' ');
            double x,y;
            if(isVertical){
                y=this.y+i*amount;
                x=this.x;
            }
            else{
                x=this.x+i*amount;
                y=this.y;
            }
            sb.append(x);
            sb.append(' ');
            sb.append(y);
            sb.append(' ');
            sb.append(0);
            sb.append(' ');
            sb.append(0);
            sb.append(' ');
            sb.append(0);
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public double getXIncidentalForce(Particle p) {
        if(!this.isVertical) return 0;
        return getFN(p);
    }

    @Override
    public double getYIncidentalForce(Particle p) {
        if(this.isVertical) return 0;
        return getFN(p);
    }

    private double getFN(Particle p) {
        Config c = Config.getInstance();
        double epsilon =  c.EPSILON();
        double rm = c.RM();
        double sigma = c.SIGMA();

        double r = (this.isVertical)?p.getX()-this.x:p.getY()-this.y;
        r=Math.abs(r);

        double coef = rm/r;

        return (12*sigma/rm) *(Math.pow(coef,13)-Math.pow(coef,7))/3;//divido por 3 como workaround, cada particula va a ser vecina de una pared 3 veces. PR
    }
}

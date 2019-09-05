package Model;

import Constants.Config;

public class Wall {
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
}

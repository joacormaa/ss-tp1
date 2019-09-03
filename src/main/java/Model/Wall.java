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
}

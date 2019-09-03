package Model;

public class Wall {
    private boolean isVertical;
    private Double position;
    private Double width;
    private int id;

    public Wall(boolean isVertical, Double position, Double width, int id) {
        this.isVertical = isVertical;
        this.position = position;
        this.width = width;
    }

    public int getId(){
        return id;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public Double getPosition() {
        return position;
    }

    public Double getWidth() {
        return width;
    }
}

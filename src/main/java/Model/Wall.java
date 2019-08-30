package Model;

public class Wall {
    private boolean isVertical;
    private Double position;
    private Double width;

    public Wall(boolean isVertical, Double position, Double width) {
        this.isVertical = isVertical;
        this.position = position;
        this.width = width;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }

    public Double getPosition() {
        return position;
    }

    public void setPosition(Double position) {
        this.position = position;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }
}

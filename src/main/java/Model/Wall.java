package Model;

public class Wall {
    private boolean isVertical;
    private Double position;

    public Wall(boolean isVertical, Double position) {
        this.isVertical = isVertical;
        this.position = position;
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
}

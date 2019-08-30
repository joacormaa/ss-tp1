package CollisionSimulator;

public class Collision<S, T> {
    S elem1;
    T elem2;
    Double timeUntil;

    public Collision(S elem1, T elem2, Double timeUntil) {
        this.elem1 = elem1;
        this.elem2 = elem2;
        this.timeUntil = timeUntil;
    }

    public S getElem1() {
        return elem1;
    }

    public void setElem1(S elem1) {
        this.elem1 = elem1;
    }

    public T getElem2() {
        return elem2;
    }

    public void setElem2(T elem2) {
        this.elem2 = elem2;
    }

    public Double getTimeUntil() {
        return timeUntil;
    }

    public void setTimeUntil(Double timeUntil) {
        this.timeUntil = timeUntil;
    }
}

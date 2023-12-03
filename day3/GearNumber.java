package day3;

public class GearNumber {

    int number;
    boolean isAbove;
    boolean isLeft;
    boolean isRight;
    boolean isBelow;

    public GearNumber(int number) {
        this.number = number;
    }

    public boolean exists(){
        return this.number != -1;
    }
}

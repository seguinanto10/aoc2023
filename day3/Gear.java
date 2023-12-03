package day3;

public class Gear {

    GearNumber numberA;
    GearNumber numberB;

    long ratio;

    public Gear(GearNumber numberA, GearNumber numberB) {
        this.numberA = numberA;
        this.numberB = numberB;
        this.ratio = (long) numberA.number * numberB.number;
    }
}

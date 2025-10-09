package sample.cafekiosk.unit.beverage;

import sample.cafekiosk.unit.Beverage;

public class Americano implements Beverage {

    @Override
    public String getName() {
        return "아메리카노";
    }

    @Override
    public int getPirate() {
        return 4000;
    }
}

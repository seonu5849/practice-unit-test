package sample.cafekiosk.unit.beverage;

import sample.cafekiosk.unit.Beverage;

public class Latte implements Beverage {

    @Override
    public String getName() {
        return "라떼";
    }

    @Override
    public int getPirate() {
        return 5000;
    }
}

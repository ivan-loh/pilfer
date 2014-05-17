package com.eriad.app.states;

public class Selangor extends State {


    public static void main(String[] args) {

        new Selangor().process();

    }

    @Override
    public int[] getStateCodes() {
        return new int[]{10, 41, 42, 43, 44};
    }
}

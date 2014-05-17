package com.eriad.app.states;


public class Perak extends State {

    public static void main(String[] args) {

        new Perak().process();

    }

    @Override
    public int[] getStateCodes() {
        return new int[]{8, 36, 37, 38, 39};
    }
}

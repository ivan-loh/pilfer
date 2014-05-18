package com.eriad.app.states;

public class Sarawak extends State {

    public static void main(String[] args) { new Sarawak().process(); }

    @Override
    public int[] getStateCodes() { return new int[]{13, 50, 51, 52, 53}; }
}

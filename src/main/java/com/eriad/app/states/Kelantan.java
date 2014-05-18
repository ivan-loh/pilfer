package com.eriad.app.states;

public class Kelantan extends State {

    public static void main(String[] args) { new Kelantan().process(); }

    @Override
    public int[] getStateCodes() { return new int[] {3, 28, 29}; }
}

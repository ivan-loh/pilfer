package com.eriad.app.states;

public class Kedah extends State {

    public static void main(String[] args) { new Kedah().process(); }

    @Override
    public int[] getStateCodes() { return new int[] {2, 25, 26, 27}; }
}

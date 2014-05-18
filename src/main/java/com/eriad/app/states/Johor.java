package com.eriad.app.states;

public class Johor extends State {

    public static void main(String[] args) { new Johor().process(); }

    @Override
    public int[] getStateCodes() { return new int[] {1, 21, 22, 23, 2}; }
}

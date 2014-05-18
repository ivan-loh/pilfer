package com.eriad.app.states;

public class Sabah extends State {

    public static void main(String[] args) { new Sabah().process(); }

    @Override
    public int[] getStateCodes() { return new int[] {12, 47, 48, 49}; }
}

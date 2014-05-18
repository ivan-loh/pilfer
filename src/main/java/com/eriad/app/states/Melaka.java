package com.eriad.app.states;

public class Melaka extends State {

    public static void main(String[] args) { new Melaka().process(); }

    @Override
    public int[] getStateCodes() { return new int[]{4, 30}; }
}

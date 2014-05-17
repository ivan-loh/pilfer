package com.eriad.app.states;

public class Perlis extends State {

    public static void main(String[] args) { new Perlis().process(); }

    @Override
    public int[] getStateCodes() { return new int[] {9, 40}; }
}

package com.eriad.app.states;

public class Pahang extends State {

    public static void main(String[] args) { new Pahang().process(); }

    @Override
    public int[] getStateCodes() { return new int[] {6, 32, 33}; }
}

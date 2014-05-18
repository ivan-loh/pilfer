package com.eriad.app.states;

public class WPKualaLumpur extends State {

    public static void main(String[] args) { new WPKualaLumpur().process(); }

    @Override
    public int[] getStateCodes() { return new int[]{14, 54, 55, 56, 57}; }
}

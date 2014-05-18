package com.eriad.app.states;

public class WPPutrajaya extends State {

    public static void main(String[] args) { new WPPutrajaya().process(); }

    @Override
    public int[] getStateCodes() { return new int[]{16}; }
}

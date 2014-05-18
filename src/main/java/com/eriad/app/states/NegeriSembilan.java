package com.eriad.app.states;

public class NegeriSembilan extends State {

    public static void main(String[] args) { new NegeriSembilan().process(); }

    @Override
    public int[] getStateCodes() { return new int[]{5, 31, 59}; }
}

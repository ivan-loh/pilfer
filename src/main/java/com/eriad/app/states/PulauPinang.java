package com.eriad.app.states;

public class PulauPinang extends State {

    public static void main(String[] args) { new PulauPinang().process(); }

    @Override
    public int[] getStateCodes() { return new int[] {7, 34, 35}; }
}

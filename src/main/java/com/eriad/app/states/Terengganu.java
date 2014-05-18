package com.eriad.app.states;

public class Terengganu extends State {

    public static void main(String[] args) { new Terengganu().process(); }

    @Override
    public int[] getStateCodes() { return new int[] {11, 45, 46}; }
}

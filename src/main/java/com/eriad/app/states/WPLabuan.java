package com.eriad.app.states;

public class WPLabuan extends State {

    public static void main(String[] args) { new WPLabuan().process(); }

    @Override
    public int[] getStateCodes() { return new int[]{15, 58}; }
}

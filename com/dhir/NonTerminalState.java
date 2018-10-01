package com.dhir;

public class NonTerminalState extends State {
    public NonTerminalState(double probability, int x, int y) {
        super(probability, x, y);
    }

    public NonTerminalState(int x, int y) {
        super(1.00/9.00, x, y);
    }
}

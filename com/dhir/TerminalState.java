package com.dhir;

public class TerminalState extends State {
    public TerminalState(int row, int column) {
        super(0, row, column);
    }

    @Override
    public double perceiveEvidence(Evidence e){
        return 1.00;
    }
}

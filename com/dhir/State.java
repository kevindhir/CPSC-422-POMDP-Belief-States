package com.dhir;

public abstract class State {
    public double getProbability() {
        return probability;
    }

    protected double probability;
    protected int row;
    protected int column;

    public State(double probability, int row, int column) {
        this.probability = probability;
        this.row = row;
        this.column = column;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public double perceiveEvidence(Evidence e) {
        if (e.equals(Evidence.two) && column != 3) return 0.9;
        if (e.equals(Evidence.two) && column == 3) return 0.1;
        if (e.equals(Evidence.one) && column != 3) return 0.1;
        if (e.equals(Evidence.one) && column == 3) return 0.9;
        return 1.0;
    }
}

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
}

package com.dhir;

import java.text.DecimalFormat;

public class Agent {
    private State[][] beliefStates;
    private static int ROWS = 4;
    private static int COLUMNS = 5;
    private static double EXPECTED_ACTION = 0.8;
    private static double UNEXPECTED_ACTION = 0.1;

    public Agent() {
        beliefStates = new State[ROWS][COLUMNS];
        for (int i = 1; i < ROWS; i++) {
            for (int j = 1; j < COLUMNS; j++) {
                beliefStates[i][j] = new NonTerminalState(i, j);
            }
        }

        beliefStates[3][4] = new TerminalState(3, 4);
        beliefStates[2][4] = new TerminalState(2, 4);
        beliefStates[2][2] = new WallState(2, 2);
    }

    public Agent(int row, int column) {
        beliefStates = new State[ROWS][COLUMNS];
        for (int i = 1; i < ROWS; i++) {
            for (int j = 1; j < COLUMNS; j++) {
                beliefStates[i][j] = new NonTerminalState(0, i, j);
            }
        }
        beliefStates[3][4] = new TerminalState(3, 4);
        beliefStates[2][4] = new TerminalState(2, 4);
        beliefStates[2][2] = new WallState(2, 2);
        beliefStates[row][column].setProbability(1.00);
    }

    public void takeAction(Action action, Evidence evidence) {
        double[][] updatedBeliefs = new double[ROWS][COLUMNS];
        for (int row = 1; row < ROWS; row++) {
            for (int col = 1; col < COLUMNS; col++) {
                if (row == 2 && col == 2) continue;
                State currentState = beliefStates[row][col];
                Double updatedBelief;
                Double evidenceProbability = perceiveEvidence(evidence, currentState, col);
                switch (action) {
                    case up:
                        updatedBelief = actionUp(row, col);
                        break;
                    case down:
                        updatedBelief = actionDown(row, col);
                        break;
                    case left:
                        updatedBelief = actionLeft(row, col);
                        break;
                    default:
                        updatedBelief = actionRight(row, col);
                        break;
                }
                updatedBelief *= evidenceProbability;
                updatedBeliefs[row][col] = updatedBelief;
            }
        }
        updateBeliefStates(updatedBeliefs);
    }

    private double perceiveEvidence(Evidence e, State currentState, int column) {
        if (currentState instanceof TerminalState && e.equals(Evidence.end)) return 1.0;
        if (currentState instanceof TerminalState) return 0.0;
        if (e.equals(Evidence.two) && column != 3) return 0.9;
        if (e.equals(Evidence.two) && column == 3) return 0.1;
        if (e.equals(Evidence.one) && column != 3) return 0.1;
        if (e.equals(Evidence.one) && column == 3) return 0.9;
        return 1.0;

    }

    private void normalize(double[][] beliefs) {
        double total = 0.00;
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 5; j++) {
                total += beliefs[i][j];
            }
        }
        System.out.println("Before normalizing " + total);
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 5; j++) {
                beliefs[i][j] /= total;
            }
        }
        double normalizedTotal = 0.00;
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 5; j++) {
                normalizedTotal += beliefs[i][j];
            }
        }
        System.out.println("After normalizing " + normalizedTotal);
    }

    private void updateBeliefStates(double[][] updatedBeliefs) {
        normalize(updatedBeliefs);
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 5; j++) {
                beliefStates[i][j].setProbability(updatedBeliefs[i][j]);
            }
        }
    }

    private double actionLeft(int row, int col) {
        double output = 0.00;
        if (col + 1 < 5) { //Left from right
            output += EXPECTED_ACTION * beliefStates[row][col + 1].getProbability();
        }
        if (col == 1) { //Hit left wall
            output += EXPECTED_ACTION * beliefStates[row][col].getProbability();
        }
        if (col == 2) { //Hit 2-2 wall
            output += UNEXPECTED_ACTION * beliefStates[row][col].getProbability();
        }
        if (row == 1 || row == 3) { //Hit top or bottom wall
            output += UNEXPECTED_ACTION * beliefStates[row][col].getProbability();
        }
        if (row + 1 < 4) { //Down from above
            output += UNEXPECTED_ACTION * beliefStates[row + 1][col].getProbability();
        }
        if (row - 1 > 0) { //Up from below
            output += UNEXPECTED_ACTION * beliefStates[row - 1][col].getProbability();
        }
        if (row == 2 && col == 3) { //Hit 2-2 wall
            output += EXPECTED_ACTION * beliefStates[row][col].getProbability();
        }
        return output;
    }

    private double actionRight(int row, int col) {
        double output = 0.00;
        if (col == 4) { //Hit right wall
            output += EXPECTED_ACTION * beliefStates[row][col].getProbability();
        }
        if (col - 1 > 0) { //Right from left
            output += EXPECTED_ACTION * beliefStates[row][col - 1].getProbability();
        }
        if (col == 1 && row == 2) { //Hit 2-2 wall
            output += EXPECTED_ACTION * beliefStates[row][col].getProbability();
        }
        if (row - 1 > 0) { //Up from below
            output += UNEXPECTED_ACTION * beliefStates[row - 1][col].getProbability();
        }
        if (row + 1 < 4) { //Down from above
            output += UNEXPECTED_ACTION * beliefStates[row + 1][col].getProbability();
        }
        if (row == 1 || row == 3) { //Hit top or bottom
            output += UNEXPECTED_ACTION * beliefStates[row][col].getProbability();
        }
        return output;
    }

    private double actionUp(int row, int col) {
        double output = 0.00;
        if (row == 3) { //Hit top wall
            output += EXPECTED_ACTION * beliefStates[row][col].getProbability();
        }
        if (row == 1 && col == 2) { //Hit 2-2 wall
            output += EXPECTED_ACTION * beliefStates[row][col].getProbability();
        }
        if (row == 2 && col == 1 || row == 2 && col == 3) { //Hit 2-2 wall
            output += UNEXPECTED_ACTION * beliefStates[row][col].getProbability();
        } else if (col == 1 || col == 4) { //Hit L or R wall
            output += UNEXPECTED_ACTION * beliefStates[row][col].getProbability();
        }
        if (row - 1 > 0) { //Up from below
            output += EXPECTED_ACTION * beliefStates[row - 1][col].getProbability();
        }
        if (col + 1 < 5) { //Left from right
            output += UNEXPECTED_ACTION * beliefStates[row][col + 1].getProbability();
        }
        if (col - 1 > 0) { //Right from left
            output += UNEXPECTED_ACTION * beliefStates[row][col - 1].getProbability();
        }
        return output;
    }

    private double actionDown(int row, int col) {
        double output = 0.00;
        if (row + 1 < 4) { //Down from above
            output += EXPECTED_ACTION * beliefStates[row + 1][col].getProbability();
        }
        if (row == 1) { //Hit bottom wall
            output += EXPECTED_ACTION * beliefStates[row][col].getProbability();
        }
        if (row == 2 && (col == 1 || col == 3)) { //Hit 2 2 wall by going L or R
            output += UNEXPECTED_ACTION * beliefStates[row][col].getProbability();
        }
        if (col == 1 || col == 4) { //Hit L or R wall
            output += UNEXPECTED_ACTION * beliefStates[row][col].getProbability();
        }
        if (col + 1 < 5) { //Left from right
            output += UNEXPECTED_ACTION * beliefStates[row][col + 1].getProbability();
        }
        if (col - 1 > 0) { //Right from left
            output += UNEXPECTED_ACTION * beliefStates[row][col - 1].getProbability();
        }
        return output;
    }

    public void printBeliefState() {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
        double total = 0.00;
        for (int i = 3; i > 0; i--) {
            for (int j = 1; j < 5; j++) {
                String belief = df.format(beliefStates[i][j].getProbability());
                System.out.print(String.format("(row %d, col %d) %s", i, j, belief));
                System.out.print("\t \t");
                total += beliefStates[i][j].getProbability();
            }
            System.out.println();
        }
        System.out.println("Total " + total);
    }

}

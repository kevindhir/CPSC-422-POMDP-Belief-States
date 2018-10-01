package com.dhir;

import java.text.DecimalFormat;

public class Agent {
    private State[][] beliefStates;

    public Agent() {
        beliefStates = new State[4][5];
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 5; j++) {
                beliefStates[i][j] = new NonTerminalState(i, j);
            }
        }

        beliefStates[3][4] = new TerminalState(4, 3);
        beliefStates[2][4] = new TerminalState(4, 2);
        beliefStates[2][2] = new WallState(2, 2);
    }

    public Agent(int row, int column) {
        beliefStates = new State[4][5];
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 5; j++) {
                beliefStates[i][j] = new NonTerminalState(0, i, j);
            }
        }
        beliefStates[3][4] = new TerminalState(4, 3);
        beliefStates[2][4] = new TerminalState(4, 2);
        beliefStates[2][2] = new WallState(2, 2);
        beliefStates[row][column].setProbability(1.00);
    }

    public void takeAction(Action action, Evidence evidence) {
        switch (action) {
            case up:
                actionUp(evidence);
                break;
            case down:
                actionDown(evidence);
                break;
            case left:
                actionLeft(evidence);
                break;
            default:
                actionRight(evidence);
                break;
        }
    }

    private void updateBeliefStates(double[][] updatedBeliefs) {
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 5; j++) {
                beliefStates[i][j].setProbability(updatedBeliefs[i][j]);
            }
        }
    }

    private void actionLeft(Evidence e) {
        double[][] updatedBeliefs = new double[4][5];
        for (int row = 1; row < 4; row++) {
            for (int col = 1; col < 5; col++) {
                State currentState = beliefStates[row][col];
                Double updatedBelief = 0.00;
                Double evidenceProbability = currentState.perceiveEvidence(e);
                if (col + 1 < 5) {
                    updatedBelief += 0.8 * beliefStates[row][col + 1].getProbability();
                }
                if (col == 1) {
                    updatedBelief += 0.8 * currentState.getProbability();
                }
                if (col == 2) {
                    updatedBelief += 0.1 * currentState.getProbability();
                }
                if (row == 1 || row == 3) {
                    updatedBelief += 0.1 * currentState.getProbability();
                }
                if (row + 1 < 4) {
                    updatedBelief += 0.1 * beliefStates[row + 1][col].getProbability();
                }
                if (row - 1 > 0) {
                    updatedBelief += 0.1 * beliefStates[row - 1][col].getProbability();
                }
                if (row == 2 && col == 3) {
                    updatedBelief += 0.8 * currentState.getProbability();
                }
                updatedBelief *= evidenceProbability;
                updatedBeliefs[row][col] = updatedBelief;
            }
        }
        updateBeliefStates(updatedBeliefs);
    }

    private void actionRight(Evidence e) {
        double[][] updatedBeliefs = new double[4][5];
        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 4; col++) {
                State currentState = beliefStates[row][col];
                Double updatedBelief = 0.00;
                Double evidenceProbability = currentState.perceiveEvidence(e);
                if (col == 4) {
                    updatedBelief += 0.8 * currentState.getProbability();
                }
                if (col - 1 > 0) {
                    updatedBelief += 0.8 * beliefStates[row][col - 1].getProbability();
                }
                if (col == 1 && row == 2) {
                    updatedBelief += 0.8 * currentState.getProbability();
                }
                if (row - 1 > 0) {
                    updatedBelief += 0.1 * beliefStates[row - 1][col].getProbability();
                }
                if (row + 1 < 4) {
                    updatedBelief += 0.1 * beliefStates[row + 1][col].getProbability();
                }
                if (row == 1 || row == 3) {
                    updatedBelief += 0.1 * currentState.getProbability();
                }
                updatedBelief *= evidenceProbability;
                updatedBeliefs[row][col] = updatedBelief;
            }
        }
        updateBeliefStates(updatedBeliefs);
    }

    private void actionUp(Evidence e) {
        double[][] updatedBeliefs = new double[4][5];
        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 4; col++) {
                State currentState = beliefStates[row][col];
                Double updatedBelief = 0.00;
                Double evidenceProbability = currentState.perceiveEvidence(e);
                if (row == 3) {
                    updatedBelief += 0.8 * currentState.getProbability();
                }
                if (row == 1 && col == 2) {
                    updatedBelief += 0.8 * currentState.getProbability();
                }
                if (row == 2 && col == 1 || row == 2 && col == 3) {
                    updatedBelief += 0.1 * currentState.getProbability();
                } else if (col == 1 || col == 4) {
                    updatedBelief += 0.1 * currentState.getProbability();
                }
                if (row - 1 > 0) {
                    updatedBelief += 0.8 * beliefStates[row - 1][col].getProbability();
                }
                updatedBelief *= evidenceProbability;
                updatedBeliefs[row][col] = updatedBelief;
            }
        }
        updateBeliefStates(updatedBeliefs);
    }

    private void actionDown(Evidence e) {
        double[][] updatedBeliefs = new double[4][5];
        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 4; col++) {
                State currentState = beliefStates[row][col];
                Double updatedBelief = 0.00;
                Double evidenceProbability = currentState.perceiveEvidence(e);
                if (row + 1 < 4) {
                    updatedBelief += 0.8 * beliefStates[row + 1][col].getProbability();
                }
                if ((col == 1 || col == 3) && row == 2) {
                    updatedBelief += 0.1 * currentState.getProbability();
                }
                if (col == 1 || col == 4) {
                    updatedBelief += 0.1 * currentState.getProbability();
                }
                if (row == 1) {
                    updatedBelief += 0.8 * currentState.getProbability();
                }
                updatedBelief *= evidenceProbability;
                updatedBeliefs[row][col] = updatedBelief;
            }
        }
        updateBeliefStates(updatedBeliefs);
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
        System.out.println("Total " + df.format(total));

    }

}

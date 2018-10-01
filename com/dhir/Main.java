package com.dhir;

public class Main {

    public static void main(String[] args) {
        Agent agent = new Agent();
        agent.takeAction(Action.left, Evidence.none);
        agent.takeAction(Action.left, Evidence.none);
        agent.takeAction(Action.left, Evidence.none);
        agent.takeAction(Action.left, Evidence.none);
        agent.takeAction(Action.left, Evidence.none);
        agent.printBeliefState();
    }
}

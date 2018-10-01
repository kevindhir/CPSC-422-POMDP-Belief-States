package com.dhir;

public class Main {
    public static void main(String[] args) {
        Agent agent = new Agent();
        agent.takeAction(Action.up, Evidence.one);
        agent.takeAction(Action.up, Evidence.one);
        agent.takeAction(Action.up, Evidence.one);
        agent.printBeliefState();
    }
}

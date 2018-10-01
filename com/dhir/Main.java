package com.dhir;

public class Main {
    public static void main(String[] args) {
        Agent agent = new Agent(2, 3);
        agent.takeAction(Action.right, Evidence.one);
        agent.takeAction(Action.right, Evidence.one);
        agent.takeAction(Action.up, Evidence.end);
        agent.printBeliefState();
    }
}

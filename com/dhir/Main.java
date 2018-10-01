package com.dhir;

public class Main {
    public static void main(String[] args) {
        Agent agent = new Agent(1,1);
        agent.takeAction(Action.up, Evidence.two);
        agent.takeAction(Action.right, Evidence.two);
        agent.takeAction(Action.right, Evidence.one);
        agent.takeAction(Action.right, Evidence.one);
        agent.printBeliefState();
    }
}

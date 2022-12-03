package ui;

import model.Event;
import model.EventLog;

// Main class to call console ui
public class Main {
    public static void main(String[] args) {
        // TODO:
        // run from constructor
        MainGUI menu = new MainGUI();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (Event e : EventLog.getInstance()) {
                System.out.println(e.toString());
            }
        }, "Shutdown-thread"));
    }
}

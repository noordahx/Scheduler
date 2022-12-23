package model;

import org.json.JSONObject;
import persistence.Writable;

// class that holds data about each timeslot.
public class TimeSlot implements Writable {
    private String userName;
    private boolean status;

    // Modifies: this
    // Effects: sets default values for the local variables
    public TimeSlot() {
        this.userName = "";
        this.status = true;
    }

    public TimeSlot(String username, boolean status) {
        this.userName = username;
        this.status = status;
    }

    // Effects: returns a status of the timeslot.
    public boolean getStatus() {
        return status;
    }

    // Modifies: this
    // Effects: changes the status of the timeslot to booked and assigns a new userName.
    public void book(String name) {
        this.status = false;
        this.userName = name;
        EventLog.getInstance().logEvent(new Event("Timeslot successfully added!"));
    }

    // Modifies: this
    // Effects: changes the status of the timeslot to free and deletes the userName.
    public void delete() {
        this.userName = "";
        this.status = true;
        EventLog.getInstance().logEvent(new Event("Deleted an old booking"));
    }

    // Effects: returns a userName of the person who booked this timeslot.
    public String getUserName() {
        return userName;
    }

    // Citation: taken from JsonSerializationDemo app.
    // Effects: saves local variable as a JSONObject and returns JSONObject.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Username", userName);
        json.put("status", status);
        return json;
    }
}

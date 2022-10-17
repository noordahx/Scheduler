package model;

// class that holds data about each timeslot.
public class TimeSlot {
    private String userName;
    private boolean status;

    // Modifies: this
    // Effects: sets default values for the local variables
    public TimeSlot() {
        this.userName = "";
        this.status = true;
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
    }

    // Modifies: this
    // Effects: changes the status of the timeslot to free and deletes the userName.
    public void delete() {
        this.userName = "";
        this.status = true;
    }

    // Effects: returns a userName of the person who booked this timeslot.
    public String getUserName() {
        return userName;
    }
}

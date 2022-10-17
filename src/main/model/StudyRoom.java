package model;

import java.util.ArrayList;
import java.util.List;

// class that holds data about the study room
public class StudyRoom {
    private final List<TimeSlot> allTimeSlots;

    // Modifies: this
    // Effects: instantiates list of timeslot objects and changes a name string.
    public StudyRoom() {
        allTimeSlots = new ArrayList<>();
        for (int i = 9; i < 18; i++) {
            allTimeSlots.add(new TimeSlot());
        }
    }

    // Effects: returns schedule of the current room in a form of list of strings.
    public List<String> getSchedule() {
        List<String> output = new ArrayList<>();
        for (int i = 9; i < 18; i++) {
            if (allTimeSlots.get(i - 9).getStatus()) {
                output.add("Free");
            } else {
                output.add(allTimeSlots.get(i - 9).getUserName());
            }
        }
        return output;
    }

    // Requires: 9 <= time <= 17
    // Modifies: this
    // Effects: given a time and name, handles the process of booking a study place.
    public void bookTimeSlot(int time, String name) {
        TimeSlot current = allTimeSlots.get(time - 9);
        current.book(name);
    }

    // Requires: 9 <= time <= 17
    // Modifies: this
    // Effects: given a time, changes the status of the timeslot to FREE.
    public void deleteTimeSlot(int time) {
        TimeSlot current = allTimeSlots.get(time - 9);
        current.delete();
    }

    // Requires: 9 <= time <= 17
    // Effects: given a time, returns the name of person who booked the place at that time.
    public String getTimeSlotUser(int time) {
        TimeSlot current = allTimeSlots.get(time - 9);
        return current.getUserName();
    }

    // Requires: 9 <= time <= 17
    // Effects: given a time, returns the status of that timeslot.
    public boolean getAvailability(int time) {
        TimeSlot current = allTimeSlots.get(time - 9);
        return current.getStatus();
    }

}

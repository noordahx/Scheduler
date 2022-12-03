package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// class that holds data about the study room
public class StudyRoom implements Writable {
    private final List<TimeSlot> allTimeSlots;
    private final String roomName;

    // Modifies: this
    // Effects: instantiates list of timeslot objects and changes a name string.
    public StudyRoom(String name) {
        this.allTimeSlots = new ArrayList<>();
        this.roomName = name;
        EventLog.getInstance().logEvent(new Event(String.format("Created new StudyRoom class with the name %s", name)));
        for (int i = 9; i < 18; i++) {
            allTimeSlots.add(new TimeSlot());
        }
        EventLog.getInstance().logEvent(new Event(String.format("Added timeslots to %s", name)));
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
        EventLog.getInstance().logEvent(new Event(String.format("Booked a timeslot for %s at %d", name, time)));
    }

    // Requires: 9 <= time <= 17
    // Modifies: this
    // Effects: given a time, changes the status of the timeslot to FREE.
    public TimeSlot getTimeSlot(int i) {
        return allTimeSlots.get(i);
    }

    // Requires: 0 <= i <= allTimeSlots.size()
    // Modifies: this
    // Effects: given an index, adds new TimeSlot to TimeSLot array.
    public void changeTimeSlot(int i, TimeSlot ts) {
        allTimeSlots.add(i, ts);
        EventLog.getInstance().logEvent(new Event(String.format("Changed timeslot number %d", i)));
    }

    // Requires: 9 <= time <= 17
    // Modifies: this
    // Effects: given a time, changes the status of the timeslot to FREE.
    public void deleteTimeSlot(int time) {
        TimeSlot current = allTimeSlots.get(time - 9);
        current.delete();
        EventLog.getInstance().logEvent(new Event(String.format("Deleted timeslot at %d", time)));
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

    // Effects: returns name of the room
    public String getName() {
        return this.roomName;
    }


    // Taken from the project JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.roomName);
        json.put("TimeSlots", timeSlotToJson());
        return json;
    }


    // Taken from the project JsonSerializationDemo
    // Effects: returns each timeslot contents a jsonArray.
    private JSONArray timeSlotToJson() {
        JSONArray jsonArray = new JSONArray();

        for (TimeSlot slot: allTimeSlots) {
            jsonArray.put(slot.toJson());
        }

        return jsonArray;
    }


}

package persistence.model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Class with the list of existing rooms
public class ListRooms implements Writable {
    private final List<StudyRoom> allRoomList;
    public static final String[] ROOMS = {
            "X100",
            "X200",
            "X300",
            "X400"
    };

    // Modifies: this
    // Effects: creates linked list of StudyRooms class
    public ListRooms() {
        allRoomList = new ArrayList<>();
        for (String room : ROOMS) {
            allRoomList.add(new StudyRoom(room));
        }
    }

    public ListRooms(String text) {
        allRoomList = new ArrayList<>();
    }


    // Modifies: this
    // Effects: adds new room to existing List of rooms
    public void add(StudyRoom newRoom) {
        allRoomList.add(newRoom);
    }

    // Effects: returns StudyRoom at index i
    public StudyRoom get(int i) {
        return allRoomList.get(i);
    }


    // Effects: returns number of existing rooms
    public int numRooms() {
        return allRoomList.size();
    }


    // Effects: returns List of rooms
    public List<StudyRoom> getRooms() {
        return allRoomList;
    }


    // Effects: Converts  list to json and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", "ListRooms");
        json.put("data", roomsToJson());
        return json;
    }

    // EFFECTS: returns data in StudyRooms class in JsonArray format
    private JSONArray roomsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (StudyRoom t : allRoomList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}

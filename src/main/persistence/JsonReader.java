package persistence;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.ListRooms;
import model.StudyRoom;
import model.TimeSlot;
import org.json.*;

// Taken from the project JsonSerializationDemo
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: constructs reader to read from source file
    public JsonReader(File file) {
        this.source = file.getAbsolutePath();
    }


    // EFFECTS: reads ListRooms from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ListRooms read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStudyRooms(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // EFFECTS: parses workroom from JSON object and returns it
    private ListRooms parseStudyRooms(JSONObject jsonObject) {
        ListRooms lr = new ListRooms("from Reader");
        addRooms(lr, jsonObject);
        return lr;
    }

    // MODIFIES: lr
    // EFFECTS: parses StudyRooms from JSON object and adds them to ListRooms
    private void addRooms(ListRooms lr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        // String name = jsonObject.getString("name");
        int i = 0;
        for (Object json : jsonArray) {
            JSONObject nextRoom = (JSONObject) json;
            String nameR = nextRoom.getString("name");
            lr.add(new StudyRoom(nameR));
            addRoom(lr.get(i), nextRoom);
            i++;
        }
    }

    // MODIFIES: lr
    // EFFECTS: parses StudyRoom's from JSON object and adds it to ListRooms
    private void addRoom(StudyRoom sr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("TimeSlots");
        int i = 0;
        for (Object json : jsonArray) {
            JSONObject nextTimeSlot = (JSONObject) json;
            addTimeSlot(sr, i, nextTimeSlot);
            i++;
        }
    }

    // MODIFIES: lr
    // Effects: parses TimeSlot's from JSON object and adds it to StudyRooms
    private void addTimeSlot(StudyRoom sr, int i, JSONObject jsonObject) {
        String username = jsonObject.getString("Username");
        boolean status = jsonObject.getBoolean("status");
        TimeSlot timeslot = new TimeSlot(username, status);
        sr.changeTimeSlot(i, timeslot);
    }
}

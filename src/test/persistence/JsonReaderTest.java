package persistence;


import model.ListRooms;
import model.StudyRoom;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest  {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ListRooms lr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkroom.json");
        try {
            ListRooms lr = reader.read();
            assertEquals(0, lr.numRooms());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }

        JsonReader readerFile = new JsonReader(new File("./data/testReaderEmptyWorkroom.json"));
        try {
            ListRooms lr = reader.read();
            assertEquals(0, lr.numRooms());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkroom.json");
        try {
            ListRooms lr = reader.read();
            List<StudyRoom> studyRooms = lr.getRooms();
            assertEquals(2, studyRooms.size());
            assertEquals("x100", studyRooms.get(0).getName());
            assertEquals("x200", studyRooms.get(1).getName());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
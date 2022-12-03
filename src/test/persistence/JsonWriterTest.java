package persistence;

import model.ListRooms;
import model.StudyRoom;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest  {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterEmptyListroom() {
        try {
            File myFile = new File("./data/testWriterEmptyWorkroomFile.json");
            ListRooms lr = new ListRooms();
            JsonWriter writer = new JsonWriter(myFile);
            writer.open();
            writer.write(lr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroomFile.json");
            lr = reader.read();
            assertEquals(4, lr.numRooms());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyListroomElse() {
        try {
            File myFile = new File("./data/testWriterEmptyWorkroomFile2");
            ListRooms lr = new ListRooms();
            JsonWriter writer = new JsonWriter(myFile);
            writer.open();
            writer.write(lr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroomFile2.json");
            lr = reader.read();
            assertEquals(4, lr.numRooms());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterInvalidFile() {
        try {
            ListRooms lr = new ListRooms();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyListroomFile() {
        try {
            ListRooms lr = new ListRooms();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(lr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            lr = reader.read();
            assertEquals(4, lr.numRooms());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ListRooms lr = new ListRooms();
            StudyRoom room1 = new StudyRoom("x100");
            StudyRoom room2 = new StudyRoom("x200");
            lr.add(room1);
            lr.add(room2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(lr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            lr = reader.read();
            List<StudyRoom> studyRooms = lr.getRooms();
            assertEquals(6, studyRooms.size());
            assertEquals(room1.getName(), studyRooms.get(4).getName());
            assertEquals(room2.getName(), studyRooms.get(5).getName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.model.ListRooms;
import persistence.model.StudyRoom;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ListRoomsTest {
    public ListRooms lr;
    public StudyRoom sr;
    @BeforeEach
    public void setup() {
        lr = new ListRooms();
        sr = new StudyRoom("test");
    }

    // test to check if add method works as expected
    @Test
    public void addTest() {
        lr.add(sr);
        assertEquals(sr, lr.get(0));
    }

    // test listRooms size method
    @Test
    public void numRoomsTest() {
        lr.add(sr);
        lr.add(sr);
        assertEquals(2, lr.numRooms());
    }
    // test getRooms method
    @Test
    public void getRoomsTest() {
        lr.add(sr);
        lr.add(sr);
        List<StudyRoom> studyRooms = new ArrayList<>();
        studyRooms.add(sr);
        studyRooms.add(sr);
        assertEquals(studyRooms.size(), lr.getRooms().size());
        assertEquals(studyRooms.get(0).getName(), lr.getRooms().get(0).getName());
    }


}

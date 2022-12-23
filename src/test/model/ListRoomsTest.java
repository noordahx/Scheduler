package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertEquals(sr, lr.get(4));
    }

    // test listRooms size method
    @Test
    public void numRoomsTest() {
        lr.add(sr);
        lr.add(sr);
        assertEquals(6, lr.numRooms());
    }
    // test getRooms method
    @Test
    public void getRoomsTest() {
        lr.add(sr);
        lr.add(sr);
        List<StudyRoom> studyRooms = new ArrayList<>();
        studyRooms.add(sr);
        studyRooms.add(sr);
        assertEquals(studyRooms.size()+4, lr.getRooms().size());
        assertEquals(studyRooms.get(0).getName(), lr.getRooms().get(4).getName());
    }


}

package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimeSlotTest {
    TimeSlot slot;

    @BeforeEach
    public void setup() {
        slot = new TimeSlot();
    }

    // test a book method
    @Test
    public void testBook() {
        slot.book("User");
        assertEquals("User", slot.getUserName());
        assertFalse(slot.getStatus());
    }

    // test delete booking method
    @Test
    public void testDelete() {
        slot.book("User");
        slot.delete();
        assertEquals("", slot.getUserName());
        assertTrue(slot.getStatus());
    }
}
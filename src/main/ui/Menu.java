package ui;

import model.StudyRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Menu class for console UI
public class Menu {

    // Const list of all rooms
    private static final String[] ROOMS = {
            "X100",
            "X200",
            "X300",
            "X400"
    };

    private final List<StudyRoom> studyRoomList;
    private final Scanner in = new Scanner(System.in);

    // Modifies: this
    // Effects: instantiates list of StudyRoom objects and calls startApp() method
    public Menu() {
        this.studyRoomList = new ArrayList<>();
        for (int i = 0; i < ROOMS.length; i++) {
            studyRoomList.add(new StudyRoom());
        }
        startApp();
    }

    // Requires: console input integer between 1 - 4 or q
    // Modifies: this
    // Effects: prints out menu options and redirects to suitable method based on user input
    private void startApp() {
        printBanner();
        while (true) {
            printMenu();
            String userInput = in.nextLine();
            switch (userInput) {
                case "1":
                    showSchedule();
                    continue;
                case "2":
                    bookStudyRoom();
                    continue;
                case "3":
                    deleteBooking();
                    continue;
                case "4":
                    alterBooking();
                    continue;
                case "q":
                    return;
            }
        }
    }

    // Modifies: this
    // Effects: lets user choose room and prints out study room availability
    private void showSchedule() {
        int room = chooseRoom();
        if (room != -1) {
            List<String> schedule = studyRoomList.get(room - 1).getSchedule();
            for (int i = 9; i < 18; i++) {
                System.out.printf("%02d:00 - %02d:00 | %s \n",
                        i, i + 1, schedule.get(i - 9));
            }
        }

    }


    // Requires: console input integer between 9 - 17 and chosen timeslot is Free
    // Modifies: this
    // Effects: lets user choose a room and timeslot1 and reserves a room if possible
    private void bookStudyRoom() {
        int room = chooseRoom();
        if (room != -1) {
            int timeSlot = chooseTimeSlot();
            if (timeSlot != -1) {
                if (!studyRoomList.get(room - 1).getAvailability(timeSlot)) {
                    System.out.println("Timeslot is already booked, choose different one!");
                } else {
                    System.out.println("Type your name:");
                    String name = in.nextLine();
                    studyRoomList.get(room - 1).bookTimeSlot(timeSlot, name);
                    System.out.println("Study room is successfully booked!");
                }
            }
        }
    }


    // Requires: console input integer between 9 - 17 and booking to be deleted exists
    // Modifies: this
    // Effects: lets user choose a room and timeslot and deletes that timeslot
    private void deleteBooking() {
        int room = chooseRoom();
        if (room != -1) {
            List<String> schedule = studyRoomList.get(room - 1).getSchedule();
            for (int i = 9; i < 18; i++) {
                System.out.printf("%02d:00 - %02d:00 | %s \n",
                        i, i + 1, schedule.get(i - 9));
            }
            int timeSlot = chooseTimeSlot();
            if (timeSlot != -1) {
                studyRoomList.get(room - 1).deleteTimeSlot(timeSlot);
                System.out.println("Timeslot is successfully deleted!");
            }
        }
    }
    // Requires: console input integer between 9 - 17 and existing old Booking
    // Modifies: this
    // Effects: lets user choose a room, old and new timeslot to change the booking
    private void alterBooking() {
        int room = chooseRoom();
        if (room != -1) {
            List<String> schedule = studyRoomList.get(room - 1).getSchedule();
            for (int i = 9; i < 18; i++) {
                System.out.printf("%02d:00 - %02d:00 | %s \n",
                        i, i + 1, schedule.get(i - 9));
            }

            System.out.println("Old Booking");
            int oldTimeSlot = chooseTimeSlot();
            String oldName = "";
            if (oldTimeSlot != -1) {
                oldName = studyRoomList.get(room - 1).getTimeSlotUser(oldTimeSlot);
                studyRoomList.get(room - 1).deleteTimeSlot(oldTimeSlot);
            }

            System.out.println("New Booking");
            int newTimeSlot = chooseTimeSlot();
            if (newTimeSlot != -1) {
                studyRoomList.get(room - 1).bookTimeSlot(newTimeSlot, oldName);
                System.out.println("Timeslot is successfully changed");
            }
        }
    }

    // Effects: prints out menu list
    private void printMenu() {
        System.out.println("Choose an option:");
        System.out.println("1 >> Show schedule");
        System.out.println("2 >> Book a Study Room");
        System.out.println("3 >> Delete Booking");
        System.out.println("4 >> Alter Booking");
    }

    // Requires: console input integer between 1 - 4
    // Effects: lets user choose a room and returns room id.
    private int chooseRoom() {
        do {
            System.out.println("Choose a room number or type [q] to return:");
            for (int i = 0; i < ROOMS.length; i++) {
                System.out.printf("%d) %s \n", i + 1, ROOMS[i]);
            }
            String input = in.nextLine();
            if (input.equals("q")) {
                return -1;
            } else if (!(!isNumeric(input) || Integer.parseInt(input) < 1
                    || Integer.parseInt(input) > ROOMS.length)) {
                return Integer.parseInt(input);
            }
        } while (true);
    }

    // Requires: console input integer between 9 - 17
    // Effects: lets user choose a timeslot and returns timeslot number.
    private int chooseTimeSlot() {
        System.out.println("Choose a Slot from 9 to 17 or type [q] to quit:");
        do {
            String input = in.nextLine();
            if (input.equals("q")) {
                return -1;
            } else if (!(!isNumeric(input) || Integer.parseInt(input) < 9
                    || Integer.parseInt(input) > 17)) {
                return Integer.parseInt(input);
            }
        } while (true);
    }


    // Effects: checks if string given can be converted to integer.
    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // Effects: prints banner
    private void printBanner() {
        List<String> output = new ArrayList<>();
        output.add("oooooooooo.                      oooo             ooooo ooooooooooooo .o. ");
        output.add("`888'   `Y8b                     `888             `888' 8'   888   `8 888");
        output.add(" 888     888  .ooooo.   .ooooo.   888  oooo        888       888      888 ");
        output.add(" 888oooo888' d88' `88b d88' `88b  888 .8P'         888       888      Y8P ");
        output.add(" 888    `88b 888   888 888   888  888888.          888       888      `8'");
        output.add(" 888    .88P 888   888 888   888  888 `88b.        888       888      .o. ");
        output.add("o888bood8P'  `Y8bod8P' `Y8bod8P' o888o o888o      o888o     o888o     Y8P ");
        for (String s : output) {
            System.out.println(s);
        }
    }
}
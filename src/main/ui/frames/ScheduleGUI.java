package ui.frames;

import model.ListRooms;
import model.StudyRoom;
import ui.MainGUI;

import javax.swing.*;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

// UI class that allows user to view the schedule
public class ScheduleGUI extends JFrame implements ActionListener {
    private JFrame scheduleFrame;
    private ListRooms allRoomsList;

    // Requires: allRoomList != null
    // Modifies: this
    // Effects: copies passed parameter and creates UI
    public ScheduleGUI(ListRooms allRoomList) {
        this.allRoomsList = allRoomList;
        System.out.println(allRoomList.getRooms());
        createUI();
    }

    // Modifies: this
    // Effects: adds a list of buttons to a frame pane
    private void addComponentsToPane(Container pane) {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(4,1,5,5));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        addActionButtons(btnPanel);
        pane.add(btnPanel, BorderLayout.CENTER);
    }

    // Modifies: this
    // Effects: adds a list of buttons to a btnPanel
    private void addActionButtons(JPanel btnPanel) {
        for (StudyRoom sr : allRoomsList.getRooms()) {
            addButton(btnPanel, sr.getName(), sr.getName());
        }
    }

    // Modifies: this
    // Effects: creates a single button with parameters specified in parameter list
    private void addButton(JPanel btnPanel, String text, String actionCommand) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200,75));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        btnPanel.add(button);
    }

    // Modifies: this
    // Effects: when called, creates new frame and puts UI elements to that frame
    private void createUI() {
        scheduleFrame = new JFrame("Schedule");
        addComponentsToPane(scheduleFrame.getContentPane());
        scheduleFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                new MainGUI(allRoomsList);
                scheduleFrame.dispose();
            }
        });
        scheduleFrame.pack();
        scheduleFrame.setLocationRelativeTo(null);
        scheduleFrame.setVisible(true);
    }

    // Modifies: this
    // Effects: defines what each button will do when clicked
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int i;
        for (i = 0; i < allRoomsList.numRooms(); i++) {
            if (actionEvent.getActionCommand() == allRoomsList.get(i).getName()) {
                break;
            }
        }
        JFrame srFrame = studyRoomFrame(actionEvent.getActionCommand(), i);
        scheduleFrame.setVisible(false);
    }

    // Modifies: this
    // Effects: created new frame that lists schedule for a single study room
    private JFrame studyRoomFrame(String roomName, int index) {
        JFrame srFrame = new JFrame(roomName);
        srFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                scheduleFrame.setVisible(true);
                srFrame.dispose();
            }
        });
        JPanel schedulePanel = new JPanel();
        schedulePanel.setLayout(new GridLayout(9,1,5,5));
        schedulePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        addSchedule(schedulePanel, index);
        srFrame.getContentPane().add(schedulePanel, BorderLayout.CENTER);
        srFrame.pack();
        srFrame.setLocationRelativeTo(null);
        srFrame.setVisible(true);
        return srFrame;
    }

    // Modifies: this
    // Effects: goes through the schedule and adds JLabel texts that show the status of the room
    private void addSchedule(JPanel schedulePanel, int index) {
        List<String> schedule = allRoomsList.get(index).getSchedule();
        for (int i = 9; i < 18; i++) {
            JLabel text = new JLabel(String.format("%02d:00 - %02d:00 | %s \n",
                    i, i + 1, schedule.get(i - 9)));
            text.setHorizontalAlignment(SwingConstants.CENTER);
            schedulePanel.add(text);
        }
    }
}

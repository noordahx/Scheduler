package ui.frames;

import model.ListRooms;
import model.StudyRoom;
import ui.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

// class that helps user to change booking time
public class ChangeGUI extends JFrame implements ActionListener {
    private ListRooms allRoomsList;
    private JFrame changeFrame;
    private List<String> schedule;
    private List<String> optionsList = new ArrayList<>();

    // Requires: allRoomsList != null
    // Modifies: this
    // Effects: copies passed parameter to local variable and creates UI
    public ChangeGUI(ListRooms allRoomsList) {
        this.allRoomsList = allRoomsList;
        createUI();
    }

    // Modifies: this
    // Effects: creates new frame and adds UI components
    private void createUI() {
        changeFrame = new JFrame("Change Reservation");
        addComponentsToPane(changeFrame.getContentPane());
        changeFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                new MainGUI(allRoomsList);
                changeFrame.dispose();
            }
        });
        changeFrame.pack();
        changeFrame.setLocationRelativeTo(null);
        changeFrame.setVisible(true);
    }

    // Modifies: this
    // Effects: adds multiple button components to the frame
    private void addComponentsToPane(Container pane) {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(4,1,5,5));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        addActionButtons(btnPanel);
        pane.add(btnPanel, BorderLayout.CENTER);
    }

    // Modifies: this
    // Effects: creates multiple buttons and adds them to passed parameter list
    private void addActionButtons(JPanel btnPanel) {
        for (StudyRoom sr : allRoomsList.getRooms()) {
            addButton(btnPanel, sr.getName(), sr.getName());
        }
    }

    // Modifies: this
    // Effects: adds a single button
    private void addButton(JPanel btnPanel, String text, String actionCommand) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200,75));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        btnPanel.add(button);
    }

    // Modifies: this
    // Effects: when button is clicked, opens the required srFrame
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int i;
        for (i = 0; i < allRoomsList.numRooms(); i++) {
            if (actionEvent.getActionCommand().equals(allRoomsList.get(i).getName())) {
                break;
            }
        }
        JFrame srFrame = studyRoomFrame(actionEvent.getActionCommand(), i);
        changeFrame.setVisible(false);
    }

    // Effects: creates new popup frame that lists studyrooms
    private JFrame studyRoomFrame(String roomName, int index) {
        JFrame srFrame = new JFrame(roomName);
        srFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                changeFrame.setVisible(true);
                srFrame.dispose();
            }
        });
        JPanel schedulePanel = new JPanel();
        schedulePanel.setLayout(new GridLayout(9,1,5,5));
        schedulePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        addSlotButtons(schedulePanel, index);
        srFrame.getContentPane().add(schedulePanel, BorderLayout.CENTER);
        srFrame.pack();
        srFrame.setLocationRelativeTo(null);
        srFrame.setVisible(true);
        return srFrame;
    }

    // Modifies: this (schedulePanel)
    // Effects: adds required buttons to passed parameter schedule panel
    private void addSlotButtons(JPanel schedulePanel, int index) {
        schedule = allRoomsList.get(index).getSchedule();
        for (int i = 9; i < 18; i++) {
            JButton button = new JButton(String.format("%02d:00 - %02d:00\n",
                    i, i + 1));
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.setActionCommand(Integer.toString(i));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    JFrame popUpFrame = popUp(Integer.valueOf(actionEvent.getActionCommand()), index);
                }
            });
            if (schedule.get(i - 9).equals("Free")) {
                button.setEnabled(false);
            }
            schedulePanel.add(button);

        }
    }


    // Effects: creates and returns new popup frame that will ask user's confirmation
    private JFrame popUp(int time, int roomIndex) {
        JFrame popUpFrame = new JFrame("Registration Prompt");
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(4,10,10,10));
        addPopUpItems(popUpFrame, panel, time, roomIndex);

        popUpFrame.getContentPane().add(panel, BorderLayout.CENTER);
        popUpFrame.pack();
        popUpFrame.setLocationRelativeTo(null);
        popUpFrame.setVisible(true);
        return popUpFrame;
    }

    // Modifies: (popUpFrame, panel) this
    // Effects: adds UI elements to pupUpFrame and panel
    private void addPopUpItems(JFrame popUpFrame, Container panel, int time, int roomIndex) {
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).equals("Free")) {
                optionsList.add(Integer.toString(i + 9));
            }
        }
        String[] options = optionsList.toArray(new String[0]);
        JLabel nameLabel = new JLabel("Change old booking to:");
        JComboBox<String> comboBox = new JComboBox<>(options);
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                allRoomsList.get(roomIndex).deleteTimeSlot(time);
                int selected = Integer.valueOf(comboBox.getItemAt(comboBox.getSelectedIndex()));
                allRoomsList.get(roomIndex).bookTimeSlot(selected, allRoomsList.get(roomIndex).getTimeSlotUser(time));
                popUpFrame.dispose();
                changeFrame.dispose();
                new ChangeGUI(allRoomsList);
            }
        });
        panel.add(nameLabel);
        panel.add(comboBox);
        panel.add(submit);
    }
}

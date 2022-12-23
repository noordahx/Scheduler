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
import java.util.List;

// UI class that allows to delete bookings
public class DeleteGUI extends JFrame implements ActionListener {
    private ListRooms allRoomsList;
    private JFrame deleteFrame;

    // Requires: allRoomsList != null
    // Modifies: this
    // Effects: copies passed parameter to local variable and createsUI
    public DeleteGUI(ListRooms allRoomsList) {
        this.allRoomsList = allRoomsList;
        createUI();
    }

    // Modifies: this
    // Effects: creates new frame and adds required elements to that frame
    private void createUI() {
        deleteFrame = new JFrame("Delete Reservation");
        addComponentsToPane(deleteFrame.getContentPane());
        deleteFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                new MainGUI(allRoomsList);
                deleteFrame.dispose();
            }
        });
        deleteFrame.pack();
        deleteFrame.setLocationRelativeTo(null);
        deleteFrame.setVisible(true);
    }

    // Modifies: (pane) this
    // Effects: adds list of buttons to pane passed as parameter
    private void addComponentsToPane(Container pane) {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(4,1,5,5));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        addActionButtons(btnPanel);
        pane.add(btnPanel, BorderLayout.CENTER);
    }

    // Modifies: (btnPanel) this
    // Effects: adds a list of buttons each reffers to a single room
    private void addActionButtons(JPanel btnPanel) {
        for (StudyRoom sr : allRoomsList.getRooms()) {
            addButton(btnPanel, sr.getName(), sr.getName());
        }
    }

    // Modifies: this (btnPanel)
    // Effects: when called, creates a button in a btnPanel with a name text and action actionCommand
    private void addButton(JPanel btnPanel, String text, String actionCommand) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200,75));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        btnPanel.add(button);
    }

    // Modifies: this
    // Effects: when action is performed, deleted this frame and creates new one
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int i;
        for (i = 0; i < allRoomsList.numRooms(); i++) {
            if (actionEvent.getActionCommand().equals(allRoomsList.get(i).getName())) {
                break;
            }
        }
        JFrame srFrame = studyRoomFrame(actionEvent.getActionCommand(), i);
        deleteFrame.setVisible(false);
    }

    // Modifies: this
    // Effects: creates a new frame with a list of booking that can be deleted
    private JFrame studyRoomFrame(String roomName, int index) {
        JFrame srFrame = new JFrame(roomName);
        srFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                deleteFrame.setVisible(true);
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

    // Modifies: this
    // Effects: adds a list of buttons and makes some of then not clickable if booking does not exist
    private void addSlotButtons(JPanel schedulePanel, int index) {
        List<String> schedule = allRoomsList.get(index).getSchedule();
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

    // Effects: creates popUp page that asks for confirmation
    private JFrame popUp(int time, int roomIndex) {
        JFrame popUpFrame = new JFrame("Registration Prompt");
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(3,10,10,10));
        JLabel nameLabel = new JLabel("Are you sure you want to delete booking for "
                + allRoomsList.get(roomIndex).getTimeSlotUser(time));
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                allRoomsList.get(roomIndex).deleteTimeSlot(time);
                popUpFrame.dispose();
                deleteFrame.dispose();
                new DeleteGUI(allRoomsList);
            }
        });
        panel.add(nameLabel);
        panel.add(Box.createRigidArea(new Dimension(5,0)));
        panel.add(submit);
        popUpFrame.getContentPane().add(panel, BorderLayout.CENTER);
        popUpFrame.pack();
        popUpFrame.setLocationRelativeTo(null);
        popUpFrame.setVisible(true);
        return popUpFrame;
    }
}

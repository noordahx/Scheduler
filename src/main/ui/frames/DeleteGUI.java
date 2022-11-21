package ui.frames;

import persistence.model.ListRooms;
import persistence.model.StudyRoom;
import ui.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class DeleteGUI extends JFrame implements ActionListener {
    private ListRooms allRoomsList;
    private JFrame deleteFrame;

    public DeleteGUI(ListRooms allRoomsList) {
        this.allRoomsList = allRoomsList;
        createUI();
    }

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

    private void addComponentsToPane(Container pane) {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(4,1,5,5));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        addActionButtons(btnPanel);
        pane.add(btnPanel, BorderLayout.CENTER);
    }

    private void addActionButtons(JPanel btnPanel) {
        for (StudyRoom sr : allRoomsList.getRooms()) {
            addButton(btnPanel, sr.getName(), sr.getName());
        }
    }

    private void addButton(JPanel btnPanel, String text, String actionCommand) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200,75));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        btnPanel.add(button);
    }

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

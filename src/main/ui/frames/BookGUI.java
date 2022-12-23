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

// class that creates book study room page
public class BookGUI extends JFrame implements ActionListener {
    private ListRooms allRoomsList;
    private JFrame bookFrame;

    // Requires: allRoomList != null
    // Modifies: this
    // Effects: copies passed parameter to the local variable and creates UI
    public BookGUI(ListRooms allRoomList) {
        this.allRoomsList = allRoomList;
        createUI();
    }

    // Modifies: this
    // Effects: adds buttons with actions
    private void addActionButtons(JPanel btnPanel) {
        for (StudyRoom sr : allRoomsList.getRooms()) {
            addButton(btnPanel, sr.getName(), sr.getName());
        }
    }

    // Modifies: this
    // Effects: creates and adds a single button with action command to panel
    private void addButton(JPanel btnPanel, String text, String actionCommand) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200,75));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        btnPanel.add(button);
    }

    // Modifies: this
    // Effects: adds buttons with room names
    private void addComponentsToPane(Container pane) {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(4,1,5,5));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        addActionButtons(btnPanel);
        pane.add(btnPanel, BorderLayout.CENTER);
    }

    // Modifies: this
    // Effects: creates UI of the BookGUI page
    private void createUI() {
        bookFrame = new JFrame("Book Study Space");
        addComponentsToPane(bookFrame.getContentPane());
        bookFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                new MainGUI(allRoomsList);
                bookFrame.dispose();
            }
        });

        bookFrame.pack();
        bookFrame.setLocationRelativeTo(null);
        bookFrame.setVisible(true);
    }

    // Modifies: this
    // Effects: does according action when referred by actionEvent parameter
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int i;
        for (i = 0; i < allRoomsList.numRooms(); i++) {
            if (actionEvent.getActionCommand().equals(allRoomsList.get(i).getName())) {
                break;
            }
        }
        JFrame srFrame = studyRoomFrame(actionEvent.getActionCommand(), i);
        bookFrame.setVisible(false);
    }

    // Effects: creates new frame that lists all available study spaces
    private JFrame studyRoomFrame(String roomName, int index) {
        JFrame srFrame = new JFrame(roomName);
        srFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                bookFrame.setVisible(true);
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
    // Effects: adds multiple buttons with timeslots available for booking
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
            if (!schedule.get(i - 9).equals("Free")) {
                button.setEnabled(false);
            }
            schedulePanel.add(button);

        }
    }

    // Modifies: this
    // Effects: creates popUp page that asks for username and confirmation
    private JFrame popUp(int time, int roomIndex) {
        JFrame popUpFrame = new JFrame("Registration Prompt");
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JLabel nameLabel = new JLabel("Type your name:");
        JTextField textField = new JTextField(20);
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                allRoomsList.get(roomIndex).bookTimeSlot(time, textField.getText());
                popUpFrame.dispose();
                bookFrame.dispose();
                new BookGUI(allRoomsList);
            }
        });
        panel.add(nameLabel, textField);
        panel.add(textField);
        panel.add(submit);
        popUpFrame.getContentPane().add(panel, BorderLayout.CENTER);
        popUpFrame.pack();
        popUpFrame.setLocationRelativeTo(null);
        popUpFrame.setVisible(true);
        return popUpFrame;
    }


}

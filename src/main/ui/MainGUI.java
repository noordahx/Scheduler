package ui;

import persistence.JsonReader;
import persistence.JsonWriter;
import model.ListRooms;
import ui.frames.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

// class that creates main menu GUI
public class MainGUI extends JFrame implements ActionListener {
    private JFrame frame;
    private ListRooms allRoomList;
    private String[] buttonNames =  {"Show schedule", "Book study space", "Delete booking", "Change booking"};
    private String[] actionCommands = {"schedule", "book", "delete", "change"};

    // Modifies: this
    // Effects: default constructor, creates main menu GUI
    public MainGUI() {
        createUI();
    }

    // Requires: listRooms != null
    // Modifies: this
    // Effects: constructor with listRooms passed, creates main menu GUI
    public MainGUI(ListRooms listRooms) {
        this.allRoomList = listRooms;
        createUI();
    }


    // Modifies: this
    // Effects: adds logo and menu buttons to provided panel
    public void addComponentsToPane(Container pane) {
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("./src/images/logo.png"));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        pane.add(logo, BorderLayout.PAGE_START);
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(4, 1, 5, 5));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        addActionButtons(btnPanel);
        pane.add(btnPanel, BorderLayout.CENTER);
    }


    // Modifies: this
    // Effects: adds buttons with actions to main menu
    private void addActionButtons(JPanel btnPanel) {
        String[] buttonNames =  {"Show schedule", "Book study space", "Delete booking", "Change booking"};
        String[] actionCommands = {"schedule", "book", "delete", "change"};
        for (int i = 0; i < buttonNames.length; i++) {
            addButton(btnPanel, buttonNames[i], actionCommands[i]);
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
    // Effects: adds menu to Main page
    private void addMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem menuItemLoad = new JMenuItem("Load workspace");
        menuItemLoad.setActionCommand("load");
        menuItemLoad.addActionListener(this);
        menu.add(menuItemLoad);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }

    // Modifies: this
    // Effects: does according action when referred by actionEvent parameter
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println(actionEvent.getActionCommand());
        if (actionEvent.getActionCommand().equals("load")) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                loadData(selectedFile);
            }
        } else if (actionEvent.getActionCommand().equals("schedule")) {
            new ScheduleGUI(allRoomList == null ? new ListRooms() : allRoomList);
            frame.dispose();
        } else if (actionEvent.getActionCommand().equals("book")) {
            new BookGUI(allRoomList == null ? new ListRooms() : allRoomList);
            frame.dispose();
        } else if (actionEvent.getActionCommand().equals("delete")) {
            new DeleteGUI(allRoomList);
            frame.dispose();
        } else if (actionEvent.getActionCommand().equals("change")) {
            new ChangeGUI(allRoomList);
            frame.dispose();
        }
    }

    // Modifies: this
    // Effects: reads data from selected file
    private void loadData(File file) {
        try {
            JsonReader jsonReader = new JsonReader(file);
            allRoomList = null;
            allRoomList = jsonReader.read();
            System.out.println("Reader" + allRoomList.getRooms());
            System.out.println("Loaded data from " + file.getName());
            JOptionPane.showMessageDialog(frame, "Loaded data from " + file.getName());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "An IOException occurred, creating new ListRooms object ...");
            System.out.println("An IOException occurred, creating new ListRooms object ...");
        }
    }


    // Modifies: this
    // Effects: creates main menu GUI
    private void createUI() {
        frame = new JFrame("Main menu");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                saveData();
            }
        });
        addMenu(frame);
        addComponentsToPane(frame.getContentPane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Modifies:
    // Effects: saves current status of the program to selected external file
    private void saveData() {
        try {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File fileToSave = chooser.getSelectedFile();
                JsonWriter jsonWriter = new JsonWriter(fileToSave);
                jsonWriter.open();
                jsonWriter.write(allRoomList);
                jsonWriter.close();
            }
        } catch (Exception e) {
            System.out.println("An Exception occurred.");
            JOptionPane.showMessageDialog(frame, "File Not Found");
        }
    }

}

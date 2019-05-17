
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import static java.util.Arrays.sort;
import javafx.scene.control.Alert;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
Date:       04/14/2018
Author:     Andrew Masiero
Filename:   LineupApp.java
Purpose:    This application displays a baseball team's batting lineup
 */
public class LineupAppASM extends JFrame {
    // declare instance variables that need to be accessed by event handlers
    private JTextField teamNameField;
    private JRadioButton homeRadioButton;
    private JRadioButton visitorRadioButton;
    private JTextField[] playerNameField = new JTextField[9];
    private JComboBox[] positionComboBox = new JComboBox[9];
    private int[] positionNumber = new int[9];
    private String homeVisitorStatus = ("home team");
    private String[] positionNames = {"Choose a position", "Pitcher", "Catcher",
            "First Base", "Second Base", "Third Base", "Short Stop", "Left Field",
            "Center Field", "Right Field"};
    private String[] playerPositions = new String[9];
    
    public LineupAppASM() {
        initComponents();
    }
    
    private void initComponents() {
        // set look and feel to the current OS
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }
        // set title, default close operation, and location of frame
        setTitle("Lineup");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        
        // create dimension object for textFields and combo boxes
        Dimension dim = new Dimension(175, 25);

        // TEAM INFORMATION
        // set up components for team information panel
        // team name text field
        teamNameField = new JTextField();
        teamNameField.setPreferredSize(dim);
        teamNameField.setMinimumSize(dim);
        
        // radio buttons
        ButtonGroup homeVisitorGroup = new ButtonGroup();
        homeRadioButton = new JRadioButton("Home");
        visitorRadioButton = new JRadioButton("Visitor");
        homeVisitorGroup.add(homeRadioButton);
        homeVisitorGroup.add(visitorRadioButton);
        homeRadioButton.setSelected(true);
        //// ASK HILEMAN HOW TO RETURN A STRING
        //// Do I need to? Do I need this method?
        homeRadioButton.addActionListener(event -> homeVisitorRadioButtonClicked());
        visitorRadioButton.addActionListener(event -> homeVisitorRadioButtonClicked());
        
        // set up team information panel and add components
        JPanel teamPanel = new JPanel();
        teamPanel.setBorder(BorderFactory.createTitledBorder("Team Information"));
        teamPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        teamPanel.add(new JLabel("Team Name:"));
        teamPanel.add(teamNameField);
        teamPanel.add(homeRadioButton);
        teamPanel.add(visitorRadioButton);
        
        // set up player information panel and add components
        JPanel playerPanel = new JPanel();
        playerPanel.setBorder(BorderFactory.createTitledBorder("Player Information"));
        playerPanel.setLayout(new GridBagLayout());
        // loop adds labels, text fields, and combo boxes to colulmns 0, 1, and 2
        for (int i = 0; i < 9; i++) {
            // add labels 1-9
            playerPanel.add(new JLabel((i + 1) + ":"), getConstraints(0, i));
            
            // add text fields for player names and set size
            playerPanel.add(playerNameField[i] = new JTextField(), getConstraints(1, i));
            playerNameField[i].setPreferredSize(dim);
            playerNameField[i].setMinimumSize(dim);
            
            // add combo boxes to each row to select player position
            playerPanel.add(positionComboBox[i] = new JComboBox(positionNames), getConstraints(2, i));
            positionComboBox[i].setPreferredSize(dim);
            positionComboBox[i].setMinimumSize(dim);
            
        }
        
        // ACCEPT AND EXIT BUTTONS
        // set up accept and exit buttons
        JButton acceptButton = new JButton("Accept");
        acceptButton.setMnemonic('a');
        acceptButton.addActionListener(event -> acceptButtonClicked());
        JButton exitButton = new JButton("Exit");
        exitButton.setMnemonic('x');
        exitButton.addActionListener(event -> exitButtonClicked());
        
        // set up button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(acceptButton);
        buttonPanel.add(exitButton);
        
        // add panels to frame
        add(teamPanel, BorderLayout.NORTH);
        add(playerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // final sizing and visibility statements
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    } // end initComponents()
    
    // helper method for getting a GridBagConstraints object
    private GridBagConstraints getConstraints(int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 5, 0, 5);
        c.gridx = x;
        c.gridy = y;
        return c;
    }
    
    private void homeVisitorRadioButtonClicked() {
        /*
        String s = "";
        if (homeRadioButton.isSelected()) {
            s = "(home team)";
        } else if (visitorRadioButton.isSelected()) {
            s = "(visitor team)";
        }
        return s;
        */
        return;
    }
    
    private void acceptButtonClicked() {
        // validate data before displaying window
        Validation v = new Validation();
        String e = "";
        // to monitor changes to the length of string e
        // and avoid duplicate error messages
        int eLength = 0;
                
        // check for empty team name
        e += v.isPresent(teamNameField.getText(), "Team name");
        if(e.length() > eLength) {
            eLength = e.length();
        }
        // check for empty player names
        for (int i = 0; i < 9; i++) {
            e += v.isPresent(playerNameField[i].getText(), "Player name");
            if(e.length() > eLength) {
                eLength = e.length();
                break;
            }
        }
        // check comboboxes for default poition
        for (int i = 0; i < 9; i++) {
            if(positionComboBox[i].getSelectedItem().equals(positionNames[0])) {
                e += "Player positions are required.\n";
                break;
            }
            // get selected position
            playerPositions[i] = (String) positionComboBox[i].getSelectedItem();
            positionNumber[i] = positionComboBox[i].getSelectedIndex();
        }
        // sort positionNumber array and check for duplicate player positions
        sort(positionNumber);
        for (int i = 0; i < positionNumber.length; i ++) {
            if (positionNumber[i] != i + 1) {
                e += "Cannot assign positions to more than one player.\n";
                break;
            }
        }
        // end validation section
        
        // if input data is OK, the new message window is displayed
        // else, an error window will pop up
        if(e.isEmpty()) {
            //// do the stuff
            // start with the team name
            String lineup = teamNameField.getText();
            // which radio button is selected?
            if(homeRadioButton.isSelected()) {
                lineup += " (home team)";
            } else {
                lineup += " (visitor team)";
            } // end radio if
            lineup += "\n\n";
            // loop to add player names and positions
            for (int i = 0; i < playerNameField.length; i ++) {
                lineup += playerNameField[i].getText() + ", ";
                lineup += (String) positionComboBox[i].getSelectedItem() + "\n";
            }
            JOptionPane.showMessageDialog(this, lineup, "Lineup", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, e, "Error = Invalid Data", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exitButtonClicked() {
        System.exit(0);
    }
    
    // main method
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            JFrame frame = new LineupAppASM();
        });
    }
    
}

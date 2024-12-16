import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Class CommunityFoodOrgVolunteeringGUI
 * Author: Andrew Parkes
 */
public class CommunityFoodOrgVolunteeringGUI {
    private JFrame frame;
    private JTextField txtFullName, txtAge, txtDistance, txtLatitude, txtLongitude, txtAddress, txtDonation;
    private JCheckBox chkNeedsTransport;
    private JComboBox<String> cmbDayAvailable;
    private JTextField txtStartTime, txtEndTime;
    private JButton btnAddVolunteer, btnSignUp;
    private JList<String> orgList;
    private DefaultListModel<String> orgListModel;

    private ArrayList<Volunteer> volunteers;
    private ArrayList<CommunityFoodOrg> orgs;

    public CommunityFoodOrgVolunteeringGUI(VolunteeringManager volManager) {
        this.volunteers = volManager.getVolunteers();
        this.orgs = volManager.getOrgs();
        initializeGUI();
    }

    /**
     * Initializes the GUI layout and components.
     */
    private void initializeGUI() {
        frame = new JFrame("Community Food Org Volunteering");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Input Panel for Volunteer Details
        JPanel inputPanel = new JPanel(new GridLayout(6, 4, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Volunteer Details"));

        addLabeledField(inputPanel, "Full Name:", txtFullName = new JTextField());
        addLabeledField(inputPanel, "Age:", txtAge = new JTextField());
        addLabeledField(inputPanel, "Distance (miles):", txtDistance = new JTextField());
        addLabeledField(inputPanel, "Needs Transport?", chkNeedsTransport = new JCheckBox());
        addLabeledField(inputPanel, "Available Day:", cmbDayAvailable = new JComboBox<>(getDaysOfWeek()));
        addLabeledField(inputPanel, "Start Time (HH:MM):", txtStartTime = new JTextField());
        addLabeledField(inputPanel, "End Time (HH:MM):", txtEndTime = new JTextField());
        addLabeledField(inputPanel, "Latitude:", txtLatitude = new JTextField());
        addLabeledField(inputPanel, "Longitude:", txtLongitude = new JTextField());
        addLabeledField(inputPanel, "Address:", txtAddress = new JTextField());
        addLabeledField(inputPanel, "Donation (lbs):", txtDonation = new JTextField());

        btnAddVolunteer = new JButton("Add Volunteer");
        inputPanel.add(btnAddVolunteer);

        // Organization List Panel
        JPanel orgPanel = new JPanel(new BorderLayout());
        orgPanel.setBorder(BorderFactory.createTitledBorder("Matching Organizations"));
        orgListModel = new DefaultListModel<>();
        orgList = new JList<>(orgListModel);
        orgPanel.add(new JScrollPane(orgList), BorderLayout.CENTER);
        btnSignUp = new JButton("Sign Up Volunteer");
        orgPanel.add(btnSignUp, BorderLayout.SOUTH);

        // Add panels to frame
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(orgPanel, BorderLayout.SOUTH);

        // Event Listeners
        btnAddVolunteer.addActionListener(this::addVolunteer);
        btnSignUp.addActionListener(this::signUpVolunteer);

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Adds a labeled field to the input panel.
     */
    private void addLabeledField(JPanel panel, String label, JComponent field) {
        panel.add(new JLabel(label));
        panel.add(field);
    }

    /**
     * Retrieves days of the week.
     * @return Array of day names.
     */
    private String[] getDaysOfWeek() {
        return new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    }

    /**
     * Handles adding a new volunteer.
     */
    private void addVolunteer(ActionEvent e) {
        try {
            String fullName = txtFullName.getText();
            int age = Integer.parseInt(txtAge.getText());
            double distance = Double.parseDouble(txtDistance.getText());
            boolean needsTransport = chkNeedsTransport.isSelected();
            String dayAvailable = (String) cmbDayAvailable.getSelectedItem();

            String[] start = txtStartTime.getText().split(":");
            String[] end = txtEndTime.getText().split(":");
            int startHour = Integer.parseInt(start[0]);
            int startMinute = Integer.parseInt(start[1]);
            int endHour = Integer.parseInt(end[0]);
            int endMinute = Integer.parseInt(end[1]);

            double latitude = Double.parseDouble(txtLatitude.getText());
            double longitude = Double.parseDouble(txtLongitude.getText());
            String address = txtAddress.getText();
            double donation = Double.parseDouble(txtDonation.getText());

            Location location = new Location(latitude, longitude, address, "", "", "");
            TimeFrame timeAvailable = new TimeFrame(startHour, startMinute, endHour, endMinute);

            Volunteer volunteer = new Volunteer(fullName, fullName, age, location, dayAvailable, timeAvailable, distance, needsTransport);
            volunteer.setDonation(donation);

            volunteers.add(volunteer);
            JOptionPane.showMessageDialog(frame, "Volunteer added successfully!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please check all fields.");
        }
    }

    /**
     * Handles signing up a volunteer to a selected organization.
     */
    private void signUpVolunteer(ActionEvent e) {
        int selectedIndex = orgList.getSelectedIndex();
        if (selectedIndex >= 0) {
            CommunityFoodOrg selectedOrg = orgs.get(selectedIndex);
            JOptionPane.showMessageDialog(frame, "Signed up for " + selectedOrg.getName());
        } else {
            JOptionPane.showMessageDialog(frame, "Please select an organization.");
        }
    }
}
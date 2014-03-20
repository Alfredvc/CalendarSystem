package com.proj.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXDatePicker;

import com.proj.deletableTests.GUItests;
import com.proj.model.*;

public class NewAppointment extends JFrame {

	private Model thisModel;
	private InternalParticipant appLeaderInternalParticipant;
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Dimensions
																				// of
																				// client
																				// screen
	GridBagConstraints x = new GridBagConstraints();

	
	private JTextField nameInput;
	private JTextField startTimeInput;
	private JTextField endTimeInput;
	private JXDatePicker startDateInput;
	private JXDatePicker endDateInput;
	private Date startTime = new Date();
	private Date endTime = new Date();
	private JRadioButton meetingRoomButton;
	private JRadioButton otherButton;
	private JTextField locationInput;
	private JComboBox<PartAmount> participantNumberInput;
	private ArrayList<MeetingRoom> freeMeetingRooms;
	private JComboBox<MeetingRoom> meetingRoomInput;
	private PartAmount[] participantAmount = { new PartAmount(2, "2 Persons"),
			new PartAmount(5, "5 Persons"),	new PartAmount(10, "10 Persons"), 
			new PartAmount(15, "15 Persons"), new PartAmount(20, "20 Persons"), 
			new PartAmount(25, "25 Persons"), new PartAmount(30, "30 Persons"), 
			new PartAmount(40, "40 Persons"), new PartAmount(50, "50 Persons"), 
			new PartAmount(75, "75 Persons"),	new PartAmount(100, "100 Persons") };

	private FuzzyDropdown<Invitable> fuzzyDropdown;

	private JScrollPane addedParticipantScrollPane;
	private JPanel addedParticipantView;
	private ArrayList<Participant> addedParticipantList;

	private JCheckBox reminderCheckBox;
	private JButton cancelButton;
	private JButton saveButton;
	private JButton deleteButton;

	public NewAppointment(Model model) {

		
		appLeaderInternalParticipant = new InternalParticipant(MainCalendar.getCurrentEmployee());
		//appLeaderInternalParticipant = new InternalParticipant(GUItests.getCurrentEmployee());
		thisModel = model;
		// Setting up the Frame, setting the size, position and making it fixed size
//		JFrame add = new JFrame();
		setTitle("New Appointment");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(750, 300);

		int xLocation = (int) (screenSize.getWidth() - getWidth()) / 2;
		int yLocation = (int) (screenSize.getHeight() - getHeight()) / 2;
		setLocation(xLocation, yLocation);
		// setResizable(false);
		setLayout(null);

		// Adding the different labels that are static and do not do anything
		JLabel nameLabel = new JLabel("Name:", SwingConstants.RIGHT);
		nameLabel.setBounds(10, 20, 70, 25);
		add(nameLabel);
		JLabel startLabel = new JLabel("Start:", SwingConstants.RIGHT);
		startLabel.setBounds(10, 60, 70, 25);
		add(startLabel);
		JLabel endLabel = new JLabel("End:", SwingConstants.RIGHT);
		endLabel.setBounds(10, 100, 70, 25);
		add(endLabel);
		JLabel locationLabel = new JLabel("Location:", SwingConstants.RIGHT);
		locationLabel.setBounds(10, 140, 70, 25);
		add(locationLabel);
		JLabel invitesLabel = new JLabel("Invites:", SwingConstants.LEFT);
		invitesLabel.setBounds(350, 20, 70, 25);
		add(invitesLabel);
		JSeparator vSeparator1 = new JSeparator(SwingConstants.VERTICAL);
		vSeparator1.setBounds(320, 20, 1, 180);
		add(vSeparator1);
		JSeparator vSeparator2 = new JSeparator(SwingConstants.VERTICAL);
		vSeparator2.setBounds(323, 20, 1, 180);
		add(vSeparator2);

		// Description / Name field
		nameInput = new JTextField();
		nameInput.setBounds(100, 20, 200, 25);
		add(nameInput);

		// Start and End time of the appointment
		startTimeInput = new JTextField();
		startTimeInput.setBounds(100, 60, 70, 25);
		startTimeInput.setText("12:00");
		stringToTimeHMS("12:00", startTime);
		startTimeInput.addFocusListener(new SuperEventListener());
		add(startTimeInput);
		endTimeInput = new JTextField();
		endTimeInput.setBounds(100, 100, 70, 25);
		endTimeInput.setText("13:00");
		stringToTimeHMS("13:00", endTime);
		endTimeInput.addFocusListener(new SuperEventListener());
		add(endTimeInput);

		// start and end date, using the JXDatePicker form SwingX
		startDateInput = new JXDatePicker();
		startDateInput.setDate(Calendar.getInstance().getTime());
		startDateInput.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		startDateInput.setBounds(180, 60, 120, 25);
		startDateInput.addActionListener(new SuperEventListener());
		add(startDateInput);
		endDateInput = new JXDatePicker();
		endDateInput.setDate(Calendar.getInstance().getTime());
		endDateInput.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		endDateInput.setBounds(180, 100, 120, 25);
		endDateInput.addActionListener(new SuperEventListener());
		add(endDateInput);

		// Choosing if meetingRoom or other location
		meetingRoomButton = new JRadioButton("Meeting Room");
		meetingRoomButton.setBounds(100, 140, 110, 25);
		meetingRoomButton.addActionListener(new locationButtonAction());
		add(meetingRoomButton);
		otherButton = new JRadioButton("Other");
		otherButton.setBounds(230, 140, 70, 25);
		otherButton.addActionListener(new locationButtonAction());
		otherButton.setSelected(true);
		add(otherButton);
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(meetingRoomButton);
		bGroup.add(otherButton);

		// if meetingroom is selected two JCombBoxes with participant and meeting room
		participantNumberInput = new JComboBox<PartAmount>(participantAmount);
		participantNumberInput.setBounds(100, 170, 100, 25);
		participantNumberInput.addActionListener(new SuperEventListener());
		participantNumberInput.setVisible(false);
		add(participantNumberInput);

		meetingRoomInput = new JComboBox<MeetingRoom>();
		meetingRoomInput.setBounds(200, 170, 100, 25);
		meetingRoomInput.setVisible(false);
        add(meetingRoomInput);
        findFreeMeetingRoomFunction();
		
		// if other is selected a JTextField for writing a location description is selected
		locationInput = new JTextField();
		locationInput.setBounds(100, 170, 200, 25);
		add(locationInput);

		addedParticipantList = new ArrayList<Participant>();

		addedParticipantScrollPane = new JScrollPane();
		addedParticipantScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		addedParticipantScrollPane.setBounds(400, 50, 300, 130);
		addedParticipantView = new JPanel();
		addedParticipantScrollPane.setViewportView(addedParticipantView);
		add(addedParticipantScrollPane);
		addedParticipantView.setLayout(new GridBagLayout());
		createAddedParticipantView(); // Funksjonen som lager addedParticipants, kj�res ved alle endringer.

		fuzzyDropdown = getFuzzyDropdown(model);
		fuzzyDropdown.setBounds(400, 20, 300, 25);
		fuzzyDropdown.addActionListener(new participantNamesInputAction());
		add(fuzzyDropdown);
		
		reminderCheckBox = new JCheckBox("Remind me of this appointment");
		reminderCheckBox.setBounds(95, 210, 300, 25);
		add(reminderCheckBox);
		
		deleteButton = new JButton("Delete");
		deleteButton.setBounds(415, 210, 90, 25);
		deleteButton.setEnabled(false);
		add(deleteButton);

		saveButton = new JButton("Save");
		saveButton.setBounds(510, 210, 90, 25);
		saveButton.addActionListener(new saveButtonAction());
		add(saveButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(605, 210, 90, 25);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		add(cancelButton);
		
		

		setVisible(true); // setter hele tingen visible.
	}

	private FuzzyDropdown<Invitable> getFuzzyDropdown(Model model) {
		ArrayListModel<Invitable> listModel = new ArrayListModel<Invitable>(Arrays.asList(model.getEmployees()));
		listModel.addAll(Arrays.asList(model.getGroups()));


		FuzzyDropdown<Invitable> fuzzyDropdown = new FuzzyDropdown<>(listModel, true);

		return fuzzyDropdown;
	}

	@SuppressWarnings("deprecation")
	public void stringToTimeHMS(String stringTime, Date date){
			
		String[] timeSplit = stringTime.split(":");
		date.setHours(Integer.parseInt(timeSplit[0]));
		date.setMinutes(Integer.parseInt(timeSplit[1]));
		date.setSeconds(Integer.parseInt("00"));
	}
	public void findFreeMeetingRoomFunction(){
		freeMeetingRooms = thisModel.getFreeMeetingRooms(startTime, endTime, ((PartAmount)participantNumberInput.getSelectedItem()).getValue());
		meetingRoomInput.removeAllItems();
		for (int i = 0; i < freeMeetingRooms.size(); i++) {
			meetingRoomInput.addItem(freeMeetingRooms.get(i));
		}	
		meetingRoomInput.setEnabled(true);
	}
	
	public void createAddedParticipantView() {
		addedParticipantView.removeAll();
		addedParticipantView.repaint();
		for (int i = 0; i < addedParticipantList.size(); i++) {
			ButtonPane buttonPane = new ButtonPane(addedParticipantList.get(i));
			x.gridx = 0;
			x.gridy = i;
			x.weighty = 0;
			if(i+1 == addedParticipantList.size()) { 
				x.weighty = 1; // makes participants stick to top left corner.
			}
			x.anchor = GridBagConstraints.FIRST_LINE_START;
			addedParticipantView.add(buttonPane, x);
			setVisible(true);
		}

	}
	class saveButtonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFrame warningWindow = new JFrame();
			Appointment app;
			String appDescription = nameInput.getText();
			Date appStartTime = startTime;
			Date appEndTime = endTime;
			InternalParticipant appLeader = appLeaderInternalParticipant;
			MeetingRoom appMeetingRoom;
			String appLocation;
			
			
			if(nameInput.getText().length() == 0){
				JOptionPane.showMessageDialog(warningWindow, "Please fill in the Name field",
						"Name Error", JOptionPane.WARNING_MESSAGE);
                return;
			}
			else{
				if(startTime.before(endTime)){
					
					if(meetingRoomButton.isSelected()){
						appMeetingRoom = freeMeetingRooms.get(meetingRoomInput.getSelectedIndex());
						app = new Appointment( appLeader, appStartTime, appEndTime, appMeetingRoom );	
					}
					else{
						if(locationInput.getText().length() == 0){
							JOptionPane.showMessageDialog(warningWindow, "Please fill in the location field",
									"Location Error", JOptionPane.WARNING_MESSAGE);
                            return;
						}
						appLocation = locationInput.getText();
						app = new Appointment( appLeader, appStartTime, appEndTime, appLocation);
					}
					
					for (int i = 0; i < addedParticipantList.size(); i++) {
						app.addParticipant(addedParticipantList.get(i));
					}
					app.setDescription(appDescription);
					thisModel.addAppointment(app);
	//				dispose();
	
				}
				else {
					JOptionPane.showMessageDialog(warningWindow, "You cannot end an appointment before it has begun",
							"Date Error", JOptionPane.WARNING_MESSAGE);
                    return;
				}
			}
			
			
		}
		
	}
	

	// Adds participants to the addedParticipantList ArrayList
	class participantNamesInputAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Invitable selectedItem = fuzzyDropdown.getSelectedValue();
			if (selectedItem instanceof Employee) {
				Employee selectedEmp = (Employee) selectedItem;

				ArrayList<Employee> tempAddedEmployees = new ArrayList<Employee>();
				for (int i = 0; i < addedParticipantList.size(); i++) {
					tempAddedEmployees.add(((InternalParticipant) addedParticipantList.get(i)).getEmployee());
				}
				if (!tempAddedEmployees.contains(selectedEmp)) {
					addedParticipantList.add((Participant) new InternalParticipant(selectedEmp, Status.Pending, false, false));
				}

			} else if (selectedItem instanceof Group) {
				Employee[] employeeList = ((Group) selectedItem).getEmployees();
				for (int i = 0; i < employeeList.length; i++) {
					Employee selectedEmp = employeeList[i];
					addedParticipantList
							.add((Participant) new InternalParticipant(
									selectedEmp, Status.Pending, false, false));
				}

			} else if (selectedItem instanceof ExternalParticipant){
                if (!addedParticipantList.contains((ExternalParticipant)selectedItem)) addedParticipantList.add((ExternalParticipant)selectedItem);
            }

			createAddedParticipantView();
		}

	}

	class ButtonPane extends JPanel {
		// Dimensions of ButtonPane are 20 less than JPanel
		private static final long serialVersionUID = 1L;
		JLabel nameLabel;
		JLabel statusLabel;
		JButton removeParticipantButton;

		public ButtonPane(final Participant participant) {

			setLayout(new GridBagLayout());

			removeParticipantButton = new JButton("X");
			removeParticipantButton.setPreferredSize(new Dimension(25, 25));
			removeParticipantButton.setMargin(new Insets(1, 1, 1, 1));
			add(removeParticipantButton);
			removeParticipantButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Participant current = participant;
					for (int i = 0; i < addedParticipantList.size(); i++) {
						addedParticipantList.remove(current);
						createAddedParticipantView();
					}
				}

			});
			nameLabel = new JLabel("  "
					+ ((Participant) participant).getDisplayName(),
					JLabel.LEFT);
			nameLabel.setPreferredSize(new Dimension(185, 25));
			add(nameLabel);
			statusLabel = new JLabel(participant instanceof ExternalParticipant ? "Invited" : ((InternalParticipant) participant)
					.getStatus().toString(), JLabel.LEFT);
			statusLabel.setPreferredSize(new Dimension(70, 25));
			add(statusLabel);
		}
	}
	
	class SuperEventListener implements ActionListener, FocusListener{


		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent event) {
			
			if(event.getSource() == startDateInput){
				startTime.setDate(startDateInput.getDate().getDate());
				startTime.setMonth(startDateInput.getDate().getMonth());
				startTime.setYear(startDateInput.getDate().getYear());
				startDateInput.setDate(startTime);
				findFreeMeetingRoomFunction();
			}
			if(event.getSource() == endDateInput){
				endTime.setDate(endDateInput.getDate().getDate());
				endTime.setMonth(endDateInput.getDate().getMonth());
				endTime.setYear(endDateInput.getDate().getYear());
				endDateInput.setDate(endTime);
				findFreeMeetingRoomFunction();
				
			}
			if(event.getSource() == participantNumberInput){
				findFreeMeetingRoomFunction();
			}
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void focusLost(FocusEvent event) {
			if(event.getComponent() == startTimeInput){
				startTimeInput.setBackground(Color.white);
				String[] startTimeSplit = startTimeInput.getText().split(":");
				try {
					int startH = Integer.parseInt(startTimeSplit[0]);
					int startM = Integer.parseInt(startTimeSplit[1]);
					if(0 > startH || startH > 23 || 0 > startM || startM > 59){
						throw new NumberFormatException();
					}
					startTime.setHours(startH);
					startTime.setMinutes(startM);
					startTimeInput.setText((startH < 10 ? "0" : "") + startH+":"+(startM < 10 ? "0" : "") + startM);
					findFreeMeetingRoomFunction();
					
				}
				catch (NumberFormatException e) {
					startTimeInput.setBackground(Color.pink);
				}
			}
			if(event.getComponent() == endTimeInput){
				endTimeInput.setBackground(Color.white);
				String[] endTimeSplit = endTimeInput.getText().split(":");
				try {
					int endH = Integer.parseInt(endTimeSplit[0]);
					int endM = Integer.parseInt(endTimeSplit[1]);
					if(0 > endH || endH > 23 || 0 > endM || endM > 59){
						throw new NumberFormatException();
					}
					endTime.setHours(endH);
					endTime.setMinutes(endM);
					endTimeInput.setText((endH < 10 ? "0" : "") + endH+":"+(endM < 10 ? "0" : "") + endM);
					findFreeMeetingRoomFunction();
				}
				catch (NumberFormatException wrongNumber) {
					endTimeInput.setBackground(Color.pink);
				}
			}
		}

		@Override
		public void focusGained(FocusEvent event) {
			// TODO Auto-generated method stub
			
		}

	}

	class locationButtonAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (meetingRoomButton.isSelected()) {
				locationInput.setVisible(false);
				participantNumberInput.setVisible(true);
				meetingRoomInput.setVisible(true);
			} else {
				locationInput.setVisible(true);
				participantNumberInput.setVisible(false);
				meetingRoomInput.setVisible(false);

			}
		}
	}

}

class PartAmount {

	private int value;
	private String partValue;

	public PartAmount(int vale, String partValue) {
		this.value = vale;
		this.partValue = partValue;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getPartValue() {
		return partValue;
	}

	public void setPartValue(String partValue) {
		this.partValue = partValue;
	}

	public String toString() {
		return this.partValue;
	}
}

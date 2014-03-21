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

import com.proj.client.Client;
import com.proj.model.*;

public class EditAppointment extends JFrame {

	private Model thisModel;
	private Appointment thisApp;
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
	
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

	@SuppressWarnings("deprecation")
	public EditAppointment(Model model, Appointment appointment) {

		
		new InternalParticipant(Client.getCurrentEmployee());
		thisModel = model;
		thisApp = appointment;
		// Setting up the Frame, setting the size, position and making it fixed size
		setTitle("Edit Appointment");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(750, 300);

		int xLocation = (int) (screenSize.getWidth() - getWidth()) / 2;
		int yLocation = (int) (screenSize.getHeight() - getHeight()) / 2;
		setLocation(xLocation, yLocation);
		setResizable(false);
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
		nameInput.setText(thisApp.getDescription());
		nameInput.setBounds(100, 20, 200, 25);
		add(nameInput);

		// Start and End time of the appointment
		startTimeInput = new JTextField();
		startTimeInput.setBounds(100, 60, 70, 25);
		startTime = thisApp.getStartTime();
		startTimeInput.setText((startTime.getHours() < 10 ? "0" : "") + startTime.getHours()+":"
								+(startTime.getMinutes() < 10 ? "0" : "") + startTime.getMinutes());
		startTimeInput.addFocusListener(new SuperEventListener());
		add(startTimeInput);
		endTimeInput = new JTextField();
		endTimeInput.setBounds(100, 100, 70, 25);
		endTime = thisApp.getEndTime();
		endTimeInput.setText((endTime.getHours() < 10 ? "0" : "") + endTime.getHours()+":"
				+(endTime.getMinutes() < 10 ? "0" : "") + endTime.getMinutes());
		endTimeInput.addFocusListener(new SuperEventListener());
		add(endTimeInput);

		// start and end date, using the JXDatePicker form SwingX
		startDateInput = new JXDatePicker();
		startDateInput.setDate(Calendar.getInstance().getTime());
		startDateInput.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		startDateInput.setDate(startTime);
		startDateInput.setBounds(180, 60, 120, 25);
		startDateInput.addActionListener(new SuperEventListener());
		add(startDateInput);
		endDateInput = new JXDatePicker();
		endDateInput.setDate(Calendar.getInstance().getTime());
		endDateInput.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		endDateInput.setDate(endTime);
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

		if (thisApp.getLocation() != "") {
			locationInput.setText(thisApp.getLocation());
			locationInput.setVisible(true);
			meetingRoomInput.setVisible(false);
			participantNumberInput.setVisible(false);
			otherButton.setSelected(true);
		}
		if (thisApp.getMeetingRoom() != null) {
			meetingRoomInput.addItem(thisApp.getMeetingRoom());
			meetingRoomInput.setSelectedItem(thisApp.getMeetingRoom());
			locationInput.setVisible(false);
			meetingRoomInput.setVisible(true);
			participantNumberInput.setVisible(true);
			meetingRoomButton.setSelected(true);
		}
				
		addedParticipantList = new ArrayList<Participant>();
		for (int i = 0; i < thisApp.getParticipants().length; i++) {
			addedParticipantList.add(thisApp.getParticipants()[i]);
		}
		
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
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				thisModel.deleteAppointment(thisApp.getId());
				dispose();
			}
		});
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
			
		repaint();
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
			String appDescription = nameInput.getText();
			Date appStartTime = startTime;
			Date appEndTime = endTime;
			MeetingRoom appMeetingRoom;
			String appLocation;
			
			
			if(nameInput.getText().length() == 0){
				JOptionPane.showMessageDialog(warningWindow, "Please fill in the Name field",
						"Name Error", JOptionPane.WARNING_MESSAGE);
                return;
			}
			else{
				if(startTime.before(endTime)){
					
					thisApp.setDescription(appDescription);
					thisApp.setStartEndTime(appStartTime, appEndTime);
					
					if(meetingRoomButton.isSelected()){
						appMeetingRoom = (MeetingRoom) meetingRoomInput.getSelectedItem();
						thisApp.setLocation(null);
						thisApp.setMeetingRoom(appMeetingRoom);
					}
					else{
						if(locationInput.getText().length() == 0){
							JOptionPane.showMessageDialog(warningWindow, "Please fill in the location field",
									"Location Error", JOptionPane.WARNING_MESSAGE);
                            return;
						}
						appLocation = locationInput.getText();
						thisApp.setMeetingRoom(null);
						thisApp.setLocation(appLocation);
					}

					for (int i = 0; i < addedParticipantList.size(); i++) {
						thisApp.addParticipant(addedParticipantList.get(i));
					}
					
					
					for (int i = 0; i < thisApp.getParticipants().length; i++) {
						System.out.println("Part: " + thisApp.getParticipants()[i]);
					}

					
					dispose();
	
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
					addedParticipantList.add((Participant) new InternalParticipant(selectedEmp, Status.Pending, false, false));
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
						thisApp.removeParticipant(current);
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
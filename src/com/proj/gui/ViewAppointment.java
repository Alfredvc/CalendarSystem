package com.proj.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXDatePicker;

import com.proj.client.Client;
import com.proj.model.*;

public class ViewAppointment extends JFrame {

	private Appointment thisAppointment;
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
	private JRadioButton attendingButton;
	private JRadioButton notAttendingButton;
	private JTextField locationInput;
	private JTextField meetingRoomInput;
	private JScrollPane addedParticipantScrollPane;
	private JPanel addedParticipantView;
	private ArrayList<Participant> addedParticipantList;

	private JCheckBox reminderCheckBox;
	private JButton deleteButton;
	private JButton saveButton;
	private JButton cancelButton;

	@SuppressWarnings("deprecation")
	public ViewAppointment(Appointment currentApp) {

		thisAppointment = currentApp;
		// Setting up the Frame, setting the size, position and making it fixed
		// size
		setTitle("View Appointment");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(670, 290);
		setResizable(false);

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
		JLabel invitesLabel = new JLabel("Invited Participants:",
				SwingConstants.LEFT);
		invitesLabel.setBounds(350, 20, 200, 25);
		add(invitesLabel);
		JSeparator vSeparator1 = new JSeparator(SwingConstants.VERTICAL);
		vSeparator1.setBounds(320, 20, 1, 180);
		add(vSeparator1);
		JSeparator vSeparator2 = new JSeparator(SwingConstants.VERTICAL);
		vSeparator2.setBounds(323, 20, 1, 180);
		add(vSeparator2);

		// Description / Name field
		nameInput = new JTextField();
		nameInput.setText(currentApp.getDescription());
		nameInput.setEditable(false);
		nameInput.setBounds(100, 20, 200, 25);
		add(nameInput);

		// Start and End time of the appointment
		startTimeInput = new JTextField();
		startTimeInput.setBounds(100, 60, 40, 25);
		startTime = thisAppointment.getStartTime();
		startTimeInput.setText(startTime.getHours() + ":"
				+ startTime.getMinutes());
		startTimeInput.setEditable(false);
		add(startTimeInput);
		endTimeInput = new JTextField();
		endTimeInput.setBounds(100, 100, 40, 25);
		endTime = thisAppointment.getEndTime();
		endTimeInput.setText(endTime.getHours() + ":" + endTime.getMinutes());
		endTimeInput.setEditable(false);
		add(endTimeInput);

		// start and end date, using the JXDatePicker form SwingX
		startDateInput = new JXDatePicker();
		startDateInput.setDate(Calendar.getInstance().getTime());
		startDateInput.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		startDateInput.setDate(startTime);
		startDateInput.setBounds(180, 60, 120, 25);
		startDateInput.setEditable(false);
		add(startDateInput);
		endDateInput = new JXDatePicker();
		endDateInput.setDate(Calendar.getInstance().getTime());
		endDateInput.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		endDateInput.setDate(endTime);
		endDateInput.setBounds(180, 100, 120, 25);
		endDateInput.setEditable(false);
		add(endDateInput);

		// Choosing if meetingRoom or other location
		meetingRoomButton = new JRadioButton("Meeting Room");
		meetingRoomButton.setBounds(100, 140, 110, 25);
		meetingRoomButton.setEnabled(false);
		add(meetingRoomButton);
		otherButton = new JRadioButton("Other");
		otherButton.setBounds(230, 140, 70, 25);
		otherButton.setEnabled(false);
		add(otherButton);
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(meetingRoomButton);
		bGroup.add(otherButton);

		meetingRoomInput = new JTextField();
		meetingRoomInput.setEditable(false);
		meetingRoomInput.setBounds(100, 170, 200, 25);
		add(meetingRoomInput);

		// if other is selected a JTextField for writing a location description
		// is selected
		locationInput = new JTextField();
		locationInput.setEditable(false);
		locationInput.setBounds(100, 170, 200, 25);
		add(locationInput);

		if (thisAppointment.getLocation() != "") {
			locationInput.setText(thisAppointment.getLocation());
			locationInput.setVisible(true);
			meetingRoomInput.setVisible(false);
			otherButton.setSelected(true);
		}
		if (thisAppointment.getMeetingRoom() != null) {
			MeetingRoom cRoom = thisAppointment.getMeetingRoom();
			String cRoomText = cRoom.getRoomNr();
			if (cRoom.getName() != null) {
				cRoomText = cRoomText + " (" + cRoom.getName() + ")";
			}
			meetingRoomInput.setText(cRoomText);
			locationInput.setVisible(false);
			meetingRoomInput.setVisible(true);
			meetingRoomButton.setSelected(true);
		}
		addedParticipantList = new ArrayList<Participant>();
		for (int i = 0; i < thisAppointment.getParticipants().length; i++) {
			addedParticipantList.add(thisAppointment.getParticipants()[i]);
		}

		addedParticipantScrollPane = new JScrollPane();
		addedParticipantScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		addedParticipantScrollPane.setBounds(350, 50, 300, 120);
		addedParticipantView = new JPanel();
		addedParticipantScrollPane.setViewportView(addedParticipantView);
		add(addedParticipantScrollPane);
		addedParticipantView.setLayout(new GridBagLayout());
		createAddedParticipantView(); // Funksjonen som lager addedParticipants,
										// kj�res ved alle endringer.

		reminderCheckBox = new JCheckBox("Remind me of this appointment");
		reminderCheckBox.setBounds(95, 210, 250, 25);
		add(reminderCheckBox);
		
		attendingButton = new JRadioButton("Attending");
		attendingButton.setBounds(400, 180, 100, 25);
		add(attendingButton);
		notAttendingButton = new JRadioButton("Not Attending");
		notAttendingButton.setBounds(500, 180, 120, 25);
		notAttendingButton.setSelected(true);
		add(notAttendingButton);
		ButtonGroup bGroupAtt = new ButtonGroup();
		bGroupAtt.add(attendingButton);
		bGroupAtt.add(notAttendingButton);
		

		deleteButton = new JButton("Delete");
		deleteButton.setBounds(365, 210, 90, 25);
		deleteButton.setEnabled(false);
		add(deleteButton);

		saveButton = new JButton("Save");
		saveButton.setBounds(460, 210, 90, 25);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(attendingButton.isSelected()){
					// attending action
					System.out.println("\t\t\tAttending : ");
					for (int i = 0; i < thisAppointment.getParticipants().length; i++) {
						Participant currentPart = thisAppointment.getParticipants()[i];
						if(currentPart instanceof InternalParticipant){
							if(Client.getCurrentEmployee().equals(currentPart)){
								((InternalParticipant) currentPart).setStatus(Status.Attending);
							}
							
						}
					}
				}
				else {
					// not attending action
					System.out.println("\t\t\tNot Attending : ");
					for (int i = 0; i < thisAppointment.getParticipants().length; i++) {
						Participant currentPart = thisAppointment.getParticipants()[i];
						if(currentPart instanceof InternalParticipant){
							if(Client.getCurrentEmployee().equals(currentPart)){
								((InternalParticipant) currentPart).setStatus(Status.Declined);
							}
							
						}
					}
				}
				dispose();
			}
		});
		add(saveButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(555, 210, 90, 25);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		add(cancelButton);

		setVisible(true); // setter hele tingen visible.
	}

	public void createAddedParticipantView() {
		for (int i = 0; i < addedParticipantList.size(); i++) {
			ButtonPane buttonPane = new ButtonPane(addedParticipantList.get(i));
			x.gridx = 0;
			x.gridy = i;
			x.weighty = 0;
			if (i + 1 == addedParticipantList.size()) {
				x.weighty = 1; // makes participants stick to top left corner.
			}
			x.anchor = GridBagConstraints.FIRST_LINE_START;
			addedParticipantView.add(buttonPane, x);
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

			nameLabel = new JLabel("  "
					+ ((Participant) participant).getDisplayName(), JLabel.LEFT);
			nameLabel.setPreferredSize(new Dimension(185, 25));
			add(nameLabel);
			statusLabel = new JLabel(
					participant instanceof ExternalParticipant ? "Invited"
							: ((InternalParticipant) participant).getStatus()
									.toString(), JLabel.LEFT);
			statusLabel.setPreferredSize(new Dimension(70, 25));
			add(statusLabel);
		}
	}

}
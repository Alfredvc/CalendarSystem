package com.proj.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXDatePicker;

import com.proj.model.*;

public class NewAppointment extends JFrame {

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
	private Date startTime;
	private Date endTime;
	private JRadioButton meetingRoomButton;
	private JRadioButton otherButton;
	private JTextField locationInput;
	private JComboBox<PartAmount> participantNumberInput;
	private JComboBox meetingRoomInput;
	private PartAmount[] participantAmount = { new PartAmount(5, "5 Persons"),
			
			new PartAmount(10, "10 Persons"), new PartAmount(15, "15 Persons"),
			new PartAmount(20, "20 Persons"), new PartAmount(25, "25 Persons"),
			new PartAmount(30, "30 Persons"), new PartAmount(40, "40 Persons"),
			new PartAmount(50, "50 Persons"), new PartAmount(75, "75 Persons"),
			new PartAmount(100, "100 Persons") };

	private JComboBox<Employee> participantNamesInput;
	private DefaultComboBoxModel<Employee> participantNamesModel;
	private FuzzyDropdown<Invitable> fuzzyDropdown;

	private JScrollPane addedParticipantScrollPane;
	private JPanel addedParticipantView;
	private ArrayList<Participant> addedParticipantList;

	private ArrayList<Employee> employeeList = new ArrayList<Employee>(); // list
																			// of
																			// Employee/Groups
																			// to
																			// add

	public NewAppointment(Model model) {

		// Setting up the Frame, setting the size, position and making it fixed
		// size
		setSize(800, 400);
		int xLocation = (int) (screenSize.getWidth() - getWidth()) / 2;
		int yLocation = (int) (screenSize.getHeight() - getHeight()) / 2;
		setLocation(xLocation, yLocation);
		// setResizable(false);
		setLayout(null);

		// Adding the different labels that are static and do not do anything
		JLabel nameLabel = new JLabel("Name:", SwingConstants.RIGHT);
		nameLabel.setBounds(10, 20, 80, 25);
		add(nameLabel);
		JLabel startLabel = new JLabel("Start:", SwingConstants.RIGHT);
		startLabel.setBounds(10, 50, 80, 25);
		add(startLabel);
		JLabel endLabel = new JLabel("End:", SwingConstants.RIGHT);
		endLabel.setBounds(10, 80, 80, 25);
		add(endLabel);
		JLabel locationLabel = new JLabel("Location:", SwingConstants.RIGHT);
		locationLabel.setBounds(10, 110, 80, 25);
		add(locationLabel);
		JLabel invitesLabel = new JLabel("Invites:", SwingConstants.LEFT);
		invitesLabel.setBounds(350, 20, 80, 25);
		add(invitesLabel);
		JSeparator vSeparator1 = new JSeparator(SwingConstants.VERTICAL);
		vSeparator1.setBounds(320, 20, 1, 200);
		add(vSeparator1);
		JSeparator vSeparator2 = new JSeparator(SwingConstants.VERTICAL);
		vSeparator2.setBounds(323, 20, 1, 200);
		add(vSeparator2);

		// Description / Name field
		nameInput = new JTextField();
		nameInput.setBounds(100, 20, 200, 25);
		nameInput.setName("nameInput");
		add(nameInput);

		// Start and End time of the appointment
		startTimeInput = new JTextField();
		startTimeInput.setBounds(100, 50, 60, 25);
		startTimeInput.setName("startTimeInput");
		startTimeInput.setText("HH:MM");
		add(startTimeInput);
		endTimeInput = new JTextField();
		endTimeInput.setBounds(100, 80, 60, 25);
		endTimeInput.setName("endTimeInput");
		endTimeInput.setText("HH:MM");
		add(endTimeInput);

		// start and end date, using the JXDatePicker form SwingX
		startDateInput = new JXDatePicker();
		startDateInput.setDate(Calendar.getInstance().getTime());
		startDateInput.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		startDateInput.setBounds(180, 50, 120, 25);
		add(startDateInput);
		endDateInput = new JXDatePicker();
		endDateInput.setDate(Calendar.getInstance().getTime());
		endDateInput.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		endDateInput.setBounds(180, 80, 120, 25);
		add(endDateInput);

		// Choosing if meetingRoom or other location
		meetingRoomButton = new JRadioButton("Meeting Room");
		meetingRoomButton.setName("meetingRoomButton");
		meetingRoomButton.setBounds(100, 110, 110, 25);
		meetingRoomButton.setSelected(true);
		meetingRoomButton.addActionListener(new locationButtonAction());
		add(meetingRoomButton);
		otherButton = new JRadioButton("Other");
		otherButton.setName("otherButton");
		otherButton.setBounds(230, 110, 70, 25);
		otherButton.addActionListener(new locationButtonAction());
		add(otherButton);
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(meetingRoomButton);
		bGroup.add(otherButton);

		// if meetingroom is selected two JCombBoxes with participant and
		// meeting room
		participantNumberInput = new JComboBox<PartAmount>(participantAmount);
		participantNumberInput.setName("participantInput");
		participantNumberInput.setBounds(100, 140, 100, 25);
		add(participantNumberInput);
		meetingRoomInput = new JComboBox();
		meetingRoomInput.setName("meetingroomInput");
		meetingRoomInput.setBounds(200, 140, 100, 25);
		add(meetingRoomInput);
		// if other is selected a JTextField for writing a location description
		// is selected
		locationInput = new JTextField();
		locationInput.setName("locationInput");
		locationInput.setBounds(100, 140, 200, 25);
		locationInput.setVisible(false);
		add(locationInput);

		addedParticipantList = new ArrayList<Participant>();

		addedParticipantScrollPane = new JScrollPane();
		addedParticipantScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		addedParticipantScrollPane.setBounds(400, 50, 300, 100);
		addedParticipantView = new JPanel();
		addedParticipantScrollPane.setViewportView(addedParticipantView);
		add(addedParticipantScrollPane);
		addedParticipantView.setLayout(new GridBagLayout());

		createAddedParticipantView();

		fuzzyDropdown = getFuzzyDropdown(model);
		fuzzyDropdown.setBounds(400, 20, 300, 25);
		fuzzyDropdown.addActionListener(new participantNamesInputAction());
		add(fuzzyDropdown);

		setVisible(true); // setter hele tingen visible.

	}

	private FuzzyDropdown<Invitable> getFuzzyDropdown(Model model) {
		ArrayListModel<Invitable> listModel = new ArrayListModel<Invitable>(
				Arrays.asList(model.getEmployees()));
		listModel.addAll(Arrays.asList(model.getGroups()));

		FuzzyDropdown<Invitable> fuzzyDropdown = new FuzzyDropdown<>(listModel, true);
		System.out.println(listModel.getSize());
		return fuzzyDropdown;
	}

	public void createAddedParticipantView() {
		addedParticipantView.removeAll();
		addedParticipantView.repaint();
		for (int i = 0; i < addedParticipantList.size(); i++) {
			ButtonPane buttonPane = new ButtonPane(addedParticipantList.get(i));
			x.gridx = 0;
			x.gridy = i;
			x.anchor = GridBagConstraints.FIRST_LINE_START;
			addedParticipantView.add(buttonPane, x);
			setVisible(true);
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
				for (int i = 0; i <	addedParticipantList.size(); i++) {
					tempAddedEmployees.add(((InternalParticipant) addedParticipantList.get(i)).getEmployee());
				}
				if(!tempAddedEmployees.contains(selectedEmp)) {
					addedParticipantList.add((Participant) new InternalParticipant(selectedEmp, Status.Pending, false, false));
				}
				
			} else if (selectedItem instanceof Group) {
				Employee[] employeeList = ((Group) selectedItem).getEmployees();
				System.out.println(employeeList.length);
				for (int i = 0; i < employeeList.length; i++) {
					Employee selectedEmp = employeeList[i];
					addedParticipantList
							.add((Participant) new InternalParticipant(
									selectedEmp, Status.Pending, false, false));
				}
			}
			System.out.println(addedParticipantList);
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
						System.out.println(addedParticipantList);
						createAddedParticipantView();
					}
				}

			});
			nameLabel = new JLabel("  "
					+ ((InternalParticipant) participant).getDisplayName(),
					JLabel.LEFT);
			nameLabel.setPreferredSize(new Dimension(185, 25));
			add(nameLabel);
			statusLabel = new JLabel(((InternalParticipant) participant)
					.getStatus().toString(), JLabel.LEFT);
			statusLabel.setPreferredSize(new Dimension(70, 25));
			add(statusLabel);
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

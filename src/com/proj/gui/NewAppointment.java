package com.proj.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.proj.deletableTests.GUItests;
import com.proj.model.Employee;
import com.proj.model.InternalParticipant;
import com.proj.model.Participant;
import com.proj.model.Status;

public class NewAppointment extends JFrame{
	
	private JPanel panel= new JPanel();
	
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Dimensions of client screen
	
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
	private PartAmount[] participantAmount = {new PartAmount(5,"5 Persons"), new PartAmount(10,"10 Persons"), 
			new PartAmount(15,"15 Persons"), new PartAmount(20,"20 Persons"), new PartAmount(25,"25 Persons"), 
			new PartAmount(30,"30 Persons"), new PartAmount(40,"40 Persons"), new PartAmount(50,"50 Persons"), 
			new PartAmount(75,"75 Persons"), new PartAmount(100,"100 Persons")};
	
	private JComboBox<Employee> participantNamesInput;
	private DefaultComboBoxModel<Employee> participantNamesModel;
	private JScrollPane addedParticipantScrollPane;
	private JList<Participant> addedParticipantView;
	private DefaultListModel<Participant> addedParticipantModel;
	
	private ArrayList<Employee> employeeList = new ArrayList<Employee>(); // list of Employee/Groups to add
	private ArrayList<Participant> addedParticipantList = new ArrayList<Participant>(); // list of Employee/Externals added	
	
	
	


	public NewAppointment() {
		
		
		// Setting up the Frame, setting the size, position and making it fixed size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 400);
		int xLocation = (int) (screenSize.getWidth() - getWidth()) / 2;
		int yLocation = (int) (screenSize.getHeight() - getHeight()) / 2;		
		setLocation(xLocation, yLocation);
		//setResizable(false);
		setLayout(null);
		
		// Adding the different labels that are static and do not do anything 	
		JLabel nameLabel = new JLabel("Name:", SwingConstants.RIGHT);
		nameLabel.setBounds(10, 20, 80, 25);
		panel.add(nameLabel);
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
		
		// if meetingroom is selected two JCombBoxes with participant and meeting room
		participantNumberInput = new JComboBox<PartAmount>(participantAmount);
		participantNumberInput.setName("participantInput");
		participantNumberInput.setBounds(100, 140, 100, 25);
		add(participantNumberInput);
		meetingRoomInput = new JComboBox();
		meetingRoomInput.setName("meetingroomInput");
		meetingRoomInput.setBounds(200, 140, 100, 25);
		add(meetingRoomInput);
		// if other is selected a JTextField for writing a location description is selected
		locationInput = new JTextField();
		locationInput.setName("locationInput");
		locationInput.setBounds(100, 140, 200, 25);
		locationInput.setVisible(false);
		add(locationInput);		
		
		
		
		
		participantNamesModel = new DefaultComboBoxModel<Employee>();
		employeeList = GUItests.testEmployeeList; // henter listen over hvilke employees som kan legges til
		
		// Legger employees over fra ArrayListen til DefaultComboBoxModel
		for (int i = 0; i < employeeList.size(); i++) {
			participantNamesModel.addElement(employeeList.get(i));
		}
		
			
		participantNamesInput = new JComboBox<Employee>();
		participantNamesInput.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
		participantNamesInput.setName("participantNamesInput");
		participantNamesInput.setModel(participantNamesModel);
		participantNamesInput.setBounds(400, 20, 300, 25);
		participantNamesInput.addItemListener(new participantNamesInputAction());
		AutoCompleteDecorator.decorate(participantNamesInput);
		add(participantNamesInput);
		
		addedParticipantScrollPane = new JScrollPane();
		addedParticipantModel = new DefaultListModel<Participant>();
		addedParticipantView = new JList<Participant>();
		addedParticipantView.setName("participantView");
		addedParticipantScrollPane.setViewportView(addedParticipantView);
		addedParticipantScrollPane.setBounds(400, 50, 300, 100);
		addedParticipantView.setModel(addedParticipantModel);
		addedParticipantView.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		addedParticipantView.setLayoutOrientation(JList.VERTICAL);
		add(addedParticipantScrollPane);
		
		
//		instance of internalparticipant get status
//		System.out.println("asdasd" + ((InternalParticipant) addedParticipantList.get(i)).isAlarm());
		
		setVisible(true);

	}
	class participantNamesInputAction implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent event) {
			if(event.getStateChange() == ItemEvent.SELECTED){
				Employee selectedEmp = (Employee) participantNamesInput.getSelectedItem();
				addedParticipantModel.addElement((Participant) new InternalParticipant(selectedEmp, Status.Pending, false, false));					
			}
		}
		
	}
	
	class locationButtonAction implements ActionListener {
			
		public void actionPerformed(ActionEvent e) {
			if(meetingRoomButton.isSelected()) {
				locationInput.setVisible(false);
				participantNumberInput.setVisible(true);
				meetingRoomInput.setVisible(true);
			} 
			else {
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
	public String toString(){
		return this.partValue;
	}
}

package com.proj.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Boilerplate functionality for any class implementing
 * some kind of action.
 * @author Truls Rustad Fossum
 */
public class ActionSupport {
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	private String actionCommand;
	private Object delegator;
	
	public ActionSupport(Object delegator, String actionCommand) {
		this.actionCommand = actionCommand;
		this.delegator = delegator;
	}
	
	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}
	
	public String getActionCommand() {
		return actionCommand;
	}
	
	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}
	
	public void fireActionPerformed() {
		fireActionPerformed(actionCommand);
	}
	
	public void fireActionPerformed(String actionCommand) {
		ActionEvent evt = new ActionEvent(
				delegator,
				ActionEvent.ACTION_PERFORMED,
				actionCommand
			);
		fireActionPerformed(evt);
	}
	
	public void fireActionPerformed(ActionEvent evt) {		
		for (ActionListener l : listeners) {
			l.actionPerformed(evt);
		}
	}

}

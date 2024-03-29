package com.proj.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class FuzzyDropdown<E> extends JTextField {
	private FuzzyPopup<E> popup;
	private FuzzyListModel<E> fuzzyListModel;
	private ArrayList<ActionListener> actionListeners = new ArrayList<>();
	private E selected;
	
	public FuzzyDropdown(ListModel<E> model, boolean withExternalParticipants) {
		super();
		fuzzyListModel = new FuzzyListModel<>(model, withExternalParticipants);
		setColumns(10);
		popup = new FuzzyPopup<E>(this, fuzzyListModel);
		getDocument().addDocumentListener(new DocumentChangeListener());
		addKeyListener(new NavigationKeyListener());
	}
	
	private void updateFuzzyListModel(String searchString) {
		fuzzyListModel.update(searchString);
	}

	private void showPopup() {
		popup.show(this, 0, getHeight());
	}
	
	public void selectValue(E value) {
		selected = value;
		
		// Close popup
		popup.setVisible(false);
		
		// Notify listeners
		for (ActionListener listener : actionListeners) {
			ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "selected");
			listener.actionPerformed(event);
		}
	}
	
	public E getSelectedValue() {
		return selected;
	}
	
	private void closePopup() {
		popup.setVisible(false);
	}
	
	public ListModel<E> getModel() {
		return fuzzyListModel.getBackingModel();
	}
	
	public void addActionListener(ActionListener listener) {
			actionListeners.add(listener);
	}


	/**
	 * Document listener to detect changes to the text.
	 */
	private class DocumentChangeListener implements DocumentListener {
		private void onDocumentChange(DocumentEvent e) {
			Document document = e.getDocument();
			try {
				
				// Get text in field
				String searchString = document.getText(0, document.getLength());
				
				if (searchString.length() == 0) {
					popup.setVisible(false);
				} else {
					updateFuzzyListModel(searchString);
					if (!popup.isVisible()) {
						showPopup();
					}
				}
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			onDocumentChange(e);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			onDocumentChange(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			onDocumentChange(e);
		}
	}

	
	/**
	 * Listens for key events to control the popup
	 */
	class NavigationKeyListener extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				popup.selectPrevious();
				break;
				
			case KeyEvent.VK_DOWN:
				popup.selectNext();
				break;
				
			case KeyEvent.VK_ENTER:
				E value = popup.getSelectedValue();
				if (value != null) {
					closePopup();
					selectValue(value);
				}
				break;
				
			case KeyEvent.VK_ESCAPE:
				closePopup();
			}
		}
	}


	public void reset() {
		setText("");
		selected = null;
	}
	
}

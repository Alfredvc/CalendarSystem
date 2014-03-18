package com.proj.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Dropdown popup for fuzzy search list
 */
public class FuzzyPopup<E> extends JPopupMenu {
	
	private JScrollPane scrollPane;
	private JList<E> list;
	private FuzzyDropdown<E> fuzzyDropdown;
	
	public FuzzyPopup(FuzzyDropdown<E> fuzzySearchList, ListModel<E> model) {
		this.fuzzyDropdown = fuzzySearchList;

		model.addListDataListener(new ModelListener());

		list = new JList<E>(model);
		list.setBorder(null);
		list.setFocusable(false);
		list.addMouseListener(new ListMouseListener());

		scrollPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);
		scrollPane.setFocusable(false);
		add(scrollPane);		

		setBorder(null);
		setFocusable(false);
	}
	
	@Override
	public void show(Component invoker, int x, int y) {
		Dimension size = new Dimension(invoker.getWidth(), 200);

		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		super.show(invoker, x, y);
	}
	
	public E getSelectedValue() {
		return list.getSelectedValue();
	}
	
	public ListModel<E> getModel() {
		return list.getModel();
	}
	
	public boolean selectNext() {
		int selectedIndex = list.getSelectedIndex();
		if (selectedIndex < 0) {
			list.setSelectedIndex(0);
			return true;
		}
		if (selectedIndex >= list.getModel().getSize()) {
			return false;
		}
		list.setSelectedIndex(selectedIndex + 1);
		return true;
	}
	
	public boolean selectPrevious() {
		int selectedIndex = list.getSelectedIndex();
		if (selectedIndex < 0) {
			list.setSelectedIndex(list.getModel().getSize() - 1);
			return true;
		}
		if (selectedIndex <= 0) {
			return false;
		}
		list.setSelectedIndex(selectedIndex - 1);
		return true;
	}
	
	protected class ListMouseListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			fuzzyDropdown.selectValue(list.getSelectedValue());
		}
	}

	
	private class ModelListener implements ListDataListener {

		@Override
		public void contentsChanged(ListDataEvent e) {
			list.clearSelection();
		}

		@Override
		public void intervalAdded(ListDataEvent e) {
			list.clearSelection();
		}

		@Override
		public void intervalRemoved(ListDataEvent e) {
			list.clearSelection();
		}
		
	}
}


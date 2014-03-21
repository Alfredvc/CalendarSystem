package com.proj.gui;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * This list presents a list based on custom components for each entry.
 * The component are active/heavy.
 * @author Truls Rustad Fossum
 */
public class ActiveList<C extends Component, E> extends JComponent {
	private ActiveComponentConstructor<C, E> constructor;
	private ListModel<E> listModel;
	
	public ActiveList(ListModel<E> listModel, ActiveComponentConstructor<C, E> constructor) {
		this.constructor = constructor;
		this.listModel = listModel;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		listModel.addListDataListener(new ListDataHandler());

		
		// Add all existing list elements
		int size = listModel.getSize();
		for (int i = 0; i < size; i++) {
			addListElement(i);
		}
	}
	
	private void addListElement(int index) {
		C component = constructor.constructActiveListComponent(
				listModel.getElementAt(index)
			);
		add(component, index);
	}
	
	private void removeListElement(int index) {
		remove(index);
	}
	
	private void updateListElement(int index) {
		Component oldComponent = getComponent(index);

		C newComponent = constructor.updateActiveListComponent(
				oldComponent,
				listModel.getElementAt(index)
			);
		
		if (newComponent != oldComponent) {
			remove(index);
			add(newComponent, index);
		}
	}
	
	
	private class ListDataHandler implements ListDataListener {

		@Override
		public void contentsChanged(ListDataEvent e) {
			int lastIndex = e.getIndex1();
			for (int i = e.getIndex0(); i <= lastIndex; i++) {
				updateListElement(i);
			}
		}

		@Override
		public void intervalAdded(ListDataEvent e) {
			int lastIndex = e.getIndex1();
			for (int i = e.getIndex0(); i <= lastIndex; i++) {
				addListElement(i);
			}			
		}

		@Override
		public void intervalRemoved(ListDataEvent e) {
			int firstIndex = e.getIndex0();
			for (int i = e.getIndex1(); i >= firstIndex; i--) {
				removeListElement(i);
			}		
		}
	}
}

package com.proj.gui;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * List model selecting serving the best matches from an underlying listmodel to
 * the list!
 */
class FuzzyListModel<E> extends AbstractListModel<E> {
	
	private ArrayList<E> currentChoices = new ArrayList<>();
	private ListModel<E> backingListModel;
	private String searchString;
	
	public FuzzyListModel(ListModel<E> backingListModel) {
		backingListModel.addListDataListener(new BackingListDataListener());
		this.backingListModel = backingListModel;
	}

	private void calculateAlternatives() {
		//TODO Not a good algorithm?
		int oldSize = currentChoices.size();
		currentChoices.clear();
		for (int i = 0; i < backingListModel.getSize(); i++) {
			E element = backingListModel.getElementAt(i);
			if (element.toString().toLowerCase().contains(searchString.toLowerCase())) {
				currentChoices.add(element);
			}
		}
		int newSize = currentChoices.size();
		fireContentsChanged(this, 0, oldSize > newSize ? oldSize - 1 : newSize - 1);
	}
	
	public void update(String searchString) {
		this.searchString = searchString;
		calculateAlternatives();
	}

	@Override
	public E getElementAt(int index) {
		return currentChoices.get(index);
	}

	@Override
	public int getSize() {
		return currentChoices.size();
	}
	
	public ListModel<E> getBackingModel() {
		return backingListModel;
	}
	
	/**
	 * Listens for changes in the underlying data to update the choices in the list on changes		 *
	 */
	private class BackingListDataListener implements ListDataListener {

		@Override
		public void contentsChanged(ListDataEvent arg0) {
			calculateAlternatives();
		}

		@Override
		public void intervalAdded(ListDataEvent arg0) {
			calculateAlternatives();
		}

		@Override
		public void intervalRemoved(ListDataEvent arg0) {
			calculateAlternatives();
		}
		
	}
	
}

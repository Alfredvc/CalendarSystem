package com.proj.gui;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * This array list is dressed as a ListModel as well! No
 * adaption needed, just spit it into swing :)
 * @author Truls Rustad Fossum
 */

public class ArrayListModel<E> extends ArrayList<E> implements ListModel<E> {
	private ArrayList<ListDataListener> listeners = new ArrayList<>();
	
	public ArrayListModel() {
		super();
	}

	public ArrayListModel(Collection<? extends E> c) {
		super(c);
	}

	public ArrayListModel(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public boolean add(E e) {
		boolean success = super.add(e);
		if (success) {
			int index = size() - 1;
			fireIntervalAdded(index, index);
			return true;
		}
		return success;
	}

	@Override
	public void add(int index, E element) {
		super.add(index, element);
		fireIntervalAdded(index, index);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		int index0 = size();
		boolean success = super.addAll(c);
		if (success) {
			fireIntervalAdded(index0, size() - 1);
		}
		return success;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		boolean success = super.addAll(index, c);
		if (success) {
			fireIntervalAdded(index, index + c.size() - 1);
		}
		return success;
	}

	@Override
	public void clear() {
		int lastIndex = size() - 1;
		super.clear();
		fireIntervalRemoved(0, lastIndex > 0 ? lastIndex : 0);
	}

	@Override
	public E remove(int index) {
		E removedElement = super.remove(index);
		fireIntervalRemoved(index, index);
		return removedElement;
	}

	@Override
	public boolean remove(Object arg0) {
		int index = indexOf(arg0);
		boolean success = super.remove(arg0);
		if (success) {
			fireIntervalRemoved(index, index);
		}
		return success;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		int lastIndex = size() - 1;
		boolean success = super.removeAll(c);
		if (success) {
			fireContentsChanged(0, lastIndex > 0 ? lastIndex : 0);
		}
		return success;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		int lastIndex = size() - 1;
		boolean success = super.retainAll(c);
		if (success) {
			fireContentsChanged(0, lastIndex > 0 ? lastIndex : 0);
		}
		return success;
	}

	@Override
	public E set(int index, E element) {
		E previousElement = super.set(index, element);
		fireContentsChanged(index, index);
		return previousElement;
	}
	
	private void fireContentsChanged(int index0, int index1) {
		ListDataEvent evt = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index0, index1);
		for (ListDataListener l : listeners) {
			l.contentsChanged(evt);
		}
	}
	
	private void fireIntervalRemoved(int index0, int index1) {
		ListDataEvent evt = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index0, index1);
		for (ListDataListener l : listeners) {
			l.intervalRemoved(evt);
		}
	}
	
	private void fireIntervalAdded(int index0, int index1) {
		ListDataEvent evt = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index0, index1);
		for (ListDataListener l : listeners) {
			l.intervalAdded(evt);
		}
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);		
	}
	
	@Override
	public E getElementAt(int index) {
		return get(index);
	}

	@Override
	public int getSize() {
		return size();
	}


	
}

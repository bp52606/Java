package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimListModel implements ListModel<Integer> {

	private int primNumber = 1;
	private List<Integer> elements = new ArrayList<>();
	private List<ListDataListener> listeners = new ArrayList<>();
	
	public PrimListModel() {
		elements.add(primNumber);
	}
	
	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return elements.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
		
	}
	
	public void next() {
		boolean isPrime = false; 
		int prime = primNumber;
		while(!isPrime) {
			boolean found = false;
			for(int i=2;i<primNumber+1;++i) {
				if((primNumber+1)%i==0) {
					found = true;
					break;
				}
			}
			if(!found) {
				prime = primNumber+1;
				isPrime = true;
			} else {
				primNumber = primNumber+1;
			}
		}
		++primNumber;
		elements.add(prime);
		notifyListeners();
	}

	private void notifyListeners() {
		int pos = elements.size();
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for(ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
		
	}

	
	
}

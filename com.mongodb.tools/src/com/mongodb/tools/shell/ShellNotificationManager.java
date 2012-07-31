/*******************************************************************************
 * Copyright (c) 2005, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial API and implementation
 *******************************************************************************/
package com.mongodb.tools.shell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.tools.shell.commands.AbstractShellCommand;

/**
 * Notification manager for shell.
 */
public class ShellNotificationManager {
	private List<ListenerEntry> listenerList = new ArrayList<ListenerEntry>();

	/**
	 * For masking event on all changes.
	 */
	public static final int ALL_EVENTS = 0xFFFF;

	private class ListenerEntry {
		private IShellCommandListener listener;
		private int eventMask;

		protected ListenerEntry(IShellCommandListener curListener, int curEventMask) {
			listener = curListener;
			eventMask = curEventMask;
		}

		protected IShellCommandListener getListener() {
			return listener;
		}

		protected int getEventMask() {
			return eventMask;
		}
	}

	/**
	 * Create a new notification manager.
	 */
	public ShellNotificationManager() {
		super();
	}

	/**
	 * Add listener for all events.
	 * 
	 * @param curListener
	 */
	public void addListener(IShellCommandListener curListener) {
		addListener(curListener, ALL_EVENTS);
	}

	/**
	 * Add listener for the events specified by the mask.
	 * 
	 * @param curListener
	 * @param eventMask
	 */
	public void addListener(IShellCommandListener curListener, int eventMask) {
//		if (Trace.FINEST) {
//			Trace.trace(Trace.STRING_FINEST,
//					"->- Adding shell listener to notification manager: "
//							+ curListener + " " + eventMask + " ->-");
//		}
		if (curListener == null) {
			return;
		}

		synchronized (listenerList) {
			listenerList.add(new ListenerEntry(curListener, eventMask));
		}
	}

	public void broadcastChange(AbstractShellCommand event) {
//		if (Trace.FINEST) {
//			Trace.trace(Trace.STRING_FINEST, "->- Broadcasting shell event: "
//					+ event + " ->-");
//		}
		if (event == null) {
			return;
		}
		int eventKind = event.getKind();
//		if (Trace.FINEST) {
//			Trace.trace(Trace.STRING_FINEST, "  Shell event kind: " + eventKind
//					+ " ->-");
//		}

		// only notify listeners that listen to module event
		int size;
		ListenerEntry[] listeners;
		synchronized (listenerList) {
			size = listenerList.size();
			listeners = listenerList.toArray(new ListenerEntry[size]);
		}
		for (int i = 0; i < size; i++) {
			ListenerEntry curEntry = listeners[i];
			int mask = curEntry.getEventMask();

			// check if the type of the event matches the mask, e.g. shell or
			// module change
			boolean isTypeMatch = true;//((mask & eventKind & ShellEvent.SERVER_CHANGE) != 0);
			// check the kind of change
			// take out the ShellEvent.SERVER_CHANGE bit and
			// ShellEvent.MODULE_CHANGE bit
			int kindOnly = -1;//ShellEvent.SERVER_CHANGE;// (eventKind |
													// ShellEvent.SERVER_CHANGE)
													// ^
													// ShellEvent.SERVER_CHANGE
													// ^
													// ShellEvent.MODULE_CHANGE;
			boolean isKindMatch = (mask & kindOnly) != 0;

			if (isTypeMatch && isKindMatch) {
//				if (Trace.FINEST) {
//					Trace.trace(
//							Trace.STRING_FINEST,
//							"->- Firing shell event to listener: "
//									+ curEntry.getListener() + " ->-");
				}
				try {
//					if (Trace.LISTENERS) {
//						Trace.trace(
//								Trace.STRING_LISTENERS,
//								"  Firing shell event to listener: "
//										+ curEntry.getListener());
//					}
					curEntry.getListener().commandAdded(event);
				} catch (Exception e) {
//					if (Trace.SEVERE) {
//						Trace.trace(
//								Trace.STRING_SEVERE,
//								"  Error firing shell event: "
//										+ curEntry.getListener(), e);
//					}
				}
//				if (Trace.LISTENERS) {
//					Trace.trace(Trace.STRING_LISTENERS,
//							"-<- Done firing shell event -<-");
//				}
			}
		}
//		if (Trace.FINEST) {
//			Trace.trace(Trace.STRING_FINEST,
//					"-<- Done broadcasting shell event -<-");
//		}
//	}

	/**
	 * Returns true if the listener list is empty, or false otherwise.
	 * 
	 * @return true if the listener list is empty, or false otherwise
	 */
	public boolean hasNoListeners() {
		return listenerList.isEmpty();
	}

	/**
	 * Remove a listener from notification.
	 * 
	 * @param curListener
	 */
	public void removeListener(IShellCommandListener curListener) {
//		if (Trace.FINEST) {
//			Trace.trace(Trace.STRING_FINEST,
//					"->- Removing shell listener from notification manager: "
//							+ curListener + " ->-");
//		}
		if (curListener == null)
			return;

		synchronized (listenerList) {
			ListenerEntry matchedListenerEntry = null;
			Iterator listenerIter = listenerList.iterator();
			while (matchedListenerEntry == null && listenerIter.hasNext()) {
				ListenerEntry curEntry = (ListenerEntry) listenerIter.next();
				if (curListener.equals(curEntry.getListener())) {
					matchedListenerEntry = curEntry;
				}
			}
			if (matchedListenerEntry != null)
				listenerList.remove(matchedListenerEntry);
		}
	}
}
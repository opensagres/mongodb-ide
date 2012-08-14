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
package fr.opensagres.mongodb.ide.core.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.opensagres.mongodb.ide.core.IServerListener;
import fr.opensagres.mongodb.ide.core.ServerEvent;

/**
 * Notification manager for server.
 */
public class ServerNotificationManager {
	private List<ListenerEntry> listenerList = new ArrayList<ListenerEntry>();

	/**
	 * For masking event on all changes.
	 */
	public static final int ALL_EVENTS = 0xFFFF;

	private class ListenerEntry {
		private IServerListener listener;
		private int eventMask;

		protected ListenerEntry(IServerListener curListener, int curEventMask) {
			listener = curListener;
			eventMask = curEventMask;
		}

		protected IServerListener getListener() {
			return listener;
		}

		protected int getEventMask() {
			return eventMask;
		}
	}

	/**
	 * Create a new notification manager.
	 */
	public ServerNotificationManager() {
		super();
	}

	/**
	 * Add listener for all events.
	 * 
	 * @param curListener
	 */
	public void addListener(IServerListener curListener) {
		addListener(curListener, ALL_EVENTS);
	}

	/**
	 * Add listener for the events specified by the mask.
	 * 
	 * @param curListener
	 * @param eventMask
	 */
	public void addListener(IServerListener curListener, int eventMask) {
		if (Trace.FINEST) {
			Trace.trace(Trace.STRING_FINEST,
					"->- Adding server listener to notification manager: "
							+ curListener + " " + eventMask + " ->-");
		}
		if (curListener == null) {
			return;
		}

		synchronized (listenerList) {
			listenerList.add(new ListenerEntry(curListener, eventMask));
		}
	}

	public void broadcastChange(ServerEvent event) {
		if (Trace.FINEST) {
			Trace.trace(Trace.STRING_FINEST, "->- Broadcasting server event: "
					+ event + " ->-");
		}
		if (event == null) {
			return;
		}
		int eventKind = event.getKind();
		if (Trace.FINEST) {
			Trace.trace(Trace.STRING_FINEST, "  Server event kind: "
					+ eventKind + " ->-");
		}

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

			// check if the type of the event matches the mask, e.g. server or
			// module change
			boolean isTypeMatch = ((mask & eventKind & ServerEvent.SERVER_CHANGE) != 0)
					|| ((mask & eventKind & ServerEvent.DATABASE_CHANGE) != 0);
			// check the kind of change
			// take out the ServerEvent.SERVER_CHANGE bit and
			// ServerEvent.MODULE_CHANGE bit
			int kindOnly = (eventKind | ServerEvent.SERVER_CHANGE | ServerEvent.DATABASE_CHANGE)
					^ ServerEvent.SERVER_CHANGE ^ ServerEvent.DATABASE_CHANGE;
			boolean isKindMatch = (mask & kindOnly) != 0;
			if (isTypeMatch && isKindMatch) {
				if (Trace.FINEST) {
					Trace.trace(
							Trace.STRING_FINEST,
							"->- Firing server event to listener: "
									+ curEntry.getListener() + " ->-");
				}
				try {
					if (Trace.LISTENERS) {
						Trace.trace(Trace.STRING_LISTENERS,
								"  Firing server event to listener: "
										+ curEntry.getListener());
					}
					curEntry.getListener().serverChanged(event);
				} catch (Exception e) {
					if (Trace.SEVERE) {
						Trace.trace(
								Trace.STRING_SEVERE,
								"  Error firing server event: "
										+ curEntry.getListener(), e);
					}
				}
				if (Trace.LISTENERS) {
					Trace.trace(Trace.STRING_LISTENERS,
							"-<- Done firing server event -<-");
				}
			}
		}
		if (Trace.FINEST) {
			Trace.trace(Trace.STRING_FINEST,
					"-<- Done broadcasting server event -<-");
		}
	}

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
	public void removeListener(IServerListener curListener) {
		if (Trace.FINEST) {
			Trace.trace(Trace.STRING_FINEST,
					"->- Removing server listener from notification manager: "
							+ curListener + " ->-");
		}
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